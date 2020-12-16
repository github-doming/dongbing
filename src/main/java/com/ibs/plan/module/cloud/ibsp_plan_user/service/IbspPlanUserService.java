package com.ibs.plan.module.cloud.ibsp_plan_user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.enums.IbsModeEnum;
import com.ibs.plan.module.cloud.ibsp_plan_user.entity.IbspPlanUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_用户与方案 服务类
 *
 * @author Robot
 */
public class IbspPlanUserService extends BaseServiceProxy {

	/**
	 * 保存IBSP_用户与方案 对象数据
	 *
	 * @param entity IbspPlanUser对象数据
	 */
	public String save(IbspPlanUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_plan_user 的 IBSP_PLAN_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_plan_user set state_='DEL' where IBSP_PLAN_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PLAN_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_plan_user 的 IBSP_PLAN_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_plan_user set state_='DEL' where IBSP_PLAN_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_plan_user  的 IBSP_PLAN_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_plan_user where IBSP_PLAN_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PLAN_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_plan_user 的 IBSP_PLAN_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_plan_user where IBSP_PLAN_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspPlanUser实体信息
	 *
	 * @param entity IBSP_用户与方案 实体
	 */
	public void update(IbspPlanUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_plan_user表主键查找 IBSP_用户与方案 实体
	 *
	 * @param id ibsp_plan_user 主键
	 * @return IBSP_用户与方案 实体
	 */
	public IbspPlanUser find(String id) throws Exception {
		return dao.find(IbspPlanUser.class, id);
	}

	/**
	 * 获取主页的方案列表信息
	 *
	 * @param gameIds   游戏id列表
	 * @param appUserId 用户id
	 * @return 主页的方案列表信息
	 */
	public Map<String, List<Map<String, Object>>> listHomeShowInfo(List<String> gameIds, String appUserId) throws SQLException {
		String sql = "SELECT ip.PLAN_NAME_,ipu.PLAN_STATE_,ipu.GAME_ID_,ipu.SN_ FROM ibsp_plan_user ipu "
				+ " LEFT JOIN ibsp_plan ip ON ipu.PLAN_ID_ = ip.IBSP_PLAN_ID_ "
				+ " WHERE ipu.APP_USER_ID_ = ? AND ipu.GAME_ID_ in ( ";
		List<Object> parameterList = new ArrayList<>(4 + gameIds.size());
		parameterList.add(appUserId);
		StringBuilder sqlBuilder = new StringBuilder();
		for (String gameId : gameIds) {
			sqlBuilder.append("?,");
			parameterList.add(gameId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","))
				.append(") AND ip.STATE_ = ? AND ipu.STATE_ = ? ORDER BY ipu.SN_,ipu.OPERATE_FREQUENCY_ LIMIT ?");
		sql += sqlBuilder.toString();
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(10 * gameIds.size());
		List<Map<String, Object>> planInfo = super.findMapList(sql, parameterList);
		Map<String, List<Map<String, Object>>> gameKeyInfo = new HashMap<>(planInfo.size());
		planInfo.forEach(info -> {
			info.put("SN_", String.format("%03d", Integer.parseInt(info.get("SN_").toString())));
			String gameId = info.remove("GAME_ID_").toString();
			info.remove("ROW_NUM");
			if (gameKeyInfo.containsKey(gameId)) {
				gameKeyInfo.get(gameId).add(info);
			} else {
				List<Map<String, Object>> plan = new ArrayList<>();
				plan.add(info);
				gameKeyInfo.put(gameId, plan);
			}
		});
		return gameKeyInfo;
	}

	/**
	 * 获取用户方案信息
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public List<Map<String, Object>> findPlanByUserId(String appUserId) throws SQLException {
		String sql = "SELECT STATE_,PLAN_ID_,GAME_ID_,PLAN_ITEM_TABLE_NAME_,PLAN_ITEM_TABLE_ID_"
				+ " FROM ibsp_plan_user WHERE APP_USER_ID_=? AND STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户方案信息
	 *
	 * @param userId   用户id
	 * @param gameId   游戏id
	 * @param planList 方案列表
	 * @return
	 */
	public List<Map<String, Object>> findPlanByUserId(String userId, String gameId, Set<String> planList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT PLAN_ID_,PLAN_ITEM_TABLE_NAME_,PLAN_ITEM_TABLE_ID_"
				+ " FROM ibsp_plan_user WHERE APP_USER_ID_=? and GAME_ID_=? AND STATE_!=? AND PLAN_ID_ in(");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());

		for (String planId : planList) {
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 获取方案详情止盈止损信息
	 *
	 * @param userPlanInfo 用户方案信息
	 * @return
	 */
	public List<Map<String, Object>> findPlanLimit(List<Map<String, Object>> userPlanInfo) throws SQLException {
		String sql = "select PLAN_ID_,PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_,GAME_ID_ from ibs_plan_library.";

		StringBuilder sqlPlus = new StringBuilder();
		List<Map<String, Object>> list = new ArrayList<>();
		List<Object> parameterList = new ArrayList<>(2);
		for (Map<String, Object> plan : userPlanInfo) {
			sqlPlus.append(plan.get("PLAN_ITEM_TABLE_NAME_")).append(" where ")
					.append(plan.get("PLAN_ITEM_TABLE_NAME_")).append("_ID_ =?");
			parameterList.add(plan.get("PLAN_ITEM_TABLE_ID_"));
			list.add(super.dao.findMap(sql.concat(sqlPlus.toString()), parameterList));
			parameterList.clear();
			sqlPlus.delete(0, sqlPlus.length());
		}
		return list;
	}

	/**
	 * 获取方案详情信息
	 *
	 * @param planInfo 方案信息
	 * @return
	 */
	public List<Map<String, Object>> findPlanItems(List<Map<String, Object>> planInfo) throws SQLException {
		String sql = "select FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,"
				+ "PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_,EXPAND_INFO_ from ibs_plan_library.";

		List<Map<String, Object>> list = new ArrayList<>();
		StringBuilder sqlPlus = new StringBuilder();
		List<Object> parameterList = new ArrayList<>(2);

		PlanUtil.Code planCode;
		JSONObject activePlanGroup;
		for (Map<String, Object> plan : planInfo) {
			parameterList.add(plan.get("PLAN_ITEM_TABLE_ID_"));
			sqlPlus.append(plan.get("PLAN_ITEM_TABLE_NAME_")).append(" where ")
					.append(plan.get("PLAN_ITEM_TABLE_NAME_")).append("_ID_ =?");
			Map<String, Object> map = super.dao.findMap(sql.concat(sqlPlus.toString()), parameterList);

			planCode = PlanUtil.Code.valueOf(plan.get("PLAN_CODE_").toString());
			if(StringTool.notEmpty(map.get("PLAN_GROUP_ACTIVE_KEY_"))){
				String[] activeKeys = map.remove("PLAN_GROUP_ACTIVE_KEY_").toString().split(",");
				JSONObject planGroupData = JSONObject.parseObject(map.remove("PLAN_GROUP_DATA_").toString());
				activePlanGroup = planCode.getPlan().advance(activeKeys, planGroupData);
				map.put("PLAN_GROUP_ACTIVE_DATA_", JSON.toJSONString(activePlanGroup, SerializerFeature.DisableCircularReferenceDetect));
			}else{
				map.put("PLAN_GROUP_ACTIVE_DATA_", "");
			}
			map.put("PLAN_CODE_", plan.get("PLAN_CODE_"));
			map.put("PLAN_ITEM_TABLE_ID_", plan.get("PLAN_ITEM_TABLE_ID_"));
			list.add(map);
			parameterList.clear();
			sqlPlus.delete(0, sqlPlus.length());
		}
		return list;
	}

	/**
	 * 获取用户方案信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 方案信息
	 */
	public List<Map<String, Object>> findPlanInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT ig.GAME_CODE_,ipu.PLAN_CODE_,ipu.PLAN_STATE_,ipu.PLAN_ITEM_TABLE_NAME_,"
				+ " ipu.PLAN_ITEM_TABLE_ID_ FROM ibsp_plan_user ipu LEFT JOIN ibsp_handicap_member ihm ON ipu.APP_USER_ID_ = ihm.APP_USER_ID_"
				+ " LEFT JOIN ibsp_game ig ON ipu.GAME_ID_ = ig.IBSP_GAME_ID_ "
				+ " WHERE ihm.IBSP_HANDICAP_MEMBER_ID_ =? and ipu.PLAN_STATE_=? AND ipu.STATE_ =?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取方案列表信息
	 *
	 * @param appUserId 用户id
	 * @param gameId    游戏id
	 * @param planCodes 方案codes
	 * @return
	 */
	public List<Map<String, Object>> findPlanInfo(String appUserId, String gameId, String[] planCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("select ipu.PLAN_CODE_,ipu.PLAN_ITEM_TABLE_NAME_,ipu.PLAN_ITEM_TABLE_ID_ FROM ibsp_plan_user ipu"
				+ " WHERE ipu.APP_USER_ID_=? and GAME_ID_=? and PLAN_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameterList);
	}

	/**
	 * 查找用户某个游戏的方案基本信息
	 *
	 * @param gameId    游戏id
	 * @param appUserId 用户id
	 * @return 方案基本信息
	 */
	public List<Map<String, Object>> listInfo(String appUserId, String gameId) throws SQLException {
		String sql = "SELECT PLAN_CODE_,SN_,PLAN_NAME_,BET_MODE_,MONITOR_PERIOD_,PLAN_STATE_ FROM `ibsp_plan_user` "
				+ " where APP_USER_ID_ = ? and GAME_ID_ = ? and STATE_ = ? ORDER BY SN_,CREATE_TIME_LONG_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户所有的方案编码
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public List<String> findPlanCodeByUserId(String appUserId) throws SQLException {
		String sql = "SELECT PLAN_CODE_ from ibsp_plan_user where APP_USER_ID_ = ? and state_ =? GROUP BY PLAN_CODE_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return dao.findStringList("PLAN_CODE_", sql, parameterList);
	}

	/**
	 * 添加方案用户信息
	 *
	 * @param planCode  方案编码
	 * @param planInfo  方案信息
	 * @param planItems 方案详情信息
	 * @param appUserId 用户id
	 * @throws SQLException
	 */
	public void saveRegister(String planCode, Map<String, Object> planInfo, List<Map<String, Object>> planItems, String appUserId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibsp_plan_user(IBSP_PLAN_USER_ID_,APP_USER_ID_,PLAN_ID_,GAME_ID_,PLAN_ITEM_TABLE_NAME_,"
				+ "PLAN_ITEM_TABLE_ID_,PLAN_CODE_,PLAN_NAME_,BET_MODE_,MONITOR_PERIOD_,PLAN_STATE_,SN_,OPERATE_FREQUENCY_,"
				+ "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
		List<Object> parameterList = new ArrayList<>(2);
		Date nowTime = new Date();
		for (Map<String, Object> planItem : planItems) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(appUserId);
			parameterList.add(planInfo.get("PLAN_ID_"));
			parameterList.add(planItem.get("GAME_ID_"));
			parameterList.add(planInfo.get("PLAN_ITEM_TABLE_NAME_"));
			parameterList.add(planItem.get("PLAN_ITEM_ID_"));
			parameterList.add(planCode);
			parameterList.add(planInfo.get("PLAN_NAME_"));
			parameterList.add(IbsModeEnum.BET_MODE_REGULAR.name());
			parameterList.add(0);
			parameterList.add(IbsStateEnum.CLOSE.name());
			parameterList.add(planItem.get("SN_"));
			parameterList.add(0);
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(IbsStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 获取用户方案信息
	 *
	 * @param appUserId 用户id
	 * @param planCode  方案编码
	 * @param gameId    游戏id
	 * @return
	 */
	public IbspPlanUser findEntity(String appUserId, String planCode, String gameId) throws Exception {
		String sql = "select * from ibsp_plan_user where APP_USER_ID_=? and PLAN_CODE_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(planCode);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspPlanUser.class, sql, parameterList);
	}

	/**
	 * 批量修改用户方案状态
	 *
	 * @param appUserId 用户id
	 * @param planCodes 方案编码列表
	 * @param gameId    游戏id
	 * @param planState 方案状态
	 * @return
	 */
	public boolean updatePlanState(String appUserId, String[] planCodes, String gameId, String planState) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBSP_PLAN_USER_ID_ from ibsp_plan_user where APP_USER_ID_=? and GAME_ID_=? and STATE_=? and PLAN_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<String> planUserIds = super.dao.findStringList("IBSP_PLAN_USER_ID_", sql.toString(), parameterList);
		if (ContainerTool.isEmpty(planUserIds)) {
			return false;
		}
		sql.delete(0, sql.length());
		parameterList.clear();

		sql.append("update ibsp_plan_user set PLAN_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSP_PLAN_USER_ID_ in(");
		parameterList.add(planState);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String planUserId : planUserIds) {
			sql.append("?,");
			parameterList.add(planUserId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.execute(sql.toString(), parameterList) > 0;
	}

	/**
	 * 获取用户方案详情表信息
	 *
	 * @param appUserId 用户id
	 * @param planCode  方案编码
	 * @param gameId    游戏id
	 * @return
	 */
	public Map<String, Object> findItemTable(String appUserId, String planCode, String gameId) throws SQLException {
		String sql = "select PLAN_ITEM_TABLE_NAME_,PLAN_ITEM_TABLE_ID_,PLAN_STATE_,PLAN_NAME_ from ibsp_plan_user where APP_USER_ID_=? and"
				+ " GAME_ID_=? and PLAN_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 修改投注模式
	 *
	 * @param appUserId     用户id
	 * @param planCode      方案code
	 * @param gameId        游戏id
	 * @param betMode       投注模式
	 * @param monitorPeriod 监控期数
	 */
	public void updateItemInfo(String appUserId, String planCode, String gameId, String betMode, String monitorPeriod) throws SQLException {
		String sql = "update ibsp_plan_user set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		if (StringTool.notEmpty(betMode)) {
			sql += ",BET_MODE_=?";
			parameterList.add(betMode);
		}
		if (StringTool.notEmpty(monitorPeriod)) {
			sql += ",MONITOR_PERIOD_=?";
			parameterList.add(NumberTool.getInteger(monitorPeriod));
		}
		sql += " where APP_USER_ID_=? and GAME_ID_=? and PLAN_CODE_=? and STATE_=?";
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取方案用户信息
	 *
	 * @param appUserId 用户id
	 * @param gameId    游戏id
	 * @return
	 */
	public Map<String, Object> listPlanInfos(String appUserId, String gameId) throws SQLException {
		String sql = "select PLAN_ITEM_TABLE_NAME_ as key_,PLAN_ITEM_TABLE_ID_ as value_ from ibsp_plan_user where"
				+ " APP_USER_ID_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 清除用户游戏方案
	 *
	 * @param appUserId 用户id
	 * @param gameId    游戏id
	 */
	public void clearPlanGame(String appUserId, String gameId) throws SQLException {
		String sql = "update ibsp_plan_user set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?"
				+ " and GAME_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取存在游戏方案
	 *
	 * @param appUserId 用户id
	 * @param gameId    游戏id
	 * @return
	 */
	public List<String> findExistPlan(String appUserId, String gameId) throws SQLException {
		String sql = "select PLAN_CODE_ from ibsp_plan_user where APP_USER_ID_=? and GAME_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_CODE_", sql, parameterList);
	}

	/**
	 * 清除用户方案
	 *
	 * @param appUserId 用户id
	 * @param planIds   方案Id列表
	 */
	public void clearPlanUser(String appUserId, List<String> planIds) throws SQLException {
		StringBuilder sql = new StringBuilder("update %s set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?"
				+ "  and STATE_!=? AND PLAN_ID_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.DEL.name());
		if (ContainerTool.notEmpty(planIds)) {
			for (String planId : planIds) {
				sql.append("?,");
				parameterList.add(planId);
			}
			sql.deleteCharAt(sql.length() - 1);
		}

		String bestSql = sql.append(")").toString();

		super.dao.execute(String.format(bestSql, "ibsp_plan_user"), parameterList);
		super.dao.execute(String.format(bestSql, "ibsp_plan_hm"), parameterList);
		super.dao.execute(String.format(bestSql, "ibsp_profit_plan"), parameterList);
		super.dao.execute(String.format(bestSql, "ibsp_profit_plan_vr"), parameterList);
	}

	/**
	 * 清理用户方案信息
	 *
	 * @param userIds   用户Id列表
	 * @param planId    方案Id
	 * @param tableName 表名
	 */
	public void clearUserPlanInfo(List<String> userIds, String planId, String tableName) throws SQLException {
		StringBuilder sql = new StringBuilder("update ibs_plan_library.%s set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where  PLAN_ID_=?  and STATE_!=? AND APP_USER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planId);
		parameterList.add(IbsStateEnum.DEL.name());
		for (String userId : userIds) {
			sql.append("?,");
			parameterList.add(userId);
		}
		sql.deleteCharAt(sql.length() - 1).append(")");

		super.dao.execute(String.format(sql.toString(), tableName), parameterList);
	}

	/**
	 * 查找用方案游戏
	 *
	 * @param appUserId 用户id
	 * @return 游戏id集合
	 */
	public List<String> listGameIds(String appUserId) throws SQLException {
		String sql = "SELECT GAME_ID_ key_ FROM `ibsp_plan_user` where APP_USER_ID_ = ?  and STATE_ = ? GROUP BY GAME_ID_ ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbsStateEnum.OPEN.name());

		return super.findStringList(sql, parameterList);
	}

	/**
	 * 获取方案次序信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param gameId           游戏id
	 * @return
	 */
	public Map<String, Object> findPlanSns(String handicapMemberId, String gameId) throws SQLException {
		String sql = "select ipu.PLAN_CODE_ as key_,ipu.SN_ as value_ from ibsp_plan_user ipu left join ibsp_handicap_member ihm"
				+ " on ipu.APP_USER_ID_=ihm.APP_USER_ID_ where ihm.IBSP_HANDICAP_MEMBER_ID_=? and ipu.GAME_ID_=? and ipu.STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 查找用户游戏方案最大序号
	 *
	 * @param userId
	 * @param gameId
	 * @return
	 * @throws SQLException
	 */
	public String findUserPlanMaxSn(String userId, String gameId) throws SQLException {
		String sql = "SELECT MAX(SN_) key_ FROM `ibsp_plan_user` WHERE APP_USER_ID_ = ? AND GAME_ID_ = ? and STATE_!=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());
		return findString(sql, parameterList);
	}

}
