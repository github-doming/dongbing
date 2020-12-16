package all.gen.sys_group_menu.t.action;

import all.gen.sys_group_menu.t.entity.SysGroupMenuT;
import all.gen.sys_group_menu.t.service.SysGroupMenuTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupMenuTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupMenuTService service = new SysGroupMenuTService();
			SysGroupMenuT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
