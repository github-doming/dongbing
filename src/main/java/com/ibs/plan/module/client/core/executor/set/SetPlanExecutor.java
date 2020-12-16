package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.thread.CodingItemThread;
import com.ibs.plan.module.client.ibsc_hm_plan.entity.IbscHmPlan;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import com.ibs.plan.module.client.ibsc_plan_item.entity.IbscPlanItem;
import com.ibs.plan.module.client.ibsc_plan_item.service.IbscPlanItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 设置方案执行器
 * @Author: null
 * @Date: 2020-05-29 16:18
 * @Version: v1.0
 */
public class SetPlanExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean =new JsonResultBeanPlus();

		String planCode=msgObj.getString("PLAN_CODE_");
		String gameCode=msgObj.getString("GAME_CODE_");
		String planState=msgObj.getString("PLAN_STATE_");

		if(StringTool.isEmpty(planCode,gameCode,planState)){
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		Date nowTime=new Date();

		//获取方案信息
		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		Map<String,Object> hmPlanInfo=hmPlanService.isExistPlan(existHmId,planCode,gameCode);

		//添加方案详情信息
		IbscPlanItemService planItemService=new IbscPlanItemService();
		if(ContainerTool.isEmpty(hmPlanInfo)){
			IbscPlanItem planItem=new IbscPlanItem();
			planItem.setExistHmId(existHmId);
			planItem.setPlanCode(planCode);
			planItem.setFundsList(msgObj.get("FUNDS_LIST_"));
			planItem.setFollowPeriod(msgObj.get("FOLLOW_PERIOD_"));
			planItem.setMonitorPeriod(msgObj.get("MONITOR_PERIOD_"));
			planItem.setBetMode(msgObj.get("BET_MODE_"));
			planItem.setFundSwapMode(msgObj.get("FUND_SWAP_MODE_"));
			planItem.setPeriodRollMode(msgObj.get("PERIOD_ROLL_MODE_"));
			planItem.setPlanGroupActiveData(msgObj.get("PLAN_GROUP_ACTIVE_DATA_"));
			planItem.setExpandInfo(msgObj.get("EXPAND_INFO_"));
			planItem.setCreateTime(nowTime);
			planItem.setCreateTimeLong(nowTime.getTime());
			planItem.setUpdateTime(nowTime);
			planItem.setUpdateTimeLong(nowTime.getTime());
			planItem.setState(IbsStateEnum.OPEN.name());
			String planItemId=planItemService.save(planItem);

			IbscHmPlan hmPlan=new IbscHmPlan();
			hmPlan.setExistHmId(existHmId);
			hmPlan.setPlanItemId(planItemId);
			hmPlan.setPlanCode(planCode);
			hmPlan.setGameCode(gameCode);
			hmPlan.setPlanState(planState);
			hmPlan.setCreateTime(nowTime);
			hmPlan.setCreateTimeLong(nowTime.getTime());
			hmPlan.setUpdateTime(nowTime);
			hmPlan.setUpdateTimeLong(nowTime.getTime());
			hmPlan.setState(IbsStateEnum.OPEN.name());
			hmPlanService.save(hmPlan);
		}else{
			//修改方案状态
			hmPlanService.updatePlanState(hmPlanInfo.get("HM_PLAN_ID_"),planState);

			//修改方案详情信息
			IbscPlanItem planItem=planItemService.find(hmPlanInfo.get("PLAN_ITEM_ID_").toString());
			planItem.setFundsList(msgObj.get("FUNDS_LIST_"));
			planItem.setFollowPeriod(msgObj.get("FOLLOW_PERIOD_"));
			planItem.setMonitorPeriod(msgObj.get("MONITOR_PERIOD_"));
			planItem.setBetMode(msgObj.get("BET_MODE_"));
			planItem.setFundSwapMode(msgObj.get("FUND_SWAP_MODE_"));
			planItem.setPeriodRollMode(msgObj.get("PERIOD_ROLL_MODE_"));
			planItem.setPlanGroupActiveData(msgObj.get("PLAN_GROUP_ACTIVE_DATA_"));
			planItem.setExpandInfo(msgObj.get("EXPAND_INFO_"));
			planItem.setUpdateTime(nowTime);
			planItem.setUpdateTimeLong(nowTime.getTime());
			planItemService.update(planItem);
		}

		if(IbsStateEnum.OPEN.name().equals(planState)){
			new CodingItemThread(existHmId, GameUtil.Code.valueOf(gameCode)).execute(null);
		}

		return bean.success();
	}
}
