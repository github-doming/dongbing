package all.gen.sys_account.t.action;
import all.gen.sys_account.t.service.SysAccountTService;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysAccountTService service = new SysAccountTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
