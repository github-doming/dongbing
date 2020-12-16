package all.gen.wx_p_mt_news.t.action;
import all.gen.wx_p_mt_news.t.service.WxPMtNewsTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtNewsTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMtNewsTService service = new WxPMtNewsTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
