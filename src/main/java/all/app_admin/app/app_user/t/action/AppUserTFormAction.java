package all.app_admin.app.app_user.t.action;

import java.util.List;

import all.gen.app_group.t.entity.AppGroupT;
import all.app_admin.app.app_group.t.service.AppGroupTService;
import all.gen.app_user.t.entity.AppUserT;
import all.app_admin.app.app_user.t.service.AppUserTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class AppUserTFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// 声明
		AppGroupTService groupService = new AppGroupTService();

		String id = (String) request.getParameter("id");
		if (id != null) {
			AppUserTService service = new AppUserTService();
			AppUserT entity = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", entity);

			// 加载该用户所关联的角色列表
			String ids_role = groupService.findRoleListByUserId2String(id);
			request.setAttribute("ids_role", ids_role);

		}
		// 加载所有角色列表
		List<AppGroupT> roleList = groupService
				.findAllPermissionGrade(this.findCurrentUserPermissionGrade());
		request.setAttribute("roleList", roleList);

		return CommViewEnum.Default.toString();
	}
}
