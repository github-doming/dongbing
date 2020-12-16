package all.gen.sys_log_request.t.action;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import c.x.platform.root.common.action.BaseAction;
public class SysLogRequestTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysLogRequestTService service = new SysLogRequestTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
