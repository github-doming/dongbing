package all.gen.wx_p_mo_text.t.action;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMoTextTService service = new WxPMoTextTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
