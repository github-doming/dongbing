package all.gen.alipay_config.t.action;

import all.gen.alipay_config.t.entity.AlipayConfigT;
import all.gen.alipay_config.t.service.AlipayConfigTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayConfigTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AlipayConfigTService service = new AlipayConfigTService();
			AlipayConfigT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
