package com.ibm.follow.connector.admin.manage.user.role;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_role.entity.IbmExpRole;
import com.ibm.follow.servlet.cloud.ibm_exp_role.service.IbmExpRoleService;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.*;
/**
 * 用户角色信息展示
 *
 * @Author: Dongming
 * @Date: 2020-05-07 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role/form") public class UserRoleFormAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String expRoleId = dataMap.getOrDefault("expRoleId", "").toString();
		try {
			Map<String, Object> data = new HashMap<>(3);

			if (StringTool.notEmpty(expRoleId)) {
				Map<String, Object> expRoleInfo = new HashMap<>(4);
				IbmExpRoleService expRoleService = new IbmExpRoleService();
				IbmExpRole expRole = expRoleService.find(expRoleId);

				expRoleInfo.put("ROLE_NAME_", expRole.getRoleName());
				expRoleInfo.put("ROLE_CODE_", expRole.getRoleCode());
				expRoleInfo.put("ROLE_LEVEL_", expRole.getRoleLevel());

				String[] codes = expRole.getGameCodes().split(",");
				expRoleInfo.put("GAME_CODES_",  Arrays.asList(codes));

				codes = expRole.getMemberHandicapCodes().split(",");
				expRoleInfo.put("MEMBER_HANDICAP_CODES_",  Arrays.asList(codes));

				codes = expRole.getAgentHandicapCodes().split(",");
				expRoleInfo.put("AGENT_HANDICAP_CODES_",  Arrays.asList(codes));

				expRoleInfo.put("MEMBER_ONLINE_MAX_", expRole.getMemberOnlineMax());
				expRoleInfo.put("AGENT_ONLINE_MAX_", expRole.getAgentOnlineMax());
				expRoleInfo.put("HM_ONLINE_MAX_", expRole.getHmOnlineMax());
				expRoleInfo.put("HA_ONLINE_MAX_", expRole.getHaOnlineMax());
				expRoleInfo.put("expRoleId", expRole.getIbmExpRoleId());
				expRoleInfo.put("STATE_", expRole.getState());

				data.put("expRoleInfo", expRoleInfo);
			}
			IbmHandicapService handicapService = new IbmHandicapService();
			List<Map<String, Object>> memberHandicaps = handicapService.listHandicap(IbmTypeEnum.MEMBER);
			List<Map<String, Object>> agentHandicaps = handicapService.listHandicap(IbmTypeEnum.AGENT);
			List<Map<String, Object>> gameInfos = new IbmGameService().findGameInfo();

			data.put("memberHandicaps", memberHandicaps);
			data.put("agentHandicaps", agentHandicaps);
			data.put("gameInfos", gameInfos);

			bean.success(data);
		} catch (Exception e) {
			log.error("盘口封盘时间列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
