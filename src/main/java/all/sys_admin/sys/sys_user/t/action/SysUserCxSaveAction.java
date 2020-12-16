package all.sys_admin.sys.sys_user.t.action;
import java.util.ArrayList;
import java.util.List;

import all.gen.sys_user.t.entity.SysUserT;
import all.gen.sys_user.t.vo.SysUserTVo;
import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysUserCxSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		// 所有角色ids
		String[] group_ids = request.getParameterValues("name_checkbox_role");
		// 保存或更新
		String id = request.getParameter("sys_user.SYS_USER_ID_");
		SysUserT userNew = (SysUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(
				SysUserTVo.class, SysUserT.class, request);
		SysUserService sysUserService = new SysUserService();
		SysGroupService sysGroupService = new SysGroupService();
		if (StringUtil.isBlank(id)) {
			// 判断系统是否存在用户名
			if (sysUserService.isUserByName(userNew.getSysUserName())) {
				List<String> msgStrList = new ArrayList<String>();
				msgStrList.add("信息");
				msgStrList.add("用户名已存在");
				request.setAttribute("msg", msgStrList);
				return "index";
			} else {
				String userId = sysUserService.save(userNew);
				// 保存用户与角色列表
				sysGroupService.save_userId_roleIdList(userId, group_ids);
			}
		} else {
			SysUserT db_user = sysUserService.find(id);
			// 原来的数据权限等级
			userNew.setPermissionGrade(db_user.getPermissionGrade());
			sysUserService.update(userNew);
			// 编辑用户与角色时，先删除用户与角色列表，然后 保存用户与角色列表
			// 删除用户与角色列表
			sysGroupService.delGroupUserByUserId(id);
			// 保存用户与角色列表
			sysGroupService.save_userId_roleIdList(id, group_ids);
		}
		return "index";
	}
}
