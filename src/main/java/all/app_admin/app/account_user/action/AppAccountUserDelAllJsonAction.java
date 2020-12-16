package all.app_admin.app.account_user.action;

import all.app_admin.app.account_user.service.AppAccountUserService;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountUserDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppAccountUserService service = new AppAccountUserService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
