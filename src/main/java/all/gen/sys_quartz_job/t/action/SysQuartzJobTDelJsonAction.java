package all.gen.sys_quartz_job.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_job.t.service.SysQuartzJobTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzJobTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysQuartzJobTService service = new SysQuartzJobTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
