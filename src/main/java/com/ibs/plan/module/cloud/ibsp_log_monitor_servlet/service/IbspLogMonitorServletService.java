package com.ibs.plan.module.cloud.ibsp_log_monitor_servlet.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_log_monitor_servlet.entity.IbspLogMonitorServlet;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBS_服务器监控日志 服务类
 * @author Robot
 */
public class IbspLogMonitorServletService extends BaseServiceProxy {

	/**
	 * 保存IBS_服务器监控日志 对象数据
	 * @param entity IbspLogMonitorServlet对象数据
	 */
	public String save(IbspLogMonitorServlet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_log_monitor_servlet 的 IBSP_LOG_MONITOR_SERVLET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_log_monitor_servlet set state_='DEL' where IBSP_LOG_MONITOR_SERVLET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_LOG_MONITOR_SERVLET_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_log_monitor_servlet 的 IBSP_LOG_MONITOR_SERVLET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_log_monitor_servlet set state_='DEL' where IBSP_LOG_MONITOR_SERVLET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_log_monitor_servlet  的 IBSP_LOG_MONITOR_SERVLET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_log_monitor_servlet where IBSP_LOG_MONITOR_SERVLET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_LOG_MONITOR_SERVLET_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_log_monitor_servlet 的 IBSP_LOG_MONITOR_SERVLET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_log_monitor_servlet where IBSP_LOG_MONITOR_SERVLET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspLogMonitorServlet实体信息
	 * @param entity IBS_服务器监控日志 实体
	 */
	public void update(IbspLogMonitorServlet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_log_monitor_servlet表主键查找 IBS_服务器监控日志 实体
	 * @param id ibsp_log_monitor_servlet 主键
	 * @return IBS_服务器监控日志 实体
	 */
	public IbspLogMonitorServlet find(String id) throws Exception {
		return dao.find(IbspLogMonitorServlet.class,id);
	}

	/**
	 * 获取监控比率列表信息
	 * @param servletIp 服务IP
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return  监控比率列表信息
	 */
	public List<Map<String, Object>> listRate(String servletIp, long startTime, long endTime) throws SQLException {
		String sql = "SELECT CPU_RATE_,RAM_RATE_,DISK_RATE_ FROM " +
				" ibsp_sys_monitor_servlet where SERVER_IP_ = ? AND CREATE_TIME_LONG_ >= ? AND CREATE_TIME_LONG_ <= ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(servletIp);
		parameterList.add(startTime);
		parameterList.add(endTime);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMapList(sql,parameterList);
	}

	/**
	 * 获取监控信息
	 * @param servletIp 服务IP
	 * @return 监控信息
	 */
	public Map<String, Object> findInfo(String servletIp) throws SQLException {
		String sql = "SELECT RAM_TOTAL_,DISK_TOTAL_ FROM ibsp_sys_monitor_servlet " +
				" WHERE SERVER_IP_ = ? AND STATE_ != ? ORDER BY UPDATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(servletIp);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findMap(sql,parameterList);
	}
}
