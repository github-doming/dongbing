package com.ibm.follow.servlet.vrc.vrc_member_bind_info.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.vrc.vrc_member_bind_info.entity.VrcMemberBindInfo;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* VRC客户端_会员绑定信息 服务类
 * @author Robot
 */
public class VrcMemberBindInfoService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_会员绑定信息 对象数据
	 * @param entity VrcMemberBindInfo对象数据
	 */
	public String save(VrcMemberBindInfo entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_member_bind_info 的 VRC_MEMBER_BIND_INFO_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_member_bind_info set state_='DEL' where VRC_MEMBER_BIND_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_MEMBER_BIND_INFO_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_member_bind_info 的 VRC_MEMBER_BIND_INFO_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_member_bind_info set state_='DEL' where VRC_MEMBER_BIND_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_member_bind_info  的 VRC_MEMBER_BIND_INFO_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_member_bind_info where VRC_MEMBER_BIND_INFO_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_MEMBER_BIND_INFO_ID_主键id数组的数据
	 * @param idArray 要删除vrc_member_bind_info 的 VRC_MEMBER_BIND_INFO_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_member_bind_info where VRC_MEMBER_BIND_INFO_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcMemberBindInfo实体信息
	 * @param entity VRC客户端_会员绑定信息 实体
	 */
	public void update(VrcMemberBindInfo entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_member_bind_info表主键查找 VRC客户端_会员绑定信息 实体
	 * @param id vrc_member_bind_info 主键
	 * @return VRC客户端_会员绑定信息 实体
	 */
	public VrcMemberBindInfo find(String id) throws Exception {
		return dao.find(VrcMemberBindInfo.class,id);
	}


	/**
	 * 会员绑定信息
	 * @param existMemberVrId		已存在虚拟会员id
	 * @param userId				用户id
	 * @return
	 */
	public String findId(String existMemberVrId, String userId) throws SQLException {
		String sql = "SELECT VRC_MEMBER_BIND_INFO_ID_ FROM `vrc_member_bind_info` where "
				+ " EXIST_HM_VR_ID_ = ? and USER_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(existMemberVrId);
		parameterList.add(userId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("VRC_MEMBER_BIND_INFO_ID_", sql, parameterList);
	}
}
