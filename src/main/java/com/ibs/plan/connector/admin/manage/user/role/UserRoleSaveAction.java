package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * 用户角色信息保存
 *
 * @Author: Dongming
 * @Date: 2020-05-07 15:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/save", method = HttpConfig.Method.POST)
public class UserRoleSaveAction
		extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String roleName = StringTool.getString(dataMap,"roleName", "");
		String roleCode = StringTool.getString(dataMap,"roleCode", "");
		int roleLevel = NumberTool.getInteger(dataMap, "roleLevel", 0);
		String gameCodes = StringTool.getString(dataMap,"gameCodes", "");
		String handicapCodes = StringTool.getString(dataMap,"handicapCodes", "");
		String planCodes = StringTool.getString(dataMap,"planCodes", "");
		int onlineMax = NumberTool.getInteger(dataMap, "onlineMax", 0);
		int hmOnlineMax = NumberTool.getInteger(dataMap, "hmOnlineMax", 0);


		if (StringTool.isEmpty(roleName, roleCode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbspExpRoleService expRoleService = new IbspExpRoleService();
			IbspExpRole expRole = expRoleService.findByCode(roleCode);
			if (expRole != null) {
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			expRole = new IbspExpRole();
			Date nowTime = new Date();
			expRole.setRoleName(roleName);
			expRole.setRoleCode(roleCode);
			expRole.setRoleLevel(roleLevel);
			expRole.setGameCodes(gameCodes);
			expRole.setHandicapCodes(handicapCodes);
			expRole.setPlanCodes(planCodes);
			expRole.setOnlineMax(onlineMax);
			expRole.setHmOnlineMax(hmOnlineMax);
			expRole.setCreateTime(nowTime);
			expRole.setCreateTimeLong(System.currentTimeMillis());
			expRole.setUpdateTime(nowTime);
			expRole.setUpdateTimeLong(System.currentTimeMillis());
			expRole.setState(IbsStateEnum.OPEN.name());
			expRoleService.save(expRole);
			bean.success();
		} catch (Exception e) {
			log.error("用户角色信息保存错误");
			throw e;
		}
		return bean;
	}
}
