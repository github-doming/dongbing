package c.x.platform.sys.sys_user.cx.action;

import c.x.platform.sys.sys_user.cx.service.SysUserCxService;
import c.x.platform.root.common.action.BaseAction;

public class SysUserCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysUserCxService service = new SysUserCxService();
		service.delAll(ids);
		return "index";
	}
}
