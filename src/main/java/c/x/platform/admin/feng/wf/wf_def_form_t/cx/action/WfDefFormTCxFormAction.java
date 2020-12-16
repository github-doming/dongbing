package c.x.platform.admin.feng.wf.wf_def_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_form_t.cx.entity.WfDefFormTCx;
import c.x.platform.admin.feng.wf.wf_def_form_t.cx.service.WfDefFormTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFormTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefFormTCxService service = new WfDefFormTCxService();
			WfDefFormTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
