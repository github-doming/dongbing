package com.ibm.follow.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_heartbeat.service.IbmClientHeartbeatService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 客户机心跳控制
 * @Author: null
 * @Date: 2020-03-24 17:18
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/heartbeat", method = HttpConfig.Method.POST)
public class ClientHeartbeatAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String clientCode = dataMap.getOrDefault("clientCode", "").toString();
		//状态为open 或者close
		String heartbeatState = dataMap.getOrDefault("heartbeatState", "").toString();

		try {
			Map<String, Object> client = new IbmClientService().findInfo(clientCode);
			//不存在或者客户机状态不为close
			if (ContainerTool.isEmpty(client) ) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			new IbmClientHeartbeatService().updateState(clientCode,heartbeatState);

			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbmMethodEnum.HEARTBEAT.name());
			content.put("methodType", heartbeatState);
			String result = RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}

			bean.success();
		} catch (Exception e) {
			log.error("客户机心跳控制错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
