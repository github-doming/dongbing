package c.x.platform.admin.feng.wf.wf_ins_task_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.service.WfInsTaskTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsTaskTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInsTaskTCxService service = new WfInsTaskTCxService();
		service.delAll(ids);
		return "index";
	}
}
