package all.sys_admin.sys.sys_monitor.t.action;
import all.sys_admin.sys.sys_monitor.t.service.SysMonitorTService;
import c.x.platform.root.common.action.BaseAction;
public class SysMonitorTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysMonitorTService service = new SysMonitorTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
