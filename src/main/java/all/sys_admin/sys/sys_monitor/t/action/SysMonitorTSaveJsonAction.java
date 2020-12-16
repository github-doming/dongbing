package all.sys_admin.sys.sys_monitor.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.sys_admin.sys.sys_monitor.t.entity.SysMonitorT;
import all.sys_admin.sys.sys_monitor.t.service.SysMonitorTService;
import all.sys_admin.sys.sys_monitor.t.vo.SysMonitorTVo;
import c.x.platform.root.common.action.BaseAction;
public class SysMonitorTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysMonitorTService service = new SysMonitorTService();
		SysMonitorT entity = null;
		String id = request.getParameter("id");
		entity = (SysMonitorT) RequestThreadLocal.doRequest2EntityByJson(SysMonitorTVo.class, SysMonitorT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
