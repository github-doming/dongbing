package com.ibm.follow.connector.admin.manage.user.role;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
/**
 * 用户角色信息修改
 *
 * @Author: Dongming
 * @Date: 2020-05-07 15:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role/edit", method = HttpConfig.Method.POST) public class UserRoleEditAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String expRoleId = dataMap.getOrDefault("expRoleId", "").toString();
		String roleName = dataMap.getOrDefault("roleName", "").toString();
		int roleLevel = NumberTool.getInteger(dataMap, "roleLevel", 0);
		String gameCodes = dataMap.getOrDefault("gameCodes", "").toString();
		String memberHandicapCodes = dataMap.getOrDefault("memberHandicapCodes", "").toString();
		String agentHandicapCodes = dataMap.getOrDefault("agentHandicapCodes", "").toString();
		int memberOnlineMax = NumberTool.getInteger(dataMap, "memberOnlineMax", 0);
		int agentOnlineMax = NumberTool.getInteger(dataMap, "agentOnlineMax", 0);
		int hmOnlineMax = NumberTool.getInteger(dataMap, "hmOnlineMax", 0);
		int haOnlineMax = NumberTool.getInteger(dataMap, "haOnlineMax", 0);
		String state = dataMap.getOrDefault("state", "").toString();
		if (StringTool.isEmpty(expRoleId, roleName, state)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		try {
			IbmExpRoleService expRoleService = new IbmExpRoleService();
			IbmExpRole expRole = expRoleService.find(expRoleId);
			if (expRole != null) {
				expRole.setRoleLevel(roleLevel);
				expRole.setGameCodes(gameCodes);
				expRole.setMemberHandicapCodes(memberHandicapCodes);
				expRole.setAgentHandicapCodes(agentHandicapCodes);
				expRole.setMemberOnlineMax(memberOnlineMax);
				expRole.setAgentOnlineMax(agentOnlineMax);

				expRole.setHmOnlineMax(hmOnlineMax);
				expRole.setHaOnlineMax(haOnlineMax);
				expRole.setState(state);
				expRole.setUpdateTime(new Date());
				expRole.setUpdateTimeLong(System.currentTimeMillis());
				expRole.setState(state);
				expRoleService.update(expRole);

				bean.success();
			} else {
				bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
			}
		} catch (Exception e) {
			log.error("用户角色信息修改错误"+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
}
