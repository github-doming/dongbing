package com.ibs.plan.module.cloud.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.enums.IbsStateEnum;

/**
 * @Description: 服务端执行MQ接口类
 * @Author: null
 * @Date: 2020-05-25 16:19
 * @Version: v1.0
 */
public interface CloudMqExecutor {
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj, IbsStateEnum requestType) throws Exception{
		return null;
	}
}
