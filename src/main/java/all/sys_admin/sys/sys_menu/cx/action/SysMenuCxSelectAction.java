package all.sys_admin.sys.sys_menu.cx.action;

import all.gen.sys_menu.t.entity.SysMenuT;
import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;
import c.x.platform.root.common.action.BaseAction;

public class SysMenuCxSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysMenuService service = new SysMenuService();
		String parent_id = request.getParameter("parent_id");
		SysMenuT s = service.find(parent_id);
		request.setAttribute("s", s);
		return "index";
	}
}
