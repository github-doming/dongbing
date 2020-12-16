package com.ibm.follow.servlet.cloud.vr_member_game_set.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.vr_member_game_set.entity.VrMemberGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * VR_会员游戏设置 服务类
 *
 * @author Robot
 */
public class VrMemberGameSetService extends BaseServiceProxy {

	/**
	 * 保存VR_会员游戏设置 对象数据
	 *
	 * @param entity VrMemberGameSet对象数据
	 */
	public String save(VrMemberGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_member_game_set 的 VR_MEMBER_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_member_game_set set state_='DEL' where VR_MEMBER_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_MEMBER_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_member_game_set 的 VR_MEMBER_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_member_game_set set state_='DEL' where VR_MEMBER_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_member_game_set  的 VR_MEMBER_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_member_game_set where VR_MEMBER_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_MEMBER_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_member_game_set 的 VR_MEMBER_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_member_game_set where VR_MEMBER_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrMemberGameSet实体信息
	 *
	 * @param entity VR_会员游戏设置 实体
	 */
	public void update(VrMemberGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_member_game_set表主键查找 VR_会员游戏设置 实体
	 *
	 * @param id vr_member_game_set 主键
	 * @return VR_会员游戏设置 实体
	 */
	public VrMemberGameSet find(String id) throws Exception {
		return dao.find(VrMemberGameSet.class, id);
	}


	/**
	 * 初始化会员游戏设置信息
	 *
	 * @param gameCodes 游戏编码集合
	 * @param memberId  会员Id
	 * @param nowTime   时间
	 */
	public void save(List<String> gameCodes, String memberId, Date nowTime) throws SQLException {
		String sql = "INSERT INTO `vr_member_game_set` (`VR_MEMBER_ID_`, `BET_STATE_`,`CREATE_TIME_`, `CREATE_TIME_LONG_`, " +
				" `UPDATE_TIME_`, `UPDATE_TIME_LONG_`, `STATE_`,`VR_MEMBER_GAME_SET_ID_`, `GAME_CODE_`) " +
				"VALUES (?,?,?,?,?,?,?,?,?)";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(memberId);
		parameterList.add(IbmTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(IbmStateEnum.OPEN.name());
		for (String gameCode : gameCodes) {
			parameterList.add(RandomTool.getNumLetter32());
			parameterList.add(gameCode);
			super.dao.execute(sql, parameterList);
			parameterList.remove(parameterList.size() - 1);
			parameterList.remove(parameterList.size() - 1);
		}
	}

	/**
	 * 查询会员游戏信息
	 *
	 * @param memberId 虚拟会员ID
	 * @return 游戏信息
	 */
	public List<Map<String, Object>> findMemberGameInfo(String memberId) throws SQLException {
		String sql = "SELECT vg.VR_MEMBER_GAME_SET_ID_,vg.GAME_CODE_,vg.BET_STATE_ FROM `vr_member_game_set` vg" +
				"  LEFT JOIN ibm_game ig ON vg.GAME_CODE_ = ig.GAME_CODE_ " +
				" WHERE vg.VR_MEMBER_ID_ = ? AND vg.STATE_!=? ORDER BY ig.SN_ ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(memberId);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取游戏信息
	 *
	 * @param vrMemberId 虚拟会员id
	 * @return 游戏信息
	 */
	public List<Map<String, Object>> listGameInfo(String vrMemberId, String handicapCode) throws SQLException {
		String sql = "SELECT ihg.GAME_NAME_,ihg.GAME_CODE_ FROM vr_member_game_set vmgs"
				+ " LEFT JOIN ibm_handicap_game ihg ON vmgs.GAME_CODE_=ihg.GAME_CODE_ "
				+" LEFT JOIN ibm_handicap ih on ihg.HANDICAP_ID_ = ih.IBM_HANDICAP_ID_ "

				+ " WHERE vmgs.VR_MEMBER_ID_ = ? and ihg.HANDICAP_CODE_=? AND ih.HANDICAP_CATEGORY_=? "
				+ " AND ihg.STATE_ =? AND vmgs.STATE_ = ? ORDER BY ihg.SN_";
		List<Object> parameters = new ArrayList<>(4);
		parameters.add(vrMemberId);
		parameters.add(handicapCode);
		parameters.add(IbmTypeEnum.MEMBER.name());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 查询会员
	 *
	 * @param memberId 虚拟会员Id
	 * @return 游戏CODE字符串
	 */
	public String getMemberBetGame(String memberId) throws SQLException {
		String sql = "SELECT GROUP_CONCAT(GAME_CODE_) key_ FROM `vr_member_game_set` where VR_MEMBER_ID_=? AND state_ !=? AND BET_STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(memberId);
		parameters.add(IbmStateEnum.DEL.name());
		parameters.add(IbmTypeEnum.TRUE.name());
		return super.findString(sql, parameters);
	}

	/**
	 * 查询会员游戏CODE
	 *
	 * @param memberId 虚拟会员Id
	 * @return 游戏CODE集合
	 */
	public List<String> getGameCode(String memberId) throws SQLException {
		String sql = "SELECT GAME_CODE_ key_ FROM `vr_member_game_set` where VR_MEMBER_ID_=? AND state_ !=? ";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(memberId);
		parameters.add(IbmStateEnum.DEL.name());
		return super.findStringList(sql, parameters);
	}
}
