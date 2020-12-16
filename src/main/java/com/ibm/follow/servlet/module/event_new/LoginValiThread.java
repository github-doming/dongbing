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
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.service.IbmEventLoginValiService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.tools.ContainerTool;

import java.util.Map;

/**
 * @Description: 登录验证
 * @Author: admin1
 * @Date: 2020-10-13 13:52
 */
public class LoginValiThread extends CommEventThread {

	private String eventId;

	public LoginValiThread(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public String execute(String inVar) throws Exception {
		IbmEventLoginValiService loginValiService = new IbmEventLoginValiService();
		Map<String, Object> eventInfo = loginValiService.findEventInfo(eventId);
		if (ContainerTool.isEmpty(eventInfo)) {
			log.error("开启验证登录线程错误,事件id={}", eventId);
			return null;
		}

		sendVailLoginInfo(eventInfo);
		return null;
	}

	private void sendVailLoginInfo(Map<String, Object> eventInfo) throws Exception {
		IbmEventLoginValiService loginValiService = new IbmEventLoginValiService();
		String customerId = eventInfo.get("CUSTOMER_ID_").toString();
		IbmTypeEnum customerType =IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());

		JSONObject content = new JSONObject();
		content.put("EVENT_ID_", eventId);
		content.put("METHOD_", IbmMethodEnum.VALI_LOGIN.name());
		content.put("CUSTOMER_TYPE_", customerType);

		Map<String, Object> loginInfo;
		Map<String, Object> existInfo;
		switch (customerType) {
			case MEMBER:
				loginInfo = new IbmHandicapMemberService().findLoginValiInfo(customerId);
				if (ContainerTool.isEmpty(loginInfo)) {
					log.error("开启验证登录线程错误,事件id={}", eventId);
					break;
				}
				content.put("HANDICAP_MEMBER_ID_", customerId);
				content.putAll(loginInfo);
				existInfo = new IbmClientHmService().findExistHmId(content.getString("HANDICAP_MEMBER_ID_"));
				if (ContainerTool.isEmpty(existInfo)) {
					existInfo = new FindClientController().strategy(new FindClientByPercent())
							.findUsableClient(content.getString("HANDICAP_CODE_"), customerType);
				}
				if (ContainerTool.isEmpty(existInfo)) {
					log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员", eventId);
					content.clear();
					content.put("code", "error");
					content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
					loginValiService.updateResultByState(eventId, content, IbmStateEnum.FINISH);
					break;
				}
				String clientCode = existInfo.get("CLIENT_CODE_").toString();
				log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "login"));
				break;
			case AGENT:
				loginInfo = new IbmHandicapAgentService().findLoginInfo(customerId);
				if (ContainerTool.isEmpty(loginInfo)) {
					log.error("开启验证登录线程错误,事件id={}", eventId);
					break;
				}
				content.put("HANDICAP_AGENT_ID_", customerId);
				content.putAll(loginInfo);
				existInfo = new IbmClientHaService().findExistHaId(content.getString("HANDICAP_AGENT_ID_"));
				if (ContainerTool.isEmpty(existInfo)) {
					existInfo = new FindClientController().strategy(new FindClientByPercent())
							.findUsableClient(content.getString("HANDICAP_CODE_"), customerType);
				}
				if (ContainerTool.isEmpty(existInfo)) {
					log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员", eventId);
					content.clear();
					content.put("code", "error");
					content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
					loginValiService.updateResultByState(eventId, content, IbmStateEnum.FINISH);
					break;
				}
				clientCode = existInfo.get("CLIENT_CODE_").toString();
				log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login"));
				break;
			default:
				log.info("验证失败，错误的客户类型："+customerType.name());
				break;
		}
	}


}
