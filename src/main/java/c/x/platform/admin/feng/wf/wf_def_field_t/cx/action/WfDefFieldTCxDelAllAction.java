package c.x.platform.admin.feng.wf.wf_def_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_t.cx.service.WfDefFieldTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefFieldTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefFieldTCxService service = new WfDefFieldTCxService();
		service.delAll(ids);
		return "index";
	}
}
