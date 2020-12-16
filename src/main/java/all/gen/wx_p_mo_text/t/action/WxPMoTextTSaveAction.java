package all.gen.wx_p_mo_text.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mo_text.t.entity.WxPMoTextT;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import all.gen.wx_p_mo_text.t.vo.WxPMoTextTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMoTextTService service = new WxPMoTextTService();
		WxPMoTextT entity = null;
		String id = request.getParameter("wx_p_mo_text.wxPMoTextId");
		entity = (WxPMoTextT) RequestThreadLocal.doRequest2Entity(WxPMoTextTVo.class,WxPMoTextT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
