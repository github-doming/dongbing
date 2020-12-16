package all.gen.wx_p_user.t.action;
import all.gen.wx_p_user.t.service.WxPUserTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPUserTService service = new WxPUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
