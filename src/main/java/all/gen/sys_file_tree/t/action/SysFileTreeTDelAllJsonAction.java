package all.gen.sys_file_tree.t.action;
import all.gen.sys_file_tree.t.service.SysFileTreeTService;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTreeTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysFileTreeTService service = new SysFileTreeTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
