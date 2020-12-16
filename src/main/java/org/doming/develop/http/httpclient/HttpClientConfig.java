package org.doming.develop.http.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicHeader;
import org.doming.core.configs.CharsetConfig;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description: HttpClient 配置类
 * @Date 2018年06月26日 16:59
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 **/
public class HttpClientConfig {
	/**
	 * 地址
	 */
	private static final ThreadLocal<String> URLS  = new ThreadLocal<>();
	private static final ThreadLocal<Map<String,Object> > MAPS  = new ThreadLocal<>();

	public HttpClientConfig() {
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			proxy("127.0.0.1",1080);
		}
	}

	/**
	 * HttpHost代理IP
	 */
	private HttpHost proxy;

	/**
	 * HttpClient对象
	 */
	private HttpClient httpClient;

	/**
	 * http上下文
	 * 用于cookie操作
	 */
	private HttpClientContext httpContext;

	/**
	 * Header头信息
	 */
	private Header[] headers;

	/**
	 * 是否返回response的headers
	 */
	private boolean isReturnRespHeaders = false;

	/**
	 * map请求参数
	 */
	private Map<String, Object> map;

	/**
	 * json请求参数
	 */
	private String json;

	/**
	 * 输出编码
	 */
	private String outputEncoding = CharsetConfig.UTF8;

	/**
	 * 输出编码
	 */
	private String inputEncoding = CharsetConfig.UTF8;

	/**
	 * 编码
	 */
	private String encoding = Charset.defaultCharset().displayName();

	/**
	 * 请求方法
	 */
	private HttpConfig.Method method = HttpConfig.Method.GET;

	/**
	 * 超时时间
	 */
	private Integer timeout;

	/**
	 * 通讯超时
	 */
	private Integer socketTimeout;

	/**
	 * 自动重定向
	 */
	private Boolean redirectsEnabled;

	private Object data;

	public void destroy() {
		httpClient = null;
		httpContext = null;
	}

	/**
	 * 设置代理IP
	 *
	 * @param ip   IP
	 * @param port 端口
	 * @return 返回当前对象
	 */
	public HttpClientConfig proxy(String ip, int port) {
		return proxy(new HttpHost(ip, port));
	}

	/**
	 * 设置代理IP
	 *
	 * @param ip IP
	 * @return 返回当前对象
	 */
	public HttpClientConfig proxy(String ip) {
		return proxy(new HttpHost(ip));
	}

	/**
	 * 设置代理IP
	 *
	 * @param proxy 代理IP类
	 * @return 返回当前对象
	 */
	public HttpClientConfig proxy(HttpHost proxy) {
		this.proxy = proxy;
		return this;
	}

	/**
	 * 获取代理IP对象HttpHost
	 *
	 * @return 代理IP对象HttpHost
	 */
	public HttpHost proxy() {
		return proxy;
	}

	/**
	 * 设置 资源url
	 *
	 * @param url 资源url
	 * @return 返回当前对象
	 */
	public HttpClientConfig url(String url) {
		URLS.set(url);
		return this;
	}

	/**
	 * 获取 资源url
	 *
	 * @return url资源
	 */
	public String url() {
		return URLS.get();
	}

	/**
	 * 获取 请求方式
	 *
	 * @return 请求方式
	 */
	public HttpConfig.Method method() {
		return this.method;
	}

	/**
	 * 设置请求方式
	 *
	 * @param method 请求方式
	 * @return 返回配置当前对象
	 */
	public HttpClientConfig method(HttpConfig.Method method) {
		this.method = method;
		return this;
	}

	/**
	 * 获取输出编码
	 *
	 * @return 输出编码
	 */
	public String outputEncoding() {
		return outputEncoding == null ? encoding : outputEncoding;
	}

	/**
	 * 设置输出编码
	 *
	 * @return 配置对象
	 */
	public HttpClientConfig outputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
		return this;
	}

	/**
	 * 获取输入编码
	 *
	 * @return 输入编码
	 */
	public String inputEncoding() {
		return inputEncoding == null ? encoding : inputEncoding;
	}

	/**
	 * 设置输入编码
	 *
	 * @return 配置对象
	 */
	public HttpClientConfig inputEncoding(String inputEncoding) {
		this.inputEncoding = inputEncoding;
		return this;
	}
	/**
	 * 获取 HttpClient对象
	 *
	 * @return HttpClient对象
	 */
	public HttpClient httpClient() {
		return httpClient;
	}

	/**
	 * 设置 HttpClient对象
	 *
	 * @param httpClient HttpClient对象
	 * @return 配置对象
	 */
	public HttpClientConfig httpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
		return this;
	}

	/**
	 * 获取 Header[]对象
	 *
	 * @return header[]对象
	 */
	public Header[] headers() {
		if (ContainerTool.isEmpty(headers)) {
			return null;
		}
		return Arrays.copyOf(headers, headers.length);
	}

	/**
	 * 设置 Header[]对象
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig headers(Header[] headers) {
		if (ContainerTool.isEmpty(headers)) {
			this.headers = null;
			return this;
		}
		this.headers = Arrays.copyOf(headers, headers.length);
		return this;
	}

	/**
	 * 添加头信息
	 *
	 * @param name  header name
	 * @param value header value
	 * @return HttpConfig对象
	 */
	private HttpClientConfig addHeader(String name, String value) {
		if (headers == null) {
			headers = new Header[0];
		}
		headers = Arrays.copyOf(headers, headers.length + 1);
		headers[headers.length - 1] = new BasicHeader(name, value);
		return this;
	}

	/**
	 * 设置头信息
	 * 没有则设置，有则更新
	 *
	 * @param name  header name
	 * @param value header value
	 * @return HttpConfig对象
	 */
	public HttpClientConfig setHeader(String name, String value) {
		if (headers == null) {
			headers = new Header[1];
			headers[0] = new BasicHeader(name, value);
			return this;
		}
		for (int i = 0; i < headers.length; i++) {
			final Header current = headers[i];
			if (current.getName().equalsIgnoreCase(name)) {
				headers[i] = new BasicHeader(name, value);
				return this;
			}
		}
		return addHeader(name, value);
	}

	/**
	 * 获取map请求参数
	 *
	 * @return map传递参数
	 */
	public Map<String, Object> map() {
		return MAPS.get();
	}

	/**
	 * 设置map请求参数
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig map(Map<String, Object> map) {
		MAPS.set(map);
		return this;
	}

	/**
	 * 获取 json格式请求参数
	 *
	 * @return json格式请求参数
	 */
	public String json() {
		return json;
	}

	/**
	 * 设置 json格式请求参数
	 * 用于请求参数操作
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig json(String json) {
		this.json = json;
		return this;
	}

	/**
	 * 获取 HttpContext 对象
	 * 用于cookie操作
	 *
	 * @return HttpContext 对象
	 */
	public HttpClientContext httpContext() {
		return httpContext;
	}

	/**
	 * 设置 HttpContext 对象
	 * 用于cookie操作
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig httpContext(HttpClientContext httpContext) {
		this.httpContext = httpContext;
		return this;
	}

	/**
	 * 获取是否返回response的headers信息
	 *
	 * @return 是否
	 */
	public boolean isReturnRespHeaders() {
		return isReturnRespHeaders;
	}

	/**
	 * 设置是否返回response的headers信息
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig isReturnRespHeaders(boolean isReturnRespHeaders) {
		this.isReturnRespHeaders = isReturnRespHeaders;
		return this;
	}

	/**
	 * 获取超时时间
	 *
	 * @return 超时时间
	 */
	public Integer timeOut() {
		return timeout;
	}

	/**
	 * 设置超时时间
	 *
	 * @param timeout 超时时间
	 * @return HttpConfig对象
	 */
	public HttpClientConfig timeOut(Integer timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * 获取通讯超时时间
	 *
	 * @return 通讯超时时间
	 */
	public Integer socketTimeout() {
		return socketTimeout;
	}

	/**
	 * 设置通讯超时时间
	 *
	 * @param socketTimeout 通讯超时时间
	 * @return HttpConfig对象
	 */
	public HttpClientConfig socketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public Boolean redirectsEnabled() {
		return redirectsEnabled;
	}
	public HttpClientConfig redirectsEnabled(Boolean redirectsEnabled) {
		this.redirectsEnabled = redirectsEnabled;
		return this;
	}

	/**
	 * 传递参数
	 *
	 * @return 传递参数
	 */
	public Object data() {
		return data;
	}
	/**
	 * 传递参数
	 *
	 * @param data 传递参数
	 * @return HttpConfig对象
	 */
	public HttpClientConfig data(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * 检测map是否含有参数，如果有，则把参数加到url中
	 *
	 * @return HttpConfig对象
	 */
	public HttpClientConfig addParameter(Map<String, Object> map) {
		String url = url();
		if (StringTool.isEmpty(url) || ContainerTool.isEmpty(map)) {
			return this;
		}
		StringBuilder urlBuilder = new StringBuilder();
		if (url.contains("?")) {
			if (url.indexOf("?") < url.indexOf("=")) {
				urlBuilder.append("&");
			}
		} else {
			urlBuilder.append("?");
		}
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&"));
		return url(url + urlBuilder.toString());
	}

	/**
	 * 默认超时时间
	 */
	public void defTimeOut() {
		socketTimeout(HttpClientUtil.CONNECT_TIMEOUT);
		timeOut(HttpClientUtil.SOCKET_TIMEOUT);
	}

	/**
	 * shezhi 超时时间
	 */
	public void httpTimeOut(int timeout) {
		socketTimeout(timeout);
		timeOut(timeout);
	}

	/**
	 * 当前请求结束，移除数据
	 */
	void requestEnd(){
		URLS.remove();
		MAPS.remove();
	}

}
