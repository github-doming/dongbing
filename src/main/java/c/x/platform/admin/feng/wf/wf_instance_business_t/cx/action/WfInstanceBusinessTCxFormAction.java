package c.x.platform.admin.feng.wf.wf_instance_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.entity.WfInstanceBusinessTCx;
import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.service.WfInstanceBusinessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInstanceBusinessTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInstanceBusinessTCxService service = new WfInstanceBusinessTCxService();
			WfInstanceBusinessTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
