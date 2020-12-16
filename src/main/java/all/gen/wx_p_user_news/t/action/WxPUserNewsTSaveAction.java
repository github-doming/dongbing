package all.gen.wx_p_user_news.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_user_news.t.entity.WxPUserNewsT;
import all.gen.wx_p_user_news.t.service.WxPUserNewsTService;
import all.gen.wx_p_user_news.t.vo.WxPUserNewsTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserNewsTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPUserNewsTService service = new WxPUserNewsTService();
		WxPUserNewsT entity = null;
		String id = request.getParameter("wx_p_user_news.wxPUserNewsId");
		entity = (WxPUserNewsT) RequestThreadLocal.doRequest2Entity(WxPUserNewsTVo.class,WxPUserNewsT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
