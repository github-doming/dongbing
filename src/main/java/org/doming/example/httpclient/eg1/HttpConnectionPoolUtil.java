package org.doming.example.httpclient.eg1;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @Description: http连接池
 * @Author: Dongming
 * @Date: 2018-10-31 10:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpConnectionPoolUtil {

	protected static final Logger log = LogManager.getLogger(HttpConnectionPoolUtil.class);

	/**
	 * 设置连接建立的超时时间为3s
	 */
	private static final int CONNECT_TIMEOUT = 3000;
	/**
	 * 清理空闲时间
	 */
	private static final int HTTP_IDLE_TIMEOUT = 5000;

	/**
	 * 执行时间段
	 */
	private static final long HTTP_MONITOR_INTERVAL = 5000L;

	/**
	 * 设置通信建立的超时时间为3s
	 */
	private static final int SOCKET_TIMEOUT = 3000;

	/**
	 * 最大连接数
	 */
	private static final int MAX_CONN = 20;

	/**
	 * 将每个路由基础的连接增加
	 */
	private static final int MAX_PRE_ROUTE = 10;

	/**
	 * 发送请求的客户端
	 */
	private volatile static CloseableHttpClient httpClient;

	/**
	 * 连接池管理类
	 */
	private static PoolingHttpClientConnectionManager manager;

	/**
	 * 相当于线程锁,用于线程安全
	 */
	private final static Object SYNC_LOCK = new Object();

	/**
	 * 获取httpclient实例
	 *
	 * @return httpclient实例
	 */
	public static CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			//多线程下多个线程同时调用getHttpClient容易导致重复创建httpClient对象的问题,所以加上了同步锁
			synchronized (SYNC_LOCK) {
				if (httpClient == null) {
					httpClient = createHttpClient();
					//开启监控线程,对异常和空闲线程进行关闭
					//替代Timer，实现多线程任务调度
					ScheduledExecutorService monitorExecutor = new ScheduledThreadPoolExecutor(1,
							new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true)
									.build());
					//monitorExecutor = Executors.newScheduledThreadPool(1);
					monitorExecutor.scheduleAtFixedRate(() -> {
						//关闭异常连接
						manager.closeExpiredConnections();
						//关闭5s空闲的连接
						manager.closeIdleConnections(HTTP_IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
						log.info("关闭过期并空闲超过5秒连接");
					}, HTTP_MONITOR_INTERVAL, HTTP_MONITOR_INTERVAL, TimeUnit.MILLISECONDS);
				}
			}
		}
		return httpClient;
	}

	/**
	 * get请求
	 *
	 * @param url 地址
	 * @return 请求结果
	 */
	public static String get(String url) {
		return get(url, CONNECT_TIMEOUT);
	}

	public static String get(String url, int timeOut) {
		String resultStr = null;
		HttpGet httpGet = new HttpGet(url);
		setRequestConfig(httpGet, timeOut);
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient().execute(httpGet);
			HttpEntity entity = response.getEntity();
			resultStr = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
		} catch (IOException e) {
			log.error("页面请求失败", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("页面请求关闭失败", e);
			}
		}
		return resultStr;
	}

	/**
	 * post请求
	 *
	 * @param url    地址
	 * @param params 请求参数
	 * @return 请求结果
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, params, CONNECT_TIMEOUT);
	}
	public static String post(String url, Map<String, String> params, int timeOut) {
		String resultStr = null;
		HttpPost httpPost = new HttpPost(url);
		setRequestConfig(httpPost, timeOut);
		if (params != null) {
			setPostParams(httpPost, params);
		}
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient().execute(httpPost);
			HttpEntity entity = response.getEntity();
			resultStr = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
		} catch (IOException e) {
			log.error("页面请求失败", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("页面请求关闭失败", e);
			}
		}
		return resultStr;
	}
	/**
	 * 设置请求配置
	 *
	 * @param httpRequestBase 请求对象
	 * @param timeOut
	 */
	private static void setRequestConfig(HttpRequestBase httpRequestBase, int timeOut) {
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeOut)
				.setConnectTimeout(timeOut).setSocketTimeout(SOCKET_TIMEOUT).build();
		httpRequestBase.setConfig(requestConfig);
	}

	/**
	 * 设置发送参数
	 *
	 * @param httpPost post请求
	 * @param params   参数
	 */
	private static void setPostParams(HttpPost httpPost, Map<String, String> params) {
		List<NameValuePair> nameValuePairs = new ArrayList<>(params.size());
		for (Map.Entry<String, String> entry: params.entrySet()){
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("设置参数失败", e);
		}
	}

	/**
	 * 构建httpclient实例
	 *
	 * @return httpclient实例
	 */
	private static CloseableHttpClient createHttpClient() {

		ConnectionSocketFactory plainSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();

		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", plainSocketFactory).register("https", sslSocketFactory).build();

		manager = new PoolingHttpClientConnectionManager(registry);
		//设置连接参数
		manager.setMaxTotal(MAX_CONN);
		manager.setDefaultMaxPerRoute(MAX_PRE_ROUTE);

		//请求失败时,进行请求重试
		HttpRequestRetryHandler handler = (exception, executionCount, context) -> {
			if (executionCount > 3) {
				//重试超过3次,放弃请求
				log.error("retry has more than 3 time, give up request");
				return false;
			}
			if (exception instanceof NoHttpResponseException) {
				//服务器没有响应,可能是服务器断开了连接,应该重试
				log.error("receive no response from server, retry");
				return true;
			}
			if (exception instanceof SSLHandshakeException) {
				// SSL握手异常
				log.error("SSL hand shake exception");
				return false;
			}
			if (exception instanceof InterruptedIOException) {
				//超时
				log.error("InterruptedIOException");
				return false;
			}
			if (exception instanceof UnknownHostException) {
				// 服务器不可达
				log.error("server host unknown");
				return false;
			}
			if (exception instanceof SSLException) {
				log.error("SSLException");
				return false;
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			//如果请求不是关闭连接的请求
			return !(request instanceof HttpEntityEnclosingRequest);
		};
		return HttpClients.custom().setConnectionManager(manager).setRetryHandler(handler).build();
	}
}
