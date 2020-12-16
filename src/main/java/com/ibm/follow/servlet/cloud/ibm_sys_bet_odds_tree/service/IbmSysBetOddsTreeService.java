package com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.service;

import com.ibm.common.core.BaseServicePlus;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds_tree.entity.IbmSysBetOddsTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务类
 *
 * @author Robot
 */
public class IbmSysBetOddsTreeService extends BaseServicePlus {

	/**
	 * 保存 对象数据
	 *
	 * @param entity IbmSysBetOddsTree对象数据
	 */
	public String save(IbmSysBetOddsTree entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibm_sys_bet_odds_tree 的 IBM_SYS_BET_ODDS_TREE_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibm_sys_bet_odds_tree set state_='DEL' where IBM_SYS_BET_ODDS_TREE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBM_SYS_BET_ODDS_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibm_sys_bet_odds_tree 的 IBM_SYS_BET_ODDS_TREE_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibm_sys_bet_odds_tree set state_='DEL' where IBM_SYS_BET_ODDS_TREE_ID_ in(" + stringBuilder
							.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibm_sys_bet_odds_tree  的 IBM_SYS_BET_ODDS_TREE_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibm_sys_bet_odds_tree where IBM_SYS_BET_ODDS_TREE_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBM_SYS_BET_ODDS_TREE_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibm_sys_bet_odds_tree 的 IBM_SYS_BET_ODDS_TREE_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibm_sys_bet_odds_tree where IBM_SYS_BET_ODDS_TREE_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbmSysBetOddsTree实体信息
	 *
	 * @param entity 实体
	 */
	public void update(IbmSysBetOddsTree entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibm_sys_bet_odds_tree表主键查找  实体
	 *
	 * @param id ibm_sys_bet_odds_tree 主键
	 * @return 实体
	 */
	public IbmSysBetOddsTree find(String id) throws Exception {
		return (IbmSysBetOddsTree) this.dao.find(IbmSysBetOddsTree.class, id);

	}

	/**
	 * 根据游戏id获取赔率
	 * @param gameId	游戏id
	 * @return
	 */
	public List<IbmSysBetOddsTree> listByGameId(String gameId) throws Exception {
		String sql="select * from ibm_sys_bet_odds_tree where GAME_ID_=?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(gameId);
		return super.dao.findObjectList(IbmSysBetOddsTree.class,sql,parameters);
	}
}
