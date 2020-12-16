package all.app_admin.app.app_account.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.app_admin.app.app_account.t.service.AppAccountTService;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppAccountTService service = new AppAccountTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
