package all.gen.wx_p_position.t.action;

import all.gen.wx_p_position.t.entity.WxPPositionT;
import all.gen.wx_p_position.t.service.WxPPositionTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPPositionTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPPositionTService service = new WxPPositionTService();
			WxPPositionT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
