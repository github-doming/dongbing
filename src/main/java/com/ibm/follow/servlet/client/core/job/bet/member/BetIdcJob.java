package com.ibm.follow.servlet.client.core.job.bet.member;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.handicap.MemberOption;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.config.SetToolController;
import com.ibm.follow.servlet.client.ibmc_hm_bet_fail.service.IbmcHmBetFailService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.service.IbmcHmBetInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_item.service.IbmcHmBetItemService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.*;

/**
 * @Description: 投注进IDC工作类
 * @Author: Dongming
 * @Date: 2019-09-12 16:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetIdcJob extends BetJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		try {
            handicapCode=HandicapUtil.Code.IDC;
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
		List<List<String>> betItemList = new ArrayList<>();
		//投注总金额
		List<Long> fundList = new ArrayList<>();
		//投注id
		List<String> hmBetIdList = new ArrayList<>();
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
				saveBetInfo(betItemList, fundList, hmBetIdList, items, entry.getKey());
			}
		}
		String methodType = StringTool.isEmpty(hmBetId) ? IbmMethodEnum.FOLLOW_BET.name() : IbmMethodEnum.MANUAL_BET.name();
        //错误信息处理
        BetJobDefine.errorProcess(existHmId,gameCode,period,errorMap,methodType);
		if (ContainerTool.isEmpty(betItemList)) {
			return;
		}
		MemberOption memberOption = handicapCode.getMemberFactory().memberOption(existHmId);
		//投注
		firstBet(memberOption, betItemList, hmBetIdList, fundList);

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
			bet(memberOption, bet, fund, hmBetInfoId, false);
		}
	}

	/**
	 * 投注
	 *
	 * @param betItemList   总投注项
	 * @param hmBetIdList   投注id
	 * @param fundList      投注金额
	 * @param memberOption 盘口会员工具类
	 */
	private void firstBet(MemberOption memberOption, List<List<String>> betItemList, List<String> hmBetIdList,
						  List<Long> fundList) throws Exception {
		IbmcHmBetInfoService hmBetInfoService = new IbmcHmBetInfoService();
        List<String> betItems;
		for (int i = 0; i < betItemList.size(); i++) {
			betItems = betItemList.get(i);
			long fund = fundList.get(i);
			//保存投注信息
			String hmBetInfoId = hmBetInfoService.save(existHmId, hmBetIdList.get(i), period, gameCode, betItems, fund);
			//投注
			bet(memberOption, betItems, NumberTool.doubleT(fund), hmBetInfoId, true);
		}
	}

	private void bet(MemberOption memberOption, List<String> betItems, double fund, String hmBetInfoId,
					 boolean flag) throws Exception {
        //进行信息校验
        if(verify(memberOption,hmBetInfoId,betItems,flag,fund)){
            return;
        }
        //获取期数字符串
        String roundno = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
		List<String> errorInfo = new ArrayList<>();
		//投注
		JsonResultBeanPlus bean = memberOption.betting(gameCode, roundno,null, betItems, errorInfo);
		//超过限额处理
		limitProcess(hmBetInfoId, errorInfo, flag);
		if (!bean.isSuccess()) {
            errorProcess(hmBetInfoId, betItems, "",flag, bean.getCode(),handicapCode);
			return;
		}
		//投注成功
		JSONArray betResult = (JSONArray) bean.getData();
		if (ContainerTool.notEmpty(betResult)) {
			new IbmcHmBetItemService().save(existHmId, hmBetInfoId, period, gameCode, betResult, betItems);
			new IbmcHmBetInfoService().updateState(hmBetInfoId, IbmStateEnum.SUCCESS.name());
			if (betItems.size() > 0) {
				saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.AGAIN.name(), "投注失败项");
			}
		} else {
			saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.AGAIN.name(), "投注失败");
		}
	}

    private boolean verify(MemberOption memberOption, String hmBetInfoId, List<String> betItems, boolean flag, double funds) throws Exception {
        //获取用户信息
		MemberUser memberUser = memberOption.userInfo(false);
		if (memberUser == null) {
			saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.OPEN.name(), "用户信息不存在");
			return true;
		}
        //个人余额
		//判断余额是否充足
		if (memberUser.getUsedQuota() - funds < 0) {
			memberUser = memberOption.userInfo(true);
			if (memberUser == null) {
				saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.OPEN.name(), "用户信息不存在");
				return true;
			}
			if (memberUser.getUsedQuota() - funds < 0) {
				saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.OPEN.name(), "余额不足");
				return true;
			}
			if (StringTool.isEmpty(memberUser.getUsedQuota())) {
				saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.OPEN.name(), "用户信息不存在");
				return true;
			}
		}

        //判断游戏是否封盘
        long sealTime = CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System
                .currentTimeMillis();
        if (sealTime < 0) {
            saveErrorBetInfo(hmBetInfoId, betItems,"", flag, IbmStateEnum.OPEN.name(), "已封盘");
            return true;
        }
        return false;
	}

    private void limitProcess(String hmBetInfoId, List<String> errorInfo, boolean flag) throws Exception {
		if (ContainerTool.isEmpty(errorInfo)) {
			return;
		}
		//超过限额，重新获取限额，不复投
		IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
		String hmGameSetId = hmGameSetService.findId(existHmId, gameCode.name());
		if (StringTool.notEmpty(hmGameSetId)) {
			SetToolController.gameLimit(existHmId, handicapCode, gameCode, hmGameSetId);
		}
		saveErrorBetInfo(hmBetInfoId, errorInfo,"", flag, IbmStateEnum.OPEN.name(), "投注失败,超过限额");
	}


	/**
	 * 分批保存投注信息
	 *
	 * @param betItemList 投注信息list
	 * @param fundList    金额list
	 * @param hmBetIdList 投注主键list
	 * @param item        投注项
	 * @param hmBetId     投注主键
	 */
	private void saveBetInfo(List<List<String>> betItemList, List<Long> fundList, List<String> hmBetIdList,
							 String[] item, String hmBetId) {
		for (int i = 0; i < item.length; i++) {
			String[] info = item[i].split("\\|");
			//投注项
			String bet = info[0].concat("|").concat(info[1]);
			if (betItemList.size() >= i + 1 && !StringTool.isContains(betItemList.get(i).toString(), bet)) {
				betItemList.get(i).add(item[i]);
				fundList.set(i, fundList.get(i) + Long.parseLong(info[2]));
				if (!StringTool.isContains(hmBetIdList.get(i), hmBetId)) {
					hmBetIdList.set(i, hmBetIdList.get(i).concat(",").concat(hmBetId));
				}
			} else {
				//存储投注项
				List<String> betItems = new ArrayList<>();
				betItems.add(item[i]);
				betItemList.add(betItems);
				//存储投注金额
				fundList.add(Long.parseLong(info[2]));
				//存储投注id
				hmBetIdList.add(hmBetId);
			}
		}
	}
}
