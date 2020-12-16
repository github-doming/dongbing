package all.gen.sys_quartz_log.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_log.t.service.SysQuartzLogTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzLogTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysQuartzLogTService service = new SysQuartzLogTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
