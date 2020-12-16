package all.gen.app_menu_group.t.action;
import all.gen.app_menu_group.t.service.AppMenuGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuGroupTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppMenuGroupTService service = new AppMenuGroupTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
