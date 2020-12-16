package c.a.util.core.request;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.date.DateUtil;
import c.a.util.core.security.UrlUtil;
import c.a.util.core.string.StringUtil;
public class RequestAyUtil extends UrlUtil{
	/**
	 * 设置相应的类型
	 * 
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	public void setContentType(String fileName, HttpServletResponse response) throws Exception {
		URL url = new URL("file://" + fileName);
		String contentType = url.openConnection().getContentType();
		response.setContentType(contentType);
	}
	/**
	 * 根据浏览器的类型得到文件名
	 * 
	 * @param fileName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String findFileName(String fileName, HttpServletRequest request) throws Exception {
		DateUtil dateUtile = DateThreadLocal.findThreadLocal().get();
		String dateStr = dateUtile.findNow2StringByFormat("yyyy-MM-dd_HH_mm_ss");
		if (StringUtil.isBlank(fileName)) {
				fileName = dateStr;
		} else {
			short s = findBrowserType(request);
			if (s == 1) {
				fileName = URLEncoder.encode(fileName, "utf-8");
			}
			if (s == 2) {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
			if (s == 0) {
				fileName = dateStr;
			}
		}
		return fileName;
	}
	/**
	 * 得到浏览器的类型
	 * 
	 * @param request
	 * @return 1为ie;2为firefox;0为其它
	 */
	public short findBrowserType(HttpServletRequest request) {
		String agent = request.getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE")) {
			return 1;
		} else {
			if (null != agent && -1 != agent.indexOf("Mozilla")) {
				return 2;
			} else {
				return 0;
			}
		}
	}
}
