package com.ibm.follow.servlet.cloud.core.mq.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.process.CloseClientController;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginController;
import com.ibm.follow.servlet.cloud.core.controller.process.VailLoginController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 代理登录信息
 * @Author: Dongming
 * @Date: 2019-08-26 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private IbmStateEnum requestType;
	private IbmMethodEnum command;
	private JSONObject msgObj;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("代理登录信息，接收的消息是：" + message));
		//消息预处理
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("代理登录信息，处理的结果是：".concat(bean.toJsonString())));
			return null;
		}
		try {
			CloudExecutor execute;
			switch (command) {
				case VALI_LOGIN:
					//验证登录
					execute = new VailLoginController();
					break;
				case LOGIN:
					//登录
					execute = new LoginController();
					break;
				case LOGOUT:
					execute=new CloseClientController();
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的代理登录信息方法接收".concat(command.name())));
					return null;
			}
            bean = execute.execute(msgObj, requestType);
			log.debug(getLog("代理登录信息，处理完成，处理的结果是：".concat(bean.toJsonString())));
		} catch (Exception e) {
			log.error(getLog("代理登录信息,处理错误:".concat(e.getMessage())));
			throw e;
		}

		return null;
	}

	@Override protected boolean valiParameter(String message) {
		if (StringTool.isEmpty(message)) {
			bean.putEnum(CodeEnum.IBS_401_MESSAGE);
			return false;
		}
		msgObj = JSON.parseObject(message);
		if (ContainerTool.isEmpty(msgObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		String requestTypeStr = msgObj.getString("requestType");
		if (StringTool.isEmpty(requestTypeStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		requestType = IbmStateEnum.valueOf(requestTypeStr);

		String commandStr=msgObj.getString("command");
		if(StringTool.isEmpty(commandStr)){
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		command=IbmMethodEnum.valueOf(commandStr);
		return true;
	}
}
