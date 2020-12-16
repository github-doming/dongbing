package com.ibm.follow.connector.admin.manage3.handicap.service;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 后台管理盘口游戏服务类
 * @Author: Dongming
 * @Date: 2019-11-07 18:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmAdminHandicapGameService extends IbmHandicapGameService {

	/**
	 * 获取盘口的游戏名称列表
	 *
	 * @param handicapId 盘口主键
	 * @return 游戏名称列表
	 */
	public List<String> listGameName(String handicapId) throws SQLException {
		String sql = "select GAME_NAME_ as key_ from ibm_handicap_game where HANDICAP_ID_ = ? and STATE_ = ? ORDER BY SN_ ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.findStringList(sql, parameterList);
	}

	/**
	 * 获取盘口不存在的游戏信息
	 *
	 * @param handicapId 盘口主键
	 * @return 游戏信息-GAME_NAME_,GAME_CODE_
	 */
	public List<Map<String, Object>> listNoGame(String handicapId) throws SQLException {
		String sql = "SELECT IBM_GAME_ID_ AS GAME_ID_, GAME_NAME_, GAME_CODE_ FROM ibm_game WHERE IBM_GAME_ID_ NOT IN "
				+ " (SELECT GAME_ID_ FROM ibm_handicap_game WHERE HANDICAP_ID_ = ? AND STATE_ = ?) AND STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.OPEN.name());
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameterList);
	}

	/**
	 * 更新盘口游戏信息
	 *
	 * @param handicapGameId 盘口游戏
	 * @param closeTime      关闭事件
	 * @param sn             盘口游戏排名
	 * @param icon           游戏图标
	 * @param tableName      表名
	 * @param gameName       游戏名
	 * @param desc           描述
	 */
	public void updateGameInfo(String handicapGameId, int closeTime, int sn, String icon, String tableName,
			String gameName,String type, String desc) throws SQLException {
		String sql = "UPDATE ibm_handicap_game SET CLOSE_TIME_ = ?, SN_ = ?,TYPE_=?, UPDATE_TIME_ = ?, UPDATE_TIME_LONG_ = ?,DESC_ = ? ";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(closeTime);
		parameterList.add(sn);
        parameterList.add(type);
		parameterList.add(new Date());
		parameterList.add(System.currentTimeMillis());
		parameterList.add(desc);

		if (StringTool.notEmpty(icon)) {
			sql += ",ICON_ = ? ";
			parameterList.add(icon);
		}
		if (StringTool.notEmpty(tableName)) {
			sql += ",SUB_IHBI_TABLE_NAME_ = ? ";
			parameterList.add(tableName);
		}
		if (StringTool.notEmpty(gameName)) {
			sql += ",GAME_NAME_ = ? ";
			parameterList.add(gameName);
		}
		sql += "WHERE IBM_HANDICAP_GAME_ID_ = ? AND STATE_ = ?";
		parameterList.add(handicapGameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		super.dao.execute(sql, parameterList);
	}

	/**
	 * 获取盘口游戏信息
	 *
	 * @return 盘口游戏信息
	 */
	public List<Map<String, Object>> listInfoByHandicapId(String handicapId) throws SQLException {
		String sql = "SELECT GAME_NAME_,GAME_CODE_,IBM_HANDICAP_GAME_ID_ as HANDICAP_GAME_ID_ ,CLOSE_TIME_, "
				+ " SUB_IHBI_TABLE_NAME_,TYPE_ FROM `ibm_handicap_game` where HANDICAP_ID_ = ? and STATE_ != ? ORDER BY SN_";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.dao.findMapList(sql, parameterList);
	}


	/**
	 * 获取盘口游戏信息
	 *
	 * @param handicapGameId 盘口游戏id
	 * @return 盘口游戏信息
	 */
	public Map<String, Object> findInfo(String handicapGameId) throws SQLException {
		String sql =
				"SELECT GAME_NAME_,CLOSE_TIME_,SUB_IHBI_TABLE_NAME_ as TABLE_NAME_,ICON_,SN_,TYPE_ FROM `ibm_handicap_game` "
						+ " where IBM_HANDICAP_GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(handicapGameId);
		parameterList.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameterList);
	}

	/**
	 * 更具主键ID查找盘口ID和游戏ID
	 *
	 * @param handicapGameId 盘口游戏主键
	 * @return 盘口ID和游戏ID
	 */
	public Map<String, Object> findIdInfo(String handicapGameId) throws SQLException {
		String sql = "select HANDICAP_ID_,GAME_ID_ from ibm_handicap_game where IBM_HANDICAP_GAME_ID_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(handicapGameId);
		parameters.add(IbmStateEnum.OPEN.name());
		return dao.findMap(sql, parameters);
	}
}
