package all.gen.alipay_log_event.t.action;
import all.gen.alipay_log_event.t.service.AlipayLogEventTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogEventTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AlipayLogEventTService service = new AlipayLogEventTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
