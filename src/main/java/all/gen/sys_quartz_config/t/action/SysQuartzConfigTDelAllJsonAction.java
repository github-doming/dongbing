package all.gen.sys_quartz_config.t.action;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysQuartzConfigTService service = new SysQuartzConfigTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
