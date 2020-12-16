package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
/**
 * @Description: 客户设置信息控制器
 * @Author: null
 * @Date: 2019-09-24 15:44
 * @Version: v1.0
 */
public class ConfigSetController implements CloudExecutor {

	@Override public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		//事件id
		String eventId = msgObj.getString("token");
		if(!new IbmEventConfigSetService().updateResultByState(eventId,msgObj,requestType)){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		bean.success();
		return bean;
	}
}
