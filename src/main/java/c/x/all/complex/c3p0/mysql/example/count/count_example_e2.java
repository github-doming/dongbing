package c.x.all.complex.c3p0.mysql.example.count;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.PropertyConfigurator;

import c.x.all.complex.c3p0.nut.DataSource_c3p0;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.log.LogConfig;
import c.a.util.core.path.PathUtil;

public class count_example_e2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 日志
		 */
		PathUtil pathUtil = new PathUtil();
		System.setProperty("webapp.root", "d:\\");
		try {
			PropertyConfigurator.configure(pathUtil.findPath(LogConfig.url));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/**
		 * 
		 */
		DataSource_c3p0 c3p0 = new DataSource_c3p0();
		Calendar startCalendar = Calendar.getInstance();
		long startCalendarLong = startCalendar.getTimeInMillis();

		String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
		String username = "jsaas_test";
		String password = "jsaas_test";

		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();

		String sql = "select  count(*) c from  test_type";
		Connection conn = null;
		try {
			// int i=1/0;
			conn = c3p0.findConnection_mysql();
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
