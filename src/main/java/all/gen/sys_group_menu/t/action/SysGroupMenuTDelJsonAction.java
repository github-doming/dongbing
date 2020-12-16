package all.gen.sys_group_menu.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_group_menu.t.service.SysGroupMenuTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupMenuTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysGroupMenuTService service = new SysGroupMenuTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
