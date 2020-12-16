package c.a.util.core.jdbc.common;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomBean;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomOrderDto;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomQueryDto;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomWhereDto;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.nut.JdbcDefaultUtil;
import c.a.util.core.jdbc.nut.JdbcFactory;
import c.a.util.core.json.JsonThreadLocal;
/**
 * 数据库的通用api
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class JdbcCommonUtil extends JdbcDefaultUtil {
	/**
	 * 执行SQL自定义查询,查找where条件的列
	 * 
	 */
	public List<SqlCustomWhereDto> findWhereFieldListByJsonData(String jsonStr) throws SQLException {
		List<SqlCustomWhereDto> whereFieldListReturn = new ArrayList<SqlCustomWhereDto>();
		SqlCustomBean bean = new SqlCustomBean();
		SqlCustomWhereDto sqlCustomQueryWhereBean = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		String jsonData = jsonMap.get("data").toString();
		List<Map<String, Object>> dataMapList = JsonThreadLocal.findThreadLocal().get().json2listMap(jsonData);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		for (Map<String, Object> dataMap : dataMapList) {
			tableName = (String) dataMap.get("tableName");
			pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
			pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
			List queryFieldList = (List) dataMap.get("queryField");
			List whereFieldList = (List) dataMap.get("whereField");
			List orderFieldList = (List) dataMap.get("orderField");
			for (int i = 0; i < queryFieldList.size(); i++) {
				MorphDynaBean morphDynaBean = (MorphDynaBean) queryFieldList.get(i);
				columnName = (String) morphDynaBean.get("columnName");
				String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
				bean.getQueryFieldList().add(queryFieldMapKey);
			}
			for (int i = 0; i < whereFieldList.size(); i++) {
				sqlCustomQueryWhereBean = new SqlCustomWhereDto();
				MorphDynaBean morphDynaBean = (MorphDynaBean) whereFieldList.get(i);
				comment = (String) morphDynaBean.get("comment");
				// log.trace("comment=" + comment);
				typeOperate = (String) morphDynaBean.get("typeOperate");
				columnName = (String) morphDynaBean.get("columnName");
				String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
				sqlCustomQueryWhereBean.setWhereFieldKey(whereFieldMapKey);
				sqlCustomQueryWhereBean.setColumnName(columnName);
				sqlCustomQueryWhereBean.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
				whereFieldListReturn.add(sqlCustomQueryWhereBean);
				bean.getWhereFieldList().add(whereFieldMapKey);
				// bean.getValueMap().put(whereFieldMapKey,
				// parameterMap.get(whereFieldMapKey));
			}
			for (int i = 0; i < orderFieldList.size(); i++) {
				MorphDynaBean morphDynaBean = (MorphDynaBean) orderFieldList.get(i);
				typeOperate = (String) morphDynaBean.get("typeOperate");
				columnName = (String) morphDynaBean.get("columnName");
				String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
				bean.getOrderFieldList().add(orderFieldMapKey);
			}
			// 只取一条
			break;
		}
		return whereFieldListReturn;
	}
	/**
	 * 
	 * 执行SQL自定义查询,返回结果
	 * 
	 */
	public PageCoreBean<Map<String, Object>> doSqlCustomQueryByJsonData(String jsonStr, Connection conn,
			Map<String, Object> parameterMap) throws SQLException {
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		SqlCustomBean bean = new SqlCustomBean();
		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		String jsonData = jsonMap.get("data").toString();
		List<Map<String, Object>> dataMapList = JsonThreadLocal.findThreadLocal().get().json2listMap(jsonData);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		for (Map<String, Object> dataMap : dataMapList) {
			tableName = (String) dataMap.get("tableName");
			pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "page", 1);
			pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
			List queryFieldList = (List) dataMap.get("queryField");
			List whereFieldList = (List) dataMap.get("whereField");
			List orderFieldList = (List) dataMap.get("orderField");
			for (int i = 0; i < queryFieldList.size(); i++) {
				MorphDynaBean morphDynaBean = (MorphDynaBean) queryFieldList.get(i);
				columnName = (String) morphDynaBean.get("columnName");
				String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
				bean.getQueryFieldList().add(queryFieldMapKey);
			}
			for (int i = 0; i < whereFieldList.size(); i++) {
				MorphDynaBean morphDynaBean = (MorphDynaBean) whereFieldList.get(i);
				typeOperate = (String) morphDynaBean.get("typeOperate");
				columnName = (String) morphDynaBean.get("columnName");
				String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
				bean.getWhereFieldList().add(whereFieldMapKey);
				bean.getValueMap().put(whereFieldMapKey, parameterMap.get(whereFieldMapKey));
			}
			for (int i = 0; i < orderFieldList.size(); i++) {
				MorphDynaBean morphDynaBean = (MorphDynaBean) orderFieldList.get(i);
				typeOperate = (String) morphDynaBean.get("typeOperate");
				columnName = (String) morphDynaBean.get("columnName");
				String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
				bean.getOrderFieldList().add(orderFieldMapKey);
			}
			// 只取一条
			break;
		}
		IJdbcUtil jdbcUtil = JdbcFactory.createApi(conn);
		bean.setTableName(tableName);
		sql = jdbcUtil.doBuildSql(bean);
		List<Object> valueObjectList = bean.findValueList();
		PageCoreBean<Map<String, Object>> page = jdbcUtil.findPageBean(conn, pageIndex, pageSize, sql, valueObjectList,
				null);
		return page;
	}
	/**
	 * 执行SQL自定义查询,查找query查询的列
	 * 
	 */
	public List<SqlCustomQueryDto> findQueryFieldListForJSONObject(String jsonStr) throws SQLException {
		List<SqlCustomQueryDto> queryFieldListReturn = new ArrayList<SqlCustomQueryDto>();
		SqlCustomBean sqlCustomBean = new SqlCustomBean();
		SqlCustomQueryDto sqlCustomQueryDto = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List queryFieldList = (List) dataMap.get("queryField");
		List whereFieldList = (List) dataMap.get("whereField");
		List orderFieldList = (List) dataMap.get("orderField");
		for (int i = 0; i < queryFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) queryFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			sqlCustomBean.getQueryFieldList().add(queryFieldMapKey);
			sqlCustomQueryDto = new SqlCustomQueryDto();
			sqlCustomQueryDto.setQueryFieldKey(queryFieldMapKey);
			sqlCustomQueryDto.setColumnName(columnName);
			sqlCustomQueryDto.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			queryFieldListReturn.add(sqlCustomQueryDto);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) whereFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonObject.get("typeOperate");
			columnName = (String) jsonObject.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getWhereFieldList().add(whereFieldMapKey);
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) orderFieldList.get(i);
			typeOperate = (String) jsonObject.get("typeOrder");
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getOrderFieldList().add(orderFieldMapKey);
		}
		return queryFieldListReturn;
	}
	/**
	 * 执行SQL自定义查询,查找where条件的列
	 * 
	 */
	public List<SqlCustomWhereDto> findWhereFieldListForJSONObject(String jsonStr) throws SQLException {
		List<SqlCustomWhereDto> whereFieldListReturn = new ArrayList<SqlCustomWhereDto>();
		SqlCustomBean bean = new SqlCustomBean();
		SqlCustomWhereDto sqlCustomQueryWhereBean = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List queryFieldList = (List) dataMap.get("queryField");
		List whereFieldList = (List) dataMap.get("whereField");
		List orderFieldList = (List) dataMap.get("orderField");
		for (int i = 0; i < queryFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) queryFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			bean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) whereFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonObject.get("typeOperate");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getWhereFieldList().add(whereFieldMapKey);
			sqlCustomQueryWhereBean = new SqlCustomWhereDto();
			sqlCustomQueryWhereBean.setWhereFieldKey(whereFieldMapKey);
			sqlCustomQueryWhereBean.setColumnName(columnName);
			sqlCustomQueryWhereBean.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			whereFieldListReturn.add(sqlCustomQueryWhereBean);
			// bean.getValueMap().put(whereFieldMapKey,
			// parameterMap.get(whereFieldMapKey));
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) orderFieldList.get(i);
			typeOperate = (String) jsonObject.get("typeOrder");
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getOrderFieldList().add(orderFieldMapKey);
		}
		return whereFieldListReturn;
	}
	/**
	 * 执行SQL自定义查询,查找order排序的列
	 * 
	 */
	public List<SqlCustomOrderDto> findOrderFieldListForJSONObject(String jsonStr) throws SQLException {
		List<SqlCustomOrderDto> orderFieldListReturn = new ArrayList<SqlCustomOrderDto>();
		SqlCustomBean sqlCustomBean = new SqlCustomBean();
		SqlCustomOrderDto sqlCustomOrderDto = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List queryFieldList = (List) dataMap.get("queryField");
		List whereFieldList = (List) dataMap.get("whereField");
		List orderFieldList = (List) dataMap.get("orderField");
		for (int i = 0; i < queryFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) queryFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			sqlCustomBean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) whereFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonObject.get("typeOperate");
			columnName = (String) jsonObject.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getWhereFieldList().add(whereFieldMapKey);
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) orderFieldList.get(i);
			typeOperate = (String) jsonObject.get("typeOrder");
			columnName = (String) jsonObject.get("columnName");
			comment = (String) jsonObject.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getOrderFieldList().add(orderFieldMapKey);
			sqlCustomOrderDto = new SqlCustomOrderDto();
			sqlCustomOrderDto.setOrderFieldKey(orderFieldMapKey);
			sqlCustomOrderDto.setColumnName(columnName);
			sqlCustomOrderDto.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			orderFieldListReturn.add(sqlCustomOrderDto);
		}
		return orderFieldListReturn;
	}
	/**
	 * 
	 * 执行SQL自定义查询,返回结果
	 * 
	 */
	public PageCoreBean<Map<String, Object>> doSqlCustomQueryForJSONObject(String jsonStr, Connection conn,
			Map<String, Object> parameterMap) throws SQLException {
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		SqlCustomBean bean = new SqlCustomBean();
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List queryFieldList = (List) dataMap.get("queryField");
		List whereFieldList = (List) dataMap.get("whereField");
		List orderFieldList = (List) dataMap.get("orderField");
		for (int i = 0; i < queryFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) queryFieldList.get(i);
			columnName = (String) jsonObject.get("columnName");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			bean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) whereFieldList.get(i);
			typeOperate = (String) jsonObject.get("typeOperate");
			columnName = (String) jsonObject.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getWhereFieldList().add(whereFieldMapKey);
			bean.getValueMap().put(whereFieldMapKey, parameterMap.get(whereFieldMapKey));
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			JSONObject jsonObject = (JSONObject) orderFieldList.get(i);
			typeOperate = (String) jsonObject.get("typeOrder");
			log.trace("typeOperate=" + typeOperate);
			columnName = (String) jsonObject.get("columnName");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getOrderFieldList().add(orderFieldMapKey);
		}
		IJdbcUtil jdbcUtil = JdbcFactory.createApi(conn);
		bean.setTableName(tableName);
		sql = jdbcUtil.doBuildSql(bean);
		log.trace("sql=" + sql);
		List<Object> valueObjectList = bean.findValueList();
		PageCoreBean<Map<String, Object>> page = jdbcUtil.findPageBean(conn, pageIndex, pageSize, sql, valueObjectList,
				null);
		return page;
	}
	/**
	 * 执行SQL自定义查询,查找query查询的列
	 * 
	 */
	public List<SqlCustomQueryDto> findQueryFieldList(String jsonStr) throws SQLException {
		List<SqlCustomQueryDto> queryFieldListReturn = new ArrayList<SqlCustomQueryDto>();
		SqlCustomBean sqlCustomBean = new SqlCustomBean();
		SqlCustomQueryDto sqlCustomQueryDto = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List<Map<String, Object>> queryFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("queryField"));
		List<Map<String, Object>> whereFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("whereField"));
		List<Map<String, Object>> orderFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("orderField"));
		for (int i = 0; i < queryFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) queryFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			sqlCustomBean.getQueryFieldList().add(queryFieldMapKey);
			sqlCustomQueryDto = new SqlCustomQueryDto();
			sqlCustomQueryDto.setQueryFieldKey(queryFieldMapKey);
			sqlCustomQueryDto.setColumnName(columnName);
			sqlCustomQueryDto.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			queryFieldListReturn.add(sqlCustomQueryDto);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) whereFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getWhereFieldList().add(whereFieldMapKey);
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) orderFieldList.get(i);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getOrderFieldList().add(orderFieldMapKey);
		}
		return queryFieldListReturn;
	}
	/**
	 * 执行SQL自定义查询,查找where条件的列
	 * 
	 */
	public List<SqlCustomWhereDto> findWhereFieldList(String jsonStr) throws SQLException {
		List<SqlCustomWhereDto> whereFieldListReturn = new ArrayList<SqlCustomWhereDto>();
		SqlCustomBean bean = new SqlCustomBean();
		SqlCustomWhereDto sqlCustomQueryWhereBean = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		log.trace("j=" + dataMap.get("queryField"));
		List<Map<String, Object>> queryFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("queryField"));
		List<Map<String, Object>> whereFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("whereField"));
		List<Map<String, Object>> orderFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("orderField"));
		for (int i = 0; i < queryFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) queryFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			bean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) whereFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonMap.get("typeOperate");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getWhereFieldList().add(whereFieldMapKey);
			sqlCustomQueryWhereBean = new SqlCustomWhereDto();
			sqlCustomQueryWhereBean.setWhereFieldKey(whereFieldMapKey);
			sqlCustomQueryWhereBean.setColumnName(columnName);
			sqlCustomQueryWhereBean.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			whereFieldListReturn.add(sqlCustomQueryWhereBean);
			// bean.getValueMap().put(whereFieldMapKey,
			// parameterMap.get(whereFieldMapKey));
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) orderFieldList.get(i);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getOrderFieldList().add(orderFieldMapKey);
		}
		return whereFieldListReturn;
	}
	/**
	 * 执行SQL自定义查询,查找order排序的列
	 * 
	 */
	public List<SqlCustomOrderDto> findOrderFieldList(String jsonStr) throws SQLException {
		List<SqlCustomOrderDto> orderFieldListReturn = new ArrayList<SqlCustomOrderDto>();
		SqlCustomBean sqlCustomBean = new SqlCustomBean();
		SqlCustomOrderDto sqlCustomOrderDto = null;
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		String comment = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List<Map<String, Object>> queryFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("queryField"));
		List<Map<String, Object>> whereFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("whereField"));
		List<Map<String, Object>> orderFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("orderField"));
		for (int i = 0; i < queryFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) queryFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			sqlCustomBean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) whereFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			// log.trace("comment=" + comment);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getWhereFieldList().add(whereFieldMapKey);
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) orderFieldList.get(i);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			comment = (String) jsonMap.get("comment");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			sqlCustomBean.getOrderFieldList().add(orderFieldMapKey);
			sqlCustomOrderDto = new SqlCustomOrderDto();
			sqlCustomOrderDto.setOrderFieldKey(orderFieldMapKey);
			sqlCustomOrderDto.setColumnName(columnName);
			sqlCustomOrderDto.setComment(BeanThreadLocal.findThreadLocal().get().find(comment, columnName));
			orderFieldListReturn.add(sqlCustomOrderDto);
		}
		return orderFieldListReturn;
	}
	/**
	 * 
	 * 执行SQL自定义查询,返回结果
	 * 
	 */
	public PageCoreBean<Map<String, Object>> doSqlCustomQuery(String jsonStr, Connection conn,
			Map<String, Object> parameterMap) throws SQLException {
		String sql = null;
		String tableName = null;
		String queryFieldPrefix = "Q";
		String whereFieldPrefix = "W";
		String orderFieldPrefix = "O";
		Integer pageIndex = null;
		Integer pageSize = null;
		SqlCustomBean bean = new SqlCustomBean();
		Map<String, Object> dataMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);
		// W#SYS_MENU_ID_#LI
		String typeOperate = null;
		String columnName = null;
		// 取数据
		tableName = (String) dataMap.get("tableName");
		pageIndex = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageIndex", 1);
		pageSize = JsonThreadLocal.findThreadLocal().get().findInt(dataMap, "pageSize", 10);
		List<Map<String, Object>> queryFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("queryField"));
		List<Map<String, Object>> whereFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("whereField"));
		List<Map<String, Object>> orderFieldList = JsonThreadLocal.findThreadLocal().get()
				.json2listMap((String) dataMap.get("orderField"));
		for (int i = 0; i < queryFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) queryFieldList.get(i);
			columnName = (String) jsonMap.get("columnName");
			String queryFieldMapKey = queryFieldPrefix + "#" + columnName;
			bean.getQueryFieldList().add(queryFieldMapKey);
		}
		for (int i = 0; i < whereFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) whereFieldList.get(i);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			String whereFieldMapKey = whereFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getWhereFieldList().add(whereFieldMapKey);
			bean.getValueMap().put(whereFieldMapKey, parameterMap.get(whereFieldMapKey));
		}
		for (int i = 0; i < orderFieldList.size(); i++) {
			Map<String, Object> jsonMap = (Map<String, Object>) orderFieldList.get(i);
			typeOperate = (String) jsonMap.get("typeOperate");
			columnName = (String) jsonMap.get("columnName");
			String orderFieldMapKey = orderFieldPrefix + "#" + columnName + "#" + typeOperate;
			bean.getOrderFieldList().add(orderFieldMapKey);
		}
		IJdbcUtil jdbcUtil = JdbcFactory.createApi(conn);
		bean.setTableName(tableName);
		sql = jdbcUtil.doBuildSql(bean);
		log.trace("sql=" + sql);
		log.trace("sql=" + sql);
		List<Object> valueObjectList = bean.findValueList();
		PageCoreBean<Map<String, Object>> page = jdbcUtil.findPageBean(conn, pageIndex, pageSize, sql, valueObjectList,
				null);
		return page;
	}
}
