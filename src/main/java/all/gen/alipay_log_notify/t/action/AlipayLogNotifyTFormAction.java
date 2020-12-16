package all.gen.alipay_log_notify.t.action;

import all.gen.alipay_log_notify.t.entity.AlipayLogNotifyT;
import all.gen.alipay_log_notify.t.service.AlipayLogNotifyTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogNotifyTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AlipayLogNotifyTService service = new AlipayLogNotifyTService();
			AlipayLogNotifyT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
