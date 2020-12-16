package c.x.all.complex.apache.dbutils.sqlserver.example.insert;

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
 * JdbcPrepareStatement
 * 
 * 
 */
public class PrepareStatement_insert_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sql = null;

		String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
		String username = "jsaas_test";
		String password = "jsaas_test";

		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = null;
		try {
			conn = jdbcUtil.getConnection();
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			/**
			 * 查询
			 */
			sql = "SELECT IDENT_CURRENT('WechatMoData')";
			ArrayList<Object> parameterList = new ArrayList<Object>();
			JdbcPrepareStatementDto jpsb = jdbcUtil.findResultSet(conn, sql,
					parameterList);
			ResultSet resultSet = jpsb.getResultSet();
			while (resultSet.next()) {
				java.math.BigDecimal PackageId = (java.math.BigDecimal) resultSet
						.getObject(1);
				System.out.println("id =" + PackageId.intValue());
			}
			/**
			 * 新增记录
			 */
			sql = "insert into WechatMoData (PackageId) values(?) ";
			Object[] arrayParameters = new Object[]{"23"};
			// jps.preparedStatement_executeUpdate(conn, sql,
			// parameterList);
			// 创建SQL执行工具
			QueryRunner qRunner = new QueryRunner();
			// 执行SQL插入
			int n = qRunner.update(conn, sql, arrayParameters);
			System.out.println("成功插入" + n + "条数据！");
			/**
			 * 查询
			 */
			sql = "select * from  WechatMoData where PackageId=?";
			parameterList = new ArrayList<Object>();
			parameterList.add("23");
			jpsb = jdbcUtil.findResultSet(conn, sql, parameterList);
			resultSet = jpsb.getResultSet();
			while (resultSet.next()) {
				String PackageId = (String) resultSet.getObject("PackageId");
				System.out.println("PackageId=" + PackageId);
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
