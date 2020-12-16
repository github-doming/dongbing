package all.gen.wx_p_menu.t.action;

import all.gen.wx_p_menu.t.entity.WxPMenuT;
import all.gen.wx_p_menu.t.service.WxPMenuTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMenuTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMenuTService service = new WxPMenuTService();
			WxPMenuT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
