package all.gen.sys_group_user.t.action;
import all.gen.sys_group_user.t.service.SysGroupUserTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysGroupUserTService service = new SysGroupUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
