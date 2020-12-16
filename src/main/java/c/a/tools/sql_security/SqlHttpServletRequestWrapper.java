package c.a.tools.sql_security;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import c.a.util.core.string.StringUtil;
public class SqlHttpServletRequestWrapper extends HttpServletRequestWrapper {
	/**
	 * 覆盖getParameter方法，将参数名和参数值都做Request过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
	 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value != null) {
			value = requestEncode(value);
		}
		return value;
	}
	/**
	 * 覆盖getHeader方法，将参数名和参数值都做req过滤。<br/>
	 * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
	 * getHeaderNames 也可能需要覆盖
	 */
	// @Override
	// public String getHeader(String name) {
	// String value = super.getHeader(requestEncode(name));
	// if (value != null) {
	// value = requestEncode(value);
	// }
	// return value;
	// }
	/**
	 * 
	 * 编码; 将容易引起req漏洞的半角字符直接替换成全角字符;
	 * 
	 * @param str
	 * @return
	 */
	public static String requestEncode(String str) {
		return StringUtil.requestEncode(str);
	}
	/**
	 * 
	 * 解码; 将容易引起req漏洞的半角字符直接替换成全角字符;
	 * 
	 * 然后再转回来;
	 * 
	 * @param str
	 * @return
	 */
	public static String requestDecode(String str) {
		return StringUtil.requestDecode(str);
	}
	// 以前的方法
	// {
	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequestOrigin() {
		return requestOrigin;
	}
	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequestOrigin(
			HttpServletRequest httpServletRequest) {
		if (httpServletRequest instanceof SqlHttpServletRequestWrapper) {
			// return ((SqlHttpServletRequestWrapper)
			// httpServletRequest).getRequestOrigin();
			SqlHttpServletRequestWrapper sqlHttpServletRequestWrapper = (SqlHttpServletRequestWrapper) httpServletRequest;
			sqlHttpServletRequestWrapper.getRequestOrigin();
		}
		return httpServletRequest;
	}
	HttpServletRequest requestOrigin = null;
	public SqlHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		requestOrigin = request;
	}
	// }
	// 以前的方法
}