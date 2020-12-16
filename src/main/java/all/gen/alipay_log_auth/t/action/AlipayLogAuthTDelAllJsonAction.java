package all.gen.alipay_log_auth.t.action;
import all.gen.alipay_log_auth.t.service.AlipayLogAuthTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogAuthTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AlipayLogAuthTService service = new AlipayLogAuthTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
