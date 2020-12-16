package com.ibm.follow.servlet.vrc.vrc_exist_member.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.vrc.vrc_exist_member.entity.VrcExistMember;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
* VRC客户端_已存在虚拟会员 服务类
 * @author Robot
 */
public class VrcExistMemberService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_已存在虚拟会员 对象数据
	 * @param entity VrcExistMember对象数据
	 */
	public String save(VrcExistMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_exist_member 的 VRC_EXIST_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_exist_member set state_='DEL' where VRC_EXIST_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_EXIST_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_exist_member 的 VRC_EXIST_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_exist_member set state_='DEL' where VRC_EXIST_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_exist_member  的 VRC_EXIST_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_exist_member where VRC_EXIST_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_EXIST_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除vrc_exist_member 的 VRC_EXIST_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_exist_member where VRC_EXIST_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcExistMember实体信息
	 * @param entity VRC客户端_已存在虚拟会员 实体
	 */
	public void update(VrcExistMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_exist_member表主键查找 VRC客户端_已存在虚拟会员 实体
	 * @param id vrc_exist_member 主键
	 * @return VRC客户端_已存在虚拟会员 实体
	 */
	public VrcExistMember find(String id) throws Exception {
		return dao.find(VrcExistMember.class,id);
	}

	/**
	 * 获取盘口信息
	 * @param existMemberVrIds	已存在虚拟会员ids
	 * @return
	 */
	public Map<String, Object> findHandicapInfo(Set<String> existMemberVrIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select VRC_EXIST_MEMBER_ID_ as key_,HANDICAP_CODE_ as value_ from vrc_exist_member where");
		sql.append(" STATE_=? and VRC_EXIST_MEMBER_ID_ in(");
		List<Object> parameterList = new ArrayList<>(existMemberVrIds.size()+1);
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String existHmId : existMemberVrIds) {
			sql.append("?,");
			parameterList.add(existHmId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKVMap(sql.toString(),parameterList);
	}

	/**
	 * 获取虚拟会员信息
	 * @return
	 */
	public Map<String, Object> findExitsInfo() throws SQLException {
		String sql = "SELECT VR_MEMBER_ID_ as key_,VRC_EXIST_MEMBER_ID_ as value_ FROM vrc_exist_member where state_ = ? ";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 删除虚拟会员相关数据
	 * @param existMemberVrId 存在会员Id
	 */
	public void delMemberInfo(String existMemberVrId) throws SQLException {
		String sql = " DELETE  FROM %s WHERE %s =? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(existMemberVrId);
		//
		List<String> tableNames = Arrays.asList("vrc_bet","vrc_member_coding_item",
				"vrc_member_game_set","vrc_member_plan","vrc_plan_group_result","vrc_plan_item");
		for (String tableName:tableNames){
			super.execute(String.format(sql,tableName ,"EXIST_MEMBER_VR_ID_"),parameters);
		}
		super.execute(String.format(sql,"vrc_member_bind_info" ,"EXIST_HM_VR_ID_"),parameters);
		super.execute(String.format(sql,"vrc_exist_member" ,"VRC_EXIST_MEMBER_ID_"),parameters);


	}
}
