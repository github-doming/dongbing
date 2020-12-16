package com.ibm.old.v1.common.doming.core;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonThreadLocal;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * @Description: 主要执行方法
 * @Author: Dongming
 * @Date: 2018-12-03 15:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CommMethod {
	/**
	 * 返回json字符串
	 *
	 * @param bean json实体类对象
	 * @return json字符串
	 */
	default String returnJson(JsonResultBean bean) {
		return JsonThreadLocal.bean2json(bean);
	}

	/**
	 * 返回json参数
	 *
	 * @param bean json实体类对象
	 * @return json=json字符串
	 */
	default String paramJson(JsonResultBean bean) {
		return "json=" + returnJson(bean);
	}

	/**
	 * 返回json参数
	 *
	 * @param jObj json对象
	 * @return json=json字符串
	 * @throws UnsupportedEncodingException 编码错误
	 */
	default String paramJson(JSONObject jObj) throws UnsupportedEncodingException {
		return "json=" + URLEncoder.encode(jObj.toString(), "UTF-8");
	}
}
