package com.ibm.old.v1.common.doming.core.servlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.servlet.MvcExecutor;
import org.doming.core.common.servlet.WebServletContent;
import org.doming.core.tools.ContainerTool;
import org.doming.develop.http.HttpConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @Description: 异步MVC
 * @Author: Dongming
 * @Date: 2019-05-16 17:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
//@WebServlet(name = "AsyncDmMvcServlet",urlPatterns = "*.dm", asyncSupported = true)
public class AsyncDmMvcServlet extends HttpServlet {
	protected Logger log = LogManager.getLogger(this.getClass());

	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.GET);
	}
	@Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.POST);
	}

	@Override protected void doHead(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.HEAD);
	}
	@Override protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.PUT);
	}
	@Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.DELETE);
	}
	@Override protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.OPTIONS);
	}
	@Override protected void doTrace(HttpServletRequest req, HttpServletResponse resp) {
		execute(req, resp, HttpConfig.Method.TRACE);
	}

	/**
	 * 异步MVC执行
	 *
	 * @param request  请求对象
	 * @param response 回执对象
	 * @param method   请求方法
	 */
	private void execute(HttpServletRequest request, HttpServletResponse response, HttpConfig.Method method) {
		WebServletContent content = WebServletContent.getInstance();
		long starTime = System.currentTimeMillis();
		final String uri = request.getServletPath();
		log.debug("MVC 请求开始， 请求uri=".concat(uri).concat("，请求类型=").concat(method.name()));

		Class<?> clazz = content.getClazz(uri);
		if (clazz == null) {
			log.error("MVC Exception，找action出错，出错的uri=".concat(uri));
			this.doExceptionPage(request, response);
			return;
		}
		if (!matchMethod(content.getMethod(uri), method)) {
			log.error("MVC Exception，请求方法出错，出错的uri=".concat(uri).concat("，出错的action=").concat(clazz.getName()));
			this.doExceptionPage(request, response);
			return;
		}
		try {
			Object object = clazz.newInstance();
			if (object instanceof MvcExecutor) {
				//找到mvc执行器
				MvcExecutor executor = (MvcExecutor) object;
				//开始执行
				executor.execute(request, response);
			} else {
				log.error("MVC Exception，定义类型出错，出错的uri=".concat(uri).concat("，出错的action=").concat(clazz.getName())
						.concat("没有继承《MvcExecutor》接口"));
				this.doExceptionPage(request, response);
			}
		} catch (InstantiationException e) {
			log.error("MVC Exception，实例化action出错，出错的uri=".concat(uri).concat("，出错的action=").concat(clazz.getName()), e);
			this.doExceptionPage(request, response);
		} catch (IllegalAccessException e) {
			log.error("MVC Exception，不合法访问异常，出错的uri=".concat(uri).concat("，出错的action=").concat(clazz.getName()), e);
			this.doExceptionPage(request, response);
		} catch (Throwable t) {
			log.error("MVC Exception，业务出错，出错的uri=".concat(uri).concat("，出错的action=").concat(clazz.getName()), t);
			this.doExceptionPage(request, response);
		}finally {
			long endTime = System.currentTimeMillis();
			log.debug("MVC 请求结束， 消耗时间=" + (endTime - starTime) + "ms");
		}

	}

	/**
	 * 请求类型是否匹配
	 * 定义类型为空或包含 为true
	 *
	 * @param methods 可以匹配的类型
	 * @param type    请求类型
	 * @return 匹配 true
	 */
	private boolean matchMethod(HttpConfig.Method[] methods, HttpConfig.Method type) {
		if (ContainerTool.isEmpty(methods)) {
			return true;
		}
		for (HttpConfig.Method method : methods) {
			if (type.equals(method)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 服务器Exception异常时，返回jsp页面
	 *
	 * @param request  请求对象
	 * @param response 返回对象
	 */
	public void doExceptionPage(HttpServletRequest request, HttpServletResponse response) {
		// 判断是否已经提交，如果提交不重复则提交
		if (!response.isCommitted()) {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/pages/c/x/platform/root/common/error/500_mvc.jsp");
			try {
				requestDispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				log.error("页面重定向错误", e);
			}
		}
	}

}
