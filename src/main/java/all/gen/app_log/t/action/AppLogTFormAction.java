package all.gen.app_log.t.action;

import all.gen.app_log.t.entity.AppLogT;
import all.gen.app_log.t.service.AppLogTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppLogTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppLogTService service = new AppLogTService();
			AppLogT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
