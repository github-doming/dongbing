package com.ibm.follow.servlet.server.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.common.tools.CacheTool;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.vr_member_bet_item.service.VrMemberBetItemService;
import com.ibm.follow.servlet.cloud.vr_member_profit.entity.VrMemberProfit;
import com.ibm.follow.servlet.cloud.vr_member_profit.service.VrMemberProfitService;
import com.ibm.follow.servlet.cloud.vr_member_profit_period.service.VrMemberProfitPeriodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 结算虚拟投注项线程
 * @Author: null
 * @Date: 2020-07-18 14:54
 * @Version: v1.0
 */
public class SettleVrBetItemThread extends BaseCommThread {
	private static final Logger log = LogManager.getLogger(SettleVrBetItemThread.class);
	private GameUtil.Code gameCode;
	private Object period;
	private HandicapUtil.Code handicapCode;
	private JSONObject drawInfo;

	public SettleVrBetItemThread(GameUtil.Code gameCode, Object period,HandicapUtil.Code handicapCode,JSONObject drawInfo) {
		this.gameCode = gameCode;
		this.period = period;
		this.handicapCode=handicapCode;
		this.drawInfo=drawInfo;
	}

	@Override public String execute(String ignore) throws Exception {
		Date nowTime = new Date();
		List<String> handicapCodes= HandicapGameUtil.handicapCodes(gameCode,handicapCode);
		if (ContainerTool.isEmpty(handicapCodes)) {
			log.info("游戏【{}】，未结算的盘口信息为空",gameCode.name());
			return null;
		}

		VrMemberBetItemService memberBetItemService=new VrMemberBetItemService();
		Map<String, List<Map<String, Object>>> betItemMap=memberBetItemService.mapBetItemInfo(gameCode,period,handicapCodes);
		if (ContainerTool.isEmpty(betItemMap)) {
			return null;
		}
		Map<String,Map<String,Object>> profitInfos=new HashMap<>(betItemMap.size());
		//获取该游戏的赔率信息
		Map<String, Integer> oddsMap = CacheTool.odds(gameCode);

		VrMemberProfitService memberProfitService=new VrMemberProfitService();
		//盈亏信息结算
		for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
			// 投注项
			updateResult(entry.getValue(), oddsMap);
			// 结算盈亏
			betProfit(entry.getValue(),entry.getKey(),profitInfos,nowTime,memberProfitService);
		}
		if(ContainerTool.notEmpty(profitInfos)){
			new VrMemberProfitPeriodService().saveSettlement(profitInfos,gameCode,period,nowTime);
		}

		return null;
	}
	/**
	 * 统计盈亏
	 * @param betInfos			投注信息
	 * @param vrMemberId			虚拟会员id
	 * @param profitInfos			游戏盈亏信息
	 * @param nowTime				当前时间
	 * @param memberProfitService	服务类
	 */
	private void betProfit(List<Map<String, Object>> betInfos, String vrMemberId,Map<String,Map<String,Object>> profitInfos,
						   Date nowTime, VrMemberProfitService memberProfitService) throws Exception {
		long sumProfitTh = 0, betFundsTh = 0, betLen = 0;
		int profitBetLen = 0, lossBetLen = 0;
		long profitTh, fundTh, betContentLen;
		for(Map<String,Object> betInfo:betInfos){
			//核算盈亏
			profitTh = NumberTool.getLong(betInfo.get("PROFIT_T_"));
			fundTh = NumberTool.getLong(betInfo.get("FUND_T_"));
			betContentLen = NumberTool.getLong(betInfo.get("BET_CONTENT_LEN_"));
			//核算每期盈亏
			sumProfitTh += profitTh;
			betFundsTh += fundTh;
			betLen += betContentLen;
			if (profitTh > 0) {
				profitBetLen += betContentLen;
			} else {
				lossBetLen += betContentLen;
			}
		}
		Map<String,Object> profitInfo=new HashMap<>(5);
		profitInfo.put("profitTh",sumProfitTh);
		profitInfo.put("betFundsTh",betFundsTh);
		profitInfo.put("betLen",betLen);
		profitInfo.put("profitBetLen",profitBetLen);
		profitInfo.put("lossBetLen",lossBetLen);
		profitInfos.put(vrMemberId,profitInfo);

		Map<String,Object> thatDayProfit=memberProfitService.findThatDayProfit(vrMemberId,getStateTime());
		if(ContainerTool.isEmpty(thatDayProfit)){
			VrMemberProfit memberProfit=new VrMemberProfit();
			memberProfit.setVrMemberId(vrMemberId);
			memberProfit.setProfitT(sumProfitTh);
			memberProfit.setBetFundsT(betFundsTh);
			memberProfit.setBetLen(betLen);
			memberProfit.setProfitPeak(sumProfitTh);
			memberProfit.setProfitValley(sumProfitTh);
			memberProfit.setProfitBetLen(profitBetLen);
			memberProfit.setLossBetLen(lossBetLen);
			memberProfit.setCreateTime(nowTime);
			memberProfit.setCreateTimeLong(nowTime.getTime()+60*1000L);
			memberProfit.setUpdateTime(nowTime);
			memberProfit.setUpdateTimeLong(nowTime.getTime());
			memberProfit.setState(IbmStateEnum.OPEN.name());
			memberProfitService.save(memberProfit);
		}else{
			thatDayProfit.put("PROFIT_T_",NumberTool.getLong(thatDayProfit.get("PROFIT_T_"))+sumProfitTh);
			thatDayProfit.put("BET_FUNDS_T_",NumberTool.getLong(thatDayProfit.get("BET_FUNDS_T_"))+betFundsTh);
			thatDayProfit.put("BET_LEN_",NumberTool.getInteger(thatDayProfit.get("BET_LEN_"))+betLen);
			//盈亏峰值
			if(NumberTool.getLong(thatDayProfit.get("PROFIT_T_"))>NumberTool.getLong(thatDayProfit.get("PROFIT_PEAK_"))){
				thatDayProfit.put("PROFIT_PEAK_",thatDayProfit.get("PROFIT_T_"));
			}
			//盈亏谷值
			if(NumberTool.getLong(thatDayProfit.get("PROFIT_T_"))<NumberTool.getLong(thatDayProfit.get("PROFIT_VALLEY_"))){
				thatDayProfit.put("PROFIT_VALLEY_",thatDayProfit.get("PROFIT_T_"));
			}

			thatDayProfit.put("PROFIT_BET_LEN_",NumberTool.getInteger(thatDayProfit.get("PROFIT_BET_LEN_"))+profitBetLen);
			thatDayProfit.put("LOSS_BET_LEN_",NumberTool.getInteger(thatDayProfit.get("LOSS_BET_LEN_"))+lossBetLen);
			memberProfitService.updateProfit(thatDayProfit);
		}
	}

	/**
	 * 结算投注信息
	 * @param betInfos					投注信息
	 * @param oddsMap						赔率信息
	 */
	private void updateResult(List<Map<String, Object>> betInfos, Map<String, Integer> oddsMap) {
		double profit, odds, funds;
		String drawItem=drawInfo.getString("drawItem");
		for (Map<String, Object> betInfo : betInfos) {
			profit = 0d;
			String betContent = betInfo.get("BET_CONTENT_").toString();
			String[] betItems = betContent.split("#");
			for (String betItem : betItems) {
				//获取投注信息
				String fundsStr = betItem.substring(betItem.lastIndexOf("|") + 1);
				betItem = betItem.substring(0, betItem.lastIndexOf("|"));
				//获取赔率
				if(!oddsMap.containsKey(betItem)){
					continue;
				}
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
			}
			betInfo.put("PROFIT_T_", NumberTool.longValueT(profit));
		}
	}

	private Date getStateTime() throws ParseException {
		Date nowTime = new Date();
		Date startTime = DateTool.getBetTime(DateTool.getDate(DateTool.getDayStart(nowTime)));
		// 当前时间在5:30之前 查询的是昨天05:30-今天05:30
		if(DateTool.compare(nowTime,startTime)){
			startTime= DateTool.getBetTime(DateTool.getDate(DateTool.getYesterdayStart()));
		}
		return startTime;
	}
}
