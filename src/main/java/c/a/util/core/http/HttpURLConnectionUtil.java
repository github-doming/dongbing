package c.a.util.core.http;
import c.a.util.core.exception.NetConntionRuntimeException;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * 
 * 网络工具
 * 
 * @author cxy
 * @Email: 使用范围：
 * 
 */
public class HttpURLConnectionUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public HttpURLContext doPost(String urlStr, Map<String, Object> parameterMap) throws IOException {
		HttpURLContext httpURLContext = new HttpURLContext();
		return this.doPost(httpURLContext, urlStr, parameterMap, "utf-8");
	}
	public HttpURLContext doPost(String urlStr, Map<String, Object> parameterMap, String charset) throws IOException {
		HttpURLContext httpURLContext = new HttpURLContext();
		return this.doPost(httpURLContext, urlStr, parameterMap, "utf-8");
	}
	public HttpURLContext doPost(HttpURLContext httpURLContext, String urlStr, Map<String, Object> parameterMap)
			throws IOException {
		return this.doPost(httpURLContext, urlStr, parameterMap, "utf-8");
	}
	/**
	 * 
	 * @Title: doPost
	 * @Description: doPost
	 *
	 *               参数说明
	 * @param urlStr
	 * @param parameterMap
	 * @param charset
	 * @return
	 * @throws IOException
	 *             返回类型 HttpURLContext
	 */
	public HttpURLContext doPost(HttpURLContext httpURLContext, String urlStr, Map<String, Object> parameterMap,
			String charset) throws IOException {
		if (StringUtil.isBlank(urlStr)) {
			// log.trace("请求URL为空，返回！");
			return null;
		}
		HttpURLConnection httpURLConnection = null;
		BufferedReader sendBufferedReader = null;
		String resultStr = null;
		try {
			URL url = null;
			url = new URL(urlStr);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=" + httpURLContext.getJSESSIONID());
			/**
			 * 参数设置 必须在 url.openConnection() 之后和
			 * httpUrlConnection.connect()之前完成;
			 */
			SSLSocketFactory oldSocketFactory = null;
			HostnameVerifier oldHostnameVerifier = null;
			boolean isUseHttps = urlStr.startsWith("https");
			if (isUseHttps) {
				HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
				oldSocketFactory = doTrustAllHosts(httpsURLConnection);
				oldHostnameVerifier = httpsURLConnection.getHostnameVerifier();
				httpsURLConnection.setHostnameVerifier(DO_NOT_VERIFY);
			}
			httpURLConnection.setRequestProperty("accept", "*/*");
			httpURLConnection.setRequestProperty("connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			/**
			 * 初始化
			 */
			httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=" + httpURLContext.getJSESSIONID());
			httpURLConnection.setRequestMethod("POST");// 设置请求的方式
			httpURLConnection.setReadTimeout(10 * 60 * 1000);// 设置超时的时间
			httpURLConnection.setConnectTimeout(10 * 60 * 1000);// 设置连接超时的时间
			// 设置是否向 httpUrlConnection 输出，因为这个是 post 请求，参数要放在 http 正文内，因此需要设为
			// true, 默认情况下是 false;
			httpURLConnection.setDoOutput(true);
			// 设置是否从 httpUrlConnection 读入，默认情况下是 true;
			httpURLConnection.setDoInput(true);
			// Post 请求不能使用缓存
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=" + charset);
			/**
			 * 参数设置
			 */
			if (parameterMap != null) {
				StringBuilder parameterPostStringBuilder = new StringBuilder();
				int index = 0;
				Set<String> keySet = parameterMap.keySet();
				for (String key : keySet) {
					if (index == 0) {
						parameterPostStringBuilder.append("");
					} else {
						parameterPostStringBuilder.append("&&");
					}
					parameterPostStringBuilder.append(key);
					parameterPostStringBuilder.append("=");
					parameterPostStringBuilder.append(parameterMap.get(key));
					index = index + 1;
				}
				// log.trace("parameterPostStringBuilder=" +
				// parameterPostStringBuilder);
				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(parameterPostStringBuilder.toString().getBytes(charset));
				outputStream.flush();
			}
			// 打印请求表头信息
			if (false) {
				Map headerMap = httpURLConnection.getHeaderFields();
				Set<String> headerKeyList = headerMap.keySet();
				for (String headerKey : headerKeyList) {
					List<String> headerValueList = (List) headerMap.get(headerKey);
					log.trace("表头信息 key =" + headerKey + ":");
					log.trace("headerValueList.size()=" + headerValueList.size());
					for (String headerValue : headerValueList) {
						log.trace("表头信息 value=" + headerValue + "\t");
					}
				}
				String cookieValue = httpURLConnection.getHeaderField("Set-Cookie");
				log.trace("表头信息cookie value=" + cookieValue);
			}

			/**
			 * HttpURLConnection的connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。
			 * 无论是post还是get
			 * ，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去。
			 */
			httpURLConnection.connect();
			/**
			 * 实际请求
			 */
			// 获取响应的状态码 404 200 505 302
			if (httpURLConnection.getResponseCode() == 200) {
				// 注意，实际发送请求的代码段就在这里
				sendBufferedReader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), charset));
				resultStr = this.readBufferedContent(sendBufferedReader);
				httpURLContext.setContent(resultStr);
				if (true) {
					String JSESSIONID = null;
					String sessionAllArrayStr = httpURLConnection.getHeaderField("Set-Cookie");// 获取session值
					// log.trace("sessionAllArrayStr=" + sessionAllArrayStr);
					// JSESSIONID
					if (StringUtil.isNotBlank(sessionAllArrayStr)) {
						String[] sessionAllArrayArray = sessionAllArrayStr.split(";");
						for (String sessionStr : sessionAllArrayArray) {
							String[] sessionArray = sessionStr.split("=");
							if (sessionArray.length == 2) {
								if ("JSESSIONID".equalsIgnoreCase(sessionArray[0])) {
									JSESSIONID = sessionArray[1];
									httpURLContext.setJSESSIONID(JSESSIONID);
									// log.trace("JSESSIONID =" + JSESSIONID);
									break;
								}
							}
						}
					}
				}
				return httpURLContext;
			} else {

				httpURLContext.setContent(httpURLConnection.getResponseMessage());
				httpURLContext.setRespCode(String.valueOf(httpURLConnection.getResponseCode()));
				return httpURLContext;
			}
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (sendBufferedReader != null) {
				sendBufferedReader.close();
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public HttpURLContext doGet(String urlStr, Map parameterMap) throws IOException {
		return this.doGet(urlStr, parameterMap, "UTF-8");
	}
	@SuppressWarnings("rawtypes")
	public HttpURLContext doPostJson(String urlStr, Map parameterMap, String jsonPost) throws IOException {
		return this.doPostJson(urlStr, parameterMap, jsonPost, "UTF-8");
	}
	@SuppressWarnings("rawtypes")
	private HttpURLContext doPostJson(String urlStr, Map parameterMap, String json, String charset) throws IOException {
		String parameterStr = "";
		if (parameterMap != null) {
			Set keySet = parameterMap.keySet();
			for (Object key : keySet) {
				Object valueObj = parameterMap.get(key);
				parameterStr = parameterStr + "&" + key + "=" + valueObj;
			}
		}
		if (parameterStr.equals("")) {
		} else {
			parameterStr = parameterStr.replaceFirst("&", "?");
			urlStr = urlStr + parameterStr;
		}
		return this.doPostJson(urlStr, json, charset);
	}
	/**
	 * 提交json,必须加上问号?
	 * 
	 * @Title: doPostJson
	 * @Description: 提交json,必须加上问号?
	 *
	 *               参数说明
	 * @param urlStr
	 * @param jsonPost
	 * @param charset
	 * @return
	 * @throws IOException
	 *             返回类型 HttpURLContext
	 */
	private HttpURLContext doPostJson(String urlStr, String jsonPost, String charset) throws IOException {
		if (StringUtil.isBlank(jsonPost)) {
			return null;
		}
		HttpURLContext httpURLContext = new HttpURLContext();
		if (StringUtil.isBlank(urlStr)) {
			return null;
		}
		HttpURLConnection httpURLConnection = null;
		String resultStr = null;
		try {
			URL urlObj = null;
			urlObj = new URL(urlStr);
			httpURLConnection = (HttpURLConnection) urlObj.openConnection();
			/**
			 * 参数设置 必须在 url.openConnection() 之后和
			 * httpUrlConnection.connect()之前完成;
			 */
			/**
			 * 初始化
			 */
			httpURLConnection.setRequestMethod("POST");// 设置请求的方式
			httpURLConnection.setReadTimeout(100 * 10 * 1000);// 设置超时的时间
			httpURLConnection.setConnectTimeout(100 * 10 * 1000);// 设置连接超时的时间
			// 设置是否向 httpUrlConnection 输出，因为这个是 post 请求，参数要放在 http 正文内，因此需要设为
			// true, 默认情况下是 false;
			httpURLConnection.setDoOutput(true);
			// 设置是否从 httpUrlConnection 读入，默认情况下是 true;
			httpURLConnection.setDoInput(true);
			// Post 请求不能使用缓存
			// httpURLConnection.setUseCaches(false);
			// httpURLConnection.setRequestProperty("Content-Type",
			// "application/json");
			// 设置接收数据的格式
			httpURLConnection.setRequestProperty("Accept", "application/json");
			httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
			/**
			 * 参数设置
			 */
			// ("参数=" + stringBuffer_postParameter.toString());
			OutputStream outputStream = null;
			try {
				outputStream = httpURLConnection.getOutputStream();
			} catch (Exception e) {
				log.error("系统异常:",e);
				throw new NetConntionRuntimeException("网络连接出错");
			}
			outputStream.write(jsonPost.getBytes(charset));
			outputStream.flush();
			/**
			 * HttpURLConnection的connect()函数，实际上只是建立了一个与服务器的tcp连接，并没有实际发送http请求。
			 * 无论是post还是get
			 * ，http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去。
			 */
			httpURLConnection.connect();
			/**
			 * 实际请求
			 */
			// 获取响应的状态码 404 200 505 302
			if (httpURLConnection.getResponseCode() == 200) {
				// 注意，实际发送请求的代码段就在这里
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), charset));
				resultStr = this.readBufferedContent(bufferedReader);
				httpURLContext.setContent(resultStr);
				if (true) {
					String JSESSIONID = null;
					String sessionAllArrayStr = httpURLConnection.getHeaderField("Set-Cookie");// 获取session值
					// log.trace("sessionAllArrayStr=" +
					// sessionAllArrayStr);
					// JSESSIONID
					if (StringUtil.isNotBlank(sessionAllArrayStr)) {
						String[] sessionAllArray = sessionAllArrayStr.split(";");
						for (String sessionStr : sessionAllArray) {
							String[] sessionArray = sessionStr.split("=");
							if (sessionArray.length == 2) {
								if ("JSESSIONID".equalsIgnoreCase(sessionArray[0])) {
									JSESSIONID = sessionArray[1];
									httpURLContext.setJSESSIONID(JSESSIONID);
									// log.trace("JSESSIONID =" +
									// JSESSIONID);
									break;
								}
							}
						}
					}
				}
				return httpURLContext;
			} else {
				String respCode = String.valueOf(httpURLConnection.getResponseCode());
				httpURLContext.setRespCode(respCode);
				resultStr = "error_response[" + respCode + "]";
				httpURLContext.setContent(resultStr);
				return httpURLContext;
			}
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
	}
	/**
	 * 
	 * @Title: doGet
	 * @Description: doGet
	 *
	 *               参数说明
	 * @param urlStr
	 * @param parameterMap
	 * @param charset
	 * @return
	 * @throws IOException
	 *             返回类型 HttpURLContext
	 */
	public HttpURLContext doGet(String urlStr, Map parameterMap, String charset) throws IOException {
		HttpURLContext httpURLContext = new HttpURLContext();
		if (StringUtil.isBlank(urlStr)) {
			return null;
		}
		String resultStr = null;
		HttpURLConnection httpURLConnection = null;
		try {
			/**
			 * 
			 */
			String parameterStr = "";
			if (parameterMap != null) {
				Set keySet = parameterMap.keySet();
				for (Object key : keySet) {
					Object valueObj = parameterMap.get(key);
					parameterStr = parameterStr + "&" + key + "=" + valueObj;
				}
			}
			if (parameterStr.equals("")) {
			} else {
				parameterStr = parameterStr.replaceFirst("&", "?");
				urlStr = urlStr + parameterStr;
			}
			// 根据地址创建URL对象(网络访问的url)
			URL url = new URL(urlStr);
			// url.openConnection()打开网络连接
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");// 设置请求的方式
			httpURLConnection.setReadTimeout(60 * 1000);// 设置超时的时间
			httpURLConnection.setConnectTimeout(60 * 1000);// 设置连接超时的时间
			httpURLConnection.connect();
			/**
			 * 实际请求
			 */
			// 获取响应的状态码 404 200 505 302
			if (httpURLConnection.getResponseCode() == 200) {
				// 获取响应的输入流对象
				// 实际发送http请求
				InputStream inputStream = httpURLConnection.getInputStream();
				// 创建字节输出流对象
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				// 定义读取的长度
				int lengthInt = 0;
				// 定义缓冲区
				byte[] bufferByteArray = new byte[1024];
				// 按照缓冲区的大小，循环读取
				while ((lengthInt = inputStream.read(bufferByteArray)) != -1) {
					// 根据读取的长度写入到os对象中
					byteArrayOutputStream.write(bufferByteArray, 0, lengthInt);
				}
				// 释放资源
				inputStream.close();
				byteArrayOutputStream.close();
				// 返回字符串
				httpURLContext.setArrayByte(byteArrayOutputStream.toByteArray());
				resultStr = new String(httpURLContext.getArrayByte(), charset);
				httpURLContext.setContent(resultStr);
				/**
				 * 
				 */
				String JSESSIONID = null;
				String sessionAllArrayStr = httpURLConnection.getHeaderField("Set-Cookie");// 获取session值
				// log.trace(" sessionAllArrayStr=" +
				// sessionAllArrayStr);
				if (StringUtil.isNotBlank(sessionAllArrayStr)) {
					// JSESSIONID
					String[] sessionAllArray = sessionAllArrayStr.split(";");
					for (String sessionStr : sessionAllArray) {
						String[] sessionArray = sessionStr.split("=");
						if (sessionArray.length == 2) {
							if ("JSESSIONID".equalsIgnoreCase(sessionArray[0])) {
								JSESSIONID = sessionArray[1];
								httpURLContext.setJSESSIONID(JSESSIONID);
								// log.trace("JSESSIONID =" +
								// JSESSIONID);
								break;
							}
						}
					}
				}
				return httpURLContext;
			} else {
				resultStr = "error_response[" + httpURLConnection.getResponseCode() + "]";
			}
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return null;
	}
	/**
	 * 下载图片
	 * 
	 * @Title: doGetImg
	 * @Description: 下载图片
	 *
	 *               参数说明
	 * @param urlStr
	 * @return
	 * @throws IOException
	 *             返回类型 byte[]
	 */
	public byte[] doGetImg(String urlStr) throws IOException {
		if (StringUtil.isBlank(urlStr)) {
			return null;
		}
		String resultStr = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(urlStr);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");// 设置请求的方式
			httpURLConnection.setReadTimeout(60 * 1000);// 设置超时的时间
			httpURLConnection.setConnectTimeout(60 * 1000);// 设置连接超时的时间
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode() == 200) {
				InputStream inputStream = httpURLConnection.getInputStream();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int lengthInt = 0;
				byte[] bufferByteArray = new byte[4096];
				while ((lengthInt = inputStream.read(bufferByteArray)) != -1) {
					byteArrayOutputStream.write(bufferByteArray, 0, lengthInt);
				}
				inputStream.close();
				byteArrayOutputStream.close();
				// 返回字符串
				byte[] byteArray = byteArrayOutputStream.toByteArray();
				return byteArray;
			} else {
				resultStr = "error_response[" + httpURLConnection.getResponseCode() + "]";
				log.error("下载图片出错=" + resultStr);
			}
		} catch (MalformedURLException e) {
			// e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			// e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// e.printStackTrace();
			throw e;
		} finally {
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
			}
		}
		return null;
	}
	public String readBufferedContent(BufferedReader bufferedReader) {
		if (bufferedReader == null) {
			return null;
		}
		StringBuilder resultStringBuilder = new StringBuilder();
		String lineStr = null;
		try {
			while ((lineStr = bufferedReader.readLine()) != null) {
				resultStringBuilder.append(lineStr + "\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return resultStringBuilder.toString();
	}
	/**
	 * 覆盖java默认的证书验证
	 */
	private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[]{};
		}
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}};
	/**
	 * 设置不验证主机
	 */
	private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	/**
	 * 
	 * @Title: trustAllHosts
	 * @Description: 信任所有
	 *
	 *               参数说明
	 * @param connection
	 * @return 返回类型 SSLSocketFactory
	 */
	private static SSLSocketFactory doTrustAllHosts(HttpsURLConnection connection) {
		SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLSocketFactory newFactory = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(newFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oldFactory;
	}
}
