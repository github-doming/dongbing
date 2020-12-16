package com.ibm.old.v1.servlet.ibm_plan_statistics.entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
/**
 * @Description: 统计结果实体类
 * @Author: Dongming
 * @Date: 2019-07-29 18:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StatisticsGeneral {
	protected static final Logger log = LogManager.getLogger(StatisticsGeneral.class);
	public static final String CONTINUOUS_FORMAT = "%s (%s %s)";
	/**
	 * 日期
	 */
	String item;

	/**
	 * 投注次数
	 */
	int betCount = 0;
	/**
	 * 赢
	 */
	int win = 0;
	/**
	 * 亏
	 */
	int lose = 0;
	/**
	 * 总投注金额
	 */
	int betSum = 0;
	/**
	 * 输赢金额
	 */
	BigDecimal profit ;

	/**
	 * 最高盈利
	 */
	double maxObtain = 0d;
	/**
	 * 最低亏损
	 */
	double maxLoss = 0d;
	/**
	 * 某期最大下注
	 */
	int periodMaxBet = 0;
	/**
	 * 某期最高盈利
	 */
	double periodMaxWin = 0;
	/**
	 * 某期最低亏损
	 */
	double periodMaxLose = 0;
	/**
	 * 最大连错
	 */
	int continuousError = 0;
	/**
	 * 前五连错
	 */
	String error;

	/**
	 * 最大连对
	 */
	int continuousRight = 0;
	/**
	 * 前五连对
	 */
	String right;
	/**
	 * 炸人次数
	 */
	int boomSum = 0;
	/**
	 * 炸
	 */
	String boom;
	public StatisticsGeneral(String item) {
		this.item = item;
		profit = new BigDecimal(0d);
		error = "";
		right = "";
		boom = "";
	}
	public static StatisticsGeneral newInstance(String item) {
		return new StatisticsGeneral(item);
	}
	/**
	 * 获取合计数组
	 *
	 * @param dayGenerals 每天的合计信息
	 * @return 合计数组
	 */
	public JSONArray getGeneralArray(List<StatisticsGeneral> dayGenerals) {
		//统计合计
		for (StatisticsGeneral general : dayGenerals) {
			betCount += general.betCount;
			win += general.win;
			lose += general.lose;
			betSum += general.betSum;
			profit = profit.add(general.profit);
			if (maxObtain < general.maxObtain) {
				maxObtain = general.maxObtain;
			}
			if (maxLoss > general.maxLoss) {
				maxLoss = general.maxLoss;
			}
			if (periodMaxBet < general.periodMaxBet) {
				periodMaxBet = general.periodMaxBet;
			}
			if (periodMaxWin < general.periodMaxWin) {
				periodMaxWin = general.periodMaxWin;
			}
			if (periodMaxLose > general.periodMaxLose) {
				periodMaxLose = general.periodMaxLose;
			}
			if (continuousError < general.continuousError) {
				continuousError = general.continuousError;
				error = general.error;
			}
			if (continuousRight < general.continuousRight) {
				continuousRight = general.continuousRight;
				right = general.right;
			}
			boomSum += general.boomSum;


		}
		dayGenerals.add(this);
		JSONArray generalResult = new JSONArray();
		JSONArray dayGeneral;
		//放入结果
		for (StatisticsGeneral general : dayGenerals) {
			dayGeneral = new JSONArray();
			dayGeneral.add(general.item);
			dayGeneral.add(general.betCount);
			dayGeneral.add(general.win);
			dayGeneral.add(general.lose);
			dayGeneral.add(general.betSum);
			dayGeneral.add(general.profit);
			dayGeneral.add(general.maxObtain);
			dayGeneral.add(general.maxLoss);
			dayGeneral.add(general.periodMaxBet);
			dayGeneral.add(general.periodMaxWin);
			dayGeneral.add(general.periodMaxLose);
			dayGeneral.add(general.continuousError);
			dayGeneral.add(general.error);
			dayGeneral.add(general.continuousRight);
			dayGeneral.add(general.right);
			dayGeneral.add(general.boomSum);
			dayGeneral.add(general.boom);
			generalResult.add(dayGeneral);
		}
		return generalResult;
	}

	/**
	 * 解析每天的投注信息
	 *
	 * @param detailResults 返回详情信息
	 * @param gameCode      游戏code
	 * @param day           解析天
	 * @param maxFunds      最大资金
	 */
	public void analysis(JSONArray detailResults, IbmGameEnum gameCode, String day, int maxFunds)
			throws ParseException {
		Map<Object, Map<String, Object>> continuousErrorMap = new HashedMap();
		Map<Object, Map<String, Object>> continuousRightMap = new HashedMap();
		item = day;
		for (int i = 0; i < detailResults.size(); i++) {
			JSONArray periodArray = detailResults.getJSONArray(i);
			//投注时间
			Date date = periodArray.getDate(0);

			String betDay = DateTool.getDate(date);
			String betTime = DateTool.getTime(date);

			if (!day.equals(betDay)) {
				continue;
			}
			Object period = periodArray.get(1);

			//单期投注信息
			JSONArray betObjs = periodArray.getJSONArray(4);
			//投注次数
			betCount += betObjs.size();
			Object lastPeriod = PeriodTool.findLastPeriod(gameCode, period);
			continuous(continuousErrorMap, continuousRightMap, maxFunds, betTime, period, betObjs, lastPeriod);

			int periodBetFunds = periodArray.getInteger(5);
			//总投注金额
			betSum += periodBetFunds;
			BigDecimal periodProfit = periodArray.getBigDecimal(6);
			//输赢金额
			profit = profit.add(periodProfit);
			//某期最大下注
			if (periodMaxBet < periodBetFunds) {
				periodMaxBet = periodBetFunds;
			}

			double dayProfit = periodArray.getDouble(7);

			if (periodProfit.doubleValue() > 0) {
				//某期最高盈利
				if (periodMaxWin < periodProfit.doubleValue()) {
					periodMaxWin = periodProfit.doubleValue();
				}
				//最高盈利
				if (maxObtain < dayProfit) {
					maxObtain = dayProfit;
				}
			} else {
				//某期最低亏损
				if (periodMaxLose > periodProfit.doubleValue()) {
					periodMaxLose = periodProfit.doubleValue();
				}
				//最低亏损
				if (maxLoss > dayProfit) {
					maxLoss = dayProfit;
				}
			}
		}
		analysisContinuous(continuousRightMap, false);
		analysisContinuous(continuousRightMap, true);
		if (boomSum > 5) {
			boom = "炸人次数大于5次";
		}
	}

	/**
	 * 获取连续信息
	 *
	 * @param continuousErrorMap 连续错误集合
	 * @param continuousRightMap 连续正确集合
	 * @param maxFunds           最大资金
	 * @param betTime            投注时间
	 * @param period             其实
	 * @param betObjs            投注对象信息
	 * @param lastPeriod         上一期期数
	 */
	private void continuous(Map<Object, Map<String, Object>> continuousErrorMap,
			Map<Object, Map<String, Object>> continuousRightMap, int maxFunds, String betTime, Object period,
			JSONArray betObjs, Object lastPeriod) {
		for (int j = 0; j < betObjs.size(); j++) {
			JSONObject betObj = betObjs.getJSONObject(j);
			//投注项
			JSONArray bets = betObj.getJSONArray("item");
			String group = betObj.getString("group");
			//投注金额
			String[] fundsStr=bets.getString(1).split("_");
			int funds=0;
			for(String str:fundsStr){
				funds=funds+Integer.parseInt(str);
			}

			//投注盈亏
			double betProfit = bets.getDouble(4);

			Integer continuous = bets.getInteger(3);

			String lastKey = lastPeriod.toString().concat("#").concat(group);
			String key = period.toString().concat("#").concat(group);

			String info = String.format(CONTINUOUS_FORMAT, period, group, continuous);
			if (betProfit > 0) {
				//投注赢次数
				++win;
				//连续信息
				Map<String, Object> continuousInfo;
				if (continuousRightMap.containsKey(lastKey)) {
					continuousInfo = continuousRightMap.remove(lastKey);
				} else {
					continuousInfo = new HashMap<>(4);
				}
				continuousInfo.put("continuous", continuous);
				continuousInfo.put("info",info );
				continuousRightMap.put(key, continuousInfo);
			} else {
				//投注亏次数
				++lose;
				Map<String, Object> continuousInfo;
				if (continuousErrorMap.containsKey(lastPeriod)) {
					continuousInfo = continuousErrorMap.remove(lastPeriod);
				} else {
					continuousInfo = new HashMap<>(4);
				}
				continuousInfo.put("continuous", continuous);
				continuousInfo.put("info",info);
				continuousErrorMap.put(key, continuousInfo);
				//炸
				if (funds - maxFunds == 0) {
					boomSum++;
					boom = boom.concat(String.format(CONTINUOUS_FORMAT, period, betTime, group)).concat(";");
				}
			}
		}
	}
	/**
	 * 解析连续信息
	 *
	 * @param continuousMap  连续信息集合
	 * @param continuousType 连续类型
	 */
	private void analysisContinuous(Map<Object, Map<String, Object>> continuousMap, boolean continuousType) {
		if (ContainerTool.notEmpty(continuousMap)) {
			List<Map<String, Object>> continuousInfos = new ArrayList<>(continuousMap.values());
			if (continuousInfos.size() > 5) {
				sortContinuous(continuousInfos);
			}
			for (int i = 0, size = continuousInfos.size(); i < 5 && i < size; i++) {
				Map<String, Object> info = continuousInfos.get(i);
				int continuous = (int) info.get("continuous");
				if (continuousType) {
					if (continuousRight < continuous) {
						continuousRight = continuous;
					}
					right = right.concat(info.get("info").toString()).concat(";");
				} else {
					if (continuousError < continuous) {
						continuousError = continuous;
					}
					error = error.concat(info.get("info").toString()).concat(";");
				}
			}
		}
	}

	/**
	 * 排序连续列表
	 *
	 * @param continuousInfos 连续列表
	 */
	private void sortContinuous(List<Map<String, Object>> continuousInfos) {
		continuousInfos.sort((o1, o2) -> {
			int num1 = (Integer) o1.get("continuous");
			int num2 = (Integer) o2.get("continuous");
			return  num2 - num1;
		});
	}
}
