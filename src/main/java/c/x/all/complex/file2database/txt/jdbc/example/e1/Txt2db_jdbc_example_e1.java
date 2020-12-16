package c.x.all.complex.file2database.txt.jdbc.example.e1;
import java.io.IOException;
import c.x.all.complex.file2database.txt.jdbc.nut.Txt2db_jdbc;
public class Txt2db_jdbc_example_e1 {
	protected static boolean isPrint = true;
	private static String filePath = "/co/chen/complex/util/file2database/txt/jdbc/example/e1/test/t.txt";
	/**
	 * 批量增加测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Txt2db_jdbc e = new Txt2db_jdbc();
			e.doJdbcTransactionBatch(e.findPathIO(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isPrint) {
			System.out.println("end");
		}
	}
}
