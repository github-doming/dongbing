package all.gen.sys_pk.t.action;
import all.gen.sys_pk.t.service.SysPkTService;
import c.x.platform.root.common.action.BaseAction;
public class SysPkTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysPkTService service = new SysPkTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
