package all.gen.sys_group_menu.t.action;
import all.gen.sys_group_menu.t.service.SysGroupMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupMenuTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysGroupMenuTService service = new SysGroupMenuTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
