package all.gen.app_config.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_config.t.service.AppConfigTService;
import c.x.platform.root.common.action.BaseAction;
public class AppConfigTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppConfigTService service = new AppConfigTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
