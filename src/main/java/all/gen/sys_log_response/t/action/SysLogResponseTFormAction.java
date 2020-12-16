package all.gen.sys_log_response.t.action;

import all.gen.sys_log_response.t.entity.SysLogResponseT;
import all.gen.sys_log_response.t.service.SysLogResponseTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysLogResponseTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysLogResponseTService service = new SysLogResponseTService();
			SysLogResponseT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
