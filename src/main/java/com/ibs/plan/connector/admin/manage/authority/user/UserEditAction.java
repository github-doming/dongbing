package com.ibs.plan.connector.admin.manage.authority.user;

import all.app.common.service.AppAccountService;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 操作员修改
 *
 * @Author: Dongming
 * @Date: 2020-04-06 16:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/user/edit")
public class UserEditAction extends CommAdminAction {

	private String userName, userId, userPassWord, roleId, state;

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

			Map<String, Object> user = authorityService.findUser(userId);
			if (ContainerTool.isEmpty(user)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.notEmpty(roleId)) {
				Map<String, Object> role = authorityService.findRole(roleId, appUserId);
				if (ContainerTool.isEmpty(role)) {
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;
				}
				authorityService.updateUserRole(userId, roleId, appUserId);
			}
			if (StringTool.notEmpty(userPassWord)) {
				String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
				if (!userPassWord.matches(regExpPwd)) {
					bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
					bean.putSysEnum(ReturnCodeEnum.code409);
					return bean;
				}
				new AppAccountService().updatePassword(userId, userPassWord);
			}

			authorityService.updateUser(userName, state, userId, appUserId);
			// TODO 修改云端用户属性
			return bean.success();
		} catch (Exception e) {
			log.error("操作员修改错误");
			throw e;
		}
	}

	private boolean valiParameters() {

		userId = StringTool.getString(dataMap, "userId", null);
		userName = StringTool.getString(dataMap, "userName", null);
		userPassWord = StringTool.getString(dataMap, "userPassWord", null);
		roleId = StringTool.getString(dataMap, "roleId", null);
		state = StringTool.getString(dataMap, "state", null);
		return StringTool.isEmpty(userId, state);
	}
}
