package c.a.tools.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;

public class JdbcTool extends JdbcCoreTool {

	@Override
	public IJdbcUtil getJdbcUtil() {

		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Class classObj,
			Connection conn, PageCoreBean<Map<String, Object>> page,
			String sql, List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {

		return null;
	}

	@Override
	public List findObjectList(Class c_Class, Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {

		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Class c_Class,
			Connection conn, int pageNo, int pageSize, String sql,
			List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {

		return null;
	}

	@Override
	public List findObjectList(Class c_Class, Connection conn, int pageNo,
			int pageSize, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {

		return null;
	}

	@Override
	public PageCoreBean<Map<String, Object>> findPageBean(Class c_Class,
			Connection conn, String sql, List<Object> parameterList,
			String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws Exception {

		return null;
	}

}
