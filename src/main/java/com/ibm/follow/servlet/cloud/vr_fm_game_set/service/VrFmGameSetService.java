package com.ibm.follow.servlet.cloud.vr_fm_game_set.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.entity.VrFmGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_跟投会员游戏设置 服务类
 * @author Robot
 */
public class VrFmGameSetService extends BaseServiceProxy {

	/**
	 * 保存VR_跟投会员游戏设置 对象数据
	 * @param entity VrFmGameSet对象数据
	 */
	public String save(VrFmGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_fm_game_set 的 VR_FM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_fm_game_set set state_='DEL' where VR_FM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_FM_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除 vr_fm_game_set 的 VR_FM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_fm_game_set set state_='DEL' where VR_FM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_fm_game_set  的 VR_FM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_fm_game_set where VR_FM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_FM_GAME_SET_ID_主键id数组的数据
	 * @param idArray 要删除vr_fm_game_set 的 VR_FM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_fm_game_set where VR_FM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrFmGameSet实体信息
	 * @param entity VR_跟投会员游戏设置 实体
	 */
	public void update(VrFmGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_fm_game_set表主键查找 VR_跟投会员游戏设置 实体
	 * @param id vr_fm_game_set 主键
	 * @return VR_跟投会员游戏设置 实体
	 */
	public VrFmGameSet find(String id) throws Exception {
		return dao.find(VrFmGameSet.class,id);
	}

	/**
	 * 获取游戏信息
	 * @param appUserId	用户id
	 * @param vrMemberId	虚拟会员id
	 * @return
	 */
	public List<Map<String, Object>> findGameInfo(String appUserId, String vrMemberId) throws SQLException {
		String sql = "select GAME_CODE_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_,"
				+ "BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,"
				+ "FILTER_PROJECT_,EXTENSION_SET_ from vr_fm_game_set where USER_ID_=? and VR_MEMBER_ID_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(vrMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}
	/**
	 * 获取游戏信息
	 * @param appUserId	用户id
	 * @param vrMemberId	虚拟会员id
	 * @return
	 */
	public boolean findGameInfo(String appUserId, Object vrMemberId, IbmTypeEnum betState) throws SQLException {
		String sql = "select GAME_CODE_ from vr_fm_game_set where USER_ID_=? and VR_MEMBER_ID_=? and BET_STATE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(vrMemberId);
		parameterList.add(betState.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return ContainerTool.isEmpty(super.dao.findMapList(sql, parameterList));
	}

	/**
	 * 获取游戏信息
	 * @param appUserId		用户id
	 * @param vrMemberId		虚拟会员id
	 * @return
	 */
	public List<Map<String, Object>> listShow(String appUserId, String vrMemberId) throws SQLException {
		String sql = "SELECT BET_STATE_,vgs.GAME_CODE_,ihg.GAME_NAME_,BET_FOLLOW_MULTIPLE_T_, "
				+ " BET_LEAST_AMOUNT_T_, BET_MOST_AMOUNT_T_, BET_FILTER_NUMBER_, BET_FILTER_TWO_SIDE_, NUMBER_OPPOSING_, "
				+ " TWO_SIDE_OPPOSING_, FILTER_PROJECT_, EXTENSION_SET_ FROM `vr_fm_game_set` vgs "
				+ " LEFT JOIN ibm_handicap_game ihg on vgs.HANDICAP_CODE_ = ihg.HANDICAP_CODE_ and vgs.GAME_CODE_ = ihg.GAME_CODE_ "
				+ " LEFT JOIN ibm_handicap ih ON ih.IBM_HANDICAP_ID_ = ihg.HANDICAP_ID_ "
				+ " WHERE USER_ID_ = ? AND VR_MEMBER_ID_=? AND ih.HANDICAP_CATEGORY_ =? and ihg.STATE_ =? and vgs.STATE_ = ? ORDER BY ihg.SN_";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(vrMemberId);
		parameterList.add(IbmTypeEnum.MEMBER.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取游戏信息
	 * @param appUserId		用户id
	 * @param vrMemberId		虚拟会员id
	 * @return
	 */
	public List<Map<String, Object>> listGameInfo(String appUserId, String vrMemberId) throws SQLException {
		String sql = "SELECT BET_STATE_, GAME_NAME_, vgs.GAME_CODE_, ICON_ FROM ibm_handicap_game ihg LEFT JOIN "
				+ " vr_fm_game_set vgs ON ihg.HANDICAP_CODE_ = vgs.HANDICAP_CODE_ AND ihg.GAME_CODE_ = vgs.GAME_CODE_ "
				+ " LEFT JOIN ibm_handicap ih on ih.IBM_HANDICAP_ID_ = ihg.HANDICAP_ID_"
				+ " WHERE vgs.USER_ID_ =? AND vgs.VR_MEMBER_ID_=? AND ihg.STATE_=? AND vgs.STATE_ = ? AND ih.HANDICAP_CATEGORY_=? ORDER BY ihg.SN_";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(appUserId);
		parameters.add(vrMemberId);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(IbmTypeEnum.MEMBER.name());
		return super.findMapList(sql, parameters);
	}

	/**
	 * 获取游戏设置id
	 * @param appUserId	用户id
	 * @param vrMemberId	虚拟会员id
	 * @param gameCode	游戏编码
	 * @return
	 */
	public Map<String,Object> findGameInfo(String appUserId, String vrMemberId, String gameCode) throws SQLException {
		String sql = "select VR_FM_GAME_SET_ID_,BET_STATE_,BET_FOLLOW_MULTIPLE_T_,BET_LEAST_AMOUNT_T_,BET_MOST_AMOUNT_T_"
				+ ",BET_FILTER_NUMBER_,BET_FILTER_TWO_SIDE_,NUMBER_OPPOSING_,TWO_SIDE_OPPOSING_,FILTER_PROJECT_"
				+ " from vr_fm_game_set where USER_ID_ = ? and VR_MEMBER_ID_=? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(appUserId);
		parameterList.add(vrMemberId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findMap(sql, parameterList);
	}

	/**
	 * 修改投注状态
	 * @param gameSetId	游戏设置id
	 * @param betState	投注状态
	 */
	public void updateBetState(String gameSetId, String betState) throws SQLException {
		String sql="update vr_fm_game_set set BET_STATE_=?,UPDATE_TIME_LONG_=? where VR_FM_GAME_SET_ID_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(betState);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(gameSetId);
		super.dao.execute(sql,parameterList);
	}

	/**
	 * 获取主键
	 * @param appUserId	用户id
	 * @param vrMemberId	虚拟会员id
	 * @param gameCode	游戏编码
	 * @return
	 */
	public String findId(String appUserId, String vrMemberId, String gameCode) throws SQLException {
		String sql="select VR_FM_GAME_SET_ID_ from vr_fm_game_set where USER_ID_=? and VR_MEMBER_ID_=?"
				+ " and GAME_CODE_=? and STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(appUserId);
		parameterList.add(vrMemberId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findString("VR_FM_GAME_SET_ID_",sql,parameterList);
	}
	/**
	 * 更新 代理游戏跟投设置
	 *
	 * @param gameSetId       	游戏设置主键
	 * @param betState          投注状态
	 * @param betFollowMultiple 跟投倍数
	 * @param betLeastAmount    最低金额
	 * @param betMostAmount     最高金额
	 * @param betFilterNumber   过滤数字
	 * @param betFilterTwoSide  过滤双面
	 * @param numberOpposing    数字反投
	 * @param twoSideOpposing   双面反投
	 * @param nowTime           更新时间
	 */
	public void update(String gameSetId, String betState, Integer betFollowMultiple, Integer betLeastAmount,
					   Integer betMostAmount, String betFilterNumber, String betFilterTwoSide, String numberOpposing,
					   String twoSideOpposing, Date nowTime) throws SQLException {
		String sql = "UPDATE vr_fm_game_set SET BET_STATE_ = ?, BET_FOLLOW_MULTIPLE_T_ = ?,BET_LEAST_AMOUNT_T_ = ?, "
				+ " BET_MOST_AMOUNT_T_ = ?,BET_FILTER_NUMBER_ = ?, BET_FILTER_TWO_SIDE_ = ?,NUMBER_OPPOSING_ = ?, "
				+ " TWO_SIDE_OPPOSING_ = ?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE VR_FM_GAME_SET_ID_ = ? AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(12);

		parameterList.add(betState);
		parameterList.add(betFollowMultiple);
		parameterList.add(betLeastAmount);
		parameterList.add(betMostAmount);
		parameterList.add(betFilterNumber);
		parameterList.add(betFilterTwoSide);
		parameterList.add(numberOpposing);
		parameterList.add(twoSideOpposing);
		parameterList.add(nowTime);
		parameterList.add(System.currentTimeMillis());
		parameterList.add(gameSetId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.execute(sql, parameterList);
	}
}
