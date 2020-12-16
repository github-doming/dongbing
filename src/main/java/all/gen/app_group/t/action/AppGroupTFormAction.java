package all.gen.app_group.t.action;

import all.gen.app_group.t.entity.AppGroupT;
import all.gen.app_group.t.service.AppGroupTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppGroupTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppGroupTService service = new AppGroupTService();
			AppGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
