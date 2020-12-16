package all.archit.complex.spring.tool.no_login.action;
import c.a.config.SysConfig;
import c.a.tools.log.custom.common.BaseLog;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.util.CookieUtil;
import c.x.platform.root.util.SysUtil;
public abstract class RepeatLoginAction_spring extends LoginNotAction_spring {
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
	public JsonTcpBean  executeLogin() throws Exception {
		if (true) {
			BaseLog.trace("login=" + login);
			BaseLog.trace("所访问的action=" + this.getClass().getName());
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
		// 是否跳转到下一个过滤器
		boolean isGotoNextFilter = false;
		SysUtil ru = new SysUtil();
		/**
		 * 当前用户是否存在, 检测其sessionId, 与用户的sessionId不等，则强制退出。
		 */
		CookieUtil cookieUtil = new CookieUtil();
		String sso = cookieUtil.findCookieValueCurrentSysUser(request, response);
		if (StringUtil.isBlank(sso)) {
			isGotoNextFilter = false;
		} else {
			// 当前用户存在
			// 二次登录
			boolean check = ru.checkSSO(request,response);
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
			String contextPath = request.getContextPath();
			String returnPage = SysConfig.RequestJspLoginSessionNot;
			response.sendRedirect(contextPath + returnPage);
		}
		return null;
	}
}
