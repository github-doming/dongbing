package c.a.util.core.jdbc.nut;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import c.a.util.core.jdbc.h2.JdbcH2Util;
import c.a.util.core.jdbc.mysql.JdbcMysqlUtil;
import c.a.util.core.jdbc.oracle.JdbcOracleUtil;
import c.a.util.core.jdbc.sqlserver.JdbcSqlServerUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
/**
 * 
 * <pre>
 * 描述：创建数据库api
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
public class JdbcFactory {
	public static IJdbcUtil createApi(String url, String username,
			String password) throws Exception {
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcUtil jdbcUtil = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				jdbcUtil = new JdbcMysqlUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.H2)) {
				jdbcUtil = new JdbcH2Util();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				jdbcUtil = new JdbcOracleUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				jdbcUtil = new JdbcSqlServerUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		} else {
			Connection conn = jdbcUtil.openConnection(url, username, password);
			jdbcUtil.setConnection(conn);
		}
		return jdbcUtil;
	}
	public static IJdbcUtil createApi(Connection conn) throws SQLException {
		DatabaseMetaData databaseMetaData = conn.getMetaData();
		String url = databaseMetaData.getURL();
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcUtil jdbcUtil = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				jdbcUtil = new JdbcMysqlUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.H2)) {
				jdbcUtil = new JdbcH2Util();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				jdbcUtil = new JdbcOracleUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				jdbcUtil = new JdbcSqlServerUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		} else {
			jdbcUtil.setConnection(conn);
		}
		return jdbcUtil;
	}
	public static IJdbcUtil createApi(String url) {
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcUtil jdbcUtil = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				jdbcUtil = new JdbcMysqlUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.H2)) {
				jdbcUtil = new JdbcH2Util();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				jdbcUtil = new JdbcOracleUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				jdbcUtil = new JdbcSqlServerUtil();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		}
		return jdbcUtil;
	}
}