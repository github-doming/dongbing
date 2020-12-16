package all.app_admin.app.app_menu.t.action;
import c.a.config.SysConfig;
import all.app_admin.app.app_menu.t.service.AppMenuTService;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class AppMenuTDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String ids = request.getParameter("id");
		AppMenuTService service = new AppMenuTService();
		service.delAll(ids.split(","));
			// 跳转
		return CommViewEnum.Default.toString();
	}
}
