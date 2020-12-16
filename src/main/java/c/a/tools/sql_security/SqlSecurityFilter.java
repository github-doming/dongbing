package c.a.tools.sql_security;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;
/**
 * 
 * 使用Servlet Filter来防止Xss漏洞和SQL注入的方法;
 * 
 * 但是不支持JSON的转换，得解码才行;
 * 
 * JSON的转换不支持全角;
 * 
 * @Description:
 * @ClassName: SqlSecurityFilter
 * @date 2017年2月7日 上午10:39:43
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class SqlSecurityFilter implements FilterI {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void doFilter(FilterContext context, FilterChain chain,
			FilterProxyChain proxy) throws RuntimeException, Exception {
		log.trace("防止Xss漏洞和SQL注入");
		SqlHttpServletRequestWrapper sqlHttpServletRequestWrapper = new SqlHttpServletRequestWrapper(
				(HttpServletRequest) context.getHttpServletRequest());
		context.setHttpServletRequest(sqlHttpServletRequestWrapper);
		proxy.doFilter(context, chain);
		return;
	}

}