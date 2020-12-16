package all.gen.sys_group.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_group.t.service.SysGroupTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysGroupTService service = new SysGroupTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
