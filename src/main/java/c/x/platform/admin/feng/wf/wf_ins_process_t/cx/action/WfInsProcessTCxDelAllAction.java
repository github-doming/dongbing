package c.x.platform.admin.feng.wf.wf_ins_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.service.WfInsProcessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsProcessTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInsProcessTCxService service = new WfInsProcessTCxService();
		service.delAll(ids);
		return "index";
	}
}
