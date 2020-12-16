package c.a.util.core.httpclient.http;

import c.a.config.core.CharsetConfigAy;
import c.a.tools.log.custom.common.BaseLogf;
import c.a.tools.log.custom.config.LogConfigFun;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

import static org.apache.http.HttpStatus.SC_OK;
/**
 * @deprecated
 */
@Deprecated
public class HttpClientAyUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static HttpClientAyUtil instance = null;
	private final static Object key = new Object();
	private HttpParams httpParams;
	private DefaultHttpClient httpClient = null;
	private String JSESSIONID; // 定义一个静态的字段，保存sessionID

	/**
	 * 私有的默认构造子
	 */
	private HttpClientAyUtil() {
	}

	public static HttpClientAyUtil findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new HttpClientAyUtil();
			}
		}
		return instance;
	}

	public HttpClient findHttpClient() {
		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
		httpParams = new BasicHttpParams();
		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 60 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 60 * 1000);
		// HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置 user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// 创建一个 HttpClient 实例
		// 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
		// 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
		httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	public HttpClient findHttpClient_v1() {
		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
		httpParams = new BasicHttpParams();
		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);
		// 设置 user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// 创建一个 HttpClient 实例
		// 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
		// 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
		httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	public byte[] doGetImg(String url) throws IOException {
		return this.doGetImg_v2(url);

	}

	public String doXml(String url, String json) throws IOException {
		return this.doPostXml_v1(url, json, false, CharsetConfigAy.utf8);
	}

	public String doJson(String url, Map parameterMap, String json) throws IOException {
		return this.doPostJson_v2(url, parameterMap, json, false, CharsetConfigAy.utf8);
	}

	public String doPost(String url, List<NameValuePair> parameterList) throws IOException {
		return this.doPost_v2(url, parameterList, CharsetConfigAy.utf8);
	}

	public String doGet(String url, Map parameterMap) throws IOException {
		return this.doGet_v2(url, parameterMap, CharsetConfigAy.utf8);
	}

	public String doPostXml_v1(String url, String json, boolean IS_NEED_PROXY, String charset) throws IOException {
		String strResult = "error_json";
		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpPost cHttpPost = new HttpPost(url);
			// 设置代理
			if (IS_NEED_PROXY) {
				HttpHost proxy = new HttpHost("192.168.13.19", 7777);
				httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			}
			StringEntity entity = new StringEntity(json, charset);// 解决中文乱码问题
			entity.setContentEncoding(charset);
			entity.setContentType("application/xml");
			cHttpPost.setEntity(entity);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpPost);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				// 请求结束，返回结果
				strResult = EntityUtils.toString(cHttpResponse.getEntity());
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
		} catch (UnsupportedCharsetException e) {
			// e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		}
		return strResult;
	}

	public String doPostJson_v2(String url, Map parameterMap, String json, boolean IS_NEED_PROXY, String charset)
			throws IOException {
		String strParameter = "";
		if (parameterMap != null) {
			Set keySet = parameterMap.keySet();
			for (Object key : keySet) {
				Object val = parameterMap.get(key);
				strParameter = strParameter + "&" + key + "=" + val;
			}
		}
		if (strParameter.equals("")) {
		} else {
			strParameter = strParameter.replaceFirst("&", "?");
			url = url + strParameter;
		}
		return this.doPostJson_v1(url, json, IS_NEED_PROXY, charset);

	}

	public String doPostJson_v1(String url, String json, boolean IS_NEED_PROXY, String charset) throws IOException {
		String strResult = "error_json";
		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpPost cHttpPost = new HttpPost(url);
			// 设置代理
			if (IS_NEED_PROXY) {
				HttpHost proxy = new HttpHost("192.168.13.19", 7777);
				httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			}
			StringEntity entity = new StringEntity(json, charset);// 解决中文乱码问题
			entity.setContentEncoding(charset);
			entity.setContentType("application/json");
			cHttpPost.setEntity(entity);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpPost);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				// 请求结束，返回结果
				strResult = EntityUtils.toString(cHttpResponse.getEntity());
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
		} catch (UnsupportedCharsetException e) {
			// e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		}
		return strResult;
	}

	/**
	 * 
	 * @Title: doGetImg_v1 @Description: 花费时间t=8430 @param
	 * url @return return @throws IOException 参数说明 @return byte[]
	 * 返回类型 @throws
	 */
	public byte[] doGetImg_v2(String url) throws IOException {
		String strResult = "error_doGetImg";

		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {

			HttpGet cHttpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpGet);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {

				/* 读返回数据 */
				cHttpEntity = cHttpResponse.getEntity();
				byte[] arrayByte = EntityUtils.toByteArray(cHttpEntity);
				return arrayByte;
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				EntityUtils.consume(cHttpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return null;
	}

	/**
	 * 下载不全
	 * 
	 * 该方法暂时弃用
	 * 
	 * @Title: doGetImg_v2 @Description: @param
	 * url @return return @throws IOException 参数说明 @return byte[]
	 * 返回类型 @throws
	 * 
	 * 
	 */
	public byte[] doGetImg_v1(String url) throws IOException {

		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
		String strResult = "error_doGetImg";

		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {

			HttpGet cHttpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			// java.net.SocketTimeoutException: Read timed out
			HttpResponse cHttpResponse = httpClient.execute(cHttpGet);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == SC_OK) {

				/* 读返回数据 */
				cHttpEntity = cHttpResponse.getEntity();
				InputStream is = cHttpEntity.getContent();

				byte[] arrayByte = fileUtil.doInputStream2byte(is);
				return arrayByte;
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				EntityUtils.consume(cHttpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return null;
	}

	public String doGet_v2(String url, Map parameterMap, String charset) throws IOException {
		String strResult = "error_doGet";
		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			/* 建立HTTPGet对象 */
			String strParameter = "";
			if (parameterMap != null) {
				Set keySet = parameterMap.keySet();
				for (Object key : keySet) {
					Object val = parameterMap.get(key);
					strParameter = strParameter + "&" + key + "=" + val;
				}
			}
			if (strParameter.equals("")) {
			} else {
				strParameter = strParameter.replaceFirst("&", "?");
				url = url + strParameter;
			}
			HttpGet cHttpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpGet);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				if (true) {
					/* 获取cookieStore */
					CookieStore cCookieStore = httpClient.getCookieStore();
					List<Cookie> listCookie = cCookieStore.getCookies();
					for (Cookie cookie : listCookie) {
						// log.trace("name="+cookie.getName());
						// log.trace("value="+cookie.getValue());
						// log.trace("version="+cookie.getVersion());
						// log.trace("date="+cookie.getExpiryDate());
						if ("JSESSIONID".equals(cookie.getName())) {
							JSESSIONID = cookie.getValue();
							// log.trace("1 JSESSIONID =" +
							// JSESSIONID);
							break;
						}
					}
				}
				// JSESSIONID = null;
				if (false) {
					Header[] arrayHeader = cHttpResponse.getHeaders("Set-Cookie");// 获取session值
					log.trace("Header size=" + arrayHeader.length);
					for (Header header : arrayHeader) {
						log.trace("name=" + header.getName());
						// JSESSIONID=2742655B20A174D0E91628B213F7D70D; Path=/a
						log.trace("value=" + header.getValue());
						String session_value_all = header.getValue();// 获取session值
						if (session_value_all != null) {
							log.trace("session_value_all =" + session_value_all);
							String[] array_session_value_a = session_value_all.split("JSESSIONID=");
							String[] array_session_value_b = array_session_value_a[1].split(";");
							log.trace("array_session_value_b =" + array_session_value_b[0]);
							JSESSIONID = array_session_value_b[0];
							log.trace("2 JSESSIONID =" + JSESSIONID);
							// break;
						}
					}
				}
				// JSESSIONID = null;
				if (false) {
					Header[] arrayHeader = cHttpResponse.getHeaders("Set-Cookie");// 获取session值
					log.trace("Header size=" + arrayHeader.length);
					for (Header header : arrayHeader) {
						// log.trace("name=" + header.getName());
						// JSESSIONID=2742655B20A174D0E91628B213F7D70D; Path=/a
						// log.trace("value=" + header.getValue());
						String strCookie = header.getValue();// 获取session值
						StringTokenizer cStringTokenizer = new StringTokenizer(strCookie, ";");
						while (cStringTokenizer.hasMoreTokens()) {
							String strJSESSIONID = cStringTokenizer.nextToken().trim();
							String[] arrayJSESSIONID = strJSESSIONID.split("=");
							if (arrayJSESSIONID.length == 2) {
								if ("JSESSIONID".equalsIgnoreCase(arrayJSESSIONID[0])) {
									JSESSIONID = arrayJSESSIONID[1];
									break;
								}
							}
						}
					}
					log.trace("3 JSESSIONID =" + JSESSIONID);
				}
				/* 读返回数据 */
				cHttpEntity = cHttpResponse.getEntity();
				strResult = EntityUtils.toString(cHttpEntity, charset);
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				EntityUtils.consume(cHttpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return strResult;
	}

	public String doGet_v1(String url, Map params) throws IOException {
		String strResult = null;
		try {
			/* 建立HTTPGet对象 */
			String paramStr = "";
			if (params != null) {
				Iterator iter = params.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					paramStr += paramStr = "&" + key + "=" + val;
				}
			}
			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				url += paramStr;
			}
			HttpGet httpRequest = new HttpGet(url);
			strResult = "doGetError";
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity());
			} else {
				strResult = "Error Response: " + httpResponse.getStatusLine().toString();
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		}
		return strResult;
	}

	public String doPost_v2(String url, List<NameValuePair> parameterList, String charset) throws IOException {
		String strResult = "error_doPost";
		HttpEntity cHttpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			/* 建立HTTPPost对象 */
			HttpPost cHttpPost = new HttpPost(url);
			/* 添加请求参数到请求对象 */
			if (parameterList != null && parameterList.size() > 0) {
				cHttpPost.setEntity(new UrlEncodedFormEntity(parameterList, charset));
			}
			if (null != JSESSIONID) {
				cHttpPost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
			}
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpPost);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				cHttpEntity = cHttpResponse.getEntity();
				strResult = EntityUtils.toString(cHttpEntity, charset);
				/* 获取cookieStore */
				CookieStore cCookieStore = httpClient.getCookieStore();
				List<Cookie> listCookie = cCookieStore.getCookies();
				for (Cookie cookie : listCookie) {
					// log.trace("name="+cookie.getName());
					// log.trace("value="+cookie.getValue());
					// log.trace("version="+cookie.getVersion());
					// log.trace("date="+cookie.getExpiryDate());
					if ("JSESSIONID".equals(cookie.getName())) {
						JSESSIONID = cookie.getValue();
						// log.trace("1 JSESSIONID =" + JSESSIONID);
						break;
					}
				}
			} else {
				strResult = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			try {
				EntityUtils.consume(cHttpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return strResult;
	}

	public String doPost_v1(String url, List<NameValuePair> params, String charset) throws IOException {
		String strResult = null;
		try {
			/* 建立HTTPPost对象 */
			HttpPost httpRequest = new HttpPost(url);
			strResult = "doPostError";
			/* 添加请求参数到请求对象 */
			if (params != null && params.size() > 0) {
				httpRequest.setEntity(new UrlEncodedFormEntity(params, charset));
			}
			if (null != JSESSIONID) {
				httpRequest.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
			}
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity());
				/* 获取cookieStore */
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if ("JSESSIONID".equals(cookies.get(i).getName())) {
						JSESSIONID = cookies.get(i).getValue();
						break;
					}
				}
			}
			BaseLogf.out("strResult=" + strResult, LogConfigFun.apache_HttpClient);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			throw e;
		} catch (ClientProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		}
		return strResult;
	}
}
