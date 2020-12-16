package com.ibm.old.v1.pc.ibm_plan_user.t.service;

import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserSetService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IbmPcPlanUserSetService extends IbmPlanUserSetService {
	/**
	 * @param planIdList    方案id列表
	 * @param planTableList 方案详情表名称
	 * @param userId        用户ID
	 * @return 方案详情表id列表
	 * @Description: 根据方案code，用户id，获取方案详情表id列表
	 * <p>
	 * 参数说明
	 */
	public List<String> listPlanItemTableIds(List<String> planIdList, List<String> planTableList, String userId)
			throws SQLException {
		List<String> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		for (String table : planTableList) {
			sql.append("SELECT ").append(table).append("_ID_ from ").append(table)
					.append(" WHERE STATE_ != ? AND USER_ID_ = ? AND PLAN_ID_ in (");
			parameters.add(IbmStateEnum.DEL.name());
			parameters.add(userId);
			for (String planId : planIdList) {
				sql.append("?,");
				parameters.add(planId);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
			list.addAll(super.dao.findStringList(table + "_ID_", sql.toString(), parameters));
			parameters.clear();
			sql.delete(0,sql.length());
		}
		return list;
	}
	/**
	 * 获取需要重置资金的方案详情id
	 * @param map
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public List<String> resetPlanId(Map<Object, Object> map, String userId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		List<String> list = new ArrayList<>();
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			sql.append("select ").append(entry.getValue()).append("_ID_ from ").append(entry.getValue()).append(" where STATE_ != ? AND USER_ID_ =? AND PLAN_ID_=?");
			parameters.add(IbmStateEnum.DEL.name());
			parameters.add(userId);
			parameters.add(entry.getKey());
			list.add(super.dao.findString(entry.getValue()+"_ID_",sql.toString(),parameters));
			parameters.clear();
			sql.delete(0,sql.length());
		}
		return list;
	}
}
