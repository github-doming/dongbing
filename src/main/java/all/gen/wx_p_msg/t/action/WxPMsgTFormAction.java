package all.gen.wx_p_msg.t.action;

import all.gen.wx_p_msg.t.entity.WxPMsgT;
import all.gen.wx_p_msg.t.service.WxPMsgTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPMsgTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPMsgTService service = new WxPMsgTService();
			WxPMsgT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
