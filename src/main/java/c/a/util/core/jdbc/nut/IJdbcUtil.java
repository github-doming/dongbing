package c.a.util.core.jdbc.nut;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
/**
 * 
 * 数据库的接口api
 * 
 * @Description:
 * @ClassName: IJdbcUtil
 * @date 2017年3月10日 上午10:27:18
 * @author cxy
 * @Email: 
 * @Copyright
 * 
 */
public interface IJdbcUtil {
	/**
	 * 1返回数据库类型
	 * 
	 * @Title: getDbType
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public String getDbType();
	/**
	 * 2设置数据库类型
	 * 
	 * @Title: setDbType
	 * @Description:
	 *
	 * 				参数说明
	 * @param dbType
	 *            返回类型 void
	 */
	public void setDbType(String dbType);
	/**
	 * 3返回驱动
	 * 
	 * @Title: getDriver @Description: @return 参数说明 @return String 返回类型 @throws
	 */
	public String getDriver();
	/**
	 * 4设置驱动
	 * 
	 * @Title: setDriver @Description: @param driver 参数说明 @return void
	 *         返回类型 @throws
	 */
	public void setDriver(String driver);
	/**
	 * 5得到数据库的连接
	 * 
	 * @Title: getConnection @Description: @return 参数说明 @return Connection
	 *         返回类型 @throws
	 */
	public Connection getConnection();
	/**
	 * 6设置数据库的连接
	 * 
	 * @Title: setConnection @Description: @param conn 参数说明 @return void
	 *         返回类型 @throws
	 */
	public void setConnection(Connection conn);
	/**
	 * 7打开并得到数据库的连接
	 * 
	 * @Title: openConnectionCore
	 * @Description:
	 *
	 * 				参数说明
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *             返回类型 Connection
	 */
	public Connection openConnectionCore(String url, String username, String password)
			throws ClassNotFoundException, SQLException;
	/**
	 * 8打开并得到数据库的连接
	 * 
	 * @Title: openConnectionCore
	 * @Description:
	 *
	 * 				参数说明
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 *             返回类型 Connection
	 */
	public Connection openConnectionCore(String driver, String url, String username, String password)
			throws ClassNotFoundException, SQLException;
	/**
	 * 9打开并得到数据库的连接
	 * 
	 * @Title: openConnection
	 * @Description:
	 *
	 * 				参数说明
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 Connection
	 */
	public Connection openConnection(String url, String username, String password) throws Exception;
	/**
	 * 10打开并得到数据库的连接
	 * 
	 * @Title: openConnection
	 * @Description:
	 *
	 * 				参数说明
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 *             返回类型 Connection
	 */
	public Connection openConnection(final String driver, final String url, final String username,
			final String password) throws Exception;
	/**
	 * 11关闭数据库连接
	 * 
	 * @Title: closeConnection
	 * @Description:
	 *
	 * 				参数说明
	 * @param conn
	 * @throws SQLException
	 *             返回类型 void
	 */
	public void closeConnection(Connection conn) throws SQLException;
	/**
	 * 12关闭PreparedStatement
	 * 
	 * @Title: closePreparedStatement
	 * @Description:
	 *
	 * 				参数说明
	 * @param preparedStatement
	 * @throws SQLException
	 *             返回类型 void
	 */
	public void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException;
	/**
	 * 13关闭Statement
	 * 
	 * @Title: closeStatement
	 * @Description:
	 *
	 * 				参数说明
	 * @param statement
	 * @throws SQLException
	 *             返回类型 void
	 */
	public void closeStatement(Statement statement) throws SQLException;
	/**
	 * 14关闭ResultSet
	 * 
	 * @Title: closeResultSet
	 * @Description:
	 *
	 * 				参数说明
	 * @param resultSet
	 * @throws SQLException
	 *             返回类型 void
	 */
	public void closeResultSet(ResultSet resultSet) throws SQLException;
	/**
	 * 15
	 * 
	 * 关闭Statement
	 * 
	 * 关闭ResultSet
	 * 
	 */
	public void close(ResultSet resultSet, Statement statement, Connection conn) throws SQLException;
	/**
	 * 16 关闭ResultSet
	 * 
	 * 关闭PreparedStatement
	 * 
	 * 
	 */
	public void close(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException;
	/**
	 * 17 关闭Statement
	 * 
	 * @Title: close
	 * @Description:
	 *
	 * 				参数说明
	 * @param statement
	 * @param conn
	 * @throws SQLException
	 *             返回类型 void
	 */
	public void close(Statement statement, Connection conn) throws SQLException;
	/**
	 * 18
	 * 
	 * 事务启动
	 * 
	 * 设置事务隔离级别
	 * 
	 */
	public void doTransactionStart(Connection conn) throws SQLException;
	/**
	 * 19 提交事务
	 * 
	 * 
	 */
	public void doTransactionCommit(Connection conn) throws SQLException;
	/**
	 * 20 回滚事务
	 * 
	 */
	public void doTransactionRollback(Connection conn) throws SQLException;
	/**
	 * 21 执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 * 
	 */
	public String execute2PK(Connection conn, String sql) throws SQLException;
	/**
	 * 22 执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 */
	public String execute2PK(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 23 执行insert,delete,update语句(返回影响的行数)
	 * 
	 */
	public int execute(Connection conn, String sql) throws SQLException;
	/**
	 * 24 执行insert,delete,update语句(返回影响的行数)
	 * 
	 */
	public int execute(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 
	 * 25 一,执行Insert语句，得到主键;
	 * 
	 * 二,如果主键是自动增长的数字，将得到主键;
	 * 
	 * 三,打印sql;
	 * 
	 * 
	 */
	public String executeInsert(Connection conn, String sql, List<Object> parameterList) throws SQLException;
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
	public String executeInsert(Boolean isPrintSqlOriginal, Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 27 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0或1开始
	 * 
	 * 
	 */
	public int findStart(int pageIndex, int pageSize);
	/**
	 * 28获得每页的记录数量,默认为Integer.MAX_VALU.
	 * 
	 * 
	 */
	public int findLimit(int pageSize);
	/**
	 * 29根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置
	 * 
	 * 
	 */
	public int findEnd(int pageNo, int pageSize);
	/**
	 * 30找出最终解析后的sql
	 * 
	 * 
	 */
	public String findSqlTarget(String sqlOriginal, List<Object> parameterList);
	/**
	 * 31解析并打印解析后的sql
	 * 
	 * 
	 */
	public void doPrintSqlTarget(String fun,String sqlOriginal, List<Object> parameterList);
	/**
	 * 32查找分页sql
	 * 
	 * 
	 */
	public String findSqlPage(String sql, long start, long end, long pageIndex, long pageSize);
	/**
	 * 
	 * 33查找统计总数的sql
	 * 
	 * 
	 */
	public String findSqlCount(String sql, long start, long limit) throws SQLException;
	/**
	 * 34执行select语句( PreparedStatement 重新调用)
	 * 
	 * 
	 */
	public JdbcPrepareStatementDto findResultSet(PreparedStatement preparedStatement, String sql,
			List<Object> parameterList) throws SQLException;
	/**
	 * 
	 * 35 一,分页通过resultSet.absolute(游标)来查询;
	 * 
	 * 二,每页取数据最好不要超过1万条;
	 * 
	 * 三,执行select语句( PreparedStatement 没有重新调用，创建新的PreparedStatement);
	 * 
	 * 
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException;
	/**
	 * 36查找记录集
	 * 
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 37将查询数据库结果转化为List<Map<String, Object>>;
	 * 
	 * 
	 */
	public List<Map<String, Object>> doResultSet2ListMap(ResultSet resultSet) throws SQLException;
	/**
	 * 38将查询数据库结果转化为Map，值均转化为Object类型;
	 * 
	 * 
	 */
	public Map<String, Object> doResultSet2Map(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException;
	/**
	 * 39 一,通过resultSet.absolute查询;
	 * 
	 * 二,添加序列编号(序号);
	 * 
	 * 三,通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * 
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException;
	/**
	 * 40通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * 
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql) throws SQLException;
	/**
	 * 
	 * 41通过sql将查询数据库结果转化为List<Map>
	 * 
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 42获取第一个结果
	 * 
	 * 
	 */
	public Map<String, Object> findMap(Connection conn, String sql) throws SQLException;
	/**
	 * 43获取第一个结果
	 * 
	 * 
	 */
	public Map<String, Object> findMap(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 44获取唯一结果
	 * 
	 * 
	 */
	public Map<String, Object> findMapUnique(Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 45获取第一个结果并转换成Double型
	 * 
	 */
	public Double findDouble(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 46获取第一个结果并转换成Integer型
	 * 
	 * 
	 */
	public Integer findInteger(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 46获取第一个结果并转换成Integer型
	 * 
	 * 
	 */
	public Integer findInteger(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 47获取第一个结果并转换成Long型
	 * 
	 * 
	 */
	public Long findLong(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 
	 * 获取第一列的多行结果并转换成String型
	 * 
	 * 
	 */
	public List<String> findStringList(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 
	 * 获取第一个结果并转换成String型
	 * 
	 */
	public String findString(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 
	 * 48获取第一个结果并转换成String型
	 * 
	 */
	public String findString(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 49分页返回PageCoreBean
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			String sqlCount) throws SQLException;
	/**
	 * 50 分页返回PageCoreBean
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount) throws SQLException;
	/**
	 * 51分页返回PageCoreBean
	 * 
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, String sqlCount) throws SQLException;
	/**
	 * 52分页返回PageCoreBean
	 * 
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount) throws SQLException;
	/**
	 * 53分页返回PageCoreBean
	 * 
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount)
					throws SQLException;
	/**
	 * 54分页返回PageCoreBean
	 * 
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount) throws SQLException;
	/**
	 * 54分页返回PageCoreBean
	 * 
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException;
	/**
	 * 
	 * 55分页返回PageCoreBean
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
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException;
	/**
	 * 56将查询数据库结果转化为List<List<String>> ;
	 * 
	 * 
	 */
	public List<List<String>> doResultSet2listList(ResultSet resultSet) throws SQLException;
	/**
	 * 57将查询数据库结果转化为List，值均转化为String类型;
	 * 
	 * 
	 */
	public List<String> doResultSet2List(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException;
	/**
	 * 58 通过sql, 将查询数据库结果转化为List<ArrayList<String>>
	 * 
	 * 
	 */
	public List<List<String>> findListList(Connection conn, String sql) throws SQLException;
	/**
	 * 59通过sql, 将查询数据库结果转化为List<List<String>>
	 * 
	 */
	public List<List<String>> findListList(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 60获取记录集的第一个结果
	 * 
	 */
	public List<String> findListFirst(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 61获取唯一结果
	 * 
	 * 
	 */
	public List<String> findListUnique(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 62将查询数据库结果转化为List<JdbcRow> ;
	 * 
	 * 
	 */
	public JdbcDataDto doResultSet2JdbcData(ResultSet resultSet) throws SQLException;
	/**
	 * 63将查询数据库结果转化为JdbcRow ;
	 * 
	 * 
	 */
	public JdbcRowDto doResultSet2JdbcRow(ResultSetMetaData rsMetaData, ResultSet resultSet) throws SQLException;
	/**
	 * 64获取记录集的第一个结果
	 * 
	 * 
	 */
	public JdbcRowDto findJdbcRow(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 65通过sql,将查询数据库结果转化为List<JdbcRow>
	 * 
	 * 
	 */
	public JdbcDataDto findJdbcData(Connection conn, String sql, List<Object> parameterList) throws SQLException;
	/**
	 * 66
	 * 
	 * jdbc元数据
	 * 
	 * 找出数据库信息
	 * 
	 */
	public DatabaseBean findDatabaseInfo(String url, String username, String password)
			throws ClassNotFoundException, SQLException;
	/**
	 * 67
	 * 
	 * jdbc元数据
	 * 
	 * 找出数据库信息
	 * 
	 */
	public DatabaseBean findDatabaseInfo(Connection conn) throws ClassNotFoundException, SQLException;
	/**
	 * 68通过java的api找到表信息
	 * 
	 * 
	 * 表名称;可包含单字符通配符("_"),或多字符通配符("%")
	 */
	public List<TableBean> findTableBeanListByApi(Connection conn, String userName, String tableNamePattern)
			throws SQLException;
	/**
	 * 69 首个单词首个字母小写，其它单词头个字母转换成大写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> sysUserId
	 * 
	 */
	public String findFieldName(String columnName);
	/**
	 * 70 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 * 
	 */
	public String findMethodName(String columnName);
	/**
	 * 71sql列类型所关联的操作类型
	 * 
	 * 
	 */
	public String findTypeOperate(String columnType);
	/**
	 * 72 sql列类型所关联的操作类型
	 * 
	 * @Description: @Title: findTypeOperateList @param columnType @return
	 *               参数说明 @return List<TypeOperateBean> 返回类型 @throws
	 */
	public List<TypeOperateBean> findTypeOperateList(String columnType);
	/**
	 * 73sql列类型转换成数据类型
	 * 
	 * @Description: @Title: findDataType @param columnType @return 参数说明 @return
	 *               String 返回类型 @throws
	 */
	public String findDataType(String columnType);
	/**
	 * 74sql列类型转换成数据类型
	 * 
	 * @Description: @Title: findDataTypeAll @param columnType @return
	 *               参数说明 @return String 返回类型 @throws
	 */
	public String findDataTypeAll(String columnType);
	/**
	 * 75jdbc元数据,通过java的api找到列信息
	 * 
	 * @Description @Title findColumnBeanListByApi @param conn @param
	 *              schemata @param tableName @param columnName @return @throws
	 *              SQLException 参数说明 @return List<ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String schemata, String tableName,
			String columnName) throws SQLException;
	/**
	 * 
	 * 76jdbc元数据 ,通过sql查找所有列信息
	 * 
	 * @Description: @Title: findColumnBeanList @param conn @param
	 *               sql @return @throws SQLException 参数说明 @return List
	 *               <ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql) throws SQLException;
	/**
	 * 
	 * 77 jdbc元数据,通过sql查找所有列信息
	 * 
	 * @Description: @Title: findColumnBeanList @param conn @param sql @param
	 *               parameterList @return @throws SQLException 参数说明 @return
	 *               List<ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql, List<Object> parameterList)
			throws SQLException;
	/**
	 * 78jdbc元数据, 通过sql查找所有列信息
	 * 
	 * @Description: @Title: findColumnBeanList @param rs @return @throws
	 *               SQLException 参数说明 @return List<ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> findColumnBeanListByApi(ResultSet resultSet) throws SQLException;
	/**
	 * 82找出列的类型
	 * 
	 * @Description: @Title: findSqlType @param dataType @param length @param
	 *               scale @return 参数说明 @return String 返回类型 @throws
	 */
	public String findSqlType(String dataType, Long length, Long scale);
	/**
	 * 83添加新的一列
	 * 
	 * @Description: @Title: addColumn @param conn @param tableName @param
	 *               columnName @param columnType @param length @param
	 *               scale @throws SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void addColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException;
	/**
	 * 84构造新的一列的sql
	 * 
	 * @Description: @Title: createSqlForAddColumn @param tableName @param
	 *               columnName @param columnType @param length @param
	 *               scale @return 参数说明 @return String 返回类型 @throws
	 */
	public String createSqlForAddColumn(String tableName, String columnName, String columnType, Long length,
			Long scale);
	/**
	 * 85修改列的定义
	 * 
	 * @Title: changeColumn @Description: @param conn @param tableName @param
	 *         columnName @param columnNameNew @param columnType @param
	 *         length @param scale @throws SQLException 参数说明 @return void
	 *         返回类型 @throws
	 */
	public void doChangeColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException;
	/**
	 * 86构造sql,修改列的定义
	 * 
	 * @Title: createChangeColumn @Description: @param tableName @param
	 *         columnName @param columnNameNew @param columnType @param
	 *         length @param scale @return 参数说明 @return String 返回类型 @throws
	 */
	public String createSqlForChangeColumn(String tableName, String columnName, String columnType, Long length,
			Long scale);
	/**
	 * 87添加外键
	 * 
	 * @Title: addForeignKey @Description: @param conn @param pkTableName @param
	 *         pkField @param fkTableName @param fkField @throws SQLException
	 *         参数说明 @return void 返回类型 @throws
	 */
	public void addForeignKey(Connection conn, String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException;
	/**
	 * 88构造添加外键的sql
	 * 
	 * @Title: createAddForeignKeySql @Description: @param pkTableName @param
	 *         pkField @param fkTableName @param fkField @return @throws
	 *         SQLException 参数说明 @return String 返回类型 @throws
	 */
	public String createSqlForAddForeignKey(String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException;
	/**
	 * 89删除外键
	 * 
	 * @Title: dropForeignKey @Description: @param conn @param tableName @param
	 *         keyName @throws SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void doDropForeignKey(Connection conn, String tableName, String keyName) throws SQLException;
	/**
	 * 90构造删除外键的sql
	 * 
	 * @Title: createDropForeignKeySql @Description: @param tableName @param
	 *         keyName @return @throws SQLException 参数说明 @return String
	 *         返回类型 @throws
	 */
	public String createSqlForDropForeignKey(String tableName, String keyName) throws SQLException;
	/**
	 * 91创建表
	 * 
	 * @Description: @Title: createTable @param conn @param bean @throws
	 *               SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void createTable(Connection conn, CreateTableListBean bean) throws SQLException;
	/**
	 * 92创建表
	 * 
	 * @Title: createTable @Description: @param conn @param bean @throws
	 *         SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void createTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 93修改表结构
	 * 
	 * @Description: @Title: alterTable @param conn @param bean @throws
	 *               SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void alterTable(Connection conn, CreateTableListBean bean) throws SQLException;
	/**
	 * 94修改表结构
	 * 
	 * @Description: @Title: alterTable @param conn @param bean @throws
	 *               SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void alterTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 95 检查表是否存在
	 * 
	 */
	public boolean isTableExist(Connection conn, String tableName) throws SQLException;
	/**
	 * 96创建或修改表的结构
	 * 
	 * @Description: @Title: createOrAlterTable @param conn @param bean @throws
	 *               SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void createOrAlterTable(Connection conn, CreateTableListBean bean) throws SQLException;
	/**
	 * 97创建或修改表的结构
	 * 
	 * @Description: @Title: createOrAlterTable @param conn @param bean @throws
	 *               SQLException 参数说明 @return void 返回类型 @throws
	 */
	public void createOrAlterTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 98 往表插入数据
	 * 
	 * @Title: @Description: @param conn @param model @throws SQLException
	 *         参数说明 @return void 返回类型 @throws
	 */
	public RowBean insertTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException;
	/**
	 * 
	 * 99对象转为sql(insert)
	 * 
	 * @param conn
	 * @param tableName
	 * @param sbKey
	 * @param sbValue
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String insertObject(Connection conn, String tableName, StringBuilder keySb, StringBuilder valueSb,
			ArrayList<Object> parameterList) throws SQLException;
	/**
	 * 100往表更新数据
	 * 
	 */
	public RowBean updateTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException;
	/**
	 * 
	 * 101更新对象(构造更新数据的sql)
	 * 
	 * @param conn
	 * @param primaryKey
	 * @param pkValue
	 * @param tableName
	 * @param sb
	 * @param valueListObject
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int updateObject(Connection conn, String primaryKey, String pkValue, String tableName, StringBuilder sb,
			ArrayList<Object> valueListObject) throws SQLException;
	/**
	 * 102往表插入或更新数据
	 * @Title: insertOrUpdateTable 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param conn
	 * @param bean
	 * @return
	 * @throws SQLException 
	 * 返回类型 CreateTableListBean
	 */
	public CreateTableListBean insertOrUpdateTable(Connection conn, CreateTableListBean bean) throws SQLException;
	/**
	 * 103往表插入或更新数据
	 * 
	 */
	public CreateTableBean insertOrUpdateTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 104取得当前连接的Schemata
	 * 
	 */
	public String findSchemata(Connection conn) throws SQLException;
	/**
	 * 105 主键是否存在
	 */
	public boolean isPkExist(CreateTableBean jdbcTableBean);
	/**
	 * 106得到表的主键id(通过api得到主键的列名)
	 * 
	 * @param conn
	 * @param schemata
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String findTablePrimaryKeyByApi(Connection conn, String schemata, String tableName) throws SQLException;
	/**
	 * 110map参数转成sql语句,拼装where条件的sql
	 * 
	 * @Title: findSqlWhere @Description: @param parameterMap @return
	 *         参数说明 @return String 返回类型 @throws
	 */
	public String findSqlWhere(Map<String, Object> parameterMap);
	/**
	 * 111 分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount) throws SQLException;
	/**
	 * 112分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount)
					throws SQLException;
	/**
	 * 113分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount) throws SQLException;
	/**
	 * 114分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount, Map<String, Object> parameterMapCount,
			String sqlPage, Map<String, Object> parameterMapPage) throws SQLException;
	/**
	 * 115分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 */
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount, String sqlPage, Map<String, Object> parameterMapPage)
					throws SQLException;
	/**
	 * 116自定义SQL查询(where条件)
	 * 
	 */
	public SqlCustomBean doBuildSql(String sql, Map<String, Object> parameterMap);
	/**
	 * 117 自定义SQL查询
	 * 
	 */
	public String doBuildSql(SqlCustomBean bean);
	/**
	 * 118自定义SQL查询(通过表名来查询)
	 * 
	 */
	public String doBuildSqlByTable(SqlCustomBean bean);
	/**
	 * 
	 * 119找出整个数据库的所有表名
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
			throws Exception;
	/**
	 * 模糊查询 通过sql找到表信息
	 * 
	 * @Description @Title queryTableBeanList @param conn @param schemata @param
	 *              tableName @return @throws SQLException 参数说明 @return List
	 *              <TableBean> 返回类型 @throws
	 */
	public List<TableBean> queryTableBeanList(Connection conn, String schemata, String tableName) throws SQLException;
	/**
	 * 121
	 * 
	 * 模糊查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn) throws SQLException;
	/**
	 * 122
	 * 
	 * 模糊查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException;
	/**
	 * 123
	 * 
	 * 模糊查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @Title: @Description: @param conn @param schemata @param tableName @param
	 *         columnName @return @throws SQLException 参数说明 @return List
	 *         <ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> queryColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException;
	/**
	 * 121
	 * 
	 * 精确查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn) throws SQLException;
	/**
	 * 122
	 * 
	 * 精确查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException;
	/**
	 * 123
	 * 
	 * 精确查询
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @Title: @Description: @param conn @param schemata @param tableName @param
	 *         columnName @return @throws SQLException 参数说明 @return List
	 *         <ColumnBean> 返回类型 @throws
	 */
	public List<ColumnBean> findColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException;
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
	public List<TableBean> findViewList(Connection conn, String schemata) throws SQLException;
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
	public List<TableBean> findViewList(Connection conn, String schemata, String viewName) throws SQLException;
	/**
	 * 126
	 * 
	 * 通过sql找到表或视图
	 * 
	 * @Description @Title findObjectList @param conn @param schemata @param
	 *              tableName @return @throws SQLException 参数说明 @return List
	 *              <TableBean> 返回类型 @throws
	 */
	public List<TableBean> findSysObjectList(Connection conn, String schemata, String tableName) throws SQLException;
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
	public List<Object> findObjectListByColumnKey(String key, Connection conn, String sql) throws SQLException;
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
			throws SQLException;
}
