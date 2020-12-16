package com.ibs.plan.module.cloud.ibsp_game.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IBSP_游戏 服务类
 *
 * @author Robot
 */
public class IbspGameService extends BaseServiceProxy {

	/**
	 * 保存IBSP_游戏 对象数据
	 *
	 * @param entity IbspGame对象数据
	 */
	public String save(IbspGame entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_game 的 IBSP_GAME_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_game set state_='DEL' where IBSP_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_game 的 IBSP_GAME_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_game set state_='DEL' where IBSP_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_game  的 IBSP_GAME_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_game where IBSP_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_GAME_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_game 的 IBSP_GAME_ID_ 数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsp_game where IBSP_GAME_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspGame实体信息
	 *
	 * @param entity IBSP_游戏 实体
	 */
	public void update(IbspGame entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_game表主键查找 IBSP_游戏 实体
	 *
	 * @param id ibsp_game 主键
	 * @return IBSP_游戏 实体
	 */
	public IbspGame find(String id) throws Exception {
		return dao.find(IbspGame.class, id);
	}

	/**
	 * 获取 游戏d为key，游戏code为value的集合
	 *
	 * @return 游戏集合
	 */
	public Map<String, String> mapIdCode() throws SQLException {
		String sql = "SELECT IBSP_GAME_ID_ as key_, GAME_CODE_ as value_ FROM ibsp_game where STATE_ = 'OPEN'";
		return super.findKVMap(sql);
	}
	/**
	 * 获取 游戏code集合
	 *
	 * @return 游戏code集合
	 */
	public List<String> findAllGameCode() throws SQLException {
		String sql = "SELECT GAME_CODE_ FROM `ibsp_game` where STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return dao.findStringList("GAME_CODE_",sql,parameterList);
	}

	/**
	 * 获取游戏ID
	 *
	 * @param gameCode 游戏编码
	 * @return 游戏ID
	 */
	public String findId(String gameCode) throws SQLException {
		String sql = "SELECT IBSP_GAME_ID_ FROM `ibsp_game` where GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameCode);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSP_GAME_ID_", sql, parameterList);
	}

	/**
	 * 获取游戏IDs
	 *
	 * @param gameCodes 游戏编码列表
	 * @return
	 */
	public List<String> findIds(Set<String> gameCodes) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBSP_GAME_ID_ FROM `ibsp_game` where STATE_ = ? and GAME_CODE_ in(");
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String gameCode : gameCodes) {
			sql.append("?,");
			parameterList.add(gameCode);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.dao.findStringList("IBSP_GAME_ID_", sql.toString(), parameterList);
	}

	/**
	 * 获取游戏开奖时间
	 *
	 * @param gameId 游戏id
	 * @return
	 */
	public int findDrawTime(String gameId) throws SQLException {
		String sql = "SELECT DRAW_TIME_ FROM `ibsp_game` where IBSP_GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		String drawTime = super.dao.findString("DRAW_TIME_", sql, parameterList);
		return NumberTool.getInteger(drawTime, 0);
	}

	/**
	 * 获取游戏编码
	 *
	 * @param gameId 游戏id
	 * @return 游戏编码
	 */
	public String findCode(String gameId) throws SQLException {
		String sql = "SELECT GAME_CODE_ FROM `ibsp_game where IBSP_GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.findString("GAME_CODE_", sql, parameterList);
	}

	/**
	 * 获取所有游戏信息
	 *
	 * @return 游戏信息
	 */
	public List<Map<String, Object>> findGameInfo() throws SQLException {
		String sql = "select GAME_CODE_,GAME_NAME_ from ibsp_game where STATE_=? ORDER BY SN_ ";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(IbsStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 获取游戏列表
	 * @param gameName  游戏名称
	 * @return
	 */
	public List<Map<String, Object>> listShow(String gameName) throws SQLException {
		String sql="select IBSP_GAME_ID_ as GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,DRAW_TIME_,STATE_ from ibsp_game where STATE_!=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(IbsStateEnum.DEL.name());
		if(StringTool.notEmpty(gameName)){
			sql+=" and GAME_NAME_ like ?";
			parameterList.add("%"+gameName+"%");
		}
		sql+=" order by SN_";
		return super.dao.findMapList(sql,parameterList);
	}

	/**
	 * 获取游戏信息
	 * @param gameId    游戏id
	 * @return
	 */
	public Map<String, Object> findInfoById(String gameId) throws SQLException {
		String sql="select IBSP_GAME_ID_ as GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_,DRAW_TIME_,STATE_,"
				+ "REP_DRAW_TABLE_NAME_,SN_ from ibsp_game where IBSP_GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>(1);
		parameterList.add(gameId);
		return super.dao.findMap(sql,parameterList);
	}

	/**
	 * TODO
	 *  获取游戏信息
	 * @param codes 游戏CODE 集合
	 * @return 游戏信息
	 */
	public List<Map<String,Object>> listByGameCodes(String[] codes) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT IBSP_GAME_ID_ GAME_ID_,GAME_NAME_,GAME_CODE_,ICON_ FROM `ibsp_game` where  STATE_ = ? AND GAME_CODE_ IN(");
		List<Object> parameterList = new ArrayList<>(1+codes.length);
		parameterList.add(IbsStateEnum.OPEN.name());
		for (String gameCode :codes){
			parameterList.add(gameCode);
			sql.append("?,");
		}
		sql.deleteCharAt(sql.length()-1).append(")");
		return super.dao.findMapList(sql.toString(),parameterList);
	}

}
