package all.gen.wx_p_article.t.action;

import all.gen.wx_p_article.t.entity.WxPArticleT;
import all.gen.wx_p_article.t.service.WxPArticleTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPArticleTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPArticleTService service = new WxPArticleTService();
			WxPArticleT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
