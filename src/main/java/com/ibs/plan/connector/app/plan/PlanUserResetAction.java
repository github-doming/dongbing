package com.ibs.plan.connector.app.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
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
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户方案重置
 * @Author: null
 * @Date: 2020-07-07 11:43
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/plan/reset", method = HttpConfig.Method.POST)
public class PlanUserResetAction extends CommCoreAction {
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
		if (StringTool.isEmpty(gameCode, planCode)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}

			List<Map<String, Object>> existInfos = new IbspClientHmService().findMemberInfos(appUserId);
			if (ContainerTool.isEmpty(existInfos)) {
				bean.success();
				return bean;
			}

			//重置会员方案盈亏信息
			new IbspProfitPlanService().resetProfit(existInfos, PlanUtil.id(planCode), gameId, "ibsp_profit_plan");
			new IbspProfitPlanService().resetProfit(existInfos, PlanUtil.id(planCode), gameId, "ibsp_profit_plan_vr");

			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbsMethodEnum.SET_PLAN_RESET.name());
			content.put("GAME_CODE_", gameCode);
			content.put("PLAN_CODES_", planCode);
			for (Map<String, Object> map : existInfos) {
				content.put("EXIST_HM_ID_", map.get("EXIST_HM_ID_"));
				String eventId = EventThreadDefine.saveMemberConfigSetEvent(content, new Date());
				content.put("EVENT_ID_", eventId);

				RabbitMqTool.sendMember(content.toString(), map.get("CLIENT_CODE_").toString(), "set");
			}

			//添加方案用户日志信息
			savePlanLog(planCode);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改用户方案状态失败", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void savePlanLog(String planCodes) throws Exception {
		IbspLogPlan logPlan = new IbspLogPlan();
		logPlan.setAppUserId(appUserId);
		logPlan.setPlanCode(planCodes);
		logPlan.setHandleType("PLAN_RESET_");
		logPlan.setHandleContent(",PLAN_CODE_:".concat(planCodes));
		logPlan.setCreateTime(new Date());
		logPlan.setCreateTimeLong(System.currentTimeMillis());
		logPlan.setUpdateTimeLong(System.currentTimeMillis());
		logPlan.setState(IbsStateEnum.OPEN.name());
		logPlan.setDesc(this.getClass().getName());
		new IbspLogPlanService().save(logPlan);
	}
}
