package all.sys_admin.sys.account_user.action;
import java.util.List;

import org.eclipse.jetty.util.StringUtil;

import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_group.t.entity.SysGroupT;
import all.gen.sys_user.t.entity.SysUserT;
import all.sys_admin.sys.account_user.service.SysAccountUserService;
import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountUserFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String accountId = (String) request.getParameter("id");
		// 声明
		SysGroupService groupService = new SysGroupService();
		SysAccountUserService appAccountUserService = new SysAccountUserService();
		if (StringUtil.isNotBlank(accountId)) {
			// 找出账号
			SysAccountT account = appAccountUserService.find(accountId);
			request.setAttribute("id", accountId);
			
			request.setAttribute("account", account);
		}
		if (StringUtil.isNotBlank(accountId)) {
			// 找出用户
			SysUserT user = appAccountUserService.findSysUserByAccountId(accountId);
			request.setAttribute("user", user);
			// 加载该用户所关联的角色列表
			String ids_role = groupService.findRoleListByUserId2String(user.getSysUserId());
			request.setAttribute("ids_role", ids_role);
		}
		// 加载所有角色列表
		List<SysGroupT> roleList = groupService.findAllPermissionGrade(this.findCurrentUserPermissionGrade());
		request.setAttribute("roleList", roleList);
		return CommViewEnum.Default.toString();
	}
}
