package com.ibm.old.v1.servlet.ibm_plan_statistics.service;
import com.ibm.old.v1.cloud.core.tool.PlanTool;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 历史统计基础服务类
 * @Author: Dongming
 * @Date: 2019-06-10 14:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanStatisticsService extends BaseServicePlus {
	/**
	 * 查询所有已开启游戏code
	 *
	 * @return 已开启游戏code
	 */
	public List<Map<String, Object>> listGameCode() throws SQLException {
		String sql = "SELECT GAME_NAME_,GAME_CODE_ FROM ibm_game WHERE STATE_ = ? " ;
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql, parameterList);
		for (Map<String, Object> map : list) {
			map.remove("ROW_NUM");
		}
		return list;
	}

	/**
	 * 根据游戏code获取游戏id
	 *
	 * @param gameCode 游戏code
	 * @return 游戏code 对应的id
	 */
	public String findGameId(String gameCode) throws SQLException {
		String sql = "SELECT IBM_GAME_ID_ FROM ibm_game WHERE GAME_CODE_ = ? and STATE_ = ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_GAME_ID_", sql, parameterList);
	}

	/**
	 * 查询所有已拥有的方案code
	 *
	 * @param userId 用户id
	 * @param gameId 游戏id
	 * @return 所有拥有的方案code
	 */
	public List<Map<String, Object>> listPlanCode(String userId, String gameId) throws SQLException {
		String sql = "SELECT PLAN_NAME_,PLAN_ITEM_TABLE_NAME_  from  ibm_plan_user  where APP_USER_ID_ = ? "
				+ " and GAME_ID_ = ? and STATE_ !=? " ;
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		List<Map<String, Object>> list = super.dao.findMapList(sql, parameterList);
		for (Map<String, Object> map : list) {
			map.put("PLAN_CODE_", map.get("PLAN_ITEM_TABLE_NAME_").toString().replace("IBM_PI_", ""));
			map.remove("PLAN_ITEM_TABLE_NAME_");
			map.remove("ROW_NUM");
		}
		return list;
	}

	/**
	 * 获取方案id
	 *
	 * @param planCode 方案code
	 * @param gameId   游戏id
	 * @return 方案id
	 */
	public String findPlanId(String planCode, String gameId) throws SQLException {
		String sql = "SELECT IBM_PLAN_ID_ FROM ibm_plan WHERE state_ != ? AND GAME_ID_ = ? AND PLAN_CODE_=?" ;
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameId);
		parameterList.add(planCode);
		return super.dao.findString("IBM_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 获取方案详情信息
	 *
	 * @param planId 方案id
	 * @param userId 用户id
	 * @param plan   方案
	 * @return 方案详情信息
	 */
	public Map<String, Object> findPlanItemInfo(String planId, String userId, PlanTool.Code plan) throws SQLException {
		String tableName = plan.getTableName();
		if (StringTool.isEmpty(tableName)) {
			return null;
		}
		String sql = "select FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,FUND_SWAP_MODE_, "
				+ " PLAN_GROUP_ACTIVE_KEY_,PLAN_GROUP_DATA_,BET_MODE_,PERIOD_ROLL_MODE_ from ".concat(tableName)
				.concat(" where USER_ID_=? and PLAN_ID_=? and STATE_ != ?");
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(userId);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> info = super.dao.findMap(sql, parameterList);
		//开启方案组key
		Object planGroupActiveKey = info.get("PLAN_GROUP_ACTIVE_KEY_");
		if (StringTool.isEmpty(planGroupActiveKey)) {
			return null;
		}
		//跟进期数
		int followPeriod = NumberTool.getInteger(info.get("FOLLOW_PERIOD_"));
		if (followPeriod == 0) {
			followPeriod = 1;
		}
		//监控期数
		int monitorPeriod = NumberTool.getInteger(info.get("MONITOR_PERIOD_"));

		//资金切换方式
		String fundSwapMode = info.get("FUND_SWAP_MODE_").toString();
		//资金列表
		String funds = info.get("FUNDS_LIST_").toString();
		if (StringTool.isEmpty(funds)) {
			return null;
		}
		//方案组数据
		JSONObject planGroupData = JSONObject.fromObject(info.get("PLAN_GROUP_DATA_"));
		//公用数据
		boolean isPublicData = false;
		JSONArray publicData = null;
		if (planGroupData.containsKey("publicData")) {
			isPublicData = true;
			JSONObject data = planGroupData.getJSONObject("publicData");

			publicData = new JSONArray();

			for (Object value : data.values()) {
				JSONObject item = (JSONObject) value;
				//存入可用的公用数据
				if (IbmTypeEnum.TRUE.equals(IbmTypeEnum.valueOf(item.remove("state").toString()))) {
					publicData.add(item);
				}
			}
		}
		String[] activeKeys = planGroupActiveKey.toString().split(",");
		Map<String, Object> activePlanGroup = new HashMap<>(activeKeys.length);
		for (String activeKey : activeKeys) {
			if (isPublicData) {
				JSONObject data = new JSONObject();
				data.put("selects",publicData);
				data.put("activeKey",activeKey);
				activePlanGroup.put(activeKey, data);
			} else {
				activePlanGroup.put(activeKey, planGroupData.get(activeKey));
			}
		}
		Map<String, Object> planItem = new HashMap<>(6);
		planItem.put("followPeriod", followPeriod);
		planItem.put("monitorPeriod", monitorPeriod);
		planItem.put("fundSwapMode", fundSwapMode);
		planItem.put("activePlanGroup", activePlanGroup);
		planItem.put("funds", funds);
		if(IbmModeEnum.BET_MODE_PERIOD_ROLL.name().equals(info.get("BET_MODE_"))){
			planItem.put("periodRollMode", info.get("PERIOD_ROLL_MODE_"));
		}
		return planItem;
	}

	/**
	 * 获取游戏的赔率
	 *
	 * @param gameId 游戏id
	 * @return 赔率
	 */
	public Map<Object, Object> findOdds(String gameId) throws SQLException {
		String sql = "SELECT GAME_PLAY_NAME_ as key_,ODDS_ as value_ FROM ibm_sys_bet_odds_detail WHERE GAME_ID_ = ? and STATE_ != ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findKVMap(sql, parameterList);
	}
	/**
	 * 获取游戏赔率
	 *
	 * @return 游戏赔率
	 */
	public Map<String, Map<Object, Object>> findOddsMap() throws SQLException {
		String sql = "SELECT GAME_CODE_ FROM `ibm_sys_bet_odds_detail` GROUP BY GAME_CODE_" ;
		List<String> gameCodes = super.dao.findStringList("GAME_CODE_", sql, null);
		if (ContainerTool.isEmpty(gameCodes)) {
			return null;
		}
		Map<String, Map<Object, Object>> oddsMap = new HashMap<>(gameCodes.size());
		sql = "SELECT GAME_PLAY_NAME_ as key_,ODDS_ as value_ FROM ibm_sys_bet_odds_detail WHERE GAME_CODE_ = ? and STATE_ != ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		for (String gameCode : gameCodes) {
			parameterList.clear();
			parameterList.add(gameCode);
			parameterList.add(IbmStateEnum.DEL.name());
			oddsMap.put(gameCode, super.findKVMap(sql, parameterList));
		}
		return oddsMap;
	}
}
