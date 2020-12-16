package all.sys_admin.sys.sys_monitor.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.sys_admin.sys.sys_monitor.t.entity.SysMonitorT;
import all.sys_admin.sys.sys_monitor.t.service.SysMonitorTService;
import all.sys_admin.sys.sys_monitor.t.vo.SysMonitorTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysMonitorTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysMonitorTService service = new SysMonitorTService();
		SysMonitorT entity = null;
		String id = request.getParameter("sys_monitor.sysMonitorId");
		entity = (SysMonitorT) RequestThreadLocal.doRequest2Entity(SysMonitorTVo.class,SysMonitorT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
