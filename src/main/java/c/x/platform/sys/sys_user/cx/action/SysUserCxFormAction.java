package c.x.platform.sys.sys_user.cx.action;

import java.util.List;

import c.x.platform.root.common.action.BaseAction;
import all.gen.sys_group.t.entity.SysGroupT;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
import all.gen.sys_user.t.entity.SysUserT;
import c.x.platform.sys.sys_user.cx.service.SysUserCxService;

public class SysUserCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		// 声明
		SysGroupCxRoleService sysGroupTCxRoleService = new SysGroupCxRoleService();
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysUserCxService service = new SysUserCxService();
			SysUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);

			// 加载该用户所关联的角色列表
			String groupRoleIdList = sysGroupTCxRoleService.findGroupRoleListByUserId2String(id);
			request.setAttribute("ids_role", groupRoleIdList);

		}
		// 加载所有角色列表
		List<SysGroupT> groupRoleList = sysGroupTCxRoleService.findListByPermissionGrade(this.findCurrentUserPermissionGrade());
		request.setAttribute("list_role", groupRoleList);

		return "index";
	}
}
