package c.a.tools.filter.time_zone;
import java.util.TimeZone;
import javax.servlet.FilterChain;
import c.a.tools.filter.proxy.FilterContext;
import c.a.tools.filter.proxy.FilterI;
import c.a.tools.filter.proxy.FilterProxyChain;
import c.a.tools.filter.time_zone.config.TimeZoneConfig;
public class TimeZoneFilter implements FilterI {
	@Override
	public void doFilter(FilterContext context, FilterChain chain,
			FilterProxyChain proxy) throws RuntimeException, Exception {
		if (true) {
			// log.trace("过滤器=" + this.getClass().getName());
		}
		if (false) {
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Yekaterinburg"));
		}
		TimeZone.setDefault(TimeZone
				.getTimeZone(TimeZoneConfig.TimeZone_Default));
		proxy.doFilter(context, chain);
	}
}
