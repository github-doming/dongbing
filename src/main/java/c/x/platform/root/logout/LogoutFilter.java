package c.x.platform.root.logout;
import javax.servlet.FilterChain;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.config.core.HttpSessionConfigIy;
import c.a.config.login.LoginUrlDy;
import c.a.config.logout.LogoutConfigEy;
import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;
public class LogoutFilter implements FilterI {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void doFilter(FilterContext context, FilterChain chain, FilterProxyChain proxy)
			throws RuntimeException, Exception {
		if (true) {
			log.trace("过滤器=" + this.getClass().getName());
		}
		/*
		 * 1;从logout退出,清空session
		 */
		if (context.getServletPath().equals(LogoutConfigEy.RequestDoLogout)) {
			// 清空CurrentUser
			context.getHttpSession().removeAttribute(HttpSessionConfigIy.CurrentAppUser);
			// 跳转
			if ("true".equals(SysConfig.findInstance().findMap().get(SysConfig.commLocalDebug))) {
				context.getHttpServletRequest().getRequestDispatcher(LoginUrlDy.RequestJspLoginDevelopModel)
						.forward(context.getHttpServletRequest(), context.getHttpServletResponse());
			} else {
				context.getHttpServletRequest().getRequestDispatcher(LoginUrlDy.RequestJspLogin)
						.forward(context.getHttpServletRequest(), context.getHttpServletResponse());
			}
			return;
		} else {
			proxy.doFilter(context, chain);
			return;
		}
	}
}
