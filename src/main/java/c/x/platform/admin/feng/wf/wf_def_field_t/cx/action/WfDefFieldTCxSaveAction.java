package c.x.platform.admin.feng.wf.wf_def_field_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_t.cx.entity.WfDefFieldTCx;
import c.x.platform.admin.feng.wf.wf_def_field_t.cx.service.WfDefFieldTCxService;
import c.x.platform.admin.feng.wf.wf_def_field_t.cx.vo.WfDefFieldTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefFieldTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_field_t.id");

		WfDefFieldTCx entity = (WfDefFieldTCx) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				WfDefFieldTCxVo.class, WfDefFieldTCx.class, request);

		WfDefFieldTCxService service = new WfDefFieldTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
