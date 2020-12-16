package com.ibm.common.core;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.connector.service.user.AdminUser;

/**
 * 后台管理 系统管理员 MVC基类
 *
 * @Author: Dongming
 * @Date: 2020-03-30 17:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class CommSysAction extends CommAdminAction {

	@Override
	public AdminUser findAdminUser() throws Exception {
		super.findAdminUser();
		//获取失败
		if (!LogThreadLocal.isSuccess()) {
			return adminUser;
		}
		AuthorityService authorityService = new AuthorityService();

		/*
		 * 1.查询管理员的角色-数据权限等级
		 * 2.查询本角色的数据权限等级
		 * 3.进行等级比较，
		 * 	如果用户所处的等级大于等于 管理员数据权限，则比较通过
		 * 	如果权限小，则不通过
		 *
		 */
		int adminGrade = authorityService.findPermissionGrade4Admin();
		int userGrade = authorityService.findPermissionGrade(adminUser.getUserId());
		if (userGrade > adminGrade) {
			MvcActionTool.returnCode403();
		}
		return adminUser;
	}
}
