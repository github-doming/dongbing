package com.ibm.old.v1.common.doming.test;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.core.BaseTest;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.StatisticsTool;
import com.ibm.old.v1.servlet.ibm_plan_statistics.controller.ExcelTool;
import com.ibm.old.v1.servlet.ibm_plan_statistics.controller.StatisticsItemController;
import com.ibm.old.v1.servlet.ibm_plan_statistics.service.IbmPlanStatisticsService;
import com.ibm.old.v1.servlet.ibm_plan_statistics.thread.PlanStatisticsThread;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-06-15 16:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanStatisticsTest extends BaseTest {

	@Test public void test01() {
		super.transactionBegin();
		try {

			IbmPlanStatisticsService planStatisticsService = new IbmPlanStatisticsService();
			final Map<String, Map<Object, Object>> oddsMap = planStatisticsService.findOddsMap();
			final Map<String, String> drawInfoSqlMap = StatisticsTool.getDrawInfoSql();

//						String newInfo = "{\"startDay\":\"2019-07-01\",\"endDay\":\"2019-07-03\",\"startTime\":\"\",\"endTime\":\"\",\"planCode\":\"FOLLOW_OPEN_BET\",\"gameCode\":\"XYFT\",\"moneyResetState\":\"CLOSE\",\"planItem\":{\"periodRollMode\":\"PERIOD_ROLL_MODE_ALL\",\"fundSwapMode\":\"FUND_SWAP_MODE_ON_SWAP_NO_RESET\",\"activePlanGroup\":{\"0\":{\"activeKey\":\"0\",\"selects\":[{\"select\":\"4\",\"bet\":\"4,5\"},{\"select\":\"1\",\"bet\":\"1,2\"}]},\"4\":{\"activeKey\":\"4\",\"selects\":[{\"select\":\"4\",\"bet\":\"4,5\"},{\"select\":\"1\",\"bet\":\"1,2\"}]}},\"funds\":\"10,11,12,13,14\",\"monitorPeriod\":1,\"followPeriod\":1}}" ;
//			String newInfo = "{\"startDay\":\"2019-06-13\",\"endDay\":\"2019-08-01\",\"startTime\":\"\",\"endTime\":\"\",\"planCode\":\"FOLLOW_TWO_SIDE\",\"gameCode\":\"XYFT\",\"moneyResetState\":\"CLOSE\",\"planItem\":{\"fundSwapMode\":\"FUND_SWAP_MODE_ON_SWAP_NO_RESET\",\"activePlanGroup\":{\"0\":{\"state\":\"TRUE\",\"code\":\"第一名_大小\"},\"1\":{\"state\":\"FALSE\",\"code\":\"第二名_大小\"},\"2\":{\"state\":\"FALSE\",\"code\":\"第三名_大小\"},\"3\":{\"state\":\"TRUE\",\"code\":\"第四名_大小\"},\"6\":{\"state\":\"TRUE\",\"code\":\"第七名_大小\"},\"7\":{\"state\":\"TRUE\",\"code\":\"第八名_大小\"},\"8\":{\"state\":\"FALSE\",\"code\":\"第九名_大小\"},\"9\":{\"state\":\"TRUE\",\"code\":\"第十名_大小\"},\"10\":{\"state\":\"TRUE\",\"code\":\"冠亚_大小\"},\"11\":{\"state\":\"FALSE\",\"code\":\"第一名_单双\"},\"14\":{\"state\":\"FALSE\",\"code\":\"第四名_单双\"},\"15\":{\"state\":\"FALSE\",\"code\":\"第五名_单双\"},\"16\":{\"state\":\"FALSE\",\"code\":\"第六名_单双\"},\"17\":{\"state\":\"FALSE\",\"code\":\"第七名_单双\"},\"18\":{\"state\":\"TRUE\",\"code\":\"第八名_单双\"},\"19\":{\"state\":\"TRUE\",\"code\":\"第九名_单双\"},\"22\":{\"state\":\"TRUE\",\"code\":\"第一名_龙虎\"},\"23\":{\"state\":\"TRUE\",\"code\":\"第二名_龙虎\"},\"25\":{\"state\":\"TRUE\",\"code\":\"第四名_龙虎\"},\"26\":{\"state\":\"FALSE\",\"code\":\"第五名_龙虎\"}},\"funds\":\"1,2,4,8,16,32,64,128,256,512,1024\",\"monitorPeriod\":0,\"followPeriod\":0}}" ;
						String newInfo = "{\"startDay\":\"2019-07-01\",\"endDay\":\"2019-07-01\",\"startTime\":\"\",\"endTime\":\"\",\"planCode\":\"LOCATION_BET_NUMBER\",\"gameCode\":\"XYFT\",\"moneyResetState\":\"CLOSE\",\"planItem\":{\"periodRollMode\":\"PERIOD_ROLL_MODE_ALL\",\"fundSwapMode\":\"FUND_SWAP_MODE_ON_SWAP_NO_RESET\",\"activePlanGroup\":{\"0\":{\"state\":\"TRUE\",\"select\":\"1,2,3\",\"bet\":\"1\"},\"1\":{\"state\":\"TRUE\",\"select\":\"2,3,4,5,6\",\"bet\":\"2\"},\"2\":{\"state\":\"TRUE\",\"select\":\"2,3,4,5,6,7,8\",\"bet\":\"2\"},\"3\":{\"state\":\"TRUE\",\"select\":\"1\",\"bet\":\"2,3,4\"}},\"funds\":\"10,11,12,13,14\",\"monitorPeriod\":1,\"followPeriod\":1}}" ;
//						String newInfo = "{\"startDay\":\"2019-07-01\",\"endDay\":\"2019-07-01\",\"startTime\":\"\",\"endTime\":\"\",\"planCode\":\"NUMBER_TO_TRACK\",\"gameCode\":\"XYFT\",\"moneyResetState\":\"CLOSE\",\"planItem\":{\"periodRollMode\":\"PERIOD_ROLL_MODE_ALL\",\"fundSwapMode\":\"FUND_SWAP_MODE_ON_SWAP_NO_RESET\",\"activePlanGroup\":{\"0\":{\"state\":\"TRUE\",\"track\":\"1\",\"bet\":\"1,2,3\"},\"1\":{\"state\":\"TRUE\",\"track\":\"6\",\"bet\":\"2,3,4,5\"}},\"funds\":\"10,11,12,13,14\",\"monitorPeriod\":0,\"followPeriod\":1}}" ;
			//			String newInfo = "{\"startDay\":\"2019-06-23\",\"endDay\":\"2019-07-03\",\"startTime\":\"\",\"endTime\":\"\",\"planCode\":\"RANK_HOT_AND_COLD\",\"gameCode\":\"PK10\",\"moneyResetState\":\"CLOSE\",\"planItem\":{\"fundSwapMode\":\"FUND_SWAP_MODE_ON_SWAP_NO_RESET\",\"activePlanGroup\":{\"1\":{\"state\":\"TRUE\",\"bet\":\"2\",\"rank\":\"2,3,4,5\"}},\"funds\":\"10,11,12,13,14\",\"monitorPeriod\":1,\"followPeriod\":1}}" ;

			System.out.println(newInfo);
			JSONObject content = JSONObject.parseObject(newInfo);
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
				return;
			}
			if (StringTool.isEmpty(odds)) {
				odds = oddsMap.get(gameCode.name());
			}

			//计算统计明细
			JSONArray detailList = new StatisticsItemController(startDay, endDay, startTime, endTime,
					drawInfoSqlMap.get(gameCode.name()), planCode, gameCode, statisticalState, moneyResetState,
					planItem, odds).statistics();

			//资金列表
			String[] funds = planItem.get("funds").toString().split(",");

			//计算统计结果
			JSONArray general = PlanStatisticsThread
					.statisticsGeneralResult(detailList, funds, DateTool.getDates(startDay, endDay), gameCode);

			JSONObject result = new JSONObject();
			//统计结果
			result.put("general", general);
			//结果颠倒
			result.put("detail", PlanStatisticsThread.upsideDown(detailList));
			//方案组标题
			result.put("groupTitle", PlanStatisticsThread.getPlanGroupTitle(planItem));

			String basePath = this.getClass().getResource("/").getPath().concat("");

//			for (int i = 0; i < 100000; i++) {
				String fileName = RandomTool.getNumLetter32().concat(".xls");
				String path = basePath.concat(fileName);
				System.out.println(path);
				ExcelTool.writeStatisticsResult(path, result);
//			}

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
}
