package all.gen.sys_group.t.action;
import all.gen.sys_group.t.service.SysGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysGroupTService service = new SysGroupTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
