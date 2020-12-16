package com.ibs.plan.module.cloud.ibsp_client_capacity.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client_capacity.entity.IbspClientCapacity;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_客户端容量记录 服务类
 *
 * @author Robot
 */
public class IbspClientCapacityService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户端容量记录 对象数据
	 *
	 * @param entity IbspClientCapacity对象数据
	 */
	public String save(IbspClientCapacity entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_client_capacity 的 IBSP_CLIENT_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_client_capacity set state_='DEL' where IBSP_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_client_capacity 的 IBSP_CLIENT_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_client_capacity set state_='DEL' where IBSP_CLIENT_CAPACITY_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_client_capacity  的 IBSP_CLIENT_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_client_capacity where IBSP_CLIENT_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_CLIENT_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_client_capacity 的 IBSP_CLIENT_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibsp_client_capacity where IBSP_CLIENT_CAPACITY_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspClientCapacity实体信息
	 *
	 * @param entity IBSP_客户端容量记录 实体
	 */
	public void update(IbspClientCapacity entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_client_capacity表主键查找 IBSP_客户端容量记录 实体
	 *
	 * @param id ibsp_client_capacity 主键
	 * @return IBSP_客户端容量记录 实体
	 */
	public IbspClientCapacity find(String id) throws Exception {
		return dao.find(IbspClientCapacity.class, id);

	}
	/**
	 * 注销客户端
	 *
	 * @param clientId 客户端主键
	 * @param nowTime  注销时间
	 */
	public void cancelClient(Object clientId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_client_capacity SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("注销客户端");
		parameterList.add(clientId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 更新容量
	 *
	 * @param clientId     客户端id
	 * @param capacityMax  最大容量
	 * @param usedCapacity 使用容量
	 * @param nowTime      更新时间
	 */
	public void updateCapacity(String clientId, int capacityMax, int usedCapacity, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_client_capacity SET CLIENT_CAPACITY_MAX_ = ?,CLIENT_CAPACITY_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(capacityMax);
		parameterList.add(usedCapacity);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(clientId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);

	}
	/**
	 * 获取容量信息
	 *
	 * @param handicapCode 盘口信息
	 * @param planType 	 客户端类型
	 * @return 容量信息
	 */
	public List<Map<String, Object>> listCapacityInfo(String handicapCode,String planType) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_, icc.CLIENT_CAPACITY_, icc.CLIENT_CAPACITY_MAX_,"
				+ " ichc.CAPACITY_HANDICAP_, ichc.CAPACITY_HANDICAP_MAX_ FROM ibsp_client_capacity icc"
				+ " LEFT JOIN ibsp_client ic ON icc.CLIENT_ID_=ic.IBSP_CLIENT_ID_"
				+ " LEFT JOIN ibsp_client_handicap_capacity ichc ON icc.IBSP_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_"
				+ " AND ichc.HANDICAP_CODE_ = ? WHERE ic.CLIENT_TYPE_=? AND icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_"
				+ " AND icc.STATE_ = ? AND ichc.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(planType);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}
	/**
	 * 获取验证数据容量信息
	 *
	 * @param handicapCode 盘口信息
	 * @return 容量信息
	 */
	public List<Map<String, Object>> listVerifyCapacityInfo(String handicapCode) throws SQLException {
		String sql = "SELECT icc.CLIENT_ID_, icc.CLIENT_CODE_, icc.CLIENT_CAPACITY_, icc.CLIENT_CAPACITY_MAX_, "
				+ " ichc.CAPACITY_HANDICAP_, ichc.CAPACITY_HANDICAP_MAX_ FROM ibsp_client_capacity icc"
				+ " LEFT JOIN ibsp_client_handicap_capacity ichc ON icc.IBSP_CLIENT_CAPACITY_ID_ = ichc.CLIENT_CAPACITY_ID_"
				+ " AND ichc.HANDICAP_CODE_ = ? WHERE icc.CLIENT_CAPACITY_ < icc.CLIENT_CAPACITY_MAX_ - 3"
				+ " AND ichc.CAPACITY_HANDICAP_ < ichc.CAPACITY_HANDICAP_MAX_ - 3 AND icc.STATE_ = ? AND ichc.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}


	/**
	 * 获取客户机容量信息
	 * @param clientCode      客户端编码
	 * @return
	 */
	public Map<String, Object> findcapacityInfo(String clientCode) throws SQLException {
		String sql="select ic.REGISTER_IP_,ic.CLIENT_CODE_,icc.CLIENT_CAPACITY_MAX_,ic.STATE_ from ibsp_client ic"
				+ " LEFT JOIN ibsp_client_capacity icc ON ic.IBM_CLIENT_ID_=icc.CLIENT_ID_"
				+ " where icc.CLIENT_CODE_=? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(clientCode);
		return super.dao.findMap(sql,parameterList);
	}

	/**
	 * 更新最大容量
	 *
	 * @param clientId     客户端id
	 * @param capacityMax  最大容量
	 * @param nowTime      更新时间
	 */
	public void updateCapacity(String clientId, int capacityMax,  Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_client_capacity SET CLIENT_CAPACITY_MAX_ = ? ,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(capacityMax);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add("修改客户端最大容量");
		parameterList.add(clientId);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);

	}
}
