package c.x.platform.admin.feng.wf.wf_ins_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.entity.WfInsNodeTCx;
import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.service.WfInsNodeTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsNodeTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfInsNodeTCxService service = new WfInsNodeTCxService();
			WfInsNodeTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
