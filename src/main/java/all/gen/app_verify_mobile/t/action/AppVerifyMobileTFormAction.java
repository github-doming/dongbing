package all.gen.app_verify_mobile.t.action;

import all.gen.app_verify_mobile.t.entity.AppVerifyMobileT;
import all.gen.app_verify_mobile.t.service.AppVerifyMobileTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppVerifyMobileTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			AppVerifyMobileTService service = new AppVerifyMobileTService();
			AppVerifyMobileT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return CommViewEnum.Default.toString();
	}
}
