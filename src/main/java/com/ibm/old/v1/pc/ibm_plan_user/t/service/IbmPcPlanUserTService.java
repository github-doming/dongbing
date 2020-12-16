package com.ibm.old.v1.pc.ibm_plan_user.t.service;

import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmPcPlanUserTService extends IbmPlanUserTService {
	/**
	 * @param handicapCode 盘口code
	 * @return 方案id, 名称和所属游戏名称
	 * @Description: 查询盘口内的所有方案
	 * <p>
	 * 参数说明
	 */
	public List<Map<String, Object>> listByHandicap(String handicapCode, String userId) throws SQLException {
		String sql = "SELECT ip.PLAN_ID_,ip.PLAN_NAME_,ihg.GAME_NAME_,ihg.GAME_CODE_ FROM ibm_plan_user ip LEFT JOIN"
				+ " ibm_handicap_game ihg ON ip.GAME_ID_ = ihg.GAME_ID_ WHERE ip.state_ != ? AND ihg.STATE_ = ?"
				+ " AND ihg.HANDICAP_CODE_ = ? AND ip.APP_USER_ID_ = ? ORDER BY GAME_CODE_  " ;
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(handicapCode);
		parameterList.add(userId);
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * @param planIdList 方案ID集合
	 * @param appUserId  用户ID
	 * @return 用户方案信息
	 * @Description: 通过方案ID集合查找出多个用户方案信息
	 */
	public List<Map<String, Object>> findUserPlanItemInfoByIds(List<String> planIdList, String appUserId)
			throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT PLAN_CODE_ as CODE_,PLAN_ITEM_TABLE_ID_ as ID_ FROM `ibm_plan_user` ipu LEFT JOIN ibm_plan ip ON ipu.PLAN_ID_ = ip.IBM_PLAN_ID_  "
						+ " where APP_USER_ID_ = ? and PLAN_ID_ in (");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		for (String planId : planIdList) {
			sql.append(" ?,");
			parameterList.add(planId);
		}
		sql.deleteCharAt(sql.length() - 1).append(") and ipu.STATE_ != ?");
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql.toString(), parameterList);
	}


	/**
	 * 更新状态
	 *
	 * @param planId 方案id
	 * @param userId 用户id
	 * @param state  状态
	 */
	public void updateState(String planId, String userId, String state) throws SQLException {
		String sql = "UPDATE `ibm_plan_user` set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where "
				+ " APP_USER_ID_ = ? and PLAN_ID_ = ? " ;
		List<Object> parameterList = new ArrayList<>(5);
		Date nowTime = new Date();
		parameterList.add(state);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(userId);
		parameterList.add(planId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 查找用户某个游戏的方案基本信息
	 *
	 * @param gameId 游戏id
	 * @param userId 用户id
	 * @return 方案基本信息
	 */
	public List<Map<String, Object>> listPlanInfo4GameUser(String gameId, String userId) throws SQLException {
		String sql = "SELECT ip.SN_, ip.PLAN_NAME_, ip.PLAN_CODE_, ipu.BET_MODE_, ipu.MONITOR_PERIOD_, ipu.STATE_ FROM "
				+ " `ibm_plan_user` ipu LEFT JOIN ibm_plan ip ON ipu.PLAN_ID_ = ip.IBM_PLAN_ID_ WHERE "
				+ " ipu.APP_USER_ID_ = ? AND ipu.GAME_ID_ = ? AND ip.STATE_ != ? AND ipu.STATE_ != ? ORDER BY ip.SN_,ipu.OPERATE_FREQUENCY_" ;
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(userId);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);

	}

	/**
	 * 获取主页的方案列表信息
	 *
	 * @param gameIds 游戏id列表
	 * @param userId  用户id
	 * @return 主页的方案列表信息
	 */
	public Map<String, List<Map<String, Object>>> listHomeInfoOrder(List<String> gameIds, String userId) throws SQLException {

		String sql = "SELECT ip.PLAN_NAME_,ipu.STATE_,ipu.GAME_ID_ FROM `ibm_plan_user` ipu LEFT JOIN ibm_plan ip ON "
				+ " ipu.PLAN_ID_ = ip.IBM_PLAN_ID_ WHERE ipu.APP_USER_ID_ = ? AND ipu.GAME_ID_ in ( " ;
		List<Object> parameterList = new ArrayList<>(4 + gameIds.size());
		parameterList.add(userId);
		StringBuilder sqlBuilder = new StringBuilder();
		for (String gameId : gameIds) {
			sqlBuilder.append("?,");
			parameterList.add(gameId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","))
				.append(") AND ip.STATE_ != ? AND ipu.STATE_ != ? ORDER BY ip.SN_,ipu.OPERATE_FREQUENCY_ LIMIT ?");
		sql += sqlBuilder.toString();
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(10);

		List<Map<String, Object>> planInfo = super.dao.findMapList(sql, parameterList);
		Map<String,List<Map<String,Object>>> gameKeyInfo = new HashMap<>(planInfo.size());
		planInfo.forEach(info ->{
			String gameId = info.get("GAME_ID_").toString();
			info.remove("GAME_ID_");
			if(gameKeyInfo.containsKey(gameId)){
				gameKeyInfo.get(gameId).add(info);
			}else{
				List<Map<String,Object>> plan = new ArrayList<>();
				plan.add(info);
				gameKeyInfo.put(gameId,plan);
			}
		});
		return gameKeyInfo;
	}
}
