package c.x.platform.sys.sys_menu.cx.action;

import c.x.platform.sys.sys_menu.cx.service.SysMenuCxService;
import c.x.platform.root.common.action.BaseAction;

public class SysMenuCxDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMenuCxService service = new SysMenuCxService();
		service.del(id);
		return "index";
	}
}
