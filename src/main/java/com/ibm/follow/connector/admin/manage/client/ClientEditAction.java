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
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 客户端容量设置
 * @Author: null
 * @Date: 2020-03-24 16:42
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/edit", method = HttpConfig.Method.POST)
public class ClientEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String clientCode = dataMap.getOrDefault("clientCode", "").toString();
		String clientCapacityMax = dataMap.getOrDefault("clientCapacityMax", "").toString();
		String state = dataMap.getOrDefault("state", "").toString();

		try {
			// 查询原始数据，有改动则执行修改
			Map<String, Object> map = new IbmClientCapacityService().findcapacityInfo(clientCode);
			if (!map.get("CLIENT_CODE_").toString().equals(clientCode) || !map.get("STATE_").toString().equals(state)) {
				IbmClientService clientService = new IbmClientService();
				clientService.updateState(clientCode, state);
			}
			if (StringTool.notEmpty(clientCapacityMax) || !map.get("CLIENT_CAPACITY_MAX_").toString().equals(clientCapacityMax)) {
				new IbmClientCapacityService().updateCapacity(clientCode, NumberTool.getInteger(clientCapacityMax));
				//直接发送消息，所有客户端设置信息不需要通过事件来处理
				JSONObject content = new JSONObject();
				content.put("clientCapacityMax", clientCapacityMax);
				content.put("METHOD_", IbmMethodEnum.CAPACITY_SET.name());
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
			}

			bean.success();
		} catch (Exception e) {
			log.error("客户端容量设置错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
