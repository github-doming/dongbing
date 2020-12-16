package all.gen.app_token.t.action;
import all.gen.app_token.t.service.AppTokenTService;
import c.x.platform.root.common.action.BaseAction;
public class AppTokenTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppTokenTService service = new AppTokenTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
