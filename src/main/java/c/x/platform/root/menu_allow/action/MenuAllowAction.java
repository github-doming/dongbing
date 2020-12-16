package c.x.platform.root.menu_allow.action;
import java.util.List;

import all.app_admin.root.login_not.current.CurrentAppUser;
import c.a.tools.string.StringSub;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.action.LoginRepeatAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
/**
 * 
 * 当前用户拥有的的菜单url
 * 
 * @author yourname
 * 
 */
public abstract class MenuAllowAction extends LoginRepeatAction {
	public boolean menuAllow = false;
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
			log.trace("menu_allow=" + menuAllow);
			log.trace("所访问的url=" + this.request.getServletPath());
			log.trace("所访问的action=" + this.getClass().getName());
		}
		if (menuAllow) {
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
		String type=this.findCurrentUserType();
		if ("sys".equals(type)) {
			CurrentSysUser currentUser = this.findCurrentSysUser();
			List<String> urlList = currentUser.getMenuAllowList();
			StringSub stringSub = new StringSub();
			boolean has = stringSub.hasSubStringList(this.request.getServletPath(), urlList);
			if (has) {
				JsonTcpBean returnStr = this.executeMenuAllow();
				return returnStr;
			} else {
				// 会跳到403.jsp,然后改变状态(403 Forbidden)
				this.getResponse().sendError(403);
				return null;
			}
		}
		if ("app".equals(type)) {
			CurrentAppUser currentAppUser = this.findCurrentAppUser();
			List<String> urlList = currentAppUser.getMenuAllowList();
			StringSub stringSub = new StringSub();
			boolean has = stringSub.hasSubStringList(this.request.getServletPath(), urlList);
			if (has) {
				JsonTcpBean returnStr = this.executeMenuAllow();
				return returnStr;
			} else {
				// 会跳到403.jsp,然后改变状态(403 Forbidden)
				this.getResponse().sendError(403);
				return null;
			}
		}
		return null;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
