package all.gen.wx_p_news.t.action;
import all.gen.wx_p_news.t.service.WxPNewsTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPNewsTService service = new WxPNewsTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
