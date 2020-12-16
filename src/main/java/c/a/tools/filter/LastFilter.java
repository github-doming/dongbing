package c.a.tools.filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;
/**
 * 
 * 最后的过滤器
 * 
 * 
 * 
 */
public class LastFilter implements FilterI {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void doFilter(FilterContext context, FilterChain chain, FilterProxyChain proxy)
			throws RuntimeException, Exception {
		if (false) {
			log.trace("过滤器=" + this.getClass().getName());
			HttpServletRequest httpServletRequest = context.getHttpServletRequest();
			String servletPath = httpServletRequest.getServletPath();
			String uri = httpServletRequest.getRequestURI();
			String url = httpServletRequest.getRequestURL().toString();
			System.out.println("调用servletPath=" + servletPath);
			System.out.println("调用uri=" + uri);
			System.out.println("调用url=" + url);
			if (url.contains("druid")) {
				System.out.println("2 调用servletPath=" + servletPath);
				System.out.println("2 调用uri=" + uri);
				System.out.println("2 调用url=" + url);
			}
		}
		proxy.doFilter(context, chain);
		return;
	}
}
