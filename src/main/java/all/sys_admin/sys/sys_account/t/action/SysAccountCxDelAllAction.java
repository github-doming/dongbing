package all.sys_admin.sys.sys_account.t.action;
import all.sys_admin.sys.sys_account.t.service.SysAccountService;
import c.a.config.SysConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountCxDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// String[] ids = request.getParameterValues("name_checkbox_ids");
		String ids = request.getParameter("id");
		SysAccountService service = new SysAccountService();
		service.delAll(ids.split(","));
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
