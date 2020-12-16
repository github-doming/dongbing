package com.ibs.plan.module.cloud.ibsp_exp_user.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_exp_role.entity.IbspExpRole;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_用户 服务类
 *
 * @author Robot
 */
public class IbspExpUserService extends BaseServiceProxy {

	/**
	 * 保存IBSP_用户 对象数据
	 *
	 * @param entity IbspExpUser对象数据
	 */
	public String save(IbspExpUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_exp_user 的 IBSP_EXP_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_exp_user set state_='DEL' where IBSP_EXP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_EXP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_exp_user 的 IBSP_EXP_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_exp_user set state_='DEL' where IBSP_EXP_USER_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_exp_user  的 IBSP_EXP_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_exp_user where IBSP_EXP_USER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_EXP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_exp_user 的 IBSP_EXP_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_exp_user where IBSP_EXP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspExpUser实体信息
	 *
	 * @param entity IBSP_用户 实体
	 */
	public void update(IbspExpUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_exp_user表主键查找 IBSP_用户 实体
	 *
	 * @param id ibsp_exp_user 主键
	 * @return IBSP_用户 实体
	 */
	public IbspExpUser find(String id) throws Exception {
		return dao.find(IbspExpUser.class, id);
	}

	/**
	 * 修改在线登录数
	 *
	 * @param appUserId 用户id
	 */
	public void updateLogoutOnline(String appUserId) throws SQLException {
		String sql = "update ibsp_exp_user set ONLINE_ = ONLINE_ - 1 where APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 获取用户拥有的游戏列表
	 *
	 * @param appUserId 用户id
	 * @return 游戏信息列表<br>
	 * GAME_NAME_	游戏名称<br>
	 * GAME_CODE_	游戏编码<br>
	 * ICON_	游戏图标<br>
	 * SN_	游戏顺序<br>
	 */
	public List<Map<String, Object>> listGameShowInfo(String appUserId) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT AVAILABLE_GAME_ FROM `ibsp_exp_user` where APP_USER_ID_ = ? and STATE_ = ?");
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		String availableGame = super.findString("AVAILABLE_GAME_", sql.toString(), parameters);
		if (StringTool.isEmpty(availableGame)) {
			return null;
		}
		parameters.clear();
		sql = new StringBuilder("SELECT IBSP_GAME_ID_ as GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,SN_ FROM ibsp_game WHERE GAME_CODE_ IN ( ");
		for (String gameCode : availableGame.split(",")) {
			sql.append("?,");
			parameters.add(gameCode);
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") AND state_ = ? ORDER BY SN_");
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMapList(sql.toString(), parameters);
	}

	/**
	 * 获取用户可用盘口最大数量
	 *
	 * @param appUserId 用户主键
	 * @return 盘口最大数量， 可用游戏
	 */
	public Map<String, Object> findAvailableHandicap(String appUserId) throws SQLException {
		String sql = "select AVAILABLE_GAME_,ONLINE_MAX_"
				+ " from ibsp_exp_user where APP_USER_ID_=? and STATE_=? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 是否存在游戏
	 *
	 * @param appUserId 用户主键
	 * @param gameCode  游戏编码
	 * @return 存在 是
	 */
	public boolean hasGame(String appUserId, String gameCode) throws SQLException {
		String sql = "SELECT AVAILABLE_GAME_ FROM ibsp_exp_user where APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		String availableGame = super.dao.findString("AVAILABLE_GAME_", sql, parameters);
		return StringTool.isContains(availableGame, gameCode);
	}

	/**
	 * 修改用户登录数
	 *
	 * @param appUserId
	 * @throws SQLException
	 */
	public void updateLoginOnline(String appUserId) throws SQLException {
		String sql = "update ibsp_exp_user set ONLINE_ = ONLINE_ + 1 where APP_USER_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql, parameters);
	}

	/**
	 * 获取可用方案
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public String findAvailablePlan(String appUserId) throws SQLException {
		String sql = "select AVAILABLE_PLAN_ from ibsp_exp_user where APP_USER_ID_=? and STATE_=? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findString("AVAILABLE_PLAN_", sql, parameters);
	}

	/**
	 * 获取在线数量
	 *
	 * @param userId 用户id
	 * @return
	 */
	public Map<String, Object> findUserHandicapInfo(String userId) throws SQLException {
		String sql = "select ONLINE_,AVAILABLE_GAME_,AVAILABLE_HANDICAP_ from ibsp_exp_user where APP_USER_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(userId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 获取在线数量
	 *
	 * @param userIdList 用户ID集合
	 * @return
	 */
	public Map<String, Map<String, Object>> findUsersHandicapInfo(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("select APP_USER_ID_,ONLINE_ MEMBER_ONLINE_,AVAILABLE_GAME_,AVAILABLE_HANDICAP_ from ibsp_exp_user where STATE_ != ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		for (String uid:userIdList){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");

		return findKeyMap(sql,parameterList,"APP_USER_ID_");
	}

	/**
	 * 获取到期时间戳
	 *
	 * @param userIdList 用户ID集合
	 * @return 到期时间戳
	 */
	public Map<String,Object> getUsersEndTime(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT  APP_USER_ID_ key_,END_TIME_LONG_ value_ FROM ibsp_sys_manage_time where STATE_ != ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		for (String uid:userIdList){
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");

		return findKVMap(sql,parameterList);
	}

	/**
	 * 根据用户id获取实体对象
	 *
	 * @param appUserId 用户id
	 * @return
	 */
	public IbspExpUser findByUserId(String appUserId) throws Exception {
		String sql = "select * from ibsp_exp_user where APP_USER_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findObject(IbspExpUser.class, sql, parameters);
	}

	/**
	 * 根据角色id获取id
	 * @param roleId 角色id
	 */
	public Map<String, Object> findByRoleId(String roleId) throws Exception {
		String sql = "select IBSP_EXP_USER_ID_ as key_,APP_USER_ID_ as value_ from ibsp_exp_user where EXP_ROLE_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(roleId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.findKVMap(sql,parameters);
	}

	/**
	 * 更新盘口用户信息
	 * @param expRole 角色
	 * @param expUserIds 盘口用户主键
	 */
	public void updateByIds(IbspExpRole expRole, List<String> expUserIds) throws SQLException {
		StringBuilder sql = new StringBuilder( "UPDATE `ibsp_exp_user` SET `AVAILABLE_GAME_`=?, `AVAILABLE_HANDICAP_`=?, `ONLINE_MAX_`=?, `UPDATE_USER_`=?, " +
				"`UPDATE_TIME_`=?, `UPDATE_TIME_LONG_`=?, `STATE_`=? WHERE `STATE_`!=? AND IBSP_EXP_USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(expRole.getGameCodes());
		parameters.add(expRole.getHandicapCodes());
		parameters.add(expRole.getHmOnlineMax());
		parameters.add(expRole.getUpdateUser());
		parameters.add(expRole.getUpdateTime());
		parameters.add(expRole.getUpdateTimeLong());
		parameters.add(expRole.getState());
		parameters.add(IbsStateEnum.DEL.name());
		for (String expUserId:expUserIds){
			sql.append("?,");
			parameters.add(expUserId);
		}
		sql.deleteCharAt(sql.length()-1).append(")");
		super.dao.execute(sql.toString(),parameters);
	}

	/**
	 * 获取包含某个游戏和盘口的所有用户主键
	 *
	 * @param gameCode         游戏编码
	 * @return 用户主键列表
	 */
	public List<String> listUserId(String gameCode) throws SQLException {
		String sql =
				"SELECT APP_USER_ID_ as key_ FROM `ibsp_exp_user` where AVAILABLE_GAME_ like ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.DEL.name());
		return super.findStringList(sql, parameters);
	}

	/**
	 * 根据角色Id查找信息
	 * @param roleId 角色ID
	 */
	public List<Map<String,Object>> findInfoByRoleId(String roleId) throws SQLException {
		String sql = "SELECT APP_USER_ID_,AVAILABLE_GAME_ FROM `ibsp_exp_user` WHERE EXP_ROLE_ID_=? AND STATE_!=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(roleId);
		parameters.add(IbsStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameters);
	}

	/**
	 * 修改相关用户的方案信息
	 * @param roleId			角色id
	 * @param planUsable		方案信息
	 */
	public void updateAvailablePlan(String roleId, String planUsable) throws SQLException {
		String sql="update ibsp_exp_user set AVAILABLE_PLAN_=?,UPDATE_TIME_=?,UPDATE_TIME_LONG_=? where EXP_ROLE_ID_=?"
				+ " and STATE_=?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(planUsable);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(roleId);
		parameters.add(IbsStateEnum.OPEN.name());
		super.execute(sql,parameters);
	}
}
