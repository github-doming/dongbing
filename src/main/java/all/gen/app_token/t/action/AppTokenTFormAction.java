package all.gen.app_token.t.action;

import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_token.t.service.AppTokenTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppTokenTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppTokenTService service = new AppTokenTService();
			AppTokenT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
