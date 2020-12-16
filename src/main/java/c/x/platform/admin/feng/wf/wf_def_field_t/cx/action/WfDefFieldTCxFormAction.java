package c.x.platform.admin.feng.wf.wf_def_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_t.cx.entity.WfDefFieldTCx;
import c.x.platform.admin.feng.wf.wf_def_field_t.cx.service.WfDefFieldTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFieldTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefFieldTCxService service = new WfDefFieldTCxService();
			WfDefFieldTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
