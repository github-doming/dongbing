package all.app_admin.app.app_group.t.action;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppGroupTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppGroupTService service = new AppGroupTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
