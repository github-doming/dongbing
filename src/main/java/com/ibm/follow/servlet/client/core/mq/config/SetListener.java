package com.ibm.follow.servlet.client.core.mq.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.core.controller.config.*;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 客户配置
 * @Author: Dongming
 * @Date: 2019-08-26 14:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SetListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbmMethodEnum method;
	private String eventId;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("客户配置，接收的消息是：" + message));
		//消息预处理
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbmStateEnum.FAIL.name());
            log.error(getLog("客户配置，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "set");
			return null;
		}
		try {
			//消息接收-process
			bean.setRequestType(IbmStateEnum.PROCESS.name());
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "set");
			// 信息处理
			ClientExecutor execute;
			switch (method) {
				case MEMBER_SET:
					//会员配置
					execute = new MemberConfigSetController();
					break;
				case AGENT_SET:
					//代理配置
					execute = new AgentConfigSetController();
					break;
				case MANAGE_SET:
                    execute=new ManageConfigSetController();
					break;
				case MANAGE_VR:
					execute=new VrManageConfigSetController();
					break;
				case GAME_VR:
					execute=new VrGameSetController();
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的客户配置方法接收".concat(method.name())));
					return null;
			}
            bean = execute.execute(msgObj);
		} catch (Exception e) {
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			log.error(getLog("客户配置,处理错误:"+e.getMessage()));
			throw e;
		} finally {
			// 消息处理完成
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbmStateEnum.SUCCESS.name());
            log.info(getLog("客户配置,处理完成，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "set");
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
		eventId = msgObj.getString("EVENT_ID_");
		Object methodObj = msgObj.get("METHOD_");

		if (StringTool.isEmpty(eventId, methodObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodObj.toString());
		bean.setToken(eventId);
		bean.setCommand(method.name());
		return true;

	}
}
