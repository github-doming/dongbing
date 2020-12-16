package c.a.tools.filter.proxy;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 过滤代理类; 过滤器扩展接口,自定义;
 */
public interface FilterI {
	public void doFilter(FilterContext context, FilterChain chain,
			FilterProxyChain proxy) throws Exception;
}
