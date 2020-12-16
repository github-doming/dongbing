package com.ibm.old.v1.servlet.ibm_plan_statistics.common;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 历史统计工具类
 * @Author: Dongming
 * @Date: 2019-06-10 16:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StatisticsTool {

	/**
	 * 获取开奖信息sql
	 *
	 * @return 开奖信息sql
	 */
	public static Map<String, String> getDrawInfoSql() {
		Map<String, String> drawInfoSql = new HashMap<>(5);
		for (IbmGameEnum game : IbmGameEnum.values()) {
			String sql = getDrawInfoSql(game);
			if (StringTool.notEmpty(sql)) {
				drawInfoSql.put(game.name(), sql);
			}
		}
		return drawInfoSql;
	}

	/**
	 * 根据游戏类型获取开奖信息sql
	 *
	 * @param game 游戏类型
	 * @return 开奖信息sql
	 */
	private static String getDrawInfoSql(IbmGameEnum game) {
		switch (game) {
			case PK10:
				return getDrawInfoBallSql("ibm_rep_draw_pk10");
			case XYFT:
				return getDrawInfoBallSql("ibm_rep_draw_xyft");
			default:
		}
		return null;
	}

	/**
	 * 球类开奖信息sql语句
	 *
	 * @param tableName 表名
	 * @return 奖信息sql语句
	 */
	private static String getDrawInfoBallSql(String tableName) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT PERIOD_,DRAW_TIME_,");
		for (int i = 1; i <= 10; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_NUMBER_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_SIZE_),");
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_SINGLE_),");
		}
		for (int i = 1; i <= 5; i++) {
			sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[i]).append("|',").append("P").append(i)
					.append("_DRAGON_),");
		}
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',")
				.append("CHAMPION_SUM_NUNMBER_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',").append("CHAMPION_SUM_SIZE_),");
		sqlBuilder.append("CONCAT('").append(StringTool.CHINESE_BALL_RANK[0]).append("|',").append("CHAMPION_SUM_SINGLE_),");
		sqlBuilder.append("DRAW_NUMBER_ FROM ").append(tableName).append(" WHERE STATE_ = ? AND PERIOD_ in( ");
		return sqlBuilder.toString();
	}

	/**
	 * 获取历史信息
	 *
	 * @param planGroupMap 历史信息map
	 * @param period       期数
	 * @param groupData    方案组数据
	 * @return 历史信息
	 */
	public static Map<String, Object> findHistory(Map<String, Map<String, Object>> planGroupMap, Object period,
			String groupData) {
		String key = groupData.concat("#").concat(String.valueOf(period));
		if (planGroupMap.containsKey(key)) {
			return planGroupMap.get(key);
		}
		return null;
	}
	/**
	 * 获取开始时间
	 *
	 * @param statisticalState 统计状态
	 * @param startTime        开始时间
	 * @param date             时间天
	 * @param index            索引
	 * @return 开始时间
	 */
	public static Date getStartDate(IbmGameEnum game, IbmStateEnum statisticalState, String startTime, String date,
			int index) throws Exception {
		if (statisticalState == null) {
			switch (game) {
				case XYFT:
					return DateTool.getMinute(date.concat(" ").concat("13:04"));
				case PK10:
					return DateTool.getMinute(date.concat(" ").concat("09:10"));
				default:
					throw new Exception("未知的游戏格式");
			}
		}
		switch (statisticalState) {
			case OPEN:
				return DateTool.getMinute(date.concat(" ").concat(startTime));
			case CLOSE:
				if (index == 0) {
					return DateTool.getMinute(date.concat(" ").concat(startTime));
				}
			default:
				switch (game) {
					case XYFT:
						return DateTool.getMinute(date.concat(" ").concat("13:04"));
					case PK10:
						return DateTool.getMinute(date.concat(" ").concat("09:10"));
					default:
						throw new Exception("未知的游戏格式");
				}
		}
	}

	/**
	 * 获取结束时间
	 *
	 * @param date    时间天
	 * @param endTime 结束时间
	 * @param index   索引
	 * @param len     时间长度
	 * @return 结束时间
	 */
	public static Date getEndDate(IbmGameEnum game, IbmStateEnum statisticalState, String endTime, String date,
			int index, int len) throws Exception {
		if (statisticalState == null) {
			switch (game) {
				case XYFT:
					Date endDate = DateTool.getMinute(date.concat(" ").concat("03:59"));
					return DateTool.getAfterDays(endDate, 1);
				case PK10:
					return DateTool.getMinute(date.concat(" ").concat("23:30"));
				default:
					throw new Exception("未知的游戏格式");
			}
		}
		switch (statisticalState) {
			case OPEN:
				return DateTool.getMinute(date.concat(" ").concat(endTime));
			case CLOSE:
				if (index == len - 1) {
					return DateTool.getMinute(date.concat(" ").concat(endTime));
				}
			default:
				switch (game) {
					case XYFT:
						Date endDate = DateTool.getMinute(date.concat(" ").concat("03:59"));
						return DateTool.getAfterDays(endDate, 1);
					case PK10:
						return DateTool.getMinute(date.concat(" ").concat("23:30"));
					default:
						throw new Exception("未知的游戏格式");
				}
		}
	}

	/**
	 * 获取 资金组key<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList    资金列表
	 * @param fundSwapMode 资金切换方式
	 * @param lastFundKey  上一次的资金组key
	 * @param execResult   执行结果
	 * @return 资金组key
	 */
	public static Object fundsKey(String fundsList, String fundSwapMode, String lastFundKey, boolean execResult) {
		String fundsKey = null;
		if (StringTool.notEmpty(fundsList)) {
			int fundsLen = fundsList.split(",").length;
			fundsKey = IbmModeEnum.fundSwap(fundSwapMode, lastFundKey, execResult, fundsLen);
			if (StringTool.isEmpty(fundsKey)) {
				return null;
			} else if (fundsLen <= Integer.parseInt(fundsKey)) {
				//炸了就重新开始
				return "0";
			}
		}
		return fundsKey;
	}
	/**
	 * 获取 资金组value<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList 资金列表
	 * @param fundKey   资金组key
	 * @return 资金组value
	 */
	public static String fundsValue(String fundsList, String fundKey) {
		if (StringTool.notEmpty(fundsList)) {
			if (fundsList.split(",").length < Integer.parseInt(fundKey)) {
				return fundsList.split(",")[0];
			}
			return fundsList.split(",")[Integer.parseInt(fundKey)];
		}
		return null;
	}

	/**
	 * pk10,xyft结算
	 *
	 * @param betItemArray 投注信息
	 * @param odds         赔率信息
	 * @param drawResult   开奖信息
	 */
	public static long updateResult(JSONArray betItemArray, Object odds, Map<String, Object> drawResult) {
		long profit = 0L;
		long selfOdds = 0;
		Map<String, Object> oddsMap = null;
		if (odds instanceof Map) {
			oddsMap = (Map<String, Object>) odds;
		} else {
			selfOdds = NumberTool.longValueT(odds);
		}

		Collection<Object> drawItems = (Collection<Object>) drawResult.get("DRAW_ITEMS_");
		String drawItem = StringUtils.join(drawItems, ",");

		String betContent = betItemArray.get(0).toString();
		int funds = betItemArray.getInteger(1);
		String[] betItems = betContent.split("#");
		for (String betItem : betItems) {
			long oddsNum = oddsMap == null ? selfOdds : NumberTool.longValueT(oddsMap.get(betItem));
			if (oddsNum == 0) {
				continue;
			}
			if (drawItem.concat(",").contains(betItem.concat(","))) {
				profit += funds * (oddsNum - 1000);
			} else {
				profit -= (funds * 1000);
			}
		}
		return profit;
	}
	/**
	 * 保存投注信息
	 *
	 * @param drawResult       当期开奖信息
	 * @param period           期数
	 * @param execBetItemMap   投注信息
	 * @param execBetItemArray 存储容器
	 * @param theDayProfit     当天盈利信息
	 * @param accruingProfit   累计盈利信息
	 * @return 投注信息
	 */
	public static long saveBetItem(Map<String, Object> drawResult, Object period, Map<String, Object> execBetItemMap,
			JSONArray execBetItemArray, long theDayProfit, long accruingProfit) throws ParseException {
		//开奖结果
		String[] drawNumbers = drawResult.get("DRAW_NUMBER_").toString().split(",");

		JSONArray execBetItem = new JSONArray();
		execBetItem.add(DateTool.getStr(drawResult.get("DRAW_TIME_")));
		execBetItem.add(period.toString());
		execBetItem.add(drawResult.get("DRAW_NUMBER_"));
		execBetItem.add(String.valueOf(Integer.parseInt(drawNumbers[0]) + Integer.parseInt(drawNumbers[1])));

		int betFunds = 0;
		long betProfit = 0L;
		JSONArray jsonArray;
		JSONArray betItems = new JSONArray();

		for (Map.Entry entry : execBetItemMap.entrySet()) {
			JSONObject betItem = new JSONObject();
			betItem.put("group", entry.getKey());
			betItem.put("item", entry.getValue());
			betItems.add(betItem);
			jsonArray = JSONArray.parseArray(entry.getValue().toString());
			if (ContainerTool.isEmpty(jsonArray)) {
				continue;
			}
			long profit = NumberTool.longValueT(jsonArray.getDouble(4));
			String[] berContent=jsonArray.getString(0).split("_");
			String[] funds = jsonArray.getString(1).split("_");
			for(int i=0;i<funds.length;i++){
				betFunds = betFunds + Integer.parseInt(funds[i]) *berContent[i].split("#").length;
			}

			betProfit = betProfit + profit;
			jsonArray.clear();
		}
		execBetItem.add(betItems);

		theDayProfit += betProfit;
		accruingProfit += betProfit;
		execBetItem.add(betFunds);
		execBetItem.add(betProfit / 1000d);
		execBetItem.add(theDayProfit / 1000d);
		execBetItem.add(accruingProfit / 1000d);

		execBetItemArray.add(execBetItem);
		return betProfit;
	}

}
