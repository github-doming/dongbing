package org.doming.core.common;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.AssertTool;
import org.doming.core.tools.StringTool;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * @Description: 支持多个数据源;
 * @Author: cjx
 * @Author: Dongming
 * @Date: 2019-05-15 15:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TransactionsBase implements DatabaseBaseMethod {
	protected static final Logger log = LogManager.getLogger(TransactionsBase.class);

	protected int transaction = Connection.TRANSACTION_READ_UNCOMMITTED;

	/**
	 * 开始jdbc链接，开始时调用
	 *
	 * @param source 数据源
	 */
	public Connection connectionBegin(String source) {
		Connection connection = null;
		try {
			DataSourceUtil dataSourceUtil;
			if (StringTool.isEmpty(source) || StringTool.match(source, DataSourceListUtil.Local)) {
				source = DataSourceListUtil.Local;
				dataSourceUtil = DataSourceListUtil.findInstance().findLocal();
			} else {
				dataSourceUtil = DataSourceListUtil.findInstance().findDataSource(source);
			}
			log.trace("启动数据源");
			IJdbcTool jdbcTool = JdbcToolFactory.createApi(dataSourceUtil.getUrl());
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			connection = dataSourceUtil.findConnection();
			if (connection == null) {
				throw new RuntimeException("找不到数据库连接");
			} else {
				jdbcUtil.setConnection(connection);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				if (JdbcThreadLocal.findJdbcToolThreadLocal().get() == null){
					synchronized (TransactionsBase.class){
						if (JdbcThreadLocal.findJdbcToolThreadLocal().get() == null){
							JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
						}
					}
				}
			}
			return connection;
		} catch (Exception e) {
			log.error("开启" + source + "数据源失败", e);
		}
		return connection;
	}

	/**
	 * 开启jdbc链接 - 元泵必须要有连接才行
	 *
	 * @param source 数据源
	 * @return 线程中维持的jdbc链接
	 */
	public IJdbcTool connectionStart(String source) {
		IJdbcTool threadJdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		connectionBegin(source);
		return threadJdbcTool;
	}

	/**
	 * 关闭jdbc链接，如果连接可用则放回去,不可用则弃用
	 * 从线程中移除该链接
	 */
	public void connectionEnd() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		try {
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection connection = jdbcUtil.getConnection();
			if (connection != null) {
				connection.close();
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
				log.trace("关闭数据源");
			} else {
				log.error("找不到数据库连接");
			}
		} catch (Exception e) {
			log.error("关闭jdbc链接失败", e);
		}
	}

	/**
	 * 关闭jdbc链接，如果连接可用则放回去,不可用则弃用
	 * 从线程中移除该链接，并将另一个jdbc链接放入线程中
	 *
	 * @param otherJdbcTool 另一个jdbc
	 */
	public void connectionFinish(IJdbcTool otherJdbcTool) {
		//关闭jdbc链接
		connectionEnd();
		//另一个jdbc链接放入线程
		JdbcThreadLocal.findJdbcToolThreadLocal().set(otherJdbcTool);
	}
	/**
	 * 开始事物，开始时调用
	 * 默认数据源
	 */
	protected void transactionBegin() {
		transactionBegin(null);
	}
	/**
	 * 开始事物，开始时调用
	 *
	 * @param source 数据源
	 */
	public void transactionBegin(String source) {
		if (transaction == Connection.TRANSACTION_NONE) {
			throw new RuntimeException("事物等级不能为 TRANSACTION_NONE，transaction=" + transaction);
		}
		//开始链接
		Connection connection = connectionBegin(source);
		try {
			if (connection == null) {
				throw new RuntimeException("找不到数据库连接");
			}
			//开始事物
			beginTransaction(connection, transaction);
		} catch (SQLException e) {
			log.error("开启" + source + "事物失败", e);
		}
	}

	/**
	 * 开启事物
	 *
	 * @param source 数据源
	 * @return 线程中维持的jdbc链接
	 */
	public IJdbcTool transactionStart(String source) {
		//线程中的jdbc
		IJdbcTool threadJdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		//新的jdbc
		transactionBegin(source);
		return threadJdbcTool;
	}

	/**
	 * 关闭事物
	 * 从线程中移除该链接
	 */
	protected void transactionEnd() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		try {
			//结束事物
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection connection = jdbcUtil.getConnection();
			if (connection == null) {
				throw new RuntimeException("找不到数据库连接");
			}
			endTransaction(connection);
			//结束链接
			connectionEnd();
		} catch (SQLException e) {
			log.error("关闭事物失败", e);
		}
	}

	/**
	 * 关闭事物
	 * 从线程中移除该链接，并将另一个jdbc链接放入线程中
	 *
	 * @param otherJdbcTool 另一个jdbc
	 */
	public void transactionFinish(IJdbcTool otherJdbcTool) {
		//关闭事物
		transactionEnd();
		//另一个jdbc链接放入线程
		if (otherJdbcTool != null){
			JdbcThreadLocal.findJdbcToolThreadLocal().set(otherJdbcTool);
		}
	}

	/**
	 * 切换事务
	 * 	链接不关闭，把事务提交后，返回链接
	 * 从线程中移除该链接，并将另一个jdbc链接放入线程中
	 *
	 * @param otherJdbcTool 另一个jdbc
	 * @return 线程中维持的jdbc链接
	 */
	protected IJdbcTool transactionSwitch(IJdbcTool otherJdbcTool) throws SQLException {
		//提交事物
		endTransaction();
		//线程中的jdbc
		IJdbcTool threadJdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();

		//另一个jdbc链接放入线程
		JdbcThreadLocal.findJdbcToolThreadLocal().set(otherJdbcTool);

		return threadJdbcTool;
	}

	/**
	 * 回滚事物
	 */
	public void transactionRoll() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		transactionRoll(jdbcTool);
	}

	/**
	 * 回滚事物
	 *
	 * @param jdbcTool jdbc链接
	 */
	protected void transactionRoll(IJdbcTool jdbcTool) {
		try {
			//回滚事物
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection connection = jdbcUtil.getConnection();
			if (connection == null) {
				throw new RuntimeException("找不到数据库连接");
			}
			rollTransaction(connection);
		} catch (Exception e) {
			log.error("回滚事物失败", e);
		}
	}
	/**
	 * 回滚所有事物
	 *
	 * @param jdbcTool jdbc链接
	 */
	protected void transactionRolls(IJdbcTool jdbcTool) {
		IJdbcTool threadJdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		transactionRoll(threadJdbcTool);
		transactionRoll(jdbcTool);
	}

	/**
	 * 提交所有的事务
	 */
	protected void transactionCommit() throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		AssertTool.notNull(jdbcTool,"不存在事务操作,不可提交事务");
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection connection = jdbcUtil.getConnection();
		AssertTool.notNull(connection,"找不到数据库连接,不可提交事务");
		if (!connection.getAutoCommit()){
			connection.commit();
		}
	}

	/**
	 * 重启事物
	 */
	protected void transactionRestart() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		transactionRestart(jdbcTool);
	}

	/**
	 * 重启事物
	 *
	 * @param jdbcTool jdbc链接
	 */
	protected void transactionRestart(IJdbcTool jdbcTool) {
		if (transaction == 0) {
			throw new RuntimeException("事物等级不能为0，transaction=" + transaction);
		}
		try {
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			Connection connection = jdbcUtil.getConnection();
			if (connection == null) {
				throw new RuntimeException("找不到数据库连接");
			}
			restartTransaction(connection, transaction);
		} catch (SQLException e) {
			log.error("重启事物失败", e);
		}
	}

	/**
	 * 开始事物
	 *
	 * @param transaction 事物等级
	 */
	protected void beginTransaction(int transaction) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		beginTransaction(conn, transaction);
	}
	/**
	 * 开始事物
	 */
	public void beginTransaction() throws SQLException {
		if (transaction == TRANSACTION_NONE){
			beginTransaction(Connection.TRANSACTION_REPEATABLE_READ);
		}else{
			beginTransaction(transaction);
		}
	}
	/**
	 * 关闭事物
	 */
	public void endTransaction() throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		endTransaction(conn);
	}
	/**
	 * 回滚事务
	 */
	protected void rollTransaction() {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		rollTransaction(conn);
	}

	protected void connectionRestart(){
		connectionEnd();
		connectionBegin(null);

	}


	protected void transaction(int transaction) {
		this.transaction = transaction;
	}
}
