package c.x.platform.admin.feng.wf.wf_def_node_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_node_t.cx.entity.WfDefNodeTCx;
import c.x.platform.admin.feng.wf.wf_def_node_t.cx.service.WfDefNodeTCxService;
import c.x.platform.admin.feng.wf.wf_def_node_t.cx.vo.WfDefNodeTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefNodeTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_node_t.id");

		WfDefNodeTCx entity = (WfDefNodeTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfDefNodeTCxVo.class, WfDefNodeTCx.class, request);

		WfDefNodeTCxService service = new WfDefNodeTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
