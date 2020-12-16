package c.x.all.simple.charset.gbk2utf8.example.e1;
import java.io.File;
import java.io.IOException;
import c.x.all.simple.charset.file.FileCharset;
/**
 * 
 * 转换所有的文件
 * 
 * 
 */
public class gbk2utf8_all_files_example_e2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "x://gen//1";
		// String filePath = "U://cxy//a3//k5//i10//v1//3//a12";
		try {
			FileCharset cFileCharset = new FileCharset();
			cFileCharset.fileDirectory2utf8(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
