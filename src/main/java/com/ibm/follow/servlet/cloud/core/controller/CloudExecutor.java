package com.ibm.follow.servlet.cloud.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import org.doming.core.Executor;
/**
 * @Description: 执行接口类
 * @Author: zjj
 * @Date: 2019-08-29 16:26
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public interface CloudExecutor extends Executor {
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @param requestType 执行类型
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception{
		return null;
	}
}
