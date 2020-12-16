package com.cloud.user.app_user_point_rep.service;

import com.cloud.user.app_user_point_rep.entity.AppUserPointRep;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * APP用户点数使用情况 服务类
 *
 * @author Robot
 */
public class AppUserPointRepService extends BaseServiceProxy {

	/**
	 * 保存APP用户点数使用情况 对象数据
	 *
	 * @param entity AppUserPointRep对象数据
	 */
	public String save(AppUserPointRep entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_user_point_rep 的 APP_USER_POINT_REP_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_user_point_rep set state_='DEL' where APP_USER_POINT_REP_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_USER_POINT_REP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_user_point_rep 的 APP_USER_POINT_REP_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_user_point_rep set state_='DEL' where APP_USER_POINT_REP_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_user_point_rep  的 APP_USER_POINT_REP_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_user_point_rep where APP_USER_POINT_REP_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_USER_POINT_REP_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_user_point_rep 的 APP_USER_POINT_REP_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from app_user_point_rep where APP_USER_POINT_REP_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppUserPointRep实体信息
	 *
	 * @param entity APP用户点数使用情况 实体
	 */
	public void update(AppUserPointRep entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_user_point_rep表主键查找 APP用户点数使用情况 实体
	 *
	 * @param id app_user_point_rep 主键
	 * @return APP用户点数使用情况 实体
	 */
	public AppUserPointRep find(String id) throws Exception {
		return dao.find(AppUserPointRep.class, id);
	}

	/**
	 * 保存日志
	 * @return 日志主键
	 */
	public String save(Map<String, Object> lastRepInfo, String userId, int point, String title, Date nowTime)
			throws SQLException {
		String sql = "INSERT INTO app_user_point_rep (APP_USER_POINT_REP_ID_,PRE_ID_,APP_USER_ID_,TITLE_, "
				+ " PRE_T_,NUMBER_T_,BALANCE_T_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
		long preTh = NumberTool.getLong(lastRepInfo, "BALANCE_T_", 0L);
		String repId = RandomTool.onlyCode("POINT_REP");
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(repId);
		parameters.add(lastRepInfo.getOrDefault("REP_POINT_ID_","NO_PRE_ID_"));
		parameters.add(userId);
		parameters.add(title);
		parameters.add(preTh);
		parameters.add(point * 1000);
		parameters.add(preTh + point * 1000);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);
		return repId;
	}

	/**
	 * 保存日志
	 * @return 日志主键
	 */
	public void savePointLog(Map<String, Object> lastRepInfo, String userId, int point, String title, Date nowTime)
			throws SQLException {
		String sql = "INSERT INTO app_user_point_rep (APP_USER_POINT_REP_ID_,PRE_ID_,APP_USER_ID_,TITLE_, "
				+ " PRE_T_,NUMBER_T_,BALANCE_T_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_LONG_,STATE_) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
		long preTh = NumberTool.getLong(lastRepInfo, "BALANCE_T_", 0L);
		String repId = RandomTool.onlyCode("POINT_REP");
		List<Object> parameters = new ArrayList<>(8);
		parameters.add(repId);
		parameters.add(lastRepInfo.getOrDefault("REP_POINT_ID_","NO_PRE_ID_"));
		parameters.add(userId);
		parameters.add(title);
		parameters.add(preTh);
		parameters.add(point*1000);
		parameters.add(point*1000);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		super.execute(sql, parameters);
	}
}
