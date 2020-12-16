package com.ibm.follow.servlet.cloud.ibm_exp_user.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_user.entity.IbmExpUser;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IBM_用户 服务类
 *
 * @author Robot
 */
public class IbmExpUserService extends BaseServiceProxy {

	/**
	 * 保存IBM_用户 对象数据
	 *
	 * @param entity IbmExpUser对象数据
	 */
	public String save(IbmExpUser entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_exp_user 的 IBM_EXP_USER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_exp_user set state_='DEL' where IBM_EXP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_EXP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_exp_user 的 IBM_EXP_USER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_exp_user set state_='DEL' where IBM_EXP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_exp_user  的 IBM_EXP_USER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_exp_user where IBM_EXP_USER_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_EXP_USER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_exp_user 的 IBM_EXP_USER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_exp_user where IBM_EXP_USER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmExpUser实体信息
	 *
	 * @param entity IBM_用户 实体
	 */
	public void update(IbmExpUser entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_exp_user表主键查找 IBM_用户 实体
	 *
	 * @param id ibm_exp_user 主键
	 * @return IBM_用户 实体
	 */
	public IbmExpUser find(String id) throws Exception {
		return this.dao.find(IbmExpUser.class, id);

	}


	/**
	 * 获取在线数量
	 *
	 * @param userIdList 用户ID集合
	 * @return map
	 */
	public Map<String, Map<String, Object>> findUsersHandicapInfo(List<String> userIdList) throws SQLException {
		StringBuilder sql = new StringBuilder("select APP_USER_ID_,AGENT_ONLINE_,MEMBER_ONLINE_,AVAILABLE_GAME_,AGENT_AVAILABLE_HANDICAP_,AGENT_AVAILABLE_HANDICAP_" +
				" from ibm_exp_user where STATE_ != ? and APP_USER_ID_ in( ");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbmStateEnum.DEL.name());
		for (String uid : userIdList) {
			sql.append("?,");
			parameterList.add(uid);
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");

		return findKeyMap(sql, parameterList, "APP_USER_ID_");
	}

	/**
	 * 根据用户id获取实体对象
	 *
	 * @param appUserId 用户id
	 * @return 实体
	 */
	public IbmExpUser findByUserId(String appUserId) throws Exception {
		String sql = "select * from ibm_exp_user where APP_USER_ID_=? and STATE_!=?";
		List<Object> parameters = new ArrayList<>(6);
		parameters.add(appUserId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findObject(IbmExpUser.class, sql, parameters);
	}

	/**
	 * 获取用户可用盘口最大数量
	 *
	 * @param appUserId 用户Id
	 * @return 用户信息
	 */
	public Map<String, Object> findAvailableHandicap(String appUserId) throws SQLException {
		String sql = "select AVAILABLE_GAME_,AGENT_ONLINE_MAX_,MEMBER_ONLINE_MAX_"
				+ " from ibm_exp_user where APP_USER_ID_=? and STATE_=? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 修改在线登录数
	 *
	 * @param appUserId    用户id
	 * @param customerType 客户类型
	 */
	public void updateLoginOnline(String appUserId, IbmTypeEnum customerType) throws SQLException {
		String sqlFormat = "update ibm_exp_user set %s where APP_USER_ID_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbmStateEnum.OPEN.name());
		switch (customerType) {
			case MEMBER:
				dao.execute(String.format(sqlFormat, "MEMBER_ONLINE_=MEMBER_ONLINE_+1"), parameters);
				break;
			case AGENT:
				dao.execute(String.format(sqlFormat, "AGENT_ONLINE_=AGENT_ONLINE_+1"), parameters);
				break;
			default:
		}
	}

	/**
	 * 修改在线登录数
	 *
	 * @param appUserId    用户id
	 * @param customerType 客户类型
	 */
	public void updateLogoutOnline(String appUserId, IbmTypeEnum customerType) throws SQLException {
		String sqlFormat = "update ibm_exp_user set %s where APP_USER_ID_=? and STATE_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(appUserId);
		parameters.add(IbmStateEnum.OPEN.name());
		switch (customerType) {
			case MEMBER:
				dao.execute(String.format(sqlFormat, "MEMBER_ONLINE_=MEMBER_ONLINE_-1"), parameters);
				break;
			case AGENT:
				dao.execute(String.format(sqlFormat, "AGENT_ONLINE_=AGENT_ONLINE_-1"), parameters);
				break;
			default:
		}
	}

	/**
	 * 获取包含某个游戏和盘口的所有用户主键
	 *
	 * @param gameCode         游戏编码
	 * @param handicapCode     盘口编码
	 * @param handicapCategory 盘口类别
	 * @return 用户主键列表
	 */
	public List<String> listUserId(String gameCode, String handicapCode, String handicapCategory) throws SQLException {
		String sql =
				"SELECT APP_USER_ID_ as key_ FROM `ibm_exp_user` where " + " AVAILABLE_GAME_ like ? and STATE_ != ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.DEL.name());
		if (IbmTypeEnum.MEMBER.equal(handicapCategory)) {
			sql += " and MEMBER_AVAILABLE_HANDICAP_ like ?";
		} else {
			sql += " and AGENT_AVAILABLE_HANDICAP_ like ?";
		}
		parameters.add(handicapCode);
		return super.findStringList(sql, parameters);
	}
}
