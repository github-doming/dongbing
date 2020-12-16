package c.a.tools.filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.config.UrlSecurityConfig;
import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;
/**
 * url安全的过滤器
 * 
 * @Description:
 * @date 2012年8月16日 下午8:22:06
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class UrlSecurityFilter implements FilterI {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void doFilter(FilterContext context, FilterChain chain, FilterProxyChain proxy)
			throws RuntimeException, Exception {
		if ("true".equals(SysConfig.findInstance().findMap().get(SysConfig.commLocalDebug))) {
			proxy.doFilter(context, chain);
			return;
		} else {
			HttpServletRequest httpServletRequest = context.getHttpServletRequest();
			String servletPath = httpServletRequest.getServletPath();
			Object value = UrlSecurityConfig.findInstance().findMap().get(servletPath);
			if (value != null) {
				httpServletRequest.getRequestDispatcher(SysConfig.RequestJspLogin)
						.forward(httpServletRequest, context.getHttpServletResponse());
			} else {
				proxy.doFilter(context, chain);
				return;
			}
			return;
		}
	}
}
