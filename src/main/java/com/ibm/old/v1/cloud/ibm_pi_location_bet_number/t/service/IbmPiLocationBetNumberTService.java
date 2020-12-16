package com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.string.StringUtil;
import com.ibm.old.v1.cloud.ibm_pi_location_bet_number.t.entity.IbmPiLocationBetNumberT;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemMain;
import com.ibm.old.v1.cloud.ibm_plan_item.t.entity.IbmPlanItemT;
import com.ibm.old.v1.common.doming.core.BaseServicePlus;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Robot
 */
public class IbmPiLocationBetNumberTService extends BaseServicePlus {

	/**
	 * 保存{ay_table_class}对象数据
	 *
	 * @param entity IbmPiLocationBetNumberT对象数据
	 */
	public String save(IbmPiLocationBetNumberT entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_pi_location_bet_number的 IBM_PI_LOCATION_BET_NUMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_pi_location_bet_number set state_='DEL' where IBM_PI_LOCATION_BET_NUMBER_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_PI_LOCATION_BET_NUMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_pi_location_bet_number的 IBM_PI_LOCATION_BET_NUMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_pi_location_bet_number set state_='DEL' where IBM_PI_LOCATION_BET_NUMBER_ID_ in("
					+ stringBuilder.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除ibm_pi_location_bet_number的 IBM_PI_LOCATION_BET_NUMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_pi_location_bet_number where IBM_PI_LOCATION_BET_NUMBER_ID_=?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_PI_LOCATION_BET_NUMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_pi_location_bet_number的 IBM_PI_LOCATION_BET_NUMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_pi_location_bet_number where IBM_PI_LOCATION_BET_NUMBER_ID_ in(" + stringBuilder
							.toString() + ")" ;
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmPiLocationBetNumberT实体信息
	 *
	 * @param entity IbmPiLocationBetNumberT实体
	 */
	public void update(IbmPiLocationBetNumberT entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_pi_location_bet_number表主键查找IbmPiLocationBetNumberT实体
	 *
	 * @param id ibm_pi_location_bet_number 主键
	 * @return IbmPiLocationBetNumberT实体
	 */
	public IbmPiLocationBetNumberT find(String id) throws Exception {
		return (IbmPiLocationBetNumberT) this.dao.find(IbmPiLocationBetNumberT.class, id);

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
		String sqlCount = "SELECT count(*) FROM ibm_pi_location_bet_number where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 获取分页IbmPiLocationBetNumberT数据
	 *
	 * @param sortFieldName 排序字段名
	 * @param sortOrderName 排序顺序
	 * @param pageIndex     页面索引
	 * @param pageSize      页面大小
	 * @return 分页IbmPiLocationBetNumberT数据
	 */
	public PageCoreBean findObject(String sortFieldName, String sortOrderName, Integer pageIndex, Integer pageSize)
			throws Exception {
		String sqlCount = "SELECT count(*) FROM ibm_pi_location_bet_number where state_!='DEL'" ;
		String sql;
		if (StringUtil.isBlank(sortFieldName) || StringUtil.isBlank(sortOrderName)) {
			sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		} else {
			sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by " + sortFieldName + " "
					+ sortOrderName;
		}
		return dao.page(IbmPiLocationBetNumberT.class, sql, null, pageIndex, pageSize, sqlCount);
	}

	/**
	 * 按照更新顺序查询所有可用Map信息
	 *
	 * @return 可用Map信息
	 */
	public List<Map<String, Object>> findAll() throws Exception {
		String sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findMapList(sql, null);
	}

	/**
	 * 按照更新顺序查询所有可用IbmPiLocationBetNumberT数据信息
	 *
	 * @return 可用<IbmPiLocationBetNumberT>数据信息
	 */
	public List findObjectAll() throws Exception {
		String sql = "SELECT * FROM ibm_pi_location_bet_number  where state_!='DEL' order by UPDATE_TIME_ desc" ;
		return this.dao.findObjectList(IbmPiLocationBetNumberT.class, sql);
	}

	/**
	 * 查找默认的位置配置
	 *
	 * @return 默认双面配置
	 */
	private IbmPiLocationBetNumberT findDef() throws Exception {
		String sql = "SELECT * FROM ibm_pi_location_bet_number  where state_ = ?" ;
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEF.name());
		return (IbmPiLocationBetNumberT) super.dao.findObject(IbmPiLocationBetNumberT.class, sql, parameters);
	}

	/**
	 * 复制默认的配置到个人头上
	 *
	 * @param userId 用户id
	 * @return 用户的配置
	 */
	public IbmPlanItemT copyDef(String userId) throws Exception {
		IbmPiLocationBetNumberT piLocationBetNumberT = this.findDef();
		piLocationBetNumberT.setIdx(0L);
		piLocationBetNumberT.setIbmPiLocationBetNumberId(null);
		piLocationBetNumberT.setUserId(userId);
		piLocationBetNumberT.setCreateTime(new Date());
		piLocationBetNumberT.setCreateTimeLong(piLocationBetNumberT.getCreateTime().getTime());
		piLocationBetNumberT.setUpdateTime(new Date());
		piLocationBetNumberT.setUpdateTimeLong(piLocationBetNumberT.getUpdateTime().getTime());
		piLocationBetNumberT.setState(IbmStateEnum.CLOSE.name());
		String id = this.save(piLocationBetNumberT);

		IbmPlanItemT planItemT = new IbmPlanItemT();
		planItemT.setName("ibm_pi_location_bet_number");
		planItemT.setId(id);
		planItemT.setProfitLimitMaxT(piLocationBetNumberT.getProfitLimitMaxT());
		planItemT.setLossLimitMinT(piLocationBetNumberT.getLossLimitMinT());
		planItemT.setBetMode(piLocationBetNumberT.getBetMode());
		planItemT.setMonitorPeriod(piLocationBetNumberT.getMonitorPeriod());
		planItemT.setState(piLocationBetNumberT.getState());
		return planItemT;
	}

	/**
	 * 根据id列表获取已开启基础信息列表
	 *
	 * @param itemIds 详情id列表
	 * @return 已开启基础信息列表
	 */
	public List<Map<String, Object>> listInfo(List<String> itemIds) throws SQLException {
		String sql = "SELECT IBM_PI_LOCATION_BET_NUMBER_ID_,FUNDS_LIST_ID_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,"
				+ " FUND_SWAP_MODE_,PLAN_GROUP_DATA_,"
				+ " PLAN_GROUP_ACTIVE_KEY_ FROM ibm_pi_location_bet_number  where 1 = 1 " ;
		StringBuilder sqlBuilder = new StringBuilder(" and IBM_PI_LOCATION_BET_NUMBER_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sqlBuilder.append("?,");
			parameterList.add(itemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and state_ = ? order by UPDATE_TIME_LONG_ desc");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据id列表获取已开启基础信息列表
	 *
	 * @param itemIds 详情id列表
	 * @return 已开启基础信息列表
	 */
	public List<Map<String, Object>> listInfo(Set<String> itemIds) throws SQLException {
		String sql = "SELECT IBM_PI_LOCATION_BET_NUMBER_ID_ as PLAN_ITEM_ID_,FOLLOW_PERIOD_,MONITOR_PERIOD_, "
				+ " FUND_SWAP_MODE_, PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_ FROM ibm_pi_location_bet_number  where 1 = 1 " ;
		StringBuilder sqlBuilder = new StringBuilder(" and IBM_PI_LOCATION_BET_NUMBER_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sqlBuilder.append("?,");
			parameterList.add(itemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and state_ = ? order by UPDATE_TIME_LONG_ desc");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 根据id获取基础信息
	 *
	 * @param planItemId 方案详情id
	 * @return 基础信息
	 */
	public Map<String, Object> findInfo(String planItemId) throws SQLException {
		String sql = "SELECT IBM_PI_LOCATION_BET_NUMBER_ID_,FUNDS_LIST_ID_,FUNDS_LIST_,FOLLOW_PERIOD_,MONITOR_PERIOD_,"
				+ " FUND_SWAP_MODE_,PLAN_GROUP_DATA_,PLAN_GROUP_ACTIVE_KEY_ FROM ibm_pi_location_bet_number "
				+ " where IBM_PI_LOCATION_BET_NUMBER_ID_ = ? and STATE_ != ?" ;
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(planItemId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 获取资金方案信息
	 *
	 * @param itemIds 位置投注_号码主键数组
	 * @return key-主键	value-资金方案
	 */
	public Map<Object, Object> mapFundsList(Set<String> itemIds) throws SQLException {
		String sql = "SELECT IBM_PI_LOCATION_BET_NUMBER_ID_ AS key_,concat_ws('#', BET_MODE_,IF ( "
				+ " ISNULL(FUNDS_LIST_) || LENGTH(trim(FUNDS_LIST_)) < 1,'NULL',FUNDS_LIST_), "
				+ " IF (ISNULL(FUNDS_LIST_ID_) || LENGTH(trim(FUNDS_LIST_ID_)) < 1, "
				+ " 'NULL',	FUNDS_LIST_ID_)) AS value_ FROM ibm_pi_location_bet_number WHERE  1 = 1 and " ;
		StringBuilder sqlBuilder = new StringBuilder(" IBM_PI_LOCATION_BET_NUMBER_ID_ in ( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size() + 1);
		for (String itemId : itemIds) {
			sqlBuilder.append("?,");
			parameterList.add(itemId);
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(",")).append(") and state_ = ? order by UPDATE_TIME_LONG_ desc");
		parameterList.add(IbmStateEnum.OPEN.name());
		sql += sqlBuilder.toString();
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 查找显示信息
	 *
	 * @param planItemId id
	 * @return 显示信息
	 */
	public Map<String, Object> findShow(String planItemId) throws SQLException {
		String sql = "SELECT PROFIT_LIMIT_MAX_T_, LOSS_LIMIT_MIN_T_, FUNDS_LIST_, FUNDS_LIST_ID_,FOLLOW_PERIOD_, "
				+ "MONITOR_PERIOD_, BET_MODE_, FUND_SWAP_MODE_, PERIOD_ROLL_MODE_, PLAN_GROUP_DATA_ FROM "
				+ "`ibm_pi_location_bet_number` WHERE IBM_PI_LOCATION_BET_NUMBER_ID_ = ? and STATE_ != ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planItemId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
	/**
	 * 更新数据
	 *
	 * @param planItemMain 主要盘口详情信息
	 * @return 更新结果
	 */
	public boolean update(IbmPlanItemMain planItemMain) throws Exception {
		IbmPiLocationBetNumberT planItem = this.find(planItemMain.getId());
		if (planItem == null){
			return false;
		}
		planItem.setProfitLimitMaxT(planItemMain.getProfitLimitMaxT());
		planItem.setLossLimitMinT(planItemMain.getLossLimitMinT());
		planItem.setFundsList(planItemMain.getFundsList());
		planItem.setFollowPeriod(planItemMain.getFollowPeriod());
		planItem.setMonitorPeriod(planItemMain.getMonitorPeriod());
		planItem.setBetMode(planItemMain.getBetMode());
		planItem.setFundSwapMode(planItemMain.getFundSwapMode());
		planItem.setPeriodRollMode(planItemMain.getPeriodRollMode());
		planItem.setPlanGroupData(planItemMain.getPlanGroupData());
		planItem.setPlanGroupActiveKey(planItemMain.getPlanGroupActiveKey());
		planItem.setUpdateTime(new Date());
		planItem.setUpdateTimeLong(planItem.getUpdateTime().getTime());
		return dao.update(planItem) == 1;
	}

	/**
	 * 更新状态
	 *
	 * @param id    位置投注_号码主键
	 * @param state 状态
	 */
	public void updateState(String id, String state,String className) throws SQLException {
		String sql = "UPDATE `ibm_pi_location_bet_number` set DESC_=?,STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBM_PI_LOCATION_BET_NUMBER_ID_ = ?" ;
		List<Object> parameterList = new ArrayList<>(4);
		Date nowTime = new Date();
		parameterList.add(className+"更新状态");
		parameterList.add(state);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(id);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取方案详情信息
	 *
	 * @param planItemTableId 方案详情id
	 * @return 方案详情信息
	 */
	public Map<String, Object> findPlanItem(String planItemTableId) throws Exception {
		String sql = "select PROFIT_LIMIT_MAX_T_, LOSS_LIMIT_MIN_T_, FUNDS_LIST_, FOLLOW_PERIOD_, "
				+ " MONITOR_PERIOD_, BET_MODE_, FUND_SWAP_MODE_, PERIOD_ROLL_MODE_,PLAN_GROUP_ACTIVE_KEY_, "
				+ " PLAN_GROUP_DATA_ from ibm_pi_location_bet_number where IBM_PI_LOCATION_BET_NUMBER_ID_= ? and STATE_ != ?" ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(planItemTableId);
		parameterList.add(IbmStateEnum.DEL.name());
		return  super.dao.findMap( sql, parameterList);
	}

	/**
	 * 获取方案id
	 *
	 * @param itemId 详情表主键id
	 * @return 方案id
	 */
	public String findPlanId(String itemId) throws SQLException {
		String sql = "SELECT PLAN_ID_ FROM ibm_pi_location_bet_number WHERE IBM_PI_LOCATION_BET_NUMBER_ID_ = ? AND STATE_ != ? " ;
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(itemId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("PLAN_ID_", sql, parameterList);
	}
}
