package org.doming.core.common.servlet;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.develop.http.HttpConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * MVC服务类基类
 * @Author: Dongming
 * @Date: 2020-05-08 18:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class MvcServletBase extends HttpServlet {
	protected Logger log = LogManager.getLogger(this.getClass());

	protected MvcServlet servlet;
	protected boolean isDefMvc = true;
	public MvcServletBase() {
		initServlet();
	}
	@Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.GET);
		} else {
			super.doGet(req, resp);
		}
	}
	@Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.POST);
		} else {
			super.doPost(req, resp);
		}
	}

	@Override protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.HEAD);
		} else {
			super.doHead(req, resp);
		}
	}
	@Override protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.PUT);
		} else {
			super.doPut(req, resp);
		}
	}
	@Override protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.DELETE);
		} else {
			super.doDelete(req, resp);
		}
	}
	@Override protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.OPTIONS);
		} else {
			super.doOptions(req, resp);
		}
	}

	@Override protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (servlet != null) {
			servletExecute(req, resp, HttpConfig.Method.TRACE);
		} else {
			super.doTrace(req, resp);
		}
	}

	/**
	 * 初始化MVC服务
	 */
	private void initServlet() {
		if (isDefMvc) {
			servlet = new MvcServletImpl();
		}
	}

	/**
	 * MVC服务执行
	 *
	 * @param req    请求对象
	 * @param resp   回执对象
	 * @param method 请求方法
	 */
	private void servletExecute(HttpServletRequest req, HttpServletResponse resp, HttpConfig.Method method) {
		if (servlet instanceof MvcServletImpl && !isDefMvc) {
			log.error("你已设置调用默认MVC为'false'，如需调用默认MVC，请将'isDefMvc'设置为'true'，或者在MVC服务类中自我实现'MvcServlet'接口");
		} else if (!(servlet instanceof MvcServletImpl) && isDefMvc) {
			log.error("你已设置调用默认MVC为'true'，如需想要实现自己的MVC，请将'isDefMvc'设置为'false'，或者使用默认的MVC");
		}
		servlet.execute(req, resp, method);
	}

	/**
	 * 执行MVC 详情方法
	 *
	 * @param request  http请求对象
	 * @param response http返回对象
	 * @param url      MVC请求路径
	 * @throws Exception MVC执行错误
	 */
	protected abstract void executeMvc(HttpServletRequest request, HttpServletResponse response,
			 String url) throws Exception;

	class MvcServletImpl implements MvcServlet {
		@Override public void execute(HttpServletRequest request, HttpServletResponse response,
				HttpConfig.Method method) {
			long starTime = System.currentTimeMillis();
			final String url = request.getRequestURI().replaceFirst(request.getContextPath(), "");
			log.trace("MVC 请求开始， 请求url={}，请求类型={}", url, method.name());

			WebServletContent content = WebServletContent.getInstance();
			Class<?> clazz = content.getClazz(url);
			String errorMsg = "MVC Exception，%s，出错的url=".concat(url);
			if (clazz == null) {
				doErrorMsg(response, String.format(errorMsg, "匹配url出错"));
				return;
			}
			errorMsg = errorMsg.concat("，出错的action=").concat(clazz.getName());
			if (!method.matchMethod(content.getMethod(url))) {
				doErrorMsg(response, String.format(errorMsg, "请求类型匹配出错《" + method.name() + "》"));
				return;
			}
			try {
				//开始执行 mvc
				executeMvc(request, response, url);
			} catch (InstantiationException e) {
				doErrorMsg(response, String.format(errorMsg, "实例化action出错"));
			} catch (IllegalAccessException e) {
				doErrorMsg(response, String.format(errorMsg, "不合法访问异常"));
			} catch (Throwable t) {
				doErrorMsg(response, String.format(errorMsg, "业务出错"));
			} finally {
				long endTime = System.currentTimeMillis();
				log.trace("MVC 请求结束， 消耗时间={}ms", (endTime - starTime));
			}

		}

		/**
		 * 服务器Exception异常
		 *
		 * @param response 返回对象
		 * @param msg      异常信息
		 */
		private void doErrorMsg(HttpServletResponse response, String msg) {
			//打印错误信息
			log.error(msg);
			// 判断是否已经提交，如果提交不重复则提交
			if (!response.isCommitted()) {
				JSONObject json = new JSONObject();
				json.put("codeSys", "500");
				json.put("msgSys", "服务器出错，请稍候再试");
				json.put("code", "500");
				json.put("msg", msg);
				json.put("success", false);
				try {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					HttpServletTool.write(response, msg);
				} catch (IOException e) {
					log.error("发送异常信息错误：".concat(json.toString()), e);
				}
			}
		}
	}
}
