package c.x.platform.admin.fav.sys.sys_org_info.action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.tree_table.action.CommTreeTableAction;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
public class SysOrgInfoParentAction extends CommTreeTableAction {
	@Override
	public TreeTableParameter initParameter() throws Exception {
		/**
		 * 设置树的参数
		 */
		TreeTableParameter treeTableParameter = new TreeTableParameter();
		// 编辑时的id
		String path = (String) request.getParameter("path");
		treeTableParameter.setEdit_path(path);
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
				.setSql_find_root("SELECT * FROM sys_org where tree_code=0001");
		treeTableParameter.setSql("SELECT * FROM sys_org_info");
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
		String sql = "SELECT * FROM sys_org_info";
		List<Map<String, Object>> listMap = this.findDao().findMapList(sql,
				null);
		for (Map<String, Object> jsonMap : listMap) {
			sb.setLength(0);
			// id
			String id = (String) jsonMap.get("id");
			// 名称
			if (false) {
				sb.append("<td>");
				sb.append((String) jsonMap.get("name"));
				sb.append("</td>");
			}
			// url
			if (false) {
				sb.append("<td>");
				sb.append((String) jsonMap.get("url"));
				sb.append("</td>");
			}
			// 操作
			sb.append("<td>");
			sb.append("<button onclick=\"selectRecord('" + id
					+ "');\"  class=\"btn btn-link\">选择</button>");
			sb.append("</td>");
			tds.put(id.toString(), sb.toString());
		}
		return tds;
	}
}
