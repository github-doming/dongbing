package all.gen.wx_p_position.t.action;
import all.gen.wx_p_position.t.service.WxPPositionTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPPositionTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPPositionTService service = new WxPPositionTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
