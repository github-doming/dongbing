package c.x.platform.admin.feng.wf.wf_ins_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.entity.WfInsFormTCx;
import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.service.WfInsFormTCxService;
import c.x.platform.admin.feng.wf.wf_ins_form_t.cx.vo.WfInsFormTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInsFormTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_ins_form_t.id");

		WfInsFormTCx entity = (WfInsFormTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfInsFormTCxVo.class, WfInsFormTCx.class, request);

		WfInsFormTCxService service = new WfInsFormTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
