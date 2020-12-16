package all.gen.app_menu_group.t.action;

import all.gen.app_menu_group.t.entity.AppMenuGroupT;
import all.gen.app_menu_group.t.service.AppMenuGroupTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuGroupTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppMenuGroupTService service = new AppMenuGroupTService();
			AppMenuGroupT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
