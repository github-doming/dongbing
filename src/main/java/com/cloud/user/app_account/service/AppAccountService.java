package com.cloud.user.app_account.service;

import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.entity.AppAccount;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APP账号表 服务类
 *
 * @author Robot
 */
public class AppAccountService extends BaseServiceProxy {

	/**
	 * 保存APP账号表 对象数据
	 *
	 * @param entity AppAccount对象数据
	 */
	public String save(AppAccount entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_account 的 APP_ACCOUNT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_account set state_='DEL' where APP_ACCOUNT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_ACCOUNT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_account 的 APP_ACCOUNT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update app_account set state_='DEL' where APP_ACCOUNT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_account  的 APP_ACCOUNT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_account where APP_ACCOUNT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_ACCOUNT_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_account 的 APP_ACCOUNT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_account where APP_ACCOUNT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppAccount实体信息
	 *
	 * @param entity APP账号表 实体
	 */
	public void update(AppAccount entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_account表主键查找 APP账号表 实体
	 *
	 * @param id app_account 主键
	 * @return APP账号表 实体
	 */
	public AppAccount find(String id) throws Exception {
		return dao.find(AppAccount.class, id);
	}

	/**
	 * 获取用户主键
	 *
	 * @param accountName     账户名称
	 * @param accountPassword 账户密码
	 * @return 用户主键
	 */
	public String findAppUserId(String accountName, String accountPassword)
			throws Exception {
		String password = EncryptTool.encode(EncryptTool.Type.ASE, accountPassword);
		if (StringTool.isEmpty(password)) {
			return null;
		}
		String sql = "SELECT APP_USER_ID_ FROM app_account WHERE ACCOUNT_NAME_ = ? AND PASSWORD_ = ? "
				+ " AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(accountName);
		parameterList.add(password);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString("APP_USER_ID_", sql, parameterList);
	}

	/**
	 * 更新登录
	 *
	 * @param appUserId   用户主键
	 * @param accountName 账户名称
	 * @param nowTime     登录事件
	 */
	public int updateLogin(String appUserId, String accountName, Date nowTime) throws SQLException {
		String sql =
				"UPDATE app_account set LOGIN_TIME_ = ?,LOGIN_TIME_LONG_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
						+ " where APP_USER_ID_ = ? and ACCOUNT_NAME_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		parameterList.add(accountName);
		return super.execute(sql, parameterList);
	}

	/**
	 * 根据用户名称查找用户主键
	 *
	 * @param accountName 账户名称
	 * @return 用户主键
	 */
	public String findUserIdByName(String accountName) throws Exception {
		String sql = "SELECT APP_USER_ID_ as key_ FROM app_account WHERE ACCOUNT_NAME_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(accountName);
		parameterList.add(StateEnum.DEL.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 根据密码查找主键
	 *
	 * @param userId   用户主键
	 * @param password 密码
	 * @return 主键
	 */
	public String findIdByPassword(String userId, String password) throws Exception {
		password = EncryptTool.encode(EncryptTool.Type.ASE, password);
		if (StringTool.isEmpty(password)) {
			return null;
		}
		String sql = "SELECT APP_ACCOUNT_ID_ as key_ FROM app_account WHERE APP_USER_ID_ = ? and  PASSWORD_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(password);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 更新密码
	 *
	 * @param accountId 账户主键
	 * @param password  密码
	 */
	public int update(String accountId, String password) throws SQLException {
		String sql = "UPDATE app_account set PASSWORD_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_ACCOUNT_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(password);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(accountId);
		return super.execute(sql, parameterList);
	}

	/**
	 * 更新密码
	 *
	 * @param userId       APP用户主键
	 * @param password     密码
	 * @param updateUserId 更新者UPDATE_USER_
	 */
	public int update(String userId, String password, String updateUserId) throws SQLException {
		String sql = "UPDATE app_account set PASSWORD_ = ?,UPDATE_USER_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(password);
		parameterList.add(updateUserId);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(userId);
		return super.execute(sql, parameterList);
	}

	/**
	 * 更新用户状态 -删除用户
	 *
	 * @param appUserId 用户主键
	 */
	public int updateState(String appUserId,Date nowTime) throws SQLException {
		String sql = "UPDATE app_account set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? "
				+ " where APP_USER_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		return super.execute(sql, parameterList);
	}

}
