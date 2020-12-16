package all.gen.app_verify_account.t.action;

import all.gen.app_verify_account.t.entity.AppVerifyAccountT;
import all.gen.app_verify_account.t.service.AppVerifyAccountTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyAccountTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppVerifyAccountTService service = new AppVerifyAccountTService();
			AppVerifyAccountT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
