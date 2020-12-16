package all.gen.sys_file_tree.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_file_tree.t.service.SysFileTreeTService;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTreeTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysFileTreeTService service = new SysFileTreeTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
