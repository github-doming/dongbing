package all.app_admin.app.app_group.t.action.menu;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.tree_table.action.BaseTreeTableAction;
/**
 * 
 * 角色授权;
 * 
 * 角色与菜单绑定；
 * 
 * 显示所有菜单列表;
 * 
 * 
 * 
 */
public class AppMenuGroupTListAction extends BaseTreeTableAction {
	public AppMenuGroupTListAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public TreeTableParameter initParameter() throws Exception {
		// int i=1/0;
		/**
		 * 设置树的参数
		 */
		TreeTableParameter treeTableParameter = new TreeTableParameter();
		// 读取search
	

		if (StringUtil.isNotBlank(request.getParameter("search"))) {
			treeTableParameter.setSearch(request.getParameter("search"));
			// System.println.out("搜索的值=" + xml_node.getSearch());
		} else {
		}

		// 读取state


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

		// 设置sql
		String tenantCode = this.findCurrentUserTenantCode();
		treeTableParameter.setSql_find_root(
				"SELECT * FROM APP_MENU  where TREE_CODE_=0001 and TENANT_CODE_='" + tenantCode + "'");
		treeTableParameter.setSql("select * FROM APP_MENU  t1 where PERMISSION_GRADE_>="
				+ this.findCurrentUserPermissionGrade() + "  order by t1.SN_ asc,t1.APP_MENU_ID_ asc");
		treeTableParameter.setColumnTreeCode("TREE_CODE_");
		treeTableParameter.setColumnId("APP_MENU_ID_");
		treeTableParameter.setColumnParentId("PARENT_");
		treeTableParameter.setColumnName("APP_MENU_NAME_");
		treeTableParameter.setColumnPath("PATH_");
		treeTableParameter.setColumnUrl("URL_");
		treeTableParameter.setColumnPic("PIC_");
		treeTableParameter.setColumnPicOpen("PIC_OPEN_");
		treeTableParameter.setColumnPicClose("PIC_CLOSE_");
		treeTableParameter.setColumnSn("sn_".toUpperCase());
		// 打开所有
		// xml_node.setOpen_all(true);
		// 有根节点
		treeTableParameter.setRoot_enable(true);
		// xml_node.setRootEnable(false);
		// root的id
		// xml_node.setRootId("1");
		// 上下文
		treeTableParameter.setContext_path(this.request.getContextPath());
		// key
		treeTableParameter.setKey("my");
		// 其它tds
		Map<String, Object> tds = this.tds();
		treeTableParameter.setTds(tds);
		// treeTableParameter.setModel$tree_table(true);
		treeTableParameter.setModel$tree_checkbox(true);
		return treeTableParameter;
	}
	@Override
	public String execute() throws Exception {
		// 设置参数
		TreeTableParameter treeParameter = this.initParameter();
		// 执行
		String str = this.execute_printlnForHTML(treeParameter);
		request.setAttribute("c_tree", str);
		/**
		 * 角色与菜单
		 */
		String group_id = request.getParameter("group_id");
		request.setAttribute("group_id", group_id);
		AppGroupTService groupService = new AppGroupTService();
		String pathList = groupService.findMenuPathListByRoleId2String(group_id);
		request.setAttribute("list_path", pathList);
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
		String sql = "SELECT * FROM APP_MENU ";
		List<Map<String, Object>> listMap = this.findDao().findMapList(sql, null);
		for (Map<String, Object> jsonMap : listMap) {
			// 要通过使用sb.setLength(0);来清空StringBuilder对象中的内容效率最高。
			sb.setLength(0);
			// id
			String id = (String) jsonMap.get("APP_MENU_ID_");
			// 名称
			if (false) {
				sb.append("<td>");
				sb.append((String) jsonMap.get("APP_MENU_NAME_"));
				sb.append("</td>");
			}
			// url
			sb.append("<td>");
			sb.append((String) jsonMap.get("URL_"));
			sb.append("</td>");
			// 排序
			sb.append("<td>");
			sb.append((Integer) jsonMap.get("SN_"));
			sb.append("</td>");
			// 操作
			sb.append("<td>");
			// sb.append("abc");
			if (false) {
				sb.append("<button onclick=\"editRecord('" + id + "');\"  class=\"btn btn-link\">编辑</button>");
				sb.append("<button onclick=\"delRecord(' " + id + "   ');\"  class=\"btn btn-link\">删除</button>");
				sb.append("<button onclick=\"newRecord(' " + id + "   ');\"  class=\"btn btn-link\">添加下级菜单</button>");
				sb.append("</td>");
			}
			tds.put(id.toString(), sb.toString());
		}
		return tds;
	}
}
