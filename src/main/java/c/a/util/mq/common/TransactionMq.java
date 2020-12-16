package c.a.util.mq.common;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.Connection;
/**
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public abstract class TransactionMq implements MessageListener  {
	protected Logger log = LogManager.getLogger(TransactionMq.class);
	public boolean transaction = false;
	public boolean database = false;
	public abstract String executeTransaction(	TextMessage textMessage) throws Exception;
	@Override
	public void onMessage(Message message) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		TextMessage textMessage = (TextMessage) message;
		try {
			if (database) {
				if (transaction) {
					// 需要事务
					this.transaction(textMessage);
				} else {
					// 不需要事务
					this.transactionNot(textMessage);
				}
			} else {
				if (transaction) {
					// 需要事务
					this.transaction(textMessage);
				} else {
					// 不需要数据库操作
					this.databaseNot(textMessage);
				}
			}
		} catch (Exception e) {
			log.error("定时出错", e);
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String databaseNot(	TextMessage textMessage) throws Exception {
		String returnStr = this.executeTransaction(textMessage);
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public String transactionNot(TextMessage textMessage) throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection connection = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			connection = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源connection=" + connection);
			log.trace("启动数据源connection.hashCode()=" +connection.hashCode());
			if (connection == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(connection);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				String returnStr = executeTransaction(textMessage);
				return returnStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 重新抛出异常
			throw e;
		} finally {
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (connection != null) {
				// 用简单数据源 DataSource关闭连接
				log.trace("数据源 DataSource关闭连接connection.hashCode()="+connection.hashCode());
				connection.close();
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
	public String transaction(TextMessage textMessage) throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection connection = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			connection = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源connection=" + connection);
			log.trace("启动数据源connection.hashCode()=" +connection.hashCode());
			if (connection == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(connection);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				// 启动事务
				jdbcUtil.doTransactionStart(connection);
				String returnStr = executeTransaction(textMessage);
				// 事务提交
				jdbcUtil.doTransactionCommit(connection);
				return returnStr;
			}
		} catch (Exception e) {
			log.error("系统异常:",e);
			if (connection != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(connection);
			}
			// 重新抛出异常
			throw e;
		} finally {
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (connection != null) {
				// 用简单数据源 DataSource关闭连接
				log.trace("数据源 DataSource关闭连接connection.hashCode()="+connection.hashCode());
				connection.close();
				// jdbcDataSource.connectionClose(conn);
				// 从ThreadLocal中remove连接
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			}
		}
	}
}
