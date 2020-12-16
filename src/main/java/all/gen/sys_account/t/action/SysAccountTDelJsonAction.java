package all.gen.sys_account.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_account.t.service.SysAccountTService;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysAccountTService service = new SysAccountTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
