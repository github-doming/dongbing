package c.a.util.core.httpclient.http;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import c.a.config.core.CharsetConfigAy;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.string.StringUtil;
@Deprecated
public class HttpClientUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private CloseableHttpClient httpClient = null;
	// 定义一个静态的字段，保存sessionID
	private String JSESSIONID; 
	private static HttpClientUtil instance = null;
	private final static Object key = new Object();
	/**
	 *私有的默认构造函数
	 */
	private HttpClientUtil() {
	}
	public static HttpClientUtil findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new HttpClientUtil();
			}
		}
		return instance;
	}
	public HttpClient findHttpClient() {
		// 浏览器Agent
		String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19";
		// 创建并配置HttpClient
		httpClient = HttpClients.custom().setUserAgent(USER_AGENT).setDefaultRequestConfig(
				RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build()).build();
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
	public String doPostXml_v1(String url, String json, boolean isNeedProxy, String charset) throws IOException {
		String strResult = "error_json";
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置代理
			if (isNeedProxy) {
				HttpHost proxy = new HttpHost("192.168.13.19", 7777);
				httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			}
			StringEntity entity = new StringEntity(json, charset);// 解决中文乱码问题
			entity.setContentEncoding(charset);
			entity.setContentType("application/xml");
			httpPost.setEntity(entity);
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpPost);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 请求结束，返回结果
				strResult = EntityUtils.toString(httpResponse.getEntity());
			} else {
				strResult = "error_response[" + httpResponse.getStatusLine().toString() + "]";
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
	public String doPostJson_v2(String url, Map parameterMap, String json, boolean isNeedProxy, String charset)
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
		return this.doPostJson_v1(url, json, isNeedProxy, charset);
	}
	public String doPostJson_v1(String url, String json, boolean isNeedProxy, String charset) throws IOException {
		String strResult = "error_json";
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			// 设置代理
			if (isNeedProxy) {
				HttpHost proxy = new HttpHost("192.168.13.19", 7777);
				httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
			}
			StringEntity entity = new StringEntity(json, charset);// 解决中文乱码问题
			entity.setContentEncoding(charset);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(httpPost);
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
	 *         url @return return @throws IOException 参数说明 @return byte[]
	 *         返回类型 @throws
	 */
	public byte[] doGetImg_v2(String url) throws IOException {
		String resultStr = "error_doGetImg";
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpGet);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				httpEntity = httpResponse.getEntity();
				byte[] arrayByte = EntityUtils.toByteArray(httpEntity);
				return arrayByte;
			} else {
				resultStr = "error_response[" + httpResponse.getStatusLine().toString() + "]";
			}
			// BaseLogf.out("resultStr=" + resultStr,
			// LogConfigFun.apache_HttpClient);
			log.trace("resultStr=" + resultStr);
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
				EntityUtils.consume(httpEntity);
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
	 *         url @return return @throws IOException 参数说明 @return byte[]
	 *         返回类型 @throws
	 * 
	 * 
	 */
	public byte[] doGetImg_v1(String url) throws IOException {
		FileUtil fileUtil =FileThreadLocal.findThreadLocal().get();
		String resultStr = "error_doGetImg";
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			// java.net.SocketTimeoutException: Read timed out
			HttpResponse httpResponse = httpClient.execute(httpGet);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == SC_OK) {
				/* 读返回数据 */
				httpEntity = httpResponse.getEntity();
				InputStream is = httpEntity.getContent();
				byte[] arrayByte = fileUtil.doInputStream2byte(is);
				return arrayByte;
			} else {
				resultStr = "error_response[" + httpResponse.getStatusLine().toString() + "]";
			}
			// BaseLogf.out("strResult=" + resultStr,
			// LogConfigFun.apache_HttpClient);
			log.trace("strResult=" + resultStr);
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
				EntityUtils.consume(httpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return null;
	}
	public String doGet_v2(String url, Map parameterMap, String charset) throws IOException {
		String resultStr = "error_doGet";
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			/* 建立HTTPGet对象 */
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
				url = url + parameterStr;
			}
			HttpGet cHttpGet = new HttpGet(url);
			/* 发送请求并等待响应 */
			HttpResponse cHttpResponse = httpClient.execute(cHttpGet);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				if (true) {
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
				/* 读返回数据 */
				httpEntity = cHttpResponse.getEntity();
				resultStr = EntityUtils.toString(httpEntity, charset);
			} else {
				resultStr = "error_response[" + cHttpResponse.getStatusLine().toString() + "]";
			}
			// BaseLogf.out("strResult=" + resultStr,
			// LogConfigFun.apache_HttpClient);
			log.trace("strResult=" + resultStr);
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
				EntityUtils.consume(httpEntity);
			} catch (IOException e) {
				// e.printStackTrace();
				throw e;
			}
		}
		return resultStr;
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
			// BaseLogf.out("strResult=" + strResult,
			// LogConfigFun.apache_HttpClient);
			log.trace("strResult=" + strResult);
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
		HttpEntity httpEntity = null;
		if (httpClient == null) {
			this.findHttpClient();
		}
		try {
			/* 建立HTTPPost对象 */
			HttpPost httpPost = new HttpPost(url);
			/* 添加请求参数到请求对象 */
			if (parameterList != null && parameterList.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(parameterList, charset));
			}
			log.trace("JSESSIONID=" + JSESSIONID);
			if (null != JSESSIONID) {
				httpPost.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
			}
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpPost);
			/* 若状态码为200 ok */
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			log.trace("状态码statusCode=" + statusCode);
			// 处理http返回码302的情况
			if (statusCode == 302) {
				String locationUrl = httpResponse.getLastHeader("Location").getValue();
				log.trace("302 重定向的url =" + locationUrl);
				return doPost(locationUrl, parameterList);// 跳转到重定向的url
			}
			if (statusCode == 200) {
				/* 读返回数据 */
				httpEntity = httpResponse.getEntity();
				strResult = EntityUtils.toString(httpEntity, charset);
				if (true) {
					Header[] arrayHeader = httpResponse.getHeaders("Set-Cookie");// 获取session值
					// log.trace("Header size=" + arrayHeader.length);
					for (Header header : arrayHeader) {
						// log.trace("header.getName=" +
						// header.getName());
						// JSESSIONID=2742655B20A174D0E91628B213F7D70D; Path=/a
						// log.trace("header.getValue=" +
						// header.getValue());
						String strSessionAll = header.getValue();// 获取session值
						if (StringUtil.isNotBlank(strSessionAll)) {
							String[] arraySessionAll = strSessionAll.split(";");
							for (String strSession : arraySessionAll) {
								String[] arraySession = strSession.split("=");
								if (arraySession.length == 2) {
									if ("JSESSIONID".equalsIgnoreCase(arraySession[0])) {
										JSESSIONID = arraySession[1];
										// log.trace("JSESSIONID =" +
										// JSESSIONID);
										break;
									}
								}
							}
						}
					}
				}
			} else {
				strResult = "error_response[" + httpResponse.getStatusLine().toString() + "]";
			}
			// BaseLogf.out("strResult=" + strResult,
			// LogConfigFun.apache_HttpClient);
			// log.trace("strResult=" + strResult);
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
				EntityUtils.consume(httpEntity);
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
			HttpResponse cHttpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (cHttpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(cHttpResponse.getEntity());
				if (true) {
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
			}
			// BaseLogf.out("strResult=" + strResult,
			// LogConfigFun.apache_HttpClient);
			log.trace("strResult=" + strResult);
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
