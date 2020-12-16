package all.gen.wx_p_news.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_news.t.entity.WxPNewsT;
import all.gen.wx_p_news.t.service.WxPNewsTService;
import all.gen.wx_p_news.t.vo.WxPNewsTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPNewsTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPNewsTService service = new WxPNewsTService();
		WxPNewsT entity = null;
		String id = request.getParameter("wx_p_news.wxPNewsId");
		entity = (WxPNewsT) RequestThreadLocal.doRequest2Entity(WxPNewsTVo.class,WxPNewsT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
