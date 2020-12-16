package all.archit.complex.spring_kaida.layout.login.action;
import c.a.util.core.json.JsonTcpBean;import c.x.platform.root.login_not.action.LoginNotAction;
public class AdminLoginAction extends LoginNotAction {
	public AdminLoginAction() {
		transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		return "login";
	}
}
