package com.ibm.follow.connector.admin.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 角色删除
 *
 * @Author: Dongming
 * @Date: 2020-04-03 14:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/role/del") public class RoleDelAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String roleId = StringTool.getString(dataMap, "roleId", null);
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> userRole = authorityService.findUserRole(adminUser.getUserId());
			if (NumberTool.getInteger(role.get("PERMISSION_GRADE_")) <= NumberTool
					.getInteger(userRole.get("PERMISSION_GRADE_"))) {
				bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			authorityService.deleteRole(roleId,adminUser.getUserId());
			bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}
