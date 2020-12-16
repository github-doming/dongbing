package all.sys_admin.sys.sys_group.cx_role.action;

import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupCxRoleDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysGroupService service = new SysGroupService();
		service.delAll(ids);
		return "index";
	}
}
