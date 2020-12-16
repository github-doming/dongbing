package all.gen.app_log_response.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_log_response.t.service.AppLogResponseTService;
import c.x.platform.root.common.action.BaseAction;
public class AppLogResponseTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppLogResponseTService service = new AppLogResponseTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
