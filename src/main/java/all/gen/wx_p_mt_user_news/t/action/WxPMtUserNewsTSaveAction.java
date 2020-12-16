package all.gen.wx_p_mt_user_news.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mt_user_news.t.entity.WxPMtUserNewsT;
import all.gen.wx_p_mt_user_news.t.service.WxPMtUserNewsTService;
import all.gen.wx_p_mt_user_news.t.vo.WxPMtUserNewsTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtUserNewsTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMtUserNewsTService service = new WxPMtUserNewsTService();
		WxPMtUserNewsT entity = null;
		String id = request.getParameter("wx_p_mt_user_news.wxPMtUserNewsId");
		entity = (WxPMtUserNewsT) RequestThreadLocal.doRequest2Entity(WxPMtUserNewsTVo.class,WxPMtUserNewsT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
