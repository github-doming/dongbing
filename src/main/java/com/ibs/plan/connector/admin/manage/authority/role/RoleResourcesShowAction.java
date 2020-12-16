package com.ibs.plan.connector.admin.manage.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示角色资源
 *
 * @Author: Dongming
 * @Date: 2020-04-02 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/resourcesShow")
public class RoleResourcesShowAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String roleId = StringTool.getString(dataMap, "roleId", null);
		try {

			//资源验证
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//用户资源
			List<Map<String, Object>> menu = authorityService.listUserMenus(appUserId);

			List<String> roleMenuIds = authorityService.listMenuIds4Role(roleId);

			Map<String, Object> data = new HashMap<>(3);
			data.put("roleId", roleId);
			data.put("roleName", role.get("APP_GROUP_NAME_"));
			data.put("menu", menu);
			data.put("roleMenuIds", roleMenuIds);
			return bean.success(data);
		} catch (Exception e) {
			log.error("展示角色资源错误", e);
			return bean.error(e.getMessage());
		}
	}
}
