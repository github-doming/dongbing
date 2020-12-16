package com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.entity.IbmHmProfitVr;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmHmProfitVrService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmHmProfitVr对象数据
	 */
	public String save(IbmHmProfitVr entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_hm_profit_vr的 IBM_HM_PROFIT_VR_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_hm_profit_vr set state_='DEL' where IBM_HM_PROFIT_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_HM_PROFIT_VR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit_vr的 IBM_HM_PROFIT_VR_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_hm_profit_vr set state_='DEL' where IBM_HM_PROFIT_VR_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_hm_profit_vr的 IBM_HM_PROFIT_VR_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_hm_profit_vr where IBM_HM_PROFIT_VR_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_HM_PROFIT_VR_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_hm_profit_vr的 IBM_HM_PROFIT_VR_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_hm_profit_vr where IBM_HM_PROFIT_VR_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmHmProfitVr实体信息
	 *
	 * @param entity IbmHmProfitVr实体
	 */
	public void update(IbmHmProfitVr entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_hm_profit_vr表主键查找IbmHmProfitVr实体
	 *
	 * @param id ibm_hm_profit_vr 主键
	 * @return IbmHmProfitVr实体
	 */
	public IbmHmProfitVr find(String id) throws Exception {
		return (IbmHmProfitVr) this.dao.find(IbmHmProfitVr.class, id);

	}

	/**
	 * 获取分页Map数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页Map数据
	 */
	public PageCoreBean find(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmHmProfitVr数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmHmProfitVr数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_hm_profit_vr where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmHmProfitVr.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmHmProfitVr数据信息
	 *
	 * @return 可用<IbmHmProfitVr>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_hm_profit_vr  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmHmProfitVr.class, sql);
	}

	/**
	 * 获取盘口会员的盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @return 盈亏信息
	 */
	public Map<String, Object> findInfo(String handicapMemberId) throws SQLException {
		String sql = "SELECT PROFIT_T_,BET_FUNDS_T_,BET_LEN_ FROM `ibm_hm_profit_vr` "
				+ "where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 通过盘口会员id获取主键信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 虚拟盈亏主键
	 */
	public String findByHmId(String handicapMemberId) throws SQLException {
		String sql = "select IBM_HM_PROFIT_VR_ID_ from ibm_hm_profit_vr where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_HM_PROFIT_VR_ID_", sql, parameterList);
	}
	/**
	 * 通过主键删除模拟盈亏信息
	 *
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          当前时间
	 */
	public void updateLogout(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "update ibm_hm_profit_vr set UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,STATE_=? where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 修改止盈止损信息
	 *
	 * @param hmSet      盘口会员设置
	 * @param profitVrId 盈亏信息主键
	 * @param className  类名
	 */
	public void update(IbmHmSet hmSet, String profitVrId, String className) throws SQLException {
		String sql = "update ibm_hm_profit_vr set PROFIT_LIMIT_MAX_=?,PROFIT_LIMIT_MIN_=?,LOSS_LIMIT_MIN_=?, "
				+ " UPDATE_TIME_LONG_=?,DESC_=? where IBM_HM_PROFIT_VR_ID_=?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(hmSet.getProfitLimitMax());
		parameterList.add(hmSet.getProfitLimitMin());
		parameterList.add(hmSet.getLossLimitMin());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(className.concat("修改止盈止损"));
		parameterList.add(profitVrId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 查找盈利id
	 *
	 * @param handicapMemberId 盘口会员ID
	 * @return 盈利id
	 */
	public String findIdByHmId(String handicapMemberId) throws SQLException {
		String sql = "SELECT IBM_HM_PROFIT_VR_ID_ as key_ FROM `ibm_hm_profit_vr` where HANDICAP_MEMBER_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 添加盘口盈亏信息
	 *
	 * @param limitInfo        限制信息
	 * @param handicapMemberId 盘口会员id
	 * @param profitTh         盈亏金额
	 * @param fundTh           投注金额
	 * @param betTotal         投注数
	 * @param nowTime          创建时间
	 */
	public void save(Map<String, Object> limitInfo, String handicapMemberId, long profitTh,
			long fundTh, int betTotal, Date nowTime) throws Exception {
		IbmHmProfitVr profit = new IbmHmProfitVr();
		profit.setHandicapMemberId(handicapMemberId);
		profit.setProfitT(profitTh);
		profit.setBetFundsT(fundTh);
		profit.setBetLen(betTotal);
		profit.setProfitLimitMax(limitInfo.get("PROFIT_LIMIT_MAX_"));
		profit.setProfitLimitMin(limitInfo.get("PROFIT_LIMIT_MIN_"));
		profit.setLossLimitMin(limitInfo.get("LOSS_LIMIT_MIN_"));
		profit.setCreateTime(nowTime);
		profit.setCreateTimeLong(System.currentTimeMillis());
		profit.setUpdateTimeLong(System.currentTimeMillis());
		profit.setState(IbmStateEnum.OPEN.name());
		save(profit);

	}

	/**
	 * 结算盈亏信息
	 *
	 * @param profitVrId 盘口会员总盈亏主键
	 * @param profit     盈亏金额
	 * @param fund       投注金额
	 * @param betTotal   投注数
	 * @param nowTime    结算时间
	 */
	public void update(String profitVrId, long profit, long fund, int betTotal, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_hm_profit_vr set PROFIT_T_ = PROFIT_T_ + ?,BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBM_HM_PROFIT_VR_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(profit);
		parameterList.add(fund);
		parameterList.add(betTotal);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("结算盈亏信息");
		parameterList.add(profitVrId);
		super.execute(sql, parameterList);
	}
}
