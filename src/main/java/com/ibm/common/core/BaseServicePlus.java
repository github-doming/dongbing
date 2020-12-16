package com.ibm.common.core;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.DatabaseBaseMethod;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
/**
 * @Description: 数据库操作基类扩张类BaseService
 * @Author: Dongming
 * @Date: 2018-12-04 14:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseServicePlus<T> extends BaseServiceProxy implements DatabaseBaseMethod {

	/**
	 * 查询是否存在表
	 * @param tableName 表名称
	 * @return 存在 true
	 * @throws SQLException 查找表错误
	 */
	public boolean hasTable(String tableName) throws SQLException {
		Connection conn = getConnection();
		DatabaseMetaData dbMetaData = conn.getMetaData();
		String[] types = {"TABLE"};
		try (ResultSet tabs = dbMetaData.getTables(null, null, tableName, types)){
			if (tabs.next()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 在任务执行的时候加入事物
	 *
	 * @param task 任务
	 * @return 任务执行结果
	 */
	public T transaction(final Callable<T> task) throws SQLException {
		Connection conn = beginTransaction();
		T result = null;
		try {
			result = task.call();
		} catch (Exception e) {
			rollTransaction(conn);
		} finally {
			endTransaction(conn);
		}
		return result;
	}

	/**
	 * 在任务执行的时候加入事物<br>
	 * 同一类名不能同时运行
	 *
	 * @param clazz 锁类
	 * @param task  任务
	 * @return 任务执行结果
	 */
	public T transactionSyn(final Class clazz, final Callable<T> task) throws SQLException {
		synchronized (clazz) {
			return transaction(task);
		}
	}

	/**
	 * 在任务执行的时候加入事物
	 *
	 * @param task 任务
	 * @return 任务执行结果
	 */
	public T transactionSyn(final Callable<T> task) throws SQLException {
		return transactionSyn(this.getClass(), task);
	}

	/**
	 * 在任务执行的时候剔除事物
	 *
	 * @param task 任务
	 * @return 任务执行结果
	 */
	public T noTransaction(final Callable<T> task) throws Exception {
		endTransaction();
		T result = task.call();
		beginTransaction();
		return result;
	}

	/**
	 * 事务提交
	 */
	protected void commitTransactionCommit() throws SQLException {
		CurrentTransaction.transactionCommit();
	}

	/**
	 * 获取JDBC链接
	 * @return JDBC链接
	 */
	private Connection getConnection() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		return jdbcUtil.getConnection();
	}

	/**
	 * 开始事物
	 *
	 * @return 数据库链接
	 */
	private Connection beginTransaction() throws SQLException {
		Connection conn = getConnection();
		beginTransaction(conn, Connection.TRANSACTION_REPEATABLE_READ);
		return conn;
	}

	/**
	 * 结束事务
	 *
	 * @return 数据库链接
	 */
	private Connection endTransaction() throws SQLException {
		Connection conn = getConnection();
		endTransaction(conn);
		return conn;
	}

}
