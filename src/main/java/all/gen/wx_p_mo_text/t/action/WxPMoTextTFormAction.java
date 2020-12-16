package all.gen.wx_p_mo_text.t.action;

import all.gen.wx_p_mo_text.t.entity.WxPMoTextT;
import all.gen.wx_p_mo_text.t.service.WxPMoTextTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMoTextTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMoTextTService service = new WxPMoTextTService();
			WxPMoTextT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
