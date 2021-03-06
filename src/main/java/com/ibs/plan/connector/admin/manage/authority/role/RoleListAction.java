package com.ibs.plan.connector.admin.manage.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * 角色管理 - 列表
 *
 * @Author: Dongming
 * @Date: 2020-04-02 10:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/list")
public class RoleListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String roleName = StringTool.getString(dataMap, "roleName", null);
		String roleCode = StringTool.getString(dataMap, "roleCode", null);
		try {
			// 列出用户可视的角色列表
			AuthorityService authorityService = new AuthorityService();
			List<Map<String, Object>> roleInfos = authorityService.listUserAllRole(appUserId, roleName, roleCode);
			return bean.success(roleInfos);
		} catch (Exception e) {
			log.error("用户表单页面错误", e);
			return bean.error(e.getMessage());
		}
	}
}
