package com.ibm.old.v1.pc.ibm_handicap_game.t.service;

import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPcHandicapGameTService extends IbmHandicapGameTService {

	/**
	 * 通过盘口会员id获取盘口会员游戏设置
	 *
	 * @param handicapMemberId 盘口会员id
	 * @return 盘口会员游戏设置
	 */
	public List<Map<String, Object>> listGameSet(String handicapMemberId) throws SQLException {
		String sql="select BET_STATE_,BET_MODE_, ihmgs.GAME_ID_,BET_AUTO_STOP_,BET_AUTO_STOP_TIME_,BET_AUTO_START_,BET_AUTO_START_TIME_,INCREASE_STATE_,INCREASE_STOP_TIME_, "
				+ " BET_SECOND_,SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_,ig.ICON_,ig.GAME_CODE_ from ibm_hm_game_set ihmgs left join ibm_game ig on "
				+ " ihmgs.GAME_ID_=ig.IBM_GAME_ID_ where ihmgs.HANDICAP_MEMBER_ID_=? and ihmgs.STATE_=? and ig.STATE_=? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 通过游戏code获取id
	 *
	 * @param gameCode 游戏code
	 * @return 盘口会员游戏设置
	 */
	public String findId(String gameCode) throws SQLException {
		String sql = "SELECT GAME_ID_ FROM ibm_handicap_game WHERE GAME_CODE_ = ? AND STATE_ != ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return dao.findString("GAME_ID_",sql,parameterList);
	}

	/**
	 * @param gameCode     游戏code
	 * @param handicapCode 盘口code
	 * @return 封盘时间
	 * @Description: 获取封盘时间
	 * <p>
	 * 参数说明
	 */
	public String findSealTime(String gameCode, String handicapCode) throws SQLException {
		String sql = "SELECT SEAL_TIME_ FROM ibm_handicap_game where STATE_ != ? AND GAME_CODE_ = ? AND HANDICAP_CODE_ = ? ";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(gameCode);
		parameterList.add(handicapCode);
		return super.dao.findString("SEAL_TIME_", sql, parameterList);
	}
	/**
	 * 通过盘口id和游戏code查询盘口游戏id
	 * @param handicapId		盘口id
	 * @param gameCode		游戏code
	 * @return
	 * @throws SQLException
	 */
	public String 	findId(String handicapId, String gameCode) throws SQLException {
		String sql="select IBM_HANDICAP_GAME_ID_ from ibm_handicap_game where HANDICAP_ID_=? and GAME_CODE_=? and STATE_!=?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(handicapId);
		parameterList.add(gameCode);
		parameterList.add(IbmStateEnum.DEL.name());
		return  super.dao.findString("IBM_HANDICAP_GAME_ID_", sql, parameterList);
	}
}
