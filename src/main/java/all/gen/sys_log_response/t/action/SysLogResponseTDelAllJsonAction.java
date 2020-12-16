package all.gen.sys_log_response.t.action;
import all.gen.sys_log_response.t.service.SysLogResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class SysLogResponseTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysLogResponseTService service = new SysLogResponseTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
