package c.x.platform.admin.feng.wf.wf_def_form_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_form_t.cx.entity.WfDefFormTCx;
import c.x.platform.admin.feng.wf.wf_def_form_t.cx.service.WfDefFormTCxService;
import c.x.platform.admin.feng.wf.wf_def_form_t.cx.vo.WfDefFormTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefFormTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_form_t.id");

		WfDefFormTCx entity = (WfDefFormTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfDefFormTCxVo.class, WfDefFormTCx.class, request);

		WfDefFormTCxService service = new WfDefFormTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
