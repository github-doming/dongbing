package all.gen.app_log.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_log.t.service.AppLogTService;
import c.x.platform.root.common.action.BaseAction;
public class AppLogTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppLogTService service = new AppLogTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
