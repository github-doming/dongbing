package com.ibs.plan.module.client.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.ModeEnum;
import com.common.enums.TypeEnum;
import com.common.game.Game;
import com.common.game.Period;
import com.common.plan.Plan;
import com.common.plan.PlanGroup;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.enums.IbsModeEnum;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.job.bet.BetJobDefine;
import com.ibs.plan.module.client.ibsc_bet.entity.IbscBet;
import com.ibs.plan.module.client.ibsc_bet.service.IbscBetService;
import com.ibs.plan.module.client.ibsc_hm_coding_item.service.IbscHmCodingItemService;
import com.ibs.plan.module.client.ibsc_plan_group_result.service.IbscPlanGroupResultService;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 方案执行线程
 * @Author: null
 * @Date: 2020-05-16 15:28
 * @Version: v1.0
 */
public class PlanItemThread extends BaseCommThread {
	private IbscBetService betService = new IbscBetService();
	private Date nowTime = new Date();

	private String existHmId;
	private List<Map<String, Object>> planItems;
	private GameUtil.Code gameCode;
	private HandicapUtil.Code handicapCode;
	private Object period;
	private Map<String, Object> gameInfo;
	private Map<String, Object> setInfo;
	private String drawType;

	public PlanItemThread(String existHmId, List<Map<String, Object>> planItems, GameUtil.Code gameCode,
			 String drawType, Object period, Map<String, Object> gameInfo,
			Map<String, Object> setInfo) {
		this.existHmId = existHmId;
		this.planItems = planItems;
		this.gameCode = gameCode;
		this.drawType = drawType;
		this.period = period;
		this.gameInfo = gameInfo;
		this.setInfo = setInfo;
	}

	@Override public String execute(String ignore) throws Exception {
		//添加会员编码详情
		new IbscHmCodingItemService().save(existHmId,gameCode,period);

		handicapCode= HandicapUtil.Code.valueOf(setInfo.get("HANDICAP_CODE_").toString());
		//获取会员方案组历史投注信息
		Map<String, Map<String, Object>> execResults = new IbscPlanGroupResultService()
				.findHistory(existHmId, gameCode);

		Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);

		//资金选项，默认打平为输
		String fundsOptions=TypeEnum.FLAT_TO_LOSE.name();
		Map<String, Object> historyMap;
		String execResult;
		long fundTs = 0;
		int fundsKey;
		int historyFundKey;
		List<Map<String, Object>> betItems = new ArrayList<>();
		List<String> betContents = new ArrayList<>();
		for (Map<String, Object> planItem : planItems) {
			if (ContainerTool.isEmpty(planItem.get("PLAN_GROUP_ACTIVE_DATA_")) || StringTool
					.isEmpty(planItem.get("FUNDS_LIST_"))) {
				continue;
			}
			//方案组，需要过滤掉当期已经处理过的方案组
			JSONObject activePlanGroup = JSONObject.parseObject(planItem.get("PLAN_GROUP_ACTIVE_DATA_").toString());
			//资金列表
			String fundsList = planItem.get("FUNDS_LIST_").toString();
			//资金切换模式
			String fundSwapMode = planItem.get("FUND_SWAP_MODE_").toString();
			//监控期数
			int monitorPeriod = NumberTool.getInteger(planItem.get("MONITOR_PERIOD_"));
			//跟随期数
			int followPeriod = NumberTool.getInteger(planItem.get("FOLLOW_PERIOD_"));
			if (followPeriod == 0) {
				followPeriod = 1;
			}
			//算出跟进期数
			Object basePeriod = periodOption.findBeforePeriod(period, followPeriod);
			//方案编码
			PlanUtil.Code planCode = PlanUtil.Code.valueOf(planItem.get("PLAN_CODE_").toString());
			Plan plan = planCode.getPlan();
			//设置方案组信息
			PlanGroup planGroupInfo=new PlanGroup(basePeriod, monitorPeriod, gameCode, handicapCode, drawType);
			//投注模式
			Object betMode = planItem.get("BET_MODE_");
			//期期滚模式
			String periodRollMode = planItem.getOrDefault("PERIOD_ROLL_MODE_", "").toString();
			//扩展信息
			Object expandInfo = planItem.get("EXPAND_INFO_");
			for (Map.Entry<String, Object> planGroup : activePlanGroup.entrySet()) {
				execResult = IbsTypeEnum.FALSE.name();
				fundsKey = 0;
				historyFundKey = -1;
				//激活组
				String activeKey = planGroup.getKey();

				String planGroupKey = planCode.name().concat("#").concat(activeKey);
				historyMap = new HashMap<>(8);
				//开启停止新增，并且最近一次执行结果为中
				if (execResults.containsKey(planGroupKey)) {
					//历史执行结果
					historyMap = execResults.get(planGroupKey);
					if(ContainerTool.notEmpty(expandInfo)){
						JSONObject expand = JSONObject.parseObject(expandInfo.toString());
						if(expand.containsKey("FUNDS_OPTIONS_")){
							fundsOptions=expand.getString("FUNDS_OPTIONS_");
						}
					}
					execResult=fundRule(fundsOptions,historyMap);

					if (IbsStateEnum.NOW.name().equals(gameInfo.get("INCREASE_STATE_")) && IbsTypeEnum.TRUE.name().equalsIgnoreCase(execResult)) {
						continue;
					}
					//历史资金信息
					historyFundKey = NumberTool.getInteger(historyMap, "FUND_GROUP_KEY_", -1);
					//添加期期滚信息
					savePeriodRoll(planCode, betMode, historyMap, activeKey, execResult, fundsList, fundSwapMode,
							historyFundKey, betItems);
				}
				//组装数据
				JSONObject groupData = JSONObject.parseObject(planGroup.getValue().toString());
				planGroupInfo.setPlanGroupItem(groupData);

				if (plan.planGroup(planGroupInfo)) {
					continue;
				}
				groupData = plan.splice(historyMap,expandInfo);
				if (ContainerTool.isEmpty(groupData)) {
					continue;
				}
				//资金处理
				if (historyFundKey!=-1) {
					int fundsLen = fundsList.split(",").length;
					if(IbsTypeEnum.NONE.name().equals(execResult)){
						if(TypeEnum.FLAT_OLD_FUND.name().equals(fundsOptions)){
							fundsKey=historyFundKey;
						}else if(TypeEnum.FLAT_TO_WIN.name().equals(fundsOptions)){
							fundsKey = ModeEnum.fundSwap(fundSwapMode, historyFundKey, true, fundsLen);
						}
					}else{
						fundsKey = ModeEnum.fundSwap(fundSwapMode, historyFundKey, Boolean.parseBoolean(execResult), fundsLen);
					}
					if (StringTool.isEmpty(fundsKey)) {
						continue;
					}
					//TODO 判断用户是否为炸停止,炸停止之后处理
					if (fundsLen <= fundsKey) {
						if (IbsStateEnum.OPEN.name().equals(setInfo.get("BLAST_STOP_"))) {
							continue;
						}
						fundsKey = 0;
					}
				}
				String fundsValue = fundsValue(fundsList, fundsKey);
				long fundTh = NumberTool.longValueT(fundsValue);
				//turn 方案组详情
				String betContent = plan.betContent(groupData, fundTh,expandInfo,historyMap);
				if (StringTool.isEmpty(betContent)) {
					continue;
				}
				//判断期期滚是否为“重复内容不投注”
				if (StringTool.notEmpty(periodRollMode) && IbsModeEnum.PERIOD_ROLL_MODE_NO_REPEAT.name()
						.equals(periodRollMode)) {
					//判断是否有期期滚投注内容
					if (periodOption.findLotteryPeriod().equals(historyMap.get("LAST_PERIOD_"))) {
						if (betContent.equals(historyMap.get("BET_CONTENT_"))) {
							betContent = null;
						}
					}
				}
				if (StringTool.isEmpty(betContent)) {
					continue;
				}
				//存储数据
				String[] betContentArr = betContent.split("#");
				int len = betContentArr.length;
				fundTs += fundTh * len;
				Map<String, Object> betItem = new HashMap<>(6);
				betItem.put("planCode", planCode.name());
				betItem.put("activeKey", activeKey);
				betItem.put("fundsKey", fundsKey);
				betItem.put("betContentLen", len);
				betItem.put("betContent", betContent);
				betItem.put("betFundT", fundTh * len);
				//保存跟进期数和扩展信息
				betItem.put("basePeriod",plan.getPlanGroup().basePeriod());
				betItem.put("expandInfo",historyMap.get("EXPAND_INFO_"));
				betItems.add(betItem);
				betContents.addAll(Arrays.asList(betContentArr));
			}
		}
		if (ContainerTool.isEmpty(betItems)) {
			return null;
		}
		//投注比例
		double betRate = NumberTool.doubleT(setInfo.getOrDefault("BET_RATE_T_", 1000));
		//批量保存投注数据 bets
		//发送投注数据到主服务器
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("HANDICAP_CODE_", handicapCode.name());
		content.put("GAME_CODE_", gameCode.name());
		content.put("BET_MODE_", gameInfo.get("BET_MODE_"));
		content.put("PERIOD_", period);
		content.put("BET_ITEMS_", betItems);
		//判断是否开启合并
		if (IbsStateEnum.CLOSE.name().equals(setInfo.get("BET_MERGER_"))) {
			List<String> betIds = betService
					.batchSave(existHmId, gameCode, drawType, period, IbsTypeEnum.PLAN, betItems);
			content.put("METHOD_", IbsMethodEnum.PLAN_BET.name());
			RabbitMqTool.sendBet(content.toString(), "item");
			if(IbsTypeEnum.VIRTUAL.name().equals(gameInfo.get("BET_MODE_"))){
				betService.update(betIds, "VIRTUAL", IbsStateEnum.SUCCESS,nowTime);
				return null;
			}
			//投注
			bet(betIds, fundTs,IbsTypeEnum.PLAN);
			return null;
		}
		betService.batchSave(existHmId, gameCode, drawType, period, IbsTypeEnum.MERGE, betItems);

		Map<String, int[][]> betInfo = new HashedMap(5);
		//转化投注项
		Game game = gameCode.getGameFactory().game();
		game.putBetInfo(betInfo,betContents.toArray(new String[0]));
		//合并投注项资金
		game.mergeInfo(betInfo);
		//合并投注项
		List<Object> info = game.mergeItem(betInfo, betRate);
		String betItem = (String) info.get(0);
		Integer fundsTh = (Integer) info.get(1);

		IbscBet bet = new IbscBet();
		bet.setExistHmId(existHmId);
		bet.setPlanCode(IbsTypeEnum.MERGE.name());
		bet.setGameCode(gameCode.name());
		bet.setGameDrawType(drawType);
		bet.setPeriod(period);
		bet.setBetType(IbsTypeEnum.MERGE.name());
		bet.setBetContent(betItem);
		bet.setBetFundT(fundsTh);
		bet.setCreateTime(nowTime);
		bet.setCreateTimeLong(nowTime.getTime());
		bet.setUpdateTime(nowTime);
		bet.setUpdateTimeLong(nowTime.getTime());
		if (StringTool.isEmpty(betItem)) {
			//TODO 状态重新定义
			bet.setExecState(IbsStateEnum.BET.name());
			bet.setDesc("合并投注信息为空");
		} else {
			bet.setExecState(IbsStateEnum.OPEN.name());
		}
		String mergeBetId = betService.save(bet);
		JSONObject mergeBetInfo = new JSONObject();
		mergeBetInfo.put("clientBetId", mergeBetId);
		mergeBetInfo.put("betContent", betItem);
		mergeBetInfo.put("betContentLen", betItem.split("#").length);
		mergeBetInfo.put("betFundT", fundsTh);
		content.put("METHOD_", IbsMethodEnum.MERGE_BET.name());
		content.put("MERGE_BET_INFO_", mergeBetInfo);
		// 合并前后的投注信息都要发送到主服务器
		RabbitMqTool.sendBet(content.toString(), "item");
		if(IbsTypeEnum.VIRTUAL.name().equals(gameInfo.get("BET_MODE_"))){
			return null;
		}
		//投注
		bet(Collections.singletonList(mergeBetId), fundsTh, IbsTypeEnum.MERGE);
		return null;
	}

	private String fundRule(String fundsOptions, Map<String, Object> historyMap) {
		String execResult=historyMap.get("EXEC_RESULT_").toString();
		if(IbsTypeEnum.NONE.name().equals(execResult)){
			if(TypeEnum.FLAT_TO_LOSE.name().equalsIgnoreCase(fundsOptions)){
				execResult=IbsTypeEnum.FALSE.name();
			}else if(TypeEnum.FLAT_TO_WIN.name().equalsIgnoreCase(fundsOptions)){
				execResult=IbsTypeEnum.TRUE.name();
			}
		}
		return execResult;
	}

	/**
	 * 开启一个线程马上投注
	 *  @param betIds 投注主键
	 * @param fundTs 投注金额
	 * @param betType 投注类型
	 */
	public void bet(List<String> betIds, long fundTs, IbsTypeEnum betType) throws Exception {
		CurrentTransaction.transactionCommit();

		// 是否已封盘
		long sealTime = CustomerCache.sealTime(existHmId, gameCode) - System.currentTimeMillis();
		if (sealTime <= 0) {
			//重新获取封盘事件
			sealTime =
					CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System.currentTimeMillis();
			if (sealTime <= 0) {
				betService.update(betIds, "已封盘", IbsStateEnum.SUCCESS,nowTime);
				BetJobDefine.sendErrorReceipt( betIds, betType,"已封盘");
				return;
			}
		}

		double betRate = NumberTool.doubleT(setInfo.getOrDefault("BET_RATE_T_", 1000));
		// 金额 向上取整
		double funds = Math.ceil(fundTs / 1000D * betRate);
		double usedQuota = CustomerCache.usedQuota(existHmId, handicapCode, period);
		if (usedQuota - funds <= 0) {
			usedQuota = CustomerCache.usedQuota(existHmId, handicapCode);
			if (usedQuota - funds <= 0) {
				//余额不足，发送错误消息
				betService.update(betIds, "余额不足", IbsStateEnum.SUCCESS,nowTime);
				BetJobDefine.sendErrorReceipt( betIds, betType, "余额不足");
				return;
			}
		}
		//内存除去可用余额
		CustomerCache.usedQuota(existHmId, usedQuota - funds);
		//距离封盘小于投注设定时间
		long betSecond = NumberTool.longValueT(gameInfo.get("BET_SECOND_"));
		if (sealTime <= betSecond || betSecond == 0) {
			ThreadExecuteUtil.findInstance().getJobExecutor().execute(() -> {
				try {
					BetJobDefine.getJob(handicapCode).bet(existHmId, gameCode, period,betIds).execute(null);
				} catch (Exception e) {
					log.error("投注失败", e);
				}
			});
			return;
		}
		//开启定时投注
		Date startTime = new Date(sealTime - betSecond + System.currentTimeMillis());
		QuartzTool.saveBetJob(BetJobDefine.getJobClass(handicapCode),existHmId,handicapCode, gameCode, period,startTime,betIds);
	}

	/**
	 * 添加期期滚投注内容
	 *
	 * @param betMode    投注模式
	 * @param historyMap 历史信息
	 * @param activeKey  激活方案组key
	 */
	private void savePeriodRoll(PlanUtil.Code planCode, Object betMode, Map<String, Object> historyMap,
								String activeKey, String execResult, String fundsList, String fundSwapMode, int historyFundKey,
								List<Map<String, Object>> betItems) {
		if (!IbsModeEnum.BET_MODE_PERIOD_ROLL.name().equals(betMode)) {
			return;
		}
		if (!gameCode.getGameFactory().period(handicapCode).findLotteryPeriod()
				.equals(historyMap.get("LAST_PERIOD_"))) {
			return;
		}
		//上次结果为true就不滚了
		if (IbsTypeEnum.TRUE.name().equalsIgnoreCase(execResult)|| historyFundKey==-1) {
			return;
		}
		//判断资金是否炸了
		int fundsLen = fundsList.split(",").length;
		int fundsKey = IbsModeEnum.fundSwap(fundSwapMode, historyFundKey,false, fundsLen);
		//TODO 判断用户是否为炸停止,炸停止之后处理
		if (fundsLen <= fundsKey) {
			if (IbsStateEnum.OPEN.equal(setInfo.get("BLAST_STOP_"))) {
				return;
			}
			fundsKey = 0;
		}
		String fundsValue = fundsValue(fundsList, fundsKey);
		long fundTh = NumberTool.longValueT(fundsValue);

		String betContent = historyMap.get("BET_CONTENT_").toString();
		StringBuilder result = new StringBuilder();
		String[] betContents = betContent.split("#");
		int len = 0;
		for (String contents : betContents) {
			result.append(contents, 0, contents.lastIndexOf("|") + 1).append(fundTh).append("#");
			len++;
		}
		Map<String, Object> betItem = new HashMap<>(6);
		betItem.put("planCode", planCode.name());
		betItem.put("activeKey", activeKey);
		betItem.put("fundsKey", fundsKey);
		betItem.put("betContentLen", len);
		betItem.put("betContent", result.toString());
		betItem.put("betFundT", fundTh * len);
		betItems.add(betItem);
	}
	/**
	 * 获取 资金组value<br>
	 * fundsList不为空则读取list<br>
	 * 仅有fundsListId时执行高级资金方案
	 *
	 * @param fundsList 资金列表
	 * @param fundKey   资金组key
	 * @return 资金组value
	 */
	private static String fundsValue(String fundsList, int fundKey) {
		if (StringTool.notEmpty(fundsList)) {
			if (fundsList.split(",").length < fundKey) {
				return fundsList.split(",")[0];
			}
			return fundsList.split(",")[fundKey];
		}
		return null;
	}

}
