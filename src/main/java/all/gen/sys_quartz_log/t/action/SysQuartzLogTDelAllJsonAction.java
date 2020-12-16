package all.gen.sys_quartz_log.t.action;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysQuartzLogTService service = new SysQuartzLogTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
