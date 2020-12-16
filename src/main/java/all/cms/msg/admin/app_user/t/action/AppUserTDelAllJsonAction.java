package all.cms.msg.admin.app_user.t.action;
import all.cms.msg.admin.app_user.t.service.AppUserTService;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppUserTService service = new AppUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
