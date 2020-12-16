package com.ibs.plan.module.client.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;

/**
 * 客户端执行MQ接口类
 * @Author: null
 * @Date: 2020-05-14 13:44
 * @Version: v1.0
 */
public interface ClientMqExecutor {
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj) throws Exception{
		return null;
	}
}
