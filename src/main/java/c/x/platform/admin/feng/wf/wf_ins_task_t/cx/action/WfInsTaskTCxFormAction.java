package c.x.platform.admin.feng.wf.wf_ins_task_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.entity.WfInsTaskTCx;
import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.service.WfInsTaskTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsTaskTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInsTaskTCxService service = new WfInsTaskTCxService();
			WfInsTaskTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
