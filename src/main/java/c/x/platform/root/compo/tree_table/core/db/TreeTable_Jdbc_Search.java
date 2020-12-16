package c.x.platform.root.compo.tree_table.core.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.bean.TreeTableNodeBaseBean;

/**
 * 
 * 
 * 代码写得不错
 * 
 */
public class TreeTable_Jdbc_Search extends TreeTable_Jdbc_Ay {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 通过request的post查找所有的;
	 * 
	 * 树名字搜索;
	 * 
	 * @throws SQLException
	 * 
	 */
	public List<TreeTableNodeBaseBean> search_list(
			TreeTableParameter treeParameter) throws SQLException {
		List<TreeTableNodeBaseBean> list_treeNode = null;
		/**
		 * 增加搜索
		 */
		if (StringUtil.isNotBlank(treeParameter.getSearch())) {
			String sql = treeParameter.getSql();
			List<String> paths = this.listPaths_BySql(treeParameter, sql);
			if (paths.size() > 0) {
			} else {

				return null;
			}
			list_treeNode = this.listTreeNode_By_sql$paths(paths,
					treeParameter, sql);
			if (list_treeNode.size() == 0) {

				return null;
			}
			/**
			 * 不搜索
			 */
		} else {
			list_treeNode = list_sql_by_editPath(treeParameter,
					treeParameter.getSql());
		}
		return list_treeNode;
	}

	/**
	 * 
	 * 
	 * 搜索单引号时有bug[treeParameter.setSearch("'");]
	 * 
	 * 
	 * 通过sql查找所有的path
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param treeTableParameter
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<String> listPaths_BySql_v1(
			TreeTableParameter treeTableParameter, String sql)
			throws SQLException {
		// 连接
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil dao = jdbcTool.getJdbcUtil();
		Connection conn = dao.getConnection();

		// 声明
		ArrayList<Object> parameterList = new ArrayList<Object>();
		// JdbcMapQuery_SimpleMethod dao = new JdbcMapQuery_SimpleMethod();

		StringBuilder sb = new StringBuilder();
		List<String> listString = new ArrayList<String>();
		/**
		 * 拆分sql(估计以后bug挺多的)
		 */
		String sql_select = null;
		String sql_order = null;
		int index = sql.indexOf("order");
		if (index >= 0) {
			int length = sql.length();
			sql_select = sql.substring(0, index);
			sql_order = sql.substring(index, length);
		}
		if (index >= 0) {
			// sql_select
			sb.append(sql_select);
		} else {
			// sql
			sb.append(sql);
		}
		/**
		 * where
		 */
		// {
		if (sql.contains("where")) {
			sb.append(" and ");
			sb.append(" ( ");
		} else {
			// where
			sb.append(" where ");
		}
		sb.append(treeTableParameter.getColumnName());
		sb.append(" LIKE '%");
		sb.append(treeTableParameter.getSearch());
		sb.append("%'  ");
		if (sql.contains("where")) {
			sb.append(" ) ");
		} else {
		}
		// }
		/**
		 * where
		 */
		/**
		 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
		 */
		// {
		if (StringUtil.isNotBlank(treeTableParameter.getEdit_path())) {
			sb.append(" and ");
			sb.append(" ( ");
			sb.append(treeTableParameter.getColumnPath());
			sb.append(" not like '");
			sb.append(treeTableParameter.getEdit_path());
			sb.append("%'");
			sb.append(")");
		}
		// }
		/**
		 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
		 */
		if (index >= 0) {
			// sql_order
			sb.append("  ");
			sb.append(sql_order);
		} else {
		}
		log.trace("通过sql查找所有的path;sql=" + sb.toString());
		List<Map<String, Object>> listMap = dao.findMapList(conn,
				sb.toString(), parameterList);
		for (Map<String, Object> jsonMap : listMap) {
			String path = (String) jsonMap.get(treeTableParameter
					.getColumnPath());
			listString.add(path);
		}
		return listString;
	}

	/**
	 * 通过sql查找所有的path
	 * 
	 * @param treeTableParameter
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<String> listPaths_BySql(TreeTableParameter treeTableParameter,
			String sql) throws SQLException {
		// 连接
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil dao = jdbcTool.getJdbcUtil();
		Connection conn = dao.getConnection();

		// 声明
		ArrayList<Object> parameterList = new ArrayList<Object>();
		// JdbcMapQuery_SimpleMethod dao = new JdbcMapQuery_SimpleMethod();

		StringBuilder sb = new StringBuilder();
		List<String> listString = new ArrayList<String>();
		/**
		 * 拆分sql(估计以后bug挺多的)
		 */
		String sql_select = null;
		String sql_order = null;
		int index = sql.indexOf("order");
		if (index >= 0) {
			int length = sql.length();
			sql_select = sql.substring(0, index);
			sql_order = sql.substring(index, length);
		}
		if (index >= 0) {
			// sql_select
			sb.append(sql_select);
		} else {
			// sql
			sb.append(sql);
		}
		/**
		 * where
		 */
		// {
		if (sql.contains("where")) {
			sb.append(" and ");
			sb.append(" ( ");
		} else {
			// where
			sb.append(" where ");
		}
		sb.append(treeTableParameter.getColumnName());
		// sb.append(" LIKE '%");
		// sb.append(treeTableParameter.getSearch());
		// sb.append("%'  ");
		sb.append(" LIKE ");
		sb.append("?");
		sb.append("  ");
		if (sql.contains("where")) {
			sb.append(" ) ");
		} else {
		}
		// }
		/**
		 * where
		 */
		/**
		 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
		 */
		// {
		if (StringUtil.isNotBlank(treeTableParameter.getEdit_path())) {
			sb.append(" and ");
			sb.append(" ( ");
			sb.append(treeTableParameter.getColumnPath());
			sb.append(" not like '");
			sb.append(treeTableParameter.getEdit_path());
			sb.append("%'");
			sb.append(")");
		}
		// }
		/**
		 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
		 */
		if (index >= 0) {
			// sql_order
			sb.append("  ");
			sb.append(sql_order);
		} else {
		}
		log.trace("通过sql查找所有的path;sql=" + sb.toString());
		// 加上like参数的值
		parameterList.add("%" + treeTableParameter.getSearch() + "%");
		List<Map<String, Object>> listMap = dao.findMapList(conn,
				sb.toString(), parameterList);
		for (Map<String, Object> jsonMap : listMap) {
			String path = (String) jsonMap.get(treeTableParameter
					.getColumnPath());
			listString.add(path);
		}
		return listString;
	}

	/**
	 * 通过path和sql查找所有的;
	 * 
	 * 找出所有父节点 ;
	 * 
	 * 
	 * 
	 * @param treeTableParameter
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<TreeTableNodeBaseBean> listTreeNode_By_sql$paths(
			List<String> paths, TreeTableParameter treeTableParameter,
			String sql) throws SQLException {
		if (paths.size() > 0) {
		} else {
			return null;
		}
		// 声明
		StringBuilder sb = new StringBuilder();
		/**
		 * 拆分sql(估计以后bug挺多的)
		 */
		String sql_select = null;
		String sql_order = null;
		int index = sql.indexOf("order");
		if (index >= 0) {
			int length = sql.length();
			sql_select = sql.substring(0, index);
			sql_order = sql.substring(index, length);
		}
		if (index >= 0) {
			// sql_select
			sb.append(sql_select);
		} else {
			// sql
			sb.append(sql);
		}
		/**
		 * where
		 */
		if (sql.contains("where")) {
			sb.append(" and ");
			sb.append(" ( ");
		} else {
			sb.append(" where ");
		}
		for (int i = 0; i < paths.size(); i++) {
			String path = paths.get(i);
			if (i == 0) {
				sb.append(" '");
			} else {
				sb.append(" or ");
				sb.append(" '");
			}
			sb.append(path);
			sb.append("' ");
			sb.append(" LIKE CONCAT(");
			sb.append(treeTableParameter.getColumnPath());
			sb.append(",'%')");
		}
		if (sql.contains("where")) {
			sb.append(" ) ");
		} else {
		}
		/**
		 * where
		 */
		if (index >= 0) {
			// sql_order
			sb.append("  ");
			sb.append(sql_order);
		} else {
		}
		log.trace("搜索paths时  sql=" + sb.toString());
		return list_sql(treeTableParameter, sb.toString());
	}
}
