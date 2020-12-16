package com.ibs.plan.module.mq.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.core.controller.process.ThreadSetController;
import com.ibs.plan.module.cloud.core.executor.CloudMqExecutor;
import com.ibs.plan.module.cloud.core.executor.LoginExecutor;
import com.ibs.plan.module.cloud.core.executor.LogoutExecutor;
import com.ibs.plan.module.cloud.core.executor.VailLoginExecutor;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

/**
 * 请求处理监听器
 * @Author: null
 * @Date: 2020-05-09 15:23
 * @Version: v1.0
 */
public class RequestHandleListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private IbsStateEnum requestType;
	private IbsMethodEnum method;
	private JSONObject msgObj;

	@Override public String execute(String message) throws Exception {
		log.info("会员登录，接收的消息是：{}", message);
		//消息预处理
		if (valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("会员管理，处理的结果是：{}"),bean.toString());
			return null;
		}
		try {
			CloudMqExecutor execute;
			switch (method) {
				case LOGIN:
					execute=new LoginExecutor();
					break;
				case VALI_LOGIN:
					execute=new VailLoginExecutor();
					break;
				case LOGOUT:
					execute=new LogoutExecutor();
					break;
				case THREAD_SET:
					execute=new ThreadSetController();
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的会员管理方法接收:{}"),method.name());
					return null;
			}
			bean=execute.execute(msgObj,requestType);
			log.debug(getLog("会员管理，处理完成，处理的结果是：".concat(bean.toString())));
		} catch (Exception e) {
			log.error(getLog("会员管理,处理错误:{}"),e.getMessage());
			throw e;
		}
		return null;
	}


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
		String requestTypeStr = msgObj.getString("requestType");
		if (StringTool.isEmpty(requestTypeStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		requestType = IbsStateEnum.valueOf(requestTypeStr);
		Object methodObj = msgObj.get("command");
		if (StringTool.isEmpty( methodObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		method = IbsMethodEnum.valueOf(methodObj.toString());

		return false;
	}
}
