package c.x.platform.root.login_not.action;
import all.app_admin.root.util.AppUtil;
import c.a.config.SysConfig;
import c.a.config.login.LoginUrlDy;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.util.CookieUtil;
import c.x.platform.root.util.SysUtil;
public abstract class LoginRepeatAction extends LoginNotAction {
	// public static Boolean login = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeRepeatLogin() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeLogin() throws Exception {
		if (false) {
			log.trace("login=" + login);
			log.trace("所访问的action=" + this.getClass().getName());
		}
		if (login) {
			/**
			 * 
			 * 需要login
			 */
			return this.repeatLogin();
		} else {
			/**
			 * 
			 * 不需要login
			 */
			return this.repeatLoginNot();
		}
	}
	/**
	 * 
	 * 不需要二次login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean repeatLoginNot() throws Exception {
		JsonTcpBean returnStr = this.executeRepeatLogin();
		return returnStr;
	}
	/**
	 * 
	 * 需要二次login
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean repeatLogin() throws Exception {
		CookieUtil cookieUtil = new CookieUtil();
		// 是否跳转到下一个过滤器
		boolean isGotoNextFilter = false;
		if ("sys".equals(this.findCurrentUserType())) {
			SysUtil sysUtil = new SysUtil();
			/**
			 * 当前用户是否存在, 检测其token，不存在则强制退出。
			 */
			String sso = cookieUtil.findCookieValueCurrentSysUser(request, response);
			if (StringUtil.isBlank(sso)) {
				isGotoNextFilter = false;
			} else {
				// 当前用户存在
				// 二次登录
				boolean check = sysUtil.checkSSO(request, response);
				if (check) {
					isGotoNextFilter = true;
				} else {
					isGotoNextFilter = false;
				}
			}
			if (isGotoNextFilter) {
				// 下一个过滤器
				JsonTcpBean returnStr = this.executeRepeatLogin();
				return returnStr;
			} else {
				// String
				// contextPath=(String)SysConfig.findInstance().findMap().get(SysConfig.keyContextPath);
				String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
				LoginUrlDy.RequestJspLoginSessionNot = SysConfig.findInstance().findMap().get("jsp.session.not")
						.toString();
				String returnPage = LoginUrlDy.RequestJspLoginSessionNot;
				response.sendRedirect(contextPath + returnPage);
			}
		}
		if ("app".equals(this.findCurrentUserType())) {
			AppUtil appUtil = new AppUtil();
			/**
			 * 当前用户是否存在, 检测其sessionId, 与用户的sessionId不等，则强制退出。
			 */
			String sso = cookieUtil.findCookieValueCurrentAppUser(request, response);
			if (StringUtil.isBlank(sso)) {
				isGotoNextFilter = false;
			} else {
				// 当前用户存在
				// 二次登录
				boolean check = appUtil.checkSSO(request, response);
				if (check) {
					isGotoNextFilter = true;
				} else {
					isGotoNextFilter = false;
				}
			}
			if (isGotoNextFilter) {
				// 下一个过滤器
				JsonTcpBean returnStr = this.executeRepeatLogin();
				return returnStr;
			} else {
				// String
				// contextPath=(String)SysConfig.findInstance().findMap().get(SysConfig.keyContextPath);
				String contextPath = RequestThreadLocal.findThreadLocal().get().findContextPath(request);
				SysConfig.RequestJspLoginSessionNot = SysConfig.findInstance().findMap().get("jsp.session.not")
						.toString();
				String returnPage = SysConfig.RequestJspLoginSessionNot;
				response.sendRedirect(contextPath + returnPage);
			}
		}
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
