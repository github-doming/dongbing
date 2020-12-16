package com.ibm.follow.connector.admin.manage3.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_user.entity.IbmHaUser;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.entity.IbmHmUser;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-09-10 11:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmSysHandicapUserService extends IbmHandicapService {
	/**
	 * 根据盘口ID删除代理表用户1
	 */
	public void delHaUser(String handicapId) throws SQLException {
		String sql = "DELETE FROM ibm_ha_user where HANDICAP_ID_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(handicapId);
		dao.execute(sql, parameters);
	}
	/**
	 * 根据盘口ID添加代理表用户2
	 */
	public void saveHaUser(IbmHaUser ibmHaUser) throws Exception {
		dao.save(ibmHaUser);
	}

	/**
	 * 根据盘口ID删除会员表用户3
	 */
	public void delHmUser(String handicapId) throws SQLException {
		String sql = "DELETE FROM ibm_hm_user where HANDICAP_ID_ = ?";
		List<Object> parameters = new ArrayList<>(1);
		parameters.add(handicapId);
		dao.execute(sql, parameters);
	}
	/**
	 * 根据盘口ID添加会员表用户4
	 */
	public void saveHmUser(IbmHmUser ibmHmUser) throws Exception {
		dao.save(ibmHmUser);
	}

	/**
	 * 根据盘口ID 查询用户表
	 *
	 * @return 用户表
	 */
	public void findByUser(String handicapId) throws SQLException {
		String sql = "update ibm_ha_user set state_='DEL' where HANDICAP_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapId);
		dao.execute(sql, parameters);
	}

	/**
	 * 根据盘口ID 查询代理表用户
	 *
	 * @return 代理表用户
	 */
	public List<Map<String, Object>> findAgentUserByHandicapID(String handicapId) throws SQLException {
		String sql = "select APP_USER_ID_ from ibm_ha_user where HANDICAP_ID_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapId);
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 根据盘口ID 查询会员表用户
	 *
	 * @return 会员表用户
	 */
	public List<Map<String, Object>> findMemberUserByHandicapID(String handicapId) throws SQLException {
		String sql = "select APP_USER_ID_ from ibm_hm_user where HANDICAP_ID_ = ?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(handicapId);
		return dao.findMapList(sql, parameters);
	}

	/**
	 * 根据盘口ID 查询用户信息
	 *
	 * @return 用户信息
	 */
	public List<Map<String, Object>> findUserByUserID(String data) throws SQLException {
		String sql = "SELECT APP_USER_TYPE_ FROM app_user WHERE FIND_IN_SET(APP_USER_ID_, ? )";
		List<Object> parameters = new ArrayList<>();
		parameters.add(data);
		return dao.findMapList(sql, parameters);
	}
	/**
	 * 根据盘口ID 查询用户信息
	 *
	 * @return 用户信息
	 */
	public List<String> findUserIdByType(List<IbmTypeEnum> types) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT APP_USER_ID_ FROM app_user where APP_USER_TYPE_ in (");
		List<Object> parameters = new ArrayList<>();
		if (ContainerTool.notEmpty(types)){
			for (IbmTypeEnum type : types) {
				sql.append("?,");
				parameters.add(type.name());
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		return dao.findStringList("APP_USER_ID_", sql.toString(), parameters);
	}

	/**
	 * 删除盘口代理
	 * @param userIds    盘口会员信息
	 * @param handicapId 盘口id
	 * @return 盘口代理用户主键
	 */
	public List<String> delHaUser(List<String> userIds, String handicapId) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBM_HA_USER_ID_ FROM `ibm_ha_user` where ")
				.append("HANDICAP_ID_ = ? and STATE_ != ?  and APP_USER_ID_ in (");
		List<Object> parameters = new ArrayList<>(2 + userIds.size());
		parameters.add(handicapId);
		parameters.add(IbmStateEnum.DEL.name());
		if (ContainerTool.notEmpty(userIds)){
			for (String userId : userIds) {
				sql.append("?,");
				parameters.add(userId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		//待删除的盘口代理用户主键
		List<String> haUserIds = super.dao.findStringList("IBM_HA_USER_ID_", sql.toString(), parameters);
		if (ContainerTool.isEmpty(haUserIds)) {
			return haUserIds;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		sql.append("UPDATE `ibm_ha_user` SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ?")
				.append("WHERE IBM_HA_USER_ID_ IN (");
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口用户");
		if (ContainerTool.notEmpty(haUserIds)){
			for (String haUserId : haUserIds) {
				sql.append("?,");
				parameters.add(haUserId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}
		super.dao.execute(sql.toString(), parameters);
		return haUserIds;
	}
	/**
	 * 删除盘口代理
	 * @param haUserIds 盘口代理主键列表
	 * @return
	 */
	public List<String> delHandicapAgent(List<String> haUserIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBM_HANDICAP_AGENT_ID_ FROM `ibm_handicap_agent` WHERE  STATE_ != ? and HA_USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(1 + haUserIds.size());
		parameters.add(IbmStateEnum.DEL.name());
		if (ContainerTool.notEmpty(haUserIds)){
			for (String haUserId : haUserIds) {
				sql.append("?,");
				parameters.add(haUserId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}
		List<String> handicapAgentIds = super.dao.findStringList("IBM_HA_USER_ID_", sql.toString(), parameters);

		sql.delete(0, sql.length());
		parameters.clear();
		sql.append("UPDATE `ibm_handicap_agent` SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ?")
				.append("WHERE IBM_HANDICAP_AGENT_ID_ IN (");
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口代理");
		if (ContainerTool.notEmpty(handicapAgentIds)){
			for (String handicapAgentId : handicapAgentIds) {
				sql.append("?,");
				parameters.add(handicapAgentId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}
		super.dao.execute(sql.toString(), parameters);
		return handicapAgentIds;
	}
	/**
	 * 清理盘口代理信息
	 * @param handicapAgentIds 盘口代理主键列表
	 */
	public void delHandicapAgentInfo(List<String> handicapAgentIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? ")
				.append("WHERE STATE_ != ? and HANDICAP_AGENT_ID_ IN (");
		List<Object> parameters = new ArrayList<>(5 + handicapAgentIds.size());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口代理");
		parameters.add(IbmStateEnum.DEL.name());
		if (ContainerTool.notEmpty(handicapAgentIds)){
			for (String handicapAgentId : handicapAgentIds) {
				sql.append("?,");
				parameters.add(handicapAgentId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}
		//清理盘口代理信息
		super.dao.execute(String.format(sql.toString(), "ibm_ha_game_set"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_ha_info"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_ha_member_bet_item"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_ha_notice"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_ha_set"), parameters);

		System.out.println("<><><><><>删除代理盘口所有信息<><><<><><>");
	}



	/**
	 * 删除盘口会员
	 * @param userIds    盘口会员信息
	 * @param handicapId 盘口id
	 * @return 盘口会员用户主键
	 */
	public List<String> delHmUser(List<String> userIds, String handicapId) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBM_HM_USER_ID_ FROM `ibm_hm_user` where ")
				.append("HANDICAP_ID_ = ? and STATE_ != ?  and APP_USER_ID_ in (");
		List<Object> parameters = new ArrayList<>(2 + userIds.size());
		parameters.add(handicapId);
		parameters.add(IbmStateEnum.DEL.name());
		if (ContainerTool.notEmpty(userIds)){
			for (String userId : userIds) {
				sql.append("?,");
				parameters.add(userId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		//待删除的盘口代理用户主键
		List<String> hmUserIds = super.dao.findStringList("IBM_HM_USER_ID_", sql.toString(), parameters);
		if (ContainerTool.isEmpty(hmUserIds)) {
			return hmUserIds;
		}
		sql.delete(0, sql.length());
		parameters.clear();
		sql.append("UPDATE `ibm_hm_user` SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? ")
				.append("WHERE IBM_HM_USER_ID_ IN (");
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口用户");
		if (ContainerTool.notEmpty(hmUserIds)){
			for (String hmUserId : hmUserIds) {
				sql.append("?,");
				parameters.add(hmUserId);
			}
		}else {
			sql.append(" '')");
		}
		sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		super.dao.execute(sql.toString(), parameters);
		return hmUserIds;
	}
	/**
	 * 删除盘口会员
	 * @param hmUserIds 盘口会员主键列表
	 * @return
	 */
	public List<String> delHandicapMember(List<String> hmUserIds) throws SQLException {
		StringBuilder sql = new StringBuilder(
				"SELECT IBM_HANDICAP_MEMBER_ID_ FROM `ibm_handicap_member` WHERE  STATE_ != ? and HM_USER_ID_ in(");
		List<Object> parameters = new ArrayList<>(1 + hmUserIds.size());
		parameters.add(IbmStateEnum.DEL.name());
		if (ContainerTool.notEmpty(hmUserIds)){
			for (String hmUserId : hmUserIds) {
				sql.append("?,");
				parameters.add(hmUserId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		List<String> handicapMemberIds = super.dao.findStringList("IBM_HM_USER_ID_", sql.toString(), parameters);
		sql.delete(0, sql.length());
		parameters.clear();
		sql.append("UPDATE ibm_handicap_member SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ? ")
				.append("WHERE IBM_HANDICAP_MEMBER_ID_ IN (");
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口代理");
		if (ContainerTool.notEmpty(handicapMemberIds)){
			for (String handicapMemberId : handicapMemberIds) {
				sql.append("?,");
				parameters.add(handicapMemberId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		super.dao.execute(sql.toString(), parameters);
		return handicapMemberIds;
	}
	/**
	 * 清理盘口会员信息
	 * @param handicapMemberIds 盘口会员主键列表
	 */
	public void delHandicapMemberInfo(List<String> handicapMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE %s SET STATE_ = ?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?, DESC_ = ?")
				.append("WHERE STATE_ != ? and HANDICAP_MEMBER_ID_ IN (");
		List<Object> parameters = new ArrayList<>(5 + handicapMemberIds.size());
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改盘口类型删除盘口会员");
		parameters.add(IbmStateEnum.DEL.name());

		if (ContainerTool.notEmpty(handicapMemberIds)){
			for (String handicapMemberId : handicapMemberIds) {
				sql.append("?,");
				parameters.add(handicapMemberId);
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(")");
		}else {
			sql.append(" '')");
		}

		//清理盘口会员信息
		super.dao.execute(String.format(sql.toString(), "ibm_hm_bet_item"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_game_set"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_info"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_notice"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_profit_item"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_profit"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_profit_period"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_profit_period_vr"), parameters);
		super.dao.execute(String.format(sql.toString(), "ibm_hm_set"), parameters);

		System.out.println("<><><><><>删除会员盘口所有信息<><><<><><>");
	}
}
