package all.gen.wx_p_user_news.t.action;
import all.gen.wx_p_user_news.t.service.WxPUserNewsTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserNewsTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPUserNewsTService service = new WxPUserNewsTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
