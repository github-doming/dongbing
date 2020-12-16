package c.x.all.simple.charset.file.example;
import java.io.IOException;
import c.x.all.simple.charset.file.FileCharset;
public class FileCharset_example_e2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileCharset cFileCharset = new FileCharset();
			// java.nio.charset.Charset.forName("").newEncoder().canEncode(c)
			String filePath = "X://gen//1//快速清空文件内容.txt";
			String charset = cFileCharset.findEncoding_v5(filePath);
			System.out.println("charset=" + charset);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
