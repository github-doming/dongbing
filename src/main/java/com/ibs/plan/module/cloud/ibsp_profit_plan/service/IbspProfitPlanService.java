package com.ibs.plan.module.cloud.ibsp_profit_plan.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_profit_plan.entity.IbspProfitPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSP_盘口会员方案盈亏 服务类
 *
 * @author Robot
 */
public class IbspProfitPlanService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员方案盈亏 对象数据
	 *
	 * @param entity IbspProfitPlan对象数据
	 */
	public String save(IbspProfitPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_profit_plan 的 IBSP_PROFIT_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_profit_plan set state_='DEL' where IBSP_PROFIT_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PROFIT_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_profit_plan 的 IBSP_PROFIT_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_profit_plan set state_='DEL' where IBSP_PROFIT_PLAN_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_profit_plan  的 IBSP_PROFIT_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_profit_plan where IBSP_PROFIT_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PROFIT_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_profit_plan 的 IBSP_PROFIT_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_profit_plan where IBSP_PROFIT_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspProfitPlan实体信息
	 *
	 * @param entity IBSP_盘口会员方案盈亏 实体
	 */
	public void update(IbspProfitPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_profit_plan表主键查找 IBSP_盘口会员方案盈亏 实体
	 *
	 * @param id ibsp_profit_plan 主键
	 * @return IBSP_盘口会员方案盈亏 实体
	 */
	public IbspProfitPlan find(String id) throws Exception {
		return dao.find(IbspProfitPlan.class, id);
	}

	/**
	 * 获取会员方案盈亏信息
	 *
	 * @param handicapMemberId 会员id
	 * @return
	 */
	public List<String> findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBSP_PROFIT_PLAN_ID_ from ibsp_profit_plan where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList("IBSP_PROFIT_PLAN_ID_", sql, parameterList);
	}

	public Map<String, Object> findHistorys(String handicapMemberId, String gameId, Set<String> planIds, IbsTypeEnum betMode) throws SQLException {
		String tableName = " ibsp_profit_plan ";
		String profitId="IBSP_PROFIT_PLAN_ID_";
		if (IbsTypeEnum.VIRTUAL.equals(betMode)) {
			tableName = " ibsp_profit_plan_vr ";
			profitId="IBSP_PROFIT_PLAN_VR_ID_";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select ").append(profitId).append(" as value_,PLAN_ID_ as key_ from ").append(tableName);
		sql.append(" where HANDICAP_MEMBER_ID_=? and GAME_ID_=? and STATE_=? and PLAN_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapMemberId);
		parameters.add(gameId);
		parameters.add(IbsStateEnum.OPEN.name());
		for(String planId:planIds){
			sql.append("?,");
			parameters.add(planId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKVMap(sql.toString(),parameters);
	}

	/**
	 * 结算更新
	 *
	 * @param betInfos         投注信息
	 * @param handicapMemberId 盘口会员主键
	 * @param gameId           游戏主键
	 * @param nowTime          更新时间
	 * @param betModel         投注模式
	 */
	public void update4Settlement(List<Map<String, Object>> betInfos, String handicapMemberId, String gameId,
			Date nowTime, IbsTypeEnum betModel) throws SQLException {
		String tableName = " ibsp_profit_plan ";
		if (IbsTypeEnum.VIRTUAL.equals(betModel)) {
			tableName = " ibsp_profit_plan_vr ";
		}
		StringBuilder sql = new StringBuilder("update ").append(tableName);
		sql.append(" set UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,PROFIT_T_ = PROFIT_T_ + CASE PLAN_ID_ ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());

		StringBuilder sqlPlus = new StringBuilder();
		List<Object> parametersPlus = new ArrayList<>();
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PLAN_ID_"));
			parameters.add(betInfo.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(betInfo.get("PLAN_ID_"));
		}
		sql.append(" end,BET_FUNDS_T_ = BET_FUNDS_T_ + CASE PLAN_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PLAN_ID_"));
			parameters.add(betInfo.get("FUND_T_"));
		}
		sql.append(" end,BET_LEN_ = BET_LEN_ + CASE PLAN_ID_");
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PLAN_ID_"));
			parameters.add(betInfo.get("BET_CONTENT_LEN_"));
		}
		sql.append(" end where HANDICAP_MEMBER_ID_ = ? and GAME_ID_ = ? and STATE_ = ? and PLAN_ID_ in (");
		parameters.add(handicapMemberId);
		parameters.add(gameId);
		parameters.add(IbsStateEnum.OPEN.name());
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}
	/**
	 * 结算更新
	 *
	 * @param betInfos         投注信息
	 * @param nowTime          更新时间
	 * @param betModel         投注模式
	 */
	public void update4Settlement(List<Map<String, Object>> betInfos, Date nowTime, IbsTypeEnum betModel) throws SQLException {
		String tableName = " ibsp_profit_plan ";
		String profitId="IBSP_PROFIT_PLAN_ID_";
		if (IbsTypeEnum.VIRTUAL.equals(betModel)) {
			tableName = " ibsp_profit_plan_vr ";
			profitId="IBSP_PROFIT_PLAN_VR_ID_";
		}
		StringBuilder sql = new StringBuilder("update ").append(tableName);
		sql.append(" set UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ =?,PROFIT_T_ = PROFIT_T_ + CASE ").append(profitId);
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());

		StringBuilder sqlPlus = new StringBuilder();
		List<Object> parametersPlus = new ArrayList<>();
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PROFIT_PLAN_ID_"));
			parameters.add(betInfo.get("PROFIT_T_"));

			sqlPlus.append("?,");
			parametersPlus.add(betInfo.get("PROFIT_PLAN_ID_"));
		}
		sql.append(" end,BET_FUNDS_T_ = BET_FUNDS_T_ + CASE ").append(profitId);
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PROFIT_PLAN_ID_"));
			parameters.add(betInfo.get("FUND_T_"));
		}
		sql.append(" end,BET_LEN_ = BET_LEN_ + CASE ").append(profitId);
		for (Map<String, Object> betInfo : betInfos) {
			sql.append(" WHEN ? THEN ? ");
			parameters.add(betInfo.get("PROFIT_PLAN_ID_"));
			parameters.add(betInfo.get("BET_CONTENT_LEN_"));
		}
		sql.append(" end where STATE_ = ? and ").append(profitId).append(" in (");
		parameters.add(IbsStateEnum.OPEN.name());
		sql.append(sqlPlus);
		parameters.addAll(parametersPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 获取方案限额信息
	 * @return 方案限额信息
	 */
	public List<Map<String, Object>> listPlainLimit() throws SQLException {
		String sql = "SELECT ipp.PLAN_HM_ID_,ihi.HANDICAP_MEMBER_ID_,ihi.HANDICAP_ID_,ihi.APP_USER_ID_,ihi.MEMBER_ACCOUNT_ FROM "
				+ " `ibsp_profit_plan` ipp LEFT JOIN ibsp_hm_info ihi ON ipp.HANDICAP_MEMBER_ID_ = ihi.HANDICAP_MEMBER_ID_ AND ihi.STATE_ = ? "
				+ " WHERE ihi.LOGIN_STATE_ = ? AND (PROFIT_T_ > PROFIT_LIMIT_MAX_T_ OR PROFIT_T_ < LOSS_LIMIT_MIN_T_) AND ipp.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.LOGIN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql,parameterList);
	}

	/**
	 * 重置方案盈亏信息
	 *
	 * @param existInfos 已存在会员信息
	 * @param planId     方案id
	 * @param gameId     游戏id
	 * @param tableName  表名
	 */
	public void resetProfit(List<Map<String, Object>> existInfos, String planId, String gameId, String tableName) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ").append(tableName).append(" set PROFIT_T_=?,BET_FUNDS_T_=?,BET_LEN_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where")
				.append(" PLAN_ID_=? and GAME_ID_=? and STATE_!=? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(0);
		parameterList.add(0);
		parameterList.add(0);
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
	 * 每天重置方案
	 * @param handicapMemberIds	盘口会员ids
	 * @param tableName			表名
	 */
	public void everyDayResetProfit(List<String> handicapMemberIds,String tableName) throws SQLException {
		StringBuilder sql= new StringBuilder("update " + tableName + " set PROFIT_T_=?,BET_FUNDS_T_=?,BET_LEN_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,DESC_=?"
				+ " where STATE_=? and HANDICAP_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(0);
		parameterList.add(0);
		parameterList.add(0);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("每天重置方案");
		parameterList.add(IbsStateEnum.OPEN.name());
		for(String handicapMemberId:handicapMemberIds){
			sql.append("?,");
			parameterList.add(handicapMemberId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}

	/**
	 * 批量添加方案盈亏信息
	 *
	 * @param betInfos         投注信息
	 * @param userId           用户id
	 * @param handicapMemberId 会员id
	 * @param gameId           游戏id
	 * @param planLimitInfo    方案限额信息
	 * @param planHmInfo		  会员方案信息
	 * @param betMode			  投注模式
	 */
	public void batchSave(Map<String, Map<String, Object>> betInfos, String userId, String handicapMemberId,
						  String gameId, List<Map<String, Object>> planLimitInfo,Map<String,Map<String,Object>> planHmInfo,IbsTypeEnum betMode) throws SQLException {
		String tableName = " ibsp_profit_plan ";
		String profitId="IBSP_PROFIT_PLAN_ID_";
		if (IbsTypeEnum.VIRTUAL.equals(betMode)) {
			tableName = " ibsp_profit_plan_vr ";
			profitId="IBSP_PROFIT_PLAN_VR_ID_";
		}
		StringBuilder sql = new StringBuilder("insert into ").append(tableName).append(" (").append(profitId);
		sql.append(",APP_USER_ID_,HANDICAP_MEMBER_ID_,PLAN_HM_ID_,GAME_ID_,PLAN_ID_,PROFIT_T_"
				+ " ,BET_FUNDS_T_,BET_LEN_,PROFIT_LIMIT_MAX_T_,LOSS_LIMIT_MIN_T_,CREATE_TIME_, "
				+ " CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		Date nowTime = new Date();
		Map<String, Object> profitInfo;
		for (Map<String, Object> map : planLimitInfo) {
			Object planHmId=planHmInfo.get(map.get("PLAN_ID_")).get(gameId);
			profitInfo=betInfos.get(map.get("PLAN_ID_"));
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(userId);
			parameters.add(handicapMemberId);
			parameters.add(planHmId);
			parameters.add(gameId);
			parameters.add(map.get("PLAN_ID_"));
			parameters.add(profitInfo.get("PROFIT_T_"));
			parameters.add(profitInfo.get("FUND_T_"));
			parameters.add(profitInfo.get("BET_CONTENT_LEN_"));
			parameters.add(NumberTool.longValueT(map.get("PROFIT_LIMIT_MAX_")));
			parameters.add(NumberTool.longValueT(map.get("LOSS_LIMIT_MIN_")));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}
}
