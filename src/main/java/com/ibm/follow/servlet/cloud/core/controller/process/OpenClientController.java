package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.ibm_event_client_open.service.IbmEventClientOpenService;

/**
 * @Description: 开启客户端控制器
 * @Author: zjj
 * @Date: 2019-09-05 09:52
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class OpenClientController implements CloudExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		//事件id
		String eventId = msgObj.getString("token");
		if(!new IbmEventClientOpenService().updateResultByState(eventId,msgObj,requestType)){
			bean.putEnum(CodeEnum.IBS_404_TYPE);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		bean.success();
		return bean;
	}
}
