package all.gen.alipay_config.t.action;
import all.gen.alipay_config.t.service.AlipayConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class AlipayConfigTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		AlipayConfigTService service = new AlipayConfigTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
