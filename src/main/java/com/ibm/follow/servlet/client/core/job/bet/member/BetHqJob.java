package com.ibm.follow.servlet.client.core.job.bet.member;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.MemberOption;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.follow.servlet.client.ibmc_hm_bet_fail.service.IbmcHmBetFailService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.service.IbmcHmBetInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_item.service.IbmcHmBetItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * @Description: 投注进Hq工作类
 * @Author: Dongming
 * @Date: 2019-09-16 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetHqJob extends BetJob {
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		try {
            handicapCode=HandicapUtil.Code.HQ;
            super.executeJob(context);
            if (ContainerTool.isEmpty(hmBetMap)||ContainerTool.isEmpty(hmGameSet)) {
                return;
            }
			//处理投注信息
			betProcess();
            log.info("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注完成",handicapCode,existHmId,gameCode,period);
		} catch (Exception e) {
            log.error("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】投注异常:{}",handicapCode,existHmId,gameCode,period,e.getMessage());
            throw e;
		}
	}

	/**
	 * 投注信息处理
	 *
	 */
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
		Map<String, String> limitTypeCodes = BallCodeTool.getItemTypeCodes(gameCode);
		Map<String, String> ballCode = BallCodeTool.getBallLimitCode(handicapCode, gameCode);
		for (Map.Entry<String, Object> entry : hmBetMap.entrySet()) {
			String[] betInfos = entry.getValue().toString().split("#");
			for (String info : betInfos) {
				//分投处理
				String[] items = BetJobDefine
						.classifyProcess(hmGameSet, info, ballCode, limitTypeCodes, errorMap, entry.getKey());
				if (ContainerTool.isEmpty(items)) {
					continue;
				}
				//保存投注信息
				BetJobDefine.saveBetInfo(betItemMap, fundMap, hmBetIdMap, items, entry.getKey());
			}
		}
		String methodType = StringTool.isEmpty(hmBetId) ? IbmMethodEnum.FOLLOW_BET.name() : IbmMethodEnum.MANUAL_BET.name();
        //错误信息处理
        BetJobDefine.errorProcess(existHmId,gameCode,period,errorMap,methodType);
		if (ContainerTool.isEmpty(betItemMap)) {
			return;
		}
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
		//投注
		firstBet(memberOption, betItemMap, hmBetIdMap, fundMap);

		//补投
		List<Map<String, Object>> errorBetInfos = new IbmcHmBetFailService().findAgainBetInfo(existHmId, period, gameCode);
		if (ContainerTool.notEmpty(errorBetInfos)) {
			againBet(memberOption, errorBetInfos);
		}
		//发送结果信息
		sendResultReceipt(memberOption, methodType);
	}
	private void againBet(MemberOption memberOption, List<Map<String, Object>> errorBetInfos) throws Exception {
		for (Map<String, Object> betInfoMap : errorBetInfos) {
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

	private void firstBet(MemberOption memberOption, Map<String, List<String>> betItemMap, Map<String, String> hmBetIdMap, Map<String, Long> fundMap) throws Exception {
		IbmcHmBetInfoService hmBetInfoService = new IbmcHmBetInfoService();
		List<String> betItems;
		for (Map.Entry<String, List<String>> betItem : betItemMap.entrySet()) {
			betItems = betItem.getValue();
			long fund = fundMap.get(betItem.getKey());
			//保存投注信息
			String hmBetInfoId = hmBetInfoService
					.save(existHmId, hmBetIdMap.get(betItem.getKey()), period, gameCode, betItems, fund);
			//投注
			bet(memberOption, betItems, betItem.getKey(), NumberTool.doubleT(fund), hmBetInfoId, true);
		}
	}

	private void bet(MemberOption memberOption, List<String> betItems, String betType, double funds, String betInfoId, boolean flag) throws Exception {
		//进行信息校验
		if (verify(memberOption, betInfoId, betItems, betType, flag, funds)) {
			return;
		}
		List<String> errorInfo = new ArrayList<>();
		//获取期数字符串
		Object roundno = handicapCode.getMemberFactory().handicap().handicapPeriod(gameCode, period);

		memberOption.oddsInfo(gameCode,betType);
		JsonResultBeanPlus bean = memberOption.betting(gameCode,roundno,betType,betItems,errorInfo);
		if (!bean.isSuccess()) {
			errorProcess(betInfoId, betItems, betType, flag, bean.getCode(), handicapCode);
			return;
		}
		JSONArray betResult = (JSONArray) bean.getData();
		if (ContainerTool.notEmpty(betResult)) {
			new IbmcHmBetItemService().save(existHmId, betInfoId, period, gameCode, betResult, betItems);
			new IbmcHmBetInfoService().updateState(betInfoId, IbmStateEnum.SUCCESS.name());
			if (betItems.size() > 0) {
				saveErrorBetInfo(betInfoId, betItems, betType, flag, IbmStateEnum.AGAIN.name(), "投注失败项");
			}
		} else {
			saveErrorBetInfo(betInfoId, betItems, betType, flag, IbmStateEnum.AGAIN.name(), "投注失败");
		}
	}
}
