package org.doming.develop.http.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpResult;
import sun.misc.BASE64Encoder;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: HttpClient工具类
 * @Author: Dongming
 * @Date: 2018-12-08 11:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class HttpClientUtil {

	protected static final Logger log = LogManager.getLogger(HttpClientUtil.class);

	/**
	 * 最大连接数
	 */
	private static final int MAX_CONN = 20;

	/**
	 * 将每个路由基础的连接增加
	 */
	private static final int MAX_PRE_ROUTE = 10;

	/**
	 * 设置连接建立的超时时间为5s
	 */
	static final int CONNECT_TIMEOUT = 5000;

	/**
	 * 设置通信建立的超时时间为8s
	 */
	static final int SOCKET_TIMEOUT = 8000;

	/**
	 * 清理空闲时间
	 */
	private static final int HTTP_IDLE_TIMEOUT = 20000;

	/**
	 * 最大循环磁珠
	 */
	private static final int MAX_RECURSIVE_SIZE = 5;

	private ScheduledExecutorService monitorExecutor = null;
	private PoolingHttpClientConnectionManager manager = null;
	private HttpClientBuilder builder = null;
	private RequestConfig.Builder requestConfigBuilder = null;

	private static volatile HttpClientUtil instance = null;

	private HttpClientUtil() {
	}

	/**
	 * 获取 HttpClientUtil 实例
	 *
	 * @return HttpClientUtil实例
	 */
	public static HttpClientUtil findInstance() {
		if (instance == null) {
			synchronized (HttpClientUtil.class) {
				if (instance == null) {
					HttpClientUtil instance = new HttpClientUtil();
					// 初始化
					instance.init();
					HttpClientUtil.instance = instance;
				}
			}
		}
		return instance;
	}

	/**
	 * 初始化HttpClient连接构建工具
	 */
	private void init() {

		//定义链接协议
		ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();

		//注册链接协议
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainSocketFactory).register("https", sslSocketFactory).build();

		//HttpClient池管理类
		manager = new PoolingHttpClientConnectionManager(registry);

		//设置连接参数
		manager.setMaxTotal(MAX_CONN);
		manager.setDefaultMaxPerRoute(MAX_PRE_ROUTE);

		//请求失败时,进行请求重试<错误监听>
		HttpRequestRetryHandler handler = (exception, executionCount, context) -> {
			if (executionCount > 3) {
				//重试超过3次,放弃请求
				log.fatal("重试有3次以上，放弃请求");
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				//服务器没有响应,可能是服务器断开了连接,应该重试
				log.error("没有收到服务器的响应，请重试");
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// SSL握手异常
				log.error("SSL握手异常");
				return false;
			}
			if (exception instanceof InterruptedIOException) {
				//超时
				log.info("中断的IO异常");
				return false;
			}
			if (exception instanceof UnknownHostException) {
				// 服务器不可达
				log.error("服务器主机未知");
				return false;
			}
			if (exception instanceof SSLException) {
				log.error("异常SSLException");
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			//如果请求不是关闭连接的请求
			return !(request instanceof HttpEntityEnclosingRequest);
		};
		//连接创建类
		builder = HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler);
		builder.setRedirectStrategy(new LaxRedirectStrategy());
		//请求配置创建类
		requestConfigBuilder = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).setCookieSpec(CookieSpecs.STANDARD);

		//开启监控线程,对异常和空闲线程进行关闭
		//替代Timer，实现多线程任务调度
		monitorExecutor = new ScheduledThreadPoolExecutor(1,
				new BasicThreadFactory.Builder().namingPattern("close-idle-connection-pool-%d").daemon(true).build());
		monitorExecutor.scheduleAtFixedRate(() -> {
			//关闭异常连接
			manager.closeExpiredConnections();
			//关闭5s空闲的连接
			manager.closeIdleConnections(HTTP_IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
			log.debug("关闭过期并空闲超过" + HTTP_IDLE_TIMEOUT + "毫秒连接");
		}, HTTP_IDLE_TIMEOUT, HTTP_IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (instance.manager != null) {
			instance.manager.closeExpiredConnections();
			instance.manager.close();
			instance.manager = null;
		}
		if (instance.monitorExecutor != null) {
			instance.monitorExecutor.shutdown();
			instance.monitorExecutor = null;
		}
		instance.requestConfigBuilder = null;
		instance.builder = null;
		instance = null;
		log.info("  销毁RabbitMQ 完成" + HttpClientUtil.class);
	}

	/**
	 * 创建一个可关闭的HttpClient类
	 *
	 * @return 一个可关闭的HttpClient类
	 */
	public CloseableHttpClient createHttpClient() {
		return builder.build();
	}

	/**
	 * 以get方式，请求资源或服务
	 *
	 * @param httpConfig 参数配置
	 * @return 请求结果
	 */
	public HttpResult get(HttpClientConfig httpConfig) {
		return send(httpConfig.method(HttpConfig.Method.GET));
	}

	/**
	 * 以Post方式，请求资源或服务
	 *
	 * @param config 请求参数配置
	 * @return 返回处理结果
	 */
	public HttpResult post(HttpClientConfig config) {
		return send(config.method(HttpConfig.Method.POST));
	}

	/**
	 * 以GET方式，html页面
	 *
	 * @param config 请求参数配置
	 * @return 返回处理结果
	 */
	public String getHtml(HttpClientConfig config) {
		return sendHtml(config.method(HttpConfig.Method.GET));
	}

	/**
	 * 以Post方式，html页面
	 *
	 * @param config 请求参数配置
	 * @return 返回处理结果
	 */
	public String postHtml(HttpClientConfig config) {
		return sendHtml(config.method(HttpConfig.Method.POST));
	}

	/**
	 * 请求资源或服务
	 *
	 * @param httpConfig 请求参数配置
	 * @return 处理结果
	 */
	public HttpResult send(HttpClientConfig httpConfig) {
		HttpResponse httpResponse = null;
		try {
			httpResponse = execute(httpConfig);
			HttpResult result = httpResult(httpResponse, httpConfig.outputEncoding());
			if (result == null) {
				return new HttpResult();
			}
			if (setCookie(httpConfig, StringTool.getHost(httpConfig.url()), result.getHtml())) {
				result = send(httpConfig, httpConfig.url(), httpConfig.map());
			}
			return result;
		} finally {
			if (httpResponse != null) {
				close(httpResponse);
			}
			httpConfig.requestEnd();
		}
	}
	/**
	 * 请求资源或服务
	 *
	 * @param httpConfig 请求参数配置
	 * @return 处理结果
	 */
	public HttpResult send(HttpClientConfig httpConfig, String url, Map<String, Object> map, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		HttpResponse httpResponse = null;
		try {
			httpResponse = execute(httpConfig.url(url).map(map));
			HttpResult result = httpResult(httpResponse, httpConfig.outputEncoding());

			if (index[0] > MAX_RECURSIVE_SIZE) {
				log.error("请求循环错误次数过多，请求URL{%s}，请求结果页面{%s}", httpConfig.url(), result.getHtml());
				return result;
			}
			if (result == null) {
				return null;
			}
			if (setCookie(httpConfig, StringTool.getHost(httpConfig.url()), result.getHtml())) {
				return send(httpConfig, url, map, ++index[0]);
			}
			return result;
		} finally {
			if (httpResponse != null) {
				close(httpResponse);
			}
			httpConfig.requestEnd();
		}
	}

	/**
	 * 请求html页面
	 *
	 * @param httpConfig 请求参数配置
	 * @return html页面
	 */
	public String sendHtml(HttpClientConfig httpConfig) {
		return send(httpConfig).getHtml();
	}

	/**
	 * 获取图片base64字符串
	 *
	 * @param httpConfig 请求参数配置
	 * @return base64字符串
	 */
	public String getImage(HttpClientConfig httpConfig) {
		String image = null;
		HttpResponse httpResponse = null;
		try {
			httpResponse = execute(httpConfig.method(HttpConfig.Method.GET));
			if (httpResponse == null) {
				return null;
			}
			image = httpResponse2Image(httpResponse);
		} catch (IOException e) {
			log.error("获取请求结果页面失败", e);
		} finally {
			if (httpResponse != null) {
				close(httpResponse);
			}
			httpConfig.requestEnd();
		}
		return image;
	}

	/**
	 * 请求资源或服务
	 *
	 * @param httpConfig http请求参数配置
	 * @return http请求结果
	 */
	public HttpResponse execute(HttpClientConfig httpConfig) {
		//结果实体
		HttpResponse httpResponse = null;
		try {
			//创建请求对象
			HttpRequestBase request = getRequest(httpConfig.url(), httpConfig.method());
			setRequestConfig(request, httpConfig);
			//设置header信息
			request.setHeaders(httpConfig.headers());
			request.setHeader("User-Agent", HttpConfig.USER_AGENT);
			//判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
			if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
				List<NameValuePair> nameValuePairs = new ArrayList<>();

				//检测url中是否存在参数
				httpConfig.url(checkHasParas(httpConfig.url(), nameValuePairs, httpConfig.inputEncoding()));

				//装填参数
				HttpEntity entity = map2HttpEntity(nameValuePairs, httpConfig.map(), httpConfig.inputEncoding());
				httpConfig.map(null);
				//设置参数到请求对象中
				((HttpEntityEnclosingRequestBase) request).setEntity(entity);

			}
			//执行请求操作，并拿到结果（同步阻塞）
			httpResponse = (httpConfig.httpContext() == null) ?
					httpConfig.httpClient().execute(request) :
					httpConfig.httpClient().execute(request, httpConfig.httpContext());
			if (httpConfig.isReturnRespHeaders()) {
				//获取所有response的header信息
				httpConfig.headers(httpResponse.getAllHeaders());
			}
			log.trace("页面加载成功，地址为：" + httpConfig.url());
		} catch (IOException e) {
			log.warn("页面加载错误，地址为：{},错误信息为：{}", httpConfig.url(), e.getMessage());
		}
		return httpResponse;
	}

	/**
	 * 根据自己的配置设置请求参数	<br>
	 * timeOut：超时				<br>
	 * socketTimeout：通讯超时	<br>
	 * proxy：代理IP				<br>
	 *
	 * @param request    请求request对象
	 * @param httpConfig http请求配置类
	 * @throws NullPointerException 请求request对象为空
	 */
	private void setRequestConfig(HttpRequestBase request, HttpClientConfig httpConfig) throws NullPointerException {
		if (request == null) {
			log.error("请求request对象为空，配置失败");
			throw new NullPointerException("请求request对象为空");
		}
		if (httpConfig.timeOut() != null) {
			requestConfigBuilder.setSocketTimeout(httpConfig.timeOut());
			requestConfigBuilder.setConnectionRequestTimeout(httpConfig.timeOut());
			httpConfig.timeOut(null);
		}
		if (httpConfig.socketTimeout() != null) {
			requestConfigBuilder.setSocketTimeout(httpConfig.socketTimeout());
			httpConfig.socketTimeout(null);
		}
		if (httpConfig.proxy() != null) {
			requestConfigBuilder.setProxy(httpConfig.proxy());
		}
		if (httpConfig.redirectsEnabled() != null) {
			requestConfigBuilder.setRedirectsEnabled(httpConfig.redirectsEnabled());
		}
		RequestConfig requestConfig = requestConfigBuilder.build();
		request.setConfig(requestConfig);
	}

	/**
	 * 获取请求结果输出对象<br>
	 * <code>html:				</code>请求结果页面<br>
	 * <code>statusCode:		</code>请求状态码<br>
	 * <code>location: 			</code>请求重定向地址-只有状态码为302时起作用<br>
	 * <code>httpResponse:		</code>请求结果Response对象用户可以自定义获取自己想要的数据
	 *
	 * @param httpResponse 请求结果Response对象
	 * @param encoding     编码格式
	 * @return 请求结果输出对象
	 */
	private HttpResult httpResult(HttpResponse httpResponse, String encoding) {
		if (httpResponse == null) {
			return null;
		}
		HttpResult result;
		String html = httpResponse2Html(httpResponse, encoding);
		int statusCode = httpResponse2StatusCode(httpResponse);
		String location = httpResponse2location(httpResponse);
		result = new HttpResult(html, statusCode, location);
		return result;
	}

	/**
	 * 关闭http请求结果response
	 *
	 * @param httpResponse http请求结果
	 */
	private void close(HttpResponse httpResponse) {
		try {
			if (httpResponse == null) {
				log.error("请求结果response对象为空，关闭失败");
				throw new NullPointerException("请求结果response对象为空");
			}
			//如果CloseableHttpResponse 是resp的父类，则支持关闭
			if (CloseableHttpResponse.class.isAssignableFrom(httpResponse.getClass())) {
				((CloseableHttpResponse) httpResponse).close();
			}
		} catch (IOException e) {
			log.error("关闭请求结果response失败 ", e);
		}
	}

	/**
	 * 根据请求方法名，获取request请求对象
	 */
	private HttpRequestBase getRequest(String url, HttpConfig.Method method) {
		HttpRequestBase request;
		switch (method) {
			// HttpGet
			case GET:
				request = new HttpGet(url);
				break;
			// HttpPost
			case POST:
				request = new HttpPost(url);
				break;
			// HttpHead
			case HEAD:
				request = new HttpHead(url);
				break;
			// HttpPut
			case PUT:
				request = new HttpPut(url);
				break;
			// HttpDelete
			case OPTIONS:
				request = new HttpOptions(url);
				break;
			// HttpTrace
			case DELETE:
				request = new HttpDelete(url);
				break;
			// HttpTrace
			case TRACE:
				request = new HttpTrace(url);
				break;
			// HttpPatch
			case PATCH:
				request = new HttpPatch(url);
				break;
			default:
				request = null;
				break;
		}
		return request;
	}

	/**
	 * 获取请求结果页面
	 *
	 * @param httpResponse 请求结果对象
	 * @param encoding     编码
	 * @return 请求结果页面
	 */
	private String httpResponse2Html(HttpResponse httpResponse, String encoding) {
		String body = null;
		try {
			if (httpResponse.getEntity() != null) {
				// 按指定编码转换结果实体为String类型
				body = EntityUtils.toString(httpResponse.getEntity(), encoding);
			} else {//有可能是head请求
				body = httpResponse.getStatusLine().toString();
			}
		} catch (IOException e) {
			log.error("获取请求结果页面失败,IO错误{}", e.getLocalizedMessage());
		}
		return body;

	}

	/**
	 * 获取请求结果，將圖片進行base64轉換
	 *
	 * @param httpResponse 请求结果
	 * @return base64字符串
	 */
	private String httpResponse2Image(HttpResponse httpResponse) throws IOException {
		String image;
		try {
			if (httpResponse.getEntity() != null) {
				// 將结果实体转换为base64字符串类型
				byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
				BASE64Encoder encoder = new BASE64Encoder();
				image = encoder.encode(body);
			} else {//有可能是head请求
				image = httpResponse.getStatusLine().toString();
			}
		} catch (IOException e) {
			log.error("获取请求结果页面失败,IO错误");
			throw e;
		}
		return image;
	}

	/**
	 * 获取将HttpResponse状态码
	 */
	private int httpResponse2StatusCode(HttpResponse httpResponse) {
		return httpResponse.getStatusLine().getStatusCode();
	}

	/**
	 * 获取导向地址
	 */
	private String httpResponse2location(HttpResponse httpResponse) {
		String header = "Location";
		if (httpResponse.getLastHeader(header) == null) {
			return null;
		}
		return httpResponse.getLastHeader(header).getValue();
	}

	/**
	 * 参数转换，将map中的参数，转到参数列表中
	 *
	 * @param nameValuePairs 参数列表
	 * @param map            参数列表（map）
	 * @param encoding       编码
	 * @return 返回HttpEntity
	 * @throws UnsupportedEncodingException 不支持的编码异常
	 */
	private HttpEntity map2HttpEntity(List<NameValuePair> nameValuePairs, Map<String, Object> map, String encoding)
			throws UnsupportedEncodingException {

		/*
		 * 传入参数特定类型
		 */
		final String entityString = "$ENTITY_STRING$";
		final String entityJson = "$ENTITY_JSON$";
		final String entityFile = "$ENTITY_FILE$";
		final String entityBytes = "$ENTITY_BYTES$";
		final String entityInputstream = "$ENTITY_INPUTSTREAM$";
		final String entitySerializable = "$ENTITY_SERIALIZABLE$";
		final String entityMultipart = "$ENTITY_MULTIPART$";
		final List<String> specialEntitiy = Arrays
				.asList(entityString, entityJson, entityBytes, entityFile, entityInputstream, entitySerializable,
						entityMultipart);

		HttpEntity entity = null;
		if (ContainerTool.notEmpty(map)) {
			boolean isSpecial = false;
			// 拼接参数
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				//判断是否在之中
				if (specialEntitiy.contains(entry.getKey())) {
					isSpecial = true;
					//string
					if (entityString.equals(entry.getKey())) {
						entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
						break;
					}
					//JSON
					else if (entityJson.equals(entry.getKey())) {
						entity = new StringEntity(String.valueOf(entry.getValue()),
								ContentType.create(" application/json", encoding));
						break;
					}
					//Bytes
					else if (entityBytes.equals(entry.getKey())) {
						entity = new ByteArrayEntity((byte[]) entry.getValue());
						break;

					}
					//file
					else if (entityFile.equals(entry.getKey())) {
						if (File.class.isAssignableFrom(entry.getValue().getClass())) {
							entity = new FileEntity((File) entry.getValue(), ContentType.APPLICATION_OCTET_STREAM);
						} else if (entry.getValue().getClass() == String.class) {
							entity = new FileEntity(new File((String) entry.getValue()),
									ContentType.create("text/plain", "UTF-8"));
						}
						break;
					} else if (entityInputstream.equals(entry.getKey())) {
						break;
					} else if (entitySerializable.equals(entry.getKey())) {
						break;
					} else if (entityMultipart.equals(entry.getKey())) {
						File[] files = null;
						if (File.class.isAssignableFrom(entry.getValue().getClass().getComponentType())) {
							files = (File[]) entry.getValue();
						} else if (entry.getValue().getClass().getComponentType() == String.class) {
							String[] names = (String[]) entry.getValue();
							files = new File[names.length];
							for (int i = 0; i < names.length; i++) {
								files[i] = new File(names[i]);
							}
						}
						MultipartEntityBuilder builder = MultipartEntityBuilder.create();
						// 设置请求的编码格式
						builder.setCharset(Charset.forName(encoding));
						// 设置浏览器兼容模式
						builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
						int count = 0;
						if (files != null && files.length != 0) {
							for (File file : files) {
								builder.addBinaryBody(String.valueOf(map.get(entityMultipart + ".name")) + count++,
										file);
							}
						}
						boolean forceRemoveContentTypeCharset = (Boolean) map.get(entityMultipart + ".rmCharset");
						Map<String, Object> m = new HashMap<>(map);
						m.remove(entityMultipart);
						m.remove(entityMultipart + ".name");
						m.remove(entityMultipart + ".rmCharset");

						// 发送的数据
						for (Map.Entry<String, Object> e : m.entrySet()) {
							builder.addTextBody(e.getKey(), String.valueOf(e.getValue()),
									ContentType.create("text/plain", encoding));
						}
						// 生成 HTTP  实体
						entity = builder.build();
						//强制去除contentType中的编码设置，否则，在某些情况下会导致上传失败
						if (forceRemoveContentTypeCharset) {
							removeContentTypeCharset(encoding, entity);
						}
						break;
					} else {
						nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
					}
				} else {
					nameValuePairs.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
			if (!isSpecial) {
				entity = new UrlEncodedFormEntity(nameValuePairs, encoding);
			}
		}
		return entity;
	}

	/**
	 * 检测url是否含有参数，如果有，则把参数加到参数列表中
	 *
	 * @param url            资源地址
	 * @param nameValuePairs 参数列表
	 * @param encoding       编码
	 * @return 返回去掉参数的url
	 * @throws UnsupportedEncodingException 不支持的编码异常
	 */
	private String checkHasParas(String url, List<NameValuePair> nameValuePairs, String encoding)
			throws UnsupportedEncodingException {
		// 检测url中是否存在参数
		if (url.contains("?") && url.indexOf("?") < url.indexOf("=")) {
			Map<String, Object> map = buildParas(url.substring(url.indexOf("?") + 1));
			map2HttpEntity(nameValuePairs, map, encoding);
			url = url.substring(0, url.indexOf("?"));
		}
		return url;
	}

	/**
	 * 生成参数
	 * 参数格式：k1=v1&amp=;&k2=v2
	 *
	 * @param paras 参数列表
	 * @return 返回参数列表（map）
	 */
	private Map<String, Object> buildParas(String paras) {
		String[] p = paras.split("&");
		String[][] ps = new String[p.length][2];
		int pos;
		for (int i = 0; i < p.length; i++) {
			pos = p[i].indexOf("=");
			ps[i][0] = p[i].substring(0, pos);
			ps[i][1] = p[i].substring(pos + 1);
		}
		return buildParas(ps);
	}

	/**
	 * 生成参数
	 * 参数类型：{{"k1","v1"},{"k2","v2"}}
	 *
	 * @param paras 参数列表
	 * @return 返回参数列表（map）
	 */
	private Map<String, Object> buildParas(String[][] paras) {
		// 创建参数队列
		Map<String, Object> map = new HashMap<>(paras.length);
		for (String[] para : paras) {
			map.put(para[0], para[1]);
		}
		return map;
	}

	/**
	 * 移除content-type中的charset
	 *
	 * @param encoding 编码
	 * @param entity   请求参数及数据信息
	 */
	private void removeContentTypeCharset(String encoding, HttpEntity entity) throws UnsupportedEncodingException {
		try {
			Class<?> clazz = entity.getClass();
			Field field = clazz.getDeclaredField("contentType");
			//将字段的访问权限设为true：即去除private修饰符的影响
			field.setAccessible(true);
			if (Modifier.isFinal(field.getModifiers())) {
				//去除final修饰符的影响，将字段设为可修改的
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			}
			BasicHeader o = (BasicHeader) field.get(entity);
			field.set(entity, new BasicHeader(HTTP.CONTENT_TYPE, o.getValue().replace("; charset=" + encoding, "")));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new UnsupportedEncodingException("移除content-type中的charset失败");
		}
	}

	/**
	 * 需要设置cookie
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param html       需要解析的页面
	 * @return 设置结果
	 */
	private static boolean setCookie(HttpClientConfig httpConfig, String url, String html) {
		//是否要放入cookie
		String cookieStr = StringUtils.substringBetween(html, "document.cookie='", "'");
		if (StringTool.notEmpty(cookieStr)) {
			HttpClientContext context = httpConfig.httpContext();
			if (context == null) {
				context = HttpClientContext.create();
				httpConfig.httpContext(context);
			}
			String name = "", value = null, path = null;
			for (String item : cookieStr.split(";")) {
				int index = item.indexOf('=');
				String key = item.substring(0, index).trim();
				switch (key) {
					case ClientCookie.PATH_ATTR:
						path = item.substring(index + 1).trim();
						break;
					case ClientCookie.DOMAIN_ATTR:
						break;
					default:
						name = key;
						value = item.substring(index + 1).trim();
				}
			}
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			cookie.setDomain(url);
			cookie.setPath(path);
			context.getCookieStore().addCookie(cookie);
			return true;
		}
		return false;
	}

	private static final ThreadLocal<HttpClientConfig> CONFIG_THREAD_LOCAL = new ThreadLocal<>();

	public static HttpClientConfig getConfig() {
		if (CONFIG_THREAD_LOCAL.get() == null) {
			CONFIG_THREAD_LOCAL.set(new HttpClientConfig());
		}
		return CONFIG_THREAD_LOCAL.get();
	}

	public static void removeConfig() {
		CONFIG_THREAD_LOCAL.remove();
	}

}
