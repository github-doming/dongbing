package com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.entity.IbmSysMonitorClient;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;
import org.doming.develop.system.bean.MemoryInfoBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_客户端监控信息 服务类
 *
 * @author Robot
 */
public class IbmSysMonitorClientService extends BaseServiceProxy {

	/**
	 * 保存IBM_客户端监控信息 对象数据
	 *
	 * @param entity IbmSysMonitorClient对象数据
	 */
	public String save(IbmSysMonitorClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_monitor_client 的 IBM_SYS_MONITOR_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_monitor_client set state_='DEL' where IBM_SYS_MONITOR_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_MONITOR_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_sys_monitor_client 的 IBM_SYS_MONITOR_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_monitor_client set state_='DEL' where IBM_SYS_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_monitor_client  的 IBM_SYS_MONITOR_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_monitor_client where IBM_SYS_MONITOR_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_MONITOR_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_monitor_client 的 IBM_SYS_MONITOR_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_monitor_client where IBM_SYS_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysMonitorClient实体信息
	 *
	 * @param entity IBM_客户端监控信息 实体
	 */
	public void update(IbmSysMonitorClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_monitor_client表主键查找 IBM_客户端监控信息 实体
	 *
	 * @param id ibm_sys_monitor_client 主键
	 * @return IBM_客户端监控信息 实体
	 */
	public IbmSysMonitorClient find(String id) throws Exception {
		return (IbmSysMonitorClient) dao.find(IbmSysMonitorClient.class, id);

	}

	/**
	 * 更新
	 *
	 * @param clientCode 客户端编码
	 * @param memoryInfo 内存信息
	 * @param nowTime    更新时间
	 */
	public void update(String clientCode, MemoryInfoBean memoryInfo, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_sys_monitor_client set JVM_TOTAL_ = ?,JVM_MAX_ = ?,JVM_FREE_ = ?,UPDATE_TIME_ = ?, " +
				" UPDATE_TIME_LONG_ = ? where CLIENT_CODE_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(memoryInfo.getTotalJvmMemory());
		parameters.add(memoryInfo.getMaxJvmMemory());
		parameters.add(memoryInfo.getFreeJvmMemory());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(clientCode);
		parameters.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameters);
	}

	/**
	 * 分页获取客户端状态
	 *
	 * @param ip        ip
	 * @param state     服务器状态
	 * @param pageIndex 页数
	 * @param pageSize  条数
	 */
	public PageCoreBean<Map<String, Object>> listShow(String ip, String state, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) FROM ibm_sys_monitor_client WHERE STATE_ != ? ";
		String sql = "SELECT SERVER_IP_,CLIENT_CODE_,JVM_FREE_,JVM_TOTAL_,JVM_MAX_,STATE_ FROM ibm_sys_monitor_client" +
				" WHERE STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		String sqlPlus = "";
		if (StringTool.notEmpty(ip)) {
			sqlPlus += " and SERVER_IP_ like ?";
			ip = "%" + ip + "%";
			parameterList.add(ip);
		}
		if (StringTool.notEmpty(state)) {
			sqlPlus += " and STATE_ = ?";
			parameterList.add(state);
		}
		sqlCount += sqlPlus;
		sql += sqlPlus;
		sql += " order by UPDATE_TIME_LONG_ desc";
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);

	}
}
