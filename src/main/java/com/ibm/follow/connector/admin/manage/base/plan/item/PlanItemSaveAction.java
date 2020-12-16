package com.ibm.follow.connector.admin.manage.base.plan.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import com.ibm.follow.servlet.cloud.vr_plan_item.entity.VrPlanItem;
import com.ibm.follow.servlet.cloud.vr_plan_item.service.VrPlanItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 方案详情信息
 * @Author: admin1
 * @Date: 2020/6/22 13:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/plan/item/save", method = HttpConfig.Method.POST)
public class PlanItemSaveAction extends CommAdminAction {

	private String planCode, fundsList, followPeriod, monitorPeriod, betMode, fundSwapMode,
			periodRollMode, planGroupData, expandInfo, happyPlanGroupData, happyExpandInfo, ballPlanGroupData, ballExpandInfo;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		planCode = StringTool.getString(dataMap, "PLAN_CODE_", "");
		fundsList = StringTool.getString(dataMap.get("FUNDS_LIST_"), "");
		followPeriod = StringTool.getString(dataMap.get("FOLLOW_PERIOD_"), "");
		monitorPeriod = StringTool.getString(dataMap.get("MONITOR_PERIOD_"), "");
		betMode = StringTool.getString(dataMap.get("BET_MODE_"), "");
		fundSwapMode = StringTool.getString(dataMap.get("FUND_SWAP_MODE_"), "");
		periodRollMode = StringTool.getString(dataMap.get("PERIOD_ROLL_MODE_"), "");

		// TODO
		planGroupData = StringTool.getString(dataMap.get("NUMBER_PLAN_GROUP_DATA_"), "");
		expandInfo = StringTool.getString(dataMap.get("NUMBER_EXPAND_INFO_"), "");
		happyPlanGroupData = StringTool.getString(dataMap.get("HAPPY_PLAN_GROUP_DATA_"), "");
		happyExpandInfo = StringTool.getString(dataMap.get("HAPPY_EXPAND_INFO_"), "");
		ballPlanGroupData = StringTool.getString(dataMap.get("BALL_PLAN_GROUP_DATA_"), "");
		ballExpandInfo = StringTool.getString(dataMap.get("BALL_EXPAND_INFO_"), "");


		if (StringTool.isEmpty(planCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		Date nowTime = new Date();
		try {
			//验证方案编码是否存在
			VrPlanService planService = new VrPlanService();
			VrPlan plan = planService.findPlan(planCode);
			if (plan == null) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return super.returnJson(bean);
			}

			String[] gameTypes = plan.getAvailableGameType().split(",");
			VrPlanItemService itemService = new VrPlanItemService();
			VrPlanItem planItem;
			List<Map<String, Object>> planItems;
			Map<String, Object> item;
			Map<String, List<Map<String, Object>>> items = new HashMap<>(3);
			for (String gameType : gameTypes) {
				if ("HAPPY".equalsIgnoreCase(gameType)) {
					planGroupData = happyPlanGroupData;
					expandInfo = happyExpandInfo;
				} else if ("BALL".equalsIgnoreCase(gameType)) {
					planGroupData = ballPlanGroupData;
					expandInfo = ballExpandInfo;
				}
				planItems = new ArrayList<>();
				item = new HashMap<>(15);
				item.put("PLAN_ID_", plan.getVrPlanId());
				item.put("PROFIT_LIMIT_MAX_", 100000);
				item.put("LOSS_LIMIT_MIN_", -100000);
				item.put("FUNDS_LIST_", fundsList);
				item.put("FOLLOW_PERIOD_", followPeriod);
				item.put("MONITOR_PERIOD_", monitorPeriod);
				item.put("BET_MODE_", betMode);
				item.put("FUND_SWAP_MODE_", fundSwapMode);
				item.put("PERIOD_ROLL_MODE_", periodRollMode);
				item.put("PLAN_GROUP_DATA_", planGroupData);
				item.put("PLAN_GROUP_ACTIVE_KEY_", "");
				item.put("EXPAND_INFO_", expandInfo);
				item.put("PLAN_CODE_", planCode);
				item.put("PLAN_NAME_", plan.getPlanName());
				item.put("SN_", plan.getSn());
				planItems.add(item);
				items.put(gameType, planItems);

				planItem = new VrPlanItem();
				planItem.setPlanCode(planCode);
				planItem.setGameType(gameType);
				planItem.setProfitLimitMax(100000);
				planItem.setLossLimitMin(-100000);
				planItem.setFundsList(fundsList);
				planItem.setFollowPeriod(followPeriod);
				planItem.setMonitorPeriod(monitorPeriod);
				planItem.setBetMode(betMode);
				planItem.setFundSwapMode(fundSwapMode);
				planItem.setPeriodRollMode(periodRollMode);
				planItem.setPlanGroupData(planGroupData);
				planItem.setPlanGroupActiveKey("");
				planItem.setExpandInfo(expandInfo);
				planItem.setCreateTime(nowTime);
				planItem.setCreateTimeLong(System.currentTimeMillis());
				planItem.setUpdateTime(nowTime);
				planItem.setUpdateTimeLong(System.currentTimeMillis());
				planItem.setState(IbmStateEnum.OPEN.name());
				itemService.save(planItem);
			}
			//基准游戏编码 -- 该方案适用的游戏编码
			List<String> baseGameCodes = getGameCodes(gameTypes);
			//初始化用户方案信息
			initVrUserPlanInfo(items, baseGameCodes, nowTime);

			bean.success();
		} catch (Exception e) {
			log.error("获取盘口修改表单页面信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void initVrUserPlanInfo(Map<String, List<Map<String, Object>>> items, List<String> bestGameCodes, Date nowTime) throws Exception {
		//查询所有用户
		List<String> memberIds = new VrMemberService().listAllMemberIds();
		VrMemberGameSetService gameSetService = new VrMemberGameSetService();
		VrPiMemberPlanItemService piMemberPlanItemService = new VrPiMemberPlanItemService();

		List<String> gameCodes;
		for (String memberId : memberIds) {
			//查询该会员拥有的游戏编码
			gameCodes = gameSetService.getGameCode(memberId);
			for (String gameCode : gameCodes) {
				//适用于该方案则添加
				if (bestGameCodes.contains(gameCode)) {
					piMemberPlanItemService.save(items.get(type(gameCode)), gameCode, memberId, nowTime);
				}

			}
		}
	}

	public static String type(String gameCode) {
		switch (gameCode) {
			case "JSSSC":
			case "CQSSC":
			case "COUNTRY_SSC":
			case "SELF_SSC_5":
				return "BALL";
			case "XYNC":
			case "GDKLC":
			case "GXKLSF":
				return "HAPPY";
			case "XYFT":
			case "JS10":
			case "PK10":
			case "COUNTRY_10":
			case "SELF_10_2":
			default:
				return "NUMBER";
		}
	}

	private List<String> getGameCodes(String[] gameTypes) {
		List<String> gameCodes = new ArrayList<>();
		for (String gameType : gameTypes) {
			if ("BALL".equalsIgnoreCase(gameType)) {
				gameCodes.add("JSSSC");
				gameCodes.add("CQSSC");
				gameCodes.add("COUNTRY_SSC");
				gameCodes.add("SELF_SSC_5");
			} else if ("NUMBER".equalsIgnoreCase(gameType)) {
				gameCodes.add("XYFT");
				gameCodes.add("JS10");
				gameCodes.add("PK10");
				gameCodes.add("COUNTRY_10");
				gameCodes.add("SELF_10_2");
			} else {
				gameCodes.add("XYNC");
				gameCodes.add("GDKLC");
				gameCodes.add("GXKLSF");
			}
		}
		return gameCodes;
	}


}
