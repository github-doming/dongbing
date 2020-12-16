package all.gen.sys_group.t.action;

import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_group.t.service.SysGroupTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupTService service = new SysGroupTService();
			SysGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
