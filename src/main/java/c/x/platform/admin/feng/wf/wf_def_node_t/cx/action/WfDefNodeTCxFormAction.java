package c.x.platform.admin.feng.wf.wf_def_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_node_t.cx.entity.WfDefNodeTCx;
import c.x.platform.admin.feng.wf.wf_def_node_t.cx.service.WfDefNodeTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefNodeTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefNodeTCxService service = new WfDefNodeTCxService();
			WfDefNodeTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
