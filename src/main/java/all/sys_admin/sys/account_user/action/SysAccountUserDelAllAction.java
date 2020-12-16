package all.sys_admin.sys.account_user.action;
import all.sys_admin.sys.account_user.service.SysAccountUserService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountUserDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// String[] ids = request.getParameterValues("name_checkbox_ids");
		String ids = request.getParameter("id");
		SysAccountUserService service = new SysAccountUserService();
		service.delAll(ids.split(","));
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
