package com.ibm.follow.servlet.cloud.vr_user_follow_member.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.entity.VrUserFollowMember;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* VR_用户跟投会员 服务类
 * @author Robot
 */
public class VrUserFollowMemberService extends BaseServiceProxy {

	/**
	 * 保存VR_用户跟投会员 对象数据
	 * @param entity VrUserFollowMember对象数据
	 */
	public String save(VrUserFollowMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_user_follow_member 的 VR_USER_FOLLOW_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_user_follow_member set state_='DEL' where VR_USER_FOLLOW_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_USER_FOLLOW_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除 vr_user_follow_member 的 VR_USER_FOLLOW_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_user_follow_member set state_='DEL' where VR_USER_FOLLOW_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_user_follow_member  的 VR_USER_FOLLOW_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_user_follow_member where VR_USER_FOLLOW_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_USER_FOLLOW_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除vr_user_follow_member 的 VR_USER_FOLLOW_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_user_follow_member where VR_USER_FOLLOW_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrUserFollowMember实体信息
	 * @param entity VR_用户跟投会员 实体
	 */
	public void update(VrUserFollowMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_user_follow_member表主键查找 VR_用户跟投会员 实体
	 * @param id vr_user_follow_member 主键
	 * @return VR_用户跟投会员 实体
	 */
	public VrUserFollowMember find(String id) throws Exception {
		return dao.find(VrUserFollowMember.class,id);
	}

	/**
	 * 获取用户信息
	 * @param appUserId        用户id
	 * @param vrMemberId        虚拟会员id
	 */
	public VrUserFollowMember findUserInfo(String appUserId, String vrMemberId) throws Exception {
		String sql="select * from vr_user_follow_member where USER_ID_=? and VR_MEMBER_ID_=? and STATE_!=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(appUserId);
		parameters.add(vrMemberId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findObject(VrUserFollowMember.class,sql,parameters);
	}

	/**
	 * 获取用户历史跟投会员信息
	 * @param appUserId		用户id
	 * @return
	 */
	public List<Map<String, Object>> listVrMemberInfos(String appUserId) throws SQLException {
		String sql="select VR_MEMBER_ID_,VR_MEMBER_ACCOUNT_ from vr_user_follow_member where USER_ID_=?"
				+ " and STATE_!=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(appUserId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql,parameters);
	}
}
