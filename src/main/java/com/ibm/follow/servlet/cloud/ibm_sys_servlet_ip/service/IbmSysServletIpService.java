package com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.service.BaseService;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.entity.IbmSysServletIp;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_系统_服务IP 服务类
 *
 * @author Robot
 */
public class IbmSysServletIpService extends BaseService {

	/**
	 * 保存IBM_系统_服务IP 对象数据
	 *
	 * @param entity IbmSysServletIp对象数据
	 */
	public String save(IbmSysServletIp entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_servlet_ip 的 IBM_SYS_SERVLET_IP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_servlet_ip set state_='DEL' where IBM_SYS_SERVLET_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_SERVLET_IP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_sys_servlet_ip 的 IBM_SYS_SERVLET_IP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_servlet_ip set state_='DEL' where IBM_SYS_SERVLET_IP_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_servlet_ip  的 IBM_SYS_SERVLET_IP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_servlet_ip where IBM_SYS_SERVLET_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_SERVLET_IP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_servlet_ip 的 IBM_SYS_SERVLET_IP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_sys_servlet_ip where IBM_SYS_SERVLET_IP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysServletIp实体信息
	 *
	 * @param entity IBM_系统_服务IP 实体
	 */
	public void update(IbmSysServletIp entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_servlet_ip表主键查找 IBM_系统_服务IP 实体
	 *
	 * @param id ibm_sys_servlet_ip 主键
	 * @return IBM_系统_服务IP 实体
	 */
	public IbmSysServletIp find(String id) throws Exception {
		return (IbmSysServletIp) this.dao.find(IbmSysServletIp.class, id);

	}

	/**
	 * 获取服务ip信息
	 *
	 * @param ip 服务ip
	 * @return 服务ip信息
	 */
	public Map<String, Object> findServletInfo(String ip) throws SQLException {
		String sql = "SELECT IBM_SYS_SERVLET_IP_ID_,START_TIME_LONG_,END_TIME_LONG_,STATE_ FROM `ibm_sys_servlet_ip` "
				+ " where SERVLET_IP_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(ip);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 激活
	 *
	 * @param registerId 注册ID
	 * @param time       激活时间
	 * @return 成功 true
	 */
	public boolean activate(String registerId, Date time) throws SQLException {
		String sql = "UPDATE ibm_sys_servlet_ip SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,"
				+ " DESC_ = ? WHERE IBM_SYS_SERVLET_IP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbmStateEnum.ACTIVATE.name());
		parameterList.add(time);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("注册客户端");
		parameterList.add(registerId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.execute(sql, parameterList) == 1;
	}

	/**
	 * 更新注册IP
	 *
	 * @param registerId 注册ID
	 * @param state      更新状态
	 * @param time       更新时间
	 */
	public void update(Object registerId, IbmStateEnum state, Date time) throws SQLException {
		String sql = "UPDATE ibm_sys_servlet_ip SET STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,"
				+ " DESC_ = ? WHERE IBM_SYS_SERVLET_IP_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(state.name());
		parameterList.add(time);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("注销客户端");
		parameterList.add(registerId);
		parameterList.add(IbmStateEnum.ACTIVATE.name());
		super.dao.execute(sql, parameterList);

	}

	/**
	 * 分页获取服务器
	 *
	 * @param ip          ip
	 * @param serverState 服务器状态
	 * @param pageIndex   页数
	 * @param pageSize    条数
	 */
	public PageCoreBean<Map<String,Object>> listShow(String ip, String serverState, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) FROM ibm_sys_servlet_ip WHERE STATE_!=? ";
		String sql = "SELECT IBM_SYS_SERVLET_IP_ID_,SERVLET_IP_,CPU_RATE_,RAM_RATE_,DISK_RATE_,START_TIME_LONG_,END_TIME_LONG_, " +
				" isp.STATE_ FROM ibm_sys_servlet_ip isp LEFT JOIN ibm_sys_monitor_servlet iss ON isp.SERVLET_IP_ = iss.SERVER_IP_ " +
				" AND iss.MODULE_CODE_ = ? and iss.STATE_ != ? and isp.STATE_ != ?";
		List<Object> parameterCount = new ArrayList<>(3);
		List<Object> parameterList = new ArrayList<>(5);
		parameterCount.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmMainConfig.Module.CLIENT.name());
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());
		String sqlPlus = "";
		if (StringTool.notEmpty(ip)) {
			sqlPlus += " and isp.SERVLET_IP_ like ?";
			ip = "%" + ip + "%";
			parameterCount.add(ip);
			parameterList.add(ip);
		}
		if (StringTool.notEmpty(serverState)) {
			sqlPlus += " and isp.STATE_ = ?";
			parameterCount.add(serverState);
			parameterList.add(serverState);
		}
		sqlCount += sqlPlus;
		sql += sqlPlus;
		sql += " order by isp.UPDATE_TIME_LONG_ desc";
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount,parameterCount );
	}

	/**
	 * 获取服务器信息
	 * @param pk 主键
	 * @return 服务器信息
	 */
	public Map<String,Object> findServerInfo(String pk) throws SQLException {
		String sql = "SELECT IBM_SYS_SERVLET_IP_ID_,SERVLET_IP_,START_TIME_LONG_,END_TIME_LONG_,STATE_ FROM ibm_sys_servlet_ip WHERE IBM_SYS_SERVLET_IP_ID_=? AND STATE_!=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(pk);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql,parameterList);
	}
}
