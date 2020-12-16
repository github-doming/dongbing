package com.ibs.plan.connector.admin.manage.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 新增新的角色
 *
 * @Author: Dongming
 * @Date: 2020-04-02 15:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/role/add")
public class RoleSaveAction extends CommAdminAction {

	private String roleName, roleCode, state;
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
			Map<String, Object> userRole = authorityService.findUserRole(appUserId);

			if (roleGrade <= NumberTool.getInteger(userRole.get("PERMISSION_GRADE_"))) {
				bean.putEnum(CodeEnum.IBS_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			if (authorityService.checkRole(roleCode)) {
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;

			}
			String roleId = authorityService.saveRole(roleName, roleCode, roleGrade, state, appUserId);

			return bean.success(roleId);
		} catch (Exception e) {
			log.error("新增新的角色错误");
			throw e;
		}
	}

	private boolean valiParameters() {
		roleName = StringTool.getString(dataMap, "roleName", null);
		roleCode = StringTool.getString(dataMap, "roleCode", null);
		state = StringTool.getString(dataMap, "state", null);
		roleGrade = NumberTool.getInteger(dataMap.get("roleGrade"), 0);
		return StringTool.isEmpty(roleName, roleCode, state);
	}
}
