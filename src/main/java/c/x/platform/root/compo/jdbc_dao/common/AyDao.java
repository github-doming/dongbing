package c.x.platform.root.compo.jdbc_dao.common;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.bean.data_row.JdbcDataDto;
import c.a.util.core.jdbc.bean.data_row.JdbcRowDto;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
/**
 * 普通dao只要继承就行
 * 
 * @ClassName: AyDao
 * @Description:
 * @author
 * @date 2015年11月3日 上午11:19:30
 * 
 */
public class AyDao {
	/**
	 * 通过列名和sql, 将查询数据库结果转化为List< Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param connection
	 * @param sql
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, String sql) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findObjectListByColumnKey(key, conn, sql, null);
	}
	/**
	 * 通过列名和sql,将查询数据库结果转化为List<Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param connection
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, String sql, List<Object> parameterList)
			throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findObjectListByColumnKey(key, conn, sql, parameterList);
	}
	public JdbcRowDto findJdbcRow(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findJdbcRow(conn, sql, parameterList);
	}
	public JdbcDataDto findJdbcData(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findJdbcData(conn, sql, parameterList);
	}
	/**
	 * 获取第一列的多行结果并转换成String型
	 * 
	 * @Title: findStringList
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 *             返回类型 List<String>
	 */
	public List<String> findStringList(String key, String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findStringList(key, conn, sql, parameterList);
	}
	/**
	 * 获取第一个结果并转换成String型
	 * 
	 * @Title: findString
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 *             返回类型 String
	 */
	public String findString(String key, String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findString(key, conn, sql, parameterList);
	}
	/**
	 * 	 获取第一个结果;
	 * 
	 * 将查询数据库结果转化为Map;
	 * @Title: findMap 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException 
	 * 返回类型 Map<String,Object>
	 */
	public Map<String, Object> findMap(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findMap(conn, sql, parameterList);
	}
	/**
	 * 分页(Class classObj,int pageNo, int pageSize)返回BasePage;
	 * 
	 * @Title: page
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @param sql
	 * @param parameterList
	 * @param pageIndex
	 * @param pageSize
	 * @param sqlCount
	 *            统计总数sql
	 * @param parameterListCount
	 * @return
	 * @throws Exception
	 *             返回类型 PageCoreBean
	 */
	public PageCoreBean page(Class classObj, String sql, List<Object> parameterList, int pageIndex, int pageSize,
			String sqlCount, List<Object> parameterListCount) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findPageBean(classObj, conn, pageIndex, pageSize, sql, parameterList, sqlCount,
				parameterListCount, null, null);
	}
	/**
	 *  分页(Class classObj,int pageNo, int pageSize)返回BasePage;
	 * @Title: page 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param classObj
	 * @param sql
	 * @param parameterList
	 * @param pageIndex
	 * @param pageSize
	 * @param sqlCount
	 * @return
	 * @throws Exception 
	 * 返回类型 PageCoreBean
	 */
	public PageCoreBean page(Class classObj, String sql, List<Object> parameterList, int pageIndex, int pageSize,
			String sqlCount) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findPageBean(classObj, conn, pageIndex, pageSize, sql, parameterList, sqlCount, null, null,
				null);
	}
	/**
	 * 分页查询(有parameters参数)
	 * 
	 * @Title: page
	 * @Description:
	 *
	 * 				参数说明
	 * @param sql
	 * @param parameterList
	 * @param pageIndex
	 * @param pageSize
	 * @param sqlCount
	 * @param parameterListCount
	 * @return
	 * @throws SQLException
	 *             返回类型 PageCoreBean
	 */
	public PageCoreBean<Map<String, Object>> page(String sql, List<Object> parameterList, int pageIndex, int pageSize, String sqlCount,
			List<Object> parameterListCount) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findPageBean(conn, pageIndex, pageSize, sql, parameterList, sqlCount, parameterListCount);
	}
	/**
	 * 分页查询(有parameters参数)
	 * 
	 * @Title: page
	 * @Description:
	 *
	 * 				参数说明
	 * @param sql
	 * @param parameterList
	 * @param pageIndex
	 * @param pageSize
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 *             返回类型 PageCoreBean
	 */
	public PageCoreBean page(String sql, List<Object> parameterList, int pageIndex, int pageSize, String sqlCount)
			throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findPageBean(conn, pageIndex, pageSize, sql, parameterList, sqlCount);
	}
	/**
	 * 将查询数据库结果转化为List<Map>
	 * @Title: findMapList 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException 
	 * 返回类型 List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findMapList(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.findMapList(conn, sql, parameterList);
	}
	/**
	 * 
	 * 获取唯一结果
	 * 
	 * @param classObj
	 * @param connection
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Object findObjectUnique(Class classObj, String sql, List<Object> parameterList) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findObjectUnique(classObj, conn, sql, parameterList);
	}
	/**
	 * 
	 * 获取第一个结果;
	 * 
	 * @param classObj
	 * @param connection
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public <T> T findObject(Class<T> classObj, String sql, List<Object> parameterList) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findObject(classObj, conn, sql, parameterList);
	}
	/**
	 * 
	 * 通过sql
	 * 
	 * 
	 * 将查询数据库结果转化为List<Object>
	 * 
	 * @param classObj
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List findObjectList(Class classObj, String sql) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findObjectList(classObj, conn, sql, null);
	}
	/**
	 * 
	 * 通过sql
	 * 
	 * 
	 * 将查询数据库结果转化为List<Object>
	 * 
	 * @param classObj
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findObjectList(Class<T> classObj, String sql, List<Object> parameterList) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findObjectList(classObj, conn, sql, parameterList);
	}
	/**
	 * Object查找
	 * 
	 * @param id
	 * @throws Exception
	 */
	public <T>  T find(Class<T> classObj, String id) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.findObjectUnique(conn, classObj, id);
	}
	/**
	 * 
	 * jpa更新
	 * 
	 * @param connection
	 * @param object
	 * @throws Exception
	 */
	public Integer update(Object object) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.updateObject(conn, object);
	}
	/**
	 * 
	 * * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @param connection
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String savePkNot(Object object) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.insertObjectPkNot(conn, object);
	}
	/**
	 * 
	 * * 支持大对象;
	 * 
	 * 对象转为sql(insert);
	 * 
	 * @param connection
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public String save(Object object) throws Exception {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcTool.insertObject(conn, object);
	}
	public JdbcPrepareStatementDto find(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		JdbcPrepareStatementDto bean = jdbcUtil.findResultSet(conn, sql, parameterList);
		return bean;
	}
	public int execute(String sql, List<Object> parameterList) throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		return jdbcUtil.execute(conn, sql, parameterList);
	}
}
