package com.ibm.follow.servlet.cloud.vr_member_profit_period.service;

import com.common.util.BaseGameUtil;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_member_profit_period.entity.VrMemberProfitPeriod;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_会员当期盈亏 服务类
 * @author Robot
 */
public class VrMemberProfitPeriodService extends BaseServiceProxy {

	/**
	 * 保存VR_会员当期盈亏 对象数据
	 * @param entity VrMemberProfitPeriod对象数据
	 */
	public String save(VrMemberProfitPeriod entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_member_profit_period 的 VR_MEMBER_PROFIT_PERIOD_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_member_profit_period set state_='DEL' where VR_MEMBER_PROFIT_PERIOD_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_MEMBER_PROFIT_PERIOD_ID_主键id数组的数据
	 * @param idArray 要删除 vr_member_profit_period 的 VR_MEMBER_PROFIT_PERIOD_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_member_profit_period set state_='DEL' where VR_MEMBER_PROFIT_PERIOD_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_member_profit_period  的 VR_MEMBER_PROFIT_PERIOD_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_member_profit_period where VR_MEMBER_PROFIT_PERIOD_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_MEMBER_PROFIT_PERIOD_ID_主键id数组的数据
	 * @param idArray 要删除vr_member_profit_period 的 VR_MEMBER_PROFIT_PERIOD_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_member_profit_period where VR_MEMBER_PROFIT_PERIOD_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrMemberProfitPeriod实体信息
	 * @param entity VR_会员当期盈亏 实体
	 */
	public void update(VrMemberProfitPeriod entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_member_profit_period表主键查找 VR_会员当期盈亏 实体
	 * @param id vr_member_profit_period 主键
	 * @return VR_会员当期盈亏 实体
	 */
	public VrMemberProfitPeriod find(String id) throws Exception {
		return dao.find(VrMemberProfitPeriod.class,id);
	}

	/**
	 * 获取昨日盈亏信息
	 * @param vrMemberId		虚拟会员id
	 * @param startTime		开始时间
	 * @param endTime			结束时间
	 */
	public Map<String, List<Map<String, Object>>> getYesterdayProfitInfo(String vrMemberId, Date startTime, Date endTime) throws SQLException {
		String sql="select GAME_CODE_,PERIOD_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_BET_LEN_,LOSS_BET_LEN_ from"
				+ " vr_member_profit_period where VR_MEMBER_ID_=? and STATE_=? and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(vrMemberId);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		return super.findKeyMaps(sql,parameters,"GAME_CODE_");
	}

	/**
	 * 批量添加游戏盈亏信息
	 * @param gameProfitInfos	游戏盈亏信息
	 */
	public void batchSave(List<Map<String, Object>> gameProfitInfos) throws SQLException {
		StringBuilder sql=new StringBuilder("insert into vr_profit_game ");
		sql.append("(VR_PROFIT_GAME_ID_,VR_MEMBER_ID_,GAME_NAME_,GAME_CODE_,BET_LEN_,PROFIT_MAX_T_,LOSS_MAX_T_,WIN_RATE_T_,"
				+ "PROFIT_T_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters=new ArrayList<>();
		Date nowTime=new Date();
		for(Map<String,Object> memberProfitInfo:gameProfitInfos){
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(memberProfitInfo.get("vrMemberId"));
			parameters.add(memberProfitInfo.get("gameName"));
			parameters.add(memberProfitInfo.get("gameCode"));
			parameters.add(memberProfitInfo.get("betLen"));
			parameters.add(memberProfitInfo.get("profitMax"));
			parameters.add(memberProfitInfo.get("lossMax"));
			parameters.add(memberProfitInfo.get("winRateT"));
			parameters.add(memberProfitInfo.get("profitT"));
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(nowTime);
			parameters.add(nowTime.getTime());
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}

	/**
	 * 保存当期盈亏信息
	 * @param profitInfos		盈亏信息
	 * @param gameCode		游戏编码
	 * @param period			期数
	 * @param nowTime			当前时间
	 */
	public void saveSettlement(Map<String, Map<String, Object>> profitInfos, BaseGameUtil.Code gameCode, Object period, Date nowTime) throws SQLException {
		StringBuilder sql=new StringBuilder();
		sql.append("insert into vr_member_profit_period(VR_MEMBER_PROFIT_PERIOD_ID_,VR_MEMBER_ID_,GAME_CODE_,PERIOD_,"
				+ "PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_BET_LEN_,LOSS_BET_LEN_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
				+ "UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();

		for(Map.Entry<String,Map<String,Object>> entry:profitInfos.entrySet()){
			Map<String,Object> profitInfo=entry.getValue();
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(entry.getKey());
			parameters.add(gameCode.name());
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
			parameters.add(IbmStateEnum.OPEN.name());
		}
		sql.delete(sql.length() - 1, sql.length());
		super.execute(sql, parameters);
	}
}
