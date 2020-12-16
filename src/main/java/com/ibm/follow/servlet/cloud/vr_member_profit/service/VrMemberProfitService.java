package com.ibm.follow.servlet.cloud.vr_member_profit.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_member_profit.entity.VrMemberProfit;
import org.doming.core.common.jdbc.BaseServiceProxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* VR_会员总盈亏 服务类
 * @author Robot
 */
public class VrMemberProfitService extends BaseServiceProxy {

	/**
	 * 保存VR_会员总盈亏 对象数据
	 * @param entity VrMemberProfit对象数据
	 */
	public String save(VrMemberProfit entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 * @param id 要删除vr_member_profit 的 VR_MEMBER_PROFIT_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_member_profit set state_='DEL' where VR_MEMBER_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_MEMBER_PROFIT_ID_主键id数组的数据
	 * @param idArray 要删除 vr_member_profit 的 VR_MEMBER_PROFIT_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_member_profit set state_='DEL' where VR_MEMBER_PROFIT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 * @param id 要删除 vr_member_profit  的 VR_MEMBER_PROFIT_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_member_profit where VR_MEMBER_PROFIT_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_MEMBER_PROFIT_ID_主键id数组的数据
	 * @param idArray 要删除vr_member_profit 的 VR_MEMBER_PROFIT_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_member_profit where VR_MEMBER_PROFIT_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrMemberProfit实体信息
	 * @param entity VR_会员总盈亏 实体
	 */
	public void update(VrMemberProfit entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_member_profit表主键查找 VR_会员总盈亏 实体
	 * @param id vr_member_profit 主键
	 * @return VR_会员总盈亏 实体
	 */
	public VrMemberProfit find(String id) throws Exception {
		return dao.find(VrMemberProfit.class,id);
	}

	/**
	 * 获取昨日盈亏信息
	 * @param startTime
	 * @param endTime
	 */
	public Map<String, Map<String, Object>> getYesterdayProfit(Date startTime, Date endTime) throws SQLException {
		String sql="select VR_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_PEAK_,PROFIT_VALLEY_,PROFIT_BET_LEN_ from vr_member_profit"
				+ " where STATE_=? and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		return super.findKeyMap(sql,parameters,"VR_MEMBER_ID_");
	}
	/**
	 * 获取盈亏信息
	 * @param startTime
	 * @param endTime
	 */
	public List<Map<String, Object>> getHistoryProfit(Date startTime, Date endTime) throws SQLException {
		String sql="select VR_MEMBER_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_PEAK_,PROFIT_VALLEY_,PROFIT_BET_LEN_ from vr_member_profit"
				+ " where STATE_=? and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<?";
		List<Object> parameters=new ArrayList<>();
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		return super.findMapList(sql,parameters);
	}

	/**
	 * 获取当天盈亏信息
	 * @param vrMemberId	虚拟会员id
	 * @param startTime	开始时间
	 * @return
	 */
	public Map<String, Object> findThatDayProfit(String vrMemberId,Date startTime) throws SQLException {
		String sql="select VR_MEMBER_PROFIT_ID_,PROFIT_T_,BET_FUNDS_T_,BET_LEN_,PROFIT_PEAK_,PROFIT_VALLEY_"
				+ ",PROFIT_BET_LEN_,LOSS_BET_LEN_ from vr_member_profit where VR_MEMBER_ID_=? and STATE_=? and CREATE_TIME_LONG_>? ";
		List<Object> parameters=new ArrayList<>();
		parameters.add(vrMemberId);
		parameters.add(IbmStateEnum.OPEN.name());
		parameters.add(startTime.getTime());
		return super.findMap(sql,parameters);
	}

	/**
	 * 更新盈亏信息
	 * @param thatDayProfit	盈亏信息
	 */
	public void updateProfit(Map<String, Object> thatDayProfit) throws SQLException {
		String sql="update vr_member_profit set PROFIT_T_=?,BET_FUNDS_T_=?,BET_LEN_=?,PROFIT_PEAK_=?,PROFIT_VALLEY_=?,"
				+ "PROFIT_BET_LEN_=?,LOSS_BET_LEN_=? where VR_MEMBER_PROFIT_ID_=?";
		List<Object> parameters=new ArrayList<>(8);
		parameters.add(thatDayProfit.get("PROFIT_T_"));
		parameters.add(thatDayProfit.get("BET_FUNDS_T_"));
		parameters.add(thatDayProfit.get("BET_LEN_"));
		parameters.add(thatDayProfit.get("PROFIT_PEAK_"));
		parameters.add(thatDayProfit.get("PROFIT_VALLEY_"));
		parameters.add(thatDayProfit.get("PROFIT_BET_LEN_"));
		parameters.add(thatDayProfit.get("LOSS_BET_LEN_"));
		parameters.add(thatDayProfit.get("VR_MEMBER_PROFIT_ID_"));
		super.execute(sql,parameters);
	}
}
