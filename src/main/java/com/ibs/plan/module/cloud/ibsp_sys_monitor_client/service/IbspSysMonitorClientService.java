package com.ibs.plan.module.cloud.ibsp_sys_monitor_client.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.entity.IbspSysMonitorClient;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;
import org.doming.develop.system.bean.MemoryInfoBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_客户端监控信息 服务类
 * @author Robot
 */
public class IbspSysMonitorClientService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户端监控信息 对象数据
	 * @param entity IbspSysMonitorClient对象数据
	 */
	public String save(IbspSysMonitorClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_sys_monitor_client 的 IBSP_SYS_MONITOR_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_monitor_client set state_='DEL' where IBSP_SYS_MONITOR_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_SYS_MONITOR_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_sys_monitor_client 的 IBSP_SYS_MONITOR_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_monitor_client set state_='DEL' where IBSP_SYS_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_sys_monitor_client  的 IBSP_SYS_MONITOR_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_monitor_client where IBSP_SYS_MONITOR_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_SYS_MONITOR_CLIENT_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_sys_monitor_client 的 IBSP_SYS_MONITOR_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_monitor_client where IBSP_SYS_MONITOR_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysMonitorClient实体信息
	 * @param entity IBSP_客户端监控信息 实体
	 */
	public void update(IbspSysMonitorClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_monitor_client表主键查找 IBSP_客户端监控信息 实体
	 * @param id ibsp_sys_monitor_client 主键
	 * @return IBSP_客户端监控信息 实体
	 */
	public IbspSysMonitorClient find(String id) throws Exception {
		return dao.find(IbspSysMonitorClient.class,id);

	}


	/**
	 * 注销客户端
	 * @param clientCode    客户机编码
	 * @param nowTime       当前时间
	 */
	public void cancelClient(Object clientCode, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_sys_monitor_client SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_CODE_ = ? and  STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add("注销客户端");
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新
	 *
	 * @param clientCode 客户端编码
	 * @param memoryInfo 内存信息
	 * @param nowTime    更新时间
	 */
	public void update(String clientCode, MemoryInfoBean memoryInfo, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_sys_monitor_client set JVM_TOTAL_ = ?,JVM_MAX_ = ?,JVM_FREE_ = ?,UPDATE_TIME_ = ?, " +
				" UPDATE_TIME_LONG_ = ? where CLIENT_CODE_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(memoryInfo.getTotalJvmMemory());
		parameters.add(memoryInfo.getMaxJvmMemory());
		parameters.add(memoryInfo.getFreeJvmMemory());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(clientCode);
		parameters.add(IbsStateEnum.DEL.name());
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
		String sqlCount = "SELECT count(*) FROM ibsp_sys_monitor_client WHERE STATE_ != ? ";
		String sql = "SELECT SERVER_IP_,CLIENT_CODE_,JVM_FREE_,JVM_TOTAL_,JVM_MAX_,STATE_ FROM ibsp_sys_monitor_client" +
				" WHERE STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbsStateEnum.DEL.name());
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
