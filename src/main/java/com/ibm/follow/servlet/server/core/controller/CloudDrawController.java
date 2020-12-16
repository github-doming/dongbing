package com.ibm.follow.servlet.server.core.controller;


import com.alibaba.fastjson.JSONObject;
import com.common.service.CacheService;
import com.common.tools.CacheTool;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit.service.IbmHmProfitService;
import com.ibm.follow.servlet.cloud.ibm_hm_profit_period.service.IbmHmProfitPeriodService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 中心端开奖信息控制器
 * @Author: null
 * @Date: 2020-07-10 10:54
 * @Version: v1.0
 */
public class CloudDrawController extends BaseCommThread {
	private static final Logger log = LogManager.getLogger(CloudDrawController.class);
	private GameUtil.Code gameCode;
	private Object period;
	private String drawType;
	private String drawNumber;
	private HandicapUtil.Code handicapCode;
	private JSONObject drawInfo;
	public CloudDrawController(GameUtil.Code gameCode, Object period, String drawType,
							   HandicapUtil.Code handicapCode, String drawNumber,JSONObject drawInfo) {
		this.gameCode = gameCode;
		this.period = period;
		this.drawType = drawType;
		this.drawNumber = drawNumber;
		this.handicapCode=handicapCode;
		this.drawInfo=drawInfo;
	}
	@Override
	public String execute(String inVar) throws Exception {
		Date nowTime = new Date();
		String drawTableName=gameCode.getGameFactory().period(handicapCode).getDrawTableName();
		//保存开奖数据
		new CacheService().save(drawType,drawInfo,drawTableName,nowTime);
		//重启事务
		CurrentTransaction.transactionCommit();

		//获取相同类型的盘口code
		List<String> handicapIds= HandicapGameUtil.handicapIds(gameCode,handicapCode);
		if (ContainerTool.isEmpty(handicapIds)) {
			log.info("游戏【{}】，未结算的盘口信息为空",gameCode.name());
			return null;
		}
		String gameId = GameUtil.id(gameCode);
		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		//获取上一期投注信息
		IbmHmBetItemService betItemService = new IbmHmBetItemService();
		Map<String, List<Map<String, Object>>> betItemMap=betItemService.mapBetItemInfo(gameId,period,handicapIds);
		if (ContainerTool.isEmpty(betItemMap)) {
			return null;
		}
		//盈亏信息容器
		Map<String,Map<String,Object>> hmProfitInfos=new HashMap<>(betItemMap.size());
		Map<String,Map<String,Object>> hmVrProfitInfos=new HashMap<>(betItemMap.size());
		//获取该游戏的赔率信息
		Map<String, Integer> oddsMap = CacheTool.odds(gameCode);

		//盈亏信息结算
		for (Map.Entry<String, List<Map<String, Object>>> entry : betItemMap.entrySet()) {
			// 更新投注项
			updateResult(betItemService, entry.getValue(), oddsMap, drawInfo.getString("drawItem"), drawNumber,
					nowTime);
			// 结算盈亏
			betProfit(entry.getValue(), entry.getKey(),hmProfitInfos,hmVrProfitInfos);
		}
		//批量修改结算盈亏信息
		if(ContainerTool.notEmpty(hmProfitInfos)){
			new IbmHmProfitService().updateSettlement(hmProfitInfos,nowTime, IbmTypeEnum.REAL);
			new IbmHmProfitPeriodService().saveSettlement(hmProfitInfos, gameId, period, nowTime, IbmTypeEnum.REAL);
		}
		if(ContainerTool.notEmpty(hmVrProfitInfos)){
			new IbmHmProfitService().updateSettlement(hmProfitInfos,nowTime, IbmTypeEnum.VIRTUAL);
			new IbmHmProfitPeriodService().saveSettlement(hmProfitInfos, gameId, period, nowTime, IbmTypeEnum.VIRTUAL);
		}
		return null;
	}
	/**
	 * 统计盈亏
	 * @param betInfos			投注信息
	 * @param handicapMemberId	盘口会员主键
	 * @param hmProfitInfos		真实盈亏信息
	 * @param hmVrProfitInfos		模拟盈亏信息
	 * @throws Exception
	 */
	private void betProfit(List<Map<String, Object>> betInfos, String handicapMemberId,
						   Map<String, Map<String, Object>> hmProfitInfos, Map<String, Map<String, Object>> hmVrProfitInfos) {
		/*
			1.更新方案盈亏信息
			2.更新每一期盈亏信息
			3.更新总盈亏信息
		 */
		long sumProfitTh = 0, betFundsTh = 0, betLen = 0, profitBetLen = 0, lossBetLen = 0;
		long sumProfitThVr = 0, betFundsThVr = 0, betLenVr = 0, profitBetLenVr = 0, lossBetLenVr = 0;
		long profitTh, fundTh, betContentLen;
		Iterator<Map<String, Object>> iterator= betInfos.iterator();
		while (iterator.hasNext()){
			Map<String, Object> betInfo=iterator.next();
			//核算盈亏
			profitTh = NumberTool.getLong(betInfo.get("PROFIT_T_"));
			fundTh = NumberTool.getLong(betInfo.get("FUND_T_"));
			betContentLen = NumberTool.getLong(betInfo.get("BET_CONTENT_LEN_"));
			if (IbmTypeEnum.VIRTUAL.name().equals(betInfo.remove("BET_MODE_"))) {
				//添加虚拟区域
				iterator.remove();
				//核算每期盈亏
				sumProfitThVr += profitTh;
				betFundsThVr += fundTh;
				betLenVr += betContentLen;
				if (profitTh > 0) {
					profitBetLenVr += betContentLen;
				} else {
					lossBetLenVr += betContentLen;
				}
			} else {
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
		}
		if (betLen != 0) {
			Map<String,Object> profitInfo=new HashMap<>(5);
			profitInfo.put("profitTh",sumProfitTh);
			profitInfo.put("betFundsTh",betFundsTh);
			profitInfo.put("betLen",betLen);
			profitInfo.put("profitBetLen",profitBetLen);
			profitInfo.put("lossBetLen",lossBetLen);
			hmProfitInfos.put(handicapMemberId,profitInfo);
		}
		if (betLenVr != 0) {
			Map<String,Object> profitInfo=new HashMap<>(5);
			profitInfo.put("profitTh",sumProfitThVr);
			profitInfo.put("betFundsTh",betFundsThVr);
			profitInfo.put("betLen",betLenVr);
			profitInfo.put("profitBetLen",profitBetLenVr);
			profitInfo.put("lossBetLen",lossBetLenVr);
			hmVrProfitInfos.put(handicapMemberId,profitInfo);
		}
	}

	/**
	 * 结算投注信息
	 * @param betItemService		服务类
	 * @param betInfos			投注信息
	 * @param oddsMap				赔率
	 * @param drawItem			开奖结果信息
	 * @param drawNumber			开奖号码
	 * @param nowTime				当前时间
	 */
	private void updateResult(IbmHmBetItemService betItemService, List<Map<String, Object>> betInfos,
							  Map<String, Integer> oddsMap, String drawItem, String drawNumber, Date nowTime) throws SQLException {
		double profit, odds, funds;
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
		betItemService.updateResult(betInfos, drawNumber, nowTime);
	}
}
