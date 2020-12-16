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

import java.util.Map;

/**
 * @Description: 删除游戏方案执行器
 * @Author: null
 * @Date: 2020-06-06 15:44
 * @Version: v1.0
 */
public class DelGamePlanExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean =new JsonResultBeanPlus();

		String gameCode=msgObj.getString("GAME_CODE_");
		String planCode=msgObj.getString("PLAN_CODE_");
		String existHmId = msgObj.getString("EXIST_HM_ID_");

		if(StringTool.isEmpty(gameCode,planCode,existHmId)){
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		Map<String,Object> hmPlanInfo=hmPlanService.isExistPlan(existHmId,planCode,gameCode);

		if(ContainerTool.isEmpty(hmPlanInfo)){
			return bean.success();
		}
		//删除方案详情
		new IbscPlanItemService().delPlanItem(hmPlanInfo);
		//删除会员方案
		hmPlanService.delHmPlan(hmPlanInfo);

		//TODO 待投注信息处理

		return bean.success();
	}
}
