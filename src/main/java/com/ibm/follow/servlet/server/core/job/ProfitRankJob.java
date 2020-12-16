package com.ibm.follow.servlet.server.core.job;

import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_profit.service.VrMemberProfitService;
import com.ibm.follow.servlet.cloud.vr_member_profit_period.service.VrMemberProfitPeriodService;
import com.ibm.follow.servlet.cloud.vr_rank_daily.service.VrRankDailyService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.quartz.JobExecutionContext;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 盈亏排名job
 * @Author: null
 * @Date: 2020-07-17 14:33
 * @Version: v1.0
 */
public class ProfitRankJob extends BaseCommJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		log.info("每天盈亏排名定时器开始");

		Date endTime = new Date();
		Date startTime = DateTool.getBeforeDays(endTime, 1);

		//获取盘口游戏名称
		Map<String, Map<String, Object>> handicapGameInfos = new IbmHandicapGameService().findHandicapGameNames();
		//每日总盈亏排名
		VrMemberProfitService memberProfitService = new VrMemberProfitService();
		VrMemberService memberService = new VrMemberService();

		VrRankDailyService rankDailyService=new VrRankDailyService();
		Map<String, Map<String, Object>> memberInfo;
		Map<String, Map<String, Object>> memberProfits = memberProfitService.getYesterdayProfit(startTime, endTime);
		if (ContainerTool.notEmpty(memberProfits)) {
			//获取会员信息
			memberInfo = memberService.findMemberInfo(memberProfits.keySet());
			//盈亏金额排序
			profitRank(memberProfits);
			//保存每日排名
			rankDailyService.batchSave(memberProfits, memberInfo,"vr_rank_daily");
			//每日游戏盈亏排名
			profitGame(handicapGameInfos, memberProfits, memberInfo, startTime, endTime);
		}
		//每周榜单
		//当日0点时间
		Date nowTime = DateTool.getDayStart();

		List<Map<String, Object>> memberProfitList;
		//本周一时间
		Date mondayTime = DateTool.getThisWeekMonday(nowTime);
		//时间等于周一时间才进行每周榜单排名
		if (nowTime.getTime() == mondayTime.getTime()) {
			//上周开始时间
			Date lastWeekTime = DateTool.geLastWeekMonday(nowTime);
			memberProfitList = memberProfitService.getHistoryProfit(lastWeekTime, nowTime);
			if (ContainerTool.notEmpty(memberProfitList)) {
				memberProfits=new HashMap<>(8);
				//统计盈亏
				profitStatistics(memberProfitList,memberProfits);
				//获取会员信息
				memberInfo = memberService.findMemberInfo(memberProfits.keySet());
				//盈亏金额排序
				profitRank(memberProfits);
				//保存每周排名
				rankDailyService.batchSave(memberProfits, memberInfo,"vr_rank_weekly");
			}
		}

		//每月榜单
		//当月一号时间
		Date monthTime = DateTool.getMonthStart();
		if (nowTime.getTime() == monthTime.getTime()) {
			//上月开始时间
			Date lastMonthTime = DateTool.getLastMonthStart();
			memberProfitList = memberProfitService.getHistoryProfit(lastMonthTime, nowTime);
			if (ContainerTool.notEmpty(memberProfitList)) {
				memberProfits=new HashMap<>(8);
				//统计盈亏
				profitStatistics(memberProfitList,memberProfits);
				//获取会员信息
				memberInfo = memberService.findMemberInfo(memberProfits.keySet());
				//盈亏金额排序
				profitRank(memberProfits);
				//保存每月排名
				rankDailyService.batchSave(memberProfits, memberInfo,"vr_rank_monthly");
			}
		}

	}

	private void profitStatistics(List<Map<String, Object>> memberProfitList, Map<String, Map<String, Object>> memberProfits) {
		for(Map<String,Object> memberProfitInfo:memberProfitList){
			String vrMemberId=memberProfitInfo.remove("VR_MEMBER_ID_").toString();
			if(memberProfits.containsKey(vrMemberId)){
				Map<String,Object> map=memberProfits.get(vrMemberId);
				long profitT=NumberTool.getLong(memberProfitInfo.get("PROFIT_T_"));
				long betLen=NumberTool.getInteger(memberProfitInfo.get("BET_LEN_"));
				long profitBetLen=NumberTool.getInteger(memberProfitInfo.get("PROFIT_BET_LEN_"));

				map.put("PROFIT_T_",NumberTool.getLong(map.get("PROFIT_T_"))+profitT);

				if(NumberTool.getLong(map.get("PROFIT_PEAK_"))<NumberTool.getLong(memberProfitInfo.get("PROFIT_PEAK_"))){
					map.put("PROFIT_PEAK_",memberProfitInfo.get("PROFIT_PEAK_"));
				}
				if(NumberTool.getLong(map.get("PROFIT_VALLEY_"))>NumberTool.getLong(memberProfitInfo.get("PROFIT_VALLEY_"))){
					map.put("PROFIT_VALLEY_",memberProfitInfo.get("PROFIT_VALLEY_"));
				}
				map.put("BET_LEN_",NumberTool.getInteger(map.get("BET_LEN_"))+betLen);
				map.put("PROFIT_BET_LEN_",NumberTool.getInteger(map.get("PROFIT_BET_LEN_"))+profitBetLen);
			}else{
				memberProfits.put(vrMemberId,memberProfitInfo);
			}
		}
	}

	private void profitGame(Map<String, Map<String, Object>> handicapGameInfos, Map<String, Map<String, Object>> memberProfits,
							Map<String, Map<String, Object>> memberInfo, Date startTime, Date endTime) throws SQLException {
		List<Map<String, Object>> vrMemberProfitGame = new ArrayList<>();
		VrMemberProfitPeriodService profitPeriodService = new VrMemberProfitPeriodService();
		Map<String, List<Map<String, Object>>> gameProfits;
		for (String vrMemberId : memberProfits.keySet()) {
			String handicapCode = memberInfo.get(vrMemberId).get("HANDICAP_CODE_").toString();
			Map<String, Object> gameInfos = handicapGameInfos.get(handicapCode);

			gameProfits = profitPeriodService.getYesterdayProfitInfo(vrMemberId, startTime, endTime);
			gameProfits.forEach((gameCode, list) -> {
				Object gameName = gameInfos.get(gameCode);
				Map<String, Object> map = new HashMap<>(8);
				//投注数
				int betLen = 0;
				//最大盈利
				long profitMax = 0L;
				//最大亏损
				long lossMax = 0L;
				//盈亏金额
				long profitT = 0L;
				//盈亏投注数
				double profitBetLen = 0;
				for (Map<String, Object> profitInfo : list) {
					long profit = NumberTool.getLong(profitInfo.get("PROFIT_T_"));
					if (profit > profitMax) {
						profitMax = profit;
					}
					if (profit < lossMax) {
						lossMax = profit;
					}
					profitT += profit;
					betLen += NumberTool.getInteger(profitInfo.get("BET_LEN_"));
					profitBetLen += NumberTool.getInteger(profitInfo.get("PROFIT_BET_LEN_"));
				}
				long winRateT = NumberTool.longValueT(100 * profitBetLen / betLen);

				map.put("vrMemberId", vrMemberId);
				map.put("gameName", gameName);
				map.put("gameCode", gameCode);
				map.put("betLen", betLen);
				map.put("profitMax", profitMax);
				map.put("lossMax", lossMax);
				map.put("winRateT", winRateT);
				map.put("profitT", profitT);

				vrMemberProfitGame.add(map);
			});
		}
		profitPeriodService.batchSave(vrMemberProfitGame);
	}


	private void profitRank(Map<String, Map<String, Object>> memberProfits) {
		List<Long> profits = new ArrayList<>();
		for (Map.Entry<String, Map<String, Object>> entry : memberProfits.entrySet()) {
			Map<String, Object> profitInfo=entry.getValue();
			int betLen = NumberTool.getInteger(profitInfo.get("BET_LEN_"));
			double profitBetLen=NumberTool.getInteger(profitInfo.get("PROFIT_BET_LEN_"));
			long winRateT = NumberTool.longValueT(100 * profitBetLen / betLen);

			profitInfo.put("WIN_RATE_T_",winRateT);
			profits.add(NumberTool.getLong(profitInfo.get("PROFIT_T_")));
		}
		//从小到大排序
		Collections.sort(profits);

		for (Map.Entry<String, Map<String, Object>> entry : memberProfits.entrySet()) {
			Map<String, Object> profitInfo=entry.getValue();
			long profitT = NumberTool.getLong(profitInfo.get("PROFIT_T_"));
			for (int i = 0; i < profits.size(); i++) {
				if (profitT == profits.get(i)) {
					profitInfo.put("RANKING_", profits.size() - i);
				}
			}
		}
	}
}
