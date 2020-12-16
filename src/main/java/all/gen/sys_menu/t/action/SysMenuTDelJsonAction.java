package all.gen.sys_menu.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_menu.t.service.SysMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class SysMenuTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysMenuTService service = new SysMenuTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
