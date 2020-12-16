package c.x.all.simple.charset.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import c.a.util.core.file.FileUtil;
import c.x.all.simple.charset.string.StringCharset;

public class FileCharset extends StringCharset {
	private static boolean isPrint = true;
	private static String filePath_ay = "/test/t.txt";
	/**
	 * 
	 * 整个文件目录转成UTF-8
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void fileDirectory2utf8(String filePath) throws IOException {
		File fileRoot = new File(filePath);
		if (fileRoot.isDirectory()) {
			File[] arrayFile = fileRoot.listFiles();
			for (File file : arrayFile) {
				if (file.isDirectory()) {
					fileDirectory2utf8(file.getAbsolutePath());
				} else {
					file2utf8(file);
				}
			}
		}
		if (fileRoot.isFile()) {
			file2utf8(fileRoot);
		}
	}

	/**
	 * 
	 * 文件转成UTF-8格式
	 * 
	 * @param filePath_ay
	 * @throws IOException
	 */
	public void file2utf8(String charset, File file) throws IOException {
		String filePath = file.getAbsolutePath();
		if (filePath.endsWith(".txt")) {
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb = fileUtil.read(charset, file);
			String string$file_content = sb.toString();
			// System.out.println("string$file_content=" + string$file_content);
			fileUtil.write(string$file_content, filePath);
		}
	}

	/**
	 * 
	 * 文件转成UTF-8格式
	 * 
	 * @param filePath_ay
	 * @throws IOException
	 */
	public void file2utf8(File file) throws IOException {
		String filePath = file.getAbsolutePath();
		if (filePath.endsWith(".txt")) {
			System.out.println("file.getAbsolutePath()=" + filePath);
			String charset = findEncoding(file.getAbsolutePath());
			System.out.println("charset=" + charset);
			if (StringCharset.UTF_8.equals(charset)) {
			} else {
				// System.out.println("开始转换");
				FileUtil fileUtil = new FileUtil();
				StringBuilder sb = fileUtil.read(StringCharset.GBK, file);
				String string$file_content = sb.toString();
				System.out
						.println("string$file_content=" + string$file_content);
				fileUtil.write(string$file_content, filePath);
			}
		}
	}

	/**
	 * 
	 * 文件转成UTF-8格式
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void file2utf8(String charset, String filePath) throws IOException {
		if (filePath.endsWith(".txt")) {
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb = fileUtil
					.readByAbsolutePath(charset, filePath);
			String string$file_content = sb.toString();
			fileUtil.write(string$file_content, filePath);
		}
	}

	/**
	 * 
	 * 文件转成UTF-8格式
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void file2utf8(String filePath) throws IOException {
		if (filePath.endsWith(".txt")) {
			String charset = findEncoding(filePath);
			if (StringCharset.UTF_8.equals(charset)) {
			} else {
				FileUtil fileUtil = new FileUtil();
				StringBuilder sb = fileUtil.readByAbsolutePath(charset,
						filePath);
				String string$file_content = sb.toString();
				fileUtil.write(string$file_content, filePath);
			}
		}
	}

	/**
	 * 
	 * 找出路径
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String findPath(String sourceNameStr)
			throws UnsupportedEncodingException {
		URL sourceNameUrl = this.getClass().getResource(sourceNameStr);
		String pathStr = null;
		if (sourceNameUrl == null) {
			String str = "找不到文件sourceName=" + sourceNameStr;
			if (isPrint) {
				System.out.println(str);
			}
			return null;
		} else {
		}
		pathStr = java.net.URLDecoder.decode(sourceNameUrl.getPath(),
				StringCharset.UTF_8);
		return pathStr;
	}

	/**
	 * 不能判断
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String findEncoding(String filePath) throws IOException {
		return this.findEncoding_v5(filePath);
	}

	/**
	 * 不能判断
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String findEncoding_v5(String filePath) throws IOException {
		boolean boolean_gbk = false;
		boolean boolean_utf8 = false;
		File file = new File(filePath);
		InputStream cInputStream = new java.io.FileInputStream(file);
		FileUtil fileUtil = new FileUtil();
		byte[] byteArray = fileUtil.doInputStream2byte(cInputStream);
		for (byte c_byte : byteArray) {
			System.out.println((char) c_byte);
			char c = (char) c_byte;
			boolean_gbk = java.nio.charset.Charset.forName("GB2312")
					.newEncoder().canEncode(c);
			boolean_utf8 = java.nio.charset.Charset
					.forName(StringCharset.UTF_8).newEncoder().canEncode(c);
			System.out.println(boolean_gbk);
			System.out.println(boolean_utf8);
			System.out.println("=");
			if (boolean_gbk && boolean_utf8) {
				// continue;
			} else {
				break;
			}
		}
		cInputStream.close();
		if (boolean_utf8) {
			return StringCharset.UTF_8;
		}
		if (boolean_gbk) {
			return StringCharset.GBK;
		}
		return StringCharset.GBK;
	}

	/**
	 * 
	 * 不能判断
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String findEncoding_v3(String filePath) throws IOException {
		File file = new File(filePath);
		InputStream in = new java.io.FileInputStream(file);
		java.io.BufferedInputStream bis = new BufferedInputStream(in);
		bis.mark(0);
		byte[] b = new byte[3];
		int read = bis.read(b, 0, 3);
		if (read == -1) {
			System.out.println("read=" + read);
			return "gbk";
		}
		in.close();
		System.out.println(b[0]);
		System.out.println(b[1]);
		System.out.println(b[2]);
		if (b[0] == (byte) 0xEF && b[1] == 0xBB && b[2] == 0xBF) {
			// System.out.println(file.getName() + "=编码为UTF-8");
			return StringCharset.UTF_8;
		} else {
			if (b[0] == -1 && b[1] == -2) {
				return StringCharset.UTF_16;
			} else {
				if (b[0] == -2 && b[1] == -1) {
					return StringCharset.Unicode;
				} else {
					// System.out.println(file.getName() + "=可能是GBK，也可能是其他编码");
					return StringCharset.GBK;
				}
			}
		}
	}

	/**
	 * 
	 * 不能判断
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String findEncoding_v12(String filePath) throws IOException {
		File file = new File(filePath);
		InputStream in = new java.io.FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
			System.out.println(file.getName() + "=编码为UTF-8");
			return StringCharset.UTF_8;
		} else {
			if (b[0] == -1 && b[1] == -2) {
				return StringCharset.UTF_16;
			} else {
				if (b[0] == -2 && b[1] == -1) {
					return StringCharset.Unicode;
				} else {
					System.out.println(file.getName() + "=可能是GBK，也可能是其他编码");
					return StringCharset.GBK;
				}
			}
		}
	}

	/**
	 * 
	 * 不能判断
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public String findEncoding_v11(String filePath) throws IOException {
		File file = new File(filePath);
		InputStream in = new java.io.FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
			System.out.println(file.getName() + "=编码为UTF-8");
			return StringCharset.UTF_8;
		} else {
			System.out.println(file.getName() + "=可能是GBK，也可能是其他编码");
			return StringCharset.GBK;
		}
	}
}
