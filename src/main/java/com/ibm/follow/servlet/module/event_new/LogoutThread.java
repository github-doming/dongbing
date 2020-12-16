package com.ibm.follow.servlet.module.event_new;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 登出线程
 * @Author: admin1
 * @Date: 2020-10-13 16:03
 */
public class LogoutThread extends CommEventThread {
	private IbmEventClientCloseService clientCloseServic = new IbmEventClientCloseService();
	private List<String> eventIds;
	private IbmTypeEnum customerType;

	public LogoutThread(List<String> eventIds, IbmTypeEnum customerType) {
		this.eventIds = eventIds;
		this.customerType = customerType;
	}
	public LogoutThread(String eventId, IbmTypeEnum customerType) {
		this(new ArrayList<>(1), customerType);
		eventIds.add(eventId);
	}

	@Override public String execute(String inVar) throws Exception {
		if (ContainerTool.isEmpty(eventIds)) {
			log.error("开启登录线程错误,事件id={}", eventIds);
			return null;
		}

		Map<String, Object> eventInfo = clientCloseServic.findEventInfos(eventIds);
		for (Map.Entry<String, Object> entry : eventInfo.entrySet()) {
			String eventId = entry.getKey();
			JSONObject content = JSONObject.parseObject(entry.getValue().toString());
			sendVailLoginInfo(content,eventId);
		}

		return null;
	}

	private void sendVailLoginInfo(JSONObject content,String eventId) throws Exception {
		content.put("EVENT_ID_", eventId);
		content.put("METHOD_", IbmMethodEnum.LOGOUT.name());
		content.put("CUSTOMER_TYPE_", customerType);

		String clientCode = content.getString("CLIENT_CODE_");
		switch (customerType) {
			case MEMBER:
				log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "login"));
				break;
			case AGENT:
				log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login"));
				break;
			default:
				log.info("验证失败，错误的客户类型："+customerType.name());
				break;
		}
	}
}
