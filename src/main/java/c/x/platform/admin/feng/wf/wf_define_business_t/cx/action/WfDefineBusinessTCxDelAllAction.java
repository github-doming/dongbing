package c.x.platform.admin.feng.wf.wf_define_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_define_business_t.cx.service.WfDefineBusinessTCxService;
import c.x.platform.root.common.action.BaseAction;

public class WfDefineBusinessTCxDelAllAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		WfDefineBusinessTCxService service = new WfDefineBusinessTCxService();
		service.delAll(ids);
		return "index";
	}
}
