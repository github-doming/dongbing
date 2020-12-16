package all.gen.wx_p_mt_article.t.action;
import all.gen.wx_p_mt_article.t.service.WxPMtArticleTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtArticleTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPMtArticleTService service = new WxPMtArticleTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
