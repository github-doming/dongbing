package all.sys_admin.sys.sys_group.cx_role.action;

import all.gen.sys_group.t.entity.SysGroupT;
import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupCxRoleFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupService service = new SysGroupService();
			SysGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
