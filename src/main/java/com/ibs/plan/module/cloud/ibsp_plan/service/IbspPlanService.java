package com.ibs.plan.module.cloud.ibsp_plan.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_plan.entity.IbspPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_方案 服务类
 *
 * @author Robot
 */
public class IbspPlanService extends BaseServiceProxy {

	/**
	 * 保存IBSP_方案 对象数据
	 *
	 * @param entity IbspPlan对象数据
	 */
	public String save(IbspPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_plan 的 IBSP_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_plan set state_='DEL' where IBSP_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_plan 的 IBSP_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_plan set state_='DEL' where IBSP_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_plan  的 IBSP_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_plan where IBSP_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_plan 的 IBSP_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_plan where IBSP_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspPlan实体信息
	 *
	 * @param entity IBSP_方案 实体
	 */
	public void update(IbspPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_plan表主键查找 IBSP_方案 实体
	 *
	 * @param id ibsp_plan 主键
	 * @return IBSP_方案 实体
	 */
	public IbspPlan find(String id) throws Exception {
		return dao.find(IbspPlan.class, id);
	}

	/**
	 * 添加方案详情信息
	 *
	 * @param planInfo    方案信息
	 * @param planGameIds 方案已有游戏ids
	 * @param gameIds     游戏ids
	 * @param appUserId   用户id
	 * @param initPlan    方案初始化信息
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> savePlanItem(Map<String, Object> planInfo, List<Object> planGameIds, List<String> gameIds,
												  String appUserId,Map<String, Object> initPlan) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibs_plan_library.").append(initPlan.get("PLAN_ITEM_TABLE_NAME_")).append("(")
				.append(initPlan.get("PLAN_ITEM_TABLE_NAME_"))
				.append("_ID_,PLAN_ID_,GAME_ID_,APP_USER_ID_,PROFIT_LIMIT_MAX_,"
						+ "LOSS_LIMIT_MIN_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,"
						+ "PLAN_GROUP_DATA_,EXPAND_INFO_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,"
						+ "STATE_) VALUES");
		Date nowTime = new Date();
		List<Object> parameters = new ArrayList<>();
		for (Object gameId : planGameIds) {
			if (!gameIds.contains(gameId)) {
				continue;
			}
			//根据不同的游戏类型，得到的方案组数据也不同
			String gameType=GameUtil.type(gameId.toString());
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(planInfo.get("PLAN_ID_"));
			parameters.add(gameId);
			parameters.add(appUserId);
			parameters.add(initPlan.get("PROFIT_LIMIT_MAX_"));
			parameters.add(initPlan.get("LOSS_LIMIT_MIN_"));
			parameters.add(initPlan.get("FUNDS_LIST_"));
			parameters.add(initPlan.get("FOLLOW_PERIOD_"));
			parameters.add(initPlan.get("MONITOR_PERIOD_"));
			parameters.add(initPlan.get("BET_MODE_"));
			parameters.add(initPlan.get("FUND_SWAP_MODE_"));
			parameters.add(initPlan.get("PERIOD_ROLL_MODE_"));
			parameters.add(initPlan.get(gameType));
			parameters.add(initPlan.get("EXPAND_INFO_"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.dao.execute(sql.toString(), parameters);

		sql.delete(0, sql.length());
		parameters.clear();

		sql.append("select GAME_ID_,").append(initPlan.get("PLAN_ITEM_TABLE_NAME_")).append("_ID_ as PLAN_ITEM_ID_ from ibs_plan_library.")
				.append(initPlan.get("PLAN_ITEM_TABLE_NAME_")).append(" where APP_USER_ID_=? and STATE_=?")
				.append(" and PLAN_ID_=? and GAME_ID_ in(");
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(planInfo.get("PLAN_ID_"));
		for (Object gameId : planGameIds) {
			if (!gameIds.contains(gameId)) {
				continue;
			}
			sql.append("?,");
			parameters.add(gameId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findMapList(sql.toString(), parameters);
	}

	/**
	 * 获取方案ID
	 *
	 * @param planCode 方案编码
	 * @return 方案ID
	 */
	public String findId(String planCode) throws SQLException {
		String sql = "SELECT IBSP_PLAN_ID_ FROM `ibsp_plan` where PLAN_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSP_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 获取方案编码
	 * @param planId	方案id
	 * @return
	 */
	public String findCode(String planId) throws SQLException {
		String sql="select PLAN_CODE_ from ibsp_plan where IBSP_PLAN_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("PLAN_CODE_", sql, parameterList);
	}

	/**
	 * 获取 方案ID为key，方案编码为value的集合
	 *
	 * @return 方案集合
	 */
	public Map<String, String> mapIdCode() throws SQLException {
		String sql = "SELECT IBSP_PLAN_ID_ as key_, PLAN_CODE_ as value_ FROM ibsp_plan where STATE_ = 'OPEN'";
		return super.findKVMap(sql);
	}

	/**
	 * 获取方案排名
	 *
	 * @param planCode 方案编码
	 * @return 方案排名
	 */
	public Integer findSn(String planCode) throws SQLException {
		String sql = "SELECT SN_ FROM `ibsp_plan` where PLAN_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return NumberTool.getInteger(super.findMap(sql, parameterList), "SN_", 999);
	}

	/**
	 * 获取 方案排名为key，方案编码为value的集合
	 *
	 * @return 方案集合
	 */
	public Map<String, Integer> mapCodeSn() throws SQLException {
		String sql = "SELECT PLAN_CODE_ as key_, SN_ as value_ FROM `ibsp_plan` where STATE_ = 'OPEN'";
		return super.findKVMap(sql);
	}

	/**
	 * 获取方案的基本信息
	 *
	 * @param planCodes 方案codes
	 * @return
	 */
	public Map<String, Map<String, Object>> findPlanInfo(Set<String> planCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("select IBSP_PLAN_ID_ as PLAN_ID_,PLAN_NAME_,PLAN_CODE_,PLAN_ITEM_TABLE_NAME_,SN_ from ibsp_plan where STATE_=? and PLAN_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(1 + planCodes.size());
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameterList.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKeyMap(sql.toString(), parameterList, "PLAN_CODE_");
	}

	/**
	 * 获取方案信息
	 *
	 * @return 方案信息
	 */
	public List<Map<String, Object>> findPlanList() throws SQLException {
		String sql = "SELECT IBSP_PLAN_ID_,PLAN_NAME_,SN_,PLAN_CODE_,PLAN_ITEM_TABLE_NAME_,AVAILABLE_GAME_TYPE_ FROM `ibsp_plan` where STATE_=? ORDER BY SN_";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取分页信息
	 *
	 * @param planName  方案名称
	 * @param pageIndex 页数
	 * @param pageSize  条数
	 * @return
	 */
	public PageCoreBean<Map<String, Object>> listShow(String planName, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "select count(IBSP_PLAN_ID_) from ibsp_plan where STATE_!=? ";
		String sql = "SELECT IBSP_PLAN_ID_ PLAN_ID_,PLAN_NAME_,SN_,PLAN_CODE_,PLAN_TYPE_,AVAILABLE_GAME_TYPE_,PLAN_WORTH_T_,STATE_,DESC_ FROM `ibsp_plan` where STATE_!=? ";
		ArrayList<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		String sqlPlus = "";
		if (StringTool.notEmpty(planName)) {
			sqlPlus += " and PLAN_NAME_ like ?";
			parameterList.add("%" + planName + "%");
		}
		sqlPlus += " order by SN_";
		sqlCount += sqlPlus;
		sql += sqlPlus;
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 查找方案的适用游戏
	 * @param planCode 方案编码列表
	 * @return 适用游戏
	 */
	public  Map<String, String> findAvailableGameType(Set<Object> planCode) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT AVAILABLE_GAME_TYPE_ value_,PLAN_CODE_ key_ FROM `ibsp_plan` WHERE WHERE STATE_!=? AND PLAN_CODE_ in(");
		ArrayList<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		for (Object code:planCode){
			sql.append("?,");
			parameterList.add(code);
		}
		sql.deleteCharAt(sql.length()-1).append("?");
		return super.findKVsMap(sql,parameterList);
	}

	/**
	 * 更新方案状态
	 * @param planId
	 * @param state
	 * @throws SQLException
	 */
	public void updatePlanState(String planId, String state) throws SQLException {
		String sql = "UPDATE %s SET `UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?, `STATE_`=? WHERE PLAN_ID_=? AND STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(state);
		parameterList.add(planId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(String.format(sql,"ibsp_plan_game"),parameterList);
		super.dao.execute(String.format(sql,"ibsp_plan_hm"),parameterList);
		super.dao.execute(String.format(sql,"ibsp_plan_user"),parameterList);
	}


}
