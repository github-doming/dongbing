package c.x.platform.admin.feng.wf.wf_ins_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.entity.WfInsFieldTCx;
import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.service.WfInsFieldTCxService;
import c.x.platform.admin.feng.wf.wf_ins_field_t.cx.vo.WfInsFieldTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInsFieldTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_ins_field_t.id");

		WfInsFieldTCx entity = (WfInsFieldTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfInsFieldTCxVo.class, WfInsFieldTCx.class, request);

		WfInsFieldTCxService service = new WfInsFieldTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
