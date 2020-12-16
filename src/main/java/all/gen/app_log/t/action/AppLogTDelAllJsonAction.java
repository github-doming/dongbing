package all.gen.app_log.t.action;
import all.gen.app_log.t.service.AppLogTService;
import c.x.platform.root.common.action.BaseAction;
public class AppLogTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppLogTService service = new AppLogTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
