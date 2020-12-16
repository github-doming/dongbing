package com.ibs.plan.module.cloud.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;

/**
 * @Description: 客户设置执行器
 * @Author: null
 * @Date: 2020-06-01 11:39
 * @Version: v1.0
 */
public class ConfigSetExecutor implements CloudMqExecutor{

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj, IbsStateEnum requestType) throws Exception {
		String eventId = msgObj.getString("token");

		IbspEventConfigSetService eventConfigSetService=new IbspEventConfigSetService();
		eventConfigSetService.updateResultByState(eventId,msgObj,requestType);

		return new JsonResultBeanPlus().success();
	}
}
