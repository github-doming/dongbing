package all.gen.sys_file_ref.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_file_ref.t.service.SysFileRefTService;
import c.x.platform.root.common.action.BaseAction;
public class SysFileRefTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysFileRefTService service = new SysFileRefTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
