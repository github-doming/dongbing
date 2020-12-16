package com.ibs.plan.connector.pc.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 用户方案库
 * @Author: null
 * @Date: 2020-06-06 16:19
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/plan/library", method = HttpConfig.Method.GET)
public class PlanUserLibraryAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		if (StringTool.isEmpty(gameCode)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			//获取用户可用方案
			IbspExpUserService expUserService=new IbspExpUserService();
			String availablePlan=expUserService.findAvailablePlan(appUserId);
			if(StringTool.isEmpty(availablePlan)){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Set<Object> availablePlanCodes = new HashSet<>(20);
			List<String> availablePlans=Arrays.asList(availablePlan.split(","));

			//获取已有的游戏方案
			List<String> existPlans=new IbspPlanUserService().findExistPlan(appUserId,gameId);

			//可用的所有方案-用户已有的方案-可新增的方案
			availablePlanCodes.addAll(ContainerTool.getDifferent(availablePlans,existPlans));

			List<Map<String,Object>> planGameInfos=new ArrayList<>();
			if(ContainerTool.notEmpty(availablePlanCodes)){
				planGameInfos=new IbspPlanGameService().findPlanGameInfo(availablePlanCodes,gameId);
			}

			bean.setData(planGameInfos);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取用户方案库失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
