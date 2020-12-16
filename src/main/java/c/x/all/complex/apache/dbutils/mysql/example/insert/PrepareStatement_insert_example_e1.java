package c.x.all.complex.apache.dbutils.mysql.example.insert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.QueryRunner;

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

			String url = "jdbc:mysql://127.0.0.1:3306/cjx?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL";
		String username = "root";
		String password = "";
		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();

		String sql = "insert into fun_type_str_t(id,name) values(?,?) ";
		Object[] arrayParameters = new Object[]{"21", "a3"};

		Connection conn = null;
		try {
			conn = jdbcUtil.openConnection(url, username, password);
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			// 新增记录
			// jps.preparedStatement_executeUpdate(conn, sql,
			// parameterList);

			// 创建SQL执行工具
			QueryRunner qRunner = new QueryRunner();
			// 执行SQL插入

			int n = qRunner.update(conn, sql, arrayParameters);
			System.out.println("成功插入" + n + "条数据！");

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
