package com.ibs.plan.module.cloud.ibsp_profit.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import com.ibs.plan.module.cloud.ibsp_profit.entity.IbspProfit;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_盘口会员总盈亏 服务类
 *
 * @author Robot
 */
public class IbspProfitService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员总盈亏 对象数据
	 *
	 * @param entity IbspProfit对象数据
	 */
	public String save(IbspProfit entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_profit 的 IBSP_PROFIT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_profit set state_='DEL' where IBSP_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_profit 的 IBSP_PROFIT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_profit set state_='DEL' where IBSP_PROFIT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_profit  的 IBSP_PROFIT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_profit where IBSP_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PROFIT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_profit 的 IBSP_PROFIT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_profit where IBSP_PROFIT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspProfit实体信息
	 *
	 * @param entity IBSP_盘口会员总盈亏 实体
	 */
	public void update(IbspProfit entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_profit表主键查找 IBSP_盘口会员总盈亏 实体
	 *
	 * @param id ibsp_profit 主键
	 * @return IBSP_盘口会员总盈亏 实体
	 */
	public IbspProfit find(String id) throws Exception {
		return dao.find(IbspProfit.class, id);
	}

	/**
	 * 通过主键删除盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update %s set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ? where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.execute(String.format(sql, "ibsp_profit"), parameterList);
		super.execute(String.format(sql, "ibsp_profit_vr"), parameterList);
		super.execute(String.format(sql, "ibsp_profit_period"), parameterList);
		super.execute(String.format(sql, "ibsp_profit_period_vr"), parameterList);
	}
	/**
	 * 清除方案盈亏信息
	 * @param handicapMemberId	盘口会员id
	 * @param nowTime				当前时间
	 */
	public void closeProfitPlan(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update %s set UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,STATE_ = ? where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.execute(String.format(sql, "ibsp_profit_plan"), parameterList);
		super.execute(String.format(sql, "ibsp_profit_plan_vr"), parameterList);
	}

	/**
	 * 获取盘口会员的盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT PROFIT_T_,BET_FUNDS_T_,BET_LEN_ FROM ibsp_profit "
				+ "where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 获取盈亏id
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盈亏id
	 */
	public String findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBSP_PROFIT_ID_ from ibsp_profit where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSP_PROFIT_ID_", sql, parameterList);
	}

	/**
	 * 修改止盈止损
	 *
	 * @param hmSet     盘口会员设置
	 * @param profitId  盈亏信息主键
	 * @param className 执行类名
	 */
	public void update(IbspHmSet hmSet, String profitId, String className) throws SQLException {
		String sql = "update ibsp_profit set PROFIT_LIMIT_MAX_T_=?,PROFIT_LIMIT_MIN_T_=?,LOSS_LIMIT_MIN_T_=?,"
				+ " UPDATE_TIME_LONG_=?,DESC_=? where IBSP_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(NumberTool.longValueT(hmSet.getProfitLimitMax()));
		parameterList.add(NumberTool.longValueT(hmSet.getProfitLimitMin()));
		parameterList.add(NumberTool.longValueT(hmSet.getLossLimitMin()));
		parameterList.add(System.currentTimeMillis());
		parameterList.add(className.concat("修改止盈止损"));
		parameterList.add(profitId);
		super.execute(sql, parameterList);
	}

	/**
	 * 获取盘口会员盈亏限额信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盈亏限额信息
	 */
	public Map<String, Object> findProfitLimit(String handicapMemberId) throws SQLException {
		String sql = "select PROFIT_LIMIT_MAX_T_,PROFIT_LIMIT_MIN_T_,LOSS_LIMIT_MIN_T_ from ibsp_profit where HANDICAP_MEMBER_ID_=?"
				+ " and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 修改定时校验盈亏
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param profitAmount     盈亏金额
	 * @param nowTime          更新时间
	 */
	public void updateProfit(String handicapMemberId, long profitAmount, Date nowTime) throws SQLException {
		String sql = "update ibsp_profit set HANDICAP_PROFIT_T_ = ?,UPDATE_TIME_=?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where HANDICAP_MEMBER_ID_  =? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(profitAmount);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("定时校验结果修改盘口盈亏");
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}
	/**
	 * 结算修改
	 *
	 * @param hmProfitInfos 会员盈亏信息
	 * @param nowTime       当前时间
	 * @param betMode       投注模式
	 */
	public void update4Settlement(Map<String, Map<String, Object>> hmProfitInfos, Date nowTime, IbsTypeEnum betMode) throws SQLException {
		String tableName = " ibsp_profit ";
		if (IbsTypeEnum.VIRTUAL.equals(betMode)) {
			tableName = " ibsp_profit_vr ";
		}
		StringBuilder sql=new StringBuilder();
		sql.append("update ").append(tableName).append(" set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,PROFIT_T_=PROFIT_T_+ CASE HANDICAP_MEMBER_ID_ ");
		List<Object> parameters = new ArrayList<>();
		parameters.add(nowTime);
		parameters.add(nowTime.getTime());
		StringBuilder sqlPlus = new StringBuilder();
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("profitTh"));
			sqlPlus.append("?,");
		}
		sql.append(" end,BET_FUNDS_T_ = BET_FUNDS_T_ + CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("betFundsTh"));
		}
		sql.append(" end,BET_LEN_ = BET_LEN_ + CASE HANDICAP_MEMBER_ID_");
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			sql.append(" WHEN ? THEN ? ");
			parameters.add(entry.getKey());
			parameters.add(entry.getValue().get("betLen"));
		}
		sql.append(" end where STATE_ = ? and HANDICAP_MEMBER_ID_ in (");
		parameters.add(IbsStateEnum.OPEN.name());
		sql.append(sqlPlus);
		sql.replace(sql.length() - 1, sql.length(), ")");
		parameters.addAll(hmProfitInfos.keySet());
		super.dao.execute(sql.toString(), parameters);
	}

	/**
	 * 盈亏限额列表
	 * @return
	 */
	public List<String> listProfitLimit() throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ as key_ FROM ibsp_profit WHERE STATE_ = ? "
				+ " AND ((PROFIT_LIMIT_MAX_T_ != 0 AND PROFIT_T_ > PROFIT_LIMIT_MAX_T_) OR "
				+ " (PROFIT_LIMIT_MIN_T_!=0 AND PROFIT_T_ < PROFIT_LIMIT_MIN_T_) "
				+ " OR (LOSS_LIMIT_MIN_T_ != 0 AND PROFIT_T_ < LOSS_LIMIT_MIN_T_))";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList(sql,parameterList);
	}

}
