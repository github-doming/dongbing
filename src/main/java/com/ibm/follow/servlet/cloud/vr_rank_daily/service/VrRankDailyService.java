package com.ibm.follow.servlet.cloud.vr_rank_daily.service;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_rank_daily.entity.VrRankDaily;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * VR_每日榜单 服务类
 *
 * @author Robot
 */
public class VrRankDailyService extends BaseServiceProxy {

	/**
	 * 保存VR_每日榜单 对象数据
	 *
	 * @param entity VrRankDaily对象数据
	 */
	public String save(VrRankDaily entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除vr_rank_daily 的 VR_RANK_DAILY_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update vr_rank_daily set state_='DEL' where VR_RANK_DAILY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除VR_RANK_DAILY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 vr_rank_daily 的 VR_RANK_DAILY_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update vr_rank_daily set state_='DEL' where VR_RANK_DAILY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 vr_rank_daily  的 VR_RANK_DAILY_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from vr_rank_daily where VR_RANK_DAILY_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除VR_RANK_DAILY_ID_主键id数组的数据
	 *
	 * @param idArray 要删除vr_rank_daily 的 VR_RANK_DAILY_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from vr_rank_daily where VR_RANK_DAILY_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新VrRankDaily实体信息
	 *
	 * @param entity VR_每日榜单 实体
	 */
	public void update(VrRankDaily entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据vr_rank_daily表主键查找 VR_每日榜单 实体
	 *
	 * @param id vr_rank_daily 主键
	 * @return VR_每日榜单 实体
	 */
	public VrRankDaily find(String id) throws Exception {
		return dao.find(VrRankDaily.class, id);
	}

	/**
	 * 获取历史排名信息
	 *
	 * @param vrMemberId 虚拟会员id
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 * @return
	 */
	public Map<String, Object> getHistoryRank(String vrMemberId, Date startTime, Date endTime) throws SQLException {
		String sql = "select VR_USER_NAME_,VR_MEMBER_ACCOUNT_,HANDICAP_CODE_,RANKING_ from vr_rank_daily where VR_MEMBER_ID_=?"
				+ " and CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<? and STATE_=?";
		List<Object> parameters = new ArrayList<>();
		parameters.add(vrMemberId);
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMap(sql, parameters);
	}

	/**
	 * 获取排名信息
	 *
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return
	 */
	public List<Map<String, Object>> getRank(Date startTime, Date endTime) throws SQLException {
		String sql = "select VR_MEMBER_ID_,VR_USER_NAME_,VR_MEMBER_ACCOUNT_,HANDICAP_CODE_,PROFIT_PEAK_,PROFIT_VALLEY_,BET_LEN_,"
				+ "WIN_RATE_T_,PROFIT_T_,RANKING_ from vr_rank_daily where CREATE_TIME_LONG_>? and CREATE_TIME_LONG_<? and STATE_=?"
				+ " order by RANKING_ limit 10";
		List<Object> parameters = new ArrayList<>();
		parameters.add(startTime.getTime());
		parameters.add(endTime.getTime());
		parameters.add(IbmStateEnum.OPEN.name());
		return super.dao.findMapList(sql, parameters);
	}

	/**
	 * 批量添加
	 *
	 * @param memberProfits 会员盈亏信息
	 * @param memberInfos   会员信息
	 * @param tableName     表名
	 */
	public void batchSave(Map<String, Map<String, Object>> memberProfits, Map<String, Map<String, Object>> memberInfos, String tableName) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into  ").append(tableName).append("(").append(tableName).append("_ID_,");
		sql.append("VR_MEMBER_ID_,VR_USER_NAME_,VR_MEMBER_ACCOUNT_,HANDICAP_CODE_,PROFIT_PEAK_,"
				+ "PROFIT_VALLEY_,BET_LEN_,WIN_RATE_T_,PROFIT_T_,RANKING_,CREATE_TIME_,CREATE_TIME_LONG_,UPDATE_TIME_,"
				+ "UPDATE_TIME_LONG_,STATE_) values");
		List<Object> parameters = new ArrayList<>();
		Date nowTime = new Date();
		Map<String, Object> memberInfo;
		Map<String, Object> memberProfit;
		for (Map.Entry<String, Map<String, Object>> entry : memberProfits.entrySet()) {
			memberInfo = memberInfos.get(entry.getKey());
			memberProfit = entry.getValue();
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),");
			parameters.add(RandomTool.getNumLetter32());
			parameters.add(entry.getKey());
			parameters.add(memberInfo.get("VR_USER_NAME_"));
			parameters.add(memberInfo.get("VR_MEMBER_ACCOUNT_"));
			parameters.add(memberInfo.get("HANDICAP_CODE_"));
			parameters.add(NumberTool.doubleT(memberProfit.get("PROFIT_PEAK_")));
			parameters.add(NumberTool.doubleT(memberProfit.get("PROFIT_VALLEY_")));
			parameters.add(memberProfit.get("BET_LEN_"));
			parameters.add(memberProfit.get("WIN_RATE_T_"));
			parameters.add(NumberTool.doubleT(memberProfit.get("PROFIT_T_")));
			parameters.add(memberProfit.get("RANKING_"));
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
