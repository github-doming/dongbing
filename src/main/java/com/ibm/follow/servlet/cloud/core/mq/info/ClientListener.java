package com.ibm.follow.servlet.cloud.core.mq.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.ClientHeartbeatController;
import com.ibm.follow.servlet.cloud.core.controller.process.ClientMigrateController;
import com.ibm.follow.servlet.cloud.core.controller.process.ClientMonitorController;
import com.ibm.follow.servlet.cloud.core.controller.process.VrClientHeartbeatController;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 客户端信息
 * @Author: Dongming
 * @Date: 2019-08-26 16:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientListener extends BaseCommMq {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private IbmMethodEnum method;
	private JSONObject msgObj;

	@Override public String execute(String message) throws Exception {
		log.info(getLog("校验信息，接收的消息是：" + message));
		if (!valiParameter(message)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			log.error(getLog("校验信息，处理的结果是：".concat(bean.toJsonString())));
			return null;
		}

		try {
			switch (method) {
				case CLIENT_HEARTBEAT:
					//  客户端心跳
					bean = new ClientHeartbeatController().execute(msgObj);
					break;
				case CLIENT_MIGRATE:
					bean = new ClientMigrateController().execute(msgObj);
					break;
				case CLIENT_MONITOR:
					bean = new ClientMonitorController().execute(msgObj);
					break;
				case VR_CLIENT_HEARTBEAT:
					//  客户端心跳
					bean = new VrClientHeartbeatController().execute(msgObj);
					break;
				default:
					bean.putEnum(CodeEnum.IBS_404_METHOD);
					bean.putSysEnum(CodeEnum.CODE_404);
					log.error(getLog( "错误的校验方法接收".concat(method.name())));
					return null;

			}
			log.debug(getLog("校验信息，处理完成，处理的结果是："+bean.toJsonString()));
		} catch (Exception e) {
			log.error(getLog("校验信息,处理错误:"+e.getMessage()));
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

		String methodStr = msgObj.getString("method");
		if (StringTool.isEmpty(methodStr)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return false;
		}
		method = IbmMethodEnum.valueOf(methodStr);
		return true;
	}
}
