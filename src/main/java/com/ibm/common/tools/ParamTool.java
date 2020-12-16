package com.ibm.common.tools;

import org.doming.core.configs.CharsetConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * @Description: 参数工具类
 * @Author: zjj
 * @Date: 2019-08-28 16:15
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class ParamTool {

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
}
