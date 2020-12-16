package com.ibm.follow.servlet.vrc.vrc_fm_game_set.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.vrc.vrc_fm_game_set.entity.VrcFmGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* VRC_跟投会员游戏设置 服务类
 * @author Robot
 */
public class VrcFmGameSetService extends BaseServiceProxy {

	/**
	 * 保存VRC_跟投会员游戏设置 对象数据
	 * @param entity VrcFmGameSet对象数据
	 */
	public String save(VrcFmGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vrc_fm_game_set 的 VRC_FM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vrc_fm_game_set set state_='DEL' where VRC_FM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VRC_FM_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除 vrc_fm_game_set 的 VRC_FM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vrc_fm_game_set set state_='DEL' where VRC_FM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vrc_fm_game_set  的 VRC_FM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vrc_fm_game_set where VRC_FM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VRC_FM_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除vrc_fm_game_set 的 VRC_FM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vrc_fm_game_set where VRC_FM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrcFmGameSet实体信息
	 * @param entity VRC_跟投会员游戏设置 实体
	 */
	public void update(VrcFmGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vrc_fm_game_set表主键查找 VRC_跟投会员游戏设置 实体
	 * @param id vrc_fm_game_set 主键
	 * @return VRC_跟投会员游戏设置 实体
	 */
	public VrcFmGameSet find(String id) throws Exception {
		return dao.find(VrcFmGameSet.class,id);
	}

	/**
	 * 获取游戏设置信息
	 * @param existMemberVrIds	已存在虚拟会员ids
	 * @param gameCode			游戏编码
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> findInfos(Set<String> existMemberVrIds, GameUtil.Code gameCode) throws SQLException {
		StringBuilder sql= new StringBuilder("SELECT vfgs.EXIST_HM_VR_ID_,vfgs.USER_ID_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,"
				+ " BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,FILTER_PROJECT_ FROM vrc_fm_game_set vfgs"
				+ " LEFT JOIN vrc_member_bind_info vmbi ON vfgs.EXIST_HM_VR_ID_=vmbi.EXIST_HM_VR_ID_"
				+ " AND vfgs.USER_ID_=vmbi.USER_ID_ AND vmbi.STATE_=? WHERE GAME_CODE_ =?"
				+ " AND BET_STATE_ =? AND vfgs.STATE_ =? AND vfgs.EXIST_HM_VR_ID_ in(");
		List<Object> parameters=new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(gameCode.name());
		parameters.add(IbmTypeEnum.TRUE.name());
		parameters.add(IbmStateEnum.OPEN.name());
		for(String existMemberVrId:existMemberVrIds){
			sql.append("?,");
			parameters.add(existMemberVrId);
		}
		sql.replace(sql.length()-1,sql.length(),")");
		return super.findKeyMaps(sql.toString(),parameters,"EXIST_HM_VR_ID_");
	}

	/**
	 * 获取游戏设置信息
	 * @param existMemberVrId		已存在虚拟会员id
	 * @param userId				用户id
	 * @param gameCode			游戏编码
	 * @return
	 */
	public VrcFmGameSet findInfo(String existMemberVrId, String userId, String gameCode) throws Exception {
		String sql="select * from vrc_fm_game_set where EXIST_HM_VR_ID_=? and USER_ID_=? and GAME_CODE_=? and STATE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(existMemberVrId);
		parameters.add(userId);
		parameters.add(gameCode);
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findObject(VrcFmGameSet.class,sql,parameters);
	}
}
