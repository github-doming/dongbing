package com.cloud.user.app_user.service;

import com.cloud.user.app_user.entity.AppUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * APP用户表 服务类
 *
 * @author Robot
 */
public class AppUserService extends BaseServiceProxy {

	/**
	 * 保存APP用户表 对象数据
	 *
	 * @param entity AppUser对象数据
	 */
	public String save(AppUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_user 的 APP_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_user set state_='DEL' where APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_user 的 APP_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_user set state_='DEL' where APP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_user  的 APP_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_user where APP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_user 的 APP_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_user where APP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppUser实体信息
	 *
	 * @param entity APP用户表 实体
	 */
	public void update(AppUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_user表主键查找 APP用户表 实体
	 *
	 * @param id app_user 主键
	 * @return APP用户表 实体
	 */
	public AppUser find(String id) throws Exception {
		return dao.find(AppUser.class, id);
	}

	/**
	 * 查找状态
	 *
	 * @param appUserId app_user主键
	 * @return 状态
	 */
	public String findState(String appUserId, String userType) throws SQLException {
		String sql = "SELECT STATE_ FROM app_user WHERE  APP_USER_ID_ = ? and APP_USER_TYPE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		parameterList.add(userType);
		parameterList.add(StateEnum.DEL.name());
		return super.findString("STATE_", sql, parameterList);
	}

	/**
	 * 查找状态
	 *
	 * @param appUserId app_user主键
	 * @return 状态
	 */
	public String findStateByName(String appUserId, String accountName) throws SQLException {
		String sql = "SELECT aa.STATE_ FROM app_user au LEFT JOIN "
				+ " (SELECT STATE_,APP_USER_ID_  FROM app_account WHERE ACCOUNT_NAME_ = ? AND APP_USER_ID_ = ?) "
				+ " aa ON au.APP_USER_ID_ = aa.APP_USER_ID_ WHERE au.APP_USER_ID_ = ? AND aa.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(accountName);
		parameterList.add(appUserId);
		parameterList.add(appUserId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString("STATE_", sql, parameterList);
	}
	/**
	 * 查找信息
	 *
	 * @param appUserId   app_user主键
	 * @param accountName APP用户名称
	 * @return 状态
	 */
	public Map<String, Object> findInfoByName(String appUserId, String accountName) throws SQLException {
		String sql = "SELECT au.NICKNAME_,aa.STATE_,au.APP_USER_CODE_ channelType  FROM app_user au LEFT JOIN "
				+ " (SELECT STATE_,APP_USER_ID_  FROM app_account WHERE ACCOUNT_NAME_ = ? AND APP_USER_ID_ = ?) "
				+ " aa ON au.APP_USER_ID_ = aa.APP_USER_ID_ WHERE au.APP_USER_ID_ = ? AND aa.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(accountName);
		parameterList.add(appUserId);
		parameterList.add(appUserId);
		parameterList.add(StateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}
	/**
	 * 查找信息
	 *
	 * @param userId   app_user主键
	 * @param userName APP用户名称
	 * @param userType APP用户类型
	 * @return 状态，昵称
	 */
	public Map<String, Object> findInfoByName(String userId, String userName, String userType) throws SQLException {
		String sql = "SELECT au.NICKNAME_,au.STATE_ FROM app_user au "
				+ "WHERE au.APP_USER_ID_ = ? AND au.APP_USER_NAME_ = ? AND au.APP_USER_TYPE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(userName);
		parameterList.add(userType);
		return super.findMap(sql, parameterList);
	}

	/**
	 * 更新登录
	 *
	 * @param appUserId 用户主键
	 * @param nowTime   登录事件
	 */
	public int updateLogin(String appUserId, Date nowTime) throws SQLException {
		String sql = "UPDATE app_user set LOGIN_TIME_ = ?,LOGIN_TIME_LONG_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		return super.execute(sql, parameterList);
	}

	/**
	 * 根据token查找用户
	 *
	 * @param token 用户token
	 * @return 用户
	 */
	public AppUser findUserByToken(String token) throws Exception {
		String sql = "SELECT ca.* FROM app_user ca LEFT JOIN app_token cat ON "
				+ " ca.APP_USER_ID_ = cat.APP_USER_ID_ AND cat.STATE_ = ? WHERE cat.VALUE_ = ? AND ca.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.findObject(AppUser.class, sql, parameterList);
	}


	/**
	 * 删除用户
	 *
	 * @param userId 用户主键
	 */
	public void delUser(String userId, Date nowTime) throws SQLException {
		String sql = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		super.execute(String.format(sql,"app_user"), parameterList);
		super.execute(String.format(sql,"app_account"), parameterList);
		super.execute(String.format(sql,"app_token"), parameterList);
		super.execute(String.format(sql,"card_admin"), parameterList);
		super.execute(String.format(sql,"card_admin_token"), parameterList);
	}
}
