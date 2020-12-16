package all.gen.wx_p_news_article.t.action;
import all.gen.wx_p_news_article.t.service.WxPNewsArticleTService;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsArticleTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		WxPNewsArticleTService service = new WxPNewsArticleTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
