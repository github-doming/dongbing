package c.x.platform.sys.sys_group_user.cx.action;

import all.gen.sys_group_user.t.entity.SysGroupUserT;
import c.x.platform.sys.sys_group_user.cx.service.SysGroupUserCxService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupUserCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupUserCxService service = new SysGroupUserCxService();
			SysGroupUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
