package com.ibm.old.v1.servlet.ibm_plan_statistics.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.entity.PlanGroupContent;
import com.ibm.old.v1.cloud.core.entity.SpliceGroupDate;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.common.StatisticsTool;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * @Description: 统计方案详情
 * @Author: Dongming
 * @Date: 2019-07-25 16:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StatisticsPlanItemController implements StatisticsController {
	private Object[] periods;
	private Map<Object, Map<String, Object>> drawInfoMap;
	private Object odds;
	private JSONObject planItem;
	private IbmStateEnum moneyResetState;
	private long accruingProfit;
	private PlanTool.Code planCode;
	private IbmGameEnum game;

	/**
	 * 存储历史方案组数据
	 */
	private Map<String, Map<String, Object>> planGroupMap = new HashMap<>();
	/**
	 * 存储投注失败需要期期滚的投注信息
	 */
	private Map<String,Map<String,Object>> periodRollMap=new HashMap<>();
	/**
	 * 存储已经翻译过的项目
	 */
	private Map<String, String> exitsMap = new HashMap<>();

	public StatisticsPlanItemController(Object[] periods, Map<Object, Map<String, Object>> drawInfoMap, Object odds,
			JSONObject planItem, IbmStateEnum moneyResetState, long accruingProfit, PlanTool.Code planCode,
			IbmGameEnum game) {
		this.periods = periods;
		this.drawInfoMap = drawInfoMap;
		this.odds = odds;
		this.planItem = planItem;
		this.moneyResetState = moneyResetState;
		this.accruingProfit = accruingProfit;
		this.planCode = planCode;
		this.game = game;
	}
	@Override public JSONArray statistics() throws Exception {
		//跟随期数
		int followPeriod = planItem.getInteger("followPeriod");
		if (followPeriod == 0) {
			followPeriod = 1;
		}
		//监控期数
		int monitorPeriod = planItem.getInteger("monitorPeriod");
		//方案组
		JSONObject activePlanGroup = planItem.getJSONObject("activePlanGroup");
		//资金列表
		String funds = planItem.getString("funds");
		//资金切换模式
		String fundSwapMode = planItem.getString("fundSwapMode");
		//期期滚模式
		String periodRollMode=null;
		if(planItem.containsKey("periodRollMode")){
			periodRollMode=planItem.getString("periodRollMode");
		}

		//每天重置资金开启时，清除之前历史数据
		if (IbmStateEnum.OPEN.name().equals(moneyResetState.name())) {
			planGroupMap.clear();
		}
		JSONArray execBetItemArray = new JSONArray();
		Map<String, Object> execBetItemMap = new HashMap<>();

		//当期盈利信息
		long theDayProfit = 0L;
		//存取连对或连错次数
		JSONObject resultObj = new JSONObject();
		for (Object period : periods) {
			//算出跟进期数
			Object basePeriod = PeriodTool.findBeforePeriod(game, period, followPeriod);
			//当前期开奖信息
			Map<String, Object> drawResult = drawInfoMap.get(period.toString());
			if (ContainerTool.isEmpty(drawResult)) {
				continue;
			}
			//循环检验方案组
			for (Map.Entry<String, Object> entry : activePlanGroup.entrySet()) {
				Map<String,Object> mergeMap = new HashMap<>(5);
				//激活组
				String activeKey = entry.getKey();

				String planGroupDesc  = String.format("第%s组", StringTool.addOne(activeKey));

				//组装数据
				JSONObject groupData = new SpliceGroupDate(JSONObject.parseObject(entry.getValue().toString()),
						basePeriod, monitorPeriod, game).splice(planCode, false);
				try {
					if (ContainerTool.isEmpty(groupData)) {
						continue;
					}
					Object fundsKey = "0";
					for (int i = 1; i <= planGroupMap.size(); i++) {
						//获取历史执行详情
						Map<String, Object> historyMap = StatisticsTool
								.findHistory(planGroupMap, PeriodTool.findBeforePeriod(game, period, i),
										groupData.toString());
						if (ContainerTool.notEmpty(historyMap)) {
							fundsKey = StatisticsTool
									.fundsKey(funds, fundSwapMode, historyMap.get("fundGroupKey").toString(),
											Boolean.parseBoolean(historyMap.get("execResult").toString()));
							break;
						}
					}
					if (StringTool.isEmpty(fundsKey)) {
						continue;
					}
					String fundsValue = StatisticsTool.fundsValue(funds, fundsKey.toString());
					//存储数据
					//资金组key,执行结果
					Map<String, Object> planGroup = new HashMap<>(2);
					planGroup.put("fundGroupKey", fundsKey);
					planGroup.put("execResult", IbmTypeEnum.FALSE.name());

					//方案组key#期数
					String planGroupKey = groupData.toString().concat("#").concat(String.valueOf(period));
					planGroupMap.put(planGroupKey, planGroup);

					//turn
					//方案组详情
					String betContent = new PlanGroupContent(planCode, game, groupData, basePeriod)
							.getBetContent(exitsMap, true);
					if (StringTool.isEmpty(betContent)) {
						continue;
					}
					//判断期期滚是否为“重复内容不投注”
					if (StringTool.notEmpty(periodRollMode) && IbmModeEnum.PERIOD_ROLL_MODE_NO_REPEAT.name()
							.equals(periodRollMode)) {
						//判断是否有期期滚投注内容
						if (ContainerTool.notEmpty(periodRollMap.get(entry.getValue().toString())) && ContainerTool
								.notEmpty(periodRollMap.get(entry.getValue().toString()))) {
							Map<String,Object> map=periodRollMap.get(entry.getValue().toString());
							//判断投注内容是否重复，重复的话当期生成的投注项不投
							for(Map.Entry<String, Object> betEmtry:map.entrySet()){
								String[] bets=betEmtry.getKey().split("_");
								if(betContent.equals(bets[1])){
									betContent=null;
									break;
								}
							}
						}
					}
					if (StringTool.isEmpty(betContent)) {
						continue;
					}
					//存储数据
					JSONArray betItemArray = new JSONArray();
					betItemArray.add(betContent);
					betItemArray.add(fundsValue);
					//结算
					//该期开奖结果
					long profit = StatisticsTool.updateResult(betItemArray, odds, drawResult);

					if (!resultObj.containsKey(activeKey)) {
						Map<String, Object> map = new HashMap<>();
						map.put("wrongNmuber", 0);
						map.put("rightNumber", 0);
						resultObj.put(activeKey, map);
					}
					Map<String, Object> keyMap = (Map<String, Object>) resultObj.get(activeKey);

					if (profit > 0) {
						betItemArray.add("赢");
						int rightNumber = (int) keyMap.get("rightNumber");
						++rightNumber;
						keyMap.put("wrongNmuber", 0);
						keyMap.put("rightNumber", rightNumber);
						betItemArray.add(rightNumber);
					} else {
						betItemArray.add("亏");
						int wrongNmuber = (int) keyMap.get("wrongNmuber");
						++wrongNmuber;
						keyMap.put("rightNumber", 0);
						keyMap.put("wrongNmuber", wrongNmuber);
						betItemArray.add(wrongNmuber);
					}
					betItemArray.add(profit);

					//判断盈亏信息
					if (betItemArray.getInteger(4) > 0) {
						planGroup.put("execResult", IbmTypeEnum.TRUE.name());
					}

					if (IbmTypeEnum.FALSE.name().equals(planGroup.get("execResult"))) {
						if (StringTool.notEmpty(periodRollMode) && funds.split(",").length > StringTool
								.addOne(fundsKey)) {
							String periodRollKey = betContent.concat("_").concat(String.valueOf(period));

							if (periodRollMap.containsKey(entry.getValue().toString())) {
								Map<String, Object> map = periodRollMap.get(entry.getValue().toString());
								map.put(periodRollKey, StringTool.addOne(fundsKey));
							} else {
								Map<String, Object> betInfoMap = new HashMap<>(1);
								betInfoMap.put(periodRollKey, StringTool.addOne(fundsKey));
								periodRollMap.put(entry.getValue().toString(), betInfoMap);
							}
						}
					}

					mergeMap.put("betContent", betItemArray.getString(0));
					mergeMap.put("fund", betItemArray.get(1));
					mergeMap.put("winOrLose", betItemArray.get(2));
					mergeMap.put("wrongNumber",betItemArray.get(3));
					mergeMap.put("profit", betItemArray.get(4));

				} finally {
					//期期滚
					if(StringTool.notEmpty(periodRollMode)){
						//添加期期滚投注内容
						savePeriodRoll(entry,drawResult,mergeMap,funds,period);
					}
					if(ContainerTool.isEmpty(mergeMap)){
						continue;
					}
					JSONArray resultArray=new JSONArray();
					resultArray.add(mergeMap.get("betContent"));
					resultArray.add(mergeMap.get("fund"));
					resultArray.add(mergeMap.get("winOrLose"));
					resultArray.add(mergeMap.get("wrongNumber"));
					resultArray.add((long)mergeMap.get("profit")/1000d);
					
					execBetItemMap.put(planGroupDesc, resultArray);
				}
			}
			//保存投注信息,返回当期盈利信息
			long betProfit = StatisticsTool
					.saveBetItem(drawResult, period, execBetItemMap, execBetItemArray, theDayProfit, accruingProfit);
			theDayProfit += betProfit;
			accruingProfit += betProfit;

			execBetItemMap.clear();
		}
		return execBetItemArray;
	}
	/**
	 * 添加期期滚投注内容
	 * @param entry		方案组
	 * @param drawResult	开奖数据
	 * @param mergeMap	合并map
	 * @param funds		资金列表
	 * @param period		当期期数
	 */
	private void savePeriodRoll(Map.Entry<String, Object> entry, Map<String, Object> drawResult,
			Map<String, Object> mergeMap, String funds, Object period) {
		//判断是否存在期期滚投注内容
		if (ContainerTool.notEmpty(periodRollMap.get(entry.getValue().toString())) && ContainerTool
				.notEmpty(periodRollMap.get(entry.getValue().toString()))) {

			Map<String, Object> map = periodRollMap.get(entry.getValue().toString());

			Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> periodRollEntry = iterator.next();
				String[] keys = periodRollEntry.getKey().split("_");
				if (period.equals(keys[1])) {
					continue;
				}
				JSONArray periodRollArray = new JSONArray();
				String fundValue = funds.split(",")[Integer
						.parseInt(periodRollEntry.getValue().toString())];
				periodRollArray.add(keys[0]);
				periodRollArray.add(fundValue);

				//开奖结果
				long profit = StatisticsTool.updateResult(periodRollArray, odds, drawResult);
				if (profit > 0) {
					periodRollArray.add("赢");
					iterator.remove();
				} else {
					periodRollArray.add("亏");
					if (funds.split(",").length > StringTool.addOne(periodRollEntry.getValue())) {
						map.put(periodRollEntry.getKey(), StringTool.addOne(periodRollEntry.getValue()));
					} else {
						iterator.remove();
					}
				}
				if (ContainerTool.isEmpty(mergeMap)) {
					mergeMap.put("betContent", periodRollArray.getString(0));
					mergeMap.put("fund", periodRollArray.getString(1));
					mergeMap.put("winOrLose", periodRollArray.getString(2));
					mergeMap.put("wrongNumber", 0);
					mergeMap.put("profit", profit);
				} else {
					mergeMap.put("betContent",
							mergeMap.get("betContent").toString().concat("_").concat(periodRollArray.getString(0)));
					mergeMap.put("fund",
							mergeMap.get("fund").toString().concat("_").concat(periodRollArray.getString(1)));
					mergeMap.put("winOrLose",
							mergeMap.get("winOrLose").toString().concat("_").concat(periodRollArray.getString(2)));
					mergeMap.put("profit", ((long) mergeMap.get("profit") + profit));
				}
			}
		}
	}
}
