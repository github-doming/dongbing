package all.sys_admin.sys.sys_menu.cx.action;

import all.gen.sys_menu.t.entity.SysMenuT;
import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.string.StringUtil;

public class SysMenuCxFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysMenuService service = new SysMenuService();
		String id = (String) request.getParameter("id");
		request.setAttribute("id", id);
		if (id == null) {
			// 选择上一级菜单
			String parent_id = (String) request.getParameter("parent_id");
			if (StringUtil.isBlank(parent_id)) {
			} else {
				parent_id = parent_id.trim();
				SysMenuT p = service.find(parent_id);
				request.setAttribute("p", p);
			}
		}
		if (id != null) {
			id = id.trim();
			// 本身
			SysMenuT s = service.find(id);
			request.setAttribute("s", s);
			// 选择上一级菜单
			String long_parent_id = null;
			SysMenuT p = null;
			// 上一级
			long_parent_id = s.getParent();
			p = service.find(long_parent_id.toString());
			request.setAttribute("p", p);
		}
		return "index";
	}
}
