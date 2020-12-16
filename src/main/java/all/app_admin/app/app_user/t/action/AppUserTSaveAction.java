package all.app_admin.app.app_user.t.action;
import java.util.ArrayList;
import java.util.List;

import all.app_admin.app.app_group.t.service.AppGroupTService;
import all.app_admin.app.app_user.t.service.AppUserTService;
import all.gen.app_user.t.entity.AppUserT;
import all.gen.app_user.t.vo.AppUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String tenantCode = this.findCurrentUserTenantCode();
		// 所有角色ids
		String[] group_ids = request.getParameterValues("name_checkbox_role");
		// 保存或更新
		String appUserId = request.getParameter("app_user.appUserId");
		AppUserT userNew = (AppUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(AppUserTVo.class,
				AppUserT.class, request);
		AppUserTService userService = new AppUserTService();
		AppGroupTService groupService = new AppGroupTService();
		if (StringUtil.isBlank(appUserId)) {
			// 判断系统是否存在用户名
			if (userService.isUserByName(userNew.getAppUserName())) {
				List<String> msgStrList = new ArrayList<String>();
				msgStrList.add("信息");
				msgStrList.add("用户名已存在");
				request.setAttribute("msg", msgStrList);
				return CommViewEnum.Default.toString();
			} else {
				userNew.setTenantCode(tenantCode);
				String userId = userService.save(userNew);
				// 保存用户与角色列表
				groupService.save_userId_roleIdList(userId, group_ids);
			}
		} else {
			AppUserT appUserDb = userService.find(appUserId);
			// 原来的数据权限等级
			userNew.setPermissionGrade(appUserDb.getPermissionGrade());
			userNew.setTenantCode(tenantCode);
			userService.update(userNew);
			// 编辑用户与角色时，先删除用户与角色列表，然后 保存用户与角色列表
			// 删除用户与角色列表
			groupService.delGroupUserByUserId(appUserId);
			// 保存用户与角色列表
			groupService.save_userId_roleIdList(appUserId, group_ids);
		}
		return CommViewEnum.Default.toString();
	}
}
