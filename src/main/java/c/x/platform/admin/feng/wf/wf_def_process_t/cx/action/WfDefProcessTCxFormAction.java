package c.x.platform.admin.feng.wf.wf_def_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_process_t.cx.entity.WfDefProcessTCx;
import c.x.platform.admin.feng.wf.wf_def_process_t.cx.service.WfDefProcessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefProcessTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefProcessTCxService service = new WfDefProcessTCxService();
			WfDefProcessTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
