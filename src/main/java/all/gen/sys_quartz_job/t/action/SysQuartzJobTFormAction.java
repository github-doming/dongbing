package all.gen.sys_quartz_job.t.action;

import all.gen.sys_quartz_job.t.entity.SysQuartzJobT;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzJobTService service = new SysQuartzJobTService();
			SysQuartzJobT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
