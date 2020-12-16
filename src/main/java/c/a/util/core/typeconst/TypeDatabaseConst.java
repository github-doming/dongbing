package c.a.util.core.typeconst;
/**
 * 
 * <pre>
 * 描述：通过JDBC连接URL，取得数据库类型
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
public class TypeDatabaseConst {
	public static String ORACLE = "oracle";
	public static String MYSQL = "mysql";
	public static String SQLSERVER = "mssql";
	// public static String SQLSERVER_JTDS = "mssql_jtds";
	public static String H2 = "h2";
	public static String SQLITE = "sqlite";
	public static String FIREBIRDSQL = "firebirdsql";
	public static String DB2 = "db2";
	public static String SYBASE = "sybase";
	public static String POSTGRESQL = "postgresql";
	public static String DERBY = "derby";
	public static String HSQL = "hsql";
	public static String HBASE = "hbase";
	public static String HIVE = "hive";
	public static String JTDS = "jtds";
	public static String MOCK = "mock";
	public static String MCKOI = "mckoi";
	public static String CLOUDSCAPE = "cloudscape";
	public static String INFORMIX = "informix";
	public static String TIMESTEN = "timesten";
	public static String AS400 = "as400";
	public static String SAPDB = "sapdb";
	public static String JSQLCONNECT = "JSQLConnect";
	public static String JTURBO = "JTurbo";
	public static String INTERBASE = "interbase";
	public static String POINTBASE = "pointbase";
	public static String EDBC = "edbc";
	public static String MIMER = "mimer";
	public static String DM = "dm";
	private static String INGRES = "ingres";
	/**
	 * 通过JDBC连接URL，取得数据库类型
	 * 
	 * @param url
	 * @return
	 */
	public static String findDbType(String url) {
		if (url == null) {
			return null;
		}
		if (url.startsWith("jdbc:mysql:")) {
			return MYSQL;
		}
		if (url.startsWith("jdbc:oracle:")) {
			return ORACLE;
		}
		if (url.startsWith("jdbc:microsoft:")
				|| url.startsWith("jdbc:sqlserver:")) {
			return SQLSERVER;
		}
		if (url.startsWith("jdbc:jtds:sqlserver:")) {
			return SQLSERVER;
		}
		if (url.startsWith("jdbc:h2:")) {
			return H2;
		}
		if (url.startsWith("jdbc:sqlite:")) {
			return SQLITE;
		}
		if (url.startsWith("jdbc:firebirdsql:")) {
			return FIREBIRDSQL;
		}
		if (url.startsWith("jdbc:db2:")) {
			return DB2;
		}
		if (url.startsWith("jdbc:sybase:Tds:")) {
			return SYBASE;
		}
		if (url.startsWith("jdbc:derby:")) {
			return DERBY;
		}
		if (url.startsWith("jdbc:jtds:")) {
			return JTDS;
		}
		if (url.startsWith("jdbc:fake:") || url.startsWith("jdbc:mock:")) {
			return MOCK;
		}
		if (url.startsWith("jdbc:postgresql:")) {
			return POSTGRESQL;
		}
		if (url.startsWith("jdbc:hsqldb:")) {
			return HSQL;
		}
		if (url.startsWith("jdbc:ingres:")) {
			return INGRES;
		}
		if (url.startsWith("jdbc:mckoi:")) {
			return MCKOI;
		}
		if (url.startsWith("jdbc:cloudscape:")) {
			return CLOUDSCAPE;
		}
		if (url.startsWith("jdbc:informix-sqli:")) {
			return INFORMIX;
		}
		if (url.startsWith("jdbc:timesten:")) {
			return TIMESTEN;
		}
		if (url.startsWith("jdbc:as400:")) {
			return AS400;
		}
		if (url.startsWith("jdbc:sapdb:")) {
			return SAPDB;
		}
		if (url.startsWith("jdbc:JSQLConnect:")) {
			return JSQLCONNECT;
		}
		if (url.startsWith("jdbc:JTurbo:")) {
			return JTURBO;
		}
		if (url.startsWith("jdbc:interbase:")) {
			return INTERBASE;
		}
		if (url.startsWith("jdbc:pointbase:")) {
			return POINTBASE;
		}
		if (url.startsWith("jdbc:edbc:")) {
			return EDBC;
		}
		if (url.startsWith("jdbc:mimer:multi1:")) {
			return MIMER;
		}
		if (url.startsWith("jdbc:dm:")) {
			return DM;
		} else {
			return null;
		}
	}
}
