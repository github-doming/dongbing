package com.ibs.plan.module.mq.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;

import java.util.Date;
import java.util.Map;

/**
 * 方案投注
 *
 * @Author: null
 * @Date: 2020-06-05 15:20
 * @Version: v1.0
 */
public class PlanBetController {
	public static final String FORMAT = "方案:%03d-组:%2s";
	private String handicapMemberId;
	private HandicapUtil.Code handicapCode;
	private GameUtil.Code gameCode;
	private String period;
	private String betMode;

	public PlanBetController(String handicapMemberId, HandicapUtil.Code handicapCode, GameUtil.Code gameCode,
			String period, String betMode) {
		this.handicapMemberId = handicapMemberId;
		this.handicapCode = handicapCode;
		this.gameCode = gameCode;
		this.period = period;
		this.betMode = betMode;
	}
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		IbspHmBetItemService hmBetItemService = new IbspHmBetItemService();
		String handicapId = HandicapUtil.id(handicapCode,IbsTypeEnum.MEMBER);
		String gameId =GameUtil.id(gameCode);
		Date nowTime = new Date();
		JSONArray betItems = msgObj.getJSONArray("BET_ITEMS_");
		//获取会员该游戏的所有方案次序
		Map<String,Object> planSnInfo=new IbspPlanUserService().findPlanSns(handicapMemberId,gameId);
		String execState = IbsTypeEnum.VIRTUAL.name().equals(betMode) ?  "SUCCESS" : "PROCESS";

		hmBetItemService
				.batchSave(betItems, handicapMemberId, period, betMode, handicapId, gameId, execState,planSnInfo,
						nowTime);
		return JsonResultBeanPlus.successConstant();
	}
}
