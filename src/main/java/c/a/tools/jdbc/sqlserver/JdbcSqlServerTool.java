package c.a.tools.jdbc.sqlserver;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.JdbcCoreTool;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.PageBean;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.nut.JdbcNutUtil;
import c.a.util.core.jdbc.sqlserver.JdbcSqlServerUtil;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class JdbcSqlServerTool extends JdbcCoreTool implements IJdbcTool {
	private IJdbcUtil JdbcUtil = new JdbcSqlServerUtil();
	/**
	 *
	 */
	@Override public IJdbcUtil getJdbcUtil() {
		return JdbcUtil;
	}
	/**
	 * 2分页(PageBean)返回PageBean;
	 *
	 */
	@Override public PageCoreBean<Map<String, Object>> findPageBean(Class classObj,
			Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {
		AssertUtil.isNull(page, "page不能为空");
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		// 计算总数
		if (sqlCount == null) {
			sqlCount = jdbcUtil.findSqlCount(sql, page.getStart(),
					page.getLimit());
		}
		List<Map<String, Object>> listMapCount = this.getJdbcUtil()
				.findMapList(conn, sqlCount, parameterListCount);
		for (Map<String, Object> map : listMapCount) {
			Set<String> set = map.keySet();
			for (String key : set) {
				if (key == JdbcNutUtil.ROW_NUM) {
					// 跳过序列编号;
					continue;
				}
				Object value = map.get(key);
				Long value_long = null;
				if (value instanceof Integer) {
					// sqlserver 统计总数是int类型
					Integer value_int = (Integer) value;
					value_long = Integer.valueOf(value_int).longValue();
				}
				page.setTotalCount(value_long);
				break;
			}
			break;
		}
		// }
		// 计算总数
		// 设置参数
		// {
		List listObject = null;
		// 是否查询全部
		if (page.isFindAll()) {
			listObject = this
					.findObjectList(classObj, conn, sql, parameterList);
			page.setPageSize(page.getTotalCount().intValue());
		} else {
			long start = page.getStart();
			long limit = page.getPageSize();
			String sqlPageFinal = null;
			if (sqlPage == null) {
				AssertUtil.isNull(sqlPage, "sql_page不能为空");
			} else {
				sqlPageFinal = sqlPage;
			}
			listObject = this.findObjectList(classObj, conn, sqlPageFinal,
					parameterList);
			// }
			// 设置参数
		}
		page.setList(listObject);
		return page;
	}
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
	@Override public List findObjectList(Class classObj, Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {
		return this.findPageBean(classObj, conn, page, sql, parameterList,
				sqlCount, parameterListCount, sqlPage, parameterListPage)
				.getList();
	}
	/**
	 * 4分页(long pageNo, long pageSize)返回PageBean;
	 *
	 */
	@Override public PageCoreBean<Map<String, Object>> findPageBean(Class classAy,
			Connection conn, int pageNo, int pageSize, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {
		PageCoreBean pageBean = new PageBean(pageNo, pageSize);
		return this.findPageBean(classAy, conn, pageBean, sql, parameterList,
				sqlCount, parameterListCount, sqlPage, parameterListPage);
	}
	/**
	 * 5分页(long pageNo, long pageSize)返回List;
	 *
	 */
	@Override public List findObjectList(Class clasObj, Connection conn, int pageNo,
			int pageSize, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {
		return this.findPageBean(clasObj, conn, pageNo, pageSize, sql,
				parameterList, sqlCount, parameterListCount, sqlPage,
				parameterListPage).getList();
	}
	/**
	 * 6 sql返回PageBean;
	 *
	 */
	@Override public PageCoreBean<Map<String, Object>> findPageBean(Class c_Class,
			Connection conn, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {
		PageCoreBean page = new PageBean(1, Integer.MAX_VALUE);
		return this.findPageBean(c_Class, conn, page, sql, parameterList,
				sqlCount, parameterListCount, sqlPage, parameterListPage);
	}
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
	@Override public <T> T findObject(Class<T> classObj, Connection conn, String sql,
			List<Object> parameterList) throws Exception {
		return super.findObject(classObj, conn, sql, parameterList);
	}
	/**
	 * 
	 * 9通过id查找实体
	 *
	 */
	@Override public Object findObject(Connection conn, Class classObj, String id)
			throws Exception {
		return super.findObject(conn, classObj, id);
	}
	/**
	 * 
	 * 10获取唯一结果
	 *
	 */
	@Override public <T> T findObjectUnique(Class<T> classObj, Connection conn, String sql,
			List<Object> parameterList) throws Exception {
		return super.findObjectUnique(classObj, conn, sql, parameterList);
	}
	/**
	 * 
	 * 11通过id查找唯一实体
	 *
	 */
	@Override public <T> T findObjectUnique(Connection conn, Class<T> classObj, String id)
			throws Exception {
		return super.findObjectUnique(conn, classObj, id);
	}
	/**
	 * 12 需要简单主键;
	 *
	 */
	@Override public String insertObjectSimplePK(Connection conn, Object object)
			throws Exception {
		return super.insertObjectSimplePK(conn, object);
	}
	/**
	 * 13 不需要主键;
	 * 
	 * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 *
	 */
	@Override public String insertObjectPkNot(Connection conn, Object object)
			throws Exception {
		return super.insertObjectPkNot(conn, object);
	}
	/**
	 * 
	 * 14不需要主键;
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
	@Override public String insertObjectNotPK(Connection conn, String tableName,
			Object object) throws Exception {
		return super.insertObjectNotPK(conn, tableName, object);
	}
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
	@Override public String insertObject(Connection conn, Object object) throws Exception {
		return super.insertObject(conn, object);
	}
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
	@Override public String insertObject(Connection conn, String tableName,
			Object object, String pkInput,Long  idxValue) throws Exception {
		return super.insertObject(conn, tableName, object, pkInput,idxValue);
	}
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
	@Override public Integer updateObject(Connection conn, String primaryKey,
			String pkValue, String tableName, Object obj) throws Exception {
		return super.updateObject(conn, primaryKey, pkValue, tableName, obj);
	}
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
	@Override public Integer updateObject(Connection conn, Object object)
			throws Exception {
		return super.updateObject(conn, object);
	}
	/**
	 * 19将查询数据库结果转化为List<Object>
	 *
	 */
	@Override public List doResultSet2ListObject(Class classObj, ResultSet resultSet)
			throws Exception {
		return super.doResultSet2ListObject(classObj, resultSet);
	}

	/**
	 * 21结果集转成实体类的Field(变量,域值);
	 *
	 */
	@Override public Object BLOB_ResultSet2EntityField(Field field,
			Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException,
			IOException {
		return super.BLOB_ResultSet2EntityField(field, value,
				columnBean, resultSet);
	}
	/**
	 * 22结果集转成实体类的Field(变量,域值);
	 *
	 */
	@Override public Object NUMERIresultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		{
			return super.NUMERIresultSet2EntityField(field, value, columnBean,
					resultSet);
		}
	}
	/**
	 * 23结果集转成实体类的Field(变量,域值);
	 *
	 */
	@Override public Object BIT_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		return super.BIT_ResultSet2EntityField(field, value, columnBean,
				resultSet);
	}
	/**
	 * 24结果集转成实体类的Field(变量,域值);
	 *
	 */
	@Override public Object INTEGER_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		return super.INTEGER_ResultSet2EntityField(field, value, columnBean,
				resultSet);
	}
	/**
	 * 25结果集转成实体类的Field(变量,域值);
	 *
	 */
	@Override public Object LONG_ResultSet2EntityField(Field field, Object value,
			ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		return super.LONG_ResultSet2EntityField(field, value, columnBean,
				resultSet);
	}
}
