package org.doming.core.common;
import org.doming.core.tools.AssertTool;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * @Description: 当前事务
 * @Author: Dongming
 * @Date: 2019-07-01 10:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CurrentTransaction {
	private static final ThreadLocal<TransactionsBase> DATABASE = new ThreadLocal<>();
	private static final ThreadLocal<DaoBase> DAO = new ThreadLocal<>();

	public static TransactionsBase getDataBase() {
		return DATABASE.get();
	}
	public static DaoBase getDao() {
		return DAO.get();
	}

	public static void setDatabase(TransactionsBase transactions) {
		DATABASE.set(transactions);
		DAO.set(new DaoBase());
	}
	public static void removeDataBase() {
		DATABASE.remove();
		DAO.remove();
	}

	//region 啥都没有
	/**
	 * 啥都没有 - 从开启链接开干
	 */
	public static void transactionBegin() {
		TransactionsBase transactions = getDataBase();
		if (transactions == null) {
			transactions = new TransactionsBase() {
			};
			transactions.transaction = Connection.TRANSACTION_READ_UNCOMMITTED;
			setDatabase(transactions);
		}
		transactions.transactionBegin();
	}

	public static void transactionEnd() {
		TransactionsBase transactions = getDataBase();
		if (transactions != null){
			transactions.transactionEnd();
			removeDataBase();
		}
	}
	//endregion

	//region 有链接了
	/**
	 * 有链接了，但是没有事务
	 */
	public static void beginTransaction() throws SQLException {
		TransactionsBase transactions = getDataBase();
		AssertTool.notNull(transactions, "不存在数据库连接,不可开启事务");
		transactions.beginTransaction();
	}
	public static void endTransaction() throws SQLException {
		TransactionsBase transactions = getDataBase();
		AssertTool.notNull(transactions, "不存在数据库连接,不可关闭事务");
		transactions.endTransaction();
	}
	public static void rollTransaction() throws SQLException {
		TransactionsBase transactions = getDataBase();
		AssertTool.notNull(transactions, "不存在数据库连接,不可关闭事务");
		transactions.rollTransaction();
	}
	//endregion


	//region 有事务了
	/**
	 * 事务提交 约等于重启事务
	 */
	public static void transactionCommit() throws SQLException {
		TransactionsBase transactions = getDataBase();
		AssertTool.notNull(transactions, "不存在事务,不可提交事务");
		transactions.transactionCommit();
	}
	/**
	 * 回滚事物
	 */
	public static void transactionRoll() throws SQLException {
		TransactionsBase transactions = getDataBase();
		AssertTool.notNull(transactions, "不存在事务,不可提交事务");
		transactions.transactionRoll();
	}
	//endregion
}
