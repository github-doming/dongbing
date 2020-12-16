package all.gen.wx_p_config.t.action;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPConfigTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPConfigTService service = new WxPConfigTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
