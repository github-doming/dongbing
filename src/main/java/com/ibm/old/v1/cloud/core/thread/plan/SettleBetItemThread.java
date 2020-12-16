package com.ibm.old.v1.cloud.core.thread.plan;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_bet_result.service.IbmExecBetResultService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_exec_result.t.service.IbmExecResultTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_info.t.entity.IbmProfitInfoT;
import com.ibm.old.v1.cloud.ibm_profit_info.t.service.IbmProfitInfoTService;
import com.ibm.old.v1.cloud.ibm_profit_info_vr.t.entity.IbmProfitInfoVrT;
import com.ibm.old.v1.cloud.ibm_profit_info_vr.t.service.IbmProfitInfoVrTService;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.entity.IbmProfitPlanT;
import com.ibm.old.v1.cloud.ibm_profit_plan.t.service.IbmProfitPlanTService;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.entity.IbmProfitPlanVrT;
import com.ibm.old.v1.cloud.ibm_profit_plan_vr.t.service.IbmProfitPlanVrTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import org.doming.core.common.CurrentTransaction;
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
 * @Date: 2019-04-03 18:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SettleBetItemThread extends BaseCommThread {

	private String gameId;
	private Object period;
	private String handicapId;
	private String drawNumber;
	private String drawItem;
	private Map<Object, Object> oddsMap;
	private IbmGameEnum game;

	public SettleBetItemThread(String gameId, String handicapId, Object period, String drawNumber, String drawItem,
			Map<Object, Object> oddsMap, IbmGameEnum game) {
		this.gameId = gameId;
		this.handicapId = handicapId;
		this.period = period;
		this.drawNumber = drawNumber;
		this.drawItem = drawItem;
		this.oddsMap = oddsMap;
		this.game = game;

	}

	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;
		IbmExecResultTService execResultService = new IbmExecResultTService();
		IbmHandicapEnum handicap = HandicapTool.findCode(handicapId);
		try {
			String result = null;
			for (int i = 0; i < 13; i++) {
				result = execResultService.findExecResult(gameId, handicapId, period);
				if (StringTool.isEmpty(result)) {
					Thread.sleep(1000);
					CurrentTransaction.transactionCommit();
					continue;
				}
				switch (IbmTypeEnum.valueOf(result)) {
					case CODING:
					case TURN:
					case MERGE:
						Thread.sleep(1000);
						CurrentTransaction.transactionCommit();
						continue;
					case SEND:
						break;
					case SETTLE:
						//已经合并过
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏投注信息已结算");
						return null;
					default:
						log.info("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中获取结算错误【" + result + "】");
						return null;
				}
				break;
			}

			if (StringTool.isEmpty(result)) {
				log.trace("盘口【" + handicapId + "】的【" + gameId + "】游戏中结算未完成");
				return null;
			}

			//存储表
			String tableName = HandicapGameTool.getTableName(game.name(), handicap.name());
			if (tableName == null) {
				log.error("盘口【" + handicap.getName() + "】的【" + game.getName() + "】游戏中存储表不存在");
				return null;
			}

			IbmExecBetItemTService execBetItemService = new IbmExecBetItemTService();

			//更新真实的投注处理结果
			Map<String, List<Map<String, Object>>> execBetItemMap = execBetItemService
					.mapExecBetItemInfo(period, tableName);

			StringBuilder oddsStr = new StringBuilder();
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
				// 真实投注盈利
				betProfit(entry.getValue(), entry.getKey());
			}

			//合并的真实的投注项处理结果
			execBetItemMap = execBetItemService.mapMergeExecBetItemInfo(period, tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
				// 真实投注盈利
				betProfit(entry.getValue(), entry.getKey());
			}
			//更新合并后的真实投注处理结果
			execBetItemMap = execBetItemService.mapMergeNewInfo(period, tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
			}

			//更新模拟投注处理结果
			execBetItemMap = execBetItemService.mapVrExecBetItemInfo(period, tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
				// 模拟投注盈利
				betProfitVr(entry.getValue(), entry.getKey());
			}

			//合并的模拟投注项处理结果
			execBetItemMap = execBetItemService.mapMergeVrExecBetItemInfo(period, tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
				// 模拟投注盈利
				betProfitVr(entry.getValue(), entry.getKey());
			}

			//更新合并后的模拟投注处理结果
			execBetItemMap = execBetItemService.mapMergeVrNewInfo(period, tableName);
			for (Map.Entry<String, List<Map<String, Object>>> entry : execBetItemMap.entrySet()) {
				updateResult(execBetItemService, entry.getValue(), oddsStr, tableName);
			}
			return super.execute(ignore);

		} finally {
			execResultService.updateExecResult(IbmTypeEnum.SETTLE, IbmTypeEnum.SEND, handicapId, period,this.getClass().getName());
			codingEnd = System.currentTimeMillis();
			log.debug("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicap.getName() + "】中【" + game.getName()
					+ "】游戏已结算完成");
		}

	}

	/**
	 * 更新处理结果
	 *
	 * @param execBetItemService 投注项修改类
	 * @param execBetItemList    投注项列表
	 * @param oddsStr            赔率
	 * @param tableName          表名
	 */
	private void updateResult(IbmExecBetItemTService execBetItemService, List<Map<String, Object>> execBetItemList,
			StringBuilder oddsStr, String tableName) throws SQLException {
		double profit;
		double funds;
		double odds;
		for (Map<String, Object> execBetItem : execBetItemList) {
			profit = 0d;
			oddsStr.delete(0, oddsStr.length());
			String betContent = execBetItem.get("BET_CONTENT_").toString();
			funds = Math.floor(NumberTool.doubleT(execBetItem.get("FUND_T_")));
			String[] betItems = betContent.split("#");
			for (String betItem : betItems) {
				odds = NumberTool.getDouble(oddsMap.get(betItem));
				if (odds - 0 == 0) {
					continue;
				}
				if (drawItem.concat(",").contains(betItem.concat(","))) {
					profit += funds * (odds - 1);
				} else {
					profit -= funds;
				}
				oddsStr.append(odds).append(",");
			}
			execBetItem.put("PROFIT_T_", NumberTool.longValueT(profit));
			execBetItem.put("ODDS_", oddsStr.toString());
			execBetItemService.updateResult(execBetItem, drawNumber, tableName);
		}
	}

	/**
	 * 真实盈利
	 *
	 * @param execBetItemList 真实盈利列表
	 */
	private void betProfit(List<Map<String, Object>> execBetItemList, String handicapMemberId) throws Exception {
		IbmHmSetT hmSet = new IbmHmSetTService().findByHandicapMemberId(handicapMemberId);

		IbmProfitTService profitService = new IbmProfitTService();
		String profitId = profitService.findIdByHmId(handicapMemberId);
		Date nowTime = new Date();
		if (StringTool.isEmpty(profitId)) {
			profitId = profitService.save(hmSet, handicapMemberId, 0);
		}
		//盘口会员软件总盈亏
		long profit = 0;
		//投注总额
		long fund = 0;
		//投注总数
		int betTotal = 0;

		for (Map<String, Object> execBetItem : execBetItemList) {
			String planId = execBetItem.get("PLAN_ID_").toString();
			long profitTh = NumberTool.getLong(execBetItem.get("PROFIT_T_"));
			long fundTh = NumberTool.getLong(execBetItem.get("FUND_T_"));
			int betLen = NumberTool.getInteger(execBetItem.get("BET_CONTENT_LEN_"));

			//更新执行方案组
			String execResult = profitTh > 0 ? IbmTypeEnum.TRUE.name() : IbmTypeEnum.FALSE.name();
			new IbmExecPlanGroupTService()
					.updateResult(execBetItem.get("EXEC_PLAN_GROUP_ID_").toString(), execResult, nowTime,this.getClass().getName());
			new IbmExecBetResultService().updateResult(gameId,planId,  handicapId, handicapMemberId,
					execBetItem.get("PLAN_ITEM_TABLE_ID_").toString(), execBetItem.get("PLAN_GROUP_KEY_").toString(),
					execBetItem.get("EXEC_PLAN_GROUP_ID_").toString(), execResult, nowTime,this.getClass().getName());

			//投注积分 = 每注积分 * 注数
			long betFundTh = fundTh * betLen;

			//累计软件盈亏
			profit += profitTh;
			//投注积分
			fund += betFundTh;
			//投注总数
			betTotal += betLen;

			//盘口会员方案盈亏
			IbmProfitPlanTService profitPlanService = new IbmProfitPlanTService();
			String profitPlanId = profitPlanService.findId(profitId, planId);
			if (StringTool.isEmpty(profitPlanId)) {
				IbmProfitPlanT profitPlan = new IbmProfitPlanT();
				if ("2".equals(hmSet.getResetType())) {
					profitPlan.setProfitLimitMaxT(hmSet.getResetProfitMaxT());
					profitPlan.setLossLimitMinT(hmSet.getResetLossMinT());
				} else {
					Map<String, Object> limitInfo = new IbmPlanUserTService().getLimitInfo(handicapMemberId, planId);
					profitPlan.setProfitLimitMaxT(limitInfo.get("PROFIT_LIMIT_MAX_T_"));
					profitPlan.setLossLimitMinT(limitInfo.get("LOSS_LIMIT_MIN_T_"));
				}
				profitPlan.setProfitId(profitId);
				profitPlan.setPlanId(planId);
				profitPlan.setHandicapMemberId(handicapMemberId);
				profitPlan.setProfitT(profitTh);
				profitPlan.setBetFundsT(betFundTh);
				profitPlan.setBetLen(betLen);
				profitPlan.setCreateTime(nowTime);
				profitPlan.setCreateTimeLong(nowTime.getTime());
				profitPlan.setState(IbmStateEnum.OPEN.name());
				profitPlanId = profitPlanService.save(profitPlan);
			} else {
				profitPlanService.updateProfit(profitPlanId, profitTh, betFundTh, betLen, nowTime,this.getClass().getName());
			}

			//盘口会员盈亏信息
			IbmProfitInfoT profitInfo = new IbmProfitInfoT();
			profitInfo.setProfitPlanId(profitPlanId);
			profitInfo.setExecBetItemId(execBetItem.get("EXEC_BET_ITEM_ID_"));
			profitInfo.setHandicapMemberId(handicapMemberId);
			profitInfo.setPeriod(period);
			profitInfo.setProfitT(profitTh);
			profitInfo.setBetFundsT(betFundTh);
			profitInfo.setBetLen(betLen);
			profitInfo.setCreateTime(nowTime);
			profitInfo.setCreateTimeLong(nowTime.getTime());
			profitInfo.setState(IbmStateEnum.OPEN.name());
			new IbmProfitInfoTService().save(profitInfo);
		}
		profitService.updateProfit(profitId, profit, fund, betTotal, nowTime,this.getClass().getName());

	}
	/**
	 * 模拟盈利
	 *
	 * @param execBetItemList 模拟盈利列表
	 */
	private void betProfitVr(List<Map<String, Object>> execBetItemList, String handicapMemberId) throws Exception {
		IbmHmSetT hmSet = new IbmHmSetTService().findByHandicapMemberId(handicapMemberId);
		Date nowTime = new Date();
		IbmProfitVrTService profitVrService = new IbmProfitVrTService();
		String profitVrId = profitVrService.findIdByHmId(handicapMemberId);
		if (StringTool.isEmpty(profitVrId)) {
			profitVrId = profitVrService.save(hmSet, handicapMemberId);
		}

		for (Map<String, Object> execBetItem : execBetItemList) {
			String planId = execBetItem.get("PLAN_ID_").toString();
			long profitTh = NumberTool.getLong(execBetItem.get("PROFIT_T_"));
			long fundTh = NumberTool.getLong(execBetItem.get("FUND_T_"));
			int betLen = NumberTool.getInteger(execBetItem.get("BET_CONTENT_LEN_"));

			//更新执行方案组
			String execResult = profitTh > 0 ? IbmTypeEnum.TRUE.name() : IbmTypeEnum.FALSE.name();
			new IbmExecPlanGroupTService()
					.updateResult(execBetItem.get("EXEC_PLAN_GROUP_ID_").toString(), execResult, nowTime,this.getClass().getName());
			new IbmExecBetResultService()
					.updateResult(gameId,planId, handicapId, handicapMemberId, execBetItem.get("PLAN_ITEM_TABLE_ID_").toString(),
							execBetItem.get("PLAN_GROUP_KEY_").toString(),
							execBetItem.get("EXEC_PLAN_GROUP_ID_").toString(), execResult, nowTime,this.getClass().getName());
			//投注积分 = 每注积分 * 注数
			long betFundTh = fundTh * betLen;

			//盘口会员总盈亏
			profitVrService.updateProfit(profitVrId, profitTh, betFundTh, betLen, nowTime,this.getClass().getName());

			//盘口会员方案盈亏
			IbmProfitPlanVrTService profitPlanVrService = new IbmProfitPlanVrTService();
			String profitPlanVrId = profitPlanVrService.findId(profitVrId, planId);

			if (StringTool.isEmpty(profitPlanVrId)) {
				IbmProfitPlanVrT profitPlanVr = new IbmProfitPlanVrT();
				if ("2".equals(hmSet.getResetType())) {
					profitPlanVr.setProfitLimitMaxT(hmSet.getResetProfitMaxT());
					profitPlanVr.setLossLimitMinT(hmSet.getResetLossMinT());
				} else {
					Map<String, Object> limitInfo = new IbmPlanUserTService().getLimitInfo(handicapMemberId, planId);
					profitPlanVr.setProfitLimitMaxT(limitInfo.get("PROFIT_LIMIT_MAX_T_"));
					profitPlanVr.setLossLimitMinT(limitInfo.get("LOSS_LIMIT_MIN_T_"));
				}
				profitPlanVr.setProfitVrId(profitVrId);
				profitPlanVr.setPlanId(planId);
				profitPlanVr.setHandicapMemberId(handicapMemberId);
				profitPlanVr.setProfitT(profitTh);
				profitPlanVr.setBetFundsT(betFundTh);
				profitPlanVr.setBetLen(betLen);
				profitPlanVr.setCreateTime(nowTime);
				profitPlanVr.setCreateTimeLong(nowTime.getTime());
				profitPlanVr.setState(IbmStateEnum.OPEN.name());
				profitPlanVrId = profitPlanVrService.save(profitPlanVr);
			} else {
				profitPlanVrService.updateProfit(profitPlanVrId, profitTh, betFundTh, betLen, nowTime,this.getClass().getName());
			}
			//盘口会员盈亏信息
			IbmProfitInfoVrT profitInfoVr = new IbmProfitInfoVrT();
			profitInfoVr.setProfitPlanVrId(profitPlanVrId);
			profitInfoVr.setExecBetItemId(execBetItem.get("EXEC_BET_ITEM_ID_"));
			profitInfoVr.setHandicapMemberId(handicapMemberId);
			profitInfoVr.setPeriod(period);
			profitInfoVr.setProfitT(profitTh);
			profitInfoVr.setBetFundsT(betFundTh);
			profitInfoVr.setBetLen(betLen);
			profitInfoVr.setCreateTime(nowTime);
			profitInfoVr.setCreateTimeLong(nowTime.getTime());
			profitInfoVr.setState(IbmStateEnum.OPEN.name());
			new IbmProfitInfoVrTService().save(profitInfoVr);
		}

	}
}
