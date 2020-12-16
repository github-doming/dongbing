package all.gen.app_user_group.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_user_group.t.service.AppUserGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppUserGroupTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppUserGroupTService service = new AppUserGroupTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
