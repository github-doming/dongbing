package com.ibs.plan.module.cloud.ibsp_user_token.service;

import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_user_token.entity.IbspUserToken;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.*;

/**
 * 投注用户令牌 服务类
 *
 * @author Robot
 */
public class IbspUserTokenService extends BaseServiceProxy {

	/**
	 * 保存投注用户令牌 对象数据
	 *
	 * @param entity IbspUserToken对象数据
	 */
	public String save(IbspUserToken entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_user_token 的 IBSP_USER_TOKEN_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_user_token set state_='DEL' where IBSP_USER_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_USER_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_user_token 的 IBSP_USER_TOKEN_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_user_token set state_='DEL' where IBSP_USER_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_user_token  的 IBSP_USER_TOKEN_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_user_token where IBSP_USER_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_USER_TOKEN_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_user_token 的 IBSP_USER_TOKEN_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_user_token where IBSP_USER_TOKEN_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspUserToken实体信息
	 *
	 * @param entity 投注用户令牌 实体
	 */
	public void update(IbspUserToken entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_user_token表主键查找 投注用户令牌 实体
	 *
	 * @param id ibsp_user_token 主键
	 * @return 投注用户令牌 实体
	 */
	public IbspUserToken find(String id) throws Exception {
		return dao.find(IbspUserToken.class, id);
	}

	/**
	 * 查询用户主键
	 *
	 * @param token       用户token
	 * @param channelType 通道类型
	 * @param userType    用户类型
	 * @return 用户主键
	 */
	public String findUserId(String token, ChannelTypeEnum channelType, IbsTypeEnum userType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibsp_user_token where " +
				" VALUE_ = ? and CHANNEL_TYPE_ = ? and USER_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(token);
		parameterList.add(channelType.name());
		parameterList.add(userType.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 查询管理用户主键，用户类型不等于user
	 *
	 * @param token       用户token
	 * @param channelType 通道类型
	 * @return 用户主键
	 */
	public String findUserId(String token, ChannelTypeEnum channelType) throws SQLException {
		String sql = "SELECT APP_USER_ID_ as key_ from  ibsp_user_token where " +
				" VALUE_ = ? and CHANNEL_TYPE_ = ? and USER_TYPE_ !=? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(token);
		parameterList.add(channelType.name());
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 查询tokenId
	 *
	 * @param userId      用户id
	 * @param channelType 通道类型
	 * @param userType    用户类型
	 * @return
	 */
	public String findId(String userId, ChannelTypeEnum channelType, IbsTypeEnum userType) throws SQLException {
		String sql = "SELECT IBSP_USER_TOKEN_ID_ as key_ from  ibsp_user_token where " +
				" APP_USER_ID_ = ? and CHANNEL_TYPE_ = ? and USER_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(channelType.name());
		parameterList.add(userType.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}
	/**
	 * 查询tokenId
	 *
	 * @param userId      用户id
	 * @param channelType 通道类型
	 * @return
	 */
	public String findId(String userId, ChannelTypeEnum channelType) throws SQLException {
		String sql = "SELECT IBSP_USER_TOKEN_ID_ as key_ from  ibsp_user_token where " +
				" APP_USER_ID_ = ? and CHANNEL_TYPE_ = ? and USER_TYPE_!= ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(channelType.name());
		parameterList.add(IbsTypeEnum.USER.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findString(sql, parameterList);
	}

	/**
	 * 修改token
	 * @param tokenId		tokenId
	 * @param token		用户token
	 * @param servletIp	ip地址
	 * @param periodTimeLong	有效时间
	 * @param nowTime		当前时间
	 */
	public void updateValue(String tokenId, String token, String servletIp, long periodTimeLong, Date nowTime) throws SQLException {
		String sql="update ibsp_user_token set VALUE_=?,IP_=?,PERIOD_TIME_=?,PERIOD_TIME_LONG_=?,"
				+ "UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSP_USER_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(token);
		parameterList.add(servletIp);
		parameterList.add(new Date(periodTimeLong));
		parameterList.add(periodTimeLong);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(tokenId);
		super.dao.execute(sql,parameterList);
	}
	/**
	 * 修改token
	 * @param tokenId		tokenId
	 * @param token		用户token
	 * @param servletIp	ip地址
	 * @param periodTimeLong	有效时间
	 * @param nowTime		当前时间
	 */
	public void updateValue(String tokenId, String token, String servletIp,String userType, long periodTimeLong, Date nowTime) throws SQLException {
		String sql="update ibsp_user_token set VALUE_=?,IP_=?,USER_TYPE_=?,PERIOD_TIME_=?,PERIOD_TIME_LONG_=?,"
				+ "UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where IBSP_USER_TOKEN_ID_=?";
		List<Object> parameterList = new ArrayList<>(8);
		parameterList.add(token);
		parameterList.add(servletIp);
		parameterList.add(userType);
		parameterList.add(new Date(periodTimeLong));
		parameterList.add(periodTimeLong);
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(tokenId);
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 获取用户最新一条记录
	 *
	 * @param userId 用户Id
	 */
	public Map<String, Object> findUserLastInfo(String userId) throws SQLException {
		String sql = "select IP_,STATE_,VALUE_ from ibsp_user_token where APP_USER_ID_ = ? AND STATE_!=? " +
				"order by UPDATE_TIME_LONG_ DESC LIMIT 1";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(userId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 登出用户
	 *
	 * @param token 用户TOKEN
	 */
	public void logout(String token) throws SQLException {
		String sql = "UPDATE ibsp_user_token SET VALUE_ = ?,STATE_ = ?,PERIOD_TIME_ = ?,PERIOD_TIME_LONG_ = ?, "
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
	/**
	 * 获取用户信息
	 * @param userIds		用户ids
	 * @param userType	用户类型
	 * @return
	 */
	public Map<String, Object> findList(List<String> userIds, String userType) throws SQLException {
		StringBuilder sql = new StringBuilder("select APP_USER_ID_,IP_,UPDATE_TIME_LONG_ from ibsp_user_token where USER_TYPE_=? and STATE_= ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(userType);
		parameterList.add(StateEnum.OPEN.name());
		for (String userId : userIds) {
			sql.append("?,");
			parameterList.add(userId);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		List<Map<String,Object>> list=super.findMapList(sql,parameterList);
		Map<String, Object> loginInfos=new HashMap<>(list.size());
		if(ContainerTool.isEmpty(list)){
			return loginInfos;
		}
		Map<String,Object> loginTimes=new HashMap<>();
		for(Map<String,Object> map:list){
			String userId=map.get("APP_USER_ID_").toString();
			String ip=map.get("IP_").toString();
			long timeLong= NumberTool.getLong(map.get("UPDATE_TIME_LONG_"));
			if(loginInfos.containsKey(userId)){
				if(NumberTool.getLong(loginTimes.get(userId))<timeLong){
					loginInfos.put(userId,ip);
				}
			}else{
				loginInfos.put(userId,ip);
				loginTimes.put(userId,timeLong);
			}
		}
		return loginInfos;
	}

	/**
	 * 获取用户token信息
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public IbspUserToken findAppTokenByToken(String token) throws Exception {
		List<Object> parameterList = new ArrayList<>();
		String sql = "SELECT * FROM ibsp_user_token WHERE VALUE_ =?";
		parameterList.add(token.trim());
		return (IbspUserToken) this.dao.findObject(IbspUserToken.class, sql, parameterList);
	}
}
