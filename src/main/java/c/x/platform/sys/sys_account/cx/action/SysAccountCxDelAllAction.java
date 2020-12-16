package c.x.platform.sys.sys_account.cx.action;
import c.x.platform.sys.sys_account.cx.service.SysAccountCxService;
import c.a.config.SysConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountCxDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// String[] ids = request.getParameterValues("name_checkbox_ids");
		String ids = request.getParameter("id");
		SysAccountCxService service = new SysAccountCxService();
		service.delAll(ids.split(","));
		// 跳转
		return CommViewEnum.Default.toString();
	}
}
