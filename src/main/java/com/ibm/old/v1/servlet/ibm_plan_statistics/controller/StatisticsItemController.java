package com.ibm.old.v1.servlet.ibm_plan_statistics.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.StatisticsTool;
import org.apache.commons.lang3.ArrayUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-07-25 16:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StatisticsItemController implements StatisticsController {
	private String startDay;
	private String endDay;
	private String startTime;
	private String endTime;
	private String sql;
	private PlanTool.Code planCode;
	private IbmGameEnum game;
	private IbmStateEnum statisticalState;
	private IbmStateEnum moneyResetState;
	private JSONObject planItem;
	private Object odds;
	private IbmGameTService gameService;

	public StatisticsItemController(String startDay, String endDay, String startTime, String endTime, String sql,
			PlanTool.Code planCode, IbmGameEnum game, IbmStateEnum statisticalState, IbmStateEnum moneyResetState,
			JSONObject planItem, Object odds) {
		this.startDay = startDay;
		this.endDay = endDay;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sql = sql;
		this.planCode = planCode;
		this.game = game;
		this.statisticalState = statisticalState;
		this.moneyResetState = moneyResetState;
		this.planItem = planItem;
		this.odds = odds;

	}
	@Override public JSONArray statistics() throws Exception {
		gameService = new IbmGameTService();
		//累计盈亏
		long accruingProfit = 0L;

		JSONArray result;
		JSONArray detail;
		JSONArray detailList = new JSONArray();
		List<String> dateList = DateTool.getDates(startDay, endDay);
		Date startDate, endDate;
		for (int i = 0, len = dateList.size(); i < len; i++) {
			if (IbmStateEnum.OPEN.name().equals(moneyResetState.name())) {
				accruingProfit = 0L;
			}
			startDate = StatisticsTool.getStartDate(game, statisticalState, startTime, dateList.get(i), i);
			endDate = StatisticsTool.getEndDate(game, statisticalState, endTime, dateList.get(i), i, len);
			detail = getStatisticsDetail(startDate, endDate, accruingProfit);
			if (ContainerTool.isEmpty(detail)) {
				continue;
			}
			result = detail.getJSONArray(detail.size() - 1);
			accruingProfit = NumberTool.longValueT(result.getDouble(result.size() - 1));
			detailList.addAll(detail);
		}
		return detailList;
	}

	/**
	 * 获取统计明细
	 *
	 * @param startDate      开始时间
	 * @param endDate        结束时间
	 * @param accruingProfit 累计金额信息
	 * @return 统计明细
	 */
	private JSONArray getStatisticsDetail(Date startDate, Date endDate, long accruingProfit) throws Exception {
		//开始期数
		Object statePeriod = PeriodTool.findPeriod(game, startDate);
		//结束期数
		Object endPeriod;
		if (DateTool.compare(new Date(), endDate)) {
			endPeriod = PeriodTool.findPeriod(game,new Date());
		} else {
			endPeriod = PeriodTool.findPeriod(game,endDate);
		}
		//获取历史期数
		Object[] historyPeriods = PeriodTool.findHistoryPeriod(game,statePeriod, 10);
		//获取统计期数
		Object[] bodyPeriods = PeriodTool.findPeriods(game,statePeriod, endPeriod);
		Object[] periods = ArrayUtils.addAll(historyPeriods, bodyPeriods);
		//获取开奖结果

		Map<Object, Map<String, Object>> drawInfoMap = gameService.findDrawInfoMap(sql, periods);
		if (ContainerTool.isEmpty(drawInfoMap)) {
			return null;
		}
		return new StatisticsPlanItemController(bodyPeriods, drawInfoMap, odds, planItem, moneyResetState,
				accruingProfit, planCode, game).statistics();

	}

}
