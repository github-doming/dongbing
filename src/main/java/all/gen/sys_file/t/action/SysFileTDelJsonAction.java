package all.gen.sys_file.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_file.t.service.SysFileTService;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysFileTService service = new SysFileTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
