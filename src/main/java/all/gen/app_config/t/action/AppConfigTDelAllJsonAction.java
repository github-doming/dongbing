package all.gen.app_config.t.action;
import all.gen.app_config.t.service.AppConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class AppConfigTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppConfigTService service = new AppConfigTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
