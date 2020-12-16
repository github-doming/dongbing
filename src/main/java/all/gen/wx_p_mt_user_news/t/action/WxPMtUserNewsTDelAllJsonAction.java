package all.gen.wx_p_mt_user_news.t.action;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMtUserNewsTService service = new WxPMtUserNewsTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
