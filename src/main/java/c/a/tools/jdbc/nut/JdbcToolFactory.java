package c.a.tools.jdbc.nut;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.h2.JdbcH2Tool;
import c.a.tools.jdbc.mysql.JdbcMysqlTool;
import c.a.tools.jdbc.oracle.JdbcOracleTool;
import c.a.tools.jdbc.sqlserver.JdbcSqlServerTool;
import c.a.util.core.typeconst.TypeDatabaseConst;
/**
 * 
 * <pre>
 * 描述：工厂模式
 * 构建组：
 * 作者：cjx
 * 邮箱:
 * 日期:2016年3月3日-下午3:31:38
 * 版权：
 * </pre>
 */
public class JdbcToolFactory {
	public static IJdbcTool createApi(Connection conn,String url, String username,
			String password) throws Exception {
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcTool api = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				api = new JdbcMysqlTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				api = new JdbcOracleTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				api = new JdbcSqlServerTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		} else {
			api.getJdbcUtil().setConnection(conn);
		}
		return api;
	}
	public static IJdbcTool createApi(Connection conn) throws SQLException {
		DatabaseMetaData databaseMetaData = conn.getMetaData();
		String url = databaseMetaData.getURL();
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcTool api = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				api = new JdbcMysqlTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				api = new JdbcOracleTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				api = new JdbcSqlServerTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		} else {
			api.getJdbcUtil().setConnection(conn);
		}
		return api;
	}
	public static IJdbcTool createApi(String url) {
		String dbType = TypeDatabaseConst.findDbType(url);
		IJdbcTool api = null;
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				api = new JdbcMysqlTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				api = new JdbcOracleTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				api = new JdbcSqlServerTool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.H2)) {
				api = new JdbcH2Tool();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		}
		return api;
	}
}