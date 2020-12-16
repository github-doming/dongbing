package com.ibs.plan.connector.admin.manage.authority.user;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import com.ibs.plan.module.cloud.ibsp_user_token.service.IbspUserTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 操作人员角色信息修改
 *
 * @Author: Dongming
 * @Date: 2020-04-06 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/user/roleEdit")
public class UserRoleEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String userId = StringTool.getString(dataMap, "userId", "");
		String roleId = StringTool.getString(dataMap, "roleId", "");
		if (StringTool.isEmpty(userId, roleId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> user = authorityService.findUser(userId);
			if (ContainerTool.isEmpty(user)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> role = authorityService.findRole(roleId);
			if (ContainerTool.isEmpty(role)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			authorityService.updateUserRole(userId, roleId, appUserId);

			new IbspUserService().updateUserRole(userId,role);

			IbspUserTokenService tokenService=new IbspUserTokenService();
			Map<String, Object> tokenInfo=tokenService.findUserLastInfo(userId);
			if(ContainerTool.notEmpty(tokenInfo)){
				tokenService.logout(tokenInfo.get("VALUE_").toString());
			}


			return bean.success();
		} catch (Exception e) {
			log.error("操作人员角色信息修改 错误");
			throw e;
		}
	}
}
