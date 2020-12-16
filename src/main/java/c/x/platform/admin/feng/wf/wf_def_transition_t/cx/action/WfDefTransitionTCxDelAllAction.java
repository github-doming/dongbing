package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefTransitionTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefTransitionTCxService service = new WfDefTransitionTCxService();
		service.delAll(ids);
		return "index";
	}
}
