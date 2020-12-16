package c.x.all.complex.c3p0.mysql.example.insert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.QueryRunner;

import c.x.all.complex.c3p0.nut.DataSource_c3p0;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.nut.IJdbcUtil;
/**
 * 
 * 
 * 
 * 
 */
public class PrepareStatement_insert_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
		String username = "jsaas_test";
		String password = "jsaas_test";
		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		DataSource_c3p0 c3p0 = new DataSource_c3p0();
		String sql = "insert into fun_type_str_t(id,name) values(?,?) ";
		Object[] arrayParameters = new Object[]{"21", "a3"};
		Connection conn = null;
		try {
			conn = c3p0.findConnection_mysql();
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			// 新增记录
			// jps.preparedStatement_executeUpdate(conn, sql,
			// parameterList);
			// 创建SQL执行工具
			QueryRunner qRunner = new QueryRunner();
			// 执行SQL插入
			int rows = qRunner.update(conn, sql, arrayParameters);
			System.out.println("成功插入" + rows + "条数据！");
			// 查询
			sql = "select * from fun_type where id=?";
			ArrayList<Object> parameterList = new ArrayList<Object>();
			parameterList.add("21");
			JdbcPrepareStatementDto jpsb = jdbcUtil.findResultSet(conn, sql,
					parameterList);
			ResultSet resultSet = jpsb.getResultSet();
			while (resultSet.next()) {
				String name = (String) resultSet.getObject("name");
				System.out.println("name=" + name);
			}
			// 业务结束
			// int i = 1 / 0;
			jdbcUtil.doTransactionCommit(conn);
		} catch (Exception e) {
			try {
				jdbcUtil.doTransactionRollback(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("end");
	}
}
