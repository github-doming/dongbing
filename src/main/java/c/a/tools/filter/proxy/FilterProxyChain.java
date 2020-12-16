package c.a.tools.filter.proxy;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 过滤代理类; 过滤链;
 * 
 * 
 */
public class FilterProxyChain {
	private List<FilterI> filters = new ArrayList<FilterI>();
	private int index = 0;
	public void add(FilterI filter) {
		this.filters.add(filter);
	}
	public List<FilterI> getFilters() {
		return filters;
	}
	public void setFilters(List<FilterI> filters) {
		this.filters = filters;
	}
	public void doFilter(FilterContext context, FilterChain chain)
			throws RuntimeException, Exception {
		if (index == filters.size()) {
			chain.doFilter(context.getHttpServletRequest(),
					context.getHttpServletResponse());
			return;
		}
		FilterI filter = filters.get(index);
		index++;
		filter.doFilter(context, chain, this);
	}
}
