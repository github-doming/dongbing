package com.ibm.follow.servlet.cloud.ibm_manage_time.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_manage_time.entity.IbmManageTime;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBM_用户时长 的服务类
 *
 * @author Robot
 */
public class IbmManageTimeService extends BaseServicePlus {

	/**
	 * 保存IBM_用户时长对象数据
	 *
	 * @param entity IbmManageTime对象数据
	 */
	public String save(IbmManageTime entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_manage_time 的 IBM_MANAGE_TIME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_manage_time set state_='DEL' where IBM_MANAGE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_MANAGE_TIME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_manage_time 的 IBM_MANAGE_TIME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_manage_time set state_='DEL' where IBM_MANAGE_TIME_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_manage_time  的 IBM_MANAGE_TIME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_manage_time where IBM_MANAGE_TIME_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_MANAGE_TIME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除IBM_用户时长的 IBM_MANAGE_TIME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_manage_time where IBM_MANAGE_TIME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmManageTime实体信息
	 *
	 * @param entity IBM_用户时长实体
	 */
	public void update(IbmManageTime entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_manage_time表主键查找IbmManageTime实体
	 *
	 * @param id ibm_manage_time 主键
	 * @return IBM_用户时长实体
	 */
	public IbmManageTime find(String id) throws Exception {
		return (IbmManageTime) this.dao.find(IbmManageTime.class, id);

	}

	public void delByAppUserId(String appUserId) throws SQLException {
		String sql = "delete from ibm_manage_time where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	public void updateStateByAppUserId(String appUserId) throws SQLException {
		String sql = "update ibm_manage_time set STATE_ = ? where APP_USER_ID_= ? ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(appUserId);
		dao.execute(sql, parameterList);
	}

	/**
	 * 获取结束时间
	 *
	 * @param userId 用户之间
	 * @return 结束时间
	 */
	public Object getEndTime(String userId) throws SQLException {
		String sql = "SELECT END_TIME_LONG_  FROM ibm_manage_time where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> map = super.findMap(sql, parameterList);
		if (map.containsKey("END_TIME_LONG_")) {
			return DateTool.getMinute(new Date(NumberTool.getLong(map.get("END_TIME_LONG_"))));
		}
		return null;
	}


	/**
	 * 获取结束时间
	 *
	 * @param userId 用户id
	 * @return 结束时间
	 */
	public Long getEndLongTime(String userId) throws SQLException {
		String sql = "SELECT END_TIME_LONG_  FROM ibm_manage_time where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		Map<String, Object> map = super.findMap(sql, parameterList);
		if (map.containsKey("END_TIME_LONG_")) {
			return NumberTool.getLong(map.get("END_TIME_LONG_"));
		}
		return 0L;
	}

	/**
	 * 获取到期时间戳
	 *
	 * @param userIdList 用户ID集合
	 * @return 到期时间戳
	 */
	public Map<String, Object> getUsersEndTime(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT  APP_USER_ID_ key_,END_TIME_LONG_ value_ FROM ibm_manage_time where  STATE_ != ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String uid : userIdList) {
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");

		return findKVMap(sql, parameterList);
	}


	/**
	 * 更新结束时间
	 *
	 * @param userId     用户Id
	 * @param repId      记录主键
	 * @param newEndTime 新的结束时间
	 * @param nowTime    当前时间
	 */
	public void updateEndTime(String userId, String repId, Date newEndTime, Date nowTime) throws SQLException {
		String sql = "UPDATE `ibm_manage_time` SET `REP_TIME_ID_` = ?,`END_TIME_` = ?,`END_TIME_LONG_` = ?,`UPDATE_USER_` = ?,`UPDATE_TIME_` = ?,`UPDATE_TIME_LONG_` = ? where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(repId);
		parameterList.add(newEndTime);
		parameterList.add(newEndTime.getTime());
		parameterList.add(userId);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		execute(sql, parameterList);
	}

	/**
	 * 获取实体对象
	 *
	 * @param appUserId 用户id
	 * @return 实体
	 */
	public IbmManageTime findByUserId(String appUserId) throws Exception {
		String sql = "select * from ibm_manage_time where APP_USER_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findObject(IbmManageTime.class, sql, parameterList);
	}

	/**
	 * 查找即将过期和已过期的用户列表
	 *
	 * @param endTime 截止时间
	 * @return 用户列表
	 */
	public List<Map<String, Object>> listNearExpiredUser(Long endTime) throws SQLException {
		String sql = "SELECT au.APP_USER_NAME_,au.APP_USER_ID_,imt.END_TIME_LONG_ FROM `ibm_manage_time` imt LEFT JOIN app_user au on imt.APP_USER_ID_ = au.APP_USER_ID_ " +
				"where imt.END_TIME_LONG_ < ? AND imt.STATE_=? AND au.APP_USER_TYPE_=? and au.STATE_!=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(endTime);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmTypeEnum.ADMIN.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findMapList(sql, parameterList);
	}
}
