package com.ibs.plan.module.cloud.ibsp_client.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client.entity.IbspClient;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_客户端 服务类
 *
 * @author Robot
 */
public class IbspClientService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户端 对象数据
	 *
	 * @param entity IbspClient对象数据
	 */
	public String save(IbspClient entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_client 的 IBSP_CLIENT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_client set state_='DEL' where IBSP_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_client 的 IBSP_CLIENT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_client set state_='DEL' where IBSP_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_client  的 IBSP_CLIENT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_client where IBSP_CLIENT_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_CLIENT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_client 的 IBSP_CLIENT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_client where IBSP_CLIENT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspClient实体信息
	 *
	 * @param entity IBSP_客户端 实体
	 */
	public void update(IbspClient entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_client表主键查找 IBSP_客户端 实体
	 *
	 * @param id ibsp_client 主键
	 * @return IBSP_客户端 实体
	 */
	public IbspClient find(String id) throws Exception {
		return dao.find(IbspClient.class, id);

	}

	/**
	 * 查找客户端信息
	 *
	 * @param clientCode 客户端编码
	 * @return 客户端信息
	 */
	public Map<String, Object> findInfo(Object clientCode) throws SQLException {
		String sql = "SELECT IBSP_CLIENT_ID_,STATE_ FROM `ibsp_client` where CLIENT_CODE_ = ? and STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 查找客户端信息
	 *
	 * @param clientCode 客户端编码
	 * @param clientIp   客户端IP
	 * @return 客户端信息
	 */
	public Map<String, Object> findInfo(Object clientCode, Object clientIp) throws SQLException {
		String sql = "SELECT IBSP_CLIENT_ID_,STATE_ FROM `ibsp_client` where CLIENT_CODE_ = ? and REGISTER_IP_ = ? and STATE_ != 'DEL'";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		parameterList.add(clientIp);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 激活客户端
	 *
	 * @param clientCode 客户端编码
	 * @param nowTime    更新时间
	 */
	public void activateClient(Object clientCode,Object clientType, Date nowTime) throws SQLException {
		String sql = "update ibsp_client set CLIENT_TYPE_=?,STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where CLIENT_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(clientType);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("激活客户端");
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 注销客户端
	 *
	 * @param clientId 客户端主键
	 * @param nowTime  注销时间
	 * @param state    注销状态
	 */
	public void cancelClient(Object clientId, Date nowTime, IbsStateEnum state) throws SQLException {
		String sql = "update ibsp_client set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where IBSP_CLIENT_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(state.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("激活客户端");
		parameterList.add(clientId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.execute(sql, parameterList);
	}

	/**
	 * 获取客户端ID
	 * @param clientCode 客户端编码
	 * @return 客户端ID
	 */
	public String findId(String clientCode) throws SQLException {
		String sql = "SELECT IBSP_CLIENT_ID_ FROM ibsp_client where CLIENT_CODE_ = ? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findString("IBSP_CLIENT_ID_",sql, parameterList);
	}

	/**
	 * 获取客户端IP
	 * @param clientCode 客户端编码
	 * @return IP
	 */
	public String findIp(String clientCode) throws SQLException {
		String sql = "SELECT REGISTER_IP_ as key_ FROM ibsp_client where CLIENT_CODE_ = ? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 分页获取客户机
	 *
	 * @param ip         ip
	 * @param clientCode 客户机编码
	 * @param pageIndex  页数
	 * @param pageSize   条数
	 */
	public PageCoreBean listShow(String ip, String clientCode , Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount="SELECT count(*) FROM (";
		String sql="SELECT ic.REGISTER_IP_,ic.CLIENT_CODE_,ic.STATE_,icc.CLIENT_CAPACITY_MAX_,icc.CLIENT_CAPACITY_,ich.UPDATE_TIME_LONG_,ich.STATE_ hearBeatState FROM ibsp_client ic"
				+ " LEFT JOIN ibsp_client_capacity icc ON ic.IBSP_CLIENT_ID_=icc.CLIENT_ID_ LEFT JOIN ibsp_client_heartbeat ich ON ic.CLIENT_CODE_=ich.CLIENT_CODE_"
				+ " WHERE (ic.STATE_=? or ic.STATE_=?) AND icc.STATE_!=? AND ich.STATE_!=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.CLOSE.name());
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		String sqlPlus = "";
		if(StringTool.notEmpty(ip)){
			sqlPlus+=" and ic.REGISTER_IP_ like ?";
			ip = "%" + ip + "%";
			parameterList.add(ip);
		}
		if(StringTool.notEmpty(clientCode)){
			sqlPlus+=" and ic.CLIENT_CODE_ like ?";
			clientCode = "%" + clientCode + "%";
			parameterList.add(clientCode);
		}
		sqlPlus += " order by ic.UPDATE_TIME_LONG_ desc";
		sql += sqlPlus;
		sqlCount += sql+") t";
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 修改客户机状态
	 * @param clientId    客户端ID
	 * @param state         状态
	 */
	public void updateState(String clientId, String state,Date nowTime) throws SQLException {
		String sql="update ibsp_client set STATE_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSP_CLIENT_ID_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(state);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clientId);
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 获取客户机列表
	 * @param state
	 * @return
	 */
	public List<String> findByState(String state) throws SQLException {
		String sql = "SELECT CLIENT_CODE_ FROM ibsp_client where state_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(state);
		return this.dao.findStringList("CLIENT_CODE_",sql,parameterList);
	}
}
