package all.gen.alipay_log_event.t.action;

import all.gen.alipay_log_event.t.entity.AlipayLogEventT;
import all.gen.alipay_log_event.t.service.AlipayLogEventTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogEventTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AlipayLogEventTService service = new AlipayLogEventTService();
			AlipayLogEventT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
