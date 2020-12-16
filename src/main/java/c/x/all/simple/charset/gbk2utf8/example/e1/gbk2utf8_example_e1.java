package c.x.all.simple.charset.gbk2utf8.example.e1;

import java.io.IOException;

import c.a.util.core.file.FileUtil;
import c.x.all.simple.charset.file.FileCharset;

public class gbk2utf8_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "x://gen//1//groupby_v1.txt";
		String charset = null;
		try {
			FileCharset cFileCharset = new FileCharset();
			charset = cFileCharset.findEncoding(filePath);
			System.out.println("charset=" + charset);
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb = fileUtil
					.readByAbsolutePath(charset, filePath);
			System.out.println("content=" + sb.toString());
			String string$file_content = sb.toString();
			fileUtil.write(string$file_content, filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
