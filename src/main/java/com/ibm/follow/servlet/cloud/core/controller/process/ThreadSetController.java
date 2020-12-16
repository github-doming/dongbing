package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ThreadTool;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程设置控制器
 * @Author: null
 * @Date: 2020-05-14 15:09
 * @Version: v1.0
 */
public class ThreadSetController {


	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		ThreadPoolExecutor messageExecutor = RabbitMqUtil.findInstance().getExecutor();
		ThreadTool.setCorePoolSize(messageExecutor, NumberTool.getInteger(msgObj.getString("coreCapacity")));
		ThreadTool.setMaximumPoolSize(messageExecutor, NumberTool.getInteger(msgObj.getString("maxCapacity")));
		bean.success();
		return bean;
	}
}
