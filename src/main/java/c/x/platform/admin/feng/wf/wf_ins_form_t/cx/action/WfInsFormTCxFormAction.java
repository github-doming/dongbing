package c.x.platform.admin.feng.wf.wf_ins_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.entity.WfInsFormTCx;
import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.service.WfInsFormTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsFormTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInsFormTCxService service = new WfInsFormTCxService();
			WfInsFormTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
