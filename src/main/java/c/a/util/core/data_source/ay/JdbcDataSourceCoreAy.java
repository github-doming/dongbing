package c.a.util.core.data_source.ay;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public abstract class JdbcDataSourceCoreAy implements DataSource {
	private static DataSource dataSource;

	public void setDataSource(DataSource dataSourceInput) {
		dataSource = dataSourceInput;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return dataSource.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	/**
	 * (non-Javadoc) 该接口方法since 1.6;
	 * 
	 * 不是所有的DataSource都实现有这个方法;
	 * 
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class);
	 * 
	 * @param iface
	 * @return
	 * @throws SQLException
	 */
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return false;
	}

	/**
	 * (non-Javadoc) 该接口方法since 1.6;
	 * 
	 * 不是所有的DataSource都实现有这个方法;
	 * 
	 * @see java.sql.Wrapper#unwrap(java.lang.Class);
	 * 
	 * @param <T>
	 * @param iface
	 * @return
	 * @throws SQLException
	 */
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {

		return null;
	}

	/**
	 * 只支持jdk7以上
	 */
	// @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {

		return null;
	}
}
