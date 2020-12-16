package c.x.platform.admin.feng.wf.wf_instance_business_t.cx.action;

import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.entity.WfInstanceBusinessTCx;
import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.service.WfInstanceBusinessTCxService;
import c.x.platform.admin.feng.wf.wf_instance_business_t.cx.vo.WfInstanceBusinessTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfInstanceBusinessTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_instance_business_t.id");

		WfInstanceBusinessTCx entity = (WfInstanceBusinessTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfInstanceBusinessTCxVo.class,
						WfInstanceBusinessTCx.class, request);

		WfInstanceBusinessTCxService service = new WfInstanceBusinessTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
