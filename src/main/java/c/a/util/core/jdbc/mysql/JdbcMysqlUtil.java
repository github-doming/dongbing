package c.a.util.core.jdbc.mysql;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import c.a.util.core.jdbc.bean.create.AlterTableBean;
import c.a.util.core.jdbc.bean.create.CreateTableBean;
import c.a.util.core.jdbc.bean.create.CreateTableListBean;
import c.a.util.core.jdbc.bean.create.RowBean;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomBean;
import c.a.util.core.jdbc.bean.sql_custom.TypeOperateBean;
import c.a.util.core.jdbc.bean.data_row.JdbcDataDto;
import c.a.util.core.jdbc.bean.data_row.JdbcRowDto;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.DatabaseBean;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.nut.JdbcNutUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
/**
 * 
 * @author cxy
 * @Email
 * 
 */
public class JdbcMysqlUtil extends JdbcNutUtil implements IJdbcUtil {
	String dbType = TypeDatabaseConst.MYSQL;
	// public String driver = "com.mysql.jdbc.Driver";
	public String driver = "com.mysql.cj.jdbc.Driver";
	// 查询所有的表
	private String sqlTable = "select t.table_schema,t.table_name,t.table_comment,t.TABLE_TYPE,t.AUTO_INCREMENT  from information_schema.tables t where t.table_type='BASE TABLE'";
	// 查询所有的视图
	private String sqlView = "select t.table_schema,t.table_name,t.table_comment,t.TABLE_TYPE,t.AUTO_INCREMENT FROM information_schema.TABLES t WHERE t.TABLE_TYPE LIKE 'VIEW'";
	// 查询所有的列
	private String sqlColumn = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS  ";
	// 查询所有的表约束
	private String sqlTableConstraint = "SELECT CONSTRAINT_NAME,TABLE_NAME,CONSTRAINT_TYPE   FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS";
	// 查询所有的列约束
	@SuppressWarnings("unused")
	private String sqlColumnConstraint = "select CONSTRAINT_NAME,TABLE_NAME,COLUMN_NAME from information_schema.key_column_usage";
	/**
	 * 1返回数据库类型
	 * 
	 * @Description:
	 * 
	 * @Title: getDbType
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	@Override
	public String getDbType() {
		return dbType;
	}
	/**
	 * 2设置数据库类型
	 * 
	 * @Description:
	 * 
	 * @Title: setDbType
	 * 
	 * @param dbType 参数说明
	 * 
	 * @throws
	 */
	@Override
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	/**
	 * 3返回驱动
	 * 
	 * @Description:
	 * 
	 * @Title: getDriver
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	public String getDriver() {
		return driver;
	}
	/**
	 * 4 设置驱动
	 * 
	 * @Description:
	 * 
	 * @Title: setDriver
	 * 
	 * @param driverInput 参数说明
	 * 
	 * @throws
	 */
	public void setDriver(String driverInput) {
		driver = driverInput;
	}
	/**
	 * 5 得到数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: getConnection
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	public Connection getConnection() {
		return super.getConnection();
	}
	/**
	 * 6设置数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: setConnection
	 * 
	 * @param connection 参数说明
	 * 
	 * @throws
	 */
	public void setConnection(Connection connection) {
		super.setConnection(connection);
	}
	/**
	 * 7打开并得到数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: openConnectionCore
	 * 
	 * @param url
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public Connection openConnectionCore(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		return super.openConnectionCore(driver, url, username, password);
	}
	/**
	 * 8打开并得到数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: openConnectionCore
	 * 
	 * @param driver
	 * 
	 * @param url
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public Connection openConnectionCore(String driver, String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		return super.openConnectionCore(driver, url, username, password);
	}
	/**
	 * 
	 * 9打开并得到数据库的连接 @
	 * 
	 */
	@Override
	public Connection openConnection(String url, String user, String password) throws Exception {
		// String driver = "org.gjt.mm.mysql.Driver";
		return super.openConnection(driver, url, user, password);
	}
	/**
	 * 
	 * 10 打开并得到数据库的连接
	 */
	@Override
	public Connection openConnection(String driver, String url, String username, String password) throws Exception {
		return super.openConnection(driver, url, username, password);
	}
	/**
	 * 11关闭数据库连接
	 * 
	 * @param conn
	 */
	public void closeConnection(Connection conn) throws SQLException {
		super.closeConnection(conn);
	}
	/**
	 * 12关闭PreparedStatement
	 * 
	 */
	public void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		super.closePreparedStatement(preparedStatement);
	}
	/**
	 * 13关闭Statement
	 * 
	 * @param statement
	 */
	public void closeStatement(Statement statement) throws SQLException {
		super.closeStatement(statement);
	}
	/**
	 * 14关闭ResultSet
	 * 
	 * @param rs
	 */
	public void closeResultSet(ResultSet resultSet) throws SQLException {
		super.closeResultSet(resultSet);
	}
	/**
	 * 15 关闭Statement
	 * 
	 * 关闭ResultSet
	 * 
	 * @param rs
	 * @param statement
	 * @param conn
	 */
	public void close(ResultSet resultSet, Statement statement, Connection conn) throws SQLException {
		super.close(resultSet, statement, conn);
	}
	/**
	 * 16 关闭ResultSet
	 * 
	 * 关闭PreparedStatement
	 * 
	 */
	public void close(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
		super.close(resultSet, preparedStatement);
	}
	/**
	 * 17 关闭Statement
	 * 
	 * @param statement
	 * @param conn
	 */
	public void close(Statement statement, Connection conn) throws SQLException {
		super.close(statement, conn);
	}
	/**
	 * 18设置事务隔离级别(没有注释)
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	@Override
	public void doTransactionStart(Connection conn) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		conn.setAutoCommit(false);
		if (conn.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		}
	}
	/**
	 * 19提交事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doTransactionCommit(Connection conn) throws SQLException {
		super.doTransactionCommit(conn);
	}
	/**
	 * 20回滚事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doTransactionRollback(Connection conn) throws SQLException {
		super.doTransactionRollback(conn);
	}
	/**
	 * 21执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public String execute2PK(Connection conn, String sql) throws SQLException {
		return super.execute2PK(conn, sql);
	}
	/**
	 * 22执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public String execute2PK(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.execute2PK(conn, sql, parameterList);
	}
	/**
	 * 
	 * 23执行insert,delete,update语句(返回影响的行数)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @throws SQLException
	 */
	public int execute(Connection conn, String sql) throws SQLException {
		return super.execute(conn, sql);
	}
	/**
	 * 
	 * 24执行insert,delete,update语句(返回影响的行数)
	 * 
	 * @Description: desc
	 * @Title: execute
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return return
	 * @throws SQLException
	 *             参数说明
	 * @throws throws
	 */
	public int execute(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.execute(conn, sql, parameterList);
	}
	/**
	 * 25 一,执行Insert语句，得到主键;
	 * 
	 * 二,如果主键是自动增长的数字，将得到主键;
	 * 
	 * 三,打印sql;
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String executeInsert(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.executeInsert(conn, sql, parameterList);
	}
	/**
	 * 26 一,执行Insert语句，得到主键;
	 * 
	 * 二,如果主键是自动增长的数字，将得到主键;
	 * 
	 * 三,打印sql;
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String executeInsert(boolean isPrintOriginalSql, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.executeInsert(isPrintOriginalSql, conn, sql, parameterList);
	}
	/**
	 * 27根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0或1开始
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public int findStart(int pageIndex, int pageSize) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		if (pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
		return ((pageIndex - 1) * pageSize);
	}
	/**
	 * 28 获得每页的记录数量,默认为Integer.MAX_VALU.
	 * 
	 * @param pageSize
	 * @return
	 */
	public int findLimit(int pageSize) {
		if (pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
		return pageSize;
	}
	/**
	 * 29 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public int findEnd(int pageNo, int pageSize) {
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize < 1) {
			pageSize = Integer.MAX_VALUE;
		}
		int start = ((pageNo - 1) * pageSize);
		int end = start + pageSize - 1;
		return end;
	}
	/**
	 * 
	 * 30找出最终解析后的sql
	 * 
	 * @param sqlOriginal
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	public String findSqlTarget(String sqlOriginal, List<Object> parameterList) {
		return super.findSqlTarget(sqlOriginal, parameterList);
	}
	/**
	 * 31 解析并打印最终的sql
	 * 
	 * @param sqlOriginal
	 * @param parameterList
	 * @throws Exception
	 */
	public void doPrintSqlTarget(String fun, String sqlOriginal, List<Object> parameterList) {
		super.doPrintSqlTarget(fun, sqlOriginal, parameterList);
	}
	/**
	 * 
	 * 32查找分页sql
	 * 
	 * @Description:
	 * 
	 * @Title: findSqlPage
	 * 
	 * @param @param sql
	 * 
	 * @param @param start
	 * 
	 * @param @param end
	 * 
	 * @param @param pageIndex
	 * 
	 * @param @param pageSize
	 * 
	 * @param @return 参数说明
	 * 
	 * @throws
	 */
	public String findSqlPage(String sql, long start, long end, long pageIndex, long pageSize) {
		StringBuilder sb = new StringBuilder();
		String sqlReturn = null;
		sqlReturn = sb.append(sql).append(" limit ").append(start).append(",").append(pageSize).toString();
		return sqlReturn;
	}
	/**
	 * 
	 * 33查找统计总数的sql
	 * 
	 * @Description:
	 * 
	 * @Title: findSqlCount
	 * 
	 * @param @param sql
	 * 
	 * @param @param start
	 * 
	 * @param @param limit
	 * 
	 * @param @return 参数说明
	 * 
	 * @throws
	 */
	@Override
	public String findSqlCount(String sql, long start, long limit) {
		StringBuilder sqlBuf = new StringBuilder(" SELECT COUNT(*) AS total_rows FROM ( ").append(sql)
				.append(" ) AS result");
		return sqlBuf.toString();
	}
	/**
	 * 34执行select语句( PreparedStatement 重新调用)
	 * 
	 * @Description:
	 * 
	 * @Title: findResultSet
	 * 
	 * @param @param preparedStatement
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	public JdbcPrepareStatementDto findResultSet(PreparedStatement preparedStatement, String sql,
			List<Object> parameterList) throws SQLException {
		return super.findResultSet(preparedStatement, sql, parameterList);
	}
	/**
	 * 35 一,分页通过resultSet.absolute(游标)来查询;
	 * 
	 * 二,每页取数据最好不要超过1万条;
	 * 
	 * 三,执行select语句( PreparedStatement 没有重新调用，创建新的PreparedStatement);
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException {
		return super.findResultSet(conn, sql, parameterList, pageNo, pageSize);
	}
	/**
	 * 
	 * 36 查找记录集
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @throws SQLException
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findResultSet(conn, sql, parameterList);
	}
	/**
	 * 37将查询数据库结果转化为List<Map<String, Object>>;
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doResultSet2ListMap(ResultSet resultSet) throws SQLException {
		return super.doResultSet2ListMap(resultSet);
	}
	/**
	 * 
	 * 
	 * 38将查询数据库结果转化为Map，值均转化为Object类型;
	 * 
	 * @param rsmd
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> doResultSet2Map(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException {
		return super.doResultSet2Map(rsmd, resultSet);
	}
	/**
	 * 
	 * 39
	 * 
	 * 一,通过resultSet.absolute查询;
	 * 
	 * 二,添加序列编号(序号);
	 * 
	 * 三,通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException {
		return super.findMapList(conn, sql, parameterList, pageNo, pageSize);
	}
	/**
	 * 40通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql) throws SQLException {
		return super.findMapList(conn, sql);
	}
	/**
	 * 41 通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findMapList(conn, sql, parameterList);
	}
	/**
	 * 
	 * 42获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMap(Connection conn, String sql) throws SQLException {
		return super.findMap(conn, sql);
	}
	/**
	 * 43 获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMap(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findMap(conn, sql, parameterList);
	}
	/**
	 * 44 获取唯一结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMapUnique(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findMapUnique(conn, sql, parameterList);
	}
	/**
	 * 
	 * 45 获取第一个结果并转换成Double型
	 * 
	 * @Description:
	 * 
	 * @Title: findDouble
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	public Double findDouble(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findDouble(conn, sql, parameterList);
	}
	/**
	 * 
	 * 46获取第一个结果并转换成Integer型
	 * 
	 * @Description:
	 * 
	 * @Title: findInteger
	 * 
	 * @param @param key
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	public Integer findInteger(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findInteger(key, conn, sql, parameterList);
	}
	/**
	 * 
	 * 46 获取第一个结果并转换成Integer型
	 * 
	 * @Description:
	 * 
	 * @Title: findInteger
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	public Integer findInteger(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findInteger(conn, sql, parameterList);
	}
	/**
	 * 
	 * 47获取第一个结果并转换成Long型
	 * 
	 * @Description:
	 * 
	 * @Title: findLong
	 * 
	 * @param @param key
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	public Long findLong(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findLong(key, conn, sql, parameterList);
	}
	/**
	 * 获取第一列的多行结果并转换成String型
	 * 
	 * @Description:
	 * 
	 * @Title: findStringList
	 * 
	 * @param @param key
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public List<String> findStringList(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findStringList(key, conn, sql, parameterList);
	}
	/**
	 * 获取第一个结果并转换成String型
	 * 
	 * @Description:
	 * 
	 * @Title: findString
	 * 
	 * @param @param key
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public String findString(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findString(key, conn, sql, parameterList);
	}
	/**
	 * 48 获取第一个结果并转换成String型
	 * 
	 * @Description:
	 * 
	 * @Title: findString
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public String findString(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findString(conn, sql, parameterList);
	}
	/**
	 * 49 分页返回PageCoreBean
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			String sqlCount) throws SQLException {
		return findPageBean(conn, pageIndex, pageSize, sql, null, sqlCount);
	}
	/**
	 * 50 分页返回PageCoreBean
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount) throws SQLException {
		PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		return findPageBean(conn, pageBean, sql, parameterList, sqlCount);
	}
	/**
	 * 51 分页返回PageCoreBean
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, String sqlCount) throws SQLException {
		return findPageBean(conn, page, sql, null, sqlCount);
	}
	/**
	 * 52分页返回PageCoreBean
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount) throws SQLException {
		return this.findPageBean(conn, page, sql, parameterList, sqlCount, null);
	}
	/**
	 * 53分页返回PageCoreBean
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount)
					throws SQLException {
		List<Map<String, Object>> listMap = null;
		// 计算总数
		if (sqlCount == null) {
			sqlCount = findSqlCount(sql, page.getStart(), page.getLimit());
		}
		List<Map<String, Object>> listMapCount = findMapList(conn, sqlCount, parameterListCount);
		for (Map<String, Object> map : listMapCount) {
			Set<String> set = map.keySet();
			for (String key : set) {
				if (key == ROW_NUM) {
					// 跳过序列编号;
					continue;
				}
				Object value = map.get(key);
				Long valueLong = null;
				if (value instanceof Long) {
					// mysql 统计总数是long类型
					valueLong = (Long) value;
				}
				page.setTotalCount(valueLong);
				break;
			}
			break;
		}
		// 是否查询全部
		if (page.isFindAll()) {
			listMap = findMapList(conn, sql, parameterList);
			page.setPageSize(page.getTotalCount().intValue());
		} else {
			// 设置参数
			long start = page.getStart();
			long end = page.getEnd();
			long pageIndex = page.getPageIndex();
			long pageSize = page.getPageSize();
			String sqlFinal = findSqlPage(sql, start, end, pageIndex, pageSize);
			listMap = findMapList(conn, sqlFinal, parameterList);
		}
		page.setList(listMap);
		return page;
	}
	/**
	 * 54分页返回PageCoreBean
	 * 
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount) throws SQLException {
		PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		return this.findPageBean(conn, pageBean, sql, parameterList, sqlCount, parameterListCount);
	}
	/**
	 * 54分页返回PageCoreBean
	 * 
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException {
		return null;
	}
	/**
	 * 55 分页返回PageCoreBean
	 * 
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException {
		return null;
	}
	/**
	 * 56将查询数据库结果转化为List<ArrayList<String>> ;
	 * 
	 * 
	 */
	public List<List<String>> doResultSet2listList(ResultSet resultSet) throws SQLException {
		return super.doResultSet2listList(resultSet);
	}
	/**
	 * 57
	 * 
	 * 将查询数据库结果转化为List，值均转化为String类型;
	 * 
	 * 
	 */
	public List<String> doResultSet2List(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException {
		return super.doResultSet2List(rsmd, resultSet);
	}
	/**
	 * 58 通过sql,将查询数据库结果转化为List<ArrayList<String>>
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> findListList(Connection conn, String sql) throws SQLException {
		return super.findListList(conn, sql);
	}
	/**
	 * 59通过sql,将查询数据库结果转化为List<List<String>>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> findListList(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findListList(conn, sql, parameterList);
	}
	/**
	 * 60 获取记录集的第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<String> findListFirst(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findListFirst(conn, sql, parameterList);
	}
	/**
	 * 61 获取唯一结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<String> findListUnique(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findListUnique(conn, sql, parameterList);
	}
	/**
	 * 62 将查询数据库结果转化为List<JdbcRow> ;
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public JdbcDataDto doResultSet2JdbcData(ResultSet resultSet) throws SQLException {
		return super.doResultSet2JdbcData(resultSet);
	}
	/**
	 * 
	 * 
	 * 63将查询数据库结果转化为JdbcRow ;
	 * 
	 * @param rsMetaData
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public JdbcRowDto doResultSet2JdbcRow(ResultSetMetaData rsMetaData, ResultSet resultSet) throws SQLException {
		return super.doResultSet2JdbcRow(rsMetaData, resultSet);
	}
	/**
	 * 
	 * 64获取记录集的第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public JdbcRowDto findJdbcRow(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findJdbcRow(conn, sql, parameterList);
	}
	/**
	 * 65通过sql,将查询数据库结果转化为List<JdbcRow>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public JdbcDataDto findJdbcData(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return super.findJdbcData(conn, sql, parameterList);
	}
	/**
	 * 66jdbc元数据
	 * 
	 * 找出数据库信息
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseBean findDatabaseInfo(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		return super.findDatabaseInfo(url, username, password);
	}
	/**
	 * 
	 * 67jdbc元数据
	 * 
	 * 找出数据库信息
	 * 
	 * @param conn
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseBean findDatabaseInfo(Connection conn) throws ClassNotFoundException, SQLException {
		return super.findDatabaseInfo(conn);
	}
	/**
	 * 68通过java的api找到表信息;
	 * 
	 * tableNamePattern 表名称;可包含单字符通配符("_"),或多字符通配符("%") ;
	 * 
	 */
	public List<TableBean> findTableBeanListByApi(Connection conn, String userName, String tableNamePattern)
			throws SQLException {
		String catalog = conn.getCatalog();
		// schemaPattern指数据名
		String schemaPattern = null;
		if (StringUtil.isBlank(schemaPattern)) {
			// log.trace("catalog =" + catalog);
			schemaPattern = catalog;
		}
		if (StringUtil.isBlank(tableNamePattern)) {
			// tableNamePattern = "%";
		}
		List<TableBean> tableBeanList = new ArrayList<TableBean>();
		DatabaseMetaData databaseMetaData = conn.getMetaData();
		ResultSet resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern,
				new String[]{"TABLE"});
		while (resultSet.next()) {
			//
			// TABLE_CAT String => 表类别（可为 null）
			// TABLE_SCHEM String => 表模式（可为 null）
			// TABLE_NAME String => 表名称
			// TABLE_TYPE String => 表类型。典型的类型是
			// "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL
			// TEMPORARY"、"ALIAS"
			// 和 "SYNONYM"。
			// REMARKS String => 表的解释性注释
			// TYPE_CAT String => 类型的类别（可为 null）
			// TYPE_SCHEM String => 类型模式（可为 null）
			// TYPE_NAME String => 类型名称（可为 null）
			// SELF_REFERENCING_COL_NAME String => 有类型表的指定 "identifier" 列的名称（可为
			// null）
			// REF_GENERATION String => 指定在 SELF_REFERENCING_COL_NAME
			// 中创建值的方式。这些值为 "SYSTEM"、"USER" 和 "DERIVED"。（可能为 null）
			TableBean tableBean = new TableBean();
			tableBean.setCatalog(resultSet.getString("TABLE_CAT"));
			tableBean.setSchemata(resultSet.getString("TABLE_SCHEM"));
			tableBean.setTableName(resultSet.getString("TABLE_NAME"));
			tableBean.setTableType(resultSet.getString("TABLE_TYPE"));
			tableBean.setComment(resultSet.getString("REMARKS"));
			// log.trace("table name="+tableBean.getTableName());
			// log.trace("table comment="+tableBean.getComment());
			tableBeanList.add(tableBean);
		}
		return tableBeanList;
	}
	/**
	 * 69全部转换成小写， 不要去掉下划线;
	 * 
	 */
	public String findFieldName(String columnName) {
		return super.findFieldName(columnName);
	}
	/**
	 * 70 头个字母转换成大写，不要去掉下划线
	 * 
	 */
	public String findMethodName(String columnName) {
		return super.findMethodName(columnName);
	}
	/**
	 * 71 sql列类型转换成数据类型
	 * 
	 */
	public String findDataTypeAll(String columnType) {
		String dataType = this.findDataType(columnType);
		if (StringUtil.isNotBlank(dataType)) {
			return dataType;
		} else {
			dataType = ColumnBean.dataType_String;
			return dataType;
		}
	}
	/**
	 * 72sql列类型转换成数据类型
	 * 
	 */
	public String findDataType(String columnType) {
		return super.findDataType(columnType);
	}
	/**
	 * 73sql列类型所关联的操作类型
	 * 
	 */
	public String findTypeOperate(String columnType) {
		return super.findTypeOperate(columnType);
	}
	/**
	 * 
	 * 74sql列类型所关联的操作类型
	 * 
	 */
	public List<TypeOperateBean> findTypeOperateList(String columnType) {
		return super.findTypeOperateList(columnType);
	}
	/**
	 * 75jdbc元数据,通过java的api找到列信息
	 * 
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String schemaPattern, String tableName,
			String columnName) throws SQLException {
		List<ColumnBean> columnBeanList = new ArrayList<ColumnBean>();
		// 表名不能为空
		if (StringUtil.isBlank(tableName)) {
			return columnBeanList;
		}
		String catalog = conn.getCatalog();
		// schemaPattern指数据名
		if (StringUtil.isBlank(schemaPattern)) {
			// log.trace("catalog =" + catalog);
			schemaPattern = catalog;
		}
		DatabaseMetaData databaseMetaData = conn.getMetaData();
		ResultSet resultSet = databaseMetaData.getColumns(catalog, schemaPattern, tableName, columnName);
		while (resultSet.next()) {
			// TABLE_CAT String => 表类别（可为 null）
			// TABLE_SCHEM String => 表模式（可为 null）
			// TABLE_NAME String => 表名称
			// COLUMN_NAME String => 列名称
			// DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型
			// TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
			// COLUMN_SIZE int => 列的大小。
			// BUFFER_LENGTH 未被使用。
			// DECIMAL_DIGITS int => 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回
			// Null。
			// NUM_PREC_RADIX int => 基数（通常为 10 或 2）
			// NULLABLE int => 是否允许使用 NULL。
			// columnNoNulls - 可能不允许使用 NULL 值
			// columnNullable - 明确允许使用 NULL 值
			// columnNullableUnknown - 不知道是否可使用 null
			// REMARKS String => 描述列的注释（可为 null）
			// COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
			// SQL_DATA_TYPE int => 未使用
			// SQL_DATETIME_SUB int => 未使用
			// CHAR_OCTET_LENGTH int => 对于 char 类型，该长度是列中的最大字节数
			// ORDINAL_POSITION int => 表中的列的索引（从 1 开始）
			// IS_NULLABLE String => ISO 规则用于确定列是否包括 null。
			// YES --- 如果参数可以包括 NULL
			// NO --- 如果参数不可以包括 NULL
			// 空字符串 --- 如果不知道参数是否可以包括 null
			// SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为
			// null）
			// SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为
			// null）
			// SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
			// SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的
			// SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null）
			// IS_AUTOINCREMENT String => 指示此列是否自动增加
			// YES --- 如果该列自动增加
			// NO --- 如果该列不自动增加
			// 空字符串 --- 如果不能确定该列是否是自动增加参数
			ColumnBean columnBean = new ColumnBean();
			columnBean.setCatalog(resultSet.getString("TABLE_CAT"));
			columnBean.setSchemata(resultSet.getString("TABLE_SCHEM"));
			columnBean.setTableName(resultSet.getString("TABLE_NAME"));
			columnBean.setColumnName(resultSet.getString("COLUMN_NAME"));
			columnBean.setColumnLabel(resultSet.getString("COLUMN_NAME"));
			// 小写
			columnBean.setFieldName(this.findFieldName(columnBean.getColumnName()));
			columnBean.setMethodName(this.findMethodName(columnBean.getColumnName()));
			columnBean.setSqlTypeInt(resultSet.getInt("DATA_TYPE"));
			columnBean.setSqlTypeStr(resultSet.getString("TYPE_NAME"));
			// log.trace("type str="+columnBean.getColumnTypeStr());
			columnBean.setDataType(this.findDataTypeAll(columnBean.getSqlTypeStr()));
			// log.trace("COLUMN_NAME="+resultSet.getString("COLUMN_NAME"));
			// log.trace("TYPE_NAME="+resultSet.getString("TYPE_NAME"));
			// log.trace("DataType="+columnBean.getDataType());
			// 操作类型
			columnBean.setTypeOperate(this.findTypeOperate(columnBean.getSqlTypeStr()));
			// 操作类型
			columnBean.setTypeOperateBeanList(this.findTypeOperateList(columnBean.getSqlTypeStr()));
			columnBean.setColumnDisplaySize(resultSet.getLong("COLUMN_SIZE"));
			// columnBean.setPrecision(resultSet.getLong("PRECISION"));
			// log.trace("Precision精度＝"+columnBean.getPrecision());
			columnBean.setScale(resultSet.getLong("DECIMAL_DIGITS"));
			// 自定义长度
			columnBean.setPrecision(columnBean.getColumnDisplaySize());
			columnBean.setLength(columnBean.getColumnDisplaySize());
			columnBean.setIsNullInt(resultSet.getLong("NULLABLE"));
			columnBean.setIsNullStr(resultSet.getString("IS_NULLABLE"));
			columnBean.setComment(resultSet.getString("REMARKS"));
			columnBean.setIsAutoIncrememtStr(resultSet.getString("IS_AUTOINCREMENT"));
			// 默认值
			columnBean.setColumnDef(resultSet.getString("COLUMN_DEF"));
			columnBeanList.add(columnBean);
		}
		return columnBeanList;
	}
	/**
	 * 
	 * 76 jdbc元数据 通过sql查找所有列信息
	 * 
	 */
	@Override
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql) throws SQLException {
		return this.findColumnBeanListByApi(conn, sql, null);
	}
	/**
	 * 
	 * 77jdbc元数据通过sql查找所有列信息
	 * 
	 */
	@Override
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findColumnBeanListByApi(conn, sql, parameterList);
	}
	/**
	 * 78jdbc元数据 通过sql查找所有列信息
	 * 
	 */
	@Override
	public List<ColumnBean> findColumnBeanListByApi(ResultSet resultSet) throws SQLException {
		return super.findColumnBeanListByApi(resultSet);
	}
	/**
	 * 82找出列的类型
	 * 
	 */
	@Override
	public String findSqlType(String dataType, Long length, Long scale) {
		boolean isFind = false;
		if (!isFind) {
			if (ColumnBean.dataType_String.equals(dataType)) {
				isFind = true;
				return "VARCHAR(" + length + ')';
			}
		}
		if (!isFind) {
			if (ColumnBean.dataType_Number.equals(dataType)) {
				return "DECIMAL(" + length + "," + scale + ")";
			}
		}
		if (!isFind) {
			if (ColumnBean.dataType_Date.equals(dataType)) {
				return "TIMESTAMP";
			}
		}
		if (!isFind) {
			if (ColumnBean.dataType_Integer.equals(dataType)) {
				return "BIGINT(" + length + ")";
			}
		}
		if (!isFind) {
			if (ColumnBean.dataType_Clob.equals(dataType)) {
				// return "TEXT";
				return "LONGTEXT";
			}
		}
		return "";
	}
	/**
	 * 83添加新的一列
	 * 
	 */
	public void addColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException {
		execute(conn, createSqlForAddColumn(tableName, columnName, columnType, length, scale));
	}
	/**
	 * 84构造新的一列的sql
	 * 
	 */
	public String createSqlForAddColumn(String tableName, String columnName, String columnType, Long length,
			Long scale) {
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" add column ");
		sb.append(" " + columnName + " ");
		sb.append(" " + findSqlType(columnType, length, scale) + " ");
		return sb.toString();
	}
	/**
	 * 85 修改列的定义
	 * 
	 */
	public void doChangeColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException {
		execute(conn, createSqlForChangeColumn(tableName, columnName, columnType, length, scale));
	}
	/**
	 * 86 构造sql,修改列的定义
	 * 
	 */
	public String createSqlForChangeColumn(String tableName, String columnName, String columnType, Long length,
			Long scale) {
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" change column ");
		sb.append(" " + columnName + " ");
		sb.append(" " + columnName + " ");
		sb.append(" " + findSqlType(columnType, length, scale) + " ");
		return sb.toString();
	}
	/**
	 * 87添加外键
	 * 
	 */
	public void addForeignKey(Connection conn, String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException {
		execute(conn, createSqlForAddForeignKey(pkTableName, pkField, fkTableName, fkField));
	}
	/**
	 * 88 构造添加外键的sql
	 * 
	 */
	public String createSqlForAddForeignKey(String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException {
		String sql = "ALTER TABLE " + fkTableName + " ADD CONSTRAINT fk_" + fkTableName + "_" + pkTableName
				+ " FOREIGN KEY (" + fkField + ") REFERENCES " + pkTableName + " (" + pkField
				+ ")  on delete restrict on update restrict";
		return sql;
	}
	/**
	 * 89删除外键
	 * 
	 */
	public void doDropForeignKey(Connection conn, String tableName, String keyName) throws SQLException {
		execute(conn, createSqlForDropForeignKey(tableName, keyName));
	}
	/**
	 * 90构造删除外键的sql
	 * 
	 */
	public String createSqlForDropForeignKey(String tableName, String keyName) throws SQLException {
		String sql = "ALTER TABLE " + tableName + " DROP FOREIGN KEY " + keyName;
		return sql;
	}
	/**
	 * 91创建表
	 * 
	 */
	@Override
	public void createTable(Connection conn, CreateTableListBean bean) throws SQLException {
		super.createTable(conn, bean);
	}
	/**
	 * 92创建表
	 */
	@Override
	public void createTable(Connection conn, CreateTableBean bean) throws SQLException {
		RowBean rowBean = bean.getRowBeanList().get(0);
		TableBean tableBean = bean.getTableBean();
		// List<JdbcRowBean> rowBeanList = bean.getRowBeanList();
		List<ColumnBean> columnList = rowBean.getColumnBeanList();
		// 外健sql
		List<String> fkSqlList = new ArrayList<String>();
		// 建表语句
		StringBuilder createTableSql = new StringBuilder();
		// 主键字段
		String pkColumn = null;
		// 建表开始
		createTableSql.append("CREATE TABLE " + tableBean.getTableName() + " (\n");
		for (int i = 0; i < columnList.size(); i++) {
			// 建字段
			ColumnBean cm = columnList.get(i);
			createTableSql.append(cm.getColumnLabel()).append(" ");
			createTableSql.append(findSqlType(cm.getDataType(), cm.getLength(), cm.getScale()));
			createTableSql.append(" ");
			// 添加默认值。
			String defaultValue = cm.getColumnDef();
			if (StringUtil.isNotBlank(defaultValue)) {
				createTableSql.append(" default '" + defaultValue + "'");
			}
			// 非空
			if (!cm.getIsNull()) {
				createTableSql.append(" NOT NULL ");
			}
			// 主键
			if (cm.getIsPk()) {
				if (pkColumn == null) {
					pkColumn = cm.getColumnLabel();
				} else {
					pkColumn = pkColumn + "," + cm.getColumnLabel();
				}
			}
			// 外健
			if (cm.getIsFk()) {
				String sql = createSqlForAddForeignKey(cm.getFkRefTable(), cm.getFkRefColumn(),
						tableBean.getTableName(), cm.getColumnLabel());
				fkSqlList.add(sql);
			}
			// 字段注释
			if (StringUtil.isNotBlank(cm.getComment())) {
				createTableSql.append(" COMMENT '" + cm.getComment() + "'");
			}
			createTableSql.append(",\n");
		}
		// 建主键
		if (pkColumn != null) {
			createTableSql.append(" PRIMARY KEY (" + pkColumn + ")");
		}
		// 表注释
		createTableSql.append("\n)");
		if (StringUtil.isNotBlank(tableBean.getComment())) {
			createTableSql.append(" COMMENT='" + tableBean.getComment() + "'");
		}
		// 构造建表的sql结束
		createTableSql.append(";");
		// 执行建表sql
		execute(conn, createTableSql.toString());
		// 执行外健sql
		for (String fkSql : fkSqlList) {
			execute(conn, fkSql);
		}
	}
	/**
	 * 93修改表结构
	 * 
	 */
	@Override
	public void alterTable(Connection conn, CreateTableListBean bean) throws SQLException {
		super.alterTable(conn, bean);
	}
	/**
	 * 94修改表结构
	 * 
	 */
	public void alterTable(Connection conn, CreateTableBean bean) throws SQLException {
		RowBean rowBean = bean.getRowBeanList().get(0);
		//
		AlterTableBean updateTableBean = new AlterTableBean();
		// 表
		updateTableBean.setTableBean(bean.getTableBean());
		// 数据库存在的列
		List<ColumnBean> columnInfoList = findColumnBeanListByApi(conn, null, bean.getTableBean().getTableName(), null);
		// 需要创建的列
		List<ColumnBean> createColumnBeanList = rowBean.getColumnBeanList();
		// 需要创建的列
		for (ColumnBean cm : createColumnBeanList) {
			boolean isExist = false;
			// 数据库存在的列
			for (ColumnBean cb : columnInfoList) {
				if (cm.getColumnLabel().trim().equalsIgnoreCase(cb.getColumnName().trim())) {
					isExist = true;
					// 长度扩大，而且小数位扩大才能更新
					if ((cm.getLength() > cb.getLength()) && cm.getScale() >= cb.getScale()) {
						// 更新的字段（只更新字段的长度）
						updateTableBean.getAlterColumnBeanList().add(cm);
					}
				}
			}
			if (isExist) {
			} else {// 增加的字段
				updateTableBean.getCreateColumnBeanList().add(cm);
			}
		}
		// 添加新一列
		for (ColumnBean cm : updateTableBean.getCreateColumnBeanList()) {
			addColumn(conn, updateTableBean.getTableBean().getTableName(), cm.getColumnLabel(), cm.getDataType(),
					cm.getLength(), cm.getScale());
		}
		// 更新一列
		for (ColumnBean cm : updateTableBean.getAlterColumnBeanList()) {
			doChangeColumn(conn, updateTableBean.getTableBean().getTableName(), cm.getColumnLabel(), cm.getDataType(),
					cm.getLength(), cm.getScale());
		}
	}
	/**
	 * 95检查表是否存在
	 * 
	 * @throws SQLException
	 */
	@Override
	public boolean isTableExist(Connection conn, String tableName) throws SQLException {
		String schemata = findSchemata(conn);
		String sql = "SELECT COUNT(*) as COUNT FROM information_schema.TABLES t  where t.TABLE_SCHEMA=? and  t.TABLE_NAME=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(schemata);
		parameterList.add(tableName);
		Long count = findLong("COUNT", conn, sql, parameterList);
		return count > 0 ? true : false;
	}
	/**
	 * 96创建或修改表的结构
	 * 
	 */
	@Override
	public void createOrAlterTable(Connection conn, CreateTableListBean bean) throws SQLException {
		super.createOrAlterTable(conn, bean);
	}
	/**
	 * 97 创建或更新表
	 * 
	 */
	public void createOrAlterTable(Connection conn, CreateTableBean bean) throws SQLException {
		if (isTableExist(conn, bean.getTableBean().getTableName())) {
			alterTable(conn, bean);
		} else {
			createTable(conn, bean);
		}
	}
	/**
	 * 98 往表插入数据
	 * 
	 */
	@Override
	public RowBean insertTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException {
		return super.insertTable(conn, rowBean, tableBean);
	}
	/**
	 * 99对象转为sql(insert)
	 * 
	 */
	@Override
	public String insertObject(Connection conn, String tableName, StringBuilder keySb, StringBuilder valueSb,
			ArrayList<Object> parameterList) throws SQLException {
		return super.insertObject(conn, tableName, keySb, valueSb, parameterList);
	}
	/**
	 * 100往表更新数据
	 * 
	 */
	@Override
	public RowBean updateTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException {
		return super.updateTable(conn, rowBean, tableBean);
	}
	/**
	 * 101更新对象(构造更新数据的sql)
	 * 
	 */
	@Override
	public int updateObject(Connection conn, String primaryKey, String pkValue, String tableName, StringBuilder sb,
			ArrayList<Object> valueListObject) throws SQLException {
		return super.updateObject(conn, primaryKey, pkValue, tableName, sb, valueListObject);
	}
	/**
	 * 102往表插入或更新数据
	 * 
	 */
	@Override
	public CreateTableListBean insertOrUpdateTable(Connection conn, CreateTableListBean bean) throws SQLException {
		return super.insertOrUpdateTable(conn, bean);
	}
	/**
	 * 103往表插入或更新数据
	 * 
	 */
	@Override
	public CreateTableBean insertOrUpdateTable(Connection conn, CreateTableBean bean) throws SQLException {
		return super.insertOrUpdateTable(conn, bean);
	}
	/**
	 * 104 取得当前连接的Schemata
	 * 
	 */
	public String findSchemata(Connection conn) throws SQLException {
		return super.findSchemata(conn);
	}
	/**
	 * 105主键是否存在
	 * 
	 */
	public boolean isPkExist(CreateTableBean jdbcTableBean) {
		return super.isPkExist(jdbcTableBean);
	}
	/**
	 * 106 得到表的主键id(列名)
	 * 
	 * @param conn
	 * @param schemata
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String findTablePrimaryKeyByApi(Connection conn, String schemata, String tableName) throws SQLException {
		return super.findTablePrimaryKeyByApi(conn, schemata, tableName);
	}
	/**
	 * 110 拼装where条件的sql
	 * 
	 */
	@Override
	public String findSqlWhere(Map<String, Object> parameterMap) {
		return super.findSqlWhere(parameterMap);
	}
	/**
	 * 
	 * 111分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount) throws SQLException {
		PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(pageIndex, pageSize);
		return findPageBeanForWhere(conn, pageBean, sql, parameterMap, sqlCount);
	}
	/**
	 * 
	 * 112 分页返回PageCoreBean( 构建where条件的sql)
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount)
					throws SQLException {
		return this.findPageBeanForWhere(conn, page, sql, parameterMap, sqlCount, parameterMap);
	}
	/**
	 * 113分页返回PageCoreBean( 构建where条件的sql)
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount) throws SQLException {
		List<Map<String, Object>> listMap = null;
		// 计算总数
		if (sqlCount == null) {
			sqlCount = findSqlCount(sql, page.getStart(), page.getLimit());
		}
		List<Map<String, Object>> countMapList = findMapList(conn, sqlCount);
		for (Map<String, Object> countMap : countMapList) {
			Set<String> set = countMap.keySet();
			for (String key : set) {
				if (key == ROW_NUM) {
					// 跳过序列编号;
					continue;
				}
				Object value = countMap.get(key);
				Long valueLong = null;
				if (value instanceof Long) {
					// mysql 统计总数是long类型
					valueLong = (Long) value;
				}
				page.setTotalCount(valueLong);
				break;
			}
			break;
		}
		// 构建where条件的sql
		String sqlWhere = this.findSqlWhere(parameterMap);
		sql = sql + sqlWhere;
		// 是否查询全部
		if (page.isFindAll()) {
			listMap = findMapList(conn, sql);
			page.setPageSize(page.getTotalCount().intValue());
		} else {
			// 设置参数
			long start = page.getStart();
			long end = page.getEnd();
			long pageIndex = page.getPageIndex();
			long pageSize = page.getPageSize();
			String sqlFinal = findSqlPage(sql, start, end, pageIndex, pageSize);
			listMap = findMapList(conn, sqlFinal);
		}
		page.setList(listMap);
		return page;
	}
	/**
	 * 
	 * 114分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount, Map<String, Object> parameterMapCount,
			String sqlPage, Map<String, Object> parameterMapPage) throws SQLException {
		return null;
	}
	/**
	 * 
	 * 115分页返回PageCoreBean( 构建where条件的sql) 
	 * 
	 */
	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount, String sqlPage, Map<String, Object> parameterMapPage)
					throws SQLException {
		return null;
	}
	/**
	 * 
	 * 116自定义SQL查询(where条件) 
	 * 
	 */
	public SqlCustomBean doBuildSql(String sql, Map<String, Object> parameterMap) {
		return super.doBuildSql(sql, parameterMap);
	}
	/**
	 * 117自定义SQL查询
	 * 
	 * @Description: @Title: doBuildSql @param @param bean @param @return
	 *               参数说明 @throws
	 */
	@Override
	public String doBuildSql(SqlCustomBean bean) {
		String sql = null;
		if (bean.isPage()) {
			PageCoreBean<Map<String, Object>> pageBean = new PageMysqlBean<Map<String, Object>>(bean.getPageIndex(),
					bean.getPageSize());
			sql = super.doBuildSql(bean);
			sql = findSqlPage(sql, pageBean.getStart(), pageBean.getEnd(), pageBean.getPageIndex(),
					pageBean.getPageSize());
		} else {
			sql = super.doBuildSql(bean);
		}
		return sql;
	}
	/**
	 * 
	 * 118 自定义SQL查询(通过表名来查询) 
	 * 
	 */
	@Override
	public String doBuildSqlByTable(SqlCustomBean bean) {
		return super.doBuildSqlByTable(bean);
	}
	/**
	 * 119找出整个数据库的所有表
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param schemata
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<String> findTableNameList_v1(String schemata, String url, String username, String password)
			throws Exception {
		Connection conn = openConnection(url, username, password);
		List<String> tableNameList = new ArrayList<String>();
		Statement statement = conn.createStatement();
		statement.execute("use " + schemata);
		ResultSet resultSet = statement.executeQuery("show tables");
		while (resultSet.next()) {
			String tableName = resultSet.getString(1);
			tableNameList.add(tableName);
		}
		return tableNameList;
	}
	/**
	 * 
	 * 120模糊查询 通过sql找到表信息 
	 * 
	 */
	public List<TableBean> queryTableBeanList(Connection conn, String schemata, String tableName) throws SQLException {
		List<TableBean> tableBeanList = new ArrayList<TableBean>();
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String sql = "";
		List<Object> parameterList = new ArrayList<Object>();
		try {
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment from " + "information_schema.tables t "
						+ "where t.table_type='BASE TABLE' " + " "
						+ "and t.table_schema  like ? and table_name like ? ";
				parameterList.add("%" + schemata + "%");
				parameterList.add("%" + tableName + "%");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment " + "from information_schema.tables t "
						+ "where t.table_type='BASE TABLE' " + "and t.table_schema  like ?";
				parameterList.add("%" + schemata + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment " + "from information_schema.tables t "
						+ "where t.table_type='BASE TABLE' ";
			}
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			while (resultSet.next()) {
				TableBean tableBean = new TableBean();
				tableBean.setSchemata(resultSet.getString("table_schema"));
				tableBean.setTableName(resultSet.getString("table_name"));
				tableBean.setComment(resultSet.getString("table_comment"));
				tableBean.setTableType(tableBean.tableType_TABLE);
				tableBeanList.add(tableBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(resultSet, preparedStatement);
		}
		return tableBeanList;
	}
	/**
	 * 121
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn) throws SQLException {
		return this.queryColumnBeanList(conn, null, null, null);
	}
	/**
	 * 122 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException {
		return queryColumnBeanList(conn, null, tableName, columnName);
	}
	/**
	 * 123
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名 
	 * 
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException {
		List<ColumnBean> columnBeanList = new ArrayList<ColumnBean>();
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String sql = "";
		List<Object> parameterList = new ArrayList<Object>();
		try {
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName)
					&& StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  like ? and t.table_name like ?  and t.COLUMN_NAME like ?";
				parameterList.add("%" + schemata + "%");
				parameterList.add("%" + tableName + "%");
				parameterList.add("%" + columnName + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  table_name like ?  and t.COLUMN_NAME like ?";
				parameterList.add("%" + tableName + "%");
				parameterList.add("%" + columnName + "%");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  like ?  and t.COLUMN_NAME like ?";
				parameterList.add("%" + schemata + "%");
				parameterList.add("%" + columnName + "%");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  like ? and t.table_name like ?";
				parameterList.add("%" + schemata + "%");
				parameterList.add("%" + tableName + "%");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  like ?";
				parameterList.add("%" + schemata + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where   t.table_name like ?";
				parameterList.add("%" + tableName + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.COLUMN_NAME like ?";
				parameterList.add("%" + columnName + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t";
			}
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			while (resultSet.next()) {
				ColumnBean columnBean = new ColumnBean();
				columnBean.setSchemata(resultSet.getString("TABLE_SCHEMA"));
				columnBean.setTableName(resultSet.getString("TABLE_NAME"));
				columnBean.setColumnName(resultSet.getString("COLUMN_NAME"));
				columnBean.setSqlTypeStr(resultSet.getString("DATA_TYPE"));
				columnBean.setLength(resultSet.getLong("CHARACTER_MAXIMUM_LENGTH"));
				columnBean.setComment(resultSet.getString("COLUMN_COMMENT"));
				columnBean.setColumnDef(resultSet.getString("COLUMN_DEFAULT"));
				// 0=NO,1=YES
				String sIsNull = resultSet.getString("IS_NULLABLE");
				if ("YES".equals(sIsNull)) {
					columnBean.setIsNullInt(1l);
				} else if ("NO".equals(sIsNull)) {
					columnBean.setIsNullInt(0l);
				} else {
					columnBean.setIsNullInt(-1l);
				}
				columnBean.setIsNullStr(resultSet.getString("IS_NULLABLE"));
				columnBeanList.add(columnBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(resultSet, preparedStatement);
		}
		return columnBeanList;
	}
	/**
	 * 121
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn) throws SQLException {
		return this.findColumnBeanList(conn, null, null, null);
	}
	/**
	 * 122 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException {
		return findColumnBeanList(conn, null, tableName, columnName);
	}
	/**
	 * 123
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException {
		List<ColumnBean> columnBeanList = new ArrayList<ColumnBean>();
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String sql = "";
		List<Object> parameterList = new ArrayList<Object>();
		try {
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName)
					&& StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  = ? and t.table_name = ?  and t.COLUMN_NAME = ?";
				parameterList.add("" + schemata + "");
				parameterList.add("" + tableName + "");
				parameterList.add("" + columnName + "");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  table_name = ?  and t.COLUMN_NAME = ?";
				parameterList.add("" + tableName + "");
				parameterList.add("" + columnName + "");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  = ?  and t.COLUMN_NAME = ?";
				parameterList.add("" + schemata + "");
				parameterList.add("" + columnName + "");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  = ? and t.table_name = ?";
				parameterList.add("" + schemata + "");
				parameterList.add("" + tableName + "");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.table_schema  = ?";
				parameterList.add("" + schemata + "");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isNotBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where   t.table_name = ?";
				parameterList.add("" + tableName + "");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isNotBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t "
						+ " where  t.COLUMN_NAME = ?";
				parameterList.add("" + columnName + "");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName) && StringUtil.isBlank(columnName)) {
				sql = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH ,CHARACTER_OCTET_LENGTH,COLUMN_DEFAULT,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_KEY,COLUMN_COMMENT  FROM INFORMATION_SCHEMA.COLUMNS t";
			}
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			while (resultSet.next()) {
				ColumnBean columnBean = new ColumnBean();
				columnBean.setSchemata(resultSet.getString("TABLE_SCHEMA"));
				columnBean.setTableName(resultSet.getString("TABLE_NAME"));
				columnBean.setColumnName(resultSet.getString("COLUMN_NAME"));
				columnBean.setSqlTypeStr(resultSet.getString("DATA_TYPE"));
				columnBean.setLength(resultSet.getLong("CHARACTER_MAXIMUM_LENGTH"));
				columnBean.setComment(resultSet.getString("COLUMN_COMMENT"));
				columnBean.setColumnDef(resultSet.getString("COLUMN_DEFAULT"));
				// 0=NO,1=YES
				String sIsNull = resultSet.getString("IS_NULLABLE");
				if ("YES".equals(sIsNull)) {
					columnBean.setIsNullInt(1l);
				} else if ("NO".equals(sIsNull)) {
					columnBean.setIsNullInt(0l);
				} else {
					columnBean.setIsNullInt(-1l);
				}
				columnBean.setIsNullStr(resultSet.getString("IS_NULLABLE"));
				columnBeanList.add(columnBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(resultSet, preparedStatement);
		}
		return columnBeanList;
	}
	/**
	 * 124
	 * 
	 * 通过schemata查找所有的视图名
	 * 
	 * @param conn
	 * @param schemata
	 * @return
	 * @throws SQLException
	 */
	public List<TableBean> findViewList(Connection conn, String schemata) throws SQLException {
		return this.findViewList(conn, schemata, null);
	}
	/**
	 * 125
	 * 
	 * 通过schemata和name查找视图
	 * 
	 * @param conn
	 * @param schemata
	 * @param viewName
	 *            指定视图名
	 * @return
	 * @throws SQLException
	 */
	public List<TableBean> findViewList(Connection conn, String schemata, String tableName) throws SQLException {
		List<TableBean> tableBeanList = new ArrayList<TableBean>();
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String sql = "";
		List<Object> parameterList = new ArrayList<Object>();
		try {
			if (StringUtil.isNotBlank(schemata) && StringUtil.isNotBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment FROM information_schema.TABLES t WHERE t.TABLE_TYPE LIKE 'VIEW'"
						+ "and table_schema like ? and table_name like ? ";
				parameterList.add("%" + schemata + "%");
				parameterList.add("%" + tableName + "%");
			}
			if (StringUtil.isNotBlank(schemata) && StringUtil.isBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment FROM information_schema.TABLES t WHERE t.TABLE_TYPE LIKE 'VIEW'"
						+ "and table_schema like ? ";
				parameterList.add("%" + schemata + "%");
			}
			if (StringUtil.isBlank(schemata) && StringUtil.isBlank(tableName)) {
				sql = "select t.table_schema,t.table_name,t.table_comment FROM information_schema.TABLES t WHERE t.TABLE_TYPE LIKE 'VIEW'";
			}
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			while (resultSet.next()) {
				TableBean tableBean = new TableBean();
				tableBean.setSchemata((String) resultSet.getString("TABLE_SCHEMA"));
				tableBean.setTableName((String) resultSet.getString("TABLE_NAME"));
				tableBean.setComment((String) resultSet.getString("TABLE_COMMENT"));
				tableBean.setTableType(tableBean.tableType_VIEW);
				// tableBean.setAutoIncrement((Long)
				// resultSet.getLong("AUTO_INCREMENT"));
				tableBeanList.add(tableBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(resultSet, preparedStatement);
		}
		return tableBeanList;
	}
	/**
	 * 126
	 * 
	 * 通过sql找到表或视图 
	 */
	public List<TableBean> findSysObjectList(Connection conn, String schemata, String tableName) throws SQLException {
		return super.findSysObjectList(conn, schemata, tableName);
	}
	/**
	 * 通过sql, 将查询数据库结果转化为List< Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, Connection conn, String sql) throws SQLException {
		return super.findObjectListByColumnKey(key, conn, sql);
	}
	/**
	 * 通过sql,将查询数据库结果转化为List<Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		return super.findObjectListByColumnKey(key, conn, sql, parameterList);
	}
}
