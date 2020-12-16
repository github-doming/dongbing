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

public class TreeTable_Jdbc_Ay {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 通过sql查找所有的
	 * 
	 * @param treeTableParameter
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<TreeTableNodeBaseBean> list_sql_by_editPath(
			TreeTableParameter treeTableParameter, String sql)
			throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		/**
		 * 编辑时,选择上一级时,不能选择本身或该节点所有的孩子;
		 */
		if (StringUtil.isNotBlank(treeTableParameter.getEdit_path())) {
			if (sb.toString().toLowerCase().contains("where")) {
				sb.append(" and ");
			} else {
				sb.append(" where ");
			}
			sb.append(treeTableParameter.getColumnPath());
			sb.append(" not like '");
			sb.append(treeTableParameter.getEdit_path());
			sb.append("%'");
		}
		if (true) {
			log.trace("编辑表单选择上一级显示树去掉edit_path，编辑 sql=" + sb.toString());
		}
		return this.list_sql(treeTableParameter, sb.toString());
	}

	/**
	 * 
	 * 读取数据库
	 * 
	 * 
	 * 通过sql查找所有的
	 * 
	 * @param treeParameter
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<TreeTableNodeBaseBean> list_sql(
			TreeTableParameter treeParameter, String sql) throws SQLException {
		/**
		 * 得到数据库连接
		 */
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil dao = jdbcTool.getJdbcUtil();
		Connection conn = dao.getConnection();

		ArrayList parameterList = new ArrayList();
		// JdbcMapQuery_SimpleMethod dao = new JdbcMapQuery_SimpleMethod();

		List<TreeTableNodeBaseBean> list_treeNode = new ArrayList<TreeTableNodeBaseBean>();
		List<Map<String, Object>> listMap = dao.findMapList(conn, sql,
				parameterList);
		for (Map<String, Object> jsonMap : listMap) {
			// 添加菜单
			TreeTableNodeBaseBean node = new TreeTableNodeBaseBean();

			String id = (String) jsonMap.get(treeParameter.getColumnId()
					.toUpperCase());
			String name = (String) jsonMap.get(treeParameter.getColumnName()
					.toUpperCase());
			String pic = (String) jsonMap.get(treeParameter.getColumnPic()
					.toUpperCase());
			String pic_open = (String) jsonMap.get(treeParameter
					.getColumnPicOpen().toUpperCase());
			String pic_close = (String) jsonMap.get(treeParameter
					.getColumnPicClose().toUpperCase());
			String parentId = (String) jsonMap.get(treeParameter
					.getColumnParentId().toUpperCase());

			String url = (String) jsonMap.get(treeParameter.getColumnUrl()
					.toUpperCase());
			Integer SN_ = (Integer) jsonMap.get(treeParameter
					.getColumnSn().toUpperCase());
			String treeCode = (String) jsonMap.get(treeParameter
					.getColumnTreeCode().toUpperCase());
			String path = (String) jsonMap.get(treeParameter.getColumnPath()
					.toUpperCase());
			node.setId(id.toString());
			node.setName(name);
			if (parentId != null) {
				node.setParent_id(parentId.toString());
			}
			node.setUrl(url);
			node.setSequence(SN_.toString());
			node.setTree_code(treeCode);
			node.setPic(pic);
			node.setPic_open(pic_open);
			node.setPic_close(pic_close);
			node.setPath(path);
			list_treeNode.add(node);
		}
		return list_treeNode;
	}
}
