package all.sys_admin.sys.sys_menu.cx.action;

import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;
import c.x.platform.root.common.action.BaseAction;

public class SysMenuCxDelAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMenuService service = new SysMenuService();
		service.del(id);
		return "index";
	}
}
