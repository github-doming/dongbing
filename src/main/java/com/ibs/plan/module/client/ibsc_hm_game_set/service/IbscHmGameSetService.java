package com.ibs.plan.module.client.ibsc_hm_game_set.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_hm_game_set.entity.IbscHmGameSet;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.*;

/**
 * IBSC客户端_盘口会员游戏设置 服务类
 *
 * @author Robot
 */
public class IbscHmGameSetService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_盘口会员游戏设置 对象数据
	 *
	 * @param entity IbscHmGameSet对象数据
	 */
	public String save(IbscHmGameSet entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_hm_game_set 的 IBSC_HM_GAME_SET_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_hm_game_set set state_='DEL' where IBSC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_hm_game_set 的 IBSC_HM_GAME_SET_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"update ibsc_hm_game_set set state_='DEL' where IBSC_HM_GAME_SET_ID_ in(" + stringBuilder.toString()
							+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_hm_game_set  的 IBSC_HM_GAME_SET_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_hm_game_set where IBSC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_HM_GAME_SET_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_hm_game_set 的 IBSC_HM_GAME_SET_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_hm_game_set where IBSC_HM_GAME_SET_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscHmGameSet实体信息
	 *
	 * @param entity IBSC客户端_盘口会员游戏设置 实体
	 */
	public void update(IbscHmGameSet entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_hm_game_set表主键查找 IBSC客户端_盘口会员游戏设置 实体
	 *
	 * @param id ibsc_hm_game_set 主键
	 * @return IBSC客户端_盘口会员游戏设置 实体
	 */
	public IbscHmGameSet find(String id) throws Exception {
		return (IbscHmGameSet) dao.find(IbscHmGameSet.class, id);

	}

	/**
	 * 获取会员停止新增状态
	 *
	 * @param existHmIds 已存在盘口会员ids
	 * @return 停止新增状态
	 */
	public Map<String, Map<String, Object>> findGameInfo(Set<String> existHmIds, GameUtil.Code gameCode)
			throws SQLException {
		StringBuilder sql = new StringBuilder("select EXIST_HM_ID_,INCREASE_STATE_,BET_SECOND_,BET_MODE_,IS_INVERSE_ from ibsc_hm_game_set"
				+ " where STATE_=? and GAME_CODE_=? and EXIST_HM_ID_ in(");
		List<Object> parameterList = new ArrayList<>(existHmIds.size());
		parameterList.add(IbsStateEnum.OPEN.name());
		parameterList.add(gameCode.name());
		for (String existHmId : existHmIds) {
			sql.append("?,");
			parameterList.add(existHmId);
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		return super.findKeyMap(sql.toString(),parameterList,"EXIST_HM_ID_");
	}
	/**
	 * 修改游戏限额
	 *
	 * @param hmGameSetId 盘口会员游戏设置id
	 * @param quotaInfo   游戏限额信息
	 */
	public void updateGameLimit(String hmGameSetId, String quotaInfo) throws SQLException {
		String sql = "update ibsc_hm_game_set set BET_LIMIT_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ?,DESC_ = ? "
				+ " where IBSC_HM_GAME_SET_ID_=?";
		List<Object> parameters = new ArrayList<>(2);
		parameters.add(quotaInfo);
		parameters.add(new Date());
		parameters.add(System.currentTimeMillis());
		parameters.add("修改游戏限额");
		parameters.add(hmGameSetId);
		super.dao.execute(sql, parameters);
	}

	/**
	 * 获取游戏设置信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏编码
	 * @return 设置信息
	 */
	public IbscHmGameSet findEntity(String existHmId, String gameCode) throws Exception {
		String sql = "select * from ibsc_hm_game_set where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.dao.findObject(IbscHmGameSet.class, sql, parameters);
	}

	/**
	 * 获取投注限额设置
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @return 投注限额设置
	 */
	public Map<String, Object> findLimit(String existHmId, GameUtil.Code gameCode) throws SQLException {
		String sql = "select SPLIT_TWO_SIDE_AMOUNT_,SPLIT_NUMBER_AMOUNT_,BET_LIMIT_ from ibsc_hm_game_set "
				+ " where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode.name());
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findMap(sql, parameters);
	}

	/**
	 * 获取配置信息主键
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏编码
	 * @return 配置信息主键
	 */
	public String findId(String existHmId, String gameCode) throws SQLException {
		String sql = "select IBSC_HM_GAME_SET_ID_ from ibsc_hm_game_set where EXIST_HM_ID_ = ? and GAME_CODE_ = ? and STATE_ = ?";
		List<Object> parameters = new ArrayList<>(3);
		parameters.add(existHmId);
		parameters.add(gameCode);
		parameters.add(IbsStateEnum.OPEN.name());
		return super.findString("IBSC_HM_GAME_SET_ID_", sql, parameters);
	}
}
