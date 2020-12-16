package c.x.platform.sys.sys_user.cx.action;
import java.util.ArrayList;
import java.util.List;

import all.gen.sys_user.t.entity.SysUserT;
import all.gen.sys_user.t.vo.SysUserTVo;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import c.x.platform.sys.sys_group.cx_role.service.SysGroupCxRoleService;
import c.x.platform.sys.sys_user.cx.service.SysUserCxService;
public class SysUserCxSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		System.out.println("==========aaaaaaaaaaaa");
		// 所有角色ids
		String[] group_ids = request.getParameterValues("name_checkbox_role");
		// 保存或更新
		String id = request.getParameter("sys_user.sysUserId");
		SysUserT userNew = (SysUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysUserTVo.class, SysUserT.class, request);
		SysUserCxService sysUserTCxService = new SysUserCxService();
		SysGroupCxRoleService sysGroupTCxRoleService = new SysGroupCxRoleService();
		if (StringUtil.isBlank(id)) {
			// 判断系统是否存在用户名
			if (sysUserTCxService.isUserByName(userNew.getSysUserName())) {
				List<String> msgStrList = new ArrayList<String>();
				msgStrList.add("信息");
				msgStrList.add("用户名已存在");
				request.setAttribute("msg", msgStrList);
				return "index";
			} else {
				String userId = sysUserTCxService.save(userNew);
				// 保存用户与角色列表
				sysGroupTCxRoleService.save_userId_groupRoleIdList(userId, group_ids);
			}
		} else {
			SysUserT userOld = sysUserTCxService.find(id);
			// 原来的数据权限等级
			userNew.setPermissionGrade(userOld.getPermissionGrade());
			sysUserTCxService.update(userNew);
			// 编辑用户与角色时，先删除用户与角色列表，然后 保存用户与角色列表
			// 删除用户与角色列表
			sysGroupTCxRoleService.delGroupUserByUserId(id);
			// 保存用户与角色列表
			sysGroupTCxRoleService.save_userId_groupRoleIdList(id, group_ids);
		}
		return "index";
	}
}
