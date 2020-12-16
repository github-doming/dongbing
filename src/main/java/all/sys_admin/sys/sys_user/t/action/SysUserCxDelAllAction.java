package all.sys_admin.sys.sys_user.t.action;

import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.x.platform.root.common.action.BaseAction;

public class SysUserCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		SysUserService service = new SysUserService();
		service.delAll(ids);
		return "index";
	}
}
