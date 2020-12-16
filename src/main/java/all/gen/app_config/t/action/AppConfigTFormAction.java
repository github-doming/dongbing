package all.gen.app_config.t.action;

import all.gen.app_config.t.entity.AppConfigT;
import all.gen.app_config.t.service.AppConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppConfigTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppConfigTService service = new AppConfigTService();
			AppConfigT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
