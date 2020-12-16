package com.ibm.follow.servlet.cloud.vr_member.service;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * VR_盘口会员 服务类
 *
 * @author Robot
 */
public class VrMemberService extends BaseServiceProxy {

	/**
	 * 保存VR_盘口会员 对象数据
	 *
	 * @param entity VrMember对象数据
	 */
	public String save(VrMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_member 的 VR_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_member set state_='DEL' where VR_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_member 的 VR_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_member set state_='DEL' where VR_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_member  的 VR_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_member where VR_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_MEMBER_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_member 的 VR_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_member where VR_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrMember实体信息
	 *
	 * @param entity VR_盘口会员 实体
	 */
	public void update(VrMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_member表主键查找 VR_盘口会员 实体
	 *
	 * @param id vr_member 主键
	 * @return VR_盘口会员 实体
	 */
	public VrMember find(String id) throws Exception {
		return dao.find(VrMember.class, id);
	}

	/**
	 * 获取用户列表
	 *
	 * @param userName     用户名
	 * @param memberName   会员名
	 * @param handicapCode 盘口编码
	 * @param pageIndex    起始页
	 * @param pageSize     页大小
	 * @return 会员列表
	 */
	public PageCoreBean<Map<String, Object>> listVrMember(String userName, String memberName, String handicapCode, Integer pageIndex, Integer pageSize) throws SQLException {
		String sqlCount = "SELECT count(*) from (";
		String sql = "SELECT VR_MEMBER_ID_ MEMBER_ID_,HANDICAP_CODE_,VR_MEMBER_ACCOUNT_,VR_USER_NAME_,'无' as gameName  " +
				" FROM `vr_member` WHERE STATE_ !=? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(IbmStateEnum.DEL.name());
		if (StringTool.notEmpty(userName)) {
			sql += " AND VR_USER_NAME_ like ? ";
			userName = "%" + userName + "%";
			parameterList.add(userName);
		}
		if (StringTool.notEmpty(memberName)) {
			sql += " AND VR_MEMBER_ACCOUNT_ like ? ";
			memberName = "%" + memberName + "%";
			parameterList.add(memberName);
		}
		if (StringTool.notEmpty(handicapCode)) {
			sql += " AND HANDICAP_CODE_ = ? ";
			parameterList.add(handicapCode);
		}
		sqlCount = sqlCount + sql + ") AS t  ";
		sql+=" ORDER BY HANDICAP_CODE_ ";
		return dao.page(sql, parameterList, pageIndex, pageSize, sqlCount, parameterList);
	}

	/**
	 * 查询盘口用户是否存在
	 *
	 * @param handicapCode 盘口编码
	 * @param memberName   会员名
	 * @return 是否
	 */
	public boolean isExist(String handicapCode, String memberName, String userName) throws SQLException {
		String sql = "SELECT VR_MEMBER_ID_ key_ FROM `vr_member` WHERE HANDICAP_CODE_ = ? AND (VR_MEMBER_ACCOUNT_ = ? OR VR_USER_NAME_=? )";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(handicapCode);
		parameterList.add(memberName);
		parameterList.add(userName);
		return StringTool.notEmpty(super.findString(sql, parameterList));
	}

	/**
	 * 获取会员信息
	 *
	 * @param vrMemberIds 虚拟会员ids
	 * @return
	 */
	public Map<String, Map<String, Object>> findMemberInfo(Set<String> vrMemberIds) throws SQLException {
		StringBuilder sql = new StringBuilder("select VR_MEMBER_ID_,HANDICAP_CODE_,VR_USER_NAME_,VR_MEMBER_ACCOUNT_ from vr_member where STATE_=?"
				+ " and VR_MEMBER_ID_ in(");
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		for (String vrMemberId : vrMemberIds) {
			sql.append("?,");
			parameters.add(vrMemberId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKeyMap(sql.toString(), parameters, "VR_MEMBER_ID_");
	}

	/**
	 * 查询所有虚拟会员信息
	 * @return id列表
	 */
	public List<String> listAllMemberIds() throws SQLException {
		String sql = "SELECT VR_MEMBER_ID_ key_ FROM `vr_member` where STATE_!=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql,parameters);
	}

	/**
	 * 删除虚拟会员相关数据
	 * @param memberId 会员Id
	 */
	public void delMemberInfo(String memberId) throws SQLException {
		String sql = " DELETE FROM %s WHERE VR_MEMBER_ID_=? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(memberId);
		List<String> tableNames = Arrays.asList("vr_member","vr_plan_hm","vr_member_game_set",
				"vr_pi_member_plan_item","vr_client_member");
		for (String tableName:tableNames){
			super.execute(String.format(sql,tableName),parameters);
		}

	}
}
