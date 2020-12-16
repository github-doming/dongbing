package com.ibs.plan.module.cloud.ibsp_handicap_game.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_game.entity.IbspHandicapGame;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* IBSP_盘口游戏 服务类
 * @author Robot
 */
public class IbspHandicapGameService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口游戏 对象数据
	 * @param entity IbspHandicapGame对象数据
	 */
	public String save(IbspHandicapGame entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibsp_handicap_game 的 IBSP_HANDICAP_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_handicap_game set state_='DEL' where IBSP_HANDICAP_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_HANDICAP_GAME_ID_主键id数组的数据
	 * @param idArray 要删除 ibsp_handicap_game 的 IBSP_HANDICAP_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_handicap_game set state_='DEL' where IBSP_HANDICAP_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibsp_handicap_game  的 IBSP_HANDICAP_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_handicap_game where IBSP_HANDICAP_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_HANDICAP_GAME_ID_主键id数组的数据
	 * @param idArray 要删除ibsp_handicap_game 的 IBSP_HANDICAP_GAME_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_handicap_game where IBSP_HANDICAP_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspHandicapGame实体信息
	 * @param entity IBSP_盘口游戏 实体
	 */
	public void update(IbspHandicapGame entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_handicap_game表主键查找 IBSP_盘口游戏 实体
	 * @param id ibsp_handicap_game 主键
	 * @return IBSP_盘口游戏 实体
	 */
	public IbspHandicapGame find(String id) throws Exception {
		return dao.find(IbspHandicapGame.class,id);
	}
	/**
	 * 获取封盘时间
	 *
	 * @param handicapId 盘口id
	 * @param gameId     游戏id
	 * @return 封盘时间
	 */
	public Map<String,Object> findInfo(String handicapId, String gameId) throws SQLException {
		String sql="select CLOSE_TIME,GAME_DRAW_TYPE_ from ibsp_handicap_game where HANDICAP_ID_ = ? and GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findMap(sql,parameterList);
	}

	/**
	 * 获取盘口主键列表
	 * @param gameCode  游戏编码
	 * @param drawType 开奖类型
	 * @return  盘口主键列表
	 */
	public List<String> listHandicapId(GameUtil.Code gameCode, String drawType) throws SQLException {
		String sql="select HANDICAP_ID_ as key_ from ibsp_handicap_game where GAME_CODE_ = ? and GAME_DRAW_TYPE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(gameCode.name());
		parameterList.add(drawType);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findStringList(sql,parameterList);
	}

	/**
	 * 修改投注状态
	 * @param handicapMemberId		盘口会员id
	 * @param nowTime					当前时间
	 */
	public void updateBetState(String handicapMemberId, Date nowTime) throws SQLException {
		String sql = "UPDATE ibsp_hm_game_set SET BET_STATE_ =?,UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ? "
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND BET_STATE_ = ? AND STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(6);
		parameterList.add(IbsTypeEnum.FALSE.name());
		parameterList.add(nowTime);
		parameterList.add(nowTime.getTime());
		parameterList.add(handicapMemberId);
		parameterList.add(IbsTypeEnum.TRUE.name());
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 根据盘口和游戏ids获取盘口游戏信息
	 *
	 * @param handicapId 盘口ids
	 * @param gameIds    游戏ids
	 * @return
	 */
	public List<String> findHandicapGameInfo(String handicapId, List<String> gameIds) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select GAME_ID_ from ibsp_handicap_game where HANDICAP_ID_=? and GAME_ID_ in(");
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		for (String gameId : gameIds) {
			sql.append("?,");
			parameterList.add(gameId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findStringList("GAME_ID_", sql.toString(), parameterList);
	}

	/**
	 * 获取指定盘口所有盘口游戏
	 *
	 * @param handicapId 盘口id
	 * @return
	 */
	public List<Map<String, Object>> findHandicapInfo(Object handicapId) throws SQLException {
		String sql = "select ihg.HANDICAP_GAME_NAME_,ig.GAME_CODE_ from ibsp_handicap_game ihg LEFT JOIN ibsp_game ig ON ihg.GAME_ID_=ig.IBSP_GAME_ID_"
				+ " LEFT JOIN ibsp_handicap ih ON ihg.HANDICAP_ID_=ih.IBSP_HANDICAP_ID_ WHERE ihg.HANDICAP_ID_=?"
				+ " and ihg.STATE_=? and ig.STATE_=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取盘口游戏列表
	 *
	 * @param handicapId 盘口id
	 * @return
	 */
	public List<Map<String, Object>> findByHandicapId(String handicapId) throws SQLException {
		String sql = "SELECT ihg.IBSP_HANDICAP_GAME_ID_ as HANDICAP_GAME_ID_,ihg.HANDICAP_GAME_NAME_,ihg.GAME_CODE_,ihg.GAME_DRAW_TYPE_,ihg.CLOSE_TIME,"
				+ "ihg.ICON_,ihg.STATE_,ihg.SN_,ihg.DRAW_TIME_,ig.GAME_NAME_ " +
				" from ibsp_handicap_game  ihg LEFT JOIN ibsp_game ig ON ihg.GAME_ID_ = ig.IBSP_GAME_ID_" +
				" WHERE ihg.HANDICAP_ID_ = ? and ihg.STATE_ != ? AND ig.STATE_ != ? ORDER BY ihg.SN_";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.DEL.name());
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 修改盘口游戏状态
	 *
	 * @param handicapId 盘口id
	 * @param state      状态
	 * @param updateUser 修改人
	 */
	public void updateByHandicapId(String handicapId, String state, String updateUser) throws SQLException {
		String sql = "UPDATE ibsp_handicap_game ihg LEFT JOIN ibsp_game ig ON ihg.GAME_ID_=ig.IBSP_GAME_ID_"
				+ " SET ihg.STATE_ =?, ihg.UPDATE_USER_ =?, ihg.UPDATE_TIME_ =?, ihg.UPDATE_TIME_LONG_ =?"
				+ " WHERE ihg.HANDICAP_ID_ =? AND ig.STATE_ =? AND ihg.STATE_ !=?";
		List<Object> parameterList = new ArrayList<>(7);
		parameterList.add(state);
		parameterList.add(updateUser);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(handicapId);
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(IbsStateEnum.DEL.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取盘口游戏信息
	 *
	 * @param handicapGameId 盘口游戏id
	 * @return
	 */
	public Map<String, Object> findInfoById(String handicapGameId) throws SQLException {
		String sql = "SELECT IBSP_HANDICAP_GAME_ID_ as HANDICAP_GAME_ID_,HANDICAP_GAME_NAME_,GAME_DRAW_TYPE_,CLOSE_TIME_,"
				+ "ICON_,STATE_,SN_ from ibsp_handicap_game WHERE IBSP_HANDICAP_GAME_ID_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapGameId);
		parameterList.add(IbsStateEnum.DEL.name());
		return super.dao.findMap(sql, parameterList);
	}
}
