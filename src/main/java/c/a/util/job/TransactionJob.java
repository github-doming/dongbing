package c.a.util.job;
import c.a.config.core.CommContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
/**
 * 任务执行基类，这个类采用模版模式进行实现。
 * 
 * 子类继承这个类后，任务执行的日志就会自动记录下来，
 * 
 * 不需要在子类中在进行记录。
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
// quartz中设置Job不并发执行
@DisallowConcurrentExecution
public abstract class TransactionJob extends TransactionBase implements Job {
	protected Logger log = LogManager.getLogger(TransactionJob.class);
	public boolean transaction = false;
	public boolean database = false;
	public abstract String executeTransaction(JobExecutionContext context) throws Exception;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		CommContextUtil commContextUtil = new CommContextUtil();
		long startCalendarLong = 0;
		startCalendarLong = commContextUtil.start();
		try {
			if (database) {
				if (transaction) {
					// 需要事务
					this.transaction(context);
				} else {
					// 不需要事务
					this.transactionNot(context);
				}
			} else {
				if (transaction) {
					// 需要事务
					this.transaction(context);
				} else {
					// 不需要数据库操作
					this.databaseNot(context);
				}
			}
		} catch (Exception e) {
			log.error("定时出错", e);
		}
		commContextUtil.end(startCalendarLong);
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String databaseNot(JobExecutionContext context) throws Exception {
		String returnStr = this.executeTransaction(context);
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transactionNot(JobExecutionContext context) throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection conn = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal()	;
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			conn = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源conn=" + conn);
			if (conn == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(conn);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				String returnStr = executeTransaction(context);
				return returnStr;
			}
		} catch (Exception e) {
			log.error("系统异常:",e);
			// 重新抛出异常
			throw e;
		} finally {
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (conn != null) {
				log.trace("用简单数据源 DataSource关闭连接  conn");
				// 用简单数据源 DataSource关闭连接
				conn.close();
				// jdbcDataSource.connectionClose(conn);
				// 从ThreadLocal中remove连接
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			}
		}
	}
	/**
	 * 
	 * 需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transaction(JobExecutionContext context) throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection conn = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource =  DataSourceListUtil.findInstance().findLocal()	;
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			conn = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源conn=" + conn);
			if (conn == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(conn);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				// 启动事务
				jdbcUtil.doTransactionStart(conn);
				String returnStr = executeTransaction(context);
				// 事务提交
				jdbcUtil.doTransactionCommit(conn);
				return returnStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(conn);
			}
			// 重新抛出异常
			throw e;
		} finally {
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (conn != null) {
				log.trace("用简单数据源 DataSource关闭连接  conn");
				// 用简单数据源 DataSource关闭连接
				conn.close();
				// jdbcDataSource.connectionClose(conn);
				// 从ThreadLocal中remove连接
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			}
		}
	}
}
