package com.ibm.follow.servlet.cloud.vr_client_member.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_client_member.entity.VrClientMember;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* VR_客户端虚拟会员 服务类
 * @author Robot
 */
public class VrClientMemberService extends BaseServiceProxy {

	/**
	 * 保存VR_客户端虚拟会员 对象数据
	 * @param entity VrClientMember对象数据
	 */
	public String save(VrClientMember entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_client_member 的 VR_CLIENT_MEMBER_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_client_member set state_='DEL' where VR_CLIENT_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_CLIENT_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除 vr_client_member 的 VR_CLIENT_MEMBER_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_client_member set state_='DEL' where VR_CLIENT_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_client_member  的 VR_CLIENT_MEMBER_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_client_member where VR_CLIENT_MEMBER_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_CLIENT_MEMBER_ID_主键id数组的数据
	 * @param idArray 要删除vr_client_member 的 VR_CLIENT_MEMBER_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_client_member where VR_CLIENT_MEMBER_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrClientMember实体信息
	 * @param entity VR_客户端虚拟会员 实体
	 */
	public void update(VrClientMember entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_client_member表主键查找 VR_客户端虚拟会员 实体
	 * @param id vr_client_member 主键
	 * @return VR_客户端虚拟会员 实体
	 */
	public VrClientMember find(String id) throws Exception {
		return dao.find(VrClientMember.class,id);
	}

	/**
	 * 获取虚拟会员账号名称
	 * @param existMemberVrId		已存在虚拟会员id
	 * @return
	 */
	public Map<String,Object> findMemberInfo(String existMemberVrId) throws SQLException {
		String sql="select vm.VR_MEMBER_ACCOUNT_,vm.HANDICAP_CODE_,vm.VR_MEMBER_ID_ from vr_client_member vcm left join vr_member vm on vcm.VR_MEMBER_ID_=vm.VR_MEMBER_ID_"
				+ " where vcm.EXIST_MEMBER_VR_ID_=? and vcm.STATE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existMemberVrId);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql,parameters);
	}

	/**
	 * 获取虚拟会员id
	 * @param existMemberVrId		已存在虚拟会员id
	 * @return
	 */
	public Map<String,Object> findMemberId(String existMemberVrId) throws SQLException {
		String sql = "select vcm.VR_MEMBER_ID_,vm.VR_MEMBER_ACCOUNT_ from vr_client_member vcm"
				+ " LEFT JOIN vr_member vm ON vcm.VR_MEMBER_ID_=vm.VR_MEMBER_ID_ where "
				+ " vcm.EXIST_MEMBER_VR_ID_ = ? and vcm.STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(existMemberVrId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap( sql, parameterList);
	}

	/**
	 * 获取虚拟会员客户端信息
	 * @param vrMemberId		虚拟会员id
	 * @return
	 */
	public Map<String, Object> findInfo(String vrMemberId) throws SQLException {
		String sql="select EXIST_MEMBER_VR_ID_,CLIENT_CODE_ from vr_client_member where VR_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(vrMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap( sql, parameterList);
	}

}
