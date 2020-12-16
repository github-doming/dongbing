package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.thread.CodingItemThread;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 设置方案状态执行器
 * @Author: null
 * @Date: 2020-06-04 14:24
 * @Version: v1.0
 */
public class SetPlanStateExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String planCode=msgObj.getString("PLAN_CODE_");
		String game=msgObj.getString("GAME_CODE_");
		String planState=msgObj.getString("PLAN_STATE_");

		if(StringTool.isEmpty(planCode,game,planState)){
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
		//获取方案信息
		IbscHmPlanService hmPlanService=new IbscHmPlanService();
		Map<String,Object> hmPlanInfo=hmPlanService.isExistPlan(existHmId,planCode,game);
		if(ContainerTool.isEmpty(hmPlanInfo)){
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		//修改方案状态
		hmPlanService.updatePlanState(hmPlanInfo.get("HM_PLAN_ID_"),planState);
		bean.success();

		if(IbsStateEnum.CLOSE.name().equals(planState)){
			return bean;
		}
		new CodingItemThread(existHmId,GameUtil.Code.valueOf(game)).execute(null);

		return bean;
	}
}
