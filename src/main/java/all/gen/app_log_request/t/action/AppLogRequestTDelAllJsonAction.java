package all.gen.app_log_request.t.action;
import all.gen.app_log_request.t.service.AppLogRequestTService;
import c.x.platform.root.common.action.BaseAction;
public class AppLogRequestTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppLogRequestTService service = new AppLogRequestTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
