package com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_sys_servlet_ip.entity.IbspSysServletIp;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* IBSP_系统服务IP 服务类
 * @author Robot
 */
public class IbspSysServletIpService extends BaseServiceProxy {

	/**
	 * 保存IBSP_系统服务IP 对象数据
	 * @param entity IbspSysServletIp对象数据
	 */
	public String save(IbspSysServletIp entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_sys_servlet_ip 的 IBSP_SYS_SERVLET_IP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_sys_servlet_ip set state_='DEL' where IBSP_SYS_SERVLET_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_SYS_SERVLET_IP_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_sys_servlet_ip 的 IBSP_SYS_SERVLET_IP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_sys_servlet_ip set state_='DEL' where IBSP_SYS_SERVLET_IP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_sys_servlet_ip  的 IBSP_SYS_SERVLET_IP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_sys_servlet_ip where IBSP_SYS_SERVLET_IP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_SYS_SERVLET_IP_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_sys_servlet_ip 的 IBSP_SYS_SERVLET_IP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_sys_servlet_ip where IBSP_SYS_SERVLET_IP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspSysServletIp实体信息
	 * @param entity IBSP_系统服务IP 实体
	 */
	public void update(IbspSysServletIp entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_sys_servlet_ip表主键查找 IBSP_系统服务IP 实体
	 * @param id ibsp_sys_servlet_ip 主键
	 * @return IBSP_系统服务IP 实体
	 */
	public IbspSysServletIp find(String id) throws Exception {
		return (IbspSysServletIp) dao.find(IbspSysServletIp.class,id);

	}

	/**
	 * 获取服务ip信息
	 *
	 * @param ip 服务ip
	 * @return 服务ip信息
	 */
	public Map<String, Object> findServletInfo(Object ip) throws SQLException {
		String sql = "SELECT IBSP_SYS_SERVLET_IP_ID_,STATE_ FROM `ibsp_sys_servlet_ip` "
				+ " where SERVLET_IP_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(ip);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
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
		String sqlCount = "SELECT count(*) FROM ibsp_sys_servlet_ip WHERE STATE_!=? ";
		String sql = "SELECT IBSP_SYS_SERVLET_IP_ID_,SERVLET_IP_,CPU_RATE_,RAM_RATE_,DISK_RATE_,START_TIME_LONG_,END_TIME_LONG_, " +
				" isp.STATE_ FROM ibsp_sys_servlet_ip isp LEFT JOIN ibsp_sys_monitor_servlet iss ON isp.SERVLET_IP_ = iss.SERVER_IP_ " +
				" AND iss.MODULE_CODE_ = ? and iss.STATE_ != ? and isp.STATE_ != ?";
		List<Object> parameterCount = new ArrayList<>(3);
		List<Object> parameterList = new ArrayList<>(5);
		parameterCount.add(IbsStateEnum.DEL.name());
		parameterList.add("客户端");
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
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
		String sql = "SELECT IBSP_SYS_SERVLET_IP_ID_,SERVLET_IP_,START_TIME_LONG_,END_TIME_LONG_,STATE_ FROM ibsp_sys_servlet_ip WHERE IBSP_SYS_SERVLET_IP_ID_=? AND STATE_!=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(pk);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql,parameterList);
	}
}
