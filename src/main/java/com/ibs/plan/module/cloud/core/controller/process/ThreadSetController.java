package com.ibs.plan.module.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.executor.CloudMqExecutor;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ThreadTool;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-07-22 09:41
 * @Version: v1.0
 */
public class ThreadSetController implements CloudMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj, IbsStateEnum requestType) throws Exception {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		ThreadPoolExecutor messageExecutor = RabbitMqUtil.findInstance().getExecutor();
		ThreadTool.setCorePoolSize(messageExecutor, NumberTool.getInteger(msgObj.getString("coreCapacity")));
		ThreadTool.setMaximumPoolSize(messageExecutor, NumberTool.getInteger(msgObj.getString("maxCapacity")));
		bean.success();
		return bean;

	}
}
