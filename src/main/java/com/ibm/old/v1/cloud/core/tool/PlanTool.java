package com.ibm.old.v1.cloud.core.tool;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.entity.SpliceGroupRepeat;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;
/**
 * @Description: 方案工具类
 * @Author: Dongming
 * @Date: 2018-12-21 10:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PlanTool {

	public static final String PLAN_GROUP_DESC = "%s-第%s组" ;

	public static final int BET_TYPE_CODE = 1;
	public static final int BET_TYPE_MERGE = 2;
	public static final int BET_TYPE_MERGE_CODE = 3;
	public static final int BET_TYPE_MANUAL = 4;

	/**
	 * 组装方案组数据
	 *
	 * @param planCode  方案信息
	 * @param openInfos 开启信息
	 * @return 方案组数据
	 */
	public static Map<String, Set<Map<String, String>>> spliceGroupRepeat(Code planCode,
			List<Map<String, Object>> openInfos) {
		Map<String, Set<Map<String, String>>> planItemMap = new HashMap<>(openInfos.size());
		for (Map<String, Object> info : openInfos) {

			if (StringTool.isEmpty(info.get("PLAN_GROUP_ACTIVE_KEY_"))) {
				continue;
			}
			//已开启方案组
			String activeKeys = info.get("PLAN_GROUP_ACTIVE_KEY_").toString();
			if (StringTool.isEmpty(activeKeys)) {
				continue;
			}
			//跟进期数默认为1
			int followPeriod = Integer.parseInt(info.get("FOLLOW_PERIOD_").toString());
			if (followPeriod == 0) {
				followPeriod = 1;
			}
			int monitorPeriod = 0;
			//不存在监控期数
			if (!Code.RANK_HOT_AND_COLD.equals(planCode)) {
				//监控期数可为0
				monitorPeriod = Integer.parseInt(info.get("MONITOR_PERIOD_").toString());
			}

			String fundSwapMode = info.get("FUND_SWAP_MODE_").toString();

			String itemId = info.get("PLAN_ITEM_ID_").toString();

			JSONObject planGroup = JSONObject.parseObject(info.get("PLAN_GROUP_DATA_").toString());
			SpliceGroupRepeat sgr = new SpliceGroupRepeat(activeKeys, followPeriod, monitorPeriod, fundSwapMode, itemId,
					planGroup);
			sgr.splice(planCode, planItemMap);
		}
		return planItemMap;
	}

	/**
	 * 抽取state 键
	 *
	 * @param planGroupData 方案组数据
	 * @return 激活键
	 */
	public static String extractState(String planGroupData) {
		StringBuilder activeKey = new StringBuilder();
		JSONObject data = JSONObject.parseObject(planGroupData);
		data.remove("publicData");
		for (String key : data.keySet()) {
			JSONObject item = data.getJSONObject(key);
			if (IbmTypeEnum.isTrue(item.getString("state"))) {
				activeKey.append(key).append(",");
			}
		}
		String key = activeKey.toString();
		//激活键为空
		if (StringTool.isEmpty(key)) {
			return null;
		}
		Integer[] keyArray = NumberTool.intValue(key.split(","));
		//激活键数字为空
		if (ContainerTool.isEmpty(keyArray)) {
			return null;
		}
		Arrays.sort(keyArray);
		key = StringUtils.join((keyArray), ",");

		return key;
	}

	public static final List<Integer> SMALL = Arrays.asList(0, 1, 2, 3, 4);
	public static final List<Integer> BIG = Arrays.asList(5, 6, 7, 8, 9);
	public static final List<Integer> DOUBLE = Arrays.asList(1, 3, 5, 7, 9);
	public static final List<Integer> SINGLE = Arrays.asList(0, 2, 4, 6, 8);

	/**
	 * 获取合并类型
	 *
	 * @param indexList 索引列表
	 * @return 合并类型
	 */
	public static IbmTypeEnum getMergeType(List<Integer> indexList) {
		if (indexList.containsAll(SMALL)) {
			return IbmTypeEnum.SMALL;
		}
		if (indexList.containsAll(BIG)) {
			return IbmTypeEnum.BIG;
		}
		if (indexList.containsAll(SINGLE)) {
			return IbmTypeEnum.SINGLE;
		}
		if (indexList.containsAll(DOUBLE)) {
			return IbmTypeEnum.DOUBLE;
		}
		return null;
	}

	/**
	 * 根据金额将投注项分组
	 *
	 * @param set     资金列表
	 * @param typeKey 类型键
	 * @param rank    排行名称
	 * @return 一个金额一组数据
	 */
	public static Map<Integer, String> getFundsMap(int[] set, String typeKey, String rank) {
		List<Integer> indexList = NumberTool.findNoZeroValIndex(set);
		Map<Integer, String> fundsMap = new HashMap<>(indexList.size());
		for (Integer index : indexList) {
			String number = GameTool.getNumber(typeKey, index);
			//以资金为key，放入map，组合投注正文
			int funds = set[index];
			String content = rank + "|" + number;
			fundsMap.put(funds, fundsMap.containsKey(funds) ? fundsMap.get(funds) + "#" + content : content);
		}
		return fundsMap;
	}

	/**
	 * 方案编码
	 */
	public enum Code {
		/**
		 * 位置投注
		 */
		LOCATION_BET_NUMBER("locationBetNumber") {
			@Override public String getName() {
				return "位置投注" ;
			}
			@Override public String getTableName() {
				return "IBM_PI_LOCATION_BET_NUMBER" ;
			}
		},
		/**
		 * 跟上期双面
		 */
		FOLLOW_TWO_SIDE("followTwoSide") {
			@Override public String getName() {
				return "跟上期双面" ;
			}
			@Override public String getTableName() {
				return "IBM_PI_FOLLOW_TWO_SIDE" ;
			}
		},
		/**
		 * 开某投某
		 */
		FOLLOW_OPEN_BET("followOpenBet") {
			@Override public String getName() {
				return "开某投某" ;
			}
			@Override public String getTableName() {
				return "IBM_PI_FOLLOW_OPEN_BET" ;
			}
		},
		/**
		 * 号码追踪
		 */
		NUMBER_TO_TRACK("numberToTrack") {
			@Override public String getName() {
				return "号码追踪" ;
			}
			@Override public String getTableName() {
				return "IBM_PI_NUMBER_TO_TRACK" ;
			}
		},
		/**
		 * 冷热排名
		 */
		RANK_HOT_AND_COLD("rankHotAndCold") {
			@Override public String getName() {
				return "冷热排名" ;
			}
			@Override public String getTableName() {
				return "IBM_PI_RANK_HOT_AND_COLD" ;
			}
		};
		private String code;
		Code(String code) {
			this.code = code;
		}
		public String getCode() {
			return code;
		}
		/**
		 * 获取方案表名
		 *
		 * @return 方案表名
		 */
		public abstract String getName();

		/**
		 * 获取方案详情表名
		 *
		 * @return 方案详情表名
		 */
		public abstract String getTableName();

		/**
		 * 根据tableName获取方案code
		 *
		 * @param table 表名
		 * @return 方案code
		 */
		public static Code valueOfTableName(Object table) {
			for (Code code : Code.values()) {
				if (code.getTableName().equals(table)) {
					return code;
				}
			}
			return null;
		}
	}

}
