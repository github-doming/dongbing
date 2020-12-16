package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.vrc.vrc_member_game_set.entity.VrcMemberGameSet;
import com.ibm.follow.servlet.vrc.vrc_member_game_set.service.VrcMemberGameSetService;
import com.ibm.follow.servlet.vrc.vrc_member_plan.entity.VrcMemberPlan;
import com.ibm.follow.servlet.vrc.vrc_member_plan.service.VrcMemberPlanService;
import com.ibm.follow.servlet.vrc.vrc_plan_item.entity.VrcPlanItem;
import com.ibm.follow.servlet.vrc.vrc_plan_item.service.VrcPlanItemService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 虚拟会员游戏设置 开启、关闭
 * @Author: null
 * @Date: 2020-07-15 17:23
 * @Version: v1.0
 */
public class VrGameSetController implements ClientExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {

		String existId = msgObj.getString("EXIST_ID_");

		if (StringTool.isEmpty(existId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		Date nowTime = new Date();


		switch (IbmMethodEnum.valueOf(msgObj.getString("SET_ITEM_"))) {
			case SET_GAME_VR:
				setBetState(msgObj, existId, nowTime);
				break;
			case SET_PLAN_VR:
				setPlanInfo(msgObj, existId,  nowTime);
			default:
				break;
		}
		return bean;
	}

	private void setPlanInfo(JSONObject planItem, String existMemberVrId,  Date nowTime) throws Exception {
		String planState = planItem.getString("PLAN_STATE_");
		String planCode = planItem.getString("PLAN_CODE_");
		String gameCode = planItem.getString("GAME_CODE_");
		if (ContainerTool.isEmpty(planItem)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		VrcPlanItemService planItemService = new VrcPlanItemService();
		VrcMemberPlanService planService = new VrcMemberPlanService();
		VrcMemberPlan memberPlan = planService.find(existMemberVrId, planCode,gameCode);
		VrcPlanItem vrcPlanItem;
		if (memberPlan == null) {
			vrcPlanItem = new VrcPlanItem();
			vrcPlanItem.setExistMemberVrId(existMemberVrId);
			vrcPlanItem.setPlanCode(planCode);
			vrcPlanItem.setFundsList(planItem.get("FUNDS_LIST_"));
			vrcPlanItem.setFollowPeriod(planItem.get("FOLLOW_PERIOD_"));
			vrcPlanItem.setMonitorPeriod(planItem.get("MONITOR_PERIOD_"));
			vrcPlanItem.setBetMode(planItem.get("BET_MODE_"));
			vrcPlanItem.setFundSwapMode(planItem.get("FUND_SWAP_MODE_"));
			vrcPlanItem.setPeriodRollMode(planItem.get("PERIOD_ROLL_MODE_"));
			vrcPlanItem.setPlanGroupActiveData(planItem.get("PLAN_GROUP_ACTIVE_DATA_"));
			vrcPlanItem.setExpandInfo(planItem.get("EXPAND_INFO_"));
			vrcPlanItem.setCreateTime(nowTime);
			vrcPlanItem.setCreateTimeLong(System.currentTimeMillis());
			vrcPlanItem.setUpdateTime(nowTime);
			vrcPlanItem.setUpdateTimeLong(System.currentTimeMillis());
			vrcPlanItem.setState(IbmStateEnum.OPEN.name());
			String pk = planItemService.save(vrcPlanItem);

			memberPlan = new VrcMemberPlan();
			memberPlan.setExistMemberVrId(existMemberVrId);
			memberPlan.setPlanItemId(pk);
			memberPlan.setPlanCode(planCode);
			memberPlan.setGameCode(gameCode);
			memberPlan.setPlanState(planState);
			memberPlan.setCreateTime(nowTime);
			memberPlan.setCreateTimeLong(System.currentTimeMillis());
			memberPlan.setUpdateTime(nowTime);
			memberPlan.setUpdateTimeLong(System.currentTimeMillis());
			memberPlan.setState(IbmStateEnum.OPEN.name());
			planService.save(memberPlan);
		}else{
			memberPlan.setPlanState(planState);
			memberPlan.setUpdateTime(nowTime);
			memberPlan.setUpdateTimeLong(System.currentTimeMillis());
			memberPlan.setState(IbmStateEnum.OPEN.name());
			planService.update(memberPlan);

			vrcPlanItem = planItemService.find(existMemberVrId, planCode);
			vrcPlanItem.setFundsList(planItem.get("FUNDS_LIST_"));
			vrcPlanItem.setFollowPeriod(planItem.get("FOLLOW_PERIOD_"));
			vrcPlanItem.setMonitorPeriod(planItem.get("MONITOR_PERIOD_"));
			vrcPlanItem.setBetMode(planItem.get("BET_MODE_"));
			vrcPlanItem.setPeriodRollMode(planItem.get("PERIOD_ROLL_MODE_"));
			vrcPlanItem.setFundSwapMode(planItem.get("FUND_SWAP_MODE_"));
			vrcPlanItem.setPlanGroupActiveData(planItem.get("PLAN_GROUP_ACTIVE_DATA_"));
			vrcPlanItem.setExpandInfo(planItem.get("EXPAND_INFO_"));
			vrcPlanItem.setUpdateTime(nowTime);
			vrcPlanItem.setUpdateTimeLong(System.currentTimeMillis());
			planItemService.update(vrcPlanItem);
		}
		bean.success();
	}

	private void setBetState(JSONObject msgObj, String existMemberVrId,  Date nowTime) throws Exception {
		String gameCode = msgObj.getString("GAME_CODE_");
		String betState = msgObj.getString("BET_STATE_");
		String gameType = msgObj.getString("GAME_TYPE_");
		if (StringTool.isEmpty(betState) || StringTool.isEmpty(gameType)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		VrcMemberGameSetService gameSetService = new VrcMemberGameSetService();
		VrcMemberGameSet memberGameSet = gameSetService.find(existMemberVrId, gameCode);
		if (memberGameSet == null) {
			memberGameSet = new VrcMemberGameSet();
			memberGameSet.setExistMemberVrId(existMemberVrId);
			memberGameSet.setGameCode(gameCode);
			memberGameSet.setGameDrawType(gameType);
			memberGameSet.setBetState(betState);
			memberGameSet.setCreateTime(nowTime);
			memberGameSet.setCreateTimeLong(System.currentTimeMillis());
			memberGameSet.setUpdateTime(nowTime);
			memberGameSet.setUpdateTimeLong(System.currentTimeMillis());
			memberGameSet.setState(IbmStateEnum.OPEN.name());
			gameSetService.save(memberGameSet);
		} else {
			memberGameSet.setBetState(betState);
			memberGameSet.setUpdateTime(nowTime);
			memberGameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSetService.update(memberGameSet);
		}

		bean.success();
	}
}
