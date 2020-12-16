package c.x.platform.root.tag.core;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import all.app_admin.root.login_not.current.CurrentAppUser;
import c.a.config.core.ContextThreadLocal;
import c.x.platform.root.login_not.current.CurrentSysUser;
public abstract class BaseBodyTagSupport extends RootBodyTagSupport {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception 
	 */
	public CurrentSysUser findCurrentSysUser() throws Exception {
		//HttpSession httpSession = this.pageContext.getSession();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
		CurrentSysUser currentUser =ContextThreadLocal.findThreadLocal().get().findCurrentSysUserCore(request, response);
		return currentUser;
	}
	/**
	 * 
	 * 当前登录用户
	 * 
	 * @return
	 * @throws Exception 
	 */
	public CurrentAppUser findCurrentAppUser() throws Exception {
		//HttpSession httpSession = this.pageContext.getSession();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
		CurrentAppUser currentUser = ContextThreadLocal.findThreadLocal().get().findCurrentAppUserCore(request, response);
		return currentUser;
	}
	public List<String> findPermissionCodeList() throws Exception {
		//HttpSession httpSession = this.pageContext.getSession();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();
		String type = ContextThreadLocal.findThreadLocal().get().findCurrentUserType(request, response);
		if ("sys".equals(type)) {
			CurrentSysUser currentUser = this.findCurrentSysUser();
			if (currentUser == null) {
				return null;
			} else {
				return currentUser.getPermissionCodeList();
			}
		}
		if ("app".equals(type)) {
			CurrentAppUser currentUser = this.findCurrentAppUser();
			if (currentUser == null) {
				return null;
			} else {
				return currentUser.getPermissionCodeList();
			}
		}
		return null;
	}
}
