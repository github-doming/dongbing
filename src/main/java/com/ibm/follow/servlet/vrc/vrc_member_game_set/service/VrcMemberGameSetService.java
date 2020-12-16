package com.ibm.follow.servlet.vrc.vrc_member_game_set.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.vrc.vrc_member_game_set.entity.VrcMemberGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.util.ArrayList;
import java.util.List;

/**
* VRC客户端_会员游戏设置 服务类
 * @author Robot
 */
public class VrcMemberGameSetService extends BaseServiceProxy {

	/**
	 * 保存VRC客户端_会员游戏设置 对象数据
	 * @param entity VrcMemberGameSet对象数据
	 */
	public String save(VrcMemberGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_member_game_set 的 VRC_MEMBER_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_member_game_set set state_='DEL' where VRC_MEMBER_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_MEMBER_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_member_game_set 的 VRC_MEMBER_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_member_game_set set state_='DEL' where VRC_MEMBER_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_member_game_set  的 VRC_MEMBER_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_member_game_set where VRC_MEMBER_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_MEMBER_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除vrc_member_game_set 的 VRC_MEMBER_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_member_game_set where VRC_MEMBER_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcMemberGameSet实体信息
	 * @param entity VRC客户端_会员游戏设置 实体
	 */
	public void update(VrcMemberGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_member_game_set表主键查找 VRC客户端_会员游戏设置 实体
	 * @param id vrc_member_game_set 主键
	 * @return VRC客户端_会员游戏设置 实体
	 */
	public VrcMemberGameSet find(String id) throws Exception {
		return dao.find(VrcMemberGameSet.class,id);
	}

	public VrcMemberGameSet find(String existMemberVrId,String gameCode) throws Exception {
		String sql = "SELECT * FROM `vrc_member_game_set`WHERE EXIST_MEMBER_VR_ID_ = ? and GAME_CODE_ = ? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(existMemberVrId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return findObject(VrcMemberGameSet.class,sql,parameterList);
	}
}
