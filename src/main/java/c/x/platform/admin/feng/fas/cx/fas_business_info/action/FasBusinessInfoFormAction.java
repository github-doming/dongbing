package c.x.platform.admin.feng.fas.cx.fas_business_info.action;
import c.x.platform.admin.feng.fas.cx.fas_business_info.entity.FasBusinessInfo;
import c.x.platform.admin.feng.fas.cx.fas_business_info.service.FasBusinessInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FasBusinessInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			FasBusinessInfoService service = new FasBusinessInfoService();
			FasBusinessInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
