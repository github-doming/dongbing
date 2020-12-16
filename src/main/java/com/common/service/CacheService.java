package com.common.service;


import com.alibaba.fastjson.JSONObject;
import com.common.util.BaseGameUtil;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
		parameters.add(StateEnum.OPEN.name());
		if (isClient) {
			sql += " and GAME_CODE_ = ? ";
			parameters.add(gameCode);
		}
		return super.dao.findString("DRAW_NUMBER_", sql, parameters);
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
	 * @param gameCode 游戏编码
	 * @return 赔率信息列表
	 */
	public Map<String, Integer> mapOddsByGame(BaseGameUtil.Code gameCode) throws SQLException {
		String gameCodeType;
		switch (gameCode) {
			case PK10:
			case XYFT:
			case SELF_10_2:
			case SELF_10_5:
			case JS10:
			case COUNTRY_10:
				gameCodeType = "PK10";
				break;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				gameCodeType = "CQSSC";
				break;
			default:
				gameCodeType = "GDKLC";
		}
		String sql = "SELECT GAME_PLAY_NAME_ as key_,ODDS_T_ as value_ FROM SYS_DRAW_ODDS WHERE GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCodeType);
		parameterList.add(StateEnum.OPEN.name());
		return super.findKVMap(sql, parameterList);
	}

	/**
	 * 获取单条开奖信息
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 开奖类型
	 * @param period   期数
	 * @return
	 */
	public String getDraw(BaseGameUtil.Code gameCode, String drawType, Object period) throws SQLException {
		String sql = "select DRAW_NUMBER_ from lottery.cloud_lottery_" + gameCode.name() + " where PERIOD_=? and DRAW_TYPE_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(period);
		parameters.add(drawType);
		return super.dao.findString("DRAW_NUMBER_", sql, parameters);
	}

	/**
	 * 获取100条开奖信息
	 *
	 * @param drawTableName 开奖表名
	 * @param drawType      开奖类型
	 * @return
	 */
	public Map<String, Object> getBatchDraw(String drawTableName, String drawType, String drawTypeName) throws SQLException {
		String sql = "select PERIOD_ as key_,DRAW_NUMBER_ as value_ from " + drawTableName + " where " + drawTypeName + "=?"
				+ " ORDER BY CREATE_TIME_LONG_ desc LIMIT 100";
		List<Object> parameters = new ArrayList<>();
		parameters.add(drawType);
		return super.findKVMap(sql, parameters);
	}

	/**
	 * 保存游戏开奖信息-客户端
	 *
	 * @param gameCode 游戏编码
	 * @param drawType 游戏类型
	 * @param drawInfo 开奖信息
	 */
	public void save(BaseGameUtil.Code gameCode, String drawType, JSONObject drawInfo) throws SQLException {
		String sql = "insert into client_rep_draw(CLIENT_REP_DRAW_ID_,GAME_CODE_,GAME_DRAW_TYPE_,PERIOD_,DRAW_TIME_,DRAW_TIME_LONG_,DRAW_NUMBER_,DRAW_ITEM_,"
				+ "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_,DESC_) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object> parameters = new ArrayList<>();
		parameters.add(RandomTool.getNumLetter32());
		parameters.add(gameCode.name());
		parameters.add(drawType);
		parameters.add(drawInfo.get("period"));
		parameters.add(new Date(drawInfo.getLong("drawTimeLong")));
		parameters.add(drawInfo.getLong("drawTimeLong"));
		parameters.add(drawInfo.getString("drawNumber"));
		parameters.add(drawInfo.getString("drawItem"));
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		parameters.add(drawInfo.getString("desc"));
		super.dao.execute(sql, parameters);
	}

	/**
	 * 保存游戏开奖信息-服务端
	 *
	 * @param drawType      开奖类型
	 * @param drawInfo      开奖结果信息
	 * @param drawTableName 开奖表名
	 * @param nowTime       当前时间
	 */
	public void save(String drawType, JSONObject drawInfo, String drawTableName, Date nowTime) throws SQLException {
		String sql = "insert into " + drawTableName + "(" + drawTableName
				+ "_ID_,GAME_DRAW_TYPE_,PERIOD_,DRAW_NUMBER_,DRAW_ITEM_,DRAW_TIME_,DRAW_TIME_LONG_,"
				+ "CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_,DESC_) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Long drawTimeLong = NumberTool.getLong(drawInfo.get("drawTimeLong"));
		List<Object> parameters = new ArrayList<>();
		parameters.add(RandomTool.getNumLetter32());
		parameters.add(drawType);
		String period = drawInfo.get("period").toString();
		if (StringTool.isContains(drawInfo.get("period").toString(), "-")) {
			period = period.substring(4);
		}
		parameters.add(period);
		parameters.add(drawInfo.getString("drawNumber"));
		parameters.add(drawInfo.getString("drawItem"));
		parameters.add(new Date(drawTimeLong));
		parameters.add(drawTimeLong);
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(nowTime);
		parameters.add(System.currentTimeMillis());
		parameters.add(StateEnum.OPEN.name());
		parameters.add(drawInfo.getString("desc"));
		super.dao.execute(sql, parameters);
	}

	/**
	 * 获取游戏开奖列表
	 *
	 * @param tableName    开奖表名
	 * @param gameDrawType 游戏开奖类型
	 * @param checkNum     获取校验数
	 * @return 开奖列表信息
	 */
	public List<Map<String, Object>> listGameDraw(String tableName, Object gameDrawType, int checkNum)
			throws SQLException {
		String sql = "SELECT DRAW_TIME_LONG_,PERIOD_,DRAW_NUMBER_ FROM " + tableName
				+ " where state_ = ? and GAME_DRAW_TYPE_=? order by DRAW_TIME_LONG_ desc limit ?";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(gameDrawType);
		parameterList.add(checkNum);
		return super.findMapList(sql, parameterList);
	}

	/**
	 * 获取游戏开奖
	 *
	 * @param tableName 开奖表名
	 * @param type      游戏类型
	 * @return 游戏开奖
	 */
	public Map<String, Object> findGameDraw(String tableName, String type, Object period)
			throws SQLException {
		String sql = "SELECT DRAW_TIME_LONG_,PERIOD_,DRAW_NUMBER_ FROM " + tableName +
				" where state_ = ? and GAME_DRAW_TYPE_ = ? and PERIOD_ = ? order by DRAW_TIME_LONG_ desc";
		List<Object> parameterList = new ArrayList<>(3);
		parameterList.add(StateEnum.OPEN.name());
		parameterList.add(type);
		parameterList.add(period);
		return super.findMap(sql, parameterList);
	}
}
