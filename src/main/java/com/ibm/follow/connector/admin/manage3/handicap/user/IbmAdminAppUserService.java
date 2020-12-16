package com.ibm.follow.connector.admin.manage3.handicap.user;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-08 17:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminAppUserService extends IbmUserService {


	/**
	 * 获取用户信息
	 *
	 * @param userType 用户类型
	 * @param key      搜索key
	 * @return 用户信息
	 */
	public List<Map<String, Object>> listShow(String userType, String key) throws SQLException {
		String sql = "SELECT APP_USER_NAME_,NICKNAME_,APP_USER_TYPE_,APP_USER_ID_ FROM app_user  where state_!= ? ";
		ArrayList<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());

		if (StringTool.notEmpty(userType)) {
			sql += " AND APP_USER_TYPE_ = ? ";
			parameterList.add(userType);
		}
		if (StringTool.notEmpty(key)) {
			sql += " AND ( APP_USER_NAME_ LIKE ? or NICKNAME_ LIKE ?) ";
			key = "%" + key + "%";
			parameterList.add(key);
			parameterList.add(key);
		}
		sql += " order by UPDATE_TIME_LONG_ desc";
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取用户信息
	 *
	 * @param appUserId
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> listShow(String appUserId) throws SQLException {
		String sql = "SELECT APP_USER_NAME_,NICKNAME_,APP_USER_TYPE_,APP_USER_ID_ FROM app_user  where APP_USER_ID_ = ? AND state_!= ? ";
		ArrayList<Object> parameterList = new ArrayList<>(2);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());

		return this.dao.findMap(sql, parameterList);
	}

	/**
	 * 删除某个用户的所有盘口信息
	 *
	 * @param appUserId 用户主键
	 * @param nowTime   删除时间
	 * @param desc      描述
	 */
	public void delByUserId(String appUserId, Date nowTime, String desc) throws SQLException {
		String sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " HANDICAP_AGENT_ID_ IN (SELECT IBM_HANDICAP_AGENT_ID_ FROM ibm_handicap_agent WHERE "
				+ " APP_USER_ID_ = ? AND STATE_ != ?) and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);
		parameterList.add(appUserId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(IbmStateEnum.DEL.name());

		//代理
		super.dao.execute(String.format(sqlFormat, "ibm_ha_follow_period"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_ha_member_bet_item"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_ha_notice"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_ha_set"), parameterList);

		sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? where "
				+ " HANDICAP_MEMBER_ID_ IN (SELECT IBM_HANDICAP_MEMBER_ID_ FROM ibm_handicap_member WHERE "
				+ " APP_USER_ID_ = ? AND STATE_ != ?) and STATE_ != ?";
		//会员
		super.dao.execute(String.format(sqlFormat, "ibm_hm_bet_item"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_notice"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_profit"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_item"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_period"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_period_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_profit_vr"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_set"), parameterList);

		sqlFormat = "UPDATE %s set STATE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where %s = ? and STATE_ != ?";
		parameterList.remove(parameterList.size() - 1);

		//代理
		super.dao.execute(String.format(sqlFormat, "ibm_ha_game_set", "USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_ha_info", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_ha_user", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_handicap_agent_member", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_handicap_agent", "APP_USER_ID_"), parameterList);

		//会员
		super.dao.execute(String.format(sqlFormat, "ibm_hm_game_set", "USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_info", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_hm_user", "APP_USER_ID_"), parameterList);
		super.dao.execute(String.format(sqlFormat, "ibm_handicap_member", "APP_USER_ID_"), parameterList);

	}

	/**
	 * 更新用户类型
	 *
	 * @param appUserId
	 * @param userType
	 * @param nowTime
	 * @throws SQLException
	 */
	public void updateUserTypeByAppUserId(String appUserId, String userType, Date nowTime) throws SQLException {
		String sql = "UPDATE `app_user` SET  `APP_USER_TYPE_` = ?,`UPDATE_USER_` =? ,`UPDATE_TIME_` =? ,`UPDATE_TIME_LONG_` =?  WHERE `APP_USER_ID_` = ? ";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(userType);
		parameterList.add(appUserId);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(appUserId);
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 查找指定时间之前的用户Id
	 *
	 * @param expireDate
	 * @return
	 * @throws SQLException
	 */
	public List<String> listExpireUser(Date expireDate) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `app_user` WHERE LOGIN_TIME_ <= ? AND STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(expireDate);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findStringList("APP_USER_ID_", sql, parameterList);
	}

	/**
	 * 获取用户信息
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public Map<String, Object> findUserInfo(String appUserId) throws SQLException {
		String sql = "SELECT iu.NICKNAME_,imt.END_TIME_LONG_ FROM ibm_user iu "
				+ " LEFT JOIN ibm_manage_time imt ON iu.APP_USER_ID_=imt.APP_USER_ID_ WHERE iu.APP_USER_ID_=?";
		ArrayList<Object> parameterList = new ArrayList<>(1);
		parameterList.add(appUserId);
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 根据用户id逻辑删除
	 *
	 * @param appUserId 用户id
	 */
	public void delUserInfo(String appUserId) throws SQLException {
		String sqlFormat = "update %s set state_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? WHERE APP_USER_ID_=? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(appUserId);
		dao.execute(String.format(sqlFormat, "app_account"), parameters);
		dao.execute(String.format(sqlFormat, "app_token"), parameters);
		dao.execute(String.format(sqlFormat, "app_user"), parameters);
		dao.execute(String.format(sqlFormat, "ibm_user"), parameters);
		dao.execute(String.format(sqlFormat, "ibm_manage_point"), parameters);
		dao.execute(String.format(sqlFormat, "ibm_manage_time"), parameters);
		dao.execute(String.format(sqlFormat, "ibm_exp_user"), parameters);
	}

	/**
	 * 获取用户最新一条记录
	 *
	 * @param userId 用户Id
	 */
	public Map<String, Object> findUserLastInfo(String userId) throws SQLException {
		String sql = "select IP_,STATE_ from app_token where APP_USER_ID_ = ? AND STATE_!=? " +
				"order by UPDATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 根据用户名查找用户Id
	 *
	 * @param userName 用户名
	 */
	public String findUserIdByUserName(String userName) throws SQLException {
		String sql = "SELECT APP_USER_ID_ FROM `app_user` WHERE APP_USER_NAME_ = ? AND APP_USER_TYPE_= ? AND STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userName);
		parameterList.add(IbmTypeEnum.USER.name());
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findString("APP_USER_ID_", sql, parameterList);
	}
}
