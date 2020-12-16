package c.x.all.complex.c3p0.sqlserver.example.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.x.all.complex.c3p0.nut.DataSource_c3p0;
import all.gen.fun_type_int.t.entity.FunTypeIntT;

public class queryObject_example_e1 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Calendar startCalendar = Calendar.getInstance();
		long startCalendarLong = startCalendar.getTimeInMillis();
		String url = "jdbc:oracle:thin:@192.168.1.20:1521:jsaas";
		String username = "jsaas_test";
		String password = "jsaas_test";

		IJdbcTool jdbcTool = JdbcToolFactory.createApi(url);
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		DataSource_c3p0 c3p0 = new DataSource_c3p0();

		String sql = "select  * from  WechatMoReceiver";
		Connection conn = null;
		try {
			// int i=1/0;
			conn = c3p0.findConnection_sqlserver();
			// 关闭自动提交
			jdbcUtil.doTransactionStart(conn);
			// 业务开始
			// 创建SQL执行工具
			QueryRunner cQueryRunner = new QueryRunner();
			// 执行SQL查询，并获取结果
			List<FunTypeIntT> list = (List<FunTypeIntT>) cQueryRunner
					.query(conn, sql, new BeanListHandler(FunTypeIntT.class));
			// 输出查询结果
			for (FunTypeIntT type : list) {
				System.out.println(type.getId());
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
