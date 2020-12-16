package c.x.platform.admin.feng.wf.wf_def_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_node_t.cx.service.WfDefNodeTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefNodeTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefNodeTCxService service = new WfDefNodeTCxService();
		service.delAll(ids);
		return "index";
	}
}
