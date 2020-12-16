package c.a.util.core.http;
import java.io.ByteArrayOutputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class HttpURLConnectionFileUtil {
	private String charset = "UTF-8";
	private URL url;
	private HttpURLConnection httpURLConnection;
	private String boundary = "feng";
	private Map<String, Object> textParameter = new HashMap<String, Object>();
	private Map<String, File> fileParameter = new HashMap<String, File>();
	private DataOutputStream dataOutputStream;
	public HttpURLConnectionFileUtil() throws Exception {
	}
	/**
	 * 
	 * @Title: setCharset
	 * @Description:
	 * @param charset
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	/**
	 * 
	 * @Title: setUrl
	 * @Description: 重新设置要请求的服务器地址，即上传文件的地址。
	 * @param url
	 * @throws Exception 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void setUrl(String url) throws Exception {
		this.url = new URL(url);
	}
	/**
	 * 
	 * @Title: addTextParameter
	 * @Description:增加一个普通字符串数据到form表单数据中
	 * @param name
	 * @param value
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void addTextParameter(String key, String value) {
		textParameter.put(key, value);
	}
	/**
	 * 
	 * @Title: addFileParameter
	 * @Description: 增加一个文件到form表单数据中
	 * @param name
	 * @param value
	 *            参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void addFileParameter(String key, File value) {
		fileParameter.put(key, value);
	}
	/**
	 * 
	 * @Title: clearParameterAll
	 * @Description: 清空所有已添加的form表单数据
	 * @param 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void clearParameterAll() {
		textParameter.clear();
		fileParameter.clear();
	}
	/**
	 * 
	 * @Title: send
	 * @Description: 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
	 * @return
	 * @throws IOException 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String send2str() throws IOException {
		String str = new String(this.send());
		return str;
	}
	/**
	 * 
	 * @Title: send
	 * @Description: 发送数据到服务器，返回一个字节包含服务器的返回结果的数组
	 * @return
	 * @throws IOException 参数说明
	 * @return byte[] 返回类型
	 * @throws
	 */
	public byte[] send() throws IOException {
		initConnection();
		try {
			httpURLConnection.connect();
		} catch (IOException e) {
			throw e;
		}
		dataOutputStream = new DataOutputStream(
				httpURLConnection.getOutputStream());
		writeFileParams();
		writeStringParams();
		paramsEnd();
		InputStream is = httpURLConnection.getInputStream();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int intByte;
		while ((intByte = is.read()) != -1) {
			byteArrayOutputStream.write(intByte);
		}
		httpURLConnection.disconnect();
		byte[] arrayByte = byteArrayOutputStream.toByteArray();
		this.clearParameterAll();
		return arrayByte;
	}
	/**
	 * 
	 * @Title: initConnection
	 * @Description: 文件上传的connection的一些必须设置
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	private void initConnection() throws IOException {
		httpURLConnection = (HttpURLConnection) this.url.openConnection();
		// 设置是否向 httpUrlConnection 输出，因为这个是 post 请求，参数要放在 http 正文内，因此需要设为
		// true, 默认情况下是 false;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setUseCaches(false);
		// 设置连接超时的时间
		// 连接超时为10秒
		httpURLConnection.setConnectTimeout(10 * 1000);
		httpURLConnection.setRequestMethod("POST");
		// cHttpURLConnection.setRequestProperty("user-agent",
		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		httpURLConnection.setRequestProperty("Charsert", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"multipart/form-data; boundary=" + boundary);
	}
	/**
	 * 
	 * @Title: writeFileParams
	 * @Description: 文件数据
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	private void writeFileParams() throws IOException {
		Set<String> setKey = fileParameter.keySet();
		for (String key : setKey) {
			File value = fileParameter.get(key);
			dataOutputStream.writeBytes("--" + boundary + "\r\n");
			dataOutputStream
					.writeBytes("Content-Disposition: form-data; name=\"" + key
							+ "\"; filename=\"" + encode(value.getName())
							+ "\"\r\n");
			dataOutputStream.writeBytes("Content-Type: "
					+ findContentType(value) + "\r\n");
			dataOutputStream.writeBytes("\r\n");
			dataOutputStream.write(file2byteArray(value));
			dataOutputStream.writeBytes("\r\n");
		}
	}
	/**
	 * 
	 * @Title: writeStringParams
	 * @Description: 普通字符串数据
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	private void writeStringParams() throws IOException {
		Set<String> setKey = textParameter.keySet();
		for (String key : setKey) {
			Object value = textParameter.get(key);
			dataOutputStream.writeBytes("--" + boundary + "\r\n");
			dataOutputStream
					.writeBytes("Content-Disposition: form-data; name=\"" + key
							+ "\"\r\n");
			dataOutputStream.writeBytes("\r\n");
			dataOutputStream.writeBytes(encode(value.toString()) + "\r\n");
		}
	}
	/**
	 * 
	 * @Title: paramsEnd
	 * @Description: 添加结尾数据
	 * @throws IOException 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	private void paramsEnd() throws IOException {
		dataOutputStream.writeBytes("--" + boundary + "--" + "\r\n");
		dataOutputStream.writeBytes("\r\n");
	}
	/**
	 * 
	 * @Title: file2arrayByte
	 * @Description: 把文件转换成字节数组
	 * @param file
	 * @return
	 * @throws IOException 参数说明
	 * @return byte[] 返回类型
	 * @throws
	 */
	private byte[] file2byteArray(File file) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(file);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] byteArray = new byte[1024];
		int intByte;
		while ((intByte = fileInputStream.read(byteArray)) != -1) {
			byteArrayOutputStream.write(byteArray, 0, intByte);
		}
		fileInputStream.close();
		return byteArrayOutputStream.toByteArray();
	}
	/**
	 * 获取文件的上传类型，图片格式为image/png,image/jpg等。 非图片为application/octet-stream
	 * 
	 * @Title: findContentType
	 * @Description:
	 * @param file
	 * @return
	 * @throws IOException 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	private String findContentType(File file) throws IOException {
		// 此行不再细分是否为图片，全部作为application/octet-stream 类型
		// return "application/octet-stream"; //
		ImageInputStream imageInputStream = ImageIO
				.createImageInputStream(file);
		if (imageInputStream == null) {
			return "application/octet-stream";
		}
		Iterator<ImageReader> itImageReader = ImageIO
				.getImageReaders(imageInputStream);
		if (itImageReader.hasNext()) {
		} else {
			imageInputStream.close();
			return "application/octet-stream";
		}
		imageInputStream.close();
		// 将FormatName返回的值转换成小写，默认为大写
		return "image/" + itImageReader.next().getFormatName().toLowerCase();
	}
	/**
	 * 
	 * 对包含中文的字符串进行转码，此为UTF-8。 服务器那边要进行一次解码
	 * 
	 * @Title: encode
	 * @Description:
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	private String encode(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, this.charset);
	}
}
