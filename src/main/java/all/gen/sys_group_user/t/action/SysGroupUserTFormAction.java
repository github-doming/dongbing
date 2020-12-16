package all.gen.sys_group_user.t.action;

import all.gen.sys_group_user.t.entity.SysGroupUserT;
import all.gen.sys_group_user.t.service.SysGroupUserTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysGroupUserTService service = new SysGroupUserTService();
			SysGroupUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
