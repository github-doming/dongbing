package all.app_admin.app.app_menu.t.action;

import all.gen.app_menu.t.entity.AppMenuT;
import all.app_admin.app.app_menu.t.service.AppMenuTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class AppMenuTSelectAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		AppMenuTService service = new AppMenuTService();
		String parent_id = request.getParameter("parent_id");
		AppMenuT s = service.find(parent_id);
		request.setAttribute("s", s);
		return CommViewEnum.Default.toString();
	}
}
