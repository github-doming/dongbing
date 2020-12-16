package com.ibs.plan.module.cloud.ibsp_profit_vr.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import com.ibs.plan.module.cloud.ibsp_profit_vr.entity.IbspProfitVr;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBSP_盘口会员总模拟盈亏 服务类
 *
 * @author Robot
 */
public class IbspProfitVrService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员总模拟盈亏 对象数据
	 *
	 * @param entity IbspProfitVr对象数据
	 */
	public String save(IbspProfitVr entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_profit_vr 的 IBSP_PROFIT_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_profit_vr set state_='DEL' where IBSP_PROFIT_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PROFIT_VR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_profit_vr 的 IBSP_PROFIT_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_profit_vr set state_='DEL' where IBSP_PROFIT_VR_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_profit_vr  的 IBSP_PROFIT_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_profit_vr where IBSP_PROFIT_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PROFIT_VR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_profit_vr 的 IBSP_PROFIT_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_profit_vr where IBSP_PROFIT_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspProfitVr实体信息
	 *
	 * @param entity IBSP_盘口会员总模拟盈亏 实体
	 */
	public void update(IbspProfitVr entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_profit_vr表主键查找 IBSP_盘口会员总模拟盈亏 实体
	 *
	 * @param id ibsp_profit_vr 主键
	 * @return IBSP_盘口会员总模拟盈亏 实体
	 */
	public IbspProfitVr find(String id) throws Exception {
		return dao.find(IbspProfitVr.class, id);
	}

	/**
	 * 获取盘口会员的盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT PROFIT_T_,BET_FUNDS_T_,BET_LEN_ FROM ibsp_profit_vr "
				+ "where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 通过盘口会员id获取主键信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 主键信息
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBSP_PROFIT_VR_ID_ from ibsp_profit_vr where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findString("IBSP_PROFIT_VR_ID_", sql, parameterList);
	}
	/**
	 * 修改止盈止损信息
	 *
	 * @param hmSet      盘口会员设置
	 * @param profitVrId 盈亏信息主键
	 * @param className  类名
	 */
	public void update(IbspHmSet hmSet, String profitVrId, String className) throws SQLException {
		String sql = "update ibsp_profit_vr set PROFIT_LIMIT_MAX_T_=?,PROFIT_LIMIT_MIN_T_=?,LOSS_LIMIT_MIN_T_=?,"
				+ " UPDATE_TIME_LONG_=?,DESC_=? where IBSP_PROFIT_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(NumberTool.longValueT(hmSet.getProfitLimitMax()));
		parameterList.add(NumberTool.longValueT(hmSet.getProfitLimitMin()));
		parameterList.add(NumberTool.longValueT(hmSet.getLossLimitMin()));
		parameterList.add(System.currentTimeMillis());
		parameterList.add(className.concat("修改止盈止损"));
		parameterList.add(profitVrId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 盈亏限额列表
	 * @return 限额会员主键
	 */
	public List<String> listProfitLimit() throws SQLException {
		String sql = "SELECT HANDICAP_MEMBER_ID_ as key_ FROM ibsp_profit_vr WHERE STATE_ = ? "
				+ " AND ((PROFIT_LIMIT_MAX_T_ != 0 AND PROFIT_T_ > PROFIT_LIMIT_MAX_T_) OR "
				+ " (PROFIT_LIMIT_MIN_T_!=0 AND PROFIT_T_ < PROFIT_LIMIT_MIN_T_) "
				+ " OR (LOSS_LIMIT_MIN_T_ != 0 AND PROFIT_T_ < LOSS_LIMIT_MIN_T_))";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList(sql,parameterList);
	}
}
