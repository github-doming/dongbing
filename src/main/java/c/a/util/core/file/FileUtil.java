package c.a.util.core.file;

import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.http.HttpURLConnectionUtil;
import c.a.util.core.http.RequestContext;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.uuid.Uuid;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * 文件工具
 * 
 * @author cxy
 * @Email:  使用范围：
 */
public class FileUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	// 回车换行符,先回车后换行
	public static String line_delimiter = "\r\n";

	public RequestContext findPic(HttpServletRequest request) throws Exception {
		RequestContext requestContext = new RequestContext();
		if (request == null) {
			return requestContext;
		}
		StringBuffer urlStringBuilder = request.getRequestURL();
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		// ServletRequest sr = (ServletRequest) request;
		// rc.setRealPath(sr.getRealPath("/"));
		requestContext.setPathReal("");
		String domainPath = urlStringBuilder.delete(urlStringBuilder.length() - uri.length(), urlStringBuilder.length())
				.append(contextPath).append("/").toString();
		requestContext.setPathDomain(domainPath);
		String id = Uuid.create().toString();
		requestContext.setPathDomainPic(requestContext.getPathDomain() + "pic/" + id + ".jpg");
		requestContext.setPathRealPic(requestContext.getPathReal() + "pic/" + id + ".jpg");
		return requestContext;
	}

	/**
	 * 文件转成byte[]
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public byte[] findByteArray(String filePath) throws IOException {
		InputStream is = this.findFileInputStream(filePath);
		return doInputStream2byte(is);
	}

	/**
	 * 
	 * 
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public BufferedInputStream findBufferedInputStream(String filePath) throws FileNotFoundException {
		BufferedInputStream bis = new BufferedInputStream(this.findFileInputStream(filePath));
		return bis;
	}

	/**
	 * 
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public FileInputStream findFileInputStream(String filePath) throws FileNotFoundException {
		try {
			return new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// throw e;
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 
	 * @Title: write @Description: 下载图片并写到硬盘 @param sUrl @param
	 * pathDestination @throws IOException 参数说明 @return void 返回类型 @throws
	 */
	public void load(String urlStr, String pathDestination) throws IOException {
		HttpURLConnectionUtil httpURLConnectionUtil = new HttpURLConnectionUtil();
		if (StringUtil.isBlank(urlStr)) {
			return;
		}
		if (StringUtil.isBlank(pathDestination)) {
			return;
		}
		doByteArray2file(httpURLConnectionUtil.doGetImg(urlStr), pathDestination);
	}

	/**
	 * byte数组到图片
	 * 
	 * @Title: byte2image @Description: @param data @param
	 * pathDestination @throws IOException 参数说明 @return void 返回类型 @throws
	 */
	public void doByteArray2file(byte[] data, String filePath) throws IOException {
		if (data == null) {
			return;
		}
		if (StringUtil.isBlank(filePath)) {
			return;
		}
		if (data.length < 3) {
			return;
		}
		FileImageOutputStream fios = new FileImageOutputStream(new File(filePath));
		fios.write(data, 0, data.length);
		fios.close();
	}

	/**
	 * 
	 * blob转成byte
	 * 
	 * @param blob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public byte[] doBlob2byte(Blob blob) throws SQLException, IOException {
		return this.doInputStream2byte(blob.getBinaryStream());
	}

	public byte[] doInputStream2byte(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return null;
		}
		try {
			int i = 4096;
			final ByteArrayBuffer buffer = new ByteArrayBuffer(i);
			final byte[] tmp = new byte[4096];
			int l;
			while ((l = inputStream.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toByteArray();
		} finally {
			inputStream.close();
		}
	}

	public String doInputStream2String(String charset, InputStream is) throws IOException {
		if (is == null) {
			return "";
		}
		byte[] bufferByteArray = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		while ((len = is.read(bufferByteArray)) != -1) {
			baos.write(bufferByteArray, 0, len);
		}
		String result = new String(baos.toByteArray(), charset);
		is.close();
		baos.close();
		return result;
	}

	/**
	 * 追加内容
	 * 
	 * @Title: append @Description: @param filePath @param
	 * content @return return @throws IOException 参数说明 @return boolean
	 * 返回类型 @throws
	 */
	public boolean append(String filePath, String content) throws IOException {
		StringBuilder sb = read(filePath);
		content = sb.toString() + content;
		boolean resultBoolean = write(filePath, content);
		return resultBoolean;
	}

	/**
	 * 追加内容
	 * 
	 * @Description: desc @Title: appendByAbsoluteFilePath @param
	 * filePath @param content @return return @throws IOException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean appendByAbsolutePath(String filePath, String content) throws IOException {
		StringBuilder sb = readByAbsolutePath(filePath);
		content = sb.toString() + content;
		boolean resultBoolean = writeByAbsolutePath(filePath, content);
		return resultBoolean;
	}
	/**
	 * 
	 * 读取文件内容
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public byte[] readFile2byteArray(String fileName) throws IOException {
		PathUtil path = new PathUtil();
		byte[] b = null;
		String filePath = null;
		if (true) {
			filePath = path.findPath(fileName);
			log.trace("绝对路径=" + filePath);
			b = findByteArray(filePath);
		}
		return b;
	}

	/**
	 * 读取相对路径的文件内容
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public StringBuilder read(String path) throws IOException {
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		String filePathAbsolute = pathUtil.findPath(path);
		log.trace("绝对路径=" + filePathAbsolute);
		StringBuilder sb = readByAbsolutePath(filePathAbsolute);
		return sb;
	}

	/**
	 * 读取绝对路径的文件内容
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public StringBuilder readByAbsolutePath(String filePath) throws IOException {
		return this.readByAbsolutePath("UTF-8", filePath);
	}

	/**
	 * 读取绝对路径的文件内容
	 * 
	 * @Description: desc @Title: readByAbsolutePath @param
	 * charsetName @param filePath @return return @throws
	 * IOException 参数说明 @return StringBuilder 返回类型 @throws
	 */
	public StringBuilder readByAbsolutePath(String charsetName, String filePath) throws IOException {
		AssertUtil.isNull(filePath, "路径不能为空");
		File file = new File(filePath);
		return this.read(charsetName, file);
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public StringBuilder read(File file) throws IOException {
		return this.read("UTF-8", file);
	}

	/**
	 * 读取文件内容
	 * 
	 * @Description: desc @Title: read @param charsetName @param
	 * file @return return @throws IOException 参数说明 @return StringBuilder
	 * 返回类型 @throws
	 */
	public StringBuilder read(String charsetName, File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
			while (br.ready()) {
				sb.append(br.readLine() + line_delimiter);
			}
		} catch (FileNotFoundException e) {
			log.error("系统异常:",e);
			throw e;
		} catch (IOException e) {
			log.error("系统异常:",e);
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return sb;
	}

	/**
	 * 写文件,绝对路径的
	 * 
	 * @Title: write @Description: @param filePath @param
	 * content @return return @throws
	 * FileNotFoundException @throws UnsupportedEncodingException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean writeByAbsolutePath(String filePathAbsolute, String content)
			throws FileNotFoundException, UnsupportedEncodingException {

		File file = new File(filePathAbsolute);

		return this.write("UTF-8", file, content);
	}

	/**
	 * 写文件,相对路径;
	 * 
	 * @Title: write @Description: @param filePath @param
	 * content @return return @throws
	 * FileNotFoundException @throws UnsupportedEncodingException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean write(String filePath, String content) throws FileNotFoundException, UnsupportedEncodingException {
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		String filePathAbsolute = pathUtil.findPath(filePath);

		File file = new File(filePathAbsolute);

		return this.write("UTF-8", file, content);
	}
	/**
	 * 
	 * 写文件 @Description: desc @Title: write @param charsetName @param
	 * file @param content @return return @throws
	 * FileNotFoundException @throws UnsupportedEncodingException
	 * 参数说明 @return boolean 返回类型 @throws
	 */
	public boolean write(String charsetName, File file, String content)
			throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file, charsetName);
			pw.write(content);
			pw.flush();
			return true;
		} catch (Exception e) {
			log.error("系统异常:",e);
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean isFileExistsByAbsoluteFilePath(String path) {
		return new File(path).exists();
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean isFileExists(String path) throws UnsupportedEncodingException {
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		String filePathAbsolute = pathUtil.findPath(path);
		// log.trace("绝对路径=" + filePathAbsolute);
		return this.isFileExistsByAbsoluteFilePath(filePathAbsolute);
	}

	/**
	 * byteArrayString
	 * 
	 * 编码必须为utf-8
	 * 
	 * @param byteArray
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doByteArray2String(byte[] byteArray) throws UnsupportedEncodingException {
		String str = new String(byteArray, "utf-8");
		return str;
	}

	/**
	 * 
	 * 
	 * inputStream转成String;
	 * 
	 * @param inputStreamInput
	 * @return
	 * @throws IOException
	 */
	public String doInputStream2String(InputStream inputStreamInput) throws IOException {
		// 总文件大小available
		int available = inputStreamInput.available();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int length = -1;
		while ((length = inputStreamInput.read()) != -1) {
			available = inputStreamInput.available();
			baos.write(length);
		}
		String str = baos.toString();
		baos.close();
		return str;
	}
	/**
	 * 
	 * 
	 * @Description: desc @Title: doFile2FileInputStream @param
	 * file @return return @throws FileNotFoundException 参数说明 @return
	 * FileInputStream 返回类型 @throws
	 */
	public FileInputStream doFile2FileInputStream(File file) throws FileNotFoundException {
		return new FileInputStream(file);
	}
	/**
	 * 文件转成byte[]
	 * 
	 * @Description: desc @Title: doFile2byte @param
	 * file @return return @throws IOException 参数说明 @return byte[]
	 * 返回类型 @throws
	 */
	public byte[] doFile2byte(File file) throws IOException {
		InputStream is = this.doFile2FileInputStream(file);
		return doInputStream2byte(is);
	}
}