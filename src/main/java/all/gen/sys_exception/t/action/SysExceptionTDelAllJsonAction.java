package all.gen.sys_exception.t.action;
import all.gen.sys_exception.t.service.SysExceptionTService;
import c.x.platform.root.common.action.BaseAction;
public class SysExceptionTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysExceptionTService service = new SysExceptionTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
