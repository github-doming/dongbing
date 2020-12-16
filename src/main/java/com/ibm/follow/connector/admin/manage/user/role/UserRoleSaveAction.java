package com.ibm.follow.connector.admin.manage.user.role;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
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
@ActionMapping(value = "/ibm/admin/manage/user/role/save", method = HttpConfig.Method.POST) public class UserRoleSaveAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String roleName = dataMap.getOrDefault("roleName", "").toString();
		String roleCode = dataMap.getOrDefault("roleCode", "").toString();
		int roleLevel = NumberTool.getInteger(dataMap, "roleLevel", 0);
		String gameCodes = dataMap.getOrDefault("gameCodes", "").toString();
		String memberHandicapCodes = dataMap.getOrDefault("memberHandicapCodes", "").toString();
		String agentHandicapCodes = dataMap.getOrDefault("agentHandicapCodes", "").toString();
		int memberOnlineMax = NumberTool.getInteger(dataMap, "memberOnlineMax", 0);
		int agentOnlineMax = NumberTool.getInteger(dataMap, "agentOnlineMax", 0);
		int hmOnlineMax = NumberTool.getInteger(dataMap, "hmOnlineMax", 0);
		int haOnlineMax = NumberTool.getInteger(dataMap, "haOnlineMax", 0);
		String state = dataMap.getOrDefault("state", "").toString();
		if (StringTool.isEmpty(roleName, roleCode, state)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			// TODO 新增类型受限
			IbmTypeEnum type = IbmTypeEnum.valueCustomerTypeOf(roleCode);
			if (type == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			IbmExpRoleService expRoleService = new IbmExpRoleService();
			IbmExpRole expRole = expRoleService.findByCode(type);
			if (expRole != null) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			expRole = new IbmExpRole();
			Date nowTime = new Date();
			expRole.setRoleName(roleName);
			expRole.setRoleCode(roleCode);
			expRole.setRoleLevel(roleLevel);
			expRole.setGameCodes(gameCodes);
			expRole.setMemberHandicapCodes(memberHandicapCodes);
			expRole.setAgentHandicapCodes(agentHandicapCodes);
			expRole.setMemberOnlineMax(memberOnlineMax);
			expRole.setAgentOnlineMax(agentOnlineMax);
			expRole.setHmOnlineMax(hmOnlineMax);
			expRole.setHaOnlineMax(haOnlineMax);
			expRole.setCreateTime(nowTime);
			expRole.setCreateTimeLong(System.currentTimeMillis());
			expRole.setUpdateTime(nowTime);
			expRole.setUpdateTimeLong(System.currentTimeMillis());
			expRole.setState(IbmStateEnum.OPEN.name());
			expRoleService.save(expRole);
			bean.success();
		} catch (Exception e) {
			log.error("用户角色信息保存错误"+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

}
