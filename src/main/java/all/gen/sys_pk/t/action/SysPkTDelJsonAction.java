package all.gen.sys_pk.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_pk.t.service.SysPkTService;
import c.x.platform.root.common.action.BaseAction;
public class SysPkTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysPkTService service = new SysPkTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
