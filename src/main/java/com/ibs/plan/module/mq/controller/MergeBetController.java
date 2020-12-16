package com.ibs.plan.module.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.entity.IbspHmBetItem;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;

import java.util.Date;
import java.util.Map;

/**
 * 合并投注
 *
 * @Author: null
 * @Date: 2020-06-05 17:48
 * @Version: v1.0
 */
public class MergeBetController {
	private String handicapMemberId;
	private HandicapUtil.Code handicapCode;
	private GameUtil.Code gameCode;
	private String period;
	private String betMode;

	public MergeBetController(String handicapMemberId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode,
			String period, String betMode) {
		this.handicapMemberId = handicapMemberId;
		this.handicapCode = handicapCode;
		this.gameCode = gameCode;
		this.period = period;
		this.betMode = betMode;
	}
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		IbspHmBetItemService hmBetItemService = new IbspHmBetItemService();
		String handicapId =HandicapUtil.id(handicapCode,IbsTypeEnum.MEMBER);
		String gameId =GameUtil.id(gameCode);
		Date nowTime = new Date();
		//获取会员该游戏的所有方案次序
		Map<String,Object> planSnInfo=new IbspPlanUserService().findPlanSns(handicapMemberId,gameId);

		hmBetItemService
				.batchSave(msgObj.getJSONArray("BET_ITEMS_"), handicapMemberId, period, betMode, handicapId, gameId,
						IbsStateEnum.MERGE.name(),planSnInfo, nowTime);

		JSONObject mergeBetInfo = msgObj.getJSONObject("MERGE_BET_INFO_");

		IbspHmBetItem hmBetItem = new IbspHmBetItem();
		hmBetItem.setClientBetId(mergeBetInfo.get("clientBetId"));
		hmBetItem.setHandicapMemberId(handicapMemberId);
		hmBetItem.setHandicapId(handicapId);
		hmBetItem.setGameId(gameId);
		hmBetItem.setPlanId(IbsStateEnum.MERGE.name());
		hmBetItem.setPeriod(period);
		hmBetItem.setBetMode(betMode);
		hmBetItem.setBetType(IbsTypeEnum.MERGE.ordinal());
		hmBetItem.setPlanGroupDesc(IbsTypeEnum.MERGE.getMsg());
		hmBetItem.setBetContent(mergeBetInfo.get("betContent"));
		hmBetItem.setBetContentLen(mergeBetInfo.get("betContentLen"));
		hmBetItem.setFundT(mergeBetInfo.get("betFundT"));
		hmBetItem.setFundsIndex(0);
		hmBetItem.setExecState(IbsStateEnum.PROCESS);
		hmBetItem.setCreateTime(nowTime);
		hmBetItem.setCreateTimeLong(System.currentTimeMillis());
		hmBetItem.setUpdateTime(nowTime);
		hmBetItem.setUpdateTimeLong(System.currentTimeMillis());
		hmBetItem.setState(IbsStateEnum.OPEN.name());
		hmBetItemService.save(hmBetItem);

		return JsonResultBeanPlus.successConstant();
	}
}
