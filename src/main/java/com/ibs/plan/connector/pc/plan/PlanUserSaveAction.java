package com.ibs.plan.connector.pc.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.entity.IbspPlanHm;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_item.entity.IbspPlanItem;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.entity.IbspPlanUser;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.entity.IbspProfitPlan;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.entity.IbspProfitPlanVr;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.service.IbspProfitPlanVrService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 添加方案
 * @Author: null
 * @Date: 2020-06-06 16:15
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/plan/save", method = HttpConfig.Method.POST)
public class PlanUserSaveAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String planCode = dataMap.getOrDefault("PLAN_CODE_", "").toString();

		String sn = dataMap.getOrDefault("SN_", "").toString();

		if (StringTool.isEmpty(gameCode, planCode,sn)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			//获取用户可用方案
			IbspExpUserService expUserService = new IbspExpUserService();
			String availablePlan = expUserService.findAvailablePlan(appUserId);
			if (StringTool.isEmpty(availablePlan)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			List<String> availablePlans = Arrays.asList(availablePlan.split(","));
			if (!availablePlans.contains(planCode)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> planGame = new IbspPlanGameService().findPlanGame(planCode, gameId);
			if (ContainerTool.isEmpty(planGame)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//获取方案初始化信息
			IbspPlanItemService planItemService = new IbspPlanItemService();
			IbspPlanItem planItemInit = planItemService.findPlanGameInit(planCode);
			if (planItemInit == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//添加方案详情信息
			String planItemId = planItemService.savePlanItem(appUserId, planItemInit, planGame, gameId);
			//添加方案用户信息
			savePlanUser(planGame, planItemInit, planItemId, gameId, Integer.parseInt(sn));

			//添加方案会员信息，会员方案盈亏信息
			savePlanHm(planCode, gameId, planItemId, planItemInit);


			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "添加方案失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private void savePlanHm(String planCode, String gameId, String planItemId, IbspPlanItem planItemInit) throws Exception {
		Date nowTime = new Date();

		List<Map<String, Object>> existInfos = new IbspClientHmService().findMemberInfos(appUserId);
		if (ContainerTool.notEmpty(existInfos)) {
			String planId = PlanUtil.id(planCode);
			//添加已登录会员方案信息
			IbspPlanHmService planHmService = new IbspPlanHmService();
			IbspPlanHm planHm = new IbspPlanHm();
			planHm.setAppUserId(appUserId);
			planHm.setPlanId(planId);
			planHm.setGameId(gameId);
			planHm.setPlanItemTableId(planItemId);
			planHm.setPlanItemTableName(planItemInit.getPlanItemTableName());
			planHm.setCreateTime(nowTime);
			planHm.setCreateTimeLong(nowTime.getTime());
			planHm.setUpdateTime(nowTime);
			planHm.setUpdateTimeLong(nowTime.getTime());
			planHm.setState(IbsStateEnum.OPEN.name());

			IbspProfitPlanService profitPlanService = new IbspProfitPlanService();
			IbspProfitPlan profitPlan = new IbspProfitPlan();
			profitPlan.setAppUserId(appUserId);
			profitPlan.setGameId(gameId);
			profitPlan.setPlanId(planId);
			profitPlan.setBetFundsT(0);
			profitPlan.setBetFundsT(0);
			profitPlan.setBetLen(0);
			profitPlan.setProfitLimitMaxT(planItemInit.getProfitLimitMax());
			profitPlan.setLossLimitMinT(planItemInit.getLossLimitMin());
			profitPlan.setCreateTime(nowTime);
			profitPlan.setCreateTimeLong(nowTime.getTime());
			profitPlan.setUpdateTime(nowTime);
			profitPlan.setUpdateTimeLong(nowTime.getTime());
			profitPlan.setState(IbsStateEnum.OPEN.name());

			IbspProfitPlanVrService profitPlanVrService = new IbspProfitPlanVrService();
			IbspProfitPlanVr profitPlanVr = new IbspProfitPlanVr();
			profitPlanVr.setAppUserId(appUserId);
			profitPlanVr.setGameId(gameId);
			profitPlanVr.setPlanId(planId);
			profitPlanVr.setBetFundsT(0);
			profitPlanVr.setBetFundsT(0);
			profitPlanVr.setBetLen(0);
			profitPlanVr.setProfitLimitMaxT(planItemInit.getProfitLimitMax());
			profitPlanVr.setLossLimitMinT(planItemInit.getLossLimitMin());
			profitPlanVr.setCreateTime(nowTime);
			profitPlanVr.setCreateTimeLong(nowTime.getTime());
			profitPlanVr.setUpdateTime(nowTime);
			profitPlanVr.setUpdateTimeLong(nowTime.getTime());
			profitPlanVr.setState(IbsStateEnum.OPEN.name());
			for (Map<String, Object> map : existInfos) {
				String handicapMemberId = map.get("HANDICAP_MEMBER_ID_").toString();
				planHm.setHandicapMemberId(handicapMemberId);
				String planHmId = planHmService.save(planHm);

				profitPlan.setHandicapMemberId(handicapMemberId);
				profitPlan.setPlanHmId(planHmId);
				profitPlanService.save(profitPlan);

				profitPlanVr.setHandicapMemberId(handicapMemberId);
				profitPlanVr.setPlanHmId(planHmId);
				profitPlanVrService.save(profitPlanVr);
			}
		}
	}

	private void savePlanUser(Map<String, Object> planGame, IbspPlanItem planItemInit, String planItemId, String gameId, int sn) throws Exception {
		IbspPlanUser planUser = new IbspPlanUser();
		planUser.setAppUserId(appUserId);
		planUser.setPlanId(planGame.get("PLAN_ID_"));
		planUser.setPlanName(planGame.get("PLAN_NAME_"));
		planUser.setGameId(gameId);
		planUser.setPlanItemTableName(planItemInit.getPlanItemTableName());
		planUser.setPlanItemTableId(planItemId);
		planUser.setPlanCode(planItemInit.getPlanCode());
		if (StringTool.notEmpty(planItemInit.getBetMode())) {
			planUser.setBetMode(planItemInit.getBetMode());
		}
		if (StringTool.notEmpty(planItemInit.getMonitorPeriod())) {
			planUser.setMonitorPeriod(planItemInit.getMonitorPeriod());
		}
		planUser.setPlanState(IbsStateEnum.CLOSE.name());
		planUser.setSn(sn);
		planUser.setCreateTime(new Date());
		planUser.setCreateTimeLong(System.currentTimeMillis());
		planUser.setUpdateTime(new Date());
		planUser.setUpdateTimeLong(System.currentTimeMillis());
		planUser.setState(IbsStateEnum.OPEN.name());
		new IbspPlanUserService().save(planUser);
	}
}
