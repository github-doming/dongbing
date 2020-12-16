package all.gen.wx_p_news_article.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_news_article.t.entity.WxPNewsArticleT;
import all.gen.wx_p_news_article.t.service.WxPNewsArticleTService;
import all.gen.wx_p_news_article.t.vo.WxPNewsArticleTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsArticleTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPNewsArticleTService service = new WxPNewsArticleTService();
		WxPNewsArticleT entity = null;
		String id = request.getParameter("id");
		entity = (WxPNewsArticleT) RequestThreadLocal.doRequest2EntityByJson(WxPNewsArticleTVo.class, WxPNewsArticleT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
