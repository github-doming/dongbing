package all.gen.sys_exception.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_exception.t.service.SysExceptionTService;
import c.x.platform.root.common.action.BaseAction;
public class SysExceptionTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysExceptionTService service = new SysExceptionTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
