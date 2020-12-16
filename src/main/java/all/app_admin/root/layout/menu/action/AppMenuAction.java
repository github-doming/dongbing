package all.app_admin.root.layout.menu.action;
import java.util.HashMap;
import java.util.Map;

import all.app_admin.root.layout.main.action.AppMainAction;
import all.app_admin.root.util.AppUtil;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.layout.menu.action.SysMenuAction;
public class AppMenuAction extends SysMenuAction {
	public AppMenuAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		String type = this.findCurrentUserType();
		if ("sys".equals(type)) {
			return this.executeSys();
		}
		if ("app".equals(type)) {
			return this.executeByApp();
		}
		return null;
	}
	public String executeByApp() throws Exception {
		AppUtil appUtil = new AppUtil();
		// 设置参数
		TreeTableParameter treeParameter = this.initParameterByApp();
		// 执行
		String str = this.execute_printlnForHTML(treeParameter);
		// log.trace("str=" + str);
		request.setAttribute("c_tree", str);
		String value = appUtil.findSSOByUserId(this.findCurrentAppUser().getAppUserId());
		request.setAttribute("sso", value);
		return "index";
	}
	public TreeTableParameter initParameterByApp() throws Exception {
		String parent_id = (String) request.getParameter("parent_id");
		request.setAttribute("parent_id", parent_id);
		// 2级菜单的第1个节点
		AppMainAction mainAction = new AppMainAction();
		// request必须设置，不然报空指针异常
		mainAction.setRequest(request);
		Map childMap = mainAction.findNodeNotChildByApp(parent_id);
		request.setAttribute("map_child", childMap);
		/**
		 * 设置树的参数
		 */
		TreeTableParameter treeTableParameter = new TreeTableParameter();
		// 读取search
		// {
		if (true) {
			if (StringUtil.isNotBlank(request.getParameter("search"))) {
				treeTableParameter.setSearch(request.getParameter("search"));
				// BaseSystem.out
				// .println("搜索的值=" + request.getParameter("search"));
			} else {
			}
		}
		// }
		// 读取state
		// 读取state
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
		// 读取state
		treeTableParameter.setSql_find_root("SELECT * FROM APP_MENU  where APP_MENU_ID_='" + parent_id + "'");
		// treeTableParameter.setSql(this.toSqlRoleByApp());
		treeTableParameter.setSql(this.toSqlRoleShowByApp());
		treeTableParameter.setColumnTreeCode("TREE_CODE_");
		treeTableParameter.setColumnId("APP_MENU_ID_");
		treeTableParameter.setColumnParentId("PARENT_");
		treeTableParameter.setColumnName("APP_MENU_NAME_");
		treeTableParameter.setColumnPath("PATH_");
		treeTableParameter.setColumnUrl("ADMIN_URL_");
		treeTableParameter.setColumnPic("PIC_");
		treeTableParameter.setColumnPicOpen("PIC_OPEN_");
		treeTableParameter.setColumnPicClose("PIC_CLOSE_");
		treeTableParameter.setColumnSn("SN_");
		// 打开所有
		// treeTableParameter.setOpen_all(true);
		// treeTableParameter.setClose_all(true);
		// 有根节点
		treeTableParameter.setRoot_enable(true);
		// treeTableParameter.setRoot_enable(false);
		// root的id
		// xml_node.setRootId("1");
		// 上下文
		treeTableParameter.setContext_path(this.request.getContextPath());
		// key
		treeTableParameter.setKey("my");
		// 其它tds
		Map<String, Object> tds = new HashMap<String, Object>();
		tds.put("1", "<td>根目录</td><td>root</td>");
		tds.put("2", "<td>菜单2</td><td>菜单2</td>");
		treeTableParameter.setTds(tds);
		// 菜单模型
		treeTableParameter.setModel$tree_menu(true);
		// treeTableParameter.setModel$simple(true);
		return treeTableParameter;
	}
	/**
	 * 菜单sql;
	 * 
	 * 
	 * 有角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * 是否显示;
	 * 
	 * @return
	 */
	public String toSqlRoleShowByApp() throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" left join  APP_MENU_GROUP rm   ");
		sb.append(" on m. APP_MENU_ID_=rm.APP_MENU_ID_    ");
		sb.append(" left join  APP_USER_GROUP ru   ");
		sb.append(" on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		sb.append(" where m.STATE_='" + TaskStateEnum.OPEN.toString() + "' and ru.APP_USER_ID_='" + userId + "'");
		sb.append(" order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	/**
	 * 菜单sql;
	 * 
	 * 
	 * 有角色;
	 * 
	 * 通过角色过滤菜单;
	 * 
	 * @return
	 */
	public String toSqlRoleByApp() throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" APP_MENU m");
		sb.append(" left join  APP_MENU_GROUP rm   ");
		sb.append(" on m. APP_MENU_ID_=rm.APP_MENU_ID_     ");
		sb.append(" left join  APP_USER_GROUP ru   ");
		sb.append("  on ru.APP_GROUP_ID_=rm.APP_GROUP_ID_ ");
		sb.append("  where  ru.APP_USER_ID_=" + userId);
		sb.append(" 	order by m.SN_ asc,m.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	/**
	 * 菜单sql;
	 * 
	 * 
	 * @return
	 */
	public String toSqlByApp() throws Exception {
		String userId = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select * FROM APP_MENU  t1 order by t1.SN_ asc,t1.APP_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
}
