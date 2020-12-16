package c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.entity.WfDefFieldPermissionTCx;
import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.service.WfDefFieldPermissionTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFieldPermissionTCxFormAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			WfDefFieldPermissionTCxService service = new WfDefFieldPermissionTCxService();
			WfDefFieldPermissionTCx entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);
		}
		return "index";
	}
}
