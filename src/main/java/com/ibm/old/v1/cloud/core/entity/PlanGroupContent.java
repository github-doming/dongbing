package com.ibm.old.v1.cloud.core.entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;
/**
 * @Description: 方案组正文
 * @Author: Dongming
 * @Date: 2019-07-25 14:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanGroupContent {

	private static final Logger log = LogManager.getLogger(PlanGroupContent.class);

	PlanTool.Code planCode;
	IbmGameEnum game;
	JSONObject groupData;
	Object basePeriod;

	public PlanGroupContent(PlanTool.Code planCode, IbmGameEnum game, JSONObject groupData, Object basePeriod) {
		this.planCode = planCode;
		this.game = game;
		this.groupData = groupData;
		this.basePeriod = basePeriod;
	}
	public PlanGroupContent(PlanTool.Code planCode, IbmGameEnum game, String groupDataVal, Object basePeriod) {
		this(planCode,game,JSONObject.parseObject(groupDataVal),basePeriod);
	}
	/**
	 * 获取投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @return 投注正文
	 */
	public String getBetContent(Map<String, String> exitsMap) {
		return getBetContent(exitsMap,false);
	}
	/**
	 * 获取投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @param isCompress 是否压缩
	 * @return 投注正文
	 */
	public String getBetContent(Map<String, String> exitsMap,boolean isCompress) {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				return locationBetNumberBc(exitsMap);
			case FOLLOW_TWO_SIDE:
				return followTwoSideBc(exitsMap);
			case FOLLOW_OPEN_BET:
				return followOpenBetBc(exitsMap,isCompress);
			case RANK_HOT_AND_COLD:
				return rankHotAndColdBc(exitsMap);
			case NUMBER_TO_TRACK:
				return numberToTrackBc(exitsMap);
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());

		}
	}
	/**
	 * 位置投注 - 投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @return 投注正文
	 */
	private String locationBetNumberBc(Map<String, String> exitsMap) {
		try {
			//数据为空
			if (ContainerTool.isEmpty(groupData) || StringTool.isEmpty(basePeriod)) {
				log.info("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
				return null;
			}
			String key = groupData + "#" + basePeriod;
			//已存在数据
			if (exitsMap.containsKey(key)) {
				return exitsMap.get(key);
			}

			//基准开奖信息
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准开奖信息为空，期数为：" + basePeriod);
				return null;
			}

			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String[] selects = groupData.getString("select").split(",");

			//选择位置或者投注位置为空
			if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets)) {
				exitsMap.put(key, null);
				log.info("解析数据出错，选择位置：" + Arrays.toString(selects) + ",投注信息：" + Arrays.toString(bets));
				return null;
			}

			Integer[] selectsInt = NumberTool.intValue(selects);
			assert selectsInt != null;
			StringBuilder result = new StringBuilder();
			String rank, number;
			for (String bet : bets) {
				rank = StringTool.getRankCn(Integer.parseInt(bet));
				for (Integer select : selectsInt) {
					number = baseData[select - 1];
					result.append(rank).append("|").append(number).append("#");
				}
			}
			exitsMap.put(key, result.toString());
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
			return null;
		}
	}
	/**
	 * 跟上期双面 - 投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @return 投注正文
	 */
	private String followTwoSideBc(Map<String, String> exitsMap) {
		try {
			//数据为空
			if (ContainerTool.isEmpty(groupData) || StringTool.isEmpty(basePeriod)) {
				log.info("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
				return null;
			}
			String key = groupData + "#" + basePeriod;
			//已存在数据
			if (exitsMap.containsKey(key)) {
				return exitsMap.get(key);
			}

			//基准开奖信息
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准开奖信息为空，期数为：" + basePeriod);
				return null;
			}

			//解析数据

			boolean state = IbmTypeEnum.isTrue(groupData.getString("state"));
			Integer[] index = GameTool.findFollowTwoSide(groupData.getString("code"));
			int numIndex = index[0];
			int typeIndex = index[1];

			int number, threshold = 5;
			if (numIndex == 0) {
				//冠亚和
				number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
				threshold = 11;
			} else if (numIndex < 0 || numIndex > 10) {
				log.warn("非法的排序捕捉，索引为" + numIndex);
				return null;
			} else {
				number = Integer.parseInt(baseData[numIndex - 1]);
			}

			StringBuilder result = new StringBuilder();
			switch (typeIndex) {
				//大小
				case 1:
					result.append(StringTool.getRankCn(numIndex)).append("|")
							.append(GameTool.getBoS(number, threshold, state));
					break;
				case 2:
					result.append(StringTool.getRankCn(numIndex)).append("|").append(GameTool.getParity(number, state));
					break;
				case 3:
					if (numIndex <= 0 || numIndex > 5) {
						log.warn("非法的排序捕捉，索引为" + numIndex);
						return null;
					}
					int number2 = Integer.parseInt(baseData[10 - numIndex]);
					result.append(StringTool.getRankCn(numIndex)).append("|")
							.append(GameTool.getDoT(number, number2, state));
					break;
				default:
					log.warn("非法的类型捕捉，类型为" + typeIndex);
					return null;

			}
			exitsMap.put(key, result.toString());
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
			return null;
		}
	}
	/**
	 * 开某投某 - 投注正文
	 *
	 * @param exitsMap 	已存在 Map 集合
	 * @param isCompress 是否压缩
	 * @return 投注正文
	 */
	private String followOpenBetBc(Map<String, String> exitsMap,boolean isCompress) {
		try {
			if(isCompress){
				String activeKey=groupData.remove("activeKey").toString();
				JSONArray selects= (JSONArray) groupData.remove("selects");

				String[] baseData = GameTool.getHistoryData(basePeriod, game);
				String open = baseData[Integer.parseInt(activeKey)];

				for (Object data : selects) {
					JSONObject item = (JSONObject) data;
					String select = item.getString("select");
					if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
						return null;
					}
					if (open.equals(select)) {
						if (groupData.containsKey("rank")) {
							groupData.put("bet", groupData.getString("bet").concat(",").concat(item.getString("bet")));
						} else {
							groupData.put("rank", activeKey);
							groupData.put("bet", item.getString("bet"));
						}
					}
				}
			}
			//数据为空
			if (ContainerTool.isEmpty(groupData) || StringTool.isEmpty(basePeriod)) {
				log.debug("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
				return null;
			}
			String key = groupData + "#" + basePeriod;
			//已存在数据
			if (exitsMap.containsKey(key)) {
				return exitsMap.get(key);
			}
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String rank = groupData.getString("rank");
			if (StringTool.isEmpty(rank) || ContainerTool.isEmpty(bets)) {
				exitsMap.put(key, null);
				log.info("解析数据出错，投注位置：" + rank + ",投注信息：" + Arrays.toString(bets));
				return null;
			}
			StringBuilder result = new StringBuilder();
			String rankCn = StringTool.getRankCn(Integer.parseInt(rank) + 1);
			for (String bet : bets) {
				result.append(rankCn).append("|").append(bet).append("#");
			}
			exitsMap.put(key, result.toString());
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
			return null;
		}
	}

	/**
	 * 冷热排名 - 投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @return 投注正文
	 */
	private String rankHotAndColdBc(Map<String, String> exitsMap) {
		try {
			//数据为空
			if (ContainerTool.isEmpty(groupData) || StringTool.isEmpty(basePeriod)) {
				log.info("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
				return null;
			}
			String key = groupData + "#" + basePeriod;
			//已存在数据
			if (exitsMap.containsKey(key)) {
				return exitsMap.get(key);
			}

			//解析数据
			int bet = groupData.getInteger("bet");
			String[] ranks = groupData.getString("rank").split(",");

			//投注位置或遗漏排名为空
			if (ContainerTool.isEmpty(bet) || ContainerTool.isEmpty(ranks)) {
				exitsMap.put(key, null);
				log.info("解析数据出错，投注位置：" + Arrays.toString(ranks) + ",投注信息：" + bet);
				return null;
			}
			Integer[] ranksInt = NumberTool.intValue(ranks);
			//冷热数据
			String[][] hotAndColdData = GameTool.findHotAndCold(basePeriod, game);
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热排名为空，期数为：" + basePeriod);
				return null;
			}
			StringBuilder result = new StringBuilder();

			//投注位置
			String rank = StringTool.getRankCn(bet);
			String number;
			assert ranksInt != null;
			for (int rankInt : ranksInt) {
				//第几号排名
				number = hotAndColdData[bet - 1][rankInt - 1];
				result.append(rank).append("|").append(number).append("#");
			}

			exitsMap.put(key, result.toString());
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
			return null;
		}
	}

	/**
	 * 号码追踪 - 投注正文
	 *
	 * @param exitsMap 已存在 Map 集合
	 * @return 投注正文
	 */
	private String numberToTrackBc(Map<String, String> exitsMap) {
		try {
			//数据为空
			if (ContainerTool.isEmpty(groupData) || StringTool.isEmpty(basePeriod)) {
				log.info("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
				return null;
			}
			String key = groupData + "#" + basePeriod;
			//已存在数据
			if (exitsMap.containsKey(key)) {
				return exitsMap.get(key);
			}

			//基准开奖信息
			String[] baseData = GameTool.getHistoryData(basePeriod, game);
			if (ContainerTool.isEmpty(baseData)) {
				log.info("获取基准开奖信息为空，期数为：" + basePeriod);
				return null;
			}

			//解析数据
			String track = groupData.getString("track");
			String[] bets = groupData.getString("bet").split(",");

			// 追踪位置或投注位置为空
			if (StringTool.isEmpty(track) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，投注号码：" + Arrays.toString(bets) + ",追踪信息：" + track);
				exitsMap.put(key, null);
				return null;
			}

			int index = ContainerTool.findIndex(baseData, track);
			//投注位置
			String rank = StringTool.getRankCn(index + 1);

			StringBuilder result = new StringBuilder();
			for (String bet : bets) {
				//第几号排名
				result.append(rank).append("|").append(bet).append("#");
			}

			exitsMap.put(key, result.toString());
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为：" + basePeriod + ",方案组详情为：" + groupData);
			return null;
		}
	}
}
