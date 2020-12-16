package all.gen.app_log_response.t.action;

import all.gen.app_log_response.t.entity.AppLogResponseT;
import all.gen.app_log_response.t.service.AppLogResponseTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppLogResponseTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppLogResponseTService service = new AppLogResponseTService();
			AppLogResponseT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
