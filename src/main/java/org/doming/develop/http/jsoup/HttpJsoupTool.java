package org.doming.develop.http.jsoup;

import org.doming.develop.http.HttpConfig;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @Description: 数据抓取工具
 * @Date 2018年12月11日17:59:30
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 **/
public class HttpJsoupTool {

	/**
	 * 执行get方法获取链接JSON信息
	 *
	 * @param timeout 超时
	 * @param url     链接地址
	 * @param param   请求参数
	 * @return JSON信息
	 * @throws IOException 请求io异常
	 */
	public static String doGetJson(int timeout, String url, String param) throws IOException {
		return doGetJson(timeout, url + "?" + param);
	}

	/**
	 * 执行get方法获取链接JSON信息
	 *
	 * @param url   链接地址
	 * @param param 请求参数
	 * @return 请求的JSON结果
	 * @throws IOException 请求io异常
	 */
	public static String doGetJson(String url, String param) throws IOException {
		return doGetJson(url + "?" + param);
	}

	/**
	 * 执行get方法获取链接JSON信息
	 *
	 * @param url 链接地址
	 * @return 请求的JSON结果
	 * @throws IOException 请求io异常
	 */
	public static String doGetJson(String url) throws IOException {
		return doGetJson(HttpConfig.TIME_OUT, url);
	}
	/**
	 * 执行get方法获取链接JSON信息
	 *
	 * @param url 链接地址
	 * @return 请求的JSON结果
	 * @throws IOException 请求io异常
	 */
	public static String doGetJson(int timeout, String url) throws IOException {
		return getConnection(timeout, url).execute().body();
	}

	/**
	 * 执行get方法获取链接信息
	 *
	 * @param url 链接地址
	 * @return 打开页面文档
	 * @throws IOException 请求io异常
	 */
	public static Document doGet(String url) throws IOException {
		return getConnection(HttpConfig.TIME_OUT, url).get();
	}

	/**
	 * 执行post方法获取链接信息
	 *
	 * @param url 链接地址
	 * @param map 请求参数
	 * @return 获取页面内容
	 * @throws IOException 请求io异常
	 */
	public static String doPostJson(String url, Map<String, Object> map) throws IOException {
		return doPost(url, map).text();
	}
	/**
	 * 执行post方法获取链接信息
	 *
	 * @param url 链接地址
	 * @param map 请求参数
	 * @return 打开页面文档
	 * @throws IOException 请求io异常
	 */
	public static Document doPost(String url, Map<String, Object> map) throws IOException {
		Connection connection = getConnection(HttpConfig.TIME_OUT, url);

		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				connection.data(entry.getKey(), entry.getValue().toString());
			}
		}
		return connection.post();
	}

	/**
	 * 根据链接地址获取jsoup的连接类
	 *
	 * @param timeout 超时
	 * @param url     链接地址
	 * @return jsoup的连接类
	 */
	public static Connection getConnection(int timeout, String url) {
		return Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", HttpConfig.ACCEPT_ENCODING)
				.header("Accept-Language", HttpConfig.ACCEPT_LANGUAGE).header("Content-Type", HttpConfig.CONTENT_TYPE)
				.header("User-Agent", HttpConfig.USER_AGENT)
				// 请求超时
				.timeout(timeout)
				// 忽略内容错误
				.ignoreHttpErrors(true)
				// 忽略内容类型
				.ignoreContentType(true);
	}

}
