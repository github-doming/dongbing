package com.ibm.old.v1.cloud.ibm_profit_plan.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_pi_follow_open_bet.t.service.IbmPiFollowOpenBetTService;
import com.ibm.old.v1.cloud.ibm_pi_follow_two_side.t.service.IbmPiFollowTwoSideTService;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.service.IbmPiLocationBetNumberTService;
import com.ibm.old.v1.cloud.ibm_pi_number_to_track.t.service.IbmPiNumberToTrackTService;
import com.ibm.old.v1.cloud.ibm_pi_rank_hot_and_cold.t.service.IbmPiRankHotAndColdTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.entity.IbmProfitPlanT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmProfitPlanTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmProfitPlanT对象数据
	 */
	public String save(IbmProfitPlanT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_profit_plan的 IBM_PROFIT_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_profit_plan set state_='DEL' where IBM_PROFIT_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PROFIT_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_profit_plan的 IBM_PROFIT_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_profit_plan set state_='DEL' where IBM_PROFIT_PLAN_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_profit_plan的 IBM_PROFIT_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_profit_plan where IBM_PROFIT_PLAN_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PROFIT_PLAN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_profit_plan的 IBM_PROFIT_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_profit_plan where IBM_PROFIT_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmProfitPlanT实体信息
	 *
	 * @param entity IbmProfitPlanT实体
	 */
	public void update(IbmProfitPlanT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_profit_plan表主键查找IbmProfitPlanT实体
	 *
	 * @param id ibm_profit_plan 主键
	 * @return IbmProfitPlanT实体
	 */
	public IbmProfitPlanT find(String id) throws Exception {
		return (IbmProfitPlanT) this.dao.find(IbmProfitPlanT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_profit_plan where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmProfitPlanT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmProfitPlanT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_profit_plan where state_!='DEL'";
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		} else {
			sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by " + sortFieldName + " " + sortOrderName;
		}
		return dao.page(IbmProfitPlanT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmProfitPlanT数据信息
	 *
	 * @return 可用<IbmProfitPlanT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_profit_plan  where state_!='DEL' order by UPDATE_TIME_ desc";
		return this.dao.findObjectList(IbmProfitPlanT.class, sql);
	}

	/**
	 * 盈利限制
	 *
	 * @param type 限制类型
	 */
	public void profitLimit(String type) throws SQLException {
		String tableName = "ibm_profit_plan";
		if (IbmTypeEnum.VIRTUAL.name().equals(type)) {
			tableName = "ibm_profit_plan_vr";
		}
		//查出所有超出方案限制的盘口会员信息
		String sql = "SELECT ipp.PLAN_ID_ AS key_, ipp.HANDICAP_MEMBER_ID_ AS value_ FROM " + tableName + " ipp "
				+ " LEFT JOIN ibm_handicap_member ihm ON ipp.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_ "
				+ " WHERE ihm.LOGIN_STATE_ = ? AND ((LOSS_LIMIT_MIN_T_ != 0 AND PROFIT_T_ < LOSS_LIMIT_MIN_T_)OR "
				+ " (PROFIT_LIMIT_MAX_T_ != 0 AND PROFIT_T_ > PROFIT_LIMIT_MAX_T_) ) "
				+ " AND ipp.STATE_ = ? ORDER BY PLAN_ID_";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		Map<String, List<String>> mapList = super.findKVsMapList(sql, parameterList);
		List<String> handicapMemberIds;
		Date nowTime = new Date();
		for (Map.Entry<String, List<String>> entry : mapList.entrySet()) {

			//查出已存在的盘口会员方案信息
			sql = "SELECT HANDICAP_MEMBER_ID_ FROM `ibm_plan_hm` WHERE PLAN_ID_ = '" + entry.getKey()
					+ "' AND HANDICAP_MEMBER_ID_ IN ('" + String.join("','", entry.getValue())
					+ "') and STATE_ != 'DEL'";
			handicapMemberIds = super.dao.findStringList("HANDICAP_MEMBER_ID_", sql, null);
			//修改已存在的盘口会员方案信息
			if (ContainerTool.notEmpty(handicapMemberIds)) {
				sql = "UPDATE `ibm_plan_hm` SET STATE_ = '" + IbmStateEnum.CLOSE.name() + "',UPDATE_TIME_LONG_ = "
						+ System.currentTimeMillis() + " WHERE PLAN_ID_ = '" + entry.getKey()
						+ "' AND HANDICAP_MEMBER_ID_ IN ('" + String.join("','", handicapMemberIds)
						+ "') AND STATE_ != 'DEL'";
				super.dao.execute(sql, null);
			}
			parameterList.clear();

			//更新状态
			sql = "update " + tableName + " set  STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
					+ " PLAN_ID_ = '" + entry.getKey() + "' and HANDICAP_MEMBER_ID_ IN ('" + String
					.join("','", entry.getValue()) + "') and STATE_ != ? ";
			parameterList.add(IbmStateEnum.CLOSE.name());
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add("方案止盈止损");
			parameterList.add(IbmStateEnum.OPEN.name());

			super.dao.execute(sql, parameterList);
		}
	}
	/**
	 * 达到盈利限制，自定义重置
	 *
	 * @param type 投注类型
	 * @throws SQLException
	 */
	public void resetByLimit(String type) throws SQLException {
		String tableName = "ibm_profit_plan";
		String profitPlanId = "IBM_PROFIT_PLAN_ID_";
		if (IbmTypeEnum.VIRTUAL.name().equals(type)) {
			tableName = "ibm_profit_plan_vr";
			profitPlanId = "IBM_PROFIT_PLAN_VR_ID_";
		}
		//查出所有超出方案限制的自定义重置方案盘口会员
		String sql = "select ipp." + profitPlanId + " from " + tableName + " ipp "
				+ " LEFT JOIN ibm_handicap_member ihm ON ipp.HANDICAP_MEMBER_ID_ = ihm.IBM_HANDICAP_MEMBER_ID_"
				+ " LEFT JOIN ibm_hm_set ihs on ipp.HANDICAP_MEMBER_ID_ =ihs.HANDICAP_MEMBER_ID_"
				+ " WHERE ihm.LOGIN_STATE_ = ? AND ((ipp.LOSS_LIMIT_MIN_T_ != 0 AND ipp.PROFIT_T_ < ipp.LOSS_LIMIT_MIN_T_)OR "
				+ " (ipp.PROFIT_LIMIT_MAX_T_ != 0 AND ipp.PROFIT_T_ > ipp.PROFIT_LIMIT_MAX_T_) ) "
				+ " AND ipp.STATE_ = ? and ihs.RESET_TYPE_=? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.LOGIN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add("2");
		List<String> list = super.dao.findStringList(profitPlanId, sql, parameterList);
		if (ContainerTool.isEmpty(list)) {
			return;
		}
		parameterList.clear();
		sql = "update " + tableName + " set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=?,DESC_=? where " + profitPlanId
				+ " IN ('" + String.join("','", list) + "') ";
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add("方案自定义重置");
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取盘口会员方案盈亏主键
	 *
	 * @param profitId 盘口会员盈亏主键
	 * @param planId   方案主键
	 * @return 盘口会员方案盈亏主键
	 */
	public String findId(String profitId, String planId) throws SQLException {
		String sql = "SELECT IBM_PROFIT_PLAN_ID_ FROM `ibm_profit_plan` where PROFIT_ID_ = ? "
				+ " and PLAN_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(profitId);
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findString("IBM_PROFIT_PLAN_ID_", sql, parameterList);
	}

	/**
	 * 更新盈利信息
	 *
	 * @param profitPlanId 盘口会员方案盈亏主键
	 * @param profitT      投注盈亏
	 * @param betFundsT    投注积分
	 * @param betLen       投注项长度
	 * @param nowTime      更新时间
	 */
	public void updateProfit(String profitPlanId, long profitT, long betFundsT, int betLen, Date nowTime,
			String className) throws SQLException {
		String sql = "UPDATE ibm_profit_plan SET DESC_=?,PROFIT_T_ = PROFIT_T_ + ?, BET_FUNDS_T_ = BET_FUNDS_T_ + ?, "
				+ " BET_LEN_ = BET_LEN_ + ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? WHERE IBM_PROFIT_PLAN_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(className + " 更新盈利信息");
		parameterList.add(profitT);
		parameterList.add(betFundsT);
		parameterList.add(betLen);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(profitPlanId);
		super.dao.execute(sql, parameterList);
	}
	/**
	 * 清理冗余数据
	 *
	 * @param nowTime  清理时间
	 * @param type     清理类型
	 * @param ruleTime 时间规则
	 */
	public void clearRedundancy(Date nowTime, IbmTypeEnum type, String ruleTime) throws Exception {
		String tableName = "ibm_profit_plan";
		if (IbmTypeEnum.VIRTUAL.equals(type)) {
			tableName = "ibm_profit_plan_vr";
		}
		String sql = "DELETE FROM ".concat(tableName).concat(" WHERE CREATE_TIME_LONG_< ? ");
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(DateTool.getAfterRule(nowTime, ruleTime).getTime());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 修改方案盈亏限额
	 *
	 * @param profitLimitMaxT 盈利上限
	 * @param lossLimitMinT   亏损下限
	 * @param appUserId       用户id
	 * @param itemId          方案详情表id
	 * @param name            方案详情表名
	 * @return 是否修改成功
	 */
	public boolean updateLimit(long profitLimitMaxT, long lossLimitMinT, String appUserId, String itemId, String name,
			String className) throws SQLException {
		String planId;
		switch (name) {
			case "IBM_PI_LOCATION_BET_NUMBER":
				IbmPiLocationBetNumberTService piLocationBetNumberTService = new IbmPiLocationBetNumberTService();
				planId = piLocationBetNumberTService.findPlanId(itemId);
				break;
			case "IBM_PI_FOLLOW_TWO_SIDE":
				IbmPiFollowTwoSideTService piFollowTwoSideTService = new IbmPiFollowTwoSideTService();
				planId = piFollowTwoSideTService.findPlanId(itemId);
				break;
			case "IBM_PI_FOLLOW_OPEN_BET":
				IbmPiFollowOpenBetTService piFollowOpenBetTService = new IbmPiFollowOpenBetTService();
				planId = piFollowOpenBetTService.findPlanId(itemId);
				break;
			case "IBM_PI_NUMBER_TO_TRACK":
				IbmPiNumberToTrackTService piNumberToTrackTService = new IbmPiNumberToTrackTService();
				planId = piNumberToTrackTService.findPlanId(itemId);
				break;
			case "IBM_PI_RANK_HOT_AND_COLD":
				IbmPiRankHotAndColdTService piRankHotAndColdTService = new IbmPiRankHotAndColdTService();
				planId = piRankHotAndColdTService.findPlanId(itemId);
				break;
			default:
				throw new RuntimeException("不存在的盘口详情表名称" + name);
		}
		if (StringTool.isEmpty(planId)) {
			return false;
		}
		String sql =
				"UPDATE ibm_profit_plan SET DESC_=?,PROFIT_LIMIT_MAX_T_ = ?,LOSS_LIMIT_MIN_T_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
						+ "WHERE HANDICAP_MEMBER_ID_ in (SELECT IBM_HANDICAP_MEMBER_ID_ FROM ibm_handicap_member WHERE APP_USER_ID_ = ? AND STATE_ != ? )"
						+ " AND PLAN_ID_ = ? AND STATE_=? ";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(className + "修改方案盈亏限额");
		parameterList.add(profitLimitMaxT);
		parameterList.add(lossLimitMinT);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(planId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
		return true;
	}
	/**
	 * 重置盘口会员方案盈亏信息
	 *
	 * @param handicapMemberId 盘口会员id
	 * @throws SQLException
	 */
	public void resetPlanProfit(String handicapMemberId, String className) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select IBM_PROFIT_PLAN_ID_ from ibm_profit_plan where HANDICAP_MEMBER_ID_=? and STATE_=?");
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		List<String> profitPlanIds = super.dao.findStringList("IBM_PROFIT_PLAN_ID_", sql.toString(), parameterList);
		if (ContainerTool.isEmpty(profitPlanIds)) {
			return;
		}
		parameterList.clear();
		sql.delete(0, sql.length());
		sql.append(
				"update ibm_profit_plan set DESC_=?, STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBM_PROFIT_PLAN_ID_ in (");
		parameterList.add(className + "重置盘口会员方案盈亏信息");
		parameterList.add(IbmStateEnum.CLOSE.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for (String profitPlanId : profitPlanIds) {
			sql.append("?,");
			parameterList.add(profitPlanId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		super.dao.execute(sql.toString(), parameterList);
	}
	/**
	 * 获取盘口会员方案盈亏信息
	 *
	 * @param handicapMemberId
	 * @return
	 */
	public List<IbmProfitPlanT> findByHmId(String handicapMemberId) throws Exception {
		String sql = "select * from ibm_profit_plan where HANDICAP_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findObjectList(IbmProfitPlanT.class, sql, parameterList);
	}

	/**
	 * 重置盘口会员的所有执行信息
	 *
	 * @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId) throws SQLException {
		String sql = "update ibm_profit_plan set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-重置执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 重置盘口会员的所有执行信息
	 *
	 * @param clazzName        执行class名称
	 * @param handicapMemberId 盘口会员id
	 * @param planIds          方案ids
	 */
	public void replayAll4Hm(String clazzName, String handicapMemberId, List<String> planIds) throws SQLException {
		String sql = "update ibm_profit_plan set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where HANDICAP_MEMBER_ID_ = ? and STATE_ = ? and PLAN_ID_ in ( ";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbmStateEnum.REPLAY.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clazzName + "-重置执行情况");
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String planId : planIds) {
			sql = sql.concat("?,");
			parameterList.add(planId);
		}
		sql = sql.substring(0, sql.lastIndexOf(",")).concat(")");
		super.dao.execute(sql, parameterList);
	}
}
