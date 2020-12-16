package c.x.platform.sys.sys_group_user.cx.action;

import c.x.platform.sys.sys_group_user.cx.service.SysGroupUserCxService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupUserCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysGroupUserCxService service = new SysGroupUserCxService();
		service.delAll(ids);
		return "index";
	}
}
