package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 客户端容量设置
 * @Author: null
 * @Date: 2020-03-24 16:42
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/edit", method = HttpConfig.Method.POST)
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
		Date nowTime = new Date();
		try {
			IbspClientService clientService = new IbspClientService();
			String clientId = clientService.findId(clientCode);
			// 查询原始数据，有改动则执行修改
			Map<String, Object> map = new IbspClientCapacityService().findcapacityInfo(clientCode);
			if (!map.get("CLIENT_CODE_").toString().equals(clientCode) || !map.get("STATE_").toString().equals(state)) {

				clientService.updateState(clientId, state, nowTime);
			}
			if (StringTool.notEmpty(clientCapacityMax) || !map.get("CLIENT_CAPACITY_MAX_").toString().equals(clientCapacityMax)) {
				new IbspClientCapacityService().updateCapacity(clientId, NumberTool.getInteger(clientCapacityMax), nowTime);
				//直接发送消息，所有客户端设置信息不需要通过事件来处理
				JSONObject content = new JSONObject();
				content.put("clientCapacityMax", clientCapacityMax);
				content.put("METHOD_", IbsMethodEnum.CAPACITY_SET.name());
				String result = RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
				if (StringTool.isEmpty(result)) {
					bean.putEnum(CodeEnum.IBS_401_MESSAGE);
					bean.putSysEnum(CodeEnum.CODE_401);
					return bean;
				}
				if (!Boolean.parseBoolean(result)) {
					bean.putEnum(CodeEnum.IBS_403_MQ);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
			}

			bean.success();
		} catch (Exception e) {
			log.error("客户端容量设置错误", e);
			return bean.error(e.getMessage());
		}
		return bean;
	}
}
