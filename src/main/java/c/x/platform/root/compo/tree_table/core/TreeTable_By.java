package c.x.platform.root.compo.tree_table.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;

public abstract class TreeTable_By extends TreeTable_Ay {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 找出根节点的id
	 * 
	 * @param sql
	 * @param treeParameter
	 * @return
	 * @throws SQLException
	 */
	public String findRootId(String sql, TreeTableParameter treeParameter)
			throws SQLException {
		if (StringUtil.isNotBlank(treeParameter.getRoot_id())) {
			return treeParameter.getRoot_id();
		}
		String root_id = null;
		/**
		 * 得到数据库连接
		 */
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil dao = jdbcTool.getJdbcUtil();
		Connection conn = dao.getConnection();

		// JdbcMapQuery_SimpleMethod dao = new JdbcMapQuery_SimpleMethod();

		List<Map<String, Object>> listMap = dao.findMapList(conn, sql, null);
		for (Map<String, Object> jsonMap : listMap) {
			root_id = (String) jsonMap.get(treeParameter.getColumnId().toUpperCase());
			break;
		}
		if (root_id == null) {
			AssertUtil.isNull(null, "找不到rootId");
			log.trace("找不到tree_code或者表中没有根节点的数据");
		}
		return root_id.toString();
	}
}
