package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity.WfDefTransitionTCx;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefTransitionTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefTransitionTCxService service = new WfDefTransitionTCxService();
			WfDefTransitionTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
