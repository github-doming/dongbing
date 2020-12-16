package c.x.platform.admin.feng.wf.wf_ins_process_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.entity.WfInsProcessTCx;
import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.service.WfInsProcessTCxService;
import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.vo.WfInsProcessTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInsProcessTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_ins_process_t.id");

		WfInsProcessTCx entity = (WfInsProcessTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfInsProcessTCxVo.class,
						WfInsProcessTCx.class, request);

		WfInsProcessTCxService service = new WfInsProcessTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
