package all.sys_admin.sys.account_user.action;
import java.util.ArrayList;
import java.util.List;

import all.gen.sys_account.t.entity.SysAccountT;
import all.gen.sys_account.t.vo.SysAccountTVo;
import all.gen.sys_user.t.entity.SysUserT;
import all.gen.sys_user.t.vo.SysUserTVo;
import all.sys_admin.sys.account_user.service.SysAccountUserService;
import all.sys_admin.sys.sys_group.cx_role.service.SysGroupService;
import all.sys_admin.sys.sys_user.t.service.SysUserService;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
public class SysAccountUserSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysAccountT account = null;
		String accountId = request.getParameter("sys_account.sysAccountId");
		account = (SysAccountT) RequestThreadLocal.doRequest2Entity(SysAccountTVo.class, SysAccountT.class,
				request);

		String userId = request.getParameter("sys_account.sysUserId");
		SysUserT userNew = (SysUserT) RequestThreadLocal.findThreadLocal().get().doRequest2Entity(SysUserTVo.class,
				SysUserT.class, request);

		// 保存用户
		// 所有角色ids
		String[] group_ids = request.getParameterValues("name_checkbox_role");
		// 保存或更新
		SysUserService userService = new SysUserService();
		SysGroupService groupService = new SysGroupService();
		if (StringUtil.isBlank(userId)) {
			// 判断系统是否存在用户名
			if (userService.isUserByName(account.getSysAccountName())) {
				List<String> msgStrList = new ArrayList<String>();
				msgStrList.add("信息");
				msgStrList.add("用户名已存在");
				//System.out.println("用户名已存在");
				log.warn("用户名已存在");
				request.setAttribute("msg", msgStrList);
				return CommViewEnum.Default.toString();
			} else {
				userNew.setSysUserName(account.getSysAccountName());
				userId = userService.save(userNew);
				// 保存用户与角色列表
				groupService.save_userId_roleIdList(userId, group_ids);
			}
		} else {
			SysUserT db_user = userService.find(userId);
			// 原来的数据权限等级
			userNew.setPermissionGrade(db_user.getPermissionGrade());
			userNew.setSysUserName(account.getSysAccountName());
			userService.update(userNew);
			// 编辑用户与角色时，先删除用户与角色列表，然后 保存用户与角色列表
			// 删除用户与角色列表
			groupService.delGroupUserByUserId(userId);
			// 保存用户与角色列表
			groupService.save_userId_roleIdList(userId, group_ids);
		}
		// 保存账号
		SysAccountUserService service = new SysAccountUserService();
		String commLocalASE = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "");
		String commLocalASE_key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"),
				"");
		if ("true".equals(commLocalASE)) {
			String password = CommASEUtil.encode(commLocalASE_key, account.getPassword().trim()).trim();
			account.setPassword(password);
		}
		account.setSysUserId(userId);
		if (StringUtil.isBlank(accountId)) {
			accountId = service.save(account);
		} else {
			service.update(account);
		}
		return CommViewEnum.Default.toString();
	}
}
