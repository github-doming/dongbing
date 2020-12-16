package all.gen.sys_quartz_config.t.action;

import all.gen.sys_quartz_config.t.entity.SysQuartzConfigT;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysQuartzConfigTService service = new SysQuartzConfigTService();
			SysQuartzConfigT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
