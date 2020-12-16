package c.x.platform.admin.feng.wf.wf_define_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_define_business_t.cx.entity.WfDefineBusinessTCx;
import c.x.platform.admin.feng.wf.wf_define_business_t.cx.service.WfDefineBusinessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefineBusinessTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefineBusinessTCxService service = new WfDefineBusinessTCxService();
			WfDefineBusinessTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
