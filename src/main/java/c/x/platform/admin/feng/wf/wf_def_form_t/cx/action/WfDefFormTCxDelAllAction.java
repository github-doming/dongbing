package c.x.platform.admin.feng.wf.wf_def_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_form_t.cx.service.WfDefFormTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFormTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefFormTCxService service = new WfDefFormTCxService();
		service.delAll(ids);
		return "index";
	}
}
