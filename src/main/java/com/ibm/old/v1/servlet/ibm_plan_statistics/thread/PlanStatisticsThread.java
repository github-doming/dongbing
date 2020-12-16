package com.ibm.old.v1.servlet.ibm_plan_statistics.thread;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.StatisticsTool;
import com.ibm.old.v1.servlet.ibm_plan_statistics.controller.ExcelTool;
import com.ibm.old.v1.servlet.ibm_plan_statistics.controller.StatisticsItemController;
import com.ibm.old.v1.servlet.ibm_plan_statistics.entity.StatisticsGeneral;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.service.IbmEventPlanstatisticsTService;
import com.ibm.old.v1.servlet.ibm_plan_statistics.service.IbmPlanStatisticsService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.*;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-11 10:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanStatisticsThread extends BaseCommThread {
	private volatile static boolean execEvent = false;
	private static final String PLAN_GROUP_TITLE_FORMAT = "第%d组" ;

	public PlanStatisticsThread() {
		super.transaction = Connection.TRANSACTION_NONE;
	}

	@Override public String execute(String ignore) throws Exception {
		try {
			IbmEventPlanstatisticsTService planstatisticsService = new IbmEventPlanstatisticsTService();
			IbmPlanStatisticsService planStatisticsService = new IbmPlanStatisticsService();

			/*
			 * 初始化数据区
			 */
			int num = 0;
			CurrentTransaction.transactionBegin();
			final Map<String, Map<Object, Object>> oddsMap = planStatisticsService.findOddsMap();
			final Map<String, String> drawInfoSqlMap = StatisticsTool.getDrawInfoSql();
			CurrentTransaction.transactionEnd();
			while (true) {
				if (!execEvent) {
					return null;
				}
				if (++num > 100) {
					CurrentTransaction.transactionCommit();

					num = 0;

				}
				Object newInfo = planstatisticsService.transactionSyn(planstatisticsService::findNewEvent);
				if (ContainerTool.isEmpty(newInfo)) {
					Thread.sleep(1000);
					continue;
				}
				Map<String, Object> info = (Map<String, Object>) newInfo;
				String eventPlanstatisticsId = info.get("IBM_EVENT_PLANSTATISTICS_ID_").toString();
				JSONObject content = JSONObject.parseObject(info.get("EVENT_CONTENT_").toString());
				String startDay = content.getString("startDay");
				String endDay = content.getString("endDay");
				String startTime = content.getString("startTime");
				String endTime = content.getString("endTime");
				Object odds = content.get("odds");
				IbmGameEnum gameCode = IbmGameEnum.valueOf(content.getString("gameCode"));
				PlanTool.Code planCode = PlanTool.Code.valueOf(content.getString("planCode"));
				IbmStateEnum statisticalState = IbmStateEnum.getState(content.get("statisticalState"));
				IbmStateEnum moneyResetState = IbmStateEnum.getState(content.get("moneyResetState"));
				JSONObject planItem = content.getJSONObject("planItem");

				if (StringTool.isEmpty(gameCode)) {
					continue;
				}
				if (StringTool.isEmpty(odds)) {
					odds = oddsMap.get(gameCode.name());
				}

				//计算统计明细
				JSONArray detailList;
				try {
					//计算统计明细
					detailList = new StatisticsItemController(startDay, endDay, startTime, endTime,
							drawInfoSqlMap.get(gameCode.name()), planCode, gameCode, statisticalState, moneyResetState,
							planItem, odds).statistics();
				} catch (Exception e) {
					log.error("统计错误，id为：".concat(eventPlanstatisticsId), e);
					continue;
				}
				//资金列表
				String[] funds = planItem.get("funds").toString().split(",");
				//计算统计结果
				JSONArray generalList = statisticsGeneralResult(detailList, funds, DateTool.getDates(startDay, endDay),
						gameCode);

				JSONObject result = new JSONObject();
				//统计结果
				result.put("general", generalList);
				//结果颠倒
				result.put("detail", upsideDown(detailList));
				//方案组标题
				result.put("groupTitle", getPlanGroupTitle(planItem));

				String basePath = this.getClass().getResource("/").getPath()
						.replaceAll("WEB-INF/classes/", "dist/servlet/statistics/");
				String fileName = RandomTool.getNumLetter32().concat(".xls");
				String path = basePath.concat(fileName);
				try {
					ExcelTool.writeStatisticsResult(path, result);
					planstatisticsService
							.transaction(() -> planstatisticsService.updateResult(eventPlanstatisticsId, result, path));
				} catch (Exception e) {
					log.error("保存错误，id为：".concat(eventPlanstatisticsId), e);
				}
			}
		} finally {
			execEvent = false;
		}
	}

	/**
	 * 修改线程状态
	 *
	 * @param flag 状态
	 * @return 修改结果
	 */
	public synchronized static boolean updateThreadState(boolean flag) throws Exception {
		if (flag) {
			if (execEvent) {
				log.error("线程已经被启动，无法再次启动");
				return false;
			} else {
				//开启一个线程，进行计算。
				ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance()
						.findLocal();
				ExecutorService executorService = threadExecutorService.findExecutorService();
				for (int i = 0; i < 5; i++) {
					executorService.execute(new PlanStatisticsThread());
				}
				execEvent = true;
				return true;
			}
		} else {
			if (execEvent) {
				execEvent = false;
				return true;
			} else {
				log.error("线程已经被关闭，无法再次关闭");
				return false;
			}
		}
	}

	/**
	 * 获取统计结果
	 *
	 * @param detailResultArray 统计结果array
	 * @param funds             资金列表
	 * @param date              日期集合
	 * @return 统计结果
	 */
	public static JSONArray statisticsGeneralResult(JSONArray detailResultArray, String[] funds, List<String> date,
			IbmGameEnum gameCode) throws ParseException {
		List<StatisticsGeneral> dayGenerals = new ArrayList<>(date.size());
		int maxFunds = NumberTool.findMax(funds);
		for (String day : date) {
			StatisticsGeneral dayGeneral = StatisticsGeneral.newInstance(day);
			dayGeneral.analysis(detailResultArray, gameCode, day, maxFunds);
			dayGenerals.add(dayGeneral);
		}
		StatisticsGeneral totalGeneral = StatisticsGeneral.newInstance("合计");
		return totalGeneral.getGeneralArray(dayGenerals);
	}

	/**
	 * 结果颠倒
	 *
	 * @param detailResultArray 统计明细信息
	 */
	public static JSONArray upsideDown(JSONArray detailResultArray) {
		JSONArray array = new JSONArray();
		for (int i = 0; i < detailResultArray.size(); i++) {
			array.add(detailResultArray.getJSONArray(detailResultArray.size() - i - 1));
		}
		return array;
	}
	/**
	 * 获取方案组标题
	 *
	 * @param planItem 方案详情
	 * @return 方案组标题列表
	 */
	public static JSONArray getPlanGroupTitle(JSONObject planItem) {
		JSONObject activePlanGroup = planItem.getJSONObject("activePlanGroup");
		JSONArray planGroupTitle = new JSONArray();
		Integer[] activeKeys = NumberTool.intValue(activePlanGroup.keySet().toArray());
		if (ContainerTool.isEmpty(activeKeys)) {
			return null;
		}
		Arrays.sort(activeKeys);
		for (Object activeKey : activeKeys) {
			planGroupTitle.add(String.format(PLAN_GROUP_TITLE_FORMAT, StringTool.addOne(activeKey)));
		}
		return planGroupTitle;
	}
}

