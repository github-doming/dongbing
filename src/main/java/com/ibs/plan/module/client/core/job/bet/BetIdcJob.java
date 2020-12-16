package com.ibs.plan.module.client.core.job.bet;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.Handicap;
import com.common.handicap.MemberOption;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.core.controller.MemberConfigController;
import com.ibs.plan.module.client.ibsc_bet_fail.service.IbscBetFailService;
import com.ibs.plan.module.client.ibsc_bet_info.service.IbscBetInfoService;
import com.ibs.plan.module.client.ibsc_bet_item.service.IbscBetItemService;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;
/**
 * @Description: IDC投注job
 * @Author: null
 * @Date: 2020-05-20 15:11
 * @Version: v1.0
 */
public class BetIdcJob extends BaseBetJob {
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		try {
			handicapCode = HandicapUtil.Code.IDC;
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
		List<List<String>> betItemList = new ArrayList<>();
		//投注总金额
		List<Long> fundList = new ArrayList<>();
		//投注id
		List<String> betIdList = new ArrayList<>();
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
				saveBetInfo(betItemList, fundList, betIdList, items, entry.getKey());
			}
		}
		//错误信息处理
		BetJobDefine.sendErrorReceipt(errorMap, IbsTypeEnum.BET,"投注项不符合限额");
		if (ContainerTool.isEmpty(betItemList)) {
			return;
		}
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
		//投注
		firstBet(memberOption, betItemList, betIdList, fundList);
		//补投
		List<Map<String, Object>> errorBetInfos = new IbscBetFailService()
				.listAgainBetInfo(existHmId, period, gameCode);
		if (ContainerTool.notEmpty(errorBetInfos)) {
			againBet(memberOption, errorBetInfos);
		}
		//发送结果信息
		sendResultReceipt(memberOption);
	}

	/**
	 * 分批保存投注信息
	 *
	 * @param betItemList 投注信息list
	 * @param fundList    金额list
	 * @param betIdList   投注主键list
	 * @param items       投注项
	 * @param betId       投注主键
	 */
	private void saveBetInfo(List<List<String>> betItemList, List<Long> fundList, List<String> betIdList,
			String[] items, String betId) {
		for (int i = 0; i < items.length; i++) {
			String[] info = items[i].split("\\|");
			//投注项
			String bet = info[0].concat("|").concat(info[1]);
			if (betItemList.size() >= i + 1 && !StringTool.isContains(betItemList.get(i).toString(), bet)) {
				betItemList.get(i).add(items[i]);
				fundList.set(i, fundList.get(i) + Long.parseLong(info[2]));
				if (!StringTool.isContains(betIdList.get(i), betId)) {
					betIdList.set(i, betIdList.get(i).concat(",").concat(betId));
				}
			} else {
				//存储投注项
				List<String> betItems = new ArrayList<>();
				betItems.add(items[i]);
				betItemList.add(betItems);
				//存储投注金额
				fundList.add(Long.parseLong(info[2]));
				//存储投注id
				betIdList.add(betId);
			}
		}
	}
	/**
	 * 首次投注
	 *
	 * @param memberOption 会员操作类
	 * @param betItemList  投注详情列表
	 * @param betIdList    投注主键列表
	 * @param fundList     金额列表
	 */
	private void firstBet(MemberOption memberOption, List<List<String>> betItemList, List<String> betIdList,
			List<Long> fundList) throws Exception {
		IbscBetInfoService betInfoService = new IbscBetInfoService();
		List<String> betItems;
		for (int i = 0; i < betItemList.size(); i++) {
			betItems = betItemList.get(i);
			long fund = fundList.get(i);
			//保存投注信息
			String hmBetInfoId = betInfoService
					.save(existHmId, betIdList.get(i), period, gameCode, betItems, fund, nowTime);
			//投注
			bet(memberOption, betItems, NumberTool.doubleT(fund), hmBetInfoId, true);
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
			bet(memberOption, bet, fund, hmBetInfoId, false);
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
	private void bet(MemberOption memberOption, List<String> betItems, double funds, String betInfoId, boolean flag)
			throws Exception {
		//进行信息校验
		if (verify(memberOption, betInfoId, betItems,"", flag, funds)) {
			return;
		}
		List<String> errorInfo = new ArrayList<>();
		JsonResultBeanPlus bean = memberOption.betting(gameCode, period, null, betItems, errorInfo);
		//超过限额处理
		limitProcess(betInfoId, errorInfo, flag);
		if (!bean.isSuccess()) {
			errorProcess(betInfoId, betItems,"", flag, bean.getCode(), handicapCode);
			return;
		}
		//投注成功
		JSONArray betResult = (JSONArray) bean.getData();
		if (ContainerTool.notEmpty(betResult)) {
			new IbscBetItemService().save(existHmId, betInfoId, period, gameCode, betResult, betItems);
			new IbscBetInfoService().update(betInfoId, IbsStateEnum.SUCCESS, "投注成功", nowTime);
			if (betItems.size() > 0) {
				saveErrorBetInfo(betInfoId, betItems,"", flag, IbsStateEnum.AGAIN, "投注失败项");
			}
		} else {
			saveErrorBetInfo(betInfoId, betItems,"", flag, IbsStateEnum.AGAIN, "投注失败");
		}
	}

	/**
	 * 限额处理
	 * @param betInfoId 投注信息主键
	 * @param errorInfo 错误信息
	 * @param flag  是否还复投
	 */
	private void limitProcess(String betInfoId, List<String> errorInfo, boolean flag) throws Exception {
		if (ContainerTool.isEmpty(errorInfo)) {
			return;
		}
		//超过限额，重新获取限额，不复投
		IbscHmGameSetService hmGameSetService = new IbscHmGameSetService();
		String hmGameSetId = hmGameSetService.findId(existHmId, gameCode.name());
		if (StringTool.notEmpty(hmGameSetId)) {
			new MemberConfigController().gameLimit(existHmId, handicapCode, gameCode, hmGameSetId);
		}
		saveErrorBetInfo(betInfoId, errorInfo,"", flag, IbsStateEnum.OPEN, "投注失败,超过限额");
	}
}
