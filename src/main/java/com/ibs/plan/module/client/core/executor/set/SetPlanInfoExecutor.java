package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.thread.CodingItemThread;
import com.ibs.plan.module.client.ibsc_hm_plan.entity.IbscHmPlan;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import com.ibs.plan.module.client.ibsc_plan_item.entity.IbscPlanItem;
import com.ibs.plan.module.client.ibsc_plan_item.service.IbscPlanItemService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 设置方案信息执行器
 * @Author: null
 * @Date: 2020-06-03 14:09
 * @Version: v1.0
 */
public class SetPlanInfoExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		JSONArray planInfos = msgObj.getJSONArray("PLAN_INFO_");
		JSONArray planItems = msgObj.getJSONArray("PLAN_ITEM_");
		if (ContainerTool.isEmpty(planInfos)||ContainerTool.isEmpty(planItems)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		Date nowTime=new Date();

		Map<String,String> planItemMap=new HashMap<>(planItems.size());
		//添加方案详情信息
		IbscPlanItemService planItemService=new IbscPlanItemService();
		IbscPlanItem hmPlanItem=new IbscPlanItem();
		hmPlanItem.setExistHmId(existHmId);
		hmPlanItem.setCreateTime(nowTime);
		hmPlanItem.setCreateTimeLong(nowTime.getTime());
		hmPlanItem.setUpdateTime(nowTime);
		hmPlanItem.setUpdateTimeLong(nowTime.getTime());
		hmPlanItem.setState(IbsStateEnum.OPEN.name());
		for(int i=0,len=planItems.size();i<len;i++){
			JSONObject planItem=planItems.getJSONObject(i);
			hmPlanItem.setPlanCode(planItem.get("PLAN_CODE_"));
			hmPlanItem.setFundsList(planItem.get("FUNDS_LIST_"));
			hmPlanItem.setFollowPeriod(planItem.get("FOLLOW_PERIOD_"));
			hmPlanItem.setMonitorPeriod(planItem.get("MONITOR_PERIOD_"));
			hmPlanItem.setBetMode(planItem.get("BET_MODE_"));
			hmPlanItem.setFundSwapMode(planItem.get("FUND_SWAP_MODE_"));
			hmPlanItem.setPeriodRollMode(planItem.get("PERIOD_ROLL_MODE_"));
			hmPlanItem.setPlanGroupActiveData(planItem.getString("PLAN_GROUP_ACTIVE_DATA_"));
			hmPlanItem.setExpandInfo(planItem.get("EXPAND_INFO_"));
			String hmPlanItemId=planItemService.save(hmPlanItem);
			planItemMap.put(planItem.getString("PLAN_ITEM_TABLE_ID_"),hmPlanItemId);
		}
		List<Object> openGameCode=new ArrayList<>();
		//判断是否已存在，存在则删除
		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		IbscHmPlan hmPlan=new IbscHmPlan();
		hmPlan.setExistHmId(existHmId);
		hmPlan.setCreateTime(nowTime);
		hmPlan.setCreateTimeLong(nowTime.getTime());
		hmPlan.setUpdateTime(nowTime);
		hmPlan.setUpdateTimeLong(nowTime.getTime());
		hmPlan.setState(IbsStateEnum.OPEN.name());
		for(int i=0,len=planInfos.size();i<len;i++){
			JSONObject planInfo=planInfos.getJSONObject(i);
			String planItemId=planItemMap.get(planInfo.get("PLAN_ITEM_TABLE_ID_"));
			hmPlan.setPlanCode(planInfo.get("PLAN_CODE_"));
			hmPlan.setPlanItemId(planItemId);
			hmPlan.setGameCode(planInfo.get("GAME_CODE_"));
			hmPlan.setPlanState(planInfo.get("PLAN_STATE_"));
			hmPlanService.save(hmPlan);

			if(IbsStateEnum.OPEN.name().equals(planInfo.get("PLAN_STATE_"))&&!openGameCode.contains(planInfo.get("GAME_CODE_"))){
				openGameCode.add(planInfo.get("GAME_CODE_"));
			}
		}
		CurrentTransaction.transactionCommit();
		//开启一个线程，进行计算。
		ThreadPoolExecutor executorService = ThreadExecuteUtil.findInstance().getCoreExecutor();
		for(Object gameCode:openGameCode){
			executorService.execute(new CodingItemThread(existHmId, GameUtil.Code.valueOf(gameCode.toString())));
		}

		bean.success();
		return bean;
	}

}
