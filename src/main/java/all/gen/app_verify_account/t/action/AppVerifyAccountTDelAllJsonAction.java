package all.gen.app_verify_account.t.action;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyAccountTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppVerifyAccountTService service = new AppVerifyAccountTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
