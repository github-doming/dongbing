package all.gen.sys_log_request.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_log_request.t.entity.SysLogRequestT;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import all.gen.sys_log_request.t.vo.SysLogRequestTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysLogRequestTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysLogRequestTService service = new SysLogRequestTService();
		SysLogRequestT entity = null;
		String id = request.getParameter("id");
		entity = (SysLogRequestT) RequestThreadLocal.doRequest2EntityByJson(SysLogRequestTVo.class, SysLogRequestT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
