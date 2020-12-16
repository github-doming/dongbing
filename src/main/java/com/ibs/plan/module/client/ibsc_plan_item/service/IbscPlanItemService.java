package com.ibs.plan.module.client.ibsc_plan_item.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_plan_item.entity.IbscPlanItem;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
* IBSC方案详情表 服务类
 * @author Robot
 */
public class IbscPlanItemService extends BaseServiceProxy {

	/**
	 * 保存IBSC方案详情表 对象数据
	 * @param entity IbscPlanItem对象数据
	 */
	public String save(IbscPlanItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsc_plan_item 的 IBSC_PLAN_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_plan_item set state_='DEL' where IBSC_PLAN_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_PLAN_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除 ibsc_plan_item 的 IBSC_PLAN_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_plan_item set state_='DEL' where IBSC_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsc_plan_item  的 IBSC_PLAN_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_plan_item where IBSC_PLAN_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_PLAN_ITEM_ID_主键id数组的数据
	 * @param idArray 要删除ibsc_plan_item 的 IBSC_PLAN_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_plan_item where IBSC_PLAN_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscPlanItem实体信息
	 * @param entity IBSC方案详情表 实体
	 */
	public void update(IbscPlanItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_plan_item表主键查找 IBSC方案详情表 实体
	 * @param id ibsc_plan_item 主键
	 * @return IBSC方案详情表 实体
	 */
	public IbscPlanItem find(String id) throws Exception {
		return (IbscPlanItem) dao.find(IbscPlanItem.class,id);

	}

	/**
	 * 获取止盈止损信息
	 * @param existHmId	已存在盘口会员id
	 * @param planCode	方案code
	 * @return
	 */
	public Map<String, Object> getLimitInfo(String existHmId, String planCode) throws SQLException {
		String sql="select PROFIT_LIMIT_MAX_,LOSS_LIMIT_MIN_ from ibsc_plan_item where EXIST_HM_ID_=?"
				+ " and PLAN_CODE_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existHmId);
		parameters.add(planCode);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql,parameters);
	}

	/**
	 * 获取开启的方案详情信息
	 * @param itemIds		详情ids
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> listInfo(List<String> itemIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select IBSC_PLAN_ITEM_ID_,EXIST_HM_ID_,PLAN_CODE_,FUNDS_LIST_,FOLLOW_PERIOD_,");
		sql.append("MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_ACTIVE_DATA_,EXPAND_INFO_");
		sql.append(" from ibsc_plan_item where IBSC_PLAN_ITEM_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sql.append("?,");
			parameterList.add(itemId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKeyMaps(sql,parameterList,"EXIST_HM_ID_");
	}
	/**
	 * 获取开启的方案详情信息
	 * @param itemIds		详情ids
	 * @return
	 */
	public List<Map<String, Object>> listHmPlanItem(List<String> itemIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select IBSC_PLAN_ITEM_ID_,EXIST_HM_ID_,PLAN_CODE_,FUNDS_LIST_,FOLLOW_PERIOD_,");
		sql.append("MONITOR_PERIOD_,BET_MODE_,FUND_SWAP_MODE_,PERIOD_ROLL_MODE_,PLAN_GROUP_ACTIVE_DATA_,EXPAND_INFO_");
		sql.append(" from ibsc_plan_item where IBSC_PLAN_ITEM_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(itemIds.size());
		for (String itemId : itemIds) {
			sql.append("?,");
			parameterList.add(itemId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.dao.findMapList(sql.toString(),parameterList);
	}

	/**
	 * 清除方案详情信息
	 * @param planItemIds		方案详情ids
	 */
	public void clearPlanItem(List<String> planItemIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("update ibsc_plan_item set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSC_PLAN_ITEM_ID_ in(");
		List<Object> parameterList = new ArrayList<>(10);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		for(String planItemId:planItemIds){
			sql.append("?,");
			parameterList.add(planItemId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		super.dao.execute(sql.toString(),parameterList);
	}

	/**
	 * 删除方案详情信息
	 * @param hmPlanInfo	会员方案信息
	 */
	public void delPlanItem(Map<String, Object> hmPlanInfo) throws SQLException {
		String sql="update ibsc_plan_item set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSC_PLAN_ITEM_ID_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(hmPlanInfo.get("PLAN_ITEM_ID_"));
		super.dao.execute(sql,parameterList);
	}
}
