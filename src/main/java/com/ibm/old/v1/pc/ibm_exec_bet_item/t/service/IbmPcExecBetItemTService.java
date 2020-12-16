package com.ibm.old.v1.pc.ibm_exec_bet_item.t.service;

import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Robot
 */
public class IbmPcExecBetItemTService extends IbmExecBetItemTService {

	/**
	 * 查询投注记录数
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param checkTime        检查时间
	 * @param tableName        检查时间
	 * @return 投注记录数
	 */
	public List<Map<String, Object>> listNewBetInfo(String handicapMemberId,  long checkTime,String tableName) throws SQLException {
		String sql = "SELECT IBM_EXEC_BET_ITEM_ID_,PERIOD_,PLAN_GROUP_DESC_,FUND_T_,BET_CONTENT_,FUNDS_INDEX_,"
				+ " DRAW_NUMBER_,EXEC_STATE_,PROFIT_T_,ODDS_,BET_MODE_,BET_TYPE_,DESC_ FROM "+tableName
				+ " WHERE HANDICAP_MEMBER_ID_ = ? AND STATE_ != ? AND CREATE_TIME_LONG_ >= ? "
				+ " ORDER BY BET_TYPE_ DESC, PLAN_ID_,(PLAN_GROUP_KEY_+0)";
		List<Object> parameterList = new ArrayList<>(4);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(checkTime);
		return this.dao.findMapList(sql, parameterList);
	}

	/**
	 * 开奖期更新数据
	 *
	 * @param handicapMemberId 盘口会员id
	 * @param checkTime        检查时间
	 * @param tableName        表名
	 * @return 开奖期更新数据
	 */
	public List<Map<String, Object>> listDrawInfo(String handicapMemberId, long checkTime,String tableName) throws SQLException {
		String sql = "SELECT IBM_EXEC_BET_ITEM_ID_,DRAW_NUMBER_,EXEC_STATE_,PROFIT_T_ / 1000 AS PROFIT_,ODDS_, "
				+ " BET_TYPE_,DESC_,BET_CONTENT_LEN_ FROM "+tableName+" bet WHERE bet.HANDICAP_MEMBER_ID_ = ? AND "
				+ " bet.STATE_ != ? AND bet.CREATE_TIME_LONG_ <= ? AND bet.UPDATE_TIME_LONG_ >= ?";
		List<Object> parameterList = new ArrayList<>(5);
		parameterList.add(handicapMemberId);
		parameterList.add(IbmStateEnum.DEL.name());
		parameterList.add(checkTime);
		parameterList.add(checkTime);
		return this.dao.findMapList(sql, parameterList);
	}

}
