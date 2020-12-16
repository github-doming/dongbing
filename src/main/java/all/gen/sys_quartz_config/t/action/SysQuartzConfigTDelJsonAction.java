package all.gen.sys_quartz_config.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_quartz_config.t.service.SysQuartzConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class SysQuartzConfigTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysQuartzConfigTService service = new SysQuartzConfigTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
