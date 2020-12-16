package all.gen.sys_log_response.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_log_response.t.service.SysLogResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class SysLogResponseTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysLogResponseTService service = new SysLogResponseTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
