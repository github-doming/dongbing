package com.ibs.plan.module.client.core.mq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.servlet.boot.IbsModuleListener;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.core.mq.controller.*;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

/**
 * 客户端基础信息监听器
 * @Author: Dongming
 * @Date: 2020-05-09 15:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientInfoListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private JSONObject msgObj;
	private IbsMethodEnum method;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("客户端信息，接收的消息是：" + message));
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setToken(IbsModuleListener.servletCode());
			bean.setCommand(method.name());
			bean.setRequestType(IbsStateEnum.FAIL.name());
			log.error(getLog("客户端信息，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "client");
			return null;
		}
		try {
			//消息接收不需要process
			//信息处理

			switch (method){
				case CAPACITY_SET:
					bean = new CapacitySetController().execute(msgObj);
					break;
				case HANDICAP_CAPACITY_SET:
					bean = new HandicapCapacitySetController().execute(msgObj);
					break;
				case HANDICAP_CAPACITY_DEL:
					bean = new HandicapCapacityDelController().execute(msgObj);
					break;
				case CLIENT_DEL:
					bean = new ClientDelController().execute(msgObj);
					break;
				case SEAL_TIME:
					bean = new SealTimeSetController().execute(msgObj);
					break;
				case CLIENT_MIGRATE:
					break;
				case REPLACE:
					break;
				case CLEAR:
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的客户配置方法接收".concat(method.name())));
					return null;
			}
		} catch (Exception e) {
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			log.error(getLog("客户端信息,处理错误:".concat(e.getMessage())));
			throw e;
		} finally {
			// 消息处理完成
			bean.setToken(IbsModuleListener.servletCode());
			bean.setCommand(method.name());
			bean.setRequestType(IbsStateEnum.SUCCESS.name());
			log.info(getLog("客户端信息,处理完成，返回的结果是：".concat(bean.toJsonString())));
			RabbitMqTool.sendConfigReceipt(bean.toJsonString(), "client");
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
		Object methodObj = msgObj.get("METHOD_");

		if (StringTool.isEmpty(methodObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbsMethodEnum.valueOf(methodObj.toString());

		return true;

	}
}
