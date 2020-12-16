package all.gen.app_log_response.t.action;
import all.gen.app_log_response.t.service.AppLogResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class AppLogResponseTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppLogResponseTService service = new AppLogResponseTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
