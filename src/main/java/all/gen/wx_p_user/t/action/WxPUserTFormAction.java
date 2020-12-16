package all.gen.wx_p_user.t.action;

import all.gen.wx_p_user.t.entity.WxPUserT;
import all.gen.wx_p_user.t.service.WxPUserTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPUserTService service = new WxPUserTService();
			WxPUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
