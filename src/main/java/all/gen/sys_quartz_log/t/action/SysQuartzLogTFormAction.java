package all.gen.sys_quartz_log.t.action;

import all.gen.sys_quartz_log.t.entity.SysQuartzLogT;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzLogTService service = new SysQuartzLogTService();
			SysQuartzLogT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
