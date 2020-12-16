package c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.action;

import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.entity.WfDefFieldPermissionTCx;
import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.service.WfDefFieldPermissionTCxService;
import c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.vo.WfDefFieldPermissionTCxVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;

public class WfDefFieldPermissionTCxSaveAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		String id = request.getParameter("wf_def_field_permission_t.id");

		WfDefFieldPermissionTCx entity = (WfDefFieldPermissionTCx) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(WfDefFieldPermissionTCxVo.class,
						WfDefFieldPermissionTCx.class, request);

		WfDefFieldPermissionTCxService service = new WfDefFieldPermissionTCxService();

		if (StringUtil.isBlank(id)) {
			service.save(entity);

		} else {
			service.update(entity);

		}

		return "index";
	}
}
