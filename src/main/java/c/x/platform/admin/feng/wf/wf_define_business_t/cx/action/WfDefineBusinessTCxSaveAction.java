package c.x.platform.admin.feng.wf.wf_define_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_define_business_t.cx.entity.WfDefineBusinessTCx;
import c.x.platform.admin.feng.wf.wf_define_business_t.cx.service.WfDefineBusinessTCxService;
import c.x.platform.admin.feng.wf.wf_define_business_t.cx.vo.WfDefineBusinessTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefineBusinessTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_define_business_t.id");

		WfDefineBusinessTCx entity = (WfDefineBusinessTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfDefineBusinessTCxVo.class,
						WfDefineBusinessTCx.class, request);

		WfDefineBusinessTCxService service = new WfDefineBusinessTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
