package org.doming.core.common.servlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: http服务工具类
 * @Author: Dongming
 * @Date: 2019-08-21 16:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpServletTool {

	private static final Logger log = LogManager.getLogger(HttpServletTool.class);

	/**
	 * 获取请求中的cookie
	 *
	 * @param request 请求对象
	 * @return cookie集合
	 */
	public static Map<String, Cookie> findCookieInfo(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Map<String, Cookie> cookieMap = null;
		if (null != cookies) {
			cookieMap = new HashMap(cookies.length);
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
	/**
	 * 获取请求中的cookie
	 *
	 * @param cookieMap cookie集合
	 * @param key       cookie键
	 * @return cookie值
	 */
	public static String findCookieVal(Map<String, Cookie> cookieMap, String key) {
		if (cookieMap.containsKey(key)) {
			return cookieMap.get(key).getValue();
		}
		return null;
	}

	/**
	 * 重定向页面
	 *
	 * @param response 响应对象
	 * @param url      重定向页面
	 * @param map      重定向参数
	 */
	protected static void forward(HttpServletResponse response, HttpServletRequest request, String url,
			Map<String, Object> map) throws ServletException, IOException {
		// 判断是否已经提交
		if (!response.isCommitted()) {
			//配置回传信息
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			if (ContainerTool.notEmpty(map)) {
				//回传参数
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
				}
			}
			//重定向
			requestDispatcher.forward(request, response);
		}

	}

	/**
	 * 回显文本信息
	 *
	 * @param response 响应对象
	 * @param result   执行结果
	 */
	public static void write(HttpServletResponse response, String result) throws IOException {
		write(response, result, false);
	}

	/**
	 * 回显文本信息
	 *
	 * @param response    响应对象
	 * @param result      执行结果
	 * @param otherOrigin 跨域
	 */
	protected static void write(HttpServletResponse response, String result, boolean otherOrigin) throws IOException {
		// 判断是否已经提交
		if (!response.isCommitted()) {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			if (otherOrigin) {
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
				response.setHeader("Access-Control-Allow-Credentials", "true");
			}
			response.getWriter().write(result);
		}
	}


}
