package c.x.platform.admin.feng.wf.wf_ins_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.service.WfInsFormTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfInsFormTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfInsFormTCxService service = new WfInsFormTCxService();
		service.delAll(ids);
		return "index";
	}
}
