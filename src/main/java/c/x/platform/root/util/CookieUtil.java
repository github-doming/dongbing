package c.x.platform.root.util;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
public class CookieUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	// 设置为600min
	private int maxAge=600*60;
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public String findCookieValueCurrentType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = this.findCookieCurrentType(request, response);
		if (cookie != null) {
			String value = cookie.getValue();
			return value;
		}
		return null;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public Cookie findCookieCurrentType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = null;
		Map<String, Cookie> cookieMap = findCookieMap(request);
		if (cookieMap.containsKey(SysConfig.CurrentType)) {
			Object cookieObject = cookieMap.get(SysConfig.CurrentType);
			if (cookieObject == null) {
			} else {
				cookie = (Cookie) cookieObject;
				return cookie;
			}
		}
		return cookie;
	}
	public Cookie saveCookieCurrentType(HttpServletResponse response, String value) throws Exception {
		String name = SysConfig.CurrentType;
		Cookie cookie = new Cookie(name.trim(), value.trim());
		// 设置为30min
		cookie.setMaxAge(maxAge);
		// cookie.setMaxAge(10);
		cookie.setPath("/");
		cookie.setComment("sys_commnet");
		response.addCookie(cookie);
		return cookie;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public String findCookieValueCurrentTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = this.findCookieCurrentTenant(request, response);
		if (cookie != null) {
			String value = cookie.getValue();
			return value;
		}
		return null;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public Cookie findCookieCurrentTenant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = null;
		Map<String, Cookie> cookieMap = findCookieMap(request);
		if (cookieMap.containsKey(SysConfig.CurrentTenant)) {
			Object cookieObject = cookieMap.get(SysConfig.CurrentTenant);
			if (cookieObject == null) {
			} else {
				cookie = (Cookie) cookieObject;
				return cookie;
			}
		}
		return cookie;
	}
	public Cookie saveCookieCurrentTenant(HttpServletResponse response, String value) throws Exception {
		String name = SysConfig.CurrentTenant;
		Cookie cookie = new Cookie(name.trim(), value.trim());
		// 设置为30min
		cookie.setMaxAge(maxAge);
		// cookie.setMaxAge(10);
		cookie.setPath("/");
		cookie.setComment("sys_commnet");
		response.addCookie(cookie);
		return cookie;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public String findCookieValueCurrentAppUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = this.findCookieCurrentAppUser(request, response);
		if (cookie != null) {
			String value = cookie.getValue();
			return value;
		}
		return null;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public Cookie findCookieCurrentAppUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = null;
		Map<String, Cookie> cookieMap = findCookieMap(request);
		if (cookieMap.containsKey(SysConfig.CurrentAppUser)) {
			Object cookieObject = cookieMap.get(SysConfig.CurrentAppUser);
			if (cookieObject == null) {
			} else {
				cookie = (Cookie) cookieObject;
				return cookie;
			}
		}
		return cookie;
	}
	public Cookie saveCookieCurrentAppUser(HttpServletResponse response, String value) throws Exception {
		String name = SysConfig.CurrentAppUser;
		Cookie cookie = new Cookie(name.trim(), value.trim());
		// 设置为30min
		cookie.setMaxAge(maxAge);
		// cookie.setMaxAge(10);
		cookie.setPath("/");
		cookie.setComment("sys_commnet");
		response.addCookie(cookie);
		return cookie;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public String findCookieValueCurrentSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie =null;
		 cookie = this.findCookieCurrentSysUser(request, response);
		if(cookie==null){
			return null;
		}else{
			String value = cookie.getValue();
			return value;
		}
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 * @throws Exception
	 */
	public Cookie findCookieCurrentSysUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = null;
		Map<String, Cookie> cookieMap = findCookieMap(request);
		if (cookieMap.containsKey(SysConfig.CurrentSysUser)) {
			Object cookieObject = cookieMap.get(SysConfig.CurrentSysUser);
			if (cookieObject == null) {
			} else {
				cookie = (Cookie) cookieObject;
				return cookie;
			}
		}
		return cookie;
	}
	public Cookie saveCookieCurrentSysUser(HttpServletResponse response, String value) throws Exception {
		String name = SysConfig.CurrentSysUser;
		Cookie cookie = new Cookie(name.trim(), value.trim());
		// 设置为30min
		cookie.setMaxAge(maxAge);
		// cookie.setMaxAge(10);
		cookie.setPath("/");
		cookie.setComment("sys_commnet");
		response.addCookie(cookie);
		return cookie;
	}
	public Cookie saveCookie(HttpServletResponse response, String name, String value) throws Exception {
		Cookie cookie = new Cookie(name.trim(), value.trim());
		// 设置为30min
		cookie.setMaxAge(maxAge);
		// cookie.setMaxAge(10);
		cookie.setPath("/");
		cookie.setComment("sys_commnet");
		response.addCookie(cookie);
		return cookie;
	}
	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public Cookie findCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = findCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}
	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Cookie> findCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		log.trace("request="+request);
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
