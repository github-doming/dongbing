package com.ibs.plan.module.cloud.ibsp_log_monitor_client.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_log_monitor_client.entity.IbspLogMonitorClient;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBS_客户端详情日志 服务类
 * @author Robot
 */
public class IbspLogMonitorClientService extends BaseServiceProxy {

	/**
	 * 保存IBS_客户端详情日志 对象数据
	 * @param entity IbspLogMonitorClient对象数据
	 */
	public String save(IbspLogMonitorClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_monitor_client 的 IBSP_LOG_MONITOR_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_monitor_client set state_='DEL' where IBSP_LOG_MONITOR_CLIENT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_MONITOR_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_monitor_client 的 IBSP_LOG_MONITOR_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_monitor_client set state_='DEL' where IBSP_LOG_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_monitor_client  的 IBSP_LOG_MONITOR_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_monitor_client where IBSP_LOG_MONITOR_CLIENT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_MONITOR_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_monitor_client 的 IBSP_LOG_MONITOR_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_monitor_client where IBSP_LOG_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogMonitorClient实体信息
	 * @param entity IBS_客户端详情日志 实体
	 */
	public void update(IbspLogMonitorClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_monitor_client表主键查找 IBS_客户端详情日志 实体
	 * @param id ibsp_log_monitor_client 主键
	 * @return IBS_客户端详情日志 实体
	 */
	public IbspLogMonitorClient find(String id) throws Exception {
		return dao.find(IbspLogMonitorClient.class,id);
	}

	/**
	 * 获取监控比率列表信息
	 *
	 * @param clientCode 客户端编码
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @return 监控比率列表信息
	 */
	public List<String> listRate(String clientCode, String startTime, String endTime) throws SQLException {
		String sql = "SELECT JVM_RATE_ as key_ FROM ibsp_log_monitor_client WHERE " +
				" CLIENT_CODE_ = ? AND CREATE_TIME_LONG_ >= ? AND CREATE_TIME_LONG_ <= ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(clientCode);
		parameterList.add(startTime);
		parameterList.add(endTime);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findStringList(sql, parameterList);
	}


	/**
	 * 获取监控信息
	 * @param clientCode 客户端编码
	 * @return 监控信息
	 */
	public Map<String, Object> findInfo(String clientCode) throws SQLException {
		String sql = "SELECT JVM_TOTAL_,JVM_MAX_ FROM ibsp_log_monitor_client WHERE CLIENT_CODE_ = ? AND STATE_ != ?" +
				" ORDER BY UPDATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMap(sql,parameterList);
	}
}
