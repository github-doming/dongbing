package all.gen.wx_p_config.t.action;

import all.gen.wx_p_config.t.entity.WxPConfigT;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxPConfigTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxPConfigTService service = new WxPConfigTService();
			WxPConfigT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
