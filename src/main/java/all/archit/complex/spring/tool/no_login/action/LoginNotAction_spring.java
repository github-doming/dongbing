package all.archit.complex.spring.tool.no_login.action;
import all.app_admin.root.login_not.current.CurrentAppUser;
import all.archit.complex.spring.crud.TransactionSpringByAction;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.CookieUtil;
public abstract class LoginNotAction_spring extends TransactionSpringByAction {
	public static Boolean login = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeLogin() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		if (false) {
		}
		if (true) {
			BaseLog.trace("login=" + login);
			BaseLog.trace("所访问的action=" + this.getClass().getName());
		}
		if (login) {
			/**
			 * 
			 * 需要login
			 */
			return this.login();
		} else {
			/**
			 * 
			 * 不需要login
			 */
			return this.login_no();
		}
	}
	/**
	 * 
	 * 不需要login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean login_no() throws Exception {
		JsonTcpBean returnStr = this.executeLogin();
		return returnStr;
	}
	/**
	 * 
	 * 需要login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean login() throws Exception {
		// if (true) {
		if (false) {
			BaseLog.trace("----------------------------------------");
			BaseLog.trace("过滤器");
			BaseLog.trace("ServletPath=" + this.request.getServletPath());
		}
		if (true) {
			/**
			 * 
			 * 任一页面;
			 */
			CookieUtil cookieUtil = new CookieUtil();
			String sso = cookieUtil.findCookieValueCurrentSysUser(request, response);
			if (StringUtil.isNotBlank(sso)) {
				// 下一个过滤器
				JsonTcpBean returnStr = this.executeLogin();
				return returnStr;
			} else {
				if (false) {
					BaseLog.trace("2过滤器UserLoginFilter");
				}
				// 跳转
				if (false) {
					request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(request, response);
				}
				request.getRequestDispatcher(SysConfig.RequestJspLoginSessionNot).forward(request, response);
			}
		}
		return null;
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentSysUser findCurrentSysUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentSysUserCore(request, response);
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public CurrentAppUser findCurrentAppUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentAppUserCore(request, response);
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 */
	public Object findCurrentUser() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUser(request, response);
	}
	/**
	 * 当前登录用户的id
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findCurrentUserId() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserId(request, response);
	}
	/**
	 * 当前登录用户的PermissionGrade
	 * 
	 * @return
	 * @throws Exception
	 */
	public Integer findCurrentUserPermissionGrade() throws Exception {
		return ContextThreadLocal.findThreadLocal().get().findCurrentUserPermissionGrade(request, response);
	}
}
