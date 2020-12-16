package all.gen.sys_bytes.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_bytes.t.service.SysBytesTService;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysBytesTService service = new SysBytesTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
