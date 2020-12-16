package all.gen.wx_qy_config.t.action;

import all.gen.wx_qy_config.t.entity.WxQyConfigT;
import all.gen.wx_qy_config.t.service.WxQyConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class WxQyConfigTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WxQyConfigTService service = new WxQyConfigTService();
			WxQyConfigT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
