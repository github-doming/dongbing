package all.archit.complex.ey_kaida.layout.check.action;
import c.a.config.SysConfig;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTcpBean;import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.SysUtil;
public class CheckAction extends LoginNotAction {
	public CheckAction() {
		this.database = true;
		transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		if (true) {
			BaseLog.trace("经过CheckAction");
		}
		SysUtil sysUtil = new SysUtil();
		String tenantName = this.request.getParameter(SysConfig.CurrentUserTenant);
		String name = this.request.getParameter(SysConfig.CurrentUserName);
		String password = this.request.getParameter(SysConfig.CurrentUserPassword);
		// 检查账号
		// 检查用户是否存在
		CurrentSysUser currentUser = sysUtil.findSysUser(name);
		if (currentUser == null) {
			this.request.setAttribute(SysConfig.UserErrorKey, SysConfig.UserErrorValue);
			// 跳转
			this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request, response);
			return null;
		} else {
			sysUtil.saveCurrentSysUserByUserId(request, response, currentUser.getSysUserId());
			return "index";
		}
	}
}
