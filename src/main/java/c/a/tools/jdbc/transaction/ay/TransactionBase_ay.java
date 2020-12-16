package c.a.tools.jdbc.transaction.ay;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.bean.BeanUtil;
import c.a.util.core.data_source.DataSourceUtil;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.date.DateUtil;
import c.a.util.core.enums.EnumThreadLocal;
import c.a.util.core.enums.EnumUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.log.LogConfig;
import c.a.util.core.path.PathUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.x.platform.root.boot.BootServlet;
import c.a.config.ProjectConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocalSlave;
import c.a.tools.jdbc.transaction.TransactionBean;
/**
 * 
 * 支持2个数据库;
 * @Description: 
 * @ClassName: TransactionBase_ay 
 * @date 2018年6月27日 下午12:02:22 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class TransactionBase_ay {
	protected Logger log = LogManager.getLogger(TransactionBase_ay.class);
	DataSourceUtil jdbcDataSource = null;
	Connection conn = null;
	IJdbcUtil jdbcUtil = null;
	DataSourceUtil jdbcDataSourceSlave = null;
	Connection connSlave = null;
	IJdbcUtil jdbcUtilSlave = null;
	long startCalendarLong = 0;
	/**
	 * 
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@Before
	public void start() throws Exception {
		// 时间
		Calendar startCalendar = Calendar.getInstance();
		startCalendarLong = startCalendar.getTimeInMillis();
		// 路径
		PathUtil pathUtil = new PathUtil();
		System.setProperty("webapp.root", "d:\\");
		try {
			PropertyConfigurator.configure(pathUtil.findPath(LogConfig.url));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// bean
		EnumUtil enumUtil = new EnumUtil();
		EnumThreadLocal.findThreadLocal().set(enumUtil);
		DateUtil dateUtil = new DateUtil();
		DateThreadLocal.findThreadLocal().set(dateUtil);
		BeanUtil beanUtil = new BeanUtil();
		BeanThreadLocal.findThreadLocal().set(beanUtil);
		JsonUtil jsonUtil =new JsonUtil();
		JsonThreadLocal.findThreadLocal().set(jsonUtil);
		ReflectUtil reflectUtil = new ReflectUtil();
		ReflectThreadLocal.findThreadLocal().set(reflectUtil);
	}
	/**
	 * 
	 * 需要事务
	 * 
	 * @return
	 * @throws Exception
	 */
	@After
	public void end() {
		// bean
		EnumThreadLocal.findThreadLocal().remove();
		DateThreadLocal.findThreadLocal().remove();
		BeanThreadLocal.findThreadLocal().remove();
		JsonThreadLocal.findThreadLocal().remove();
		ReflectThreadLocal.findThreadLocal().remove();
		// 时间
		Calendar endCalendar = Calendar.getInstance();
		long endCalendarLong = endCalendar.getTimeInMillis();
		long ms = endCalendarLong - startCalendarLong;
		// mysql 花费时间spend time=710
		log.trace("花费时间spend time=" + ms);
		log.trace("end TransactionTest");
	}
	/**
	 * 
	 * 事务开始
	 * 
	 * @return
	 * @throws Exception
	 */
	public TransactionBean transactionStart() throws Exception {
		TransactionBean transactionBean = new TransactionBean();
		try {
			// 得到连接
			// 用简单数据源 DataSource
			jdbcDataSource = DataSourceListUtil.findInstance().findLocal();
			conn = jdbcDataSource.findConnection();
			log.trace("启动数据源jdbcDataSource=" + jdbcDataSource);
			log.trace("启动数据源conn=" + conn);
			jdbcDataSourceSlave = DataSourceListUtil.findInstance().findDataSource(ProjectConfig.commSlaveTag);
			connSlave = jdbcDataSourceSlave.findConnection();
			log.trace("启动数据源jdbcDataSourceSlave=" + jdbcDataSourceSlave);
			log.trace("启动数据源conTbank=" + connSlave);
			if (conn == null) {
				throw new java.lang.RuntimeException("找不到数据库连接");
			} else {
				// 保存连接到ThreadLocal
				IJdbcTool jdbcTool = JdbcToolFactory.createApi(conn);
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				jdbcUtil = jdbcTool.getJdbcUtil();
				JdbcThreadLocal.findJdbcToolThreadLocal().set(jdbcTool);
				IJdbcTool jdbcToolSlave = JdbcToolFactory.createApi(connSlave);
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				jdbcUtilSlave = jdbcToolSlave.getJdbcUtil();
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().set(jdbcToolSlave);
				// 启动事务
				jdbcUtil.doTransactionStart(conn);
				jdbcUtilSlave.doTransactionStart(connSlave);
				log.trace("conn start=" + conn);
				log.trace("conn.getAutoCommit()=" + conn.getAutoCommit());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionBean.setConn(conn);
		transactionBean.setConnSlave(connSlave);
		return transactionBean;
	}
	/**
	 * 
	 * 事务Commit
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionEnd() {
		try {
			log.trace("conn end=" + conn);
			log.trace("conn.getAutoCommit()=" + conn.getAutoCommit());
			// 事务提交
			jdbcUtil.doTransactionCommit(conn);
			jdbcUtilSlave.doTransactionCommit(connSlave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 事务roll
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionRoll() {
		try {
			if (conn != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(conn);
			}
			if (connSlave != null) {
				// 事务回滚
				jdbcUtil.doTransactionRollback(connSlave);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 关闭连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public void transactionClose() {
		try {
			log.trace("conn end=" + conn);
			log.trace("conn.getAutoCommit()=" + conn.getAutoCommit());
			// 如果连接可用则放回去,不可用则弃用
			// 连接回收，用简单数据源 DataSource回收连接
			if (conn != null) {
				// 用简单数据源 DataSource关闭连接
				conn.close();
				log.trace("用简单数据源 DataSource关闭连接 conn");
				// 从ThreadLocal中remove连接
				JdbcThreadLocal.findJdbcToolThreadLocal().remove();
			}
			if (connSlave != null) {
				// 用简单数据源 DataSource关闭连接
				connSlave.close();
				log.trace("用简单数据源 DataSource关闭连接connSlave");
				// 从ThreadLocal中remove连接
				JdbcThreadLocalSlave.findJdbcToolThreadLocal().remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
