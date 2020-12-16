package c.a.tools.filter.proxy;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.filter.CharacterEncodingFilter;
import c.a.tools.filter.LastFilter;
import c.a.tools.filter.UrlSecurityFilter;
import c.a.tools.filter.time_zone.TimeZoneFilter;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.tools.sql_security.SqlSecurityFilter;
import c.x.platform.root.logout.LogoutFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * 过滤代理类; 实现Filter接口,添加自定义过滤类;
 */
public class FilterProxy implements Filter {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFun = "FilterProxy功能,";
	private String logFunError = "FilterProxy功能出错,";
	private ServletContext servletContext;
	@Override public void init(FilterConfig filterConfig) throws ServletException {

		boolean isStart;
		try {
			isStart = Boolean
					.parseBoolean(SysConfig.findInstance().findMap().getOrDefault("comm.local.mvc", false).toString());
		} catch (Exception e) {
			isStart = false;
		}
		if (isStart) {
			servletContext = filterConfig.getServletContext();
		}
	}
	@Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (servletContext == null){
			return;
		}
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		if (true) {
			// log.trace("过滤器 Filter 过滤doFilter开始start");
		}
		/*
		 * 1;
		 */
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String servletPath = httpServletRequest.getServletPath();
		HttpSession httpSession = httpServletRequest.getSession(false);
		String contextPath = httpServletRequest.getContextPath();
		/*
		 * 2;
		 */
		FilterContext filterContext = new FilterContext();
		/*
		 * 3;
		 */
		filterContext.setServletContext(servletContext);
		filterContext.setHttpServletRequest(httpServletRequest);
		filterContext.setHttpServletResponse(httpServletResponse);
		filterContext.setServletPath(servletPath);
		filterContext.setHttpSession(httpSession);
		filterContext.setContextPath(contextPath);
		/*
		 * 4;
		 */
		FilterProxyChain filterProxyChain = new FilterProxyChain();
		/**
		 * 添加所有的过滤器或拦截器
		 */
		boolean isAddCustomFilter = false;
		// boolean isAddCustomFilter = true;
		filterProxyChain.add(new CharacterEncodingFilter());
		filterProxyChain.add(new TimeZoneFilter());
		filterProxyChain.add(new UrlSecurityFilter());
		/**
		 * 开发时注释验证码，以后项目上线时加上
		 */
		if (isAddCustomFilter) {
			// filterProxyChain.add(new SessionFilter());
			/**
			 * 防止sql注入
			 */
			filterProxyChain.add(new SqlSecurityFilter());
			// filterProxyChain.add(new CaptchaFilter());
			filterProxyChain.add(new LogoutFilter());
		}
		/**
		 * 最后的过滤器
		 */
		filterProxyChain.add(new LastFilter());
		if (true) {
			try {
				filterProxyChain.doFilter(filterContext, chain);
			} catch (BizRuntimeException e1) {
				String logStr = "BizRuntimeException，filter出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e1);
				// 打印到控制台
				// e1.printStackTrace();
				// 写日志
				this.log2database(logStr, servletContext, e1, servletPath);
				// 必须重新抛出异常给系统才能跳转
				throw e1;
			} catch (RuntimeException e2) {
				String logStr = "RuntimeException，filter出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, e2);
				// 打印到控制台
				// e2.printStackTrace();
				// 写日志
				this.log2database(logStr, servletContext, e2, servletPath);
				// 必须重新抛出异常给系统才能跳转
				throw e2;
			} catch (Throwable t) {
				// httpServletResponse.sendError(403);
				// httpServletResponse.setStatus(403);
				String logStr = "Throwable，filter出错,出错的url=" + servletPath;
				logStr = logFunError + logStr;
				log.error(logStr);
				log.error(logStr, t);
				// 打印到控制台
				// t.printStackTrace();
				// 写日志
				this.log2database(logStr, servletContext, t, servletPath);
			}
		}
		if (true) {
			// log.trace("过滤器 Filter 过滤doFilter结束end");
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	@Override public void destroy() {
		if (true) {
			// log.trace("过滤器 Filter销毁 destroy");
		}
	}
	/**
	 * 日志
	 *
	 * @param servletContext
	 * @param throwable
	 * @param servletPath
	 * @throws ServletException
	 * @throws IOException
	 */
	public void log2database(String logStr, ServletContext servletContext, Throwable throwable, String servletPath)
			throws ServletException, IOException {
		log.error(logStr, throwable);
	}
	/**
	 * Exception异常时，返回jsp页面
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void exceptionForwardJsp(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String returnPage_relative = "/pages/c/x/platform/root/common/error/500_filter.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(returnPage_relative);
		try {
			if (true) {
				// if (false) {
				requestDispatcher.forward(request, response);
			}
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
