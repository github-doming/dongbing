package com.ibm.old.v1.cloud.ibm_plan_user.t.service;

import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmPlanUserSetService extends BaseServicePlus {

	/**
	 * @param gameId 游戏ID
	 * @param table
	 * @return 默认双面配置
	 * @Description: 查找默认的位置配置
	 */
	public Map<String, Object> findDefData(String gameId, String table) throws Exception {
		String sql = "SELECT ipl.* FROM " + table
				+ " ipl LEFT JOIN ibm_plan ipu ON ipl.PLAN_ID_ = ipu.IBM_PLAN_ID_ WHERE ipl.state_ = ? AND ipu.state_ != ? AND ipu.GAME_ID_ = ? " ;
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbmStateEnum.DEF.name());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(gameId);
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * @param table
	 * @Description: 保存对象
	 * <p>
	 * 参数说明
	 */
	public String saveEdit(Map<String, Object> map, String table) throws SQLException {
		StringBuilder sql = new StringBuilder("INSERT INTO " + table + "(");
		List<Object> parameters = new ArrayList<>();
		String id = UUID.randomUUID().toString().replace("-", "");
		map.remove("ROW_NUM");
		map.put(table + "_ID_", id);
		for (String set : map.keySet()) {
			sql.append(set).append(",");
		}
		sql.replace(sql.length() - 1, sql.length(), " ) VALUES(");
		for (Object obj : map.values()) {
			sql.append("?,");
			parameters.add(obj);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
		return id;
	}

	/**
	 * @param excPlanIds 无效方案ID
	 * @param userId     用户ID
	 * @param table      用户方案ID设置表名
	 * @Description: 删除用户方案设置
	 * <p>
	 * 参数说明
	 */
	public void delplanUserSet(List<String> excPlanIds, String userId, String table) throws SQLException {
		StringBuilder sql = new StringBuilder("update " + table + " set DESC_=?,state_=? where USER_ID_=? AND PLAN_ID_ in (");
		List<Object> parameters = new ArrayList<>(excPlanIds.size() + 2);
		parameters.add("删除用户无效方案设置");
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(userId);
		for (String excPlanId : excPlanIds) {
			sql.append("?,");
			parameters.add(excPlanId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * @param planId 方案id
	 * @param userId 用户id
	 * @param table  表名
	 * @Description: 通过方案id查找方案详情表id
	 * <p>
	 * 参数说明
	 */
	public String findIdByPlanId(String planId, String userId, String table) throws SQLException {
		String sql = "SELECT " + table + "_ID_ FROM " + table + " WHERE PLAN_ID_ = ? AND USER_ID_ = ? AND STATE_ = ? " ;
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(planId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString(table + "_ID_", sql, parameterList);
	}

	/**
	 * 根据id列表获取已开启基础信息列表
	 *
	 * @param itemIds 详情id列表
	 * @return 已开启基础信息列表
	 */
	public List<Map<String, Object>> listInfo(Set<String> itemIds, String table) throws SQLException {
		String sql = "SELECT " + table + "_ID_,FOLLOW_PERIOD_,MONITOR_PERIOD_,FUND_SWAP_MODE_,PLAN_GROUP_DATA_,"
				+ " PLAN_GROUP_ACTIVE_KEY_ FROM " + table + "  where 1 = 1 " ;
		StringBuilder sqlBuilder = new StringBuilder(" and " + table + "_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sqlBuilder.append("?,");
			parameterList.add(itemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and state_ = ? order by UPDATE_TIME_LONG_ desc");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取资金方案信息
	 *
	 * @param itemIds 位置投注_号码主键数组
	 * @return key-主键	value-资金方案
	 */
	public Map<Object, Object> mapFundsList(Set<String> itemIds, String table) throws SQLException {
		String sql = "SELECT " + table + "_ID_ AS key_,concat_ws('#', BET_MODE_,IF ( "
				+ " ISNULL(FUNDS_LIST_) || LENGTH(trim(FUNDS_LIST_)) < 1,'NULL',FUNDS_LIST_), "
				+ " IF (ISNULL(FUNDS_LIST_ID_) || LENGTH(trim(FUNDS_LIST_ID_)) < 1, "
				+ " 'NULL',	FUNDS_LIST_ID_)) AS value_ FROM " + table + " WHERE  1 = 1 and " ;
		StringBuilder sqlBuilder = new StringBuilder(" " + table + "_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size() + 1);
		for (String itemId : itemIds) {
			sqlBuilder.append("?,");
			parameterList.add(itemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and state_ = ? order by UPDATE_TIME_LONG_ desc");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 获取方案详情信息
	 *
	 * @param planItemId 方案详情id
	 * @param table      方案表名
	 * @return 方案详情信息
	 */
	public Map<String, Object> findInfo(String planItemId, String table) throws SQLException {
		String sql = "SELECT " + table + "_ID_ as PLAN_ITEM_ID_,FUNDS_LIST_,FUNDS_LIST_ID_,FOLLOW_PERIOD_, "
				+ " MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PLAN_GROUP_DATA_," + " PLAN_GROUP_ACTIVE_KEY_ FROM "
				+ table + "  where " + table + "_ID_=? " ;
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(planItemId);
		return super.dao.findMap(sql, parameterList);
	}
}
