package c.x.platform.admin.feng.wf.wf_ins_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.entity.WfInsProcessTCx;
import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.service.WfInsProcessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsProcessTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInsProcessTCxService service = new WfInsProcessTCxService();
			WfInsProcessTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
