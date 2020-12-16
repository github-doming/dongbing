package c.x.platform.admin.feng.wf.wf_def_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_process_t.cx.entity.WfDefProcessTCx;
import c.x.platform.admin.feng.wf.wf_def_process_t.cx.service.WfDefProcessTCxService;
import c.x.platform.admin.feng.wf.wf_def_process_t.cx.vo.WfDefProcessTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefProcessTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_process_t.id");

		WfDefProcessTCx entity = (WfDefProcessTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfDefProcessTCxVo.class,
						WfDefProcessTCx.class, request);

		WfDefProcessTCxService service = new WfDefProcessTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
