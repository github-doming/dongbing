package all.gen.app_user_group.t.action;

import all.gen.app_user_group.t.entity.AppUserGroupT;
import all.gen.app_user_group.t.service.AppUserGroupTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppUserGroupTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppUserGroupTService service = new AppUserGroupTService();
			AppUserGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
