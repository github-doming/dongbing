package c.a.tools.jdbc;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
public interface IJdbcTool {
	/**
	 * 1
	 * 
	 * @Title: getJdbcUtil
	 * @Description:
	 * @return 参数说明
	 * @return IJdbcUtil 返回类型
	 * @throws
	 */
	public IJdbcUtil getJdbcUtil();
	/**
	 * 2分页(PageBean)返回PageBean;
	 * 
	 * @Description:
	 * @Title: findPageBean
	 * @param classObj
	 * @param conn
	 * @param page
	 * @param sql
	 * @param parameterList
	 * @param sqlCount
	 * @param parameterListCount
	 * @param sqlPage
	 * @param parameterListPage
	 * @return
	 * @throws Exception 参数说明
	 * @return PageCoreBean<Map<String,Object>> 返回类型
	 * @throws
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Class classObj,
			Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception;
	/**
	 * 3分页(PageBean)返回List;
	 * 
	 * @Description:
	 * @Title: findObjectList
	 * @param classObj
	 * @param conn
	 * @param page
	 * @param sql
	 * @param parameterList
	 * @param sqlCount
	 * @param parameterListCount
	 * @param sqlPage
	 * @param parameterListPage
	 * @return
	 * @throws Exception 参数说明
	 * @return List 返回类型
	 * @throws
	 */
	public List findObjectList(Class classObj, Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception;
	/**
	 * 4分页(long pageNo, long pageSize)返回PageBean;
	 * 
	 * @Description:
	 * @Title: findPageBean
	 * @param classObj
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param parameterList
	 * @param sqlCount
	 * @param parameterListCount
	 * @param sqlPage
	 * @param parameterListPage
	 * @return
	 * @throws Exception 参数说明
	 * @return PageCoreBean<Map<String,Object>> 返回类型
	 * @throws
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Class classObj,
			Connection conn, int pageNo, int pageSize, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception;
	/**
	 * 5分页(long pageNo, long pageSize)返回List;
	 * 
	 * @Description:
	 * @Title: findObjectList
	 * @param classObj
	 * @param conn
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @param parameterList
	 * @param sqlCount
	 * @param parameterListCount
	 * @param sqlPage
	 * @param parameterListPage
	 * @return
	 * @throws Exception 参数说明
	 * @return List 返回类型
	 * @throws
	 */
	public List findObjectList(Class classObj, Connection conn, int pageNo,
			int pageSize, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception;
	/**
	 * 6 sql返回PageBean;
	 * 
	 * @Description:
	 * @Title: findPageBean
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @param sqlCount
	 * @param parameterListCount
	 * @param sqlPage
	 * @param parameterListPage
	 * @return
	 * @throws Exception 参数说明
	 * @return PageCoreBean<Map<String,Object>> 返回类型
	 * @throws
	 */
	public PageCoreBean<Map<String, Object>> findPageBean(Class classObj,
			Connection conn, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception;
	/**
	 * 7通过sql将查询数据库结果转化为List<Object>
	 * 
	 * @Description:
	 * @Title: findObjectList
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws Exception 参数说明
	 * @return List 返回类型
	 * @throws
	 */
	public <T> List<T>  findObjectList(Class<T>  classObj, Connection conn, String sql,
			List<Object> parameterList) throws Exception;
	/**
	 * 
	 * 8获取结果
	 * 
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	public <T> T findObject(Class<T> classObj, Connection conn, String sql,
			List<Object> parameterList) throws Exception;
	/**
	 * 
	 * 9通过id查找实体
	 * 
	 * @param conn
	 * @param classObj
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Object findObject(Connection conn, Class classObj, String id)
			throws Exception;
	/**
	 * 
	 * 10获取唯一结果
	 * 
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	public <T> T findObjectUnique(Class<T> classObj, Connection conn, String sql,
			List<Object> parameterList) throws Exception;
	/**
	 * 
	 * 11通过id查找唯一实体
	 * 
	 * @param conn
	 * @param classObj
	 * @return
	 * @throws Exception
	 */
	public <T> T findObjectUnique(Connection conn, Class<T> classObj, String id)
			throws Exception;
	/**
	 * 12 需要简单主键;
	 * 
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String insertObjectSimplePK(Connection conn, Object object)
			throws Exception;
	/**
	 * 13 不需要主键;
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @Description:
	 * @Title: insertObjectNotPK
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String insertObjectPkNot(Connection conn, Object object)
			throws Exception;
	/**
	 * 14不需要主键;
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @Description:
	 * @Title: insertObjectNotPK
	 * @param conn
	 * @param tableName
	 * @param object
	 * @return
	 * @throws Exception 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String insertObjectNotPK(Connection conn, String tableName,
			Object object) throws Exception;
	/**
	 * 15需要主键;
	 * 
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String insertObject(Connection conn, Object object) throws Exception;
	/**
	 * 
	 * 16需要主键;
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @param conn
	 * @param tableName
	 * @param object
	 * @throws Exception
	 * @throws Exception
	 */
	public String insertObject(Connection conn, String tableName,
			Object object, String pkInput,Long  idxValue) throws Exception;
	/**
	 * 
	 * 17 支持大对象;
	 * 
	 * 对象转为sql(update);
	 * 
	 * @param conn
	 * @param primaryKey
	 * @param pkValue
	 * @param tableName
	 * @param obj
	 * @throws Exception
	 * @throws Exception
	 */
	public Integer updateObject(Connection conn, String primaryKey,
			String pkValue, String tableName, Object obj) throws Exception;
	/**
	 * 18jpa更新
	 * 
	 * @Description:
	 * @Title: updateObject
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception 参数说明
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer updateObject(Connection conn, Object object)
			throws Exception;
	/**
	 * 19将查询数据库结果转化为List<Object>
	 * 
	 * @Description:
	 * @Title: doResultSet2ListObject
	 * @param classObj
	 * @param resultSet
	 * @return
	 * @throws Exception 参数说明
	 * @return List 返回类型
	 * @throws
	 */
	<T> List<T> doResultSet2ListObject(Class<T> classObj, ResultSet resultSet)
			throws Exception;
	/**
	 * 
	 * 20将查询数据库结果转化为类
	 *
	 * @param columnBeanList
	 * @param classObj
	 * @param resultSet
	 * @return
	 * @throws Exception
	 */
	public <T> T doResultSet2Object(List<ColumnBean> columnBeanList, Class<T> classObj, ResultSet resultSet) throws Exception;
	/**
	 * 21结果集转成实体类的Field(变量,域值);
	 * 
	 * @param columnBean
	 * @param field
	 * @param value
	 * @param resultSet
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Object BLOB_ResultSet2EntityField(Field field,
			Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException,
			IOException;
	/**
	 * 22结果集转成实体类的Field(变量,域值);
	 * 
	 * @param field
	 * @param value
	 * @param columnBean
	 * @param resultSet
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	public Object NUMERIresultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException;
	/**
	 * 23结果集转成实体类的Field(变量,域值);
	 * 
	 * @param field
	 * @param value
	 * @param columnBean
	 * @param resultSet
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	public Object BIT_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException;
	/**
	 * 24结果集转成实体类的Field(变量,域值);
	 * 
	 * @param field
	 * @param value
	 * @param columnBean
	 * @param resultSet
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	public Object INTEGER_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException;
	/**
	 * 25结果集转成实体类的Field(变量,域值);
	 * 
	 * @param field
	 * @param value
	 * @param columnBean
	 * @param resultSet
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	public Object LONG_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException;
}
