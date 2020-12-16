package com.cloud.user.app_user_point.service;

import com.alibaba.fastjson.JSONArray;
import com.cloud.user.app_user_point.entity.AppUserPoint;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * APP用户点数 服务类
 *
 * @author Robot
 */
public class AppUserPointService extends BaseServiceProxy {

	/**
	 * 保存APP用户点数 对象数据
	 *
	 * @param entity AppUserPoint对象数据
	 */
	public String save(AppUserPoint entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_user_point 的 APP_USER_POINT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_user_point set state_='DEL' where APP_USER_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_USER_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_user_point 的 APP_USER_POINT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update app_user_point set state_='DEL' where APP_USER_POINT_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_user_point  的 APP_USER_POINT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_user_point where APP_USER_POINT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_USER_POINT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_user_point 的 APP_USER_POINT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_user_point where APP_USER_POINT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppUserPoint实体信息
	 *
	 * @param entity APP用户点数 实体
	 */
	public void update(AppUserPoint entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_user_point表主键查找 APP用户点数 实体
	 *
	 * @param id app_user_point 主键
	 * @return APP用户点数 实体
	 */
	public AppUserPoint find(String id) throws Exception {
		return dao.find(AppUserPoint.class, id);
	}

	/**
	 * 获取最后一次的报表信息
	 *
	 * @param userId 用户主键
	 * @return 报表信息
	 */
	public Map<String, Object> findLastRepInfo(String userId) throws SQLException {
		String sql = "SELECT APP_USER_POINT_ID_,REP_POINT_ID_,USEABLE_POINT_T_ BALANCE_T_ FROM app_user_point "
				+ " WHERE APP_USER_ID_ = ?  AND STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 更新可用积分
	 *
	 * @param appUserPointId APP用户点数主键
	 * @param point          积分
	 * @param repId          末次报表主键
	 */
	public void update(String appUserPointId, long point, String repId, Date nowTime) throws SQLException {
		String sql = "UPDATE app_user_point set REP_POINT_ID_ = ?,TOTAL_POINT_T_ = TOTAL_POINT_T_ + ?, "
				+ " USEABLE_POINT_T_ = USEABLE_POINT_T_ + ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_POINT_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(repId);
		parameterList.add(point * 1000);
		parameterList.add(point * 1000);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserPointId);
		super.execute(sql, parameterList);
	}

	/**
	 * 修改可用积分
	 *
	 * @param appUserPointId APP用户点数主键
	 * @param point          积分
	 * @param repId          末次报表主键
	 */
	public void updatePoint(String appUserPointId, int point, String repId, Date nowTime) throws SQLException {
		String sql = "UPDATE app_user_point set REP_POINT_ID_ = ?,TOTAL_POINT_T_ = ?, "
				+ " USEABLE_POINT_T_ =?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_POINT_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(repId);
		parameterList.add(point*1000 );
		parameterList.add(point*1000);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserPointId);
		super.execute(sql, parameterList);
	}

	/**
	 * 保存用户点数
	 */
	public void save(String userId, String repId, int point, Date nowTime) throws SQLException {
		String sql = "INSERT INTO app_user_point (APP_USER_POINT_ID_,APP_USER_ID_,REP_POINT_ID_,TOTAL_POINT_T_, "
				+ " USEABLE_POINT_T_,FROZEN_POINT_T_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(RandomTool.onlyCode("USER_POINT"));
		parameters.add(userId);
		parameters.add(repId);
		parameters.add(point * 1000);
		parameters.add(point * 1000);
		parameters.add(0);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);
	}


	public double findUseablePoint(String userId) throws SQLException {
		String sql = "SELECT USEABLE_POINT_T_ FROM app_user_point where APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(StateEnum.OPEN.name());
		return NumberTool.doubleT(NumberTool.getLong(super.findMap(sql, parameterList), "USEABLE_POINT_T_", 0L));
	}
	/**
	 * 获取可用积分
	 *
	 * @return 可用积分
	 */
	public Map<String,Object> getUsersUseablePoint(JSONArray userIds) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT APP_USER_ID_ key_,USEABLE_POINT_T_ value_ FROM `app_user_point` where STATE_ != ?  ");
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(StateEnum.DEL.name());
		if(ContainerTool.isEmpty(userIds)){
			return findKVMap(sql.toString(),parameterList);
		}
		sql.append(" and APP_USER_ID_ in(");
		for(Object userId:userIds){
			sql.append("?,");
			parameterList.add(userId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return findKVMap(sql.toString(),parameterList);
	}
}
