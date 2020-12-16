package com.ibs.plan.module.cloud.ibsp_log_plan.service;

import com.ibs.plan.module.cloud.ibsp_log_plan.entity.IbspLogPlan;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* IBSP_方案用户操作日志 服务类
 * @author Robot
 */
public class IbspLogPlanService extends BaseServiceProxy {

	/**
	 * 保存IBSP_方案用户操作日志 对象数据
	 * @param entity IbspLogPlan对象数据
	 */
	public String save(IbspLogPlan entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_plan 的 IBSP_LOG_PLAN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_plan set state_='DEL' where IBSP_LOG_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_plan 的 IBSP_LOG_PLAN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_plan set state_='DEL' where IBSP_LOG_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_plan  的 IBSP_LOG_PLAN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_plan where IBSP_LOG_PLAN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_PLAN_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_plan 的 IBSP_LOG_PLAN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_plan where IBSP_LOG_PLAN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogPlan实体信息
	 * @param entity IBSP_方案用户操作日志 实体
	 */
	public void update(IbspLogPlan entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_plan表主键查找 IBSP_方案用户操作日志 实体
	 * @param id ibsp_log_plan 主键
	 * @return IBSP_方案用户操作日志 实体
	 */
	public IbspLogPlan find(String id) throws Exception {
		return dao.find(IbspLogPlan.class,id);
	}
}
