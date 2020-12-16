package all.app_admin.app.app_group.t.action.menu;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import c.x.platform.root.common.action.BaseAction;
/**
 * 
 * 角色授权;
 * 
 * 角色与菜单绑定；
 * 
 * 
 */
public class AppMenuGroupTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {;
		
		String group_id = request.getParameter("name_roleId");
		String[] menu_ids = request.getParameterValues("name_checkbox_my");
		// 编辑角色与菜单时，先删除角色与菜单列表，然后 保存角色与菜单列表
		AppGroupTService groupService = new AppGroupTService();
		// 删除角色与菜单列表
		groupService.delMenuGroupByRoleId(group_id);
		// 保存角色与菜单列表
		groupService.save_roleId_menuIds(group_id, menu_ids);
		return "index";
	}
}
