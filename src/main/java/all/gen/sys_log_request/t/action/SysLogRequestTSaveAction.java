package all.gen.sys_log_request.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_log_request.t.entity.SysLogRequestT;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import all.gen.sys_log_request.t.vo.SysLogRequestTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysLogRequestTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysLogRequestTService service = new SysLogRequestTService();
		SysLogRequestT entity = null;
		String id = request.getParameter("sys_log_request.sysLogRequestId");
		entity = (SysLogRequestT) RequestThreadLocal.doRequest2Entity(SysLogRequestTVo.class,SysLogRequestT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
