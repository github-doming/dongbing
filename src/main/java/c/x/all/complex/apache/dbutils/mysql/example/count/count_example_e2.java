package c.x.all.complex.apache.dbutils.mysql.example.count;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.nut.IJdbcUtil;

public class count_example_e2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar startCalendar = Calendar.getInstance();
		long startCalendarLong = startCalendar.getTimeInMillis();

			String url = "jdbc:mysql://127.0.0.1:3306/cjx?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL";
		String username = "root";
		String password = "";
		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		String sql = "select  count(*) c from  test_type";
		Connection conn = null;
		try {
			// int i=1/0;
			conn = jdbcUtil.openConnection(url, username, password);
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			// 创建SQL执行工具
			QueryRunner cQueryRunner = new QueryRunner();
			// 执行SQL查询，并获取结果
			List<Map<String, Object>> list = (List<Map<String, Object>>) cQueryRunner
					.query(conn, sql, new MapListHandler());
			// 输出查询结果
			for (Map<String, Object> map : list) {
				System.out.println("c2=" + map.get("c"));
			}
			// 业务结束
			// int i=1/0;
			jdbcUtil.doTransactionCommit(conn);
		} catch (Throwable e) {
			try {
				jdbcUtil.doTransactionRollback(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		Calendar endCalendar = Calendar.getInstance();
		long endCalendarLong = endCalendar.getTimeInMillis();
		long ms = endCalendarLong - startCalendarLong;
		// mysql花费时间spend time=343
		// sqlserver 花费时间
		System.out.println("花费时间spend time=" + ms);
		System.out.println("end");
	}
}
