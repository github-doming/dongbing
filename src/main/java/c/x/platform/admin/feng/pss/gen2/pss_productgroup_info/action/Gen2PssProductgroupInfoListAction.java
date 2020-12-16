package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.action;

import java.sql.SQLException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

import c.a.util.core.string.StringUtil;
import c.x.platform.root.tree_table.action.BaseTreeTableRoleAction;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig;
import c.a.util.core.bean.BeanThreadLocal;
public class Gen2PssProductgroupInfoListAction extends BaseTreeTableRoleAction {
	@Override
	public TreeTableParameter initParameter() throws Exception {
		/**
		 * 设置树的参数
		 */
		TreeTableParameter treeTableParameter = new TreeTableParameter();
		/**
		 * 读取search
		 */
		// {
		if (true) {
			if (StringUtil.isNotBlank(request.getParameter("search"))) {
				treeTableParameter.setSearch(request.getParameter("search"));
			} else {
			}
		}
		// }
		/**
		 * 读取search
		 */
		/**
		 * 读取state
		 */
		// {
		if (true) {
			if (StringUtil.isNotBlank(request.getParameter("state"))) {
				String state = (String) request.getParameter("state");
				if (state.equals(TreeTableConfig.command_openAll)) {
					treeTableParameter.setOpen_all(true);
				}
				if (state.equals(TreeTableConfig.command_closeAll)) {
					treeTableParameter.setClose_all(true);
				}
			} else {
				treeTableParameter.setOpen_all(true);
			}
		}
		// }
		/**
		 * 读取state
		 */
		treeTableParameter
				.setSql_find_root("SELECT * FROM pss_productgroup_info where tree_code=0001");
		treeTableParameter
				.setSql("select * from pss_productgroup_info t1 order by t1.sequence asc,t1.id asc");
		treeTableParameter.setColumnTreeCode("tree_code");
		treeTableParameter.setColumnId("id");
		treeTableParameter.setColumnParentId("parent");
		treeTableParameter.setColumnName("name");
		treeTableParameter.setColumnPath("path");
		treeTableParameter.setColumnUrl("url");
		treeTableParameter.setColumnPic("pic");
		treeTableParameter.setColumnPicOpen("pic_open");
		treeTableParameter.setColumnPicClose("pic_close");
		// 有根节点
		treeTableParameter.setRoot_enable(true);
		// 上下文
		treeTableParameter.setContext_path(this.request.getContextPath());
		// key
		treeTableParameter.setKey("my");
		// 其它tds
		Map<String, Object> tds = this.tds();
		treeTableParameter.setTds(tds);
		treeTableParameter.setModel$tree_table(true);
		return treeTableParameter;
	}
	@Override
	public String execute() throws Exception {
		// 设置参数
		TreeTableParameter treeParameter = this.initParameter();
		// 执行
		String str = this.execute_printlnForHTML(treeParameter);
		request.setAttribute("c_tree", str);
		return "index";
	}
	/**
	 * 
	 * 构造html的td
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> tds() throws Exception {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> tds = new HashMap<String, Object>();
		String sql = "SELECT * FROM pss_productgroup_info";
		List<Map<String, Object>> listMap = this.findDao().findMapList(sql,
				null);
		for (Map<String, Object> entityMap : listMap) {
			sb.setLength(0);
			// id
			String id = (String) entityMap.get("id");
			// 名称
			if (false) {
				sb.append("<td>");
				sb.append((String) entityMap.get("name"));
				sb.append("</td>");
			}
			// url
			// sb.append("<td>");
			sb.append("<td class='class_crud_td_white'>");
			sb.append((String) entityMap.get("url"));
			sb.append("</td>");
			// 排序
			// sb.append("<td>");
			sb.append("<td class='class_crud_td_white'>");
			sb.append((Integer) entityMap.get("sequence"));
			sb.append("</td>");
			// 操作
			// sb.append("<td>");
			sb.append("<td class='class_crud_td_white'>");
			// sb.append("abc");
			sb.append("<input type='button' onclick=\"editRecord(' " + id
					+ "   ');\"  class=\"btn btn-link\" value='编辑'></input>");
			sb.append("<input type='button' onclick=\"delRecord(' " + id
					+ "   ');\"  class=\"btn btn-link\" value='删除'></input>");
			sb.append("<input type='button' onclick=\"newRecord(' "
					+ id
					+ "   ');\"  class=\"btn btn-link\"  value='添加下级菜单'></input>");
			sb.append("</td>");
			tds.put(id.toString(), sb.toString());
		}
		return tds;
	}
}
