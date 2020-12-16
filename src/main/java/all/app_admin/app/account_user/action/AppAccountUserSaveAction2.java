package all.app_admin.app.account_user.action;
import all.app_admin.app.account_user.service.AppAccountUserService;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import all.app_admin.app.app_user.t.service.AppUserTService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.vo.AppAccountTVo;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.vo.AppUserTVo;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppAccountUserSaveAction2 extends BaseAction {
	@Override
	public String execute() throws Exception {
		String tenantCode = this.findCurrentUserTenantCode();
		AppAccountT account = null;
		String accountId = request.getParameter("app_account.appAccountId");
		account = (AppAccountT) RequestThreadLocal.doRequest2Entity(AppAccountTVo.class, AppAccountT.class, request);
		account.setTenantCode(tenantCode);
		String appUserId = request.getParameter("app_user.appUserId");
		AppUserT userNew = (AppUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(AppUserTVo.class,
				AppUserT.class, request);
		userNew.setTenantCode(tenantCode);
		// 保存用户
		// 所有角色ids
		String[] group_ids = request.getParameterValues("name_checkbox_role");
		// 保存或更新
		AppAccountUserService accountUserService = new AppAccountUserService();
		AppUserTService userService = new AppUserTService();
		AppGroupTService groupService = new AppGroupTService();
		AppAccountT accountDB = accountUserService.find(accountId);
		userNew.setAppUserName(accountDB.getAccountName());
		if (StringUtil.isBlank(appUserId)) {
			appUserId = userService.save(userNew);
			// 保存用户与角色列表
			groupService.save_userId_roleIdList(appUserId, group_ids);
		} else {
			AppUserT db_user = userService.find(appUserId);
			// 原来的数据权限等级
			userNew.setPermissionGrade(db_user.getPermissionGrade());
			userNew.setAppUserName(account.getAccountName());
			userService.update(userNew);
			// 编辑用户与角色时，先删除用户与角色列表，然后 保存用户与角色列表
			// 删除用户与角色列表
			groupService.delGroupUserByUserId(appUserId);
			// 保存用户与角色列表
			groupService.save_userId_roleIdList(appUserId, group_ids);
		}
		// 保存账号
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			String password = CommASEUtil.encode(commLocalASE_key, account.getPassword().trim()).trim();
			account.setPassword(password);
		}
		account.setAppUserId(appUserId);
		account.setAccountName(accountDB.getAccountName());
		if (StringUtil.isBlank(accountId)) {
			accountId = accountUserService.save(account);
		} else {
			accountUserService.update(account);
		}
		return CommViewEnum.Default.toString();
	}
}
