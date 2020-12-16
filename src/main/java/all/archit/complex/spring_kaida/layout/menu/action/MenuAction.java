package all.archit.complex.spring_kaida.layout.menu.action;
import java.util.Map;
import java.util.HashMap;
import java.util.Map;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.layout.main.action.SysMainAction;
import c.x.platform.root.tree_table.action.BaseTreeTableAction;
import c.x.platform.root.compo.tree_table.parameter.TreeTableParameter;
import c.x.platform.root.compo.tree_table.config.TreeTableConfig;
public class MenuAction extends BaseTreeTableAction {
	public MenuAction() {
		// 菜单允许
		this.menuAllow = false;
	}
	/**
	 * 菜单sql;
	 * 
	 * 
	 * @return
	 */
	public String to_sql() throws Exception {
		String user_id = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select * FROM SYS_MENU  t1 order by t1.SN_ asc,t1.SYS_MENU_ID_ asc  ");
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
	public String to_sql$role() throws Exception {
		String user_id = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" SYS_MENU m");
		sb.append(" left join  SYS_GROUP_MENU rm   ");
		sb.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		sb.append(" left join  SYS_GROUP_USER ru   ");
		sb.append("  on ru.GROUP_ID_=rm.GROUP_ID_ ");
		sb.append("  where ru.USER_ID_=" + user_id);
		sb.append(" 	order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
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
	 * 是否显示;
	 * 
	 * @return
	 */
	public String to_sql$role$show() throws Exception {
		String user_id = this.findCurrentUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" select DISTINCT m.* from    ");
		sb.append(" SYS_MENU m");
		sb.append(" left join  SYS_GROUP_MENU rm   ");
		sb.append(" on m.SYS_MENU_ID_=rm.SYS_MENU_ID_    ");
		sb.append(" left join  SYS_GROUP_USER ru   ");
		sb.append("  on ru.GROUP_ID_=rm.GROUP_ID_ ");
		sb.append("  where m.IS_SHOW_=1 and ru.USER_ID_=" + user_id);
		sb.append(" 	order by m.SN_ asc,m.SYS_MENU_ID_ asc  ");
		String sql = sb.toString();
		return sql;
	}
	@Override
	public TreeTableParameter initParameter() throws Exception {
		if (false) {
			System.out.println("搜索的值=" + request.getParameter("search"));
			System.out.println("state=" + request.getParameter("state"));
			System.out
					.println("parent_id=" + request.getParameter("parent_id"));
		}
		String parent_id = (String) request.getParameter("parent_id");
		BaseLog.trace("parent_id =" + parent_id);
		request.setAttribute("parent_id", parent_id);
		// 2级菜单的第1个节点
		SysMainAction mainAction = new SysMainAction();
		// request必须设置，不然报空指针异常
		mainAction.setRequest(request);
		Map map_child = mainAction.findNodeNotChilds(parent_id);
		request.setAttribute("map_child", map_child);
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
		treeTableParameter
				.setSql_find_root("SELECT * FROM SYS_MENU  where id="
						+ parent_id);
		if (false) {
			treeTableParameter
					.setSql("select * FROM SYS_MENU  t1 order by t1.sequence asc,t1.id asc");
		}
		// treeTableParameter.setSql(this.to_sql$role());
		treeTableParameter.setSql(this.to_sql$role$show());
		// treeTableParameter.setColumn_tree_code("tree_code");
		treeTableParameter.setColumnId("id");
		treeTableParameter.setColumnParentId("parent");
		treeTableParameter.setColumnName("name");
		treeTableParameter.setColumnPath("path");
		treeTableParameter.setColumnUrl("url");
		treeTableParameter.setColumnPic("pic");
		treeTableParameter.setColumnPicOpen("pic_open");
		treeTableParameter.setColumnPicClose("pic_close");
		// 打开所有
		// treeTableParameter.setOpen_all(true);
		// treeTableParameter.setClose_all(true);
		// 有根节点
		// treeTableParameter.setRoot_enable(true);
		treeTableParameter.setRoot_enable(false);
		// root的id
		treeTableParameter.setRoot_id("1");
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
	@Override
	public String execute() throws Exception {
		// 设置参数
		TreeTableParameter treeParameter = this.initParameter();
		// 执行
		String str = this.execute_printlnForHTML(treeParameter);
		// BaseLog.trace("str=" + str);
		request.setAttribute("c_tree", str);
		return "index";
	}
}
