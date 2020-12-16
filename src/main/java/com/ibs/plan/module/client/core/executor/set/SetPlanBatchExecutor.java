package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONArray;
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
 * @Description: 修改方案状态执行器
 * @Author: null
 * @Date: 2020-06-04 16:01
 * @Version: v1.0
 */
public class SetPlanBatchExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean =new JsonResultBeanPlus();

		String planCodes=msgObj.getString("PLAN_CODES_");
		String gameCode=msgObj.getString("GAME_CODE_");
		String planState=msgObj.getString("PLAN_STATE_");

		if(StringTool.isEmpty(planCodes,gameCode,planState)){
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
		String[] codes=planCodes.split(",");

		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		if(IbsStateEnum.CLOSE.name().equals(planState)){
			hmPlanService.updatePlanState(existHmId,gameCode,codes,planState);
			return bean.success();
		}
		Date nowTime=new Date();
		JSONArray planItems=msgObj.getJSONArray("PLAN_ITEM_");

		if(ContainerTool.isEmpty(planItems)){
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		Map<String, Map<String,Object>> existPlan=hmPlanService.findPlanInfo(existHmId,gameCode,codes);

		IbscPlanItem planItem=new IbscPlanItem();
		planItem.setExistHmId(existHmId);
		planItem.setCreateTime(nowTime);
		planItem.setCreateTimeLong(nowTime.getTime());
		planItem.setUpdateTime(nowTime);
		planItem.setUpdateTimeLong(nowTime.getTime());
		planItem.setState(IbsStateEnum.OPEN.name());
		IbscPlanItemService planItemService=new IbscPlanItemService();

		IbscHmPlan hmPlan=new IbscHmPlan();
		hmPlan.setExistHmId(existHmId);
		hmPlan.setGameCode(gameCode);
		hmPlan.setPlanState(planState);
		hmPlan.setCreateTime(nowTime);
		hmPlan.setCreateTimeLong(nowTime.getTime());
		hmPlan.setUpdateTime(nowTime);
		hmPlan.setUpdateTimeLong(nowTime.getTime());
		hmPlan.setState(IbsStateEnum.OPEN.name());
		for(int i=0,len=planItems.size();i<len;i++){
			JSONObject planItemInfo=planItems.getJSONObject(i);
			if(existPlan.containsKey(planItemInfo.get("PLAN_CODE_"))){
				hmPlanService.updatePlanState(existPlan.get(planItemInfo.get("PLAN_CODE_")).get("HM_PLAN_ID_"),planState);
				continue;
			}
			planItem.setPlanCode(planItemInfo.get("PLAN_CODE_"));
			planItem.setFundsList(planItemInfo.get("FUNDS_LIST_"));
			planItem.setFollowPeriod(planItemInfo.get("FOLLOW_PERIOD_"));
			planItem.setMonitorPeriod(planItemInfo.get("MONITOR_PERIOD_"));
			planItem.setBetMode(planItemInfo.get("BET_MODE_"));
			planItem.setFundSwapMode(planItemInfo.get("FUND_SWAP_MODE_"));
			planItem.setPeriodRollMode(planItemInfo.get("PERIOD_ROLL_MODE_"));
			planItem.setPlanGroupActiveData(planItemInfo.get("PLAN_GROUP_ACTIVE_DATA_"));
			planItem.setExpandInfo(planItemInfo.get("EXPAND_INFO_"));
			String planItemId=planItemService.save(planItem);

			hmPlan.setPlanItemId(planItemId);
			hmPlan.setPlanCode(planItemInfo.get("PLAN_CODE_"));
			hmPlanService.save(hmPlan);
		}

		new CodingItemThread(existHmId,GameUtil.Code.valueOf(gameCode)).execute(null);

		return bean.success();
	}
}
