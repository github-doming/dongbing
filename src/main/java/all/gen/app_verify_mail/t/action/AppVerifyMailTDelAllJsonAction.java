package all.gen.app_verify_mail.t.action;
import all.gen.app_verify_mail.t.service.AppVerifyMailTService;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMailTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppVerifyMailTService service = new AppVerifyMailTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
