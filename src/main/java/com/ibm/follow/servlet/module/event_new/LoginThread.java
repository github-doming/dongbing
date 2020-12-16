package com.ibm.follow.servlet.module.event_new;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientByPercent;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientController;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 登录处理线程
 * @Author: admin1
 * @Date: 2020-10-12 15:06
 */
public class LoginThread extends CommEventThread {
	private List<String> eventIds;

	private IbmTypeEnum customerType;

	public LoginThread(List<String> eventIds, IbmTypeEnum customerType) {
		this.eventIds = eventIds;
		this.customerType = customerType;
	}

	public LoginThread(String eventId, IbmTypeEnum customerType) {
		this(new ArrayList<>(1), customerType);
		eventIds.add(eventId);
	}

	@Override
	public String execute(String inVar) throws Exception {
		if (ContainerTool.isEmpty(eventIds)) {
			log.error("开启登录线程错误,事件id={}", eventIds);
			return null;
		}
		IbmEventLoginService loginService = new IbmEventLoginService();
		Map<String, Object> eventInfo = loginService.findEventInfos(eventIds);
		if (ContainerTool.isEmpty(eventInfo)) {
			log.error("开启登录线程错误,事件id={}", eventIds);
			return null;
		}
		// 自动登录处理代理、会员需分开处理
		Map<String, Map<String, Object>> loginInfo;
		Map<String, Object> existInfo;
		JSONObject content = new JSONObject();
		String eventId,clientCode;
		switch (customerType) {
			case MEMBER:
				loginInfo = new IbmHandicapMemberService().findLoginInfo(eventInfo.keySet());

				IbmClientHmService clientHmService = new IbmClientHmService();
				for (Map.Entry<String, Map<String, Object>> entry : loginInfo.entrySet()) {
					//执行事件，获取结果
					eventId = eventInfo.get(entry.getKey()).toString();
					content.clear();
					content.put("EVENT_ID_", eventId);
					content.put("METHOD_", IbmMethodEnum.LOGIN.name());
					content.put("CUSTOMER_TYPE_", customerType);
					content.putAll(entry.getValue());
					existInfo = clientHmService.findExistHmId(content.getString("HANDICAP_MEMBER_ID_"));
					if (ContainerTool.isEmpty(existInfo)) {
						existInfo = new FindClientController().strategy(new FindClientByPercent())
								.findUsableClient(content.getString("HANDICAP_CODE_"), customerType);
					}
					if (ContainerTool.isEmpty(existInfo)) {
						log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员", eventId);
						content.clear();
						content.put("code", "error");
						content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
						loginService.updateResultByState(eventId, content, IbmStateEnum.FINISH);
						continue;
					}
					clientCode = existInfo.get("CLIENT_CODE_").toString();
					log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "login"));
				}
				break;
			case AGENT:
				loginInfo = new IbmHandicapAgentService().findLoginInfo(eventInfo.keySet());
				IbmClientHaService clientHaService = new IbmClientHaService();
				for (Map.Entry<String, Map<String, Object>> entry : loginInfo.entrySet()) {
					//执行事件，获取结果
					eventId = eventInfo.get(entry.getKey()).toString();

					content.clear();
					content.put("EVENT_ID_", eventId);
					content.put("METHOD_", IbmMethodEnum.LOGIN.name());
					content.put("CUSTOMER_TYPE_", customerType);
					content.putAll(entry.getValue());
					existInfo =clientHaService.findExistHaId(content.getString("HANDICAP_AGENT_ID_"));
					if (ContainerTool.isEmpty(existInfo)) {
						existInfo = new FindClientController().strategy(new FindClientByPercent())
								.findUsableClient(content.getString("HANDICAP_CODE_"), customerType);
					}
					if (ContainerTool.isEmpty(existInfo)) {
						log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员", eventId);
						content.clear();
						content.put("code", "error");
						content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
						loginService.updateResultByState(eventId, content, IbmStateEnum.FINISH);
						continue;
					}
					clientCode = existInfo.get("CLIENT_CODE_").toString();
					log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login"));
				}
				break;
			default:
				log.error("登录失败,错误的客户类型" + customerType.name());
				return null;
		}
		return null;
	}

}
