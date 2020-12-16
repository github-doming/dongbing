package all.sys_admin.sys.sys_user.t.action;

import java.util.List;

import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_user.t.entity.SysUserT;
import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.x.platform.root.common.action.BaseAction;

public class SysUserCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		// 声明
		SysGroupService sysGroupTCxRoleService = new SysGroupService();
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysUserService service = new SysUserService();
			SysUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);

			// 加载该用户所关联的角色列表
			String ids_role = sysGroupTCxRoleService
					.findRoleListByUserId2String(id);
			request.setAttribute("ids_role", ids_role);

		}
		// 加载所有角色列表
		List<SysGroupT> list_role = sysGroupTCxRoleService.findAllPermissionGrade(this
				.findCurrentSysUser().getPermissionGrade());
		request.setAttribute("list_role", list_role);

		return "index";
	}
}
