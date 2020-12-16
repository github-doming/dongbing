package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.ibsc_plan_group_result.service.IbscPlanGroupResultService;
import org.doming.core.tools.StringTool;

/**
 * @Author: null
 * @Date: 2020-06-04 14:24
 * @Version: v1.0
 */
public class SetPlanResetExecutor implements ClientMqExecutor {
	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		if(StringTool.isEmpty(msgObj.get("PLAN_CODES_"),msgObj.get("GAME_CODE_"))){
			new IbscPlanGroupResultService().updatePlanReset(existHmId);
		}else{
			String gameCode = msgObj.getString("GAME_CODE_");
			String[] planCodes=msgObj.getString("PLAN_CODES_").split(",");
			new IbscPlanGroupResultService().updatePlanReset(existHmId,gameCode,planCodes);
		}

		return bean.success();
	}
}
