package all.gen.wx_p_mo_text.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.wx_p_mo_text.t.entity.WxPMoTextT;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import all.gen.wx_p_mo_text.t.vo.WxPMoTextTVo;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		WxPMoTextTService service = new WxPMoTextTService();
		WxPMoTextT entity = null;
		String id = request.getParameter("id");
		entity = (WxPMoTextT) RequestThreadLocal.doRequest2EntityByJson(WxPMoTextTVo.class, WxPMoTextT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
