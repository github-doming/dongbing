package all.gen.sys_group_user.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_group_user.t.service.SysGroupUserTService;
import c.x.platform.root.common.action.BaseAction;
public class SysGroupUserTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysGroupUserTService service = new SysGroupUserTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
