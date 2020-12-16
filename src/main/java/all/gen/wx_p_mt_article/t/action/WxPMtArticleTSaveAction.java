package all.gen.wx_p_mt_article.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mt_article.t.entity.WxPMtArticleT;
import all.gen.wx_p_mt_article.t.service.WxPMtArticleTService;
import all.gen.wx_p_mt_article.t.vo.WxPMtArticleTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtArticleTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMtArticleTService service = new WxPMtArticleTService();
		WxPMtArticleT entity = null;
		String id = request.getParameter("wx_p_mt_article.wxPMtArticleId");
		entity = (WxPMtArticleT) RequestThreadLocal.doRequest2Entity(WxPMtArticleTVo.class,WxPMtArticleT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
