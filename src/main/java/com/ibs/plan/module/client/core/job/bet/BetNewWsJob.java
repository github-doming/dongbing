package com.ibs.plan.module.client.core.job.bet;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.Handicap;
import com.common.handicap.MemberOption;
import com.common.util.BaseHandicapUtil;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_bet_fail.service.IbscBetFailService;
import com.ibs.plan.module.client.ibsc_bet_info.service.IbscBetInfoService;
import com.ibs.plan.module.client.ibsc_bet_item.service.IbscBetItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * @Description: 新濠(NewWs)投注Job
 * @Author: admin1
 * @Date: 2020-09-10 10:49
 */
public class BetNewWsJob extends BaseBetJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		try {
			handicapCode = HandicapUtil.Code.NEWWS;
			super.executeJob(context);
			if (ContainerTool.isEmpty(betMap) || ContainerTool.isEmpty(gameSet)) {
				return;
			}
			//处理投注信息
			betProcess();
			log.info("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注完成", handicapCode, existHmId, gameCode, period);
		} catch (Exception e) {
			log.error("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注异常:{}", handicapCode, existHmId, gameCode, period, e.getMessage());
			throw e;
		}
	}

	private void betProcess() throws Exception {
		//总投注项
		Map<String, List<String>> betItemMap = new HashMap<>(3);
		//投注id
		Map<String, String> hmBetIdMap = new HashMap<>(3);
		//投注总金额
		Map<String, Long> fundMap = new HashMap<>(3);
		//保存错误投注项
		Map<String, Object> errorMap = new HashMap<>(10);
		//投注限制code
		Handicap handicap = HandicapUtil.handicap(handicapCode);
		Map<String, String> itemType = handicap.itemTypeMap(gameCode);
		Map<String, String> itemLimit = handicap.itemLimitMap(gameCode);
		for (Map.Entry<String, Object> entry : betMap.entrySet()) {
			String[] betInfos = entry.getValue().toString().split("#");
			for (String betInfo : betInfos) {
				//分投处理
				String[] items = BetJobDefine
						.classifyProcess(gameSet, betInfo, itemType, itemLimit, errorMap, entry.getKey());
				if (ContainerTool.isEmpty(items)) {
					continue;
				}
				//保存投注信息
				BetJobDefine.saveBetInfo(betItemMap, fundMap, hmBetIdMap, items, entry.getKey());
			}
		}
		//错误信息处理
		BetJobDefine.sendErrorReceipt(errorMap, IbsTypeEnum.BET, "投注项不符合限额");
		if (ContainerTool.isEmpty(betItemMap)) {
			return;
		}
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
		//投注
		firstBet(memberOption, betItemMap, hmBetIdMap, fundMap);
		//补投
		List<Map<String, Object>> errorBetInfos = new IbscBetFailService()
				.listAgainBetInfo(existHmId, period, gameCode);
		if (ContainerTool.notEmpty(errorBetInfos)) {
			againBet(memberOption, errorBetInfos);
		}
		//发送结果信息
		sendResultReceipt(memberOption);
	}

	private void firstBet(MemberOption memberOption, Map<String, List<String>> betItemMap, Map<String, String> hmBetIdMap, Map<String, Long> fundMap) throws Exception {
		IbscBetInfoService betInfoService = new IbscBetInfoService();
		List<String> betItems;
		for (Map.Entry<String, List<String>> betItem : betItemMap.entrySet()) {
			betItems = betItem.getValue();
			long fund = fundMap.get(betItem.getKey());
			//保存投注信息
			String hmBetInfoId = betInfoService
					.save(existHmId, hmBetIdMap.get(betItem.getKey()), period, gameCode, betItems, fund, nowTime);
			//投注
			bet(memberOption, betItems, betItem.getKey(), NumberTool.doubleT(fund), hmBetInfoId, true);
		}
	}

	/**
	 * 二次投注
	 *
	 * @param memberOption  会员操作类
	 * @param againBetInfos 复投信息
	 */
	private void againBet(MemberOption memberOption, List<Map<String, Object>> againBetInfos) throws Exception {
		for (Map<String, Object> betInfoMap : againBetInfos) {
			List<String> betItems = Arrays.asList(betInfoMap.get("betInfo").toString().split(","));
			double fund = 0;
			for (String betItem : betItems) {
				String[] infos = betItem.split("\\|");
				fund += NumberTool.doubleT(infos[2]);
			}
			//防止throw UnsupportedOperationException
			List<String> bet = new ArrayList<>(betItems);
			String hmBetInfoId = betInfoMap.get("betInfoId").toString();
			//投注
			bet(memberOption, bet, betInfoMap.get("betType").toString(), fund, hmBetInfoId, false);
		}
	}

	/**
	 * 投注
	 *
	 * @param memberOption 会员操作类
	 * @param betItems     投注信息详情
	 * @param funds        金额
	 * @param betInfoId    投注信息主键
	 * @param flag         是否还复投
	 */
	private void bet(MemberOption memberOption, List<String> betItems, String betType, double funds, String betInfoId, boolean flag)
			throws Exception {
		//进行信息校验
		if (verify(memberOption, betInfoId, betItems, "", flag, funds)) {
			return;
		}
		List<String> errorInfo = new ArrayList<>();
		//获取期数字符串
		Object roundno = handicapCode.getMemberFactory().handicap().handicapPeriod(gameCode, period);
		memberOption.oddsInfo(gameCode, betType);

		JsonResultBeanPlus bean = memberOption.betting(gameCode, roundno, betType, betItems, errorInfo);
		if (!bean.isSuccess()) {
			errorProcess(betInfoId, betItems, betType, flag, bean.getCode(), handicapCode);
			return;
		}
		JSONArray betResult = (JSONArray) bean.getData();
		if (ContainerTool.notEmpty(betResult)) {
			new IbscBetItemService().save(existHmId, betInfoId, period, gameCode, betResult, betItems);
			new IbscBetInfoService().update(betInfoId, IbsStateEnum.SUCCESS, "投注成功", nowTime);
			if (betItems.size() > 0) {
				saveErrorBetInfo(betInfoId, betItems, "", flag, IbsStateEnum.AGAIN, "投注失败项");
			}
		} else {
			saveErrorBetInfo(betInfoId, betItems, "", flag, IbsStateEnum.AGAIN, "投注失败");
		}
	}

}
