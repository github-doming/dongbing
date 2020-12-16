package org.doming.core.common.servlet;
import org.doming.develop.http.HttpConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * MVC服务 基类
 * @Author: Dongming
 * @Date: 2020-05-08 18:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface MvcServlet {

	/**
	 * MVC执行
	 *
	 * @param request  请求对象
	 * @param response 回执对象
	 * @param method   请求方法
	 */
	void execute(HttpServletRequest request, HttpServletResponse response, HttpConfig.Method method);
}
