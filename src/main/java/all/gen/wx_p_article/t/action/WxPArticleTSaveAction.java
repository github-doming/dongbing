package all.gen.wx_p_article.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_article.t.entity.WxPArticleT;
import all.gen.wx_p_article.t.service.WxPArticleTService;
import all.gen.wx_p_article.t.vo.WxPArticleTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPArticleTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPArticleTService service = new WxPArticleTService();
		WxPArticleT entity = null;
		String id = request.getParameter("wx_p_article.wxPArticleId");
		entity = (WxPArticleT) RequestThreadLocal.doRequest2Entity(WxPArticleTVo.class,WxPArticleT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
