package c.a.tools.crud.action;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.config.ProjectConfig;
import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocalSlave;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
/**
 * 事务;
 * 2个数据库;
 * 
 * 
 * 
 */
public abstract class TransactionActionList extends MvcAction {
	protected Logger log = LogManager.getLogger(TransactionActionList.class);
	// protected Logger log = LogManager.getLogger(this.getClass());
	public static String driverClassName = null;
	public static String jdbc_url = null;
	public static String jdbc_username = null;
	public static String jdbc_password = null;
	public boolean transaction = false;
	public boolean database = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeTransaction() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeMvc() throws Exception {
		if (database) {
			if (transaction) {
				// 需要事务
				return this.transaction();
			} else {
				// 不需要事务
				return this.transactionNot();
			}
		} else {
			if (transaction) {
				// 需要事务
				return this.transaction();
			} else {
				// 不需要数据库操作
				return this.databaseNot();
			}
		}
	}
	/**
	 * 
	 * 不需要数据库操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean databaseNot() throws Exception {
		JsonTcpBean returnStr = this.executeTransaction();
		return returnStr;
	}
	/**
	 * 
	 * 不需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean transactionNot() throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection conn = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		DataSourceUtil jdbcDataSourceSlave = null;
		Connection connSlave = null;
		IJdbcTool jdbcToolSlave = null;
		IJdbcUtil jdbcUtilSlave = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			jdbcDataSourceSlave = DataSourceListUtil.findInstance().findDataSource(ProjectConfig.commSlaveTag);
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			jdbcToolSlave = JdbcToolFactory.createApi(jdbcDataSourceSlave.getUrl());
			jdbcUtilSlave = jdbcToolSlave.getJdbcUtil();
			conn = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源conn=" + conn);
			connSlave = jdbcDataSourceSlave.findConnection();
			log.trace("启动数据源jdbcDataSource Slave=" + jdbcDataSourceSlave);
			log.trace("启动数据源conn Slave=" + connSlave);
			if (conn == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(conn);
				jdbcUtilSlave.setConnection(connSlave);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				JsonTcpBean returnStr = executeTransaction();
				return returnStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
			if (connSlave != null) {
				log.trace("用简单数据源 DataSource关闭连接  Slave");
				// 用简单数据源 DataSource关闭连接
				connSlave.close();
				// jdbcDataSourceSlave.connectionClose(connSlave);
				// 从ThreadLocal中remove连接
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().remove();
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
	public JsonTcpBean transaction() throws Exception {
		DataSourceUtil jdbcDataSource = null;
		Connection conn = null;
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		DataSourceUtil jdbcDataSourceSlave = null;
		Connection connSlave = null;
		IJdbcTool jdbcToolSlave = null;
		IJdbcUtil jdbcUtilSlave = null;
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			jdbcDataSourceSlave = DataSourceListUtil.findInstance().findDataSource(ProjectConfig.commSlaveTag);
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			jdbcToolSlave = JdbcToolFactory.createApi(jdbcDataSourceSlave.getUrl());
			jdbcUtilSlave = jdbcToolSlave.getJdbcUtil();
			conn = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源conn=" + conn);
			connSlave = jdbcDataSourceSlave.findConnection();
			log.trace("启动数据源jdbcDataSource Slave=" + jdbcDataSourceSlave);
			log.trace("启动数据源conn Slave=" + connSlave);
			if (conn == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(conn);
				jdbcUtilSlave.setConnection(connSlave);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				// 启动事务
				jdbcUtil.doTransactionStart(conn);
				jdbcUtilSlave.doTransactionStart(connSlave);
				JsonTcpBean returnStr = executeTransaction();
				// 事务提交
				jdbcUtil.doTransactionCommit(conn);
				jdbcUtilSlave.doTransactionCommit(connSlave);
				return returnStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(conn);
			}
			if (connSlave != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(connSlave);
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
			if (connSlave != null) {
				log.trace("用简单数据源 DataSource关闭连接  Slave");
				// 用简单数据源 DataSource关闭连接
				connSlave.close();
				// jdbcDataSourceSlave.connectionClose(connSlave);
				// 从ThreadLocal中remove连接
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().remove();
			}
		}
	}
	public String findPK(Object object) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get().find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine),
				"");
		if (StringUtil.isNotBlank(machine_key)) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			return pk;
		} else {
			return null;
		}
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
