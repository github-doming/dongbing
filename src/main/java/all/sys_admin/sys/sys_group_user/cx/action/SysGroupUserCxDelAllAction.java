package all.sys_admin.sys.sys_group_user.cx.action;

import all.sys_admin.sys.sys_group_user.cx.service.SysGroupUserService;
import c.x.platform.root.common.action.BaseAction;

public class SysGroupUserCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysGroupUserService service = new SysGroupUserService();
		service.delAll(ids);
		return "index";
	}
}
