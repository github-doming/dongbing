package all.gen.app_verify_mail.t.action;

import all.gen.app_verify_mail.t.entity.AppVerifyMailT;
import all.gen.app_verify_mail.t.service.AppVerifyMailTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMailTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppVerifyMailTService service = new AppVerifyMailTService();
			AppVerifyMailT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
