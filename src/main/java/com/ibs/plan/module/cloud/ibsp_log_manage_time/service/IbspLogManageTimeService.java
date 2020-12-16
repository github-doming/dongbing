package com.ibs.plan.module.cloud.ibsp_log_manage_time.service;

import com.ibs.plan.module.cloud.ibsp_log_manage_time.entity.IbspLogManageTime;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBSP_可用时长情况 服务类
 * @author Robot
 */
public class IbspLogManageTimeService extends BaseServiceProxy {

	/**
	 * 保存IBSP_可用时长情况 对象数据
	 * @param entity IbspLogManageTime对象数据
	 */
	public String save(IbspLogManageTime entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_manage_time 的 IBSP_LOG_MANAGE_TIME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_manage_time set state_='DEL' where IBSP_LOG_MANAGE_TIME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_MANAGE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_manage_time 的 IBSP_LOG_MANAGE_TIME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_manage_time set state_='DEL' where IBSP_LOG_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_manage_time  的 IBSP_LOG_MANAGE_TIME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_manage_time where IBSP_LOG_MANAGE_TIME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_MANAGE_TIME_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_manage_time 的 IBSP_LOG_MANAGE_TIME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_manage_time where IBSP_LOG_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogManageTime实体信息
	 * @param entity IBSP_可用时长情况 实体
	 */
	public void update(IbspLogManageTime entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_manage_time表主键查找 IBSP_可用时长情况 实体
	 * @param id ibsp_log_manage_time 主键
	 * @return IBSP_可用时长情况 实体
	 */
	public IbspLogManageTime find(String id) throws Exception {
		return dao.find(IbspLogManageTime.class,id);
	}
	/**
	 * 用户最后一条积分修改记录主键
	 * @param appUserId
	 * @return
	 */
	public Map<String, Object> findLastRepByUserId(String appUserId) throws SQLException {
		String sql = "SELECT IBSP_LOG_MANAGE_TIME_ID_ preKey,START_TIME_,START_TIME_LONG_,END_TIME_,END_TIME_LONG_ FROM `ibsp_log_manage_time` WHERE APP_USER_ID_ =? ORDER BY CREATE_TIME_LONG_ DESC LIMIT 1 ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return dao.findMap(sql,parameterList);
	}
}
