package c.x.platform.root.exception.dao;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.gen.sys_exception.t.entity.SysExceptionT;
import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
public class LogDao {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String thisClassName = this.getClass().getName();
	public void save(ServletContext cServletContext, String servletPath, Exception exception) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		// BaseJdbc baseJdbc = new BaseJdbc();
		// 连接
		// 用简单数据源 DataSource
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance().findLocal();
		Connection connectionLog = jdbcDataSource.getConnection();
		if (connectionLog == null) {
			return;
		}
		// 取得 Throwable
		Throwable throwable = exception.getCause();
		if (throwable == null) {
			throwable = exception;
		}
		SysExceptionT sysExceptionT = new SysExceptionT	();
		StackTraceElement[] stacks = throwable.getStackTrace();
		int stacksLength = stacks.length;
		// 异常类名称
		sysExceptionT.setExceptionClass(throwable.getClass().getName());
		sysExceptionT.setBizClass(stacks[0].getClassName());
		sysExceptionT.setMsg(throwable.getMessage());
		sysExceptionT.setMethodName(stacks[0].getMethodName());
		sysExceptionT.setLineNumber(stacks[0].getLineNumber());
		sysExceptionT.setServletPath(servletPath);
		// 保存
		// sql
		String sql = "INSERT INTO sys_exception("
				+ "CREATE_TIME_,BIZ_CLASS_,METHOD_NAME,LINE_NUMBER_,MSG_,EXCEPTION_CLASS_,SERVLET_PATH_"
				+ ") VALUES(?,?,?,?,?,?,?)";
		// 参数
		ArrayList<Object> parameterList = new ArrayList<Object>();
		Date date = new Date();
		parameterList.add(date.toLocaleString());
		parameterList.add(sysExceptionT.getBizClass());
		parameterList.add(sysExceptionT.getMethodName());
		parameterList.add(sysExceptionT.getLineNumber());
		parameterList.add(sysExceptionT.getMsg());
		parameterList.add(sysExceptionT.getExceptionClass());
		parameterList.add(sysExceptionT.getServletPath());
		// 设置事 务
		connectionLog.setAutoCommit(false);
		// executeInsert
		jdbcUtil.execute(connectionLog, sql, parameterList);
		// 设置事 务
		connectionLog.commit();
		connectionLog.setAutoCommit(true);
		if (true) {
			log.trace("类" + this.thisClassName + "[" + servletPath + "]" + "关闭日志的数据库连接的hashCode="
					+ connectionLog.hashCode());
		}
		// 关闭连接
		jdbcUtil.closeConnection(connectionLog);
	}
}
