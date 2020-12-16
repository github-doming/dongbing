package com.ibs.plan.connector.admin.manage.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_role.service.IbspExpRoleService;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户角色信息展示
 *
 * @Author: Dongming
 * @Date: 2020-05-07 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/role/form",method = HttpConfig.Method.GET)
public class UserRoleFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String expRoleId = StringTool.getString(dataMap,"expRoleId", "");
		try {
			Map<String, Object> data = new HashMap<>(3);
			IbspHandicapService handicapService = new IbspHandicapService();
			List<Map<String, Object>> memberHandicaps = handicapService.listHandicap();

			List<Map<String, Object>> gameInfos = new IbspGameService().findGameInfo();

			if (StringTool.notEmpty(expRoleId)) {
				Map<String, Object> expRoleInfo = new HashMap<>(8);
				IbspExpRoleService expRoleService = new IbspExpRoleService();
				IbspExpRole expRole = expRoleService.find(expRoleId);
				expRoleInfo.put("expRoleId", expRoleId);
				expRoleInfo.put("ROLE_NAME_", expRole.getRoleName());
				expRoleInfo.put("ROLE_CODE_", expRole.getRoleCode());
				expRoleInfo.put("ROLE_LEVEL_", expRole.getRoleLevel());

				List<String> codeList = Arrays.asList(expRole.getGameCodes().split(","));
				for (Map<String, Object> gameInfo : gameInfos) {
					if (codeList.contains(gameInfo.get("GAME_CODE_").toString())) {
						gameInfo.put("has", true);
					} else {
						gameInfo.put("has", false);
					}
				}

				codeList = Arrays.asList(expRole.getHandicapCodes().split(","));
				for (Map<String, Object> handicap : memberHandicaps) {
					if (codeList.contains(handicap.get("HANDICAP_CODE_").toString())) {
						handicap.put("has", true);
					} else {
						handicap.put("has", false);
					}
				}
				codeList = Arrays.asList(expRole.getPlanCodes().split(","));
				IbspPlanService planService = new IbspPlanService();
				List<Map<String, Object>> planInfos = planService.findPlanList();
				for (Map<String, Object> planInfo : planInfos) {
					if (codeList.contains(planInfo.get("PLAN_CODE_").toString())) {
						planInfo.put("has", true);
					} else {
						planInfo.put("has", false);
					}
				}
				expRoleInfo.put("ONLINE_MAX_", expRole.getOnlineMax());
				expRoleInfo.put("HM_ONLINE_MAX_", expRole.getHmOnlineMax());
				expRoleInfo.put("STATE_", expRole.getState());

				data.put("expRoleInfo", expRoleInfo);
			}

			data.put("memberHandicaps", memberHandicaps);
			data.put("gameInfos", gameInfos);


			bean.success(data);
		} catch (Exception e) {
			log.error("用户角色信息展示错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
