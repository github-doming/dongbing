package all.app_admin.app.app_user.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.app_admin.app.app_user.t.service.AppUserTService;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppUserTService service = new AppUserTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
