package all.gen.alipay_log_auth.t.action;

import all.gen.alipay_log_auth.t.entity.AlipayLogAuthT;
import all.gen.alipay_log_auth.t.service.AlipayLogAuthTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AlipayLogAuthTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AlipayLogAuthTService service = new AlipayLogAuthTService();
			AlipayLogAuthT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
