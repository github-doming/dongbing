package all.gen.wx_p_news_article.t.action;

import all.gen.wx_p_news_article.t.entity.WxPNewsArticleT;
import all.gen.wx_p_news_article.t.service.WxPNewsArticleTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsArticleTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPNewsArticleTService service = new WxPNewsArticleTService();
			WxPNewsArticleT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
