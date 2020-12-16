package all.gen.alipay_log_return.t.action;
import all.gen.alipay_log_return.t.service.AlipayLogReturnTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogReturnTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AlipayLogReturnTService service = new AlipayLogReturnTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
