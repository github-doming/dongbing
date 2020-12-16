package all.gen.wx_p_mt_article.t.action;

import all.gen.wx_p_mt_article.t.entity.WxPMtArticleT;
import all.gen.wx_p_mt_article.t.service.WxPMtArticleTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtArticleTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMtArticleTService service = new WxPMtArticleTService();
			WxPMtArticleT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
