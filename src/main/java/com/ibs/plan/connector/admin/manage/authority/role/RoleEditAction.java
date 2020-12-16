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

import java.util.Map;

/**
 * 角色修改
 *
 * @Author: Dongming
 * @Date: 2020-04-03 13:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/edit")
public class RoleEditAction extends CommAdminAction {

	private String roleId, roleName, roleCode, state;
	private Integer roleGrade;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}


			authorityService.updateRole(roleId, roleName, roleCode, roleGrade, state, appUserId);
			return bean.success();
		} catch (Exception e) {
			log.error("新增新的角色错误");
			throw e;
		}
	}

	private boolean valiParameters() {
		roleId = StringTool.getString(dataMap, "roleId", null);
		roleName = StringTool.getString(dataMap, "roleName", null);
		roleCode = StringTool.getString(dataMap, "roleCode", null);
		state = StringTool.getString(dataMap, "state", null);
		roleGrade = NumberTool.getInteger(dataMap.get("roleGrade"), 0);
		return StringTool.isEmpty(roleId, roleName, roleCode, state);
	}
}
