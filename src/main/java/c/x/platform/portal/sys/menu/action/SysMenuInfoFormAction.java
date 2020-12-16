package c.x.platform.portal.sys.menu.action;
import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import all.gen.sys_menu.t.entity.SysMenuT;
import c.x.platform.portal.sys.menu.service.SysMenuInfoService;
import c.x.platform.root.login_not.action.LoginNotAction;
//public class SysMenuInfoActionNew extends TransactionAction {
public class SysMenuInfoFormAction extends LoginNotAction {
	public SysMenuInfoFormAction() {
		transaction = true;
		this.login = false;
		// this.login=true;
	}
	public JsonTcpBean  executeLogin() throws Exception {
		String returnStr = SysConfig.configValueTrue;
		return this.returnJsonTcpBean(returnStr);
	}
	@Override
	public String execute() throws Exception {
		String id = (String) request.getParameter("id");
		if (id != null) {
			SysMenuInfoService service = new SysMenuInfoService();
			SysMenuT s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
		}
		return "index";
	}
}
