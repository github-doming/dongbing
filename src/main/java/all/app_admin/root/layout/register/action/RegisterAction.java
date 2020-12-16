package all.app_admin.root.layout.register.action;
import all.gen.app_account.t.entity.AppAccountT;
import all.app_admin.app.app_account.t.service.AppAccountTService;
import c.a.config.SysConfig;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.action.LoginNotAction;
public class RegisterAction extends LoginNotAction {
	public RegisterAction() {
		this.database = true;
		transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
	@Override
	public String execute() throws Exception {
		String name = this.request.getParameter(SysConfig.CurrentUserName);
		String password = this.request.getParameter(SysConfig.CurrentUserPassword);
		// 判断系统是否存在用户名
		AppAccountTService sysAccountTCxService = new AppAccountTService();
		if (sysAccountTCxService.isUserByName(name)) {
		} else {
			AppAccountT acc = new AppAccountT();
			acc.setAccountName(name);
			acc.setPassword(password);
			sysAccountTCxService.save(acc);
		}
		return "index";
	}
}
