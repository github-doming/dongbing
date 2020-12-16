package all.sys_admin.sys.dict.sys_dict.action;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.x.platform.root.common.action.BaseAction;
import all.sys_admin.sys.dict.sys_dict.service.SysDictTService;
public class SysDictTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysDictTService service = new SysDictTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
