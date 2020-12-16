package all.gen.wx_p_menu.t.action;
import all.gen.wx_p_menu.t.service.WxPMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMenuTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMenuTService service = new WxPMenuTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
