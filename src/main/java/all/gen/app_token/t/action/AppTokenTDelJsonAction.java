package all.gen.app_token.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_token.t.service.AppTokenTService;
import c.x.platform.root.common.action.BaseAction;
public class AppTokenTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppTokenTService service = new AppTokenTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
