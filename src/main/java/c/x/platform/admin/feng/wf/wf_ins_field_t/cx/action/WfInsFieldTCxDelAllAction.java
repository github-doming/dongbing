package c.x.platform.admin.feng.wf.wf_ins_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.service.WfInsFieldTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsFieldTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInsFieldTCxService service = new WfInsFieldTCxService();
		service.delAll(ids);
		return "index";
	}
}
