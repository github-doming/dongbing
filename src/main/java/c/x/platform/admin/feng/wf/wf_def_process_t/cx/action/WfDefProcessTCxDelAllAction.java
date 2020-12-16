package c.x.platform.admin.feng.wf.wf_def_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_process_t.cx.service.WfDefProcessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefProcessTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefProcessTCxService service = new WfDefProcessTCxService();
		service.delAll(ids);
		return "index";
	}
}
