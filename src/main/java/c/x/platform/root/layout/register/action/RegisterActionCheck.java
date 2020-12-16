package c.x.platform.root.layout.register.action;

import java.util.Map;

import c.a.config.SysConfig;
import c.a.config.login.RequestLoginConfigCy;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.sys.sys_user.cx.service.SysUserCxService;

public class RegisterActionCheck extends LoginNotAction {
	public RegisterActionCheck() {
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
		String jsonStr = this.request.getParameter("json");

		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(jsonStr);

		String name = (String) jsonMap.get(RequestLoginConfigCy.CurrentUserName);
		String password =  (String) jsonMap.get(RequestLoginConfigCy.CurrentUserPassword);

		// 判断系统是否存在用户名
		SysUserCxService userService = new SysUserCxService();
		if (userService.isUserByName(name)) {
			// 跳转
			JsonTcpBean jrb = new JsonTcpBean();
			jrb.setSuccess(false);
			jrb.setMsgSys("用户名已经存在");
			String resultJsonStr = JsonThreadLocal.bean2json(jrb);
			response.getWriter().print(resultJsonStr);
			return null;

		} else {

			// 跳转
			JsonTcpBean jrb = new JsonTcpBean();
			jrb.setSuccess(true);
			jrb.setMsgSys("用户名不存在");
			String resultJsonStr = JsonThreadLocal.bean2json(jrb);
			response.getWriter().print(resultJsonStr);
			return null;

		}

		

	}

}
