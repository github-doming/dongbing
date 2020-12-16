package all.gen.app_log_request.t.action;

import all.gen.app_log_request.t.entity.AppLogRequestT;
import all.gen.app_log_request.t.service.AppLogRequestTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppLogRequestTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppLogRequestTService service = new AppLogRequestTService();
			AppLogRequestT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
