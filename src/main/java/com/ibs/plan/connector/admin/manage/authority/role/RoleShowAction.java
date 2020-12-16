package com.ibs.plan.connector.admin.manage.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色详情信息展示
 *
 * @Author: Dongming
 * @Date: 2020-04-02 14:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/show")
public class RoleShowAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
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
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}


			Map<String, Object> data = new HashMap<>(2);
			Map<String, Object> userRole = authorityService.findUserRole(appUserId);
			data.put("role", role);
			data.put("minGrade", NumberTool.getInteger(userRole.get("PERMISSION_GRADE_")));
			return bean.success(data);
		} catch (Exception e) {
			log.error("菜单项展示错误", e);
			return bean.error(e.getMessage());
		}
	}
}
