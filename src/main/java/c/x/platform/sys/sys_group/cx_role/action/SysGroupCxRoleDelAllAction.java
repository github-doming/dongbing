package c.x.platform.sys.sys_group.cx_role.action;

import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupCxRoleDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysGroupCxRoleService service = new SysGroupCxRoleService();
		service.delAll(ids);
		return "index";
	}
}
