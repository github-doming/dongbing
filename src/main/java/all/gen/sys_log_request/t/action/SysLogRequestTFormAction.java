package all.gen.sys_log_request.t.action;

import all.gen.sys_log_request.t.entity.SysLogRequestT;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysLogRequestTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysLogRequestTService service = new SysLogRequestTService();
			SysLogRequestT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
