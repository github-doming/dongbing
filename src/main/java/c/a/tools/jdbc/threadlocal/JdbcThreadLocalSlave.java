package c.a.tools.jdbc.threadlocal;

import c.a.tools.jdbc.IJdbcTool;

public class JdbcThreadLocalSlave {
	/**
	 * connection
	 */
	private static ThreadLocal<IJdbcTool> JdbcToolThreadLocal = null;

	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<IJdbcTool> findJdbcToolThreadLocal() {
		if (JdbcToolThreadLocal == null) {
			JdbcToolThreadLocal = new ThreadLocal<IJdbcTool>();
		}
		return JdbcToolThreadLocal;
	}

	// -- set与get --//
	public static ThreadLocal<IJdbcTool> getJdbcToolThreadLocal() {
		return JdbcToolThreadLocal;
	}

	public static void setJdbcToolThreadLocal(
			ThreadLocal<IJdbcTool> jdbcToolsThreadLocal) {
		JdbcToolThreadLocal = jdbcToolsThreadLocal;
	}

	// -- set与get --//
}
