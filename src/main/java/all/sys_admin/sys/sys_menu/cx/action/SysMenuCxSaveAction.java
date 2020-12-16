package all.sys_admin.sys.sys_menu.cx.action;

import all.gen.sys_menu.t.entity.SysMenuT;
import all.gen.sys_menu.t.vo.SysMenuTVo;
import all.sys_admin.sys.sys_menu.cx.service.SysMenuService;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;

public class SysMenuCxSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String parent_id = request.getParameter("sys_menu.parent");
		String id = request.getParameter("sys_menu.sysMenuId");

		SysMenuT entity = (SysMenuT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysMenuTVo.class, SysMenuT.class, request);
		SysMenuService service = new SysMenuService();
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return "index";
	}
}
