package com.cloud.user.app_token.service;

import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.app_token.entity.AppToken;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * APP令牌APP_TOKEN 服务类
 *
 * @author Robot
 */
public class AppTokenService extends BaseServiceProxy {

	/**
	 * 保存APP令牌APP_TOKEN 对象数据
	 *
	 * @param entity AppToken对象数据
	 */
	public String save(AppToken entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_token 的 APP_TOKEN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_token set state_='DEL' where APP_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_token 的 APP_TOKEN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_token set state_='DEL' where APP_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_token  的 APP_TOKEN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_token where APP_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_token 的 APP_TOKEN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_token where APP_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppToken实体信息
	 *
	 * @param entity APP令牌APP_TOKEN 实体
	 */
	public void update(AppToken entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_token表主键查找 APP令牌APP_TOKEN 实体
	 *
	 * @param id app_token 主键
	 * @return APP令牌APP_TOKEN 实体
	 */
	public AppToken find(String id) throws Exception {
		return dao.find(AppToken.class, id);
	}

	/**
	 * 查找token主键
	 *
	 * @param appUserId   用户主键
	 * @param channelType 通道类型
	 * @return APP_TOKEN_ID_
	 */
	public String findId(String appUserId, ChannelTypeEnum channelType) throws SQLException {
		String sql =
				"SELECT APP_TOKEN_ID_ FROM app_token WHERE APP_USER_ID_ = ? AND CHANNEL_TYPE_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(appUserId);
		parameterList.add(channelType.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findString("APP_TOKEN_ID_", sql, parameterList);
	}

	/**
	 * 更新token信息
	 *
	 * @param tokenId        token主键
	 * @param value          token值
	 * @param servletIp      服务IP
	 * @param periodTimeLong 到期时间
	 * @param nowTime        更新时间
	 */
	public void update(String tokenId, String value, String servletIp, long periodTimeLong, Date nowTime)
			throws SQLException {
		String sql = "UPDATE app_token SET VALUE_ = ?,IP_ = ?,PERIOD_TIME_ = ?,PERIOD_TIME_LONG_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE APP_TOKEN_ID_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(value);
		parameterList.add(servletIp);
		parameterList.add(new Date(periodTimeLong));
		parameterList.add(periodTimeLong);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(tokenId);
		super.execute(sql, parameterList);
	}

	/**
	 * 根据用户名称查找用户主键
	 *
	 * @param token token
	 * @return 用户主键
	 */
	public String findUserIdByToken(String token) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ FROM app_token WHERE VALUE_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 根据用户名称查找用户主键
	 *
	 * @param token       token
	 * @param channelType 渠道类型
	 * @return 用户主键
	 */
	public String findUserIdByToken(String token, String channelType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ FROM app_token WHERE VALUE_ = ?  AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(token);
		parameterList.add(StateEnum.OPEN.name());
		if (StringTool.notEmpty(channelType)) {
			sql += "AND CHANNEL_TYPE_ = ?";
			parameterList.add(channelType);
		}
		return super.findString(sql, parameterList);
	}

	/**
	 * 登出用户
	 *
	 * @param token 用户TOKEN
	 */
	public void logout(String token) throws SQLException {
		String sql = "UPDATE app_token SET VALUE_ = ?,STATE_ = ?,PERIOD_TIME_ = ?,PERIOD_TIME_LONG_ = ?, "
				+ " UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? WHERE VALUE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(RandomTool.getNumLetter32());
		parameterList.add(StateEnum.LOGOUT.name());
		parameterList.add(null);
		parameterList.add(0);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(token);
		super.execute(sql, parameterList);
	}
}
