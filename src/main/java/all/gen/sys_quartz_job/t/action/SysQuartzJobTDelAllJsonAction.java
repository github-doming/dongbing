package all.gen.sys_quartz_job.t.action;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobTDelAllJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		//String ids = request.getParameter("id");
		SysQuartzJobTService service = new SysQuartzJobTService();
		//service.delAll(ids.split(","));
		service.delAll(ids);
		// 跳转
		return this.returnJson(true, "删除成功");
	}
}
