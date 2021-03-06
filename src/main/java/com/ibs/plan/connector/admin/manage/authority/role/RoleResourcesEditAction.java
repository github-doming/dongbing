package com.ibs.plan.connector.admin.manage.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 修改保存角色资源
 *
 * @Author: Dongming
 * @Date: 2020-04-02 17:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/resourcesEdit")
public class RoleResourcesEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String roleId = StringTool.getString(dataMap, "roleId", null);
		String menuIdsStr = StringTool.getString(dataMap, "menuIds", null);
		if (StringTool.isEmpty(roleId, menuIdsStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		List<String> menuIds = Arrays.asList(menuIdsStr.split(","));
		try {

			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			List<String> userMenuIds = authorityService.listMenuIds4User(appUserId);
			menuIds.removeIf(menuId -> !userMenuIds.contains(menuId));
			authorityService.updateRoleResources(roleId, menuIds, appUserId);
			return bean.success();
		} catch (Exception e) {
			log.error("角色资源 修改保存错误");
			throw e;
		}
	}
}
