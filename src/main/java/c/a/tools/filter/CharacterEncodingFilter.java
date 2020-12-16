package c.a.tools.filter;

import javax.servlet.FilterChain;

import c.a.config.core.CharsetConfigAy;
import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;

public class CharacterEncodingFilter implements FilterI {
	@Override
	public void doFilter(FilterContext context, FilterChain chain,
			FilterProxyChain proxy) throws RuntimeException, Exception {

		context.getHttpServletRequest()
				.setCharacterEncoding(CharsetConfigAy.utf8);
		context.getHttpServletResponse().setContentType(
				"text/html;charset=" + CharsetConfigAy.utf8);
		proxy.doFilter(context, chain);
	}
}
