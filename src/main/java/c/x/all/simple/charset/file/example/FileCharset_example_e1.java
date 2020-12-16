package c.x.all.simple.charset.file.example;
import c.x.all.simple.charset.file.FileCharset;
public class FileCharset_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileCharset cFileCharset = new FileCharset();
			String filePath = "/test/t.txt";
			String charset = cFileCharset.findEncoding(cFileCharset
					.findPath(filePath));
			System.out.println("charset=" + charset);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
