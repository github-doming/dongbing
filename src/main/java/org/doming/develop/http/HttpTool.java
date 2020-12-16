package org.doming.develop.http;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.configs.CharsetConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 * @Description: http工具类
 * 采用jdk中间的接口进行发送
 * @Author: Dongming
 * @Date: 2018-12-11 15:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class HttpTool {

	static Logger log = LogManager.getLogger(HttpTool.class);

	/**
	 * 发送get请求，无需返回值
	 *
	 * @param url   请求地址
	 * @param param 请求参数
	 * @throws IOException 请求错误
	 */
	public static void sendGet(String url, String param) throws IOException {
		sendGet(url + "?" + param);
	}

	/**
	 * 发送get请求，无需返回值
	 *
	 * @param url 请求地址
	 * @throws IOException 请求错误
	 */
	public static void sendGet(String url) throws IOException {
		try {
			URLConnection connection = getUrlConnection(url);
			connection.connect();
		} catch (MalformedURLException e) {
			log.error("URL转换错误", e);
			throw e;
		} catch (IOException e) {
			log.error("开启http链接错误", e);
			throw e;
		}
	}

	/**
	 * 发送get请求，需要获取到返回参数
	 *
	 * @param url 请求地址
	 * @return 请求结果
	 * @throws IOException 请求错误
	 */
	public static String doGet(String url) throws IOException {
		String result;
		BufferedReader in = null;
		try {
			URLConnection connection = getUrlConnection(url);
			connection.connect();
			//获得网络返回的输入流，并封装为输入流
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			result = new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		} catch (MalformedURLException e) {
			log.error("URL转换错误", e);
			throw e;
		} catch (IOException e) {
			log.error("开启http链接错误", e);
			throw e;
		} finally {
			close(null, in);
		}
		return result;
	}

	/**
	 * 发送get请求，需要获取到返回参数
	 *
	 * @param url   请求地址
	 * @param param 请求参数
	 * @return 请求结果
	 * @throws IOException 请求错误
	 */
	public static String doGet(String url, String param) throws IOException {
		return doGet(url + "?" + param);
	}

	/**
	 * 发送post请求，无需获取到返回参数
	 *
	 * @param url   请求地址
	 * @param param 请求参数
	 * @throws IOException 请求错误
	 */
	public static void sendPost(String url, String param) throws IOException {
		PrintWriter out = null;
		try {
			URLConnection connection = getUrlConnection(url);
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//获得输出流，并将其封装为字符流
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(),StandardCharsets.UTF_8));
			//按字节的方式打印输出字符，并写入数组的某一部分
			out.print(new String(param.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
			//刷新输出流的缓冲
			out.flush();
			connection.connect();
		} catch (MalformedURLException e) {
			log.error("URL转换错误", e);
			throw e;
		} catch (IOException e) {
			log.error("开启http链接错误", e);
			throw e;
		} finally {
			close(out, null);
		}
	}

	/**
	 * 发送post请求，需要获取到返回参数
	 *
	 * @param url   请求地址
	 * @param param 请求参数
	 * @return 请求结果
	 * @throws IOException 请求错误
	 */
	public static String doPost(String url, Object param) throws IOException {
		String result;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URLConnection connection = getUrlConnection(url);
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//获得输出流，并将其封装为字符流
			out = new PrintWriter(new OutputStreamWriter(
					connection.getOutputStream(), StandardCharsets.UTF_8));
			//按字节的方式打印输出字符，并写入数组的某一部分
			out.print(param);
			//刷新输出流的缓冲
			out.flush();
			connection.connect();
			//获得网络返回的输入流，并封装为输入流
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = in.readLine()) != null) {
				sb.append("\n").append(line);
			}
			result = new String(sb.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

		} catch (MalformedURLException e) {
			log.error("URL转换错误", e);
			throw e;
		} catch (IOException e) {
			log.error("开启http链接错误", e);
			throw e;
		} finally {
			close(out, in);
		}
		return result;
	}

	/**
	 * 返回json参数
	 *
	 * @param data json对象
	 * @return json=json字符串
	 * @throws UnsupportedEncodingException 编码错误
	 */
	public static String paramJson(String data) throws UnsupportedEncodingException {
		return "json=" + URLEncoder.encode(data, CharsetConfig.UTF8);
	}

	/**
	 * 返回json参数
	 *
	 * @param data json对象
	 * @return json=json字符串
	 * @throws UnsupportedEncodingException 编码错误
	 */
	public static String paramJson(net.sf.json.JSONObject data) throws UnsupportedEncodingException {
		return paramJson(data.toString());
	}
	/**
	 * 返回json参数
	 *
	 * @param data json对象
	 * @return json=json字符串
	 * @throws UnsupportedEncodingException 编码错误
	 */
	public static String paramJson(com.alibaba.fastjson.JSONObject data) throws UnsupportedEncodingException {
		return paramJson(data.toString());
	}

	/**
	 * 获取url链接
	 *
	 * @param url 请求地址
	 * @return url链接
	 * @throws IOException 获取链接错误
	 * @see URL#openConnection()
	 */
	private static URLConnection getUrlConnection(String url) throws IOException {
		URLConnection connection =  new URL(url).openConnection();
		connection.setRequestProperty("accept", HttpConfig.ACCEPT);
		connection.setRequestProperty("Content-Type", HttpConfig.CONTENT_TYPE);
		connection.setRequestProperty("connection", HttpConfig.CONNECTION);
		connection.setRequestProperty("user-agent", HttpConfig.USER_AGENT);
		connection.setConnectTimeout(HttpConfig.TIME_OUT);
		String redirect = connection.getHeaderField("Location");
		if (redirect != null){
			connection = new URL(redirect).openConnection();
		}
		return connection;
	}

	/**
	 * 关闭IO流
	 *
	 * @param out 打印输出流
	 * @param in  带buff的读取流
	 * @throws IOException 关闭读取流错误
	 */
	private static void close(PrintWriter out, BufferedReader in) throws IOException {
		if (out != null) {
			out.close();
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				log.error("关闭读取流错误", e);
				throw e;
			}
		}
	}
}
