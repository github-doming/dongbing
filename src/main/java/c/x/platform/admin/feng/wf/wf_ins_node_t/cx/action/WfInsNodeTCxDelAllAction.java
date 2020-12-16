package c.x.platform.admin.feng.wf.wf_ins_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.service.WfInsNodeTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsNodeTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInsNodeTCxService service = new WfInsNodeTCxService();
		service.delAll(ids);
		return "index";
	}
}
