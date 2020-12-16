package c.x.platform.admin.feng.pss.gen3.pss_product_info.action;

import c.x.platform.admin.feng.pss.gen3.pss_product_info.service.Gen3PssProductInfoService;
import c.x.platform.root.common.action.BaseAction;

public class Gen3PssProductInfoDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("checkboxIds");
		Gen3PssProductInfoService service = new Gen3PssProductInfoService();
		service.delAll(ids);
		return "index";
	}
}
