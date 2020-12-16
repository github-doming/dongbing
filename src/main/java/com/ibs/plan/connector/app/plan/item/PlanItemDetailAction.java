package com.ibs.plan.connector.app.plan.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.common.core.JsonResultBeanPlus;
import com.common.entity.PlanItem;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_log_plan.entity.IbspLogPlan;
import com.ibs.plan.module.cloud.ibsp_log_plan.service.IbspLogPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改方案详情信息
 * @Author: null
 * @Date: 2020-06-01 17:30
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/plan/itemDetail", method = HttpConfig.Method.POST)
public class PlanItemDetailAction extends CommCoreAction {
	private String gameCode;
	private String planCode;
	private String profitLimitMax;
	private String lossLimitMin;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			IbspPlanUserService planUserService = new IbspPlanUserService();
			Map<String, Object> planItemTableInfo = planUserService.findItemTable(appUserId, planCode, gameId);
			if (ContainerTool.isEmpty(planItemTableInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String fundsList = dataMap.getOrDefault("FUNDS_LIST_", "").toString();
			String followPeriod = dataMap.getOrDefault("FOLLOW_PERIOD_", "0").toString();
			String monitorPeriod = dataMap.getOrDefault("MONITOR_PERIOD_", "0").toString();
			String betMode = dataMap.getOrDefault("BET_MODE_", "").toString();
			String fundSwapMode = dataMap.getOrDefault("FUND_SWAP_MODE_", "").toString();
			String periodRollMode = dataMap.getOrDefault("PERIOD_ROLL_MODE_", "").toString();

			PlanItem planItem = new PlanItem();
			planItem.setPlanCode(planCode);
			planItem.setProfitLimitMax(profitLimitMax);
			planItem.setLossLimitMin(lossLimitMin);
			planItem.setFundsList(fundsList);
			planItem.setFollowPeriod(followPeriod);
			planItem.setMonitorPeriod(monitorPeriod);
			planItem.setBetMode(betMode);
			planItem.setFundSwapMode(fundSwapMode);
			planItem.setPeriodRollMode(periodRollMode);

			if (StringTool.notEmpty(betMode) || StringTool.notEmpty(monitorPeriod)) {
				planUserService.updateItemInfo(appUserId, planCode, gameId, betMode, monitorPeriod);
			}

			IbspPlanItemService planItemService = new IbspPlanItemService();
			planItemService.update(planItemTableInfo, planItem);

			Map<String, String> existInfos = new IbspClientHmService().findUserMemberInfos(appUserId);
			if (ContainerTool.notEmpty(existInfos)) {
				JSONObject content = new JSONObject();
				//放入方案详情信息
				putPlanItem(planCode, planItemTableInfo, content);
				content.put("PLAN_STATE_", planItemTableInfo.get("PLAN_STATE_"));
				content.put("PLAN_CODE_", planCode);
				content.put("GAME_CODE_", gameCode);
				for (Map.Entry<String, String> entry : existInfos.entrySet()) {
					content.put("EXIST_HM_ID_", entry.getKey());
					String eventId = EventThreadDefine.saveMemberConfigSetEvent(content, new Date());
					content.put("EVENT_ID_", eventId);

					RabbitMqTool.sendMember(content.toString(), entry.getValue(), "set");
				}
			}
			//添加方案用户日志信息
			savePlanLog(planItem);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改方案详情信息失败", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 放入方案详情信息
	 *
	 * @param planCode          方案编码
	 * @param planItemTableInfo 方案详情表信息
	 * @param content           消息内容
	 */
	private static void putPlanItem(String planCode, Map<String, Object> planItemTableInfo, JSONObject content) throws SQLException {
		PlanUtil.Code plan = PlanUtil.Code.valueOf(planCode);


		//获取方案详情信息
		Map<String, Object> planItemInfo = new IbspPlanItemService().findPlanItemInfo(planItemTableInfo);
		planItemInfo.remove("PROFIT_LIMIT_MAX_");
		planItemInfo.remove("LOSS_LIMIT_MIN_");
		Object activceKey = planItemInfo.remove("PLAN_GROUP_ACTIVE_KEY_");
		Object planGroup = planItemInfo.remove("PLAN_GROUP_DATA_");
		//写入客户设置事件
		content.putAll(planItemInfo);
		content.put("METHOD_", IbsMethodEnum.SET_PLAN.name());
		if (StringTool.isEmpty(activceKey)) {
			content.put("PLAN_GROUP_ACTIVE_DATA_", "");
			return;
		}
		JSONObject planGroupData = JSONObject.parseObject(planGroup.toString());
		String[] activeKeys = activceKey.toString().split(",");
		//方案组数据预处理
		JSONObject activePlanGroup = plan.getPlan().advance(activeKeys, planGroupData);
		content.put("PLAN_GROUP_ACTIVE_DATA_", JSON.toJSONString(activePlanGroup, SerializerFeature.DisableCircularReferenceDetect));
	}


	private void savePlanLog(PlanItem planItem) throws Exception {
		IbspLogPlan logPlan = new IbspLogPlan();
		logPlan.setAppUserId(appUserId);
		logPlan.setPlanCode(planCode);
		logPlan.setHandleType("UPDATE_ITEM");
		logPlan.setHandleContent("PROFIT_LIMIT_MAX_:".concat(profitLimitMax).concat(",LOSS_LIMIT_MIN_:").concat(lossLimitMin)
				.concat(",FUNDS_LIST_:").concat(planItem.getFundsList()).concat(",FOLLOW_PERIOD_:") + planItem.getFollowPeriod()
				+ ",MONITOR_PERIOD_:" + planItem.getMonitorPeriod() + ",BET_MODE_:".concat(planItem.getBetMode())
				.concat(",FUND_SWAP_MODE_:").concat(planItem.getFundSwapMode()).concat(",PERIOD_ROLL_MODE_:").concat(planItem.getPeriodRollMode()));
		logPlan.setCreateTime(new Date());
		logPlan.setCreateTimeLong(System.currentTimeMillis());
		logPlan.setUpdateTimeLong(System.currentTimeMillis());
		logPlan.setState(IbsStateEnum.OPEN.name());
		logPlan.setDesc(this.getClass().getName());
		new IbspLogPlanService().save(logPlan);
	}

	private boolean valiParameters() {
		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		planCode = dataMap.getOrDefault("PLAN_CODE_", "").toString();

		profitLimitMax = dataMap.getOrDefault("PROFIT_LIMIT_MAX_", "").toString();

		lossLimitMin = dataMap.getOrDefault("LOSS_LIMIT_MIN_", "").toString();

//		planGroupData = dataMap.getOrDefault("PLAN_GROUP_DATA_", "").toString();

		return StringTool.isEmpty(gameCode, planCode, profitLimitMax, lossLimitMin);
	}
}
