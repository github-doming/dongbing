package c.x.platform.root.layout.register.action;
import c.a.config.SysConfig;
import c.a.config.login.RequestLoginConfigCy;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.action.LoginNotAction;
import all.gen.sys_account.t.entity.SysAccountT;
import c.x.platform.sys.sys_account.cx.service.SysAccountCxService;
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
		String name = this.request.getParameter(RequestLoginConfigCy.CurrentUserName);
		String password = this.request.getParameter(RequestLoginConfigCy.CurrentUserPassword);
		// 判断系统是否存在用户名
		SysAccountCxService sysAccountTCxService = new SysAccountCxService();
		if (sysAccountTCxService.isUserByName(name)) {
		} else {
			SysAccountT acc = new SysAccountT();
			acc.setSysAccountName(name);
			acc.setPassword(password);
			sysAccountTCxService.save(acc);
		}
		return "index";
	}
}
