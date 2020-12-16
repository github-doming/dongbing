package all.app_admin.app.app_group.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppGroupTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppGroupTService service = new AppGroupTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
