package c.a.util.core.jdbc.nut;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import c.a.util.core.jdbc.bean.create.CreateTableBean;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
/**
 * 
 * 
 * 数据库的普通api
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JdbcDefaultUtil extends JdbcNutUtil {

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDbType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDbType(String dbType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDriver(String driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Connection openConnectionCore(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection openConnection(String url, String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doTransactionStart(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int findStart(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findLimit(int pageSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findEnd(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String findSqlPage(String sql, long start, long end, long pageIndex, long pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSqlCount(String sql, long start, long limit) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			String sqlCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, String sqlCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount)
					throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize, String sql,
			List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableBean> findTableBeanListByApi(Connection conn, String schemata, String tableNamePattern)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findDataTypeAll(String columnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String schemata, String tableName,
			String columnName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSqlType(String dataType, Long length, Long scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSqlForAddColumn(String tableName, String columnName, String columnType, Long length,
			Long scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doChangeColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSqlForChangeColumn(String tableName, String columnName, String columnType, Long length,
			Long scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addForeignKey(Connection conn, String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSqlForAddForeignKey(String pkTableName, String pkField, String fkTableName, String fkField)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doDropForeignKey(Connection conn, String tableName, String keyName) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createSqlForDropForeignKey(String tableName, String keyName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createTable(Connection conn, CreateTableBean bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterTable(Connection conn, CreateTableBean bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTableExist(Connection conn, String tableName) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createOrAlterTable(Connection conn, CreateTableBean bean) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount)
					throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount, Map<String, Object> parameterMapCount,
			String sqlPage, Map<String, Object> parameterMapPage) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount, String sqlPage, Map<String, Object> parameterMapPage)
					throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findTableNameList_v1(String schemata, String url, String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableBean> queryTableBeanList(Connection conn, String schemata, String tableName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> queryColumnBeanList(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> queryColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> queryColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> findColumnBeanList(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> findColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ColumnBean> findColumnBeanList(Connection conn, String schemata, String tableName, String columnName)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableBean> findViewList(Connection conn, String schemata) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TableBean> findViewList(Connection conn, String schemata, String viewName) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
