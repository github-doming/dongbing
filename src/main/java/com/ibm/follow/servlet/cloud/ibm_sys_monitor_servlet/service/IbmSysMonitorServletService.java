package com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.entity.IbmSysMonitorServlet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_服务器监控信息 服务类
 *
 * @author Robot
 */
public class IbmSysMonitorServletService extends BaseServiceProxy {

	/**
	 * 保存IBM_服务器监控信息 对象数据
	 *
	 * @param entity IbmSysMonitorServlet对象数据
	 */
	public String save(IbmSysMonitorServlet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_monitor_servlet 的 IBM_SYS_MONITOR_SERVLET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_monitor_servlet set state_='DEL' where IBM_SYS_MONITOR_SERVLET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_MONITOR_SERVLET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_sys_monitor_servlet 的 IBM_SYS_MONITOR_SERVLET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_monitor_servlet set state_='DEL' where IBM_SYS_MONITOR_SERVLET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_monitor_servlet  的 IBM_SYS_MONITOR_SERVLET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_monitor_servlet where IBM_SYS_MONITOR_SERVLET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_MONITOR_SERVLET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_monitor_servlet 的 IBM_SYS_MONITOR_SERVLET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_monitor_servlet where IBM_SYS_MONITOR_SERVLET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysMonitorServlet实体信息
	 *
	 * @param entity IBM_服务器监控信息 实体
	 */
	public void update(IbmSysMonitorServlet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_monitor_servlet表主键查找 IBM_服务器监控信息 实体
	 *
	 * @param id ibm_sys_monitor_servlet 主键
	 * @return IBM_服务器监控信息 实体
	 */
	public IbmSysMonitorServlet find(String id) throws Exception {
		return (IbmSysMonitorServlet) dao.find(IbmSysMonitorServlet.class, id);

	}

	/**
	 * 查找服务IP和编码的主键
	 *
	 * @param serverIp   服务IP
	 * @param moduleCode 服务编码
	 * @return 服务器监控信息主键
	 */
	public String findId(String serverIp, String moduleCode) throws SQLException {
		String sql = "SELECT IBM_SYS_MONITOR_SERVLET_ID_ as key_ FROM `ibm_sys_monitor_servlet` where " +
				" SERVER_IP_ = ? and MODULE_CODE_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(serverIp);
		parameters.add(moduleCode);
		parameters.add(IbmStateEnum.DEL.name());
		return super.findString(sql, parameters);


	}

	/**
	 * 更新
	 *
	 * @param monitorServletId 服务器监控信息主键
	 * @param cpuRatio         cup使用率
	 * @param jvmRate          JVM使用率
	 * @param ramRate          内存使用率
	 * @param diskRate         磁盘使用率
	 * @param nowTime          更新时间
	 */
	public void update(String monitorServletId, double cpuRatio, int jvmRate, String ramRate, String diskRate, Date nowTime) throws SQLException {
		String sql = "UPDATE ibm_sys_monitor_servlet set CPU_RATE_ = ?,JVM_RATE_ = ?,RAM_RATE_ = ?,DISK_RATE_ = ?, " +
				" UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where IBM_SYS_MONITOR_SERVLET_ID_ = ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(7);
		parameters.add(cpuRatio);
		parameters.add(jvmRate);
		parameters.add(ramRate);
		parameters.add(diskRate);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(monitorServletId);
		parameters.add(IbmStateEnum.DEL.name());
		super.execute(sql, parameters);
	}

	/**
	 * 查询服务模块信息
	 *
	 * @param serverIp 服务IP
	 * @return 服务模块信息
	 */
	public List<Map<String, Object>> listShow(String serverIp) throws SQLException {
		String sql = "SELECT SERVER_IP_,MODULE_NAME_,JVM_RATE_,RAM_RATE_,CPU_RATE_,DISK_RATE_,STATE_ FROM ibm_sys_monitor_servlet " +
				" where STATE_ != ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(serverIp)) {
			sql += " and SERVER_IP_ = ? ";
			parameters.add(serverIp);
		}
		return super.findMapList(sql, parameters);
	}
}
