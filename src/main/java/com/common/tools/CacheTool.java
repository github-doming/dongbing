package com.common.tools;

import c.a.tools.jdbc.IJdbcTool;
import com.common.enums.TypeEnum;
import com.common.game.Period;
import com.common.service.CacheService;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import com.google.common.collect.ImmutableList;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.container.LruMap;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 缓存信息工具类
 * @Author: null
 * @Date: 2020-07-10 16:04
 * @Version: v1.0
 */
public class CacheTool {
	private static Boolean isClient;

	public static void setModeInfo(String module) {
		isClient = "CLIENT".equals(module);
	}

	public static boolean isClient() {
		isClient = isClient != null ? isClient : false;
		return isClient;
	}

	public static void cache() throws SQLException {
		GameOdds.CACHE.cache();
		GameHistoryDraw.CACHE.cache();
	}

	/**
	 * 获取赔率
	 *
	 * @param gameCode 游戏编码
	 * @return 赔率
	 */
	public static Map<String, Integer> odds(BaseGameUtil.Code gameCode) throws SQLException {
		return GameOdds.CACHE.odds(gameCode);
	}

	/**
	 * 保存历史开奖
	 *
	 * @param gameCode   游戏编码
	 * @param drawType   开奖类型
	 * @param period     期数
	 * @param drawNumber 开奖信息
	 */
	public static void saveDraw(BaseGameUtil.Code gameCode, String drawType, String drawNumber, Object period) {
		GameHistoryDraw.CACHE.saveDraw(gameCode, drawType, period, drawNumber);
	}

	/**
	 * 获取历史开奖
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 开奖类型
	 * @param period   期数
	 * @return 开奖信息
	 */
	public static String getDraw(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
		return GameHistoryDraw.CACHE.getDraw(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 保存冷热数据
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param drawNumber   开奖号码
	 * @param period       期数
	 * @throws Exception
	 */
	public static void saveHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, String drawNumber, Object period) throws Exception {
		GameHistoryDraw.CACHE.saveNumberHotAndCold(gameCode, handicapCode, drawType, period, drawNumber);
		GameHistoryDraw.CACHE.saveLocationHotAndCold(gameCode, handicapCode, drawType, period, drawNumber);
		GameHistoryDraw.CACHE.saveHotAndCold(gameCode, handicapCode, drawType, period, drawNumber);
	}


	/**
	 * 保存号码出现的间隔期数
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param drawNumber   开奖号码
	 * @param period       期数
	 */
	public static void saveLastInterval(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, String drawNumber, Object period) throws Exception {
		GameHistoryDraw.CACHE.saveLastInterval(gameCode, handicapCode, drawType, period, drawNumber);
	}

	/**
	 * 是否存在该开奖信息
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 开奖类型
	 * @param period   期数
	 */
	public static boolean isExist(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws SQLException {
		return GameHistoryDraw.CACHE.isExist(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 获取冷热排名 --NORMAL 正常排名
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param period       期数
	 * @return
	 */
	public static String[][] getHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
		return GameHistoryDraw.CACHE.getHotAndCold(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 获取冷热排名 -号码追踪 号码排名 NUMBER
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param period       期数
	 * @return
	 */
	public static String[][] getNumberHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
		return GameHistoryDraw.CACHE.getNumberHotAndCold(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 获取冷热排名 -号码追踪 位置排名 LOCATION
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param period       期数
	 * @return
	 */
	public static String[][] getLocationHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
		return GameHistoryDraw.CACHE.getLocationHotAndCold(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 获取号码间隔次序
	 *
	 * @param gameCode     游戏编码
	 * @param handicapCode 盘口编码
	 * @param drawType     开奖类型
	 * @param period       期数
	 * @return
	 */
	public static int[][] getLastInterval(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
		return GameHistoryDraw.CACHE.getLastInterval(gameCode, handicapCode, drawType, period);
	}

	/**
	 * 获取唯一键
	 *
	 * @param type   类型
	 * @param module 模块
	 * @return 唯一键
	 */
	public static String onlyCode(TypeEnum type, String module) {
		String code;
		switch (type) {
			case REMIND:
				code = "R_" + module;
				break;
			case ANNOUNCE:
				code = "A_" + module;
				break;
			case MESSAGE:
				code = "M_" + module;
				break;
			default:
				throw new RuntimeException("错误的消息类型：" + type.name());
		}
		return RandomTool.onlyCode(code);
	}

	private static class GameOdds {
		private static final GameOdds CACHE = new GameOdds();
		private Map<BaseGameUtil.Code, Map<String, Integer>> oddsInfo;

		private GameOdds() {
			oddsInfo = new HashMap<>();
		}

		public void cache() throws SQLException {
			//赔率信息
			List<Map<String, Object>> oddsInfos;
			TransactionsBase transactions = CurrentTransaction.getDataBase();
			if (transactions == null) {
				transactions = new TransactionsBase();
			}
			IJdbcTool holdJdbc = transactions.transactionStart("cloud");
			try {
				oddsInfos = new CacheService().listOddsInfo();
			} finally {
				transactions.transactionFinish(holdJdbc);
			}
			if (ContainerTool.isEmpty(oddsInfos)) {
				return;
			}
			Map<String, Map<String, Integer>> info = new HashMap<>(8);
			for (Map<String, Object> oddsInfo : oddsInfos) {
				String gameCode = oddsInfo.get("GAME_CODE_").toString();
				Integer oddsTh = NumberTool.getInteger(oddsInfo, "ODDS_T_", 0);
				if (info.containsKey(gameCode)) {
					info.get(gameCode).put(oddsInfo.get("GAME_PLAY_NAME_").toString(), oddsTh);
				} else {
					Map<String, Integer> data = new HashMap<>(50);
					data.put(oddsInfo.get("GAME_PLAY_NAME_").toString(), oddsTh);
					info.put(gameCode, data);
				}
			}
			info.forEach((key, val) -> oddsInfo.put(BaseGameUtil.Code.valueOf(key), val));
		}

		/**
		 * 获取某个游戏的赔率
		 *
		 * @param gameCode 游戏编码
		 * @return 赔率集合
		 */
		public Map<String, Integer> odds(BaseGameUtil.Code gameCode) throws SQLException {
			if (!oddsInfo.containsKey(gameCode)) {
				oddsInfo.put(gameCode, reloadOdds(gameCode));
			}
			return oddsInfo.get(gameCode);
		}

		/**
		 * 重载赔率信息
		 *
		 * @param gameCode 游戏编码
		 * @return 赔率信息
		 */
		private Map<String, Integer> reloadOdds(BaseGameUtil.Code gameCode) throws SQLException {
			TransactionsBase transactions = CurrentTransaction.getDataBase();
			if (transactions == null) {
				transactions = new TransactionsBase();
			}
			IJdbcTool holdJdbc = transactions.transactionStart("cloud");
			try {
				return new CacheService().mapOddsByGame(gameCode);
			} finally {
				transactions.transactionFinish(holdJdbc);
			}
		}
	}

	private static class GameHistoryDraw {
		private static final GameHistoryDraw CACHE = new GameHistoryDraw();
		private Map<BaseGameUtil.Code, Map<String, LruMap<Object, String>>> historyDrawInfo;
		private Map<BaseGameUtil.Code, Map<String, LruMap<Object, String[][]>>> hotAndCold;
		private Map<BaseGameUtil.Code, Map<String, LruMap<Object, String[][]>>> locationHotAndCold;
		private Map<BaseGameUtil.Code, Map<String, LruMap<Object, String[][]>>> numberHotAndCold;
		private Map<BaseGameUtil.Code, Map<String, LruMap<Object, int[][]>>> lastInterval;

		private GameHistoryDraw() {
			historyDrawInfo = new HashMap<>(8);
			hotAndCold = new HashMap<>(8);
			locationHotAndCold = new HashMap<>(10);
			numberHotAndCold = new HashMap<>(10);
			lastInterval = new HashMap<>(8);
		}

		public void cache() {
		}

		/**
		 * 保存开奖信息
		 *
		 * @param gameCode   游戏编码
		 * @param drawType   开奖类型
		 * @param period     期数
		 * @param drawNumber 开奖信息
		 */
		private void saveDraw(BaseGameUtil.Code gameCode, String drawType, Object period, String drawNumber) {
			Map<String, LruMap<Object, String>> gameTypeMap;
			//类型集合
			if (historyDrawInfo.containsKey(gameCode)) {
				gameTypeMap = historyDrawInfo.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				historyDrawInfo.put(gameCode, gameTypeMap);
			}
			//开奖集合
			if (gameTypeMap.containsKey(drawType)) {
				gameTypeMap.get(drawType).put(period, drawNumber);
			} else {
				LruMap<Object, String> drawMap = new LruMap<>(20);
				drawMap.put(period, drawNumber);
				gameTypeMap.put(drawType, drawMap);
			}
		}
		/**
		 * 获取开奖信息
		 *
		 * @param gameCode 游戏编码
		 * @param drawType 开奖类型
		 * @param period   期数
		 * @return 开奖信息
		 */
		public String getDraw(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
			CacheService cacheService = new CacheService();
			String drawTableName = gameCode.getGameFactory().period(handicapCode).getDrawTableName();
			if (isClient()) {
				drawTableName = "client_rep_draw";
			}
			//类型集合
			Map<String, LruMap<Object, String>> gameTypeMap;
			if (historyDrawInfo.containsKey(gameCode)) {
				gameTypeMap = historyDrawInfo.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				historyDrawInfo.put(gameCode, gameTypeMap);
			}
			LruMap<Object, String> drawMap;
			if (gameTypeMap.containsKey(drawType)) {
				drawMap = gameTypeMap.get(drawType);
			} else {
				drawMap = new LruMap<>(20);
				gameTypeMap.put(drawType, drawMap);
			}
			if (!drawMap.containsKey(period)) {
				String drawNum = cacheService.findDrawNumber(gameCode.name(), drawType, period, drawTableName, isClient);
				if (StringTool.isEmpty(drawNum)) {
					TransactionsBase transactions = CurrentTransaction.getDataBase();
					if (transactions == null) {
						transactions = new TransactionsBase();
					}
					IJdbcTool holdJdbc = transactions.transactionStart("cloud");
					try {
						drawNum = new CacheService().getDraw(gameCode, drawType, period);
					} finally {
						transactions.transactionFinish(holdJdbc);
					}
					if (StringTool.isEmpty(drawNum)) {
						return null;
					}
				}
				drawMap.put(period, drawNum);
			}
			return drawMap.get(period);
		}

		/**
		 * 是否存在开奖信息
		 *
		 * @param gameCode 游戏编码
		 * @param drawType 开奖类型
		 * @param period   期数
		 * @return 开奖信息
		 */
		public boolean isExist(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
							   Object period) throws SQLException {
			String drawTableName = gameCode.getGameFactory().period(handicapCode).getDrawTableName();
			if (isClient()) {
				drawTableName = "client_rep_draw";
			}
			//类型集合
			if (!historyDrawInfo.containsKey(gameCode)) {
				return false;
			}
			Map<String, LruMap<Object, String>> gameTypeMap = historyDrawInfo.get(gameCode);
			if (!gameTypeMap.containsKey(drawType)) {
				return false;
			}
			LruMap<Object, String> drawMap = gameTypeMap.get(drawType);
			if (!drawMap.containsKey(period)) {
				String drawNum = new CacheService().findDrawNumber(gameCode.name(), drawType, period, drawTableName, isClient);
				if (StringTool.isEmpty(drawNum)) {
					return false;
				}
				drawMap.put(period, drawNum);
			}
			return true;
		}

		/**
		 * 号码追踪 追踪号码位子冷热排名
		 *
		 * @param gameCode     游戏编码
		 * @param handicapCode 盘口编码
		 * @param drawType     开奖类型
		 * @param period       期数
		 * @return
		 */
		private String[][] getLocationHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
												 Object period) throws Exception {
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (locationHotAndCold.containsKey(gameCode)) {
				gameTypeMap = locationHotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				locationHotAndCold.put(gameCode, gameTypeMap);
			}
			LruMap<Object, String[][]> periodMap;
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
			} else {
				periodMap = new LruMap<>(20);
				gameTypeMap.put(drawType, periodMap);
			}
			if (!periodMap.containsKey(period)) {
				String drawNumber = getDraw(gameCode, handicapCode, drawType, period);
				if (StringTool.isEmpty(drawNumber)) {
					return null;
				}
				String[][] hotAndColdRank = hotAndCold(gameCode, handicapCode, drawType, drawNumber.split(","),
						period, "LOCATION");
				if (ContainerTool.isEmpty(hotAndColdRank)) {
					return null;
				}
				periodMap.put(period, hotAndColdRank);
			}
			return periodMap.get(period);
		}

		/**
		 * 号码追踪 追踪号码冷热排名
		 *
		 * @param gameCode     游戏编码
		 * @param handicapCode 盘口编码
		 * @param drawType     开奖类型
		 * @param period       期数
		 * @return
		 */
		private String[][] getNumberHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
											   Object period) throws Exception {
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (numberHotAndCold.containsKey(gameCode)) {
				gameTypeMap = numberHotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				numberHotAndCold.put(gameCode, gameTypeMap);
			}
			LruMap<Object, String[][]> periodMap;
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
			} else {
				periodMap = new LruMap<>(20);
				gameTypeMap.put(drawType, periodMap);
			}
			if (!periodMap.containsKey(period)) {
				String drawNumber = getDraw(gameCode, handicapCode, drawType, period);
				if (StringTool.isEmpty(drawNumber)) {
					return null;
				}
				String[][] hotAndColdRank = hotAndCold(gameCode, handicapCode, drawType, drawNumber.split(","),
						period, "NUMBER");
				if (ContainerTool.isEmpty(hotAndColdRank)) {
					return null;
				}
				periodMap.put(period, hotAndColdRank);
			}
			return periodMap.get(period);
		}
		/**
		 * 获取冷热排名
		 *
		 * @param gameCode     游戏编码
		 * @param handicapCode 盘口编码
		 * @param drawType     开奖类型
		 * @param period       期数
		 * @return
		 */
		private String[][] getHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
										 Object period) throws Exception {
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (hotAndCold.containsKey(gameCode)) {
				gameTypeMap = hotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				hotAndCold.put(gameCode, gameTypeMap);
			}
			LruMap<Object, String[][]> periodMap;
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
			} else {
				periodMap = new LruMap<>(20);
				gameTypeMap.put(drawType, periodMap);
			}
			if (!periodMap.containsKey(period)) {
				String drawNumber = getDraw(gameCode, handicapCode, drawType, period);
				if (StringTool.isEmpty(drawNumber)) {
					return null;
				}
				String[][] hotAndColdRank = hotAndCold(gameCode, handicapCode, drawType, drawNumber.split(","),
						period, "NORMAL");
				if (ContainerTool.isEmpty(hotAndColdRank)) {
					return null;
				}
				periodMap.put(period, hotAndColdRank);
			}
			return periodMap.get(period);
		}

		private int[][] getLastInterval(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period) throws Exception {
			Map<String, LruMap<Object, int[][]>> gameTypeMap;
			//类型集合
			if (lastInterval.containsKey(gameCode)) {
				gameTypeMap = lastInterval.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				lastInterval.put(gameCode, gameTypeMap);
			}
			LruMap<Object, int[][]> periodMap;
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
			} else {
				periodMap = new LruMap<>(20);
				gameTypeMap.put(drawType, periodMap);
			}
			if (!periodMap.containsKey(period)) {
				String drawNumber = getDraw(gameCode, handicapCode, drawType, period);
				if (StringTool.isEmpty(drawNumber)) {
					return null;
				}
				Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
				int[][] lastInterval = lastInterval(gameCode, handicapCode, drawType, drawNumber.split(","), period, periodOption);
				if (ContainerTool.isEmpty(lastInterval)) {
					return null;
				}
				periodMap.put(period, lastInterval);
			}
			return periodMap.get(period);
		}

		private void saveLastInterval(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period, String drawNumber) throws Exception {
			//后期加入GXKLSF需要修改
			switch (gameCode) {
				case GXKLSF:
					return;
				default:
					break;
			}
			Map<String, LruMap<Object, int[][]>> gameTypeMap;
			//类型集合
			if (lastInterval.containsKey(gameCode)) {
				gameTypeMap = lastInterval.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				lastInterval.put(gameCode, gameTypeMap);
			}
			int len = 10;
			int dif = 1;
			switch (gameCode) {
				case XYNC:
				case GDKLC:
					len = 20;
					break;
				case CQSSC:
				case JSSSC:
				case COUNTRY_SSC:
				case SELF_SSC_5:
					dif = 0;
					break;
				default:
					break;
			}
			String[] recentDraw = drawNumber.split(",");
			Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
			int[][] interval;
			LruMap<Object, int[][]> periodMap;
			//开奖集合
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
				//上一期期数间隔
				int[][] lotteryLastInterval = periodMap.get(periodOption.findBeforePeriod(period, 1));
				if (ContainerTool.isEmpty(lotteryLastInterval)) {
					interval = lastInterval(gameCode, handicapCode, drawType, recentDraw, period, periodOption);
				} else {
					interval = new int[recentDraw.length][len];
					ArrayCopyTool.rankArrayCopy(interval, lotteryLastInterval, recentDraw, dif);
				}
			} else {
				//无历史冷热排名信息
				periodMap = new LruMap<>(64);
				gameTypeMap.put(drawType, periodMap);
				interval = lastInterval(gameCode, handicapCode, drawType, recentDraw, period, periodOption);
			}
			if (ContainerTool.isEmpty(interval)) {
				return;
			}
			periodMap.put(period, interval);
		}

		private int[][] lastInterval(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode,
									 String drawType, String[] recentDraw, Object period, Period<?> periodOption) throws Exception {
			//获取历史100条开奖信息
			int[][] interval;
			int dif = 1;
			switch (gameCode) {
				case XYFT:
				case JS10:
				case PK10:
				case SELF_10_2:
				case COUNTRY_10:
					interval = new int[recentDraw.length][10];
					break;
				case JSSSC:
				case COUNTRY_SSC:
				case SELF_SSC_5:
				case CQSSC:
					dif = 0;
					interval = new int[recentDraw.length][10];
					break;
				case GDKLC:
				case XYNC:
					interval = new int[recentDraw.length][20];
					break;
				default:
					return null;
			}
			Map<String, Object> drawInfos;
			if (isClient()) {
				drawInfos = getBatchDraw(gameCode, drawType);
			} else {
				String drawTypeName = "GAME_DRAW_TYPE_";
				String drawTableName = gameCode.getGameFactory().period(handicapCode).getDrawTableName();
				drawInfos = new CacheService().getBatchDraw(drawTableName, drawType, drawTypeName);
			}
			if (ContainerTool.isEmpty(drawInfos)) {
				return null;
			}
			for (int j = 0; j < drawInfos.size(); j++) {
				//历史期数
				Object lotteryPeriod = periodOption.findBeforePeriod(period, j + 1);
				//历史开奖
				Object lotteryDraw = drawInfos.get(lotteryPeriod.toString());
				if (StringTool.isEmpty(lotteryDraw)) {
					continue;
				}
				String[] lotteryDraws = lotteryDraw.toString().split(",");

				for (int h = 0, len = lotteryDraws.length; h < len; h++) {
					String history = lotteryDraws[h];
					if (interval[h][Integer.parseInt(history) - dif] == 0) {
						interval[h][Integer.parseInt(history) - dif] = j + 1;
					}
				}
			}
			return interval;
		}


		/**
		 * 正常冷热数据
		 */
		private void saveHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
									Object period, String drawNumber) throws Exception {
			//后期加入GXKLSF需要修改
			switch (gameCode) {
				case GXKLSF:
					return;
				default:
					break;
			}
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (hotAndCold.containsKey(gameCode)) {
				gameTypeMap = hotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				hotAndCold.put(gameCode, gameTypeMap);
			}
			String[] recentDraw = drawNumber.split(",");
			int len;
			switch (gameCode) {
				case XYNC:
				case GDKLC:
					len = 20;
					break;
				default:
					len = 10;
					break;
			}
			getHotAndCold(gameCode, handicapCode, drawType, period, "NORMAL", gameTypeMap, recentDraw, len);
		}

		/**
		 * 追踪号码 开奖位置 冷热数据
		 */
		private void saveLocationHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
											Object period, String drawNumber) throws Exception {
			//非竞速类游戏return
			switch (gameCode) {
				case SELF_10_2:
				case COUNTRY_10:
				case SELF_10_5:
				case XYFT:
				case JS10:
				case PK10:
					break;
				default:
					return;
			}
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (locationHotAndCold.containsKey(gameCode)) {
				gameTypeMap = locationHotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				locationHotAndCold.put(gameCode, gameTypeMap);
			}
			String[] recentDraw = drawNumber.split(",");

			getHotAndCold(gameCode, handicapCode, drawType, period, "LOCATION", gameTypeMap, recentDraw, 10);
		}

		/**
		 * 追踪号码 开奖号码 冷热数据
		 */
		private void saveNumberHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType,
										  Object period, String drawNumber) throws Exception {
			//非竞速类游戏return
			switch (gameCode) {
				case SELF_10_2:
				case COUNTRY_10:
				case SELF_10_5:
				case XYFT:
				case JS10:
				case PK10:
					break;
				default:
					return;
			}
			Map<String, LruMap<Object, String[][]>> gameTypeMap;
			//类型集合
			if (numberHotAndCold.containsKey(gameCode)) {
				gameTypeMap = numberHotAndCold.get(gameCode);
			} else {
				gameTypeMap = new HashMap<>(3);
				numberHotAndCold.put(gameCode, gameTypeMap);
			}
			String[] recentDraw = drawNumber.split(",");

			getHotAndCold(gameCode, handicapCode, drawType, period, "NUMBER", gameTypeMap, recentDraw, 10);
		}

		private void getHotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, Object period,
			String rankMode, Map<String, LruMap<Object, String[][]>> gameTypeMap, String[] recentDraw, int len) throws Exception {
			Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
			String[][] hotAndCold;
			LruMap<Object, String[][]> periodMap;
			//开奖集合
			if (gameTypeMap.containsKey(drawType)) {
				periodMap = gameTypeMap.get(drawType);
				//上一期冷热排名
				String[][] lotteryHotAndCold = periodMap.get(periodOption.findBeforePeriod(period, 1));
				if (ContainerTool.isEmpty(lotteryHotAndCold)) {
					hotAndCold = hotAndCold(gameCode, handicapCode, drawType, recentDraw, period, rankMode);
				} else {
					hotAndCold = new String[recentDraw.length][len];
					switch (rankMode){
						case "LOCATION":
							ArrayCopyTool.arrayCopyLocation(hotAndCold, lotteryHotAndCold, recentDraw);
							break;
						case "NUMBER":
							Object lotteryPeriod = periodOption.findBeforePeriod(period, 1);
							String valiDrawNumber = getDraw(gameCode, handicapCode, drawType, lotteryPeriod);
							String[] valiNumber = valiDrawNumber.split(",");
							ArrayCopyTool.arrayCopyNumber(hotAndCold, lotteryHotAndCold, recentDraw,valiNumber);
							break;
						default:
							ArrayCopyTool.arrayCopy(hotAndCold, lotteryHotAndCold, recentDraw);
					}
				}
			} else {
				//无历史冷热排名信息
				periodMap = new LruMap<>(64);
				gameTypeMap.put(drawType, periodMap);
				hotAndCold = hotAndCold(gameCode, handicapCode, drawType, recentDraw, period, rankMode);
			}
			if (ContainerTool.isEmpty(hotAndCold)) {
				return;
			}
			periodMap.put(period, hotAndCold);
		}
		/**
		 * 计算冷热排名
		 *
		 * @param gameCode   游戏编码
		 * @param drawType   开奖类型
		 * @param recentDraw 当期开奖号码
		 * @param period     期数
		 * @param rankMode   排名方式
		 * @return
		 */
		private String[][] hotAndCold(BaseGameUtil.Code gameCode, BaseHandicapUtil.Code handicapCode, String drawType, String[] recentDraw,
									  Object period, String rankMode) throws Exception {
			//获取历史100条开奖信息
			List<String> number;
			String[][] hotAndCold;
			switch (gameCode) {
				case XYFT:
				case JS10:
				case PK10:
				case SELF_10_2:
				case COUNTRY_10:
					number = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
							.add("7").add("8").add("9").add("10").build();
					hotAndCold = new String[recentDraw.length][10];
					break;
				case JSSSC:
				case COUNTRY_SSC:
				case SELF_SSC_5:
				case CQSSC:
					number = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
							.add("7").add("8").add("9").add("0").build();
					hotAndCold = new String[recentDraw.length][10];
					break;
				case GDKLC:
				case XYNC:
					number = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
							.add("7").add("8").add("9").add("10").add("11").add("12").add("13").add("14").add("15")
							.add("16").add("17").add("18").add("19").add("20").build();
					hotAndCold = new String[recentDraw.length][20];
					break;
				default:
					return null;
			}

			Map<String, Object> drawInfos;
			if (isClient()) {
				drawInfos = getBatchDraw(gameCode, drawType);
			} else {
				String drawTypeName = "GAME_DRAW_TYPE_";
				String drawTableName = gameCode.getGameFactory().period(handicapCode).getDrawTableName();
				drawInfos = new CacheService().getBatchDraw(drawTableName, drawType, drawTypeName);
			}
			if (ContainerTool.isEmpty(drawInfos)) {
				return null;
			}

			Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
			switch (rankMode) {
				case "LOCATION":
					return getHotLocationAndCold(recentDraw, period, periodOption, number, hotAndCold, drawInfos);
				case "NUMBER":
					return getNumberHotAndCold(recentDraw, period, periodOption, number, hotAndCold, drawInfos);
				default:
					return getHotAndCold(recentDraw, period, periodOption, number, hotAndCold, drawInfos);
			}

		}

		/**
		 * LOCATION 冷热排名
		 */
		private String[][] getHotLocationAndCold(String[] recentDraw, Object period, Period<?> periodOption, List<String> number,
												 String[][] hotAndCold, Map<String, Object> drawInfos) throws ParseException {
			List<String> numberCopy;
			for (int i = 0; i < recentDraw.length; i++) {
				int h = 0;
				numberCopy = new ArrayList<>(number);
				for (int j = 0; j < drawInfos.size(); j++) {
					//历史期数
					Object lotteryPeriod = periodOption.findBeforePeriod(period, j + 1);
					//历史开奖
					Object lotteryDraw = drawInfos.get(lotteryPeriod.toString());
					if (StringTool.isEmpty(lotteryDraw)) {
						return null;
					}
					String[] lotteryDraws = lotteryDraw.toString().split(",");
					int index = ContainerTool.findIndex(lotteryDraws, String.valueOf(i + 1)) + 1;

					String rank = String.valueOf(index);
					if (numberCopy.size() == 1) {
						hotAndCold[i][hotAndCold[i].length - 1] = numberCopy.get(0);
						break;
					}
					if (numberCopy.contains(rank)) {
						hotAndCold[i][h] = rank;
						++h;
						numberCopy.remove(rank);
					}
				}
			}
			return hotAndCold;
		}

		/**
		 * NUMBER 冷热排名
		 */
		private String[][] getNumberHotAndCold(String[] recentDraw, Object period, Period<?> periodOption, List<String> number,
											   String[][] hotAndCold, Map<String, Object> drawInfos) throws ParseException {
			List<String> numberCopy;
			for (int i = 0; i < recentDraw.length; i++) {
				int h = 0;
				numberCopy = new ArrayList<>(number);
				for (int j = 0; j < drawInfos.size(); j++) {
					//历史期数
					Object lotteryPeriod = periodOption.findBeforePeriod(period, j + 1);
					//历史开奖
					Object lotteryDraw = drawInfos.get(lotteryPeriod.toString());
					if (StringTool.isEmpty(lotteryDraw)) {
						return null;
					}
					String[] lotteryDraws = lotteryDraw.toString().split(",");
					int index = ContainerTool.findIndex(lotteryDraws, String.valueOf(i + 1));

					lotteryPeriod = periodOption.findBeforePeriod(period, j);
					lotteryDraw = drawInfos.get(lotteryPeriod.toString());
					lotteryDraws = lotteryDraw.toString().split(",");

					if (numberCopy.size() == 1) {
						hotAndCold[i][hotAndCold[i].length - 1] = numberCopy.get(0);
						break;
					}
					if (numberCopy.contains(lotteryDraws[index])) {
						hotAndCold[i][h] = lotteryDraws[index];
						++h;
						numberCopy.remove(lotteryDraws[index]);
					}
				}
			}
			return hotAndCold;

		}

		/**
		 * 基础冷热排名
		 */
		private String[][] getHotAndCold(String[] recentDraw, Object period, Period<?> periodOption, List<String> number, String[][] hotAndCold, Map<String, Object> drawInfos) throws java.text.ParseException {
			List<String> numberCopy;
			for (int i = 0; i < recentDraw.length; i++) {
				int h = 0;
				hotAndCold[i][h] = recentDraw[i];
				numberCopy = new ArrayList<>(number);
				numberCopy.remove(recentDraw[i]);
				for (int j = 0; j < drawInfos.size(); j++) {
					//历史期数
					Object lotteryPeriod = periodOption.findBeforePeriod(period, j + 1);
					//历史开奖
					Object lotteryDraw = drawInfos.get(lotteryPeriod.toString());
					if (StringTool.isEmpty(lotteryDraw)) {
						return null;
					}

					String[] lotteryDraws = lotteryDraw.toString().split(",");
					if (numberCopy.size() == 1) {
						hotAndCold[i][hotAndCold[i].length - 1] = numberCopy.get(0);
						break;
					}
					if (numberCopy.contains(lotteryDraws[i])) {
						hotAndCold[i][++h] = lotteryDraws[i];
						numberCopy.remove(lotteryDraws[i]);
					}
				}
			}
			return hotAndCold;
		}

		/**
		 * 防止数据源切换错误
		 *
		 * @param gameCode 游戏编码
		 * @param drawType 开奖类型
		 * @return
		 */
		private synchronized Map<String, Object> getBatchDraw(BaseGameUtil.Code gameCode, String drawType) throws SQLException {
			TransactionsBase transactions = CurrentTransaction.getDataBase();
			if (transactions == null) {
				transactions = new TransactionsBase();
			}
			IJdbcTool holdJdbc = transactions.transactionStart("cloud");
			String drawTableName = "lottery.cloud_lottery_" + gameCode.name();
			String drawTypeName = "DRAW_TYPE_";
			try {
				return new CacheService().getBatchDraw(drawTableName, drawType, drawTypeName);
			} finally {
				transactions.transactionFinish(holdJdbc);
			}
		}
	}


}
