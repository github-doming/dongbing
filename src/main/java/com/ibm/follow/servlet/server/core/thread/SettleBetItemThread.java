package com.ibm.follow.servlet.server.core.thread;

import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_item.service.IbmHmProfitItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.entity.IbmHmProfitPeriod;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service.IbmHmProfitPeriodService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.entity.IbmHmProfitPeriodVr;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period_vr.service.IbmHmProfitPeriodVrService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_vr.service.IbmHmProfitVrService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 结算投注项线程
 * @Author: Dongming
 * @Date: 2019-10-10 18:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleBetItemThread extends BaseCommThread {
	private Object period;
	private String handicapId;
	private String drawNumber;
	private String drawItem;
	private Map<String, Integer> oddsMap;
	private GameUtil.Code game;

	public SettleBetItemThread(String handicapId, Object period, String drawNumber, String drawItem,
			Map<String, Integer> oddsMap, GameUtil.Code game) {
		this.handicapId = handicapId;
		this.period = period;
		this.drawNumber = drawNumber;
		this.drawItem = drawItem;
		this.oddsMap = oddsMap;
		this.game = game;

	}
	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;
		HandicapUtil.Code handicap =HandicapUtil.code(handicapId);
		try {
			String tableName = "ibm_hm_bet_item";
			Date nowTime = new Date();
			StringBuilder oddsStr = new StringBuilder();
			IbmHmBetItemService betItemService = new IbmHmBetItemService();
			Map<String, List<Map<String, Object>>> betItemMap = betItemService.mapBetItemInfo(handicapId, period, IbmTypeEnum.REAL,tableName);

			// 真实数据结算
			for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
				// 更新投注项
				updateResult(betItemService, entry.getValue(), oddsStr, tableName, nowTime);
				// 结算盈亏
				betProfit(entry.getValue(), entry.getKey(), nowTime);
			}

			// 模拟数据结算
			betItemMap = betItemService.mapBetItemInfo(handicapId, period, IbmTypeEnum.VIRTUAL,tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
				// 更新投注项
				updateResult(betItemService, entry.getValue(), oddsStr, tableName, nowTime);
				// 结算盈亏
				betProfitVr(entry.getValue(), entry.getKey(), nowTime);
			}
		} finally {
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏已结算完成");
		}
		return null;
	}

	/**
	 * 更新结算信息
	 *
	 * @param betItemService 投注项服务类
	 * @param betItemList    投注项列表
	 * @param oddsStr        赔率字符串
	 * @param tableName      表名
	 * @param nowTime        更新时间
	 */
	private void updateResult(IbmHmBetItemService betItemService, List<Map<String, Object>> betItemList,
			StringBuilder oddsStr, String tableName, Date nowTime) throws SQLException {
		double profit;
		double funds;
		double odds;
		for (Map<String, Object> betItemInfo : betItemList) {
			profit = 0d;
			oddsStr.delete(0, oddsStr.length());
			String betContent = betItemInfo.get("BET_CONTENT_").toString();
			String[] betItems = betContent.split("#");
			for (String betItem : betItems) {
				//获取投注信息
				String fundsStr = betItem.substring(betItem.lastIndexOf("|") + 1);
				betItem = betItem.substring(0, betItem.lastIndexOf("|"));
				//获取赔率
				odds = oddsMap.get(betItem) / 1000D;
				if (odds - 0 == 0) {
					//赔率异常
					continue;
				}
				//算出积分
				funds = NumberTool.doubleT(fundsStr);
				if (drawItem.concat(",").contains(betItem.concat(","))) {
					profit += funds * (odds - 1);
				} else {
					profit -= funds;
				}
				betItemInfo.put("PROFIT_T_", NumberTool.longValueT(profit));
				betItemInfo.put("ODDS_", oddsStr.toString());
			}
			betItemService.updateResult(betItemInfo, drawNumber, tableName, nowTime);
		}
	}

	/**
	 * 投注盈亏结算
	 *
	 * @param betItemList      投注项列表
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          结算时间
	 */
	private void betProfit(List<Map<String, Object>> betItemList, String handicapMemberId, Date nowTime)
			throws Exception {
		IbmHmProfitService profitService = new IbmHmProfitService();
		IbmHmProfitItemService profitItemService = new IbmHmProfitItemService();
        IbmHmProfitPeriodService profitPeriodService=new IbmHmProfitPeriodService();
		//盈亏总额
		long profit = 0;
		//投注总额
		long fund = 0;
		//投注总数
		int betTotal = 0;
		//盈利投注数
        int profitBetLen=0;
        //亏损投注数
        int lossBetLen=0;

		for (Map<String, Object> betItemInfo : betItemList) {
			int betLen = NumberTool.getInteger(betItemInfo.get("BET_CONTENT_LEN_"));
			long profitTh = NumberTool.getLong(betItemInfo.get("PROFIT_T_"));
			long fundTh = NumberTool.getLong(betItemInfo.get("FUND_T_"));
			profit += profitTh;
			betTotal += betLen;
			fund += fundTh;
			if(fundTh>0){
                profitBetLen++;
            }else{
                lossBetLen++;
            }
			//盘口会员盈亏详情
			profitItemService.save(handicapMemberId, betItemInfo, profitTh, fundTh, nowTime);
		}
        //盘口会员当期盈亏
        IbmHmProfitPeriod profitPeriod=profitPeriodService.findByHmIdAndPeriod(handicapMemberId,period.toString());
		if(profitPeriod==null){
            profitPeriodService.save(handicapMemberId, period, profit, fund, betTotal, nowTime,profitBetLen,lossBetLen);
        }else{
            profitPeriod.setProfitT(profit);
            profitPeriod.setBetFundsT(fund);
            profitPeriod.setBetLen(betTotal);
            profitPeriod.setProfitBetLen(profitBetLen);
            profitPeriod.setLossBetLen(lossBetLen);
            profitPeriod.setUpdateTimeLong(System.currentTimeMillis());
            profitPeriodService.update(profitPeriod);
        }

		//盘口会员软件总盈亏
		String profitId = profitService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitId)) {
			Map<String, Object> limitInfo = new IbmHmSetService().findLimitInfoByHMId(handicapMemberId);
			profitService.save(limitInfo, handicapMemberId,profit, fund, betTotal, nowTime);
		} else {
			profitService.update(profitId, profit, fund, betTotal, nowTime);
		}
	}

	/**
	 * 投注盈亏结算
	 *
	 * @param betItemList      投注项列表
	 * @param handicapMemberId 盘口会员主键
	 * @param nowTime          结算时间
	 */
	private void betProfitVr(List<Map<String, Object>> betItemList, String handicapMemberId, Date nowTime)
			throws Exception {
		IbmHmProfitVrService profitService = new IbmHmProfitVrService();
        IbmHmProfitPeriodVrService profitPeriodVrService=new IbmHmProfitPeriodVrService();
		//盈亏总额
		long profit = 0;
		//投注总额
		long fund = 0;
		//投注总数
		int betTotal = 0;
        //盈利投注数
        int profitBetLen=0;
        //亏损投注数
        int lossBetLen=0;

		for (Map<String, Object> betItemInfo : betItemList) {
			int betLen = NumberTool.getInteger(betItemInfo.get("BET_CONTENT_LEN_"));
			long profitTh = NumberTool.getLong(betItemInfo.get("PROFIT_T_"));
			long fundTh = NumberTool.getLong(betItemInfo.get("FUND_T_"));
			profit += profitTh;
			betTotal += betLen;
			fund += fundTh;
			if(fundTh>0){
                profitBetLen++;
            }else{
			    lossBetLen++;
            }
		}
        //盘口会员当期盈亏
        IbmHmProfitPeriodVr profitPeriod=profitPeriodVrService.findByHmIdAndPeriod(handicapMemberId,period.toString());
		if(profitPeriod==null){
            profitPeriodVrService.save(handicapMemberId, period, profit, fund, betTotal, nowTime,profitBetLen,lossBetLen);
        }else{
            profitPeriod.setProfitT(profit);
            profitPeriod.setBetFundsT(fund);
            profitPeriod.setBetLen(betTotal);
            profitPeriod.setProfitBetLen(profitBetLen);
            profitPeriod.setLossBetLen(lossBetLen);
            profitPeriod.setUpdateTimeLong(System.currentTimeMillis());
            profitPeriodVrService.update(profitPeriod);
        }

		//盘口会员软件总盈亏
		String profitVrId = profitService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitVrId)) {
			Map<String, Object> limitInfo = new IbmHmSetService().findLimitInfoByHMId(handicapMemberId);
			profitService.save(limitInfo, handicapMemberId, profit, fund, betTotal, nowTime);
		} else {
			profitService.update(profitVrId, profit, fund, betTotal, nowTime);
		}

	}
}
