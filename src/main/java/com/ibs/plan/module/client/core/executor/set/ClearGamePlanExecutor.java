package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import com.ibs.plan.module.client.ibsc_plan_item.service.IbscPlanItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;

/**
 * @Description: 清除游戏方案执行器
 * @Author: null
 * @Date: 2020-06-06 14:57
 * @Version: v1.0
 */
public class ClearGamePlanExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean =new JsonResultBeanPlus();

		String gameCode=msgObj.getString("GAME_CODE_");
		String existHmId = msgObj.getString("EXIST_HM_ID_");

		if(StringTool.isEmpty(gameCode,existHmId)){
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		//获取方案详情ids
		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		List<String> planItemIds= hmPlanService.findPlanItemIds(existHmId,gameCode);
		if(ContainerTool.isEmpty(planItemIds)){
			bean.success();
			return bean;
		}
		//清除方案详情信息
		IbscPlanItemService planItemService=new IbscPlanItemService();
		planItemService.clearPlanItem(planItemIds);

		//清除方案信息
		hmPlanService.clearPlanGame(existHmId,gameCode);

		//TODO 是否清除该游戏待投注信息

		return bean.success();
	}
}
