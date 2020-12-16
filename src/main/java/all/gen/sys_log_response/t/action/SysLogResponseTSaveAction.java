package all.gen.sys_log_response.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_log_response.t.entity.SysLogResponseT;
import all.gen.sys_log_response.t.service.SysLogResponseTService;
import all.gen.sys_log_response.t.vo.SysLogResponseTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysLogResponseTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysLogResponseTService service = new SysLogResponseTService();
		SysLogResponseT entity = null;
		String id = request.getParameter("sys_log_response.sysLogResponseId");
		entity = (SysLogResponseT) RequestThreadLocal.doRequest2Entity(SysLogResponseTVo.class,SysLogResponseT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
