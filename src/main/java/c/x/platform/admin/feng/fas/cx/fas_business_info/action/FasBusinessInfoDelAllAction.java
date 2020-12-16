package c.x.platform.admin.feng.fas.cx.fas_business_info.action;
import c.x.platform.admin.feng.fas.cx.fas_business_info.service.FasBusinessInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FasBusinessInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		FasBusinessInfoService service = new FasBusinessInfoService();
		service.delAll(ids);
		return "index";
	}
}
