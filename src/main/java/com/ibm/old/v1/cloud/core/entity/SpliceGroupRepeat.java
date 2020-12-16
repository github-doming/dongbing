package com.ibm.old.v1.cloud.core.entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * @Description: 组装重复项
 * @Author: Dongming
 * @Date: 2019-07-24 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SpliceGroupRepeat {
	private String activeKeys;
	private int followPeriod;
	private int monitorPeriod;
	private String fundSwapMode;
	private String itemId;
	private JSONObject planGroup;

	public SpliceGroupRepeat(String activeKeys, int followPeriod, int monitorPeriod, String fundSwapMode, String itemId,
			JSONObject planGroup) {
		this.activeKeys = activeKeys;
		this.followPeriod = followPeriod;
		this.monitorPeriod = monitorPeriod;
		this.fundSwapMode = fundSwapMode;
		this.itemId = itemId;
		this.planGroup = planGroup;
	}
	/**
	 * 拼接
	 * @param planCode 方案 code
	 * @param planItemMap 方案详情 MAP
	 */
	public void splice(PlanTool.Code planCode, Map<String, Set<Map<String, String>>> planItemMap) {
		switch (planCode) {
			case LOCATION_BET_NUMBER:
				locationBetNumberSgr(planItemMap);
				break;
			case FOLLOW_TWO_SIDE:
				followTwoSideSgr(planItemMap);
				break;
			case FOLLOW_OPEN_BET:
				followOpenBetSgr(planItemMap);
				break;
			case RANK_HOT_AND_COLD:
				rankHotAndColdSgr(planItemMap);
				break;
			case NUMBER_TO_TRACK:
				numberToTrackSgr(planItemMap);
				break;
			default:
				throw new RuntimeException("不存在的方案名称" + planCode.getName());

		}
	}

	/**
	 * 位置投注- 组装重复项
	 * @param planItemMap 方案详情 MAP
	 */
	private void locationBetNumberSgr(Map<String, Set<Map<String, String>>> planItemMap) {
		for (String activeKey : activeKeys.split(",")) {
			JSONObject data = planGroup.getJSONObject(activeKey);
			JSONObject planItem = new JSONObject();
			planItem.put("followPeriod", followPeriod);
			planItem.put("monitorPeriod", monitorPeriod);
			planItem.put("select", data.getString("select"));
			planItem.put("bet", data.getString("bet"));
			String key = planItem.toJSONString();

			Map<String, String> map = new HashMap<>(3);
			map.put("itemId", itemId);
			map.put("activeKey", activeKey);
			map.put("fundSwapMode", fundSwapMode);
			if (planItemMap.containsKey(key)) {
				planItemMap.get(key).add(map);
			} else {
				Set<Map<String, String>> set = new HashSet<>();
				set.add(map);
				planItemMap.put(key, set);
			}
		}
	}

	/**
	 * 跟上期双面- 组装重复项
	 * @param planItemMap 方案详情 MAP
	 */
	private void followTwoSideSgr(Map<String, Set<Map<String, String>>> planItemMap) {
		//开启方案组《planItemJson-itemId_activeKey,itemId_activeKey》
		for (String activeKey : activeKeys.split(",")) {
			JSONObject data = planGroup.getJSONObject(activeKey);
			JSONObject planItem = new JSONObject();
			planItem.put("followPeriod", followPeriod);
			planItem.put("monitorPeriod", monitorPeriod);
			planItem.put("state", data.getString("state"));
			planItem.put("code", data.getString("code"));
			String key = planItem.toJSONString();

			Map<String, String> map = new HashMap<>(3);
			map.put("itemId", itemId);
			map.put("activeKey", activeKey);
			map.put("fundSwapMode", fundSwapMode);
			if (planItemMap.containsKey(key)) {
				planItemMap.get(key).add(map);
			} else {
				Set<Map<String, String>> set = new HashSet<>();
				set.add(map);
				planItemMap.put(key, set);
			}
		}
	}

	/**
	 * 开某投某- 组装重复项
	 * @param planItemMap 方案详情 MAP
	 */
	private void followOpenBetSgr(Map<String, Set<Map<String, String>>> planItemMap) {
		JSONArray selects = new JSONArray();
		JSONObject publicData = planGroup.getJSONObject("publicData");
		for (Object value : publicData.values()) {
			JSONObject item = (JSONObject) value;
			//存入可用的公用数据
			if (IbmTypeEnum.TRUE.equals(IbmTypeEnum.valueOf(item.remove("state").toString()))) {
				selects.add(item);
			}
		}

		//开启方案组《planItemJson-itemId_activeKey,itemId_activeKey》
		for (String activeKey : activeKeys.split(",")) {
			JSONObject planItem = new JSONObject();
			planItem.put("followPeriod", followPeriod);
			planItem.put("monitorPeriod", monitorPeriod);
			planItem.put("selects", selects);
			planItem.put("activeKey", activeKey);

			String key = planItem.toJSONString();
			Map<String, String> map = new HashMap<>(3);
			map.put("itemId", itemId);
			map.put("activeKey", activeKey);
			map.put("fundSwapMode", fundSwapMode);
			if (planItemMap.containsKey(key)) {
				planItemMap.get(key).add(map);
			} else {
				Set<Map<String, String>> set = new HashSet<>();
				set.add(map);
				planItemMap.put(key, set);
			}
		}
	}

	/**
	 * 冷热排名- 组装重复项
	 * @param planItemMap 方案详情 MAP
	 */
	private void rankHotAndColdSgr(Map<String, Set<Map<String, String>>> planItemMap) {
		//开启方案组《planItemJson-itemId_activeKey,itemId_activeKey》
		for (String activeKey : activeKeys.split(",")) {
			JSONObject data = planGroup.getJSONObject(activeKey);
			JSONObject planItem = new JSONObject();
			planItem.put("followPeriod", followPeriod);
			planItem.put("monitorPeriod", monitorPeriod);
			planItem.put("bet", data.get("bet"));
			planItem.put("rank", data.get("rank"));
			String key = planItem.toJSONString();

			Map<String, String> map = new HashMap<>(3);
			map.put("itemId", itemId);
			map.put("activeKey", activeKey);
			map.put("fundSwapMode", fundSwapMode);
			if (planItemMap.containsKey(key)) {
				planItemMap.get(key).add(map);
			} else {
				Set<Map<String, String>> set = new HashSet<>();
				set.add(map);
				planItemMap.put(key, set);
			}
		}
	}

	/**
	 * 号码追踪- 组装重复项
	 * @param planItemMap 方案详情 MAP
	 */
	private void numberToTrackSgr(Map<String, Set<Map<String, String>>> planItemMap) {
		//开启方案组《planItemJson-itemId_activeKey,itemId_activeKey》
		for (String activeKey : activeKeys.split(",")) {
			JSONObject data = planGroup.getJSONObject(activeKey);
			JSONObject planItem = new JSONObject();
			planItem.put("followPeriod", followPeriod);
			planItem.put("monitorPeriod", monitorPeriod);
			planItem.put("track", data.getString("track"));
			planItem.put("bet", data.getString("bet"));
			String key = planItem.toJSONString();

			Map<String, String> map = new HashMap<>(3);
			map.put("itemId", itemId);
			map.put("activeKey", activeKey);
			map.put("fundSwapMode", fundSwapMode);
			if (planItemMap.containsKey(key)) {
				planItemMap.get(key).add(map);
			} else {
				Set<Map<String, String>> set = new HashSet<>();
				set.add(map);
				planItemMap.put(key, set);
			}

		}
	}

}
