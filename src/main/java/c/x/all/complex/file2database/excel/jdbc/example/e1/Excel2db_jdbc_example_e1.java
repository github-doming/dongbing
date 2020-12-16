package c.x.all.complex.file2database.excel.jdbc.example.e1;
import java.io.IOException;
import c.x.all.complex.file2database.excel.jdbc.nut.Excel2db_jdbc;
public class Excel2db_jdbc_example_e1 {
	protected static boolean isPrint = true;
	/**
	 * 批量增加测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Excel2db_jdbc e = new Excel2db_jdbc();
			e.doJdbcTransactionBatch("x://删除//测试_所有字段.xls");
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
