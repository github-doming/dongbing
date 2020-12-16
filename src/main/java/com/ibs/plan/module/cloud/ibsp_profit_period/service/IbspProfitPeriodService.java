package com.ibs.plan.module.cloud.ibsp_profit_period.service;

import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_profit_period.entity.IbspProfitPeriod;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * IBSP_盘口会员当期盈亏 服务类
 *
 * @author Robot
 */
public class IbspProfitPeriodService extends BaseServiceProxy {

	/**
	 * 保存IBSP_盘口会员当期盈亏 对象数据
	 *
	 * @param entity IbspProfitPeriod对象数据
	 */
	public String save(IbspProfitPeriod entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除ibsp_profit_period 的 IBSP_PROFIT_PERIOD_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update ibsp_profit_period set state_='DEL' where IBSP_PROFIT_PERIOD_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除IBSP_PROFIT_PERIOD_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 ibsp_profit_period 的 IBSP_PROFIT_PERIOD_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update ibsp_profit_period set state_='DEL' where IBSP_PROFIT_PERIOD_ID_ in(" + stringBuilder
					.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 ibsp_profit_period  的 IBSP_PROFIT_PERIOD_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from ibsp_profit_period where IBSP_PROFIT_PERIOD_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除IBSP_PROFIT_PERIOD_ID_主键id数组的数据
	 *
	 * @param idArray 要删除ibsp_profit_period 的 IBSP_PROFIT_PERIOD_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql =
					"delete from ibsp_profit_period where IBSP_PROFIT_PERIOD_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新IbspProfitPeriod实体信息
	 *
	 * @param entity IBSP_盘口会员当期盈亏 实体
	 */
	public void update(IbspProfitPeriod entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据ibsp_profit_period表主键查找 IBSP_盘口会员当期盈亏 实体
	 *
	 * @param id ibsp_profit_period 主键
	 * @return IBSP_盘口会员当期盈亏 实体
	 */
	public IbspProfitPeriod find(String id) throws Exception {
		return dao.find(IbspProfitPeriod.class, id);
	}
	/**
	 * 保存当期盈亏信息
	 * @param hmProfitInfos		会员盈亏信息
	 * @param gameId				游戏id
	 * @param period				期数
	 * @param nowTime				当前时间
	 * @param betMode				投注模式
	 */
	public void save4Settlement(Map<String, Map<String, Object>> hmProfitInfos, String gameId, Object period,
								  Date nowTime, IbsTypeEnum betMode) throws SQLException {
		StringBuilder sql=new StringBuilder();
		String tableName="ibsp_profit_period";
		String profitId="IBSP_PROFIT_PERIOD_ID_";
		if(IbsTypeEnum.VIRTUAL.equals(betMode)){
			tableName="ibsp_profit_period_vr";
			profitId="IBSP_PROFIT_PERIOD_VR_ID_";
		}
		sql.append("insert into ").append(tableName).append("(").append(profitId).append(",HANDICAP_MEMBER_ID_,GAME_ID_,PERIOD_,")
				.append("PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_BET_LEN_,LOSS_BET_LEN_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,")
				.append("UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		for(Map.Entry<String,Map<String,Object>> entry:hmProfitInfos.entrySet()){
			Map<String,Object> profitInfo=entry.getValue();
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(entry.getKey());
			parameters.add(gameId);
			parameters.add(period);
			parameters.add(profitInfo.get("profitTh"));
			parameters.add(profitInfo.get("betFundsTh"));
			parameters.add(profitInfo.get("betLen"));
			parameters.add(profitInfo.get("profitBetLen"));
			parameters.add(profitInfo.get("lossBetLen"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbsStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}
}
