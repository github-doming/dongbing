package c.x.platform.sys.sys_group.cx_role.action.menu;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
/**
 * 
 * 角色授权;
 * 
 * 角色与菜单绑定；
 * 
 * 
 */
public class SysGroupMenuCxRoleSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String group_id = request.getParameter("name_roleId");
		String[] menu_ids = request.getParameterValues("name_checkbox_my");
		// 编辑角色与菜单时，先删除角色与菜单列表，然后 保存角色与菜单列表
		SysGroupCxRoleService groupService = new SysGroupCxRoleService();
		// 删除角色与菜单列表
		groupService.delMenuGroupByRoleId(group_id);
		// 保存角色与菜单列表
		groupService.save_groupId_menuIdList(group_id, menu_ids);
		return "index";
	}
}
