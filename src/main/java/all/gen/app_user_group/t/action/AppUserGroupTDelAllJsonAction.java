package all.gen.app_user_group.t.action;
import all.gen.app_user_group.t.service.AppUserGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppUserGroupTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppUserGroupTService service = new AppUserGroupTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
