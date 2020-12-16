package com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.entity.IbspClientHandicapCapacity;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_客户端盘口容量记录 服务类
 *
 * @author Robot
 */
public class IbspClientHandicapCapacityService extends BaseServiceProxy {

	/**
	 * 保存IBSP_客户端盘口容量记录 对象数据
	 *
	 * @param entity IbspClientHandicapCapacity对象数据
	 */
	public String save(IbspClientHandicapCapacity entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_client_handicap_capacity 的 IBSP_CLIENT_HANDICAP_CAPACITY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_client_handicap_capacity set state_='DEL' where IBSP_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSP_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_client_handicap_capacity 的 IBSP_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsp_client_handicap_capacity set state_='DEL' where IBSP_CLIENT_HANDICAP_CAPACITY_ID_ in("
							+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_client_handicap_capacity  的 IBSP_CLIENT_HANDICAP_CAPACITY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_client_handicap_capacity where IBSP_CLIENT_HANDICAP_CAPACITY_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSP_CLIENT_HANDICAP_CAPACITY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_client_handicap_capacity 的 IBSP_CLIENT_HANDICAP_CAPACITY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_client_handicap_capacity where IBSP_CLIENT_HANDICAP_CAPACITY_ID_ in("
					+ stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspClientHandicapCapacity实体信息
	 *
	 * @param entity IBSP_客户端盘口容量记录 实体
	 */
	public void update(IbspClientHandicapCapacity entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_client_handicap_capacity表主键查找 IBSP_客户端盘口容量记录 实体
	 *
	 * @param id ibsp_client_handicap_capacity 主键
	 * @return IBSP_客户端盘口容量记录 实体
	 */
	public IbspClientHandicapCapacity find(String id) throws Exception {
		return dao.find(IbspClientHandicapCapacity.class, id);

	}
	/**
	 * 注销客户端
	 *
	 * @param clientId 客户端主键
	 * @param nowTime  注销时间
	 */
	public void cancelClient(Object clientId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_client_handicap_capacity SET STATE_ = ?,UPDATE_TIME_ = ?,"
				+ " UPDATE_TIME_LONG_ = ?,DESC_ = ? WHERE CLIENT_ID_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
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
	 * @param clientId     客户端主键
	 * @param handicapCode 盘口编码
	 * @param capacityMax  盘口最大容量
	 * @param capacity     盘口使用容量
	 * @param nowTime      当前时间
	 */
	public void updateCapacity(String clientId, String handicapCode, int capacityMax, int capacity, Date nowTime)
			throws SQLException {
		String sql = "UPDATE ibsp_client_handicap_capacity SET CAPACITY_HANDICAP_MAX_ = ?,CAPACITY_HANDICAP_ = ?,"
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE CLIENT_ID_ = ? and HANDICAP_CODE_ = ? and  STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(capacityMax);
		parameterList.add(capacity);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(clientId);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);

	}

	/**
	 * 获取客户端盘口容量信息
	 *
	 * @param clientCode 客户端编码
	 */
	public PageCoreBean<Map<String, Object>> findByClientCode(String clientCode, int pageIndex, int pageSize) throws SQLException {
		String sql = "select CLIENT_CODE_,HANDICAP_CODE_,CAPACITY_HANDICAP_MAX_,CAPACITY_HANDICAP_"
				+ " from ibsp_client_handicap_capacity where CLIENT_CODE_=? and STATE_=?";
		String sqlCount = "SELECT count(*) FROM( ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(clientCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		sqlCount = sqlCount + sql + ") AS t  ";
		return super.dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 获取盘口容量信息
	 *
	 * @param clientCode   客户端编码
	 * @param handicapCode 盘口编码
	 * @return
	 */
	public Map<String, Object> findHandicapCapacityInfo(String clientCode, String handicapCode) throws SQLException {
		String sql = "select CLIENT_CODE_,HANDICAP_CODE_,CAPACITY_HANDICAP_MAX_ from ibsp_client_handicap_capacity"
				+ " where CLIENT_CODE_=? and HANDICAP_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(clientCode);
		parameterList.add(handicapCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

}
