package c.a.tools.jdbc.transaction;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import c.a.config.core.CommContextUtil;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.log.LogConfig;
import c.a.util.core.log.LogUtil;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
/**
 * 支持1个数据库;
 * 
 * @Description:
 * @ClassName: TransactionBase
 * @date 2018年6月27日 下午12:02:49
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class TransactionBase {
	protected Logger log = LogManager.getLogger(this.getClass());
	protected DataSourceUtil jdbcDataSource = null;
	protected Connection connection = null;
	protected IJdbcTool jdbcTool = null;
	protected IJdbcUtil jdbcUtil = null;
	protected long startCalendarLong = 0;
	/**
	 * 
	 * 测试开始
	 * 
	 * @return
	 * @throws Exception
	 */
	@Before
	public void startTest() throws Exception {
		CommContextUtil commContextUtil = new CommContextUtil();
		startCalendarLong = commContextUtil.start();
		String servletContextPath="d:\\";
		LogUtil.findInstance().init(servletContextPath);
	}
	public void startTest_v1() throws Exception {
		CommContextUtil commContextUtil = new CommContextUtil();
		startCalendarLong = commContextUtil.start();
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		// 路径
		System.setProperty("webapp.root", "d:\\");
		// System.setProperty("webapp.root", "c:\\");
		try {
			PropertyConfigurator.configure(pathUtil.findPath(LogConfig.url));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 测试结束
	 * 
	 * @return
	 * @throws Exception
	 */
	@After
	public void endTest() {
		CommContextUtil commContextUtil = new CommContextUtil();
		commContextUtil.end(startCalendarLong);
	}
	/**
	 * 
	 * 事务开始
	 * 
	 * @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public TransactionBean transactionStart() throws Exception {
		TransactionBean transactionBean = new TransactionBean();
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			jdbcTool = JdbcToolFactory.createApi(jdbcDataSource.getUrl());
			jdbcUtil = jdbcTool.getJdbcUtil();
			connection = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源connection=" + connection);
			log.trace("启动数据源connection.hashCode()=" + connection.hashCode());
			if (connection == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
				return null;
			} else {
				// 保存连接到ThreadLocal
				// 保存连接到ThreadLocal
				jdbcUtil.setConnection(connection);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				// 启动事务
				jdbcUtil.doTransactionStart(connection);
				// log.trace("conn start=" + conn);
				// log.trace("conn.getAutoCommit()=" +
				// conn.getAutoCommit());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionBean.setConn(connection);
		return transactionBean;
	}
	/**
	 * 
	 * 事务Commit
	 * 
	 * @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public void transactionEnd() {
		try {
			// log.trace("conn end=" + conn);
			// log.trace("conn.getAutoCommit()=" +
			// conn.getAutoCommit());
			// 事务提交
			jdbcUtil.doTransactionCommit(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 事务roll
	 * 
	 * @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public void transactionRoll() {
		try {
			if (connection != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 关闭连接
	 * 
	 * @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public void transactionClose() {
		try {
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (connection != null) {
				// 用简单数据源 DataSource关闭连接
				log.trace("数据源 DataSource关闭连接connection.hashCode()=" + connection.hashCode());
				connection.close();
				// 从ThreadLocal中remove连接
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 提交事务并重启事务
	 * 
	 * @Title: doTransactionPost
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void doTransactionPost() throws Exception {
		IJdbcTool jdbcToolLocal = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
		Connection connectionLocal = jdbcUtilLocal.getConnection();
		jdbcUtilLocal.doTransactionCommit(connectionLocal);
		jdbcUtilLocal.doTransactionStart(connectionLocal);
	}
	/**
	 * 
	 * findJdbcTool(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public IJdbcTool findJdbcTool(String id) throws Exception {
		DataSourceUtil jdbcDataSourceLocal = null;
		IJdbcTool jdbcToolLocal = null;
		IJdbcUtil jdbcUtilLocal = null;
		jdbcDataSourceLocal = DataSourceListUtil.findInstance().findDataSource(id);
		log.trace("启动数据源jdbcDataSourceLocal=" + jdbcDataSourceLocal);
		jdbcToolLocal = JdbcToolFactory.createApi(jdbcDataSourceLocal.getUrl());
		jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
		Connection connectionLocal = jdbcDataSourceLocal.findConnection();
		jdbcUtilLocal.setConnection(connectionLocal);
		// 需要ThreadLocal
		JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcToolLocal);
		return jdbcToolLocal;
	}
	/**
	 * 
	 * findJdbcTool(需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public IJdbcTool findJdbcToolLocal() throws Exception {
		DataSourceUtil jdbcDataSourceLocal = null;
		IJdbcTool jdbcToolLocal = null;
		IJdbcUtil jdbcUtilLocal = null;
		jdbcDataSourceLocal = DataSourceListUtil.findInstance().findLocal();
		log.trace("启动数据源jdbcDataSourceLocal=" + jdbcDataSourceLocal);
		jdbcToolLocal = JdbcToolFactory.createApi(jdbcDataSourceLocal.getUrl());
		jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
		Connection connectionLocal = jdbcDataSourceLocal.findConnection();
		jdbcUtilLocal.setConnection(connectionLocal);
		// 需要ThreadLocal
		JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcToolLocal);
		return jdbcToolLocal;
	}
	/**
	 * 
	 * findJdbcTool(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public IJdbcTool findJdbcToolLocalNotThreadLocal() throws Exception {
		DataSourceUtil jdbcDataSourceLocal = null;
		IJdbcTool jdbcToolLocal = null;
		IJdbcUtil jdbcUtilLocal = null;
		jdbcDataSourceLocal = DataSourceListUtil.findInstance().findLocal();
		log.trace("启动数据源jdbcDataSourceLocal=" + jdbcDataSourceLocal);
		jdbcToolLocal = JdbcToolFactory.createApi(jdbcDataSourceLocal.getUrl());
		jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
		Connection connectionLocal = jdbcDataSourceLocal.findConnection();
		jdbcUtilLocal.setConnection(connectionLocal);
		return jdbcToolLocal;
	}
	/**
	 * 
	 * 事务开始(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionStart(IJdbcTool jdbcToolLocal) throws Exception {
		if (jdbcToolLocal != null) {
			IJdbcUtil jdbcUtil = null;
			Connection conn = null;
			try {
				jdbcUtil = jdbcToolLocal.getJdbcUtil();
				conn = jdbcUtil.getConnection();
				log.trace("启动数据源conn=" + conn);
				if (conn == null) {
					// throw new java.lang.RuntimeException("找不到数据库连接");
				} else {
					// 保存连接到ThreadLocal
					// 保存连接到ThreadLocal
					jdbcUtil.setConnection(conn);
					// 启动事务
					jdbcUtil.doTransactionStart(conn);
					// log.trace("conn start=" + conn);
					// log.trace("conn.getAutoCommit()=" +
					// conn.getAutoCommit());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 事务Commit(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionEnd(IJdbcTool jdbcToolLocal) {
		if (jdbcToolLocal != null) {
			IJdbcUtil jdbcUtilLocal = null;
			Connection connectionLocal = null;
			try {
				jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
				connectionLocal = jdbcUtilLocal.getConnection();
				// log.trace("conn end=" + conn);
				// log.trace("conn.getAutoCommit()=" +
				// conn.getAutoCommit());
				// 事务提交
				jdbcUtilLocal.doTransactionCommit(connectionLocal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 事务roll(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionRoll(IJdbcTool jdbcToolLocal) {
		if (jdbcToolLocal != null) {
			IJdbcUtil jdbcUtilLocal = null;
			Connection connectionLocal = null;
			try {
				jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
				connectionLocal = jdbcUtilLocal.getConnection();
				if (connectionLocal != null) {
					// 事务回滚
					jdbcUtilLocal.doTransactionRollback(connectionLocal);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 关闭连接(需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionClose(IJdbcTool jdbcToolLocal) {
		if (jdbcToolLocal != null) {
			IJdbcUtil jdbcUtilLocal = null;
			Connection connectionLocal = null;
			try {
				jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
				connectionLocal = jdbcUtilLocal.getConnection();
				// 如果连接可用则放回去,不可用则弃用
				// 连接回收，用简单数据源 DataSource回收连接
				if (connectionLocal != null) {
					// 用简单数据源 DataSource关闭连接
					connectionLocal.close();
					// 从ThreadLocal中remove连接
					JdbcThreadLocal.findJdbcToolThreadLocal().remove();
					jdbcToolLocal = null;
					jdbcUtilLocal = null;
					connectionLocal = null;
					log.trace("用简单数据源 DataSource关闭连接 conn");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 关闭连接(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionCloseNotThreadLocal(IJdbcTool jdbcToolLocal) {
		if (jdbcToolLocal != null) {
			IJdbcUtil jdbcUtilLocal = null;
			Connection connectionLocal = null;
			try {
				jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
				connectionLocal = jdbcUtilLocal.getConnection();
				// 如果连接可用则放回去,不可用则弃用
				// 连接回收，用简单数据源 DataSource回收连接
				if (connectionLocal != null) {
					// 用简单数据源 DataSource关闭连接
					connectionLocal.close();
					log.trace("用简单数据源 DataSource关闭连接 conn");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
