package all.sys_admin.sys.sys_monitor.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.sys_admin.sys.sys_monitor.t.service.SysMonitorTService;
import c.x.platform.root.common.action.BaseAction;
public class SysMonitorTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMonitorTService service = new SysMonitorTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
