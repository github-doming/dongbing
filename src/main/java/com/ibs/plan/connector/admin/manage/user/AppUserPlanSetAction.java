package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.entity.IbspPlanHm;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.entity.IbspProfitPlan;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.entity.IbspProfitPlanVr;
import com.ibs.plan.module.cloud.ibsp_profit_plan_vr.service.IbspProfitPlanVrService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 设置用户方案列表
 * @Author: admin1
 * @Date: 2020/6/16 17:01
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/plan/set")
public class AppUserPlanSetAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = StringTool.getString(dataMap, "appUserId", "");
		String planUsable = StringTool.getString(dataMap, "planUsable", "");
		try {
			IbspExpUserService expUserService = new IbspExpUserService();
			IbspExpUser expUser = expUserService.findByUserId(appUserId);
			if (expUser == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			//更新方案
			updatePlan(expUser, planUsable);

			expUser.setAvailablePlan(planUsable);
			expUserService.update(expUser);

			bean.success(planUsable);
		} catch (Exception e) {
			log.error("获取计划列表", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}

	public static void updatePlan(IbspExpUser expUser, String planUsable) throws Exception {
		List<String> oldList = Arrays.asList(expUser.getAvailablePlan().split(","));
		List<String> newList = Arrays.asList(planUsable.split(","));
		//新方案codes-原方案codes----需要添加方案
//		Set<String> saveUserPlanCode = new HashSet<>((Collection<? extends String>) ContainerTool.getDifferent(newList, oldList));

		//原方案codes-新方案codes----需要删除方案
		Set<String> delUserPlanCode = new HashSet<>((Collection<? extends String>) ContainerTool.getDifferent(oldList, newList));

		IbspPlanUserService planUserService = new IbspPlanUserService();
		IbspPlanService planService = new IbspPlanService();

//		if (ContainerTool.notEmpty(saveUserPlanCode)) {
//			//获取方案的基本信息
//			Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(saveUserPlanCode);
//			//获取可用的方案游戏,在结合可用游戏进行过滤
//			Set<String> gameCodes = new HashSet<>(Arrays.asList(expUser.getAvailableGame().split(",")));
//			List<String> gameIds = new IbspGameService().findIds(gameCodes);
//
//			List<Map<String, Object>> planItems;
//			//方案对应的游戏ID
//			Map<String, List<Object>> planGames = new IbspPlanGameService().listInfoByCodes(saveUserPlanCode);
//
//			//获取方案初始化信息
//			IbspPlanItemService planItemService = new IbspPlanItemService();
//			Map<Object, Map<String, Object>> initPlanInfo = planItemService.findInitInfo(saveUserPlanCode);
//
//			List<Map<String, Object>> existInfos = new IbspClientHmService().findMemberInfos(expUser.getAppUserId());
//			if (ContainerTool.notEmpty(planGames) && ContainerTool.notEmpty(initPlanInfo)) {
//				for (Map.Entry<String, List<Object>> entry : planGames.entrySet()) {
//					String planCode = entry.getKey();
//					List<Object> planGameIds = entry.getValue();
//
//					Map<String, Object> planInfo = planInfos.get(planCode);
//					//初始化方案详情信息
//					planItems = planService.savePlanItem(planInfo, planGameIds, gameIds, expUser.getAppUserId(), initPlanInfo.get(planCode));
//					for(Map<String,Object> planItem:planItems){
//						String snStr = planUserService.findUserPlanMaxSn(expUser.getAppUserId(), planItem.get("GAME_ID_").toString());
//						int sn = StringTool.isEmpty(snStr) ? 1 : NumberTool.getInteger(snStr)+1;
//						planItem.put("SN_",sn);
//					}
//					planUserService.saveRegister(planCode, planInfo, planItems, expUser.getAppUserId());
//					//初始化会员相应信息
//					if (ContainerTool.notEmpty(existInfos)) {
//						//添加方案会员信息，会员方案盈亏信息
//						savePlanHm(existInfos, planItems, planInfo, initPlanInfo.get(planCode),expUser.getAppUserId());
//					}
//				}
//			}
//		}
		//删除方案 PLAN_ID_,PLAN_NAME_,PLAN_CODE_,PLAN_ITEM_TABLE_NAME_
		if (ContainerTool.notEmpty(delUserPlanCode)) {
			Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(delUserPlanCode);
			List<String> planIds = new ArrayList<>();
			List<String> userId =new ArrayList<>();
			userId.add(expUser.getAppUserId());
			for (String planCode : delUserPlanCode) {
				Map<String, Object> planInfo = planInfos.get(planCode);
				planIds.add(planInfo.get("PLAN_ID_").toString());
				// 清理相关方案表信息
				planUserService.clearUserPlanInfo(userId, planInfo.get("PLAN_ID_").toString(), planInfo.get("PLAN_ITEM_TABLE_NAME_").toString());
			}
			// 清理ibsp_plan_user、ibsp_plan_hm、ibsp_profit_plan、ibsp_profit_plan_vr
			planUserService.clearPlanUser(expUser.getAppUserId(), planIds);
		}
	}

	private static void savePlanHm(List<Map<String, Object>> existInfos, List<Map<String, Object>> planItems, Map<String, Object> planInfo,
								   Map<String, Object> initPlanInfo,String appUserId) throws Exception {
		Date nowTime = new Date();
		for (Map<String, Object> planItem : planItems) {
			String gameId = planItem.get("GAME_ID_").toString();
			String planId = planInfo.get("PLAN_ID_").toString();
			//添加已登录会员方案信息
			IbspPlanHmService planHmService = new IbspPlanHmService();
			IbspPlanHm planHm = new IbspPlanHm();
			planHm.setAppUserId(appUserId);
			planHm.setPlanId(planId);
			planHm.setGameId(gameId);
			planHm.setPlanItemTableId(planItem.get("PLAN_ITEM_ID_"));
			planHm.setPlanItemTableName(planInfo.get("PLAN_ITEM_TABLE_NAME_"));
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
			profitPlan.setProfitLimitMaxT(initPlanInfo.get("PROFIT_LIMIT_MAX_"));
			profitPlan.setLossLimitMinT(initPlanInfo.get("LOSS_LIMIT_MIN_"));
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
			profitPlanVr.setProfitLimitMaxT(initPlanInfo.get("PROFIT_LIMIT_MAX_"));
			profitPlanVr.setLossLimitMinT(initPlanInfo.get("LOSS_LIMIT_MIN_"));
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
}
