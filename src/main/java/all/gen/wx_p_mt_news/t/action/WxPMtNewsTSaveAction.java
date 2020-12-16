package all.gen.wx_p_mt_news.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mt_news.t.entity.WxPMtNewsT;
import all.gen.wx_p_mt_news.t.service.WxPMtNewsTService;
import all.gen.wx_p_mt_news.t.vo.WxPMtNewsTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMtNewsTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMtNewsTService service = new WxPMtNewsTService();
		WxPMtNewsT entity = null;
		String id = request.getParameter("wx_p_mt_news.wxPMtNewsId");
		entity = (WxPMtNewsT) RequestThreadLocal.doRequest2Entity(WxPMtNewsTVo.class,WxPMtNewsT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
