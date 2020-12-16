package com.ibs.plan.module.cloud.ibsp_plan_hm.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_plan_hm.entity.IbspPlanHm;
import com.ibs.plan.module.cloud.ibsp_plan_user.entity.IbspPlanUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员与方案 服务类
 *
 * @author Robot
 */
public class IbspPlanHmService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员与方案 对象数据
	 *
	 * @param entity IbspPlanHm对象数据
	 */
	public String save(IbspPlanHm entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_plan_hm 的 IBSP_PLAN_HM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_plan_hm set state_='DEL' where IBSP_PLAN_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_plan_hm 的 IBSP_PLAN_HM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_plan_hm set state_='DEL' where IBSP_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_plan_hm  的 IBSP_PLAN_HM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_plan_hm where IBSP_PLAN_HM_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PLAN_HM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_plan_hm 的 IBSP_PLAN_HM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_plan_hm where IBSP_PLAN_HM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspPlanHm实体信息
	 *
	 * @param entity IBSP_盘口会员与方案 实体
	 */
	public void update(IbspPlanHm entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_plan_hm表主键查找 IBSP_盘口会员与方案 实体
	 *
	 * @param id ibsp_plan_hm 主键
	 * @return IBSP_盘口会员与方案 实体
	 */
	public IbspPlanHm find(String id) throws Exception {
		return dao.find(IbspPlanHm.class, id);
	}

	/**
	 * 初始化方案会员信息
	 *
	 * @param handicapMember 盘口会员
	 * @param userPlanInfo   用户方案信息
	 */
	public void initPlanHm(IbspHandicapMember handicapMember, List<Map<String, Object>> userPlanInfo,
			Map<String, String> planHmInfo) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ibsp_plan_hm(IBSP_PLAN_HM_ID_,APP_USER_ID_,HANDICAP_MEMBER_ID_,PLAN_ID_,GAME_ID_,"
				+ "PLAN_ITEM_TABLE_NAME_,PLAN_ITEM_TABLE_ID_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
		List<Object> parameterList = new ArrayList<>();

		Date nowTime = new Date();
		for (Map<String, Object> plan : userPlanInfo) {
			if (planHmInfo.containsKey(plan.get("PLAN_ITEM_TABLE_ID_"))) {
				continue;
			}
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?),");
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(handicapMember.getAppUserId());
			parameterList.add(handicapMember.getIbspHandicapMemberId());
			parameterList.add(plan.get("PLAN_ID_"));
			parameterList.add(plan.get("GAME_ID_"));
			parameterList.add(plan.get("PLAN_ITEM_TABLE_NAME_"));
			parameterList.add(plan.get("PLAN_ITEM_TABLE_ID_"));
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(nowTime);
			parameterList.add(nowTime.getTime());
			parameterList.add(plan.get("STATE_"));
		}
		if (parameterList.size() > 11) {
			sql.delete(sql.length() - 1, sql.length());
			super.dao.execute(sql.toString(), parameterList);
		}
	}

	/**
	 * 获取会员方案信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return
	 */
	public Map<String, String> findPlanHm(String handicapMemberId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select PLAN_ITEM_TABLE_ID_ as key_,IBSP_PLAN_HM_ID_ as value_ from ibsp_plan_hm where HANDICAP_MEMBER_ID_=?"
						+ " and STATE_!=?");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		Map<String, String> planHmMap = super.findKVMap(sql.toString(), parameterList);
		if (ContainerTool.notEmpty(planHmMap)) {
			parameterList.clear();
			sql.delete(0, sql.length());
			sql.append(
					"update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where STATE_=? and IBSP_PLAN_HM_ID_ in(");
			parameterList.add(IbsStateEnum.OPEN.name());
			parameterList.add(new Date());
			parameterList.add(System.currentTimeMillis());
			parameterList.add(IbsStateEnum.CLOSE.name());
			for (Map.Entry<String, String> entry : planHmMap.entrySet()) {
				sql.append("?,");
				parameterList.add(entry.getValue());
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
			super.dao.execute(sql.toString(), parameterList);
		}
		return planHmMap;
	}

	/**
	 * 更新状态
	 * @param planHmIds 会员主键数组
	 * @param nowTime 更新时间
	 * @param newState 新状态
	 * @param oldState 旧状态
	 * @param desc 描述
	 */
	public void updateState(List<String> planHmIds, Date nowTime, IbsStateEnum newState, IbsStateEnum oldState,
			String desc) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameterList = new ArrayList<>();
		sql.append("update ibsp_plan_hm set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where STATE_ = ? and IBSP_PLAN_HM_ID_ in(");
		parameterList.add(newState.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(oldState.name());
		for (String planHmId : planHmIds) {
			sql.append("?,");
			parameterList.add(planHmId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}

	/**
	 * 删除用户相关会员的方案信息
	 * @param appUserId	用户id
	 * @param gameId		游戏id
	 */
	public void clearPlanHm(String appUserId, String gameId) throws SQLException {
		String sql="update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?"
				+ " and GAME_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 删除会员游戏方案
	 * @param planUser	方案用户
	 */
	public void delPlanHm(IbspPlanUser planUser) throws SQLException {
		String sql="update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?"
				+ " and PLAN_ID_=? and GAME_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planUser.getAppUserId());
		parameterList.add(planUser.getPlanId());
		parameterList.add(planUser.getGameId());
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 关闭会员方案状态
	 * @param handicapMemberId	盘口会员id
	 */
	public void closeHmPlan(String handicapMemberId) throws SQLException {
		String sql="update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 获取会员方案信息
	 * @param handicapMemberId	盘口会员id
	 * @return
	 */
	public Map<String,Map<String,Object>> findPlanHmInfo(String handicapMemberId) throws SQLException {
		String sql="select IBSP_PLAN_HM_ID_ as PLAN_HM_ID_,PLAN_ID_,GAME_ID_ from ibsp_plan_hm where HANDICAP_MEMBER_ID_=?"
				+ " and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.DEL.name());
		List<Map<String, Object>> list=super.dao.findMapList(sql,parameterList);

		Map<String,Map<String,Object>> hmInfo=new HashMap<>(list.size());
		for (Map<String,Object> map: list){
			String planId=map.get("PLAN_ID_").toString();
			String gameId=map.get("GAME_ID_").toString();
			if(hmInfo.containsKey(planId)){
				hmInfo.get(planId).put(gameId,map.get("PLAN_HM_ID_"));
			}else{
				Map<String,Object> gameInfoMap=new HashMap<>(4);
				gameInfoMap.put(gameId,map.get("PLAN_HM_ID_"));
				hmInfo.put(planId,gameInfoMap);
			}
		}
		return hmInfo;
	}

	/**
	 * 修改方案会员状态
	 *
	 * @param existInfos 已存在会员信息
	 * @param planId     方案id
	 * @param gameId     游戏id
	 * @param planState  方案状态
	 */
	public void updateState(List<Map<String,Object>> existInfos, String planId, String gameId, String planState) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
				+ " PLAN_ID_=? and GAME_ID_=? and STATE_!=? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(planState);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(planId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.DEL.name());
		for(Map<String,Object> existInfo:existInfos){
			sql.append("?,");
			parameterList.add(existInfo.get("HANDICAP_MEMBER_ID_"));
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}

	/**
	 * 删除用户相关会员的方案信息
	 * @param appUserId	用户id
	 * @param planIds		方案Id列表
	 */
	public void clearPlanHmByPlanIds(String appUserId, List<String> planIds) throws SQLException {
		StringBuilder sql= new StringBuilder("update ibsp_plan_hm set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where APP_USER_ID_=?"
				+ "  and STATE_!=? and GAME_ID_ in(");
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);

		parameterList.add(IbsStateEnum.DEL.name());
		for(String planId:planIds){
			sql.append("?,");
			parameterList.add(planId);
		}
		sql.deleteCharAt(sql.length()-1).append(")");
		super.dao.execute(sql.toString(),parameterList);
	}


}
