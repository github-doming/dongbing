package all.gen.wx_qy_config.t.action;
import all.gen.wx_qy_config.t.service.WxQyConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class WxQyConfigTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxQyConfigTService service = new WxQyConfigTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
