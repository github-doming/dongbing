package c.x.platform.admin.feng.wf.wf_ins_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.entity.WfInsNodeTCx;
import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.service.WfInsNodeTCxService;
import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.vo.WfInsNodeTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInsNodeTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_ins_node_t.id");

		WfInsNodeTCx entity = (WfInsNodeTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfInsNodeTCxVo.class, WfInsNodeTCx.class, request);

		WfInsNodeTCxService service = new WfInsNodeTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
