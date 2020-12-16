package com.ibm.follow.servlet.client.core.mq.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.core.controller.main.CloseClientController;
import com.ibm.follow.servlet.client.core.controller.main.MigrateClientController;
import com.ibm.follow.servlet.client.core.controller.main.OpenClientController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 代理管理
 * @Author: Dongming
 * @Date: 2019-08-26 14:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ManageListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbmMethodEnum method;
	private String eventId;
	private IbmTypeEnum customerType;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("代理管理，接收的消息是：" + message));
		//消息预处理
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("代理管理，返回的结果是：".concat(bean.toJsonString())));
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbmStateEnum.FAIL.name());
			RabbitMqTool.sendAgentReceipt(bean.toJsonString(), "manage");
			return null;
		}
		try {
			//消息接收-process
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbmStateEnum.PROCESS.name());
			RabbitMqTool.sendAgentReceipt(bean.toJsonString(), "manage");
			// 信息处理
			ClientExecutor execute;
			switch (method) {
				case OPEN:
					//开启客户端
					execute = new OpenClientController(customerType);
					break;
				case CLOSE:
					//关闭客户端
					execute = new CloseClientController(customerType);
					break;
				case CLIENT_MIGRATE:
					//客户端迁移
					execute = new MigrateClientController(customerType);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog("错误的代理管理方法接收".concat(method.name())));
					return null;
			}
            bean = execute.execute(msgObj);
		} catch (Exception e) {
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			log.error(getLog("代理管理,处理错误:".concat(e.getMessage())));
			throw e;
		} finally {
			// 消息处理完成
			bean.setToken(eventId);
			bean.setCommand(method.name());
			bean.setRequestType(IbmStateEnum.SUCCESS.name());
            log.info(getLog("代理管理,处理完成，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendAgentReceipt(bean.toJsonString(), "manage");
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
		String type = msgObj.getString("CUSTOMER_TYPE_");

		if (StringTool.isEmpty(eventId, methodObj, type)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodObj.toString());
		customerType = IbmTypeEnum.valueCustomerTypeOf(type);
		if (customerType == null) {
			bean.putEnum(CodeEnum.IBS_401_METHOD);
			return false;
		}
		return true;

	}

}
