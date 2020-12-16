package c.x.all.simple.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import c.a.tools.log.custom.common.BaseLog;
public class ServletRequestListener_ay implements ServletRequestListener {
	public void v1() {
		if (false) {
			// 监听器=>Filter=>HttpServlet=>Filter=>监听器
		}
		if (false) {
			// [业务日志]监听器ServletRequestListener初始化
			// requestInitialized;servletPath=/admin/index.do[系统]class=co.do.listener.ServletRequestListener_ay
		}
		if (false) {
			// [业务日志]过滤器 Filter
			// 过滤doFilter开始start[系统]class=c.a.tools.filter.proxy.FilterProxy
		}
		if (false) {
			// [业务日志]mvc框架=c.a.tools.mvc_2.servlet.MvcHttpServlet[系统]class=c.a.tools.mvc_2.servlet.MvcHttpServlet
		}
		if (false) {
			// [业务日志]过滤器 Filter
			// 过滤doFilter结束end[系统]class=c.a.tools.filter.proxy.FilterProxy
		}
		if (false) {
			// [业务日志]监听器ServletRequestListener销毁
			// requestDestroyed;servletPath=/admin/index.do[系统]class=co.do.listener.ServletRequestListener_ay
		}
		if (false) {
			// 监听器=>Filter=>HttpServlet=>Filter=>监听器
		}
	}
	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) sre
				.getServletRequest();
		String servletPath = httpServletRequest.getServletPath();
		if (false) {
		}
		if (true) {
			BaseLog.out("监听器ServletRequestListener销毁 requestDestroyed;servletPath="
					+ servletPath);
			BaseLog.out(BaseLog.lineDividing());
			BaseLog.out("当前线程=" + Thread.currentThread().getName());
			BaseLog.out("监听结束;  servletPath=" + servletPath);
		}
	}
	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		BaseLog.trace(BaseLog.lineDividing());
		// 执行顺序;1监听器,2过滤器,3servlet
		HttpServletRequest httpServletRequest = (HttpServletRequest) sre
				.getServletRequest();
		String servletPath = httpServletRequest.getServletPath();
		if (false) {
		}
		if (true) {
			BaseLog.out("监听器ServletRequestListener初始化 requestInitialized");
			BaseLog.out("getRequestURI=" + httpServletRequest.getRequestURI());
			BaseLog.out("url=" + httpServletRequest.getRequestURL());
			BaseLog.out("当前线程=" + Thread.currentThread().getName());
			BaseLog.out("监听开始;servletPath=" + servletPath);
		}
	}
}
