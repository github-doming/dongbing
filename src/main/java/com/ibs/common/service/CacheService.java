package com.ibs.common.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取开奖信息服务类
 * @Author: null
 * @Date: 2020-05-22 14:28
 * @Version: v1.0
 */
public class CacheService extends BaseServiceProxy {

	/**
	 * 获取开奖号码
	 *
	 * @param gameCode 游戏编码
	 * @param period   期数
	 * @param isClient 是否是客户端
	 * @return 开奖号码
	 */
	public String findDrawNumber(String gameCode, String type, Object period, String drawTableName, boolean isClient)
			throws SQLException {
		String sql = "select DRAW_NUMBER_ from " + drawTableName + " where GAME_DRAW_TYPE_ = ? and PERIOD_ = ? and state_ = ? ";
		List<Object> parameters = new ArrayList<>();
		parameters.add(type);
		parameters.add(period);
		parameters.add(IbsStateEnum.OPEN.name());
		if (isClient) {
			sql += " and GAME_CODE_ = ? ";
			parameters.add(gameCode);
		}
		return super.dao.findString("DRAW_NUMBER_", sql, parameters);
	}

	/**
	 * 获取游戏赔率信息
	 *
	 * @param gameCode      游戏编码
	 * @param oddsTableName 赔率表名
	 * @return 赔率信息
	 */
	public Map<String, Integer> mapOddsByGame(GameUtil.Code gameCode, String oddsTableName) throws SQLException {
		String sql = "SELECT GAME_PLAY_NAME_ as key_,ODDS_T_ as value_ FROM " + oddsTableName
				+ " WHERE GAME_CODE_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode.name());
		parameterList.add(StateEnum.DEL.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 获取赔率信息
	 *
	 * @param oddsTableName 赔率表名称
	 * @return 赔率信息列表
	 */
	public List<Map<String, Object>> listOddsInfo(String oddsTableName) throws SQLException {
		String sql = "SELECT GAME_CODE_,GAME_PLAY_NAME_,ODDS_T_ FROM " + oddsTableName
				+ " WHERE STATE_ = 'OPEN' ORDER BY GAME_CODE_";
		return super.findMapList(sql);
	}

	/**
	 * 获取某一期的最近10期的开奖信息
	 *
	 * @param gameCode      游戏编码
	 * @param drawType      开奖类型
	 * @param period        期数
	 * @param drawTableName 开奖表名
	 * @return 开奖信息
	 */
	public Map<Object, String> mapDrawInfo(String gameCode, String drawType, Object period, String drawTableName)
			throws SQLException {
		long drawTimeLong = getDrawTimeLong(gameCode, drawType, period, drawTableName);
		String sql = "SELECT PERIOD_ as key_,DRAW_NUMBER_ as value_ FROM " + drawTableName
				+ " where GAME_CODE_ = ? and GAME_DRAW_TYPE_ = ?  and STATE_ != ? DRAW_TIME_LONG_ < ? ORDER BY DRAW_TIME_LONG_ DESC LIMIT 10";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameCode);
		parameterList.add(drawType);
		parameterList.add(StateEnum.DEL.name());
		parameterList.add(drawTimeLong);
		return super.findKVMap(sql, parameterList);

	}
	/**
	 * 获取开奖时间戳
	 *
	 * @param gameCode      游戏编码
	 * @param drawType      开奖类型
	 * @param period        期数
	 * @param drawTableName 开奖表名
	 * @return 开奖时间戳
	 */
	private long getDrawTimeLong(String gameCode, String drawType, Object period, String drawTableName)
			throws SQLException {
		String sql = " SELECT DRAW_TIME_LONG_ FROM " + drawTableName
				+ " WHERE GAME_CODE_ = ? AND GAME_DRAW_TYPE_ = ? AND PERIOD_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(gameCode);
		parameterList.add(drawType);
		parameterList.add(period);
		parameterList.add(StateEnum.DEL.name());
		return NumberTool.getLong(super.findMap(sql, parameterList), "DRAW_TIME_LONG_", System.currentTimeMillis());

	}

	/**
	 * 获取赔率信息
	 *
	 * @return 赔率信息列表
	 */
	public List<Map<String, Object>> listOddsInfo() throws SQLException {
		String sql = "SELECT GAME_CODE_,GAME_PLAY_NAME_,ODDS_T_ FROM SYS_DRAW_ODDS WHERE STATE_ = 'OPEN' ORDER BY GAME_CODE_";
		return super.findMapList(sql);
	}
	/**
	 * 获取赔率信息
	 *
	 * @param gameCode      游戏编码
	 * @return 赔率信息列表
	 */
	public Map<String, Integer> mapOddsByGame(GameUtil.Code gameCode) throws SQLException {
		String sql = "SELECT GAME_PLAY_NAME_ as key_,ODDS_T_ as value_ FROM SYS_DRAW_ODDS WHERE GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode.name());
		parameterList.add(StateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 获取单条开奖信息
	 * @param gameCode	游戏编码
	 * @param drawType	开奖类型
	 * @param period		期数
	 * @return
	 */
	public String getDraw(GameUtil.Code gameCode, String drawType, Object period) throws SQLException {
		String sql="select DRAW_NUMBER_ from lottery.cloud_lottery_"+gameCode.name()+" where PERIOD_=? and DRAW_TYPE_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(period);
		parameters.add(drawType);
		return super.dao.findString("DRAW_NUMBER_",sql,parameters);
	}

	/**
	 * 获取100条开奖信息
	 *
	 * @param drawTableName 开奖表名
	 * @param drawType      开奖类型
	 * @return
	 */
	public Map<String, Object> getBatchDraw(String drawTableName, String drawType,String drawTypeName) throws SQLException {
		String sql="select PERIOD_ as key_,DRAW_NUMBER_ as value_ from "+drawTableName+" where "+drawTypeName+"=?"
				+ " ORDER BY CREATE_TIME_LONG_ desc LIMIT 100";
		List<Object> parameters=new ArrayList<>();
		parameters.add(drawType);
		return super.findKVMap(sql,parameters);
	}
}
