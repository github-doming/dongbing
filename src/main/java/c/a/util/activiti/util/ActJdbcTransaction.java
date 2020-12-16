package c.a.util.activiti.util;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
/**
 * 
 * 自定义事务
 * 
 * @Description:
 * @ClassName: ActJdbcTransaction
 * @date 2018年7月13日 下午5:51:34
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class ActJdbcTransaction extends JdbcTransaction {
	protected static Logger log = LogManager.getLogger(ActJdbcTransaction.class);
	public ActJdbcTransaction(Connection connection) {
		super(connection);
	}
	public ActJdbcTransaction(DataSource ds, TransactionIsolationLevel desiredLevel, boolean desiredAutoCommit) {
		super(ds, desiredLevel, desiredAutoCommit);
	}
	@Override
	protected void openConnection() throws SQLException {
		try {
			// DataSourceUtil jdbcDataSource =
			// DataSourceListUtil.findInstance().findLocal();
			// connection = jdbcDataSource.findConnection();
			IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
			IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
			connection = jdbcUtil.getConnection();
			if (log.isDebugEnabled()) {
				// log.trace("activiti工作流 open Connection=" + connection);
				//log.trace("activiti工作流{CustomJdbcTransaction } Openning JDBC Connection=" + connection.hashCode() + "[]"+ autoCommmit);
			}
			// connection = dataSource.getConnection();
			if (level != null) {
				connection.setTransactionIsolation(level.getLevel());
			}
			setDesiredAutoCommit(autoCommmit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void close() throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		connection = jdbcUtil.getConnection();
		if (log.isDebugEnabled()) {
			//log.trace("activiti工作流 close Connection=" + connection);
			//log.trace("activiti工作流{CustomJdbcTransaction } closing JDBC Connection=" + connection.hashCode());
		}
		// super.close();
	}
	@Override
	public void commit() throws SQLException {
		// super.commit();
	}
	@Override
	public void rollback() throws SQLException {
		// super.rollback();
	}
}
