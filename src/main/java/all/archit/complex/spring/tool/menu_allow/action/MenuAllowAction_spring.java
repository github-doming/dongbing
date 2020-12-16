package all.archit.complex.spring.tool.menu_allow.action;
import java.util.List;

import all.archit.complex.spring.tool.no_login.action.RepeatLoginAction_spring;
import c.a.tools.log.custom.common.BaseLog;
import c.a.tools.string.StringSub;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.current.CurrentSysUser;
public abstract class MenuAllowAction_spring extends RepeatLoginAction_spring {
	// -- 下面的方法不再更新 --//
	// {
	public static Boolean menu_allow = false;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeMenuAllow() throws Exception;
	/**
	 * 实现父类方法
	 * 
	 */
	@Override
	public JsonTcpBean executeRepeatLogin() throws Exception {
		if (false) {
			BaseLog.trace("所访问的action=" + this.getClass().getName());
		}
		if (true) {
			BaseLog.trace("menu_allow=" + menu_allow);
			BaseLog.trace("所访问的url=" + this.request.getServletPath());
			BaseLog.trace("所访问的action=" + this.getClass().getName());
		}
		if (menu_allow) {
			/**
			 * 
			 * 需要menu_allow
			 */
			return this.menuAllow();
		} else {
			/**
			 * 
			 * 不需要menu_allow
			 */
			return this.menuAllowNot();
		}
	}
	/**
	 * 
	 * 不需要menu_allow
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean menuAllowNot() throws Exception {
		JsonTcpBean returnStr = this.executeMenuAllow();
		return returnStr;
	}
	/**
	 * 
	 * 需要menu_allow
	 * 
	 * @return
	 * @throws Exception
	 */
	public JsonTcpBean menuAllow() throws Exception {
		CurrentSysUser currentUser = this.findCurrentSysUser();
		List<String> urlList = currentUser.getMenuAllowList();
		if (false) {
			for (String url : urlList) {
				BaseLog.trace("允许url=" + url);
			}
		}
		StringSub stringSub = new StringSub();
		boolean has = stringSub.hasSubStringList(this.request.getServletPath(),
				urlList);
		if (has) {
			JsonTcpBean returnStr = this.executeMenuAllow();
			return returnStr;
		} else {
			// 会跳到403.jsp,然后改变状态(403 Forbidden)
			this.getResponse().sendError(403);
			return null;
		}
	}
	// }
	// -- 上面的方法不再更新 --/
}
