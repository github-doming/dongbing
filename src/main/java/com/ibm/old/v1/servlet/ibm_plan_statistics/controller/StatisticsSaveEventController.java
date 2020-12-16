package com.ibm.old.v1.servlet.ibm_plan_statistics.controller;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.core.IbmExecutor;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.IbmServletCmdEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.entity.IbmEventPlanstatisticsT;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.service.IbmEventPlanstatisticsTService;
import com.ibm.old.v1.servlet.ibm_plan_statistics.service.IbmPlanStatisticsService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 统计事件保存控制器
 * @Author: Dongming
 * @Date: 2019-06-10 17:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StatisticsSaveEventController implements IbmExecutor {
	private AppUserT appUserT;
	private Map<String, Object> dataMap;

	private String startDay;
	private String endDay;
	private String startTime;
	private String endTime;
	private String gameCode;
	private String planCode;
	private String odds;
	private IbmGameEnum game;
	private PlanTool.Code plan;
	private IbmStateEnum statisticalState, moneyResetState;
	public StatisticsSaveEventController(AppUserT appUserT, Map<String, Object> dataMap) {
		this.appUserT = appUserT;
		this.dataMap = dataMap;

	}
	@Override public void execute(JsonResultBeanPlus jrb) throws Exception {
		if (!valiParameters()) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return;
		}

		IbmPlanStatisticsService planStatisticsService = new IbmPlanStatisticsService();
		//查找游戏id
		String gameId = planStatisticsService.findGameId(gameCode);
		//获取方案id
		String planId = planStatisticsService.findPlanId(planCode, gameId);
		jrb.putSysEnum(IbmCodeEnum.CODE_404);
		if (StringTool.isEmpty(gameId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_GAME);
			return;
		}
		if (StringTool.isEmpty(planId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
			return;
		}

		//获取方案详情信息
		Map<String, Object> planItem = planStatisticsService.findPlanItemInfo(planId, appUserT.getAppUserId(), plan);
		if (ContainerTool.isEmpty(planItem)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_PLAN_GROUP_ACTIVE);
			return;
		}

		//赔率信息
		JSONObject statisticsData = new JSONObject();
		if (StringTool.notEmpty(odds)) {
			statisticsData.put("odds", odds);
		}

		//统计信息
		statisticsData.put("startDay", startDay);
		statisticsData.put("endDay", endDay);
		statisticsData.put("startTime", startTime);
		statisticsData.put("endTime", endTime);
		statisticsData.put("planCode", plan.name());
		statisticsData.put("gameCode", game.name());
		statisticsData.put("statisticalState", statisticalState);
		statisticsData.put("moneyResetState", moneyResetState);
		statisticsData.put("planItem", planItem);

		//写入事件
		Date nowTime = new Date();
		IbmEventPlanstatisticsTService planstatisticsTService = new IbmEventPlanstatisticsTService();
		IbmEventPlanstatisticsT planstatisticsT = new IbmEventPlanstatisticsT();
		planstatisticsT.setAppUserId(appUserT.getAppUserId());
		planstatisticsT.setEventType(IbmServletCmdEnum.STATISTICS.name());
		planstatisticsT.setEventContent(statisticsData);
		planstatisticsT.setEventState(IbmStateEnum.BEGIN.name());
		planstatisticsT.setCreateTime(nowTime);
		planstatisticsT.setCreateTimeLong(System.currentTimeMillis());
		planstatisticsT.setUpdateTime(nowTime);
		planstatisticsT.setUpdateTimeLong(System.currentTimeMillis());
		planstatisticsT.setState(IbmStateEnum.OPEN.name());

		//返回结果
		String eventId = planstatisticsTService.save(planstatisticsT);
		Map<String, Object> data = new HashMap<>(2);
		data.put("eventId", eventId);
		data.put("time", nowTime.getTime());
		jrb.setData(data);
		jrb.success();
	}

	/**
	 * 验证参数
	 *
	 * @return 验证结果
	 */
	private boolean valiParameters() {
		//开始天
		startDay = BeanThreadLocal.find(dataMap.get("startDay"), "");
		//结束天
		endDay = BeanThreadLocal.find(dataMap.get("endDay"), "");
		//开始时间
		startTime = BeanThreadLocal.find(dataMap.get("startTime"), "");
		//结束时间
		endTime = BeanThreadLocal.find(dataMap.get("endTime"), "");
		//游戏code
		gameCode = BeanThreadLocal.find(dataMap.get("gameCode"), "");
		//方案code
		planCode = BeanThreadLocal.find(dataMap.get("planCode"), "");
		//统计状态,是否按每天的时间段来统计
		String statistical = BeanThreadLocal.find(dataMap.get("statisticalState"), "");
		//每天重置资金状态
		String resetState = BeanThreadLocal.find(dataMap.get("resetState"), "");
		//赔率
		odds = BeanThreadLocal.find(dataMap.get("odds"), "");

		if (!DateTool.isDate(startDay) || !DateTool.isDate(endDay)) {
			return false;
		}
		if (StringTool.notEmpty(startTime,endTime)){
			if(!DateTool.isTime(startTime) || !DateTool.isTime(endTime)){
				return false;
			}
		}

		//获取游戏
		game = IbmGameEnum.valueOf(gameCode);
		//获取方案code
		plan = PlanTool.Code.valueOf(planCode);
		//统计状态,是否按每天的时间段来统计
		statisticalState = IbmStateEnum.getState(statistical);
		//每天重置资金状态
		moneyResetState = IbmStateEnum.getState(resetState);

		return !StringTool.isEmpty(game, plan, moneyResetState);
	}
}
