package all.app_admin.app.account_user.action;
import java.util.List;
import org.eclipse.jetty.util.StringUtil;
import all.app_admin.app.account_user.service.AppAccountUserService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_group.t.entity.AppGroupT;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountUserFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String accountId = (String) request.getParameter("id");
		// 声明
		AppGroupTService groupService = new AppGroupTService();
		AppAccountUserService appAccountUserService = new AppAccountUserService();
		if (StringUtil.isNotBlank(accountId)) {
			// 找出账号
			AppAccountT account = appAccountUserService.find(accountId);
			request.setAttribute("id", accountId);
			
			request.setAttribute("account", account);
		}
		if (StringUtil.isNotBlank(accountId)) {
			// 找出用户
			AppUserT user = appAccountUserService.findAppUserByAccountId(accountId);
			request.setAttribute("user", user);
			// 加载该用户所关联的角色列表
			String ids_role = groupService.findRoleListByUserId2String(user.getAppUserId());
			request.setAttribute("ids_role", ids_role);
		}
		// 加载所有角色列表
		List<AppGroupT> roleList = groupService.findAllPermissionGrade(this.findCurrentUserPermissionGrade());
		request.setAttribute("roleList", roleList);
		return CommViewEnum.Default.toString();
	}
}
