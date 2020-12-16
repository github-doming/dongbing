package com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.entity.IbmSysBetOdds;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*  服务类
 * @author Robot
 */
public class IbmSysBetOddsService extends BaseServicePlus {

	/**
	 * 保存 对象数据
	 * @param entity IbmSysBetOdds对象数据
	 */
	public String save(IbmSysBetOdds entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除ibm_sys_bet_odds 的 IBM_SYS_BET_ODDS_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_bet_odds set state_='DEL' where IBM_SYS_BET_ODDS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_BET_ODDS_ID_主键id数组的数据
	 * @param idArray 要删除 ibm_sys_bet_odds 的 IBM_SYS_BET_ODDS_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibm_sys_bet_odds set state_='DEL' where IBM_SYS_BET_ODDS_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 ibm_sys_bet_odds  的 IBM_SYS_BET_ODDS_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_bet_odds where IBM_SYS_BET_ODDS_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_BET_ODDS_ID_主键id数组的数据
	 * @param idArray 要删除ibm_sys_bet_odds 的 IBM_SYS_BET_ODDS_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibm_sys_bet_odds where IBM_SYS_BET_ODDS_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysBetOdds实体信息
	 * @param entity  实体
	 */
	public void update(IbmSysBetOdds entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_bet_odds表主键查找  实体
	 * @param id ibm_sys_bet_odds 主键
	 * @return  实体
	 */
	public IbmSysBetOdds find(String id) throws Exception {
		return (IbmSysBetOdds) this.dao.find(IbmSysBetOdds. class,id);

	}

	/**
	 * 获取游戏的赔率
	 * @param gameId 游戏id
	 * @return 赔率
	 */
	public Map<String, Integer> mapOddsByGame(String gameId) throws SQLException {
		String sql ="SELECT GAME_PLAY_NAME_ as key_,ODDS_T_ as value_ FROM ibm_sys_bet_odds WHERE GAME_ID_ = ? and STATE_ != ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		parameterList.add(IbmStateEnum.DEL.name());
		return super.findKVMap(sql,parameterList);
	}
	public List<IbmSysBetOdds> findByGameId(String gameId) throws Exception {
		String sql="select * from ibm_sys_bet_odds where GAME_ID_=?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(gameId);
		return super.dao.findObjectList(IbmSysBetOdds.class,sql,parameterList);
	}

	public List<IbmSysBetOdds> listByTreeId(String treeId) throws Exception {
		String sql="select * from ibm_sys_bet_odds where SYS_BET_ODDS_TREE_ID_ = ?";
		List<Object> parameterList = new ArrayList<>(2);
		parameterList.add(treeId);
		return super.dao.findObjectList(IbmSysBetOdds.class,sql,parameterList);
	}
}
