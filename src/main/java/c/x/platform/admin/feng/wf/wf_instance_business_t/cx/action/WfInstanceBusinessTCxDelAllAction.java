package c.x.platform.admin.feng.wf.wf_instance_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.service.WfInstanceBusinessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInstanceBusinessTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInstanceBusinessTCxService service = new WfInstanceBusinessTCxService();
		service.delAll(ids);
		return "index";
	}
}
