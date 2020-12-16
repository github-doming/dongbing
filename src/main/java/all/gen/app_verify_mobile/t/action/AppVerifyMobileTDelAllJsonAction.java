package all.gen.app_verify_mobile.t.action;
import all.gen.app_verify_mobile.t.service.AppVerifyMobileTService;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMobileTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AppVerifyMobileTService service = new AppVerifyMobileTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
