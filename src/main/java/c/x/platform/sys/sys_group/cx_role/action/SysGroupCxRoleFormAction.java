package c.x.platform.sys.sys_group.cx_role.action;

import all.gen.sys_group.t.entity.SysGroupT;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupCxRoleFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupCxRoleService service = new SysGroupCxRoleService();
			SysGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
