package all.app_admin.app.app_account.t.action;
import all.app_admin.app.app_account.t.service.AppAccountTService;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppAccountTService service = new AppAccountTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
