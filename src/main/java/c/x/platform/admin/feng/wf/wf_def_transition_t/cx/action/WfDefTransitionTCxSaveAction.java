package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity.WfDefTransitionTCx;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxService;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.vo.WfDefTransitionTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefTransitionTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_transition_t.id");

		WfDefTransitionTCx entity = (WfDefTransitionTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfDefTransitionTCxVo.class,
						WfDefTransitionTCx.class, request);

		WfDefTransitionTCxService service = new WfDefTransitionTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
