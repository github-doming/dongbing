package com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
* IBSP_模式智能切换 服务类
 * @author Robot
 */
public class IbspHmModeCutoverService extends BaseServiceProxy {

	/**
	 * 保存IBSP_模式智能切换 对象数据
	 * @param entity IbspHmModeCutover对象数据
	 */
	public String save(IbspHmModeCutover entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_hm_mode_cutover 的 IBSP_HM_MODE_CUTOVER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_hm_mode_cutover set state_='DEL' where IBSP_HM_MODE_CUTOVER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HM_MODE_CUTOVER_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_hm_mode_cutover 的 IBSP_HM_MODE_CUTOVER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_hm_mode_cutover set state_='DEL' where IBSP_HM_MODE_CUTOVER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_hm_mode_cutover  的 IBSP_HM_MODE_CUTOVER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_hm_mode_cutover where IBSP_HM_MODE_CUTOVER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HM_MODE_CUTOVER_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_hm_mode_cutover 的 IBSP_HM_MODE_CUTOVER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_hm_mode_cutover where IBSP_HM_MODE_CUTOVER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHmModeCutover实体信息
	 * @param entity IBSP_模式智能切换 实体
	 */
	public void update(IbspHmModeCutover entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_hm_mode_cutover表主键查找 IBSP_模式智能切换 实体
	 * @param id ibsp_hm_mode_cutover 主键
	 * @return IBSP_模式智能切换 实体
	 */
	public IbspHmModeCutover find(String id) throws Exception {
		return dao.find(IbspHmModeCutover.class,id);
	}

	/**
	 * 获取模式切换数据组
	 * @param handicapMemberId	盘口会员id
	 * @return
	 */
	public Map<String,Object> findCutoverGroupData(String handicapMemberId) throws SQLException {
		String sql="select CUTOVER_GROUP_DATA_,CUTOVER_KEY_ from ibsp_hm_mode_cutover where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>(2);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql,parameters);
	}

	/**
	 * 获取实体对象
	 * @param handicapMemberId	盘口会员id
	 * @return
	 */
	public IbspHmModeCutover findEntity(String handicapMemberId) throws Exception {
		String sql="select * from ibsp_hm_mode_cutover where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>(2);
		parameters.add(handicapMemberId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbspHmModeCutover.class,sql,parameters);
	}

	/**
	 *
	 * @return
	 */
	public String findInitInfo() throws SQLException {
		String sql="select CUTOVER_GROUP_DATA_ from ibsp_hm_mode_cutover where STATE_=?";
		List<Object> parameters=new ArrayList<>(2);
		parameters.add(IbsStateEnum.DEF.name());
		return super.dao.findString("CUTOVER_GROUP_DATA_",sql,parameters);
	}

	/**
	 * 真实转模拟
	 * @param handicapMemberIds	盘口会员ids
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> listCutoverInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds,IbsTypeEnum.REAL,IbsTypeEnum.VIRTUAL);
	}
	/**
	 * 模拟转真实
	 * @param handicapMemberIds	盘口会员ids
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> listCutoverVrInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds,IbsTypeEnum.VIRTUAL,IbsTypeEnum.REAL);
	}

	/**
	 * 真实转停止投注
	 * @param handicapMemberIds	盘口会员ids
	 * @return
	 * @throws SQLException
	 */
	public Map<String, List<Map<String, Object>>> listCutoverStopInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds,IbsTypeEnum.REAL,IbsTypeEnum.STOP);
	}

	/**
	 * 模拟转停止投注
	 * @param handicapMemberIds  盘口会员ids
	 * @return
	 * @throws SQLException
	 */
	public Map<String, List<Map<String, Object>>> listCutoverVrStopInfo(Set<String> handicapMemberIds) throws SQLException {
		return listCutoverInfo(handicapMemberIds,IbsTypeEnum.VIRTUAL,IbsTypeEnum.STOP);
	}

	/**
	 * 获取切换会员游戏信息
	 *
	 * @param handicapMemberIds 盘口会员ids
	 * @param currentMode       当前模式
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> listCutoverInfo(Set<String> handicapMemberIds,
		 IbsTypeEnum currentMode,IbsTypeEnum cutoverMode) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ihgs.HANDICAP_MEMBER_ID_,ihgs.GAME_ID_,ihgs.IBSP_HM_GAME_SET_ID_ FROM ibsp_hm_game_set ihgs")
				.append(" LEFT JOIN ibsp_hm_mode_cutover ihmc ON ihgs.HANDICAP_MEMBER_ID_ = ihmc.HANDICAP_MEMBER_ID_")
				.append(" WHERE ihgs.BET_STATE_ =? AND ihgs.BET_MODE_ =? and ihmc.CURRENT_=? and ihmc.CUTOVER_=?")
				.append(" AND ((ihmc.PROFIT_T_ > ihmc.CUTOVER_PROFIT_T_ AND ihmc.CUTOVER_PROFIT_T_ != 0) OR")
				.append(" (ihmc.PROFIT_T_ < ihmc.CUTOVER_LOSS_T_ AND ihmc.CUTOVER_LOSS_T_ != 0)) AND ihgs.HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsTypeEnum.TRUE.name());
		parameterList.add(currentMode);
		parameterList.add(currentMode);
		parameterList.add(cutoverMode);
		for (Object handicapMemberId : handicapMemberIds) {
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKeyMaps(sql.toString(), parameterList,"HANDICAP_MEMBER_ID_");
	}

	/**
	 * 修改模式切换信息
	 * @param cutoverMap	会员模式切换信息
	 */
	public void updateCutoverInfo(Map<String, JSONObject> cutoverMap) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsp_hm_mode_cutover set PROFIT_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,CURRENT_INDEX_ CASE HANDICAP_MEMBER_ID_");
		List<Object> parameters = new ArrayList<>();
		parameters.add(0);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());

		StringBuilder sqlPlus = new StringBuilder();
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("CURRENT_INDEX_",-1));

			sqlPlus.append("?,");
		}
		sql.append(" end,PROFIT_T_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.get("PROFIT_T_"));
		}
		sql.append(" end,CURRENT_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("CURRENT_",""));
		}
		sql.append(" end,CUTOVER_PROFIT_T_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("CUTOVER_PROFIT_T_",0));
		}
		sql.append(" end,CUTOVER_LOSS_T_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("CUTOVER_LOSS_T_",0));
		}
		sql.append(" end,CUTOVER_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("CUTOVER_",""));
		}
		sql.append(" end,RESET_PROFIT_ = CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String, JSONObject> entry:cutoverMap.entrySet()){
			sql.append(" WHEN ? THEN ?");
			String handicapMemberId=entry.getKey();
			JSONObject cutoverInfo=entry.getValue();
			parameters.add(handicapMemberId);
			parameters.add(cutoverInfo.getOrDefault("RESET_PROFIT_","FALSE"));
		}
		sql.append(" end where HANDICAP_MEMBER_ID_ in (");
		sql.append(sqlPlus);
		parameters.addAll(cutoverMap.keySet());
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 停止模式切换
	 * @param handicapMemberIds	盘口会员ids
	 */
	public void stopCutover(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsp_hm_mode_cutover set PROFIT_T_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,CURRENT_INDEX_=?"
				+ " where HANDICAP_MEMBER_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		parameters.add(0);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(-1);
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameters.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameters);
	}

	/**
	 * 修改盈亏信息
	 * @param cutoverHmProfits	轮盘会员盈亏信息
	 * @param nowTime				当前时间
	 */
	public void update4Settlement(Map<String, Object> cutoverHmProfits, Date nowTime) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsp_hm_mode_cutover set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,PROFIT_T_= PROFIT_T_+ CASE HANDICAP_MEMBER_ID_ ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		StringBuilder sqlPlus = new StringBuilder();
		for(Map.Entry<String,Object> entry:cutoverHmProfits.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue());
			sqlPlus.append("?,");
		}
		sql.append(" end where STATE_ = ? and HANDICAP_MEMBER_ID_ in (");
		parameters.add(IbsStateEnum.OPEN.name());
		sql.append(sqlPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		parameters.addAll(cutoverHmProfits.keySet());
		super.dao.execute(sql.toString(), parameters);
	}
}
