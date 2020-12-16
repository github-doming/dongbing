package com.ibm.follow.connector.admin.authority.role;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
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
@ActionMapping(value = "/ibm/admin/authority/role/add")
public class RoleSaveAction extends CommAdminAction {

	String roleName, roleCode, state;
	Integer roleGrade;


	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		if (valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String,Object> userRole = authorityService.findUserRole(adminUser.getUserId());

			if (roleGrade <=  NumberTool.getInteger(userRole.get("PERMISSION_GRADE_"))){
				bean.putEnum(IbmCodeEnum.IBM_403_PERMISSION);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			if (authorityService.checkRole(roleCode)){
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;

			}
			String roleId = authorityService.saveRole(roleName,roleCode,roleGrade,state,adminUser.getUserId());

			return bean.success(roleId);
		} catch (Exception e) {
			log.error("新增新的角色错误");
			bean.error(e.getMessage());
		}
		return bean;
	}

	private boolean valiParameters() {
		roleName = StringTool.getString(dataMap, "roleName", null);
		roleCode = StringTool.getString(dataMap, "roleCode", null);
		state = StringTool.getString(dataMap, "state", null);
		roleGrade = NumberTool.getInteger(dataMap.get("roleGrade"), 0);
		return StringTool.isEmpty(roleName, roleCode, state);
	}
}
