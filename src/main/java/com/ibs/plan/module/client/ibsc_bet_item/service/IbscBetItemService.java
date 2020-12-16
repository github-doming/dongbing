package com.ibs.plan.module.client.ibsc_bet_item.service;

import com.alibaba.fastjson.JSONArray;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.ibsc_bet_item.entity.IbscBetItem;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * IBSC客户端_投注详情 服务类
 *
 * @author Robot
 */
public class IbscBetItemService extends BaseServiceProxy {

	/**
	 * 保存IBSC客户端_投注详情 对象数据
	 *
	 * @param entity IbscBetItem对象数据
	 */
	public String save(IbscBetItem entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsc_bet_item 的 IBSC_BET_ITEM_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsc_bet_item set state_='DEL' where IBSC_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 逻辑删除IBSC_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsc_bet_item 的 IBSC_BET_ITEM_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsc_bet_item set state_='DEL' where IBSC_BET_ITEM_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsc_bet_item  的 IBSC_BET_ITEM_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsc_bet_item where IBSC_BET_ITEM_ID_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(id);
		dao.execute(sql, parameters);
	}

	/**
	 * 物理删除IBSC_BET_ITEM_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsc_bet_item 的 IBSC_BET_ITEM_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from ibsc_bet_item where IBSC_BET_ITEM_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbscBetItem实体信息
	 *
	 * @param entity IBSC客户端_投注详情 实体
	 */
	public void update(IbscBetItem entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsc_bet_item表主键查找 IBSC客户端_投注详情 实体
	 *
	 * @param id ibsc_bet_item 主键
	 * @return IBSC客户端_投注详情 实体
	 */
	public IbscBetItem find(String id) throws Exception {
		return dao.find(IbscBetItem.class, id);

	}
	/**
	 * 保存投注结果信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betInfoId 投注信息主键
	 * @param period    期数
	 * @param gameCode  游戏code
	 * @param betResult 投注结果
	 * @param betItems  投注项
	 */
	public void save(String existHmId, String betInfoId, Object period, GameUtil.Code gameCode, JSONArray betResult,
			List<String> betItems) throws SQLException {
		StringBuilder sql = new StringBuilder();
		List<Object> parameters = new ArrayList<>();
		sql.append("insert into ibsc_bet_item (IBSC_BET_ITEM_ID_,EXIST_HM_ID_,BET_INFO_ID_,");
		sql.append("PERIOD_,GAME_CODE_,BET_INFO_,BET_NUMBER_,ODDS_,CREATE_TIME_,CREATE_TIME_LONG_,");
		sql.append("UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) VALUES");
		Date nowTime = new Date();
		for (int i = 0; i < betResult.size(); i++) {
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?),");
			JSONArray betInfo = betResult.getJSONArray(i);
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(existHmId);
			parameters.add(betInfoId);
			parameters.add(period);
			parameters.add(gameCode.name());
			parameters.add(betInfo.getString(1));
			parameters.add(betInfo.get(0).toString());
			parameters.add(betInfo.getString(3));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
			betItems.remove(betInfo.getString(1).concat("|")
					.concat(String.valueOf(NumberTool.longValueT(betInfo.getDouble(2)))));
		}
		sql.deleteCharAt(sql.length() - 1);
		super.execute(sql.toString(), parameters);
	}
}
