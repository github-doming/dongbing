package c.x.platform.admin.feng.wf.wf_ins_task_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.entity.WfInsTaskTCx;
import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.service.WfInsTaskTCxService;
import c.x.platform.admin.feng.wf.wf_ins_task_t.cx.vo.WfInsTaskTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInsTaskTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_ins_task_t.id");

		WfInsTaskTCx entity = (WfInsTaskTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfInsTaskTCxVo.class, WfInsTaskTCx.class, request);

		WfInsTaskTCxService service = new WfInsTaskTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
