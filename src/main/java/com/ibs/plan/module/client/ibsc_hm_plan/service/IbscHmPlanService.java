package com.ibs.plan.module.client.ibsc_hm_plan.service;


import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_hm_plan.entity.IbscHmPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC客户端_盘口会员与方案 服务类
 *
 * @author Robot
 */
public class IbscHmPlanService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_盘口会员与方案 对象数据
	 *
	 * @param entity IbscHmPlan对象数据
	 */
	public String save(IbscHmPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_hm_plan 的 IBSC_HM_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_hm_plan set state_='DEL' where IBSC_HM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_HM_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_hm_plan 的 IBSC_HM_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_hm_plan set state_='DEL' where IBSC_HM_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_hm_plan  的 IBSC_HM_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_hm_plan where IBSC_HM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_HM_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_hm_plan 的 IBSC_HM_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_hm_plan where IBSC_HM_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHmPlan实体信息
	 *
	 * @param entity IBSC客户端_盘口会员与方案 实体
	 */
	public void update(IbscHmPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_hm_plan表主键查找 IBSC客户端_盘口会员与方案 实体
	 *
	 * @param id ibsc_hm_plan 主键
	 * @return IBSC客户端_盘口会员与方案 实体
	 */
	public IbscHmPlan find(String id) throws Exception {
		return (IbscHmPlan) dao.find(IbscHmPlan.class, id);

	}

	/**
	 * 获取开启的方案信息
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 游戏类型
	 * @return
	 */
	public List<String> getOpenItemIds(GameUtil.Code gameCode, String drawType) throws SQLException {
		String sql = "SELECT ihp.PLAN_ITEM_ID_ FROM ibsc_hm_plan ihp"
				+ " LEFT JOIN ibsc_hm_game_set ihgs ON ihp.EXIST_HM_ID_=ihgs.EXIST_HM_ID_"
				+ " AND ihp.GAME_CODE_=ihgs.GAME_CODE_"
				+ " WHERE ihp.GAME_CODE_ =? AND ihgs.GAME_DRAW_TYPE_ =? AND ihp.PLAN_STATE_ =?"
				+ " AND ihp.STATE_ =? AND ihgs.BET_STATE_=? AND ihgs.STATE_ =?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(gameCode.name());
		parameters.add(drawType);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsTypeEnum.TRUE.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_ITEM_ID_", sql, parameters);
	}
	/**
	 * 获取开启的方案
	 * @param existHmId	已存在盘口会员id
	 * @param gameCode	游戏编码
	 * @return
	 */
	public List<String> getOpenItemId(String existHmId, String gameCode) throws SQLException {
		String sql="SELECT PLAN_ITEM_ID_ from ibsc_hm_plan where EXIST_HM_ID_=? and GAME_CODE_=? and PLAN_STATE_=? AND STATE_ =?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_ITEM_ID_", sql, parameters);
	}
	/**
	 * 判断是否存在游戏方案
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param planCode  方案编码
	 * @param gameCode  游戏编码
	 * @return
	 */
	public Map<String, Object> isExistPlan(String existHmId, String planCode, String gameCode) throws SQLException {
		String sql = "SELECT IBSC_HM_PLAN_ID_ as HM_PLAN_ID_,IBSC_PLAN_ITEM_ID_ as PLAN_ITEM_ID_,ihp.PLAN_CODE_ FROM ibsc_hm_plan ihp "
				+ " LEFT JOIN ibsc_plan_item ipi ON ihp.PLAN_ITEM_ID_=ipi.IBSC_PLAN_ITEM_ID_"
				+ " WHERE ihp.EXIST_HM_ID_=? AND ihp.PLAN_CODE_=? AND ihp.GAME_CODE_=? and ipi.STATE_=? and ihp.STATE_=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(existHmId);
		parameters.add(planCode);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 修改方案状态
	 *
	 * @param hmPlanId  会员方案id
	 * @param planState 方案状态
	 */
	public void updatePlanState(Object hmPlanId, String planState) throws SQLException {
		String sql = "update ibsc_hm_plan set PLAN_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSC_HM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(planState);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(hmPlanId);
		super.dao.execute(sql, parameters);
	}

	/**
	 * 修改方案状态
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏编码
	 * @param planCodes 方案codes
	 * @param planState 方案状态
	 */
	public void updatePlanState(String existHmId, String gameCode, String[] planCodes, String planState) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("update ibsc_hm_plan set PLAN_STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where EXIST_HM_ID_=?"
				+ " and GAME_CODE_=? and STATE_=? and PLAN_CODE_ in(");
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(planState);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(existHmId);
		parameters.add(gameCode);
		for (String planCode : planCodes) {
			sql.append("?,");
			parameters.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(),parameters);
	}

	/**
	 * 获取方案信息
	 *
	 * @param existHmId 已存在会员id
	 * @param gameCode  游戏编码
	 * @param planCodes 盘口codes
	 */
	public Map<String, Map<String, Object>> findPlanInfo(String existHmId, String gameCode, String[] planCodes) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBSC_HM_PLAN_ID_ as HM_PLAN_ID_,PLAN_ITEM_ID_,PLAN_CODE_ from ibsc_hm_plan where EXIST_HM_ID_=?"
				+ " and GAME_CODE_=? and STATE_=? and PLAN_CODE_ in(");
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		for (String planCode : planCodes) {
			sql.append("?,");
			parameters.add(planCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		List<Map<String, Object>> list = super.dao.findMapList(sql.toString(), parameters);
		if (ContainerTool.isEmpty(list)) {
			return new HashMap<>(2);
		}
		Map<String, Map<String, Object>> existPlan = new HashMap<>(list.size());

		for (Map<String, Object> map : list) {
			existPlan.put(map.remove("PLAN_CODE_").toString(), map);
		}

		return existPlan;
	}

	/**
	 * 获取方案游戏详情ids
	 * @param existHmId	已存在盘口会员id
	 * @param gameCode	游戏编码
	 * @return
	 */
	public List<String> findPlanItemIds(String existHmId, String gameCode) throws SQLException {
		String sql="select PLAN_ITEM_ID_ from ibsc_hm_plan where EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findStringList("PLAN_ITEM_ID_",sql,parameters);
	}

	/**
	 * 清除会员游戏方案
	 * @param existHmId	已存在盘口会员id
	 * @param gameCode	游戏编码
	 */
	public void clearPlanGame(String existHmId, String gameCode) throws SQLException {
		String sql="update ibsc_hm_plan set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
				+ " EXIST_HM_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql,parameters);
	}

	/**
	 * 删除会员方案
	 * @param hmPlanInfo		会员方案信息
	 */
	public void delHmPlan(Map<String, Object> hmPlanInfo) throws SQLException {
		String sql="update ibsc_hm_plan set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where"
				+ " IBSC_HM_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(IbsStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(hmPlanInfo.get("HM_PLAN_ID_"));
		super.dao.execute(sql,parameters);
	}



}
