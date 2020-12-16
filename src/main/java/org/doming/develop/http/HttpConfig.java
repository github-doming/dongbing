package org.doming.develop.http;

import org.doming.core.tools.ContainerTool;
/**
 * @Description: Http请求配置类
 * @Date 2018年06月13日 15:59
 * @Date 2018年12月11日18:03:54
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 * @Copyright © 2018-2018 本源代码受软件著作法保护，请在授权允许范围内使用。
 **/
public interface HttpConfig {

	enum Method {
		/**
		 * http请求类型
		 */
		GET, POST, HEAD, PUT, OPTIONS, DELETE, PATCH, TRACE;

		/**
		 * 请求类型是否匹配
		 * 定义类型为空或包含 为true
		 *
		 * @param methods 可以匹配的类型
		 * @return 匹配 true
		 */
		public boolean matchMethod(Method[] methods) {
			if (ContainerTool.isEmpty(methods)) {
				return true;
			}
			for (Method type : methods) {
				if (type.equals(this)) {
					return true;
				}
			}
			return false;
		}
	}
	enum Code {
		/**
		 * http请求类型
		 */
		CORE("core"), QUERY("query"), BASE("base");

		String code;
		Code(String code) {
			this.code = code;
		}
		public String code() {
			return code;
		}
	}

	/**
	 * 客户端理解的内容编码方式
	 */
	String ACCEPT_ENCODING = "gzip, deflate, br";

	/**
	 * 客户端理解的自然语言
	 */
	String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.9";

	String ACCEPT = "*/*";

	/**
	 * 客户端理解的解码格式
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	String CONNECTION = "Keep-Alive";

	/**
	 * 一个特征字符串
	 * 让网络协议的对端来识别发起请求的用户代理软件的应用类型、操作系统、软件开发商以及版本号。
	 */
	String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36";

	Integer TIME_OUT = 10 * 1000;
}
