package all.cms.msg.admin.sys_user.t.action;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.cms.msg.admin.sys_user.t.service.SysUserTService;
import c.x.platform.root.common.action.BaseAction;
public class SysUserTDelJsonAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		SysUserTService service = new SysUserTService();
		service.del(id);
		return this.returnJson(true, "删除成功");
	}
}
