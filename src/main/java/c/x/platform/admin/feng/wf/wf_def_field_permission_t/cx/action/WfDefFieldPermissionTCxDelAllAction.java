package c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.service.WfDefFieldPermissionTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFieldPermissionTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefFieldPermissionTCxService service = new WfDefFieldPermissionTCxService();
		service.delAll(ids);
		return "index";
	}
}
