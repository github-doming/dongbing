package com.ibm.old.v1.common.zjj.test.http;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
/**
 * @Description: java原生HttpURLConnection
 * @Author: zjj
 * @Date: 2019-05-31 09:50
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HttpTest {

	public static String doGet(String httpurl) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		// 返回结果字符串
		String result = null;
		try {
			// 创建远程url连接对象
			URL url = new URL(httpurl);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod(HttpConstant.Method.GET);
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15*1000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(60*1000);
			// 发送请求
			connection.connect();
			// 通过connection连接，获取输入流
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// 封装输入流is，并指定字符集
				br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
				// 存放数据
				StringBuilder sbf = new StringBuilder();
				String temp;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 关闭远程连接
			if(connection!=null){
				connection.disconnect();
			}
		}
		return result;
	}


	public static String doPost(String httpUrl,String param){

		HttpURLConnection connection=null;
		InputStream inputStream=null;
		OutputStream outputStream=null;
		BufferedReader bufferedReader=null;
		String result=null;

		try {
			URL url=new URL(httpUrl);
			// 通过远程url连接对象打开连接
			connection= (HttpURLConnection) url.openConnection();
			// 设置连接请求方式
			connection.setRequestMethod(HttpConstant.Method.POST);
			// 设置连接主机服务器超时时间：15000毫秒
			connection.setConnectTimeout(15*1000);
			// 设置读取主机服务器返回数据超时时间：60000毫秒
			connection.setReadTimeout(60*1000);
			// 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
			connection.setDoOutput(true);
			// 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
			connection.setDoInput(true);
			// 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
			connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
			// 通过连接对象获取一个输出流
			outputStream=connection.getOutputStream();
			// 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
			outputStream.write(param.getBytes());
			// 通过连接对象获取一个输入流，向远程读取
			if(connection.getResponseCode()==200){
				inputStream=connection.getInputStream();
				// 对输入流对象进行包装:charset根据工作项目组的要求来设置
				bufferedReader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

				StringBuilder stringBuilder = new StringBuilder();
				String temp = null;
				// 循环遍历一行一行读取数据
				while ((temp = bufferedReader.readLine()) != null) {
					stringBuilder.append(temp);
					stringBuilder.append("\r\n");
				}
				result = stringBuilder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bufferedReader!=null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return result;
	}




}
