package all.app_admin.app.account_user.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.app_admin.app.account_user.service.AppAccountUserService;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountUserDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppAccountUserService service = new AppAccountUserService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
