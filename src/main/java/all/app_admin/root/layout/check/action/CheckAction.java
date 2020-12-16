package all.app_admin.root.layout.check.action;
import org.eclipse.jetty.util.StringUtil;

import all.app_admin.root.login_not.current.CurrentAppUser;
import all.app_admin.root.util.AppUtil;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.login_not.action.LoginNotAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
import c.x.platform.root.util.CookieUtil;
import c.x.platform.root.util.SysUtil;
public class CheckAction extends LoginNotAction {
	private String logName = "经过CheckAction";
	public CheckAction() {
		this.database = true;
		this.transaction = true;
		this.login = false;
	}
	@Override
	public JsonTcpBean  executeLogin() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
	@Override
	public String execute() throws Exception {
		try {
			log.trace(logName);
		
			// 检查type
			String type = this.request.getParameter(SysConfig.CurrentUserType);
			log.trace("type=" + type);
			if (StringUtil.isBlank(type)) {
				type = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalType), "");
			}
			// 检查机构名
			String tenantCode = this.request.getParameter(SysConfig.CurrentUserTenant);
			log.trace("tenantCode=" + tenantCode);
			if (StringUtil.isBlank(tenantCode)) {
				tenantCode = BeanThreadLocal.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalTenant),
						"");
			}
			
			if (StringUtil.isBlank(tenantCode)) {
				this.request.setAttribute(SysConfig.TenantErrorKey, SysConfig.TenantErrorValue);
				// 跳转
				this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request, response);
				return null;
			}
			CookieUtil cookieUtil = new CookieUtil();
			cookieUtil.saveCookieCurrentType(response, type);
			cookieUtil.saveCookieCurrentTenant(response, tenantCode);
			if ("sys".equals(type)) {
				SysUtil sysUtil = new SysUtil();
				String currentUserName = this.request.getParameter(SysConfig.CurrentUserName);
				log.trace("CurrentUserName=" + currentUserName);
				String password = this.request.getParameter(SysConfig.CurrentUserPassword);
				// 检查用户是否存在
				CurrentSysUser currentUser = sysUtil.findSysUser(currentUserName, password);
				if (currentUser == null) {
					this.request.setAttribute(SysConfig.UserErrorKey, SysConfig.UserErrorValue);
					// 跳转
					this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request, response);
					return null;
				} else {
					sysUtil.saveCurrentSysUserByUserId(request, response, currentUser.getSysUserId());
					return CommViewEnum.Default.toString();
				}
			}
			if ("app".equals(type)) {
				AppUtil appUtil = new AppUtil();
				String currentUserName = this.request.getParameter(SysConfig.CurrentUserName);
				log.trace("CurrentUserName=" + currentUserName);
				String password = this.request.getParameter(SysConfig.CurrentUserPassword);
				// 检查用户是否存在
				CurrentAppUser currentUser = appUtil.findAppUser(currentUserName, password);
				if (currentUser == null) {
					this.request.setAttribute(SysConfig.UserErrorKey, SysConfig.UserErrorValue);
					// 跳转
					this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request, response);
					return null;
				} else {
					appUtil.saveCurrentAppUserByUserId(request, response, currentUser.getAppUserId());
					return CommViewEnum.Default.toString();
				}
			}
		} catch (Exception e) {
			log.error(logName);
			log.error(logName, e);
			this.request.setAttribute(SysConfig.UserErrorKey, SysConfig.UserErrorValue);
			// 跳转
			this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request, response);
			return null;
		}
		log.error("找不到type");
		this.request.setAttribute(SysConfig.UserErrorKey, SysConfig.UserErrorValue);
		// 跳转
		// this.request.getRequestDispatcher(SysConfig.RequestJspLogin).forward(this.request,
		// response);
		return null;
	}
}
