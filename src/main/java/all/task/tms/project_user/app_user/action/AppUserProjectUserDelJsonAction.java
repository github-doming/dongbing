package all.task.tms.project_user.app_user.action;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
import all.task.tms.project_user.app_user.service.AppUserProjectUserService;
public class AppUserProjectUserDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		AppUserProjectUserService service = new AppUserProjectUserService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
