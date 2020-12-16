package c.a.tools.jdbc.transaction;
import java.sql.Connection;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.nut.JdbcToolFactory;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.data_source.DataSourceListUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
/**
 * 支持1个数据库;
 * 
 * @Description:
 * @date 2018年6月27日 下午12:02:49
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class DatabaseBase extends TransactionBase {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 
	 * 不需要事务;
	 * 
	 * 开始;
	 * 
	 * @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public TransactionBean databaseStart() throws Exception {
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
				// log.trace("conn start=" + conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		transactionBean.setConn(connection);
		return transactionBean;
	}
	/**
	 * 
	 * 不需要事务;
	 * 
	 * 开始;
	 * 
	 * (不需要ThreadLocal)
	 * 
	 * @d
	 * @return
	 * @throws Exception
	 */
	public void databaseStart(IJdbcTool jdbcToolLocal) throws Exception {
		IJdbcUtil jdbcUtilLocal = null;
		Connection connectionLocal = null;
		try {
			jdbcUtilLocal = jdbcToolLocal.getJdbcUtil();
			connectionLocal = jdbcUtilLocal.getConnection();
			log.trace("启动数据源conn=" + connectionLocal);
			if (connectionLocal == null) {
				// throw new java.lang.RuntimeException("找不到数据库连接");
			} else {
				// 保存连接到ThreadLocal
				// 保存连接到ThreadLocal
				jdbcUtilLocal.setConnection(connectionLocal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 不需要事务;
	 * 
	 * 关闭连接
	 * 	  @deprecated Thread类不能继承调用,有线程问题;
	 * @return
	 * @throws Exception
	 */
	public void databaseClose() {
		this.transactionClose();
	}
	/**
	 * 
	 * 关闭连接(不需要ThreadLocal)
	 * 
	 * @return
	 * @throws Exception
	 */
	public void databaseClose(IJdbcTool jdbcTool) {
		this.transactionClose(jdbcTool);
	}
}
