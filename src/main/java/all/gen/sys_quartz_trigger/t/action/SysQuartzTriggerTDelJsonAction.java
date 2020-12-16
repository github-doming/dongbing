package all.gen.sys_quartz_trigger.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_trigger.t.service.SysQuartzTriggerTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzTriggerTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysQuartzTriggerTService service = new SysQuartzTriggerTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
