package all.gen.sys_menu.t.action;
import all.gen.sys_menu.t.service.SysMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class SysMenuTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysMenuTService service = new SysMenuTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
