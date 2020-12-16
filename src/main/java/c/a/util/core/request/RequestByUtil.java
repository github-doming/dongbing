package c.a.util.core.request;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.core.CharsetConfigAy;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.reflect.ReflectJsonUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.security.UrlUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * @author cxy
 * @Email: 使用范围：
 * 
 */
public class RequestByUtil extends RequestAyUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public String separator = ",";
	/**
	 * 从请求中读取整个post数据 request.getInputStream()只能获取一次
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String findData(HttpServletRequest request) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder lineStringBuilder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			lineStringBuilder.append(line);
		}
		String returnStr = lineStringBuilder.toString();
		return returnStr;
	}
	/**
	 * url参数转成实体类
	 * 
	 * @Title: doUrlStr2Entity
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @param urlAllStr
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 Object
	 */
	@SuppressWarnings("rawtypes")
	public Object doUrlStr2Entity(Class classObj, String urlAllStr) throws UnsupportedEncodingException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return doUrlStr2Entity(classObj, urlAllStr, CharsetConfigAy.utf8);
	}
	/**
	 * url参数转成实体类
	 * 
	 * @Title: doUrlStr2Entity
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @param urlAllStr
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 Object
	 */
	@SuppressWarnings("rawtypes")
	public Object doUrlStr2Entity(Class classObj, String urlAllStr, String charset) throws UnsupportedEncodingException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ReflectJsonUtil reflectJsonUtil = ReflectThreadLocal.findThreadLocal().get();
		Map<String, Object> map = doUrlParameter2map(urlAllStr, charset);
		return reflectJsonUtil.doMap2Object(classObj, map);
	}
	/**
	 * 
	 * url参数map类型转成String
	 * 
	 * 
	 */
	public String doUrlParameter2str(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		Set<String> urlStrList = map.keySet();
		for (String key : urlStrList) {
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		return sb.toString();
	}
	/**
	 * url参数转成map
	 * 
	 * @Title: doUrlParameter2map
	 * @Description:
	 *
	 * 				参数说明
	 * @param urlAllStrInput
	 * @return
	 * @throws UnsupportedEncodingException
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> doUrlParameter2map(String urlAllStrInput) throws UnsupportedEncodingException {
		return doUrlParameter2map(urlAllStrInput, CharsetConfigAy.utf8);
	}
	/**
	 * url参数转成map
	 * 
	 * @Title: doUrlParameter2map
	 * @Description:
	 *
	 * 				参数说明
	 * @param urlAllInputStr
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> doUrlParameter2map(String urlAllInputStr, String charset)
			throws UnsupportedEncodingException {
		String urlAllStr = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isBlank(urlAllInputStr)) {
			return map;
		} else {
			urlAllStr = URLDecoder.decode(urlAllInputStr, charset);
		}
		String[] parameterAllArray = urlAllStr.split("\\&");
		for (String urlString : parameterAllArray) {
			String[] paraArray = urlString.split("=");
			if (paraArray.length == 2) {
				map.put(paraArray[0], paraArray[1]);
			}
		}
		return map;
	}
	/**
	 * url参数转成map
	 * 
	 * @Title: doUrlParameter2map
	 * @Description:
	 *
	 * 				参数说明
	 * @param urlAllStr
	 * @param defaultKey
	 * @param defaultValue
	 * @return 返回类型 Map<String,Object>
	 */
	public Map<String, Object> doUrlParameter2map(String urlAllStr, String defaultKey, String defaultValue) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isBlank(urlAllStr)) {
			map.put(defaultKey, defaultValue);
			return map;
		}
		String[] parameterAllArray = urlAllStr.split("\\&");
		for (String urlString : parameterAllArray) {
			String[] paraArray = urlString.split("=");
			if (paraArray.length == 1) {
				map.put(defaultKey, defaultValue);
			} else {
				map.put(paraArray[0], paraArray[1]);
			}
		}
		return map;
	}
	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param class_origin
	 * @param destinatioClass
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Object doRequest2Entity(Class destinatioClass, HttpServletRequest request) throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) destinatioClass.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		return doRequest2Entity(tableName, destinatioClass, request);
	}
	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param class_origin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Object doRequest2Entity(String tableName, Class classDestination, HttpServletRequest request)
			throws Exception {
		HashMap<String, Object> map = findParameterListByTableName(tableName, request);
		ReflectUtil reflectUti = new ReflectUtil();
		Object obj = reflectUti.doMap2Object(classDestination, map);
		return obj;
	}
	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param classOrigin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object doRequest2Entity(Class classOrigin, Class classDestination, HttpServletRequest request)
			throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) classDestination.getAnnotation(AnnotationTable.class);
		String tableNameStr = table.name();
		return doRequest2Entity(tableNameStr, classOrigin, classDestination, request);
	}
	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param classOrigin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Object doRequest2Entity(String tableName, Class classOrigin, Class classDestination,
			HttpServletRequest request) throws Exception {
		ReflectJsonUtil reflectJsonUtil = ReflectThreadLocal.findThreadLocal().get();
		HashMap<String, Object> map = findParameterListByTableName(tableName, request);
		Object obj = reflectJsonUtil.doMap2Object(classOrigin, classDestination, map);
		return obj;
	}
	public String  findParameter(HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String, Object> parameterMap=this.findParameterList(request);
		return this.findParameter(parameterMap);
	}
	public String  findParameter( Map<String, Object> parameterMap){
		String parameterStr = "";
		if (parameterMap != null) {
			Set keySet = parameterMap.keySet();
			for (Object key : keySet) {
				Object val = parameterMap.get(key);
				parameterStr = parameterStr + "&" + key + "=" + val;
			}
		}
		if (parameterStr.equals("")) {
		} else {
			parameterStr = parameterStr.replaceFirst("&", "?");
		}
		return parameterStr;
	}
	/**
	 * 
	 * request.getParameterNames;
	 * 
	 * 从HttpServletRequest中取得参数
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> findParameterList(HttpServletRequest request) throws UnsupportedEncodingException {
		// 1;
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// 得到页面传过来的参数
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			// 解码
			String keyNew = java.net.URLDecoder.decode(key, "UTF-8");
			// 如果全选的选择框的值都不选的话,key为null
			// Base//log.trace("key=" + key);
			Object oValue = request.getParameter(key);
			// 得到下拉列表或全选的选择框的值
			String sValue = doParameterCheckboxs2Str(oValue);
			// 解码
			sValue = URLDecoder.decode(sValue, "UTF-8");
			returnMap.put(keyNew, sValue);
		}
		return returnMap;
	}
	public void printParameterList(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, Object> map = findParameterList(request);
		Set<String> setKey = map.keySet();
		for (String key : setKey) {
			// log.trace("key=" + key);
			// log.trace("value=" + map.get(key));
			log.trace("key=" + key);
			log.trace("value=" + map.get(key));
		}
	}
	/**
	 * 
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 例如:sys_user.sys_user_id;
	 * 
	 * 从HttpServletRequest中取得参数;
	 * 
	 * request.getParameterNames;
	 * 
	 * @param request
	 * @param tableName
	 *            数据库表名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<String, Object> findParameterListByTableName(String tableName, HttpServletRequest request) {
		// 1;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		// System.out.println("returnMap.size="+ returnMap.size());
		// 得到页面传过来的参数
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String[] keyArray = key.split("\\.");
			// 如果等于数据库的表名称的话
			// log.trace("keyArray[0]=" + keyArray[0]);
			// System.out.println("keyArray[0]=" + keyArray[0]);
			if (keyArray[0].equals(tableName)) {
				// 如果全选的选择框的值都不选的话,key为null
				// log.trace("key=" + key);
				Object value = request.getParameter(key);
				// 得到下拉列表或全选的选择框的值
				String string_value = doParameterCheckboxs2Str(value);
				// array_key[1]是数据库的字段名称
				returnMap.put(keyArray[1], string_value);
			}
		}
		// 放表的名称放到returnMap
		returnMap.put("table_name", tableName);
		return returnMap;
	}
	/**
	 * 复选参数转成字符串
	 * 
	 * @param ids
	 * @return
	 */
	public String doParameterCheckboxs2Str(Object ids) {
		StringBuilder returnStringBuilder = new StringBuilder();
		if (ids instanceof String[]) {
			String[] strArray = (String[]) ids;
			for (Object id : strArray) {
				returnStringBuilder.append(id.toString().trim()).append(separator);
			}
			// 删除结尾的分隔符
			int start = returnStringBuilder.length() - separator.length();
			int end = returnStringBuilder.length();
			returnStringBuilder.delete(start, end);
		}
		if (ids instanceof String) {
			returnStringBuilder.append(ids.toString().trim());
		}
		return returnStringBuilder.toString();
	}
	public String findMAC() throws UnknownHostException, SocketException {
		InetAddress ia = InetAddress.getLocalHost();// 获取本地IP对象
		if(ia!=null){
			byte[] byteArray = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			return findMAC(byteArray);
		}
		return null;
	}
	/**
	 * 获取访问者ip
	 * 
	 * @param request
	 * @return
	 */
	public String findIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 找出本地服务器IP
	 * 
	 */
	public String findIPLocal() throws UnknownHostException {
		// 获取本地IP对象
		InetAddress ia = InetAddress.getLocalHost();
		byte[] byteArray = ia.getAddress();
		return findIP(byteArray);
	}
	/**
	 * 找出本地服务器IP
	 * 
	 */
	public String findIP(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			byte b = byteArray[i];
			int valueInt = 0;
			if (b >= 0)
				valueInt = b;
			else {
				valueInt = 256 + b;
			}
			// 0 代表前面补充0
			// 3 代表长度为3
			// d 代表参数为正数型
			String str = String.format("%03d", valueInt);
			sb.append(str);
			if (i != byteArray.length - 1) {
				sb.append(".");
			}
		}
		return sb.toString();
	}
	/**
	 * 找出本地mac;
	 * 
	 * byteArray有可能为空;
	 * 
	 */
	public String findMAC(byte[] byteArray) {
		// byteArray有可能为空
		if (byteArray != null && byteArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteArray.length; i++) {
				byte b = byteArray[i];
				int valueInt = 0;
				if (b >= 0)
					valueInt = b;
				else {
					valueInt = 256 + b;
				}
				sb.append(Integer.toHexString(valueInt));
				if (i != byteArray.length - 1) {
					// sb.append("_");
					sb.append("-");
				}
			}
			return sb.toString();
		}
		return null;
	}
	/**
	 * 返回请求中的上下路径(比如http://localhost:8080/jsaas/)
	 * 
	 * @param request
	 * @return
	 */
	public String findContextPath(HttpServletRequest request) {
		if (false) {
			// 就是取得客户端的系统版本
			System.out.println("客户端的系统版本=" + request.getHeader("User-Agent"));
			// 取得客户端的IP
			System.out.println("客户端的IP=" + request.getRemoteAddr());
			// 取得客户端的主机名
			System.out.println("取得客户端的主机名=" + request.getRemoteHost());
			// 取得客户端的端口
			System.out.println("客户端的端口=" + request.getRemotePort());
			// 取得客户端的用户
			System.out.println("客户端的用户=" + request.getRemoteUser());
			// 取得服务器IP
			System.out.println("服务器IP=" + request.getLocalAddr());
			// 取得服务器端口;
			System.out.println("服务器端口=" + request.getLocalPort());
		}
		int portInt = request.getLocalPort();
		String port = String.valueOf(portInt);
		// uri=/jsaas/login.do
		// url=http://localhost:8080/jsaas/login2.do
		// contextPath=/jsaas
		String contextPath = request.getContextPath();
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		int index = url.indexOf(uri);
		if (index != -1) {
			// String result= url.substring(0, index) +":"+port+ contextPath;
			String result = url.substring(0, index) + contextPath;
			return result;
		}
		return url;
	}
	/**
	 * 返回请求中的绝对路径(login2.do)
	 * 
	 * @param request
	 * @return
	 */
	public String findRequestURI(HttpServletRequest request) {
		//
		// uri=/jsaas/login.do
		// url=http://localhost:8080/jsaas/login2.do
		// contextPath=/jsaas
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		if (StringUtil.isNotBlank(contextPath)) {
			int index = uri.indexOf(contextPath);
			if (index != -1) {
				return uri.substring(index + contextPath.length());
			}
		}
		return uri;
	}
}