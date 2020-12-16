package all.sys_admin.sys.sys_menu.cx.action;

import c.x.platform.root.common.action.BaseAction;
import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;

public class SysMenuCxDelJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMenuService service = new SysMenuService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
