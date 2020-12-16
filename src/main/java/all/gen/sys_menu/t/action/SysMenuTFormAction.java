package all.gen.sys_menu.t.action;

import all.gen.sys_menu.t.entity.SysMenuT;
import all.gen.sys_menu.t.service.SysMenuTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysMenuTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysMenuTService service = new SysMenuTService();
			SysMenuT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
