package com.ibs.plan.common.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsMethodEnum;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * 智能投注 客户端MQ 基类
 * @Author: null
 * @Date: 2020-05-14 15:39
 * @Version: v1.0
 */
public abstract class IbsCommMq extends BaseCommMq {

	protected JsonResultBeanPlus bean = new JsonResultBeanPlus();
	protected JSONObject msgObj;
	protected IbsMethodEnum method;

	@Override protected boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		msgObj = JSON.parseObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		Object methodObj = msgObj.get("METHOD_");
		if (StringTool.isEmpty( methodObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		method = IbsMethodEnum.valueOf(methodObj.toString());
		return false;
	}

}
