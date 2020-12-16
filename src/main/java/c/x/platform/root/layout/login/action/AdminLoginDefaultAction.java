package c.x.platform.root.layout.login.action;
import c.a.config.SysConfig;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.action.LoginNotAction;
public class AdminLoginDefaultAction extends LoginNotAction {
	public AdminLoginDefaultAction() {
		transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
	@Override
	public String execute() throws Exception {
		// 跳转
		return CommViewEnum.Default.toString();

	}
}
