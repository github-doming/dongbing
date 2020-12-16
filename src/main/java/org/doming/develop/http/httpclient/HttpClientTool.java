package org.doming.develop.http.httpclient;
import org.doming.develop.http.HttpConfig;

import java.util.Map;
/**
 * @Description: HttpClient工具类
 * @Author: Dongming
 * @Date: 2019-01-19 13:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpClientTool {

	/**
	 * get请求地址
	 *
	 * @param url url 地址
	 * @return 请求结果
	 */
	public static String doGet(String url) {
		return html(getHc().url(url).method(HttpConfig.Method.GET));
	}
	/**
	 * get请求地址
	 *
	 * @param url url 地址
	 * @return 请求结果
	 */
	public static String doGet(String url,int timeOut) {
		return html(getHc().url(url).timeOut(timeOut).socketTimeout(timeOut).method(HttpConfig.Method.GET));
	}

	/**
	 * get请求地址
	 *
	 * @param url   url 地址
	 * @param param 请求参数
	 * @return 请求结果
	 */
	public static String doGet(String url, String param) {
		return html(getHc().url(url + "?" + param).method(HttpConfig.Method.GET));
	}

	/**
	 * get请求地址
	 *
	 * @param url   url 地址
	 * @param param 请求参数
	 * @return 请求结果
	 */
	public static String doGetProxy(String url, String param, String ip, int port) {
		return html(getHc().url(url + "?" + param).proxy(ip, port).method(HttpConfig.Method.GET));
	}

	/**
	 * post请求地址
	 *
	 * @param url   url 地址
	 * @param param 请求参数
	 * @return 请求结果
	 */
	public static String doPost(String url, Map<String, Object> param) {
		return html(getHc().url(url).map(param).method(HttpConfig.Method.POST));
	}
	/**
	 * post请求地址
	 *
	 * @param url     url 地址
	 * @param param   请求参数
	 * @param timeOut 超时时间
	 * @return 请求结果
	 */
	public static String doPost(String url, Map<String, Object> param, int timeOut) {
		return html(getHc().url(url).map(param).timeOut(timeOut).socketTimeout(timeOut)
				.method(HttpConfig.Method.POST));
	}

	/**
	 * 获取请求页面
	 *
	 * @param config 请求参数配置
	 * @return 请求页面
	 */
	private static String html(HttpClientConfig config) {
		HttpClientUtil util = HttpClientUtil.findInstance();
		return util.sendHtml(config.httpClient(util.createHttpClient()));
	}

	/**
	 * 获取http请求配置类
	 * @return http请求配置类
	 */
	private static HttpClientConfig getHc(){
		return new HttpClientConfig().redirectsEnabled(true);
	}


}
