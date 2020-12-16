package org.doming.core.common;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-12-07 19:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public interface DatabaseBaseMethod {

	/**
	 * 一个常量，指示不需要事务。
	 */
	 int TRANSACTION_NONE = Connection.TRANSACTION_NONE;

	/**
	 * 开始事物
	 *
	 * @param conn        数据库链接
	 * @param transaction 事物等级
	 * @throws SQLException 开始失败
	 */
	default void beginTransaction(Connection conn, int transaction) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("JDBC链接不能为空");
		}
		if (conn.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
			conn.setTransactionIsolation(transaction);
			conn.setAutoCommit(false);
		}else {
			throw new SQLException("JDBC链接不支持事物，开启事物失败");
		}
	}

	/**
	 * 结束事物
	 *
	 * @param conn 数据库链接
	 * @throws SQLException 结束失败
	 */
	default void endTransaction(Connection conn) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("JDBC链接不能为空");
		}
		if (!conn.getAutoCommit()){
			conn.commit();
			conn.setAutoCommit(true);
		}
	}

	/**
	 * 重启事物
	 *
	 * @param conn        数据库链接
	 * @param transaction 事物等级
	 * @throws SQLException 重启失败
	 */
	default void restartTransaction(Connection conn, int transaction) throws SQLException {
		endTransaction(conn);
		beginTransaction(conn, transaction);
	}

	/**
	 * 回滚事物
	 *
	 * @param conn 数据库链接
	 */
	default void rollTransaction(Connection conn) {
		if (conn == null) {
			throw new RuntimeException("JDBC链接不能为空");
		}
		try {
			if (!conn.getAutoCommit()) {
				conn.rollback();
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
