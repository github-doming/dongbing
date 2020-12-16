package all.gen.alipay_log_return.t.action;

import all.gen.alipay_log_return.t.entity.AlipayLogReturnT;
import all.gen.alipay_log_return.t.service.AlipayLogReturnTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogReturnTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AlipayLogReturnTService service = new AlipayLogReturnTService();
			AlipayLogReturnT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
