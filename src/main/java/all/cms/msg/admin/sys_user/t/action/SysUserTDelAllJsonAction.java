package all.cms.msg.admin.sys_user.t.action;
import all.cms.msg.admin.sys_user.t.service.SysUserTService;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysUserTService service = new SysUserTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
