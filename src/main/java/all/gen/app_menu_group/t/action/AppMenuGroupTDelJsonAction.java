package all.gen.app_menu_group.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.app_menu_group.t.service.AppMenuGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class AppMenuGroupTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppMenuGroupTService service = new AppMenuGroupTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
