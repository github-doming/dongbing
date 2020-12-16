package c.x.platform.admin.feng.wf.wf_ins_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.entity.WfInsFieldTCx;
import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.service.WfInsFieldTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsFieldTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInsFieldTCxService service = new WfInsFieldTCxService();
			WfInsFieldTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
