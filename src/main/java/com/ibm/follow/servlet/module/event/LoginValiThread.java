package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientByPercent;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientController;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.service.IbmEventLoginValiService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证登录任务
 *
 * @Author: Dongming
 * @Date: 2019-12-24 16:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginValiThread extends CommEventThread {
	private Map<String, Object> eventInfo;
	private IbmEventLoginValiService loginValiService = new IbmEventLoginValiService();

	public LoginValiThread(Map<String, Object> eventInfo) {
		this.eventInfo = eventInfo;
	}

	@Override public String execute(String ignore) throws Exception {
		//事件id
		String eventId = eventInfo.get("EVENT_ID_").toString();

		//执行事件，获取结果
		JSONObject result = loginVali(eventId);
		if (ContainerTool.isEmpty(result)) {
			result = new JSONObject();
			result.put("code", "error");
			result.put("msg", "用户登录失败,请稍后再试");
		}
		loginValiService.updateResult(eventId, result);

		return null;
	}

	private JSONObject loginVali(String eventId) throws Exception {
		JSONObject resultJson = new JSONObject();
		IbmTypeEnum customerType = IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());

		JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
		String handicapCode = content.getString("HANDICAP_CODE_");
		// 获取可用客户机
		Map<String, Object> clientInfo = new FindClientController().strategy(new FindClientByPercent())
				.findVerifyClient(handicapCode, customerType);
		if (ContainerTool.isEmpty(clientInfo)) {
			log.info("验证登录失败，客户机容量已满");
			return resultJson;
		}
		String clientCode = clientInfo.get("CLIENT_CODE_").toString();
		//mq发送消息
		String result = sendVailLoginInfo(customerType, content, clientCode, eventId);
		if (!Boolean.parseBoolean(result)) {
			log.error("验证登录，发送消息失败");
			return resultJson;
		}
		//监听事件结果 - 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
		Map<String, Object> eventMap = listenEventResult(eventId);
		if (ContainerTool.isEmpty(eventMap) || StringTool.isEmpty(eventMap.get("EVENT_RESULT_"))) {
			log.error("验证登录，事件状态异常，事件id=".concat(eventId));
			return resultJson;
		}
		JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
		if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_500.getCode(),
				CodeEnum.CODE_401.getCode())) {
			log.info("用户验证登录失败=" + eventMap.get("EVENT_RESULT_"));
			resultJson.put("code", "error");
			resultJson.put("msg", "用户验证登录参数错误");
			return resultJson;
		}
		try {
			CurrentTransaction.beginTransaction();
			//客户机信息
			JSONObject data = eventResultJson.getJSONObject("data");
			String clientId = clientInfo.getOrDefault("CLIENT_ID_", "").toString();
			//更新容量信息 capacity
			EventThreadDefine.updateCapacity(data.getJSONObject("CAPACITY_"), clientId, clientCode,
					content.getString("HANDICAP_CODE_"));
			//403为容量已满，重新验证
			if (StringTool.contains(eventResultJson.getString("code"), CodeEnum.IBS_403_MAX_CAPACITY.getCode())) {
				return resultJson;
			}
			if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_404.getCode(),
					CodeEnum.CODE_403.getCode())) {
				log.info("用户验证登录失败=" + eventMap.get("EVENT_RESULT_"));
				resultJson.put("code", eventResultJson.getString("code"));
				resultJson.put("msg", eventResultJson.getString("msg"));
				return resultJson;
			}
			//验证成功,开启客户端
			if (eventResultJson.getBoolean("success")) {
				// 绑定客户端,添加登录信息
				String hmOrHaId = EventThreadDefine.saveCustomerInfo(data, customerType, content, clientId, clientCode);
				resultJson.put("data", hmOrHaId);
			}
			resultJson.put("code", eventResultJson.getString("codeSys"));
			resultJson.put("msg", eventResultJson.getString("msg"));
		} catch (Exception e) {
			CurrentTransaction.rollTransaction();
			log.error("验证盘口会员失败", e);
		} finally {
			CurrentTransaction.endTransaction();
		}
		return resultJson;
	}
	/**
	 * 监听事件结果
	 *
	 * @param eventId 事件id
	 * @return 事件结果
	 */
	private Map<String, Object> listenEventResult(String eventId) throws Exception {
		Map<String, Object> eventMap = null;
		int pollNum = 15;
		for (int i = 0; i < pollNum; i++) {
			eventMap = loginValiService.findEventResult(eventId);
			//处理状态为send,process时，继续等待
			switch (IbmStateEnum.valueOf(eventMap.get("EVENT_STATE_").toString())) {
				case SEND:
					Thread.sleep(RandomTool.getInt(1000, 2000));
					continue;
				case PROCESS:
					if (pollNum == 15) {
						pollNum = 30;
					}
					Thread.sleep(RandomTool.getInt(500, 1500));
					continue;
				case FAIL:
					log.error("用户验证登录错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
					return new HashMap<>(1);
				case SUCCESS:
					break;
				default:
					log.error("验证登录，事件状态异常，事件id=".concat(eventId));
					return new HashMap<>(1);
			}
			break;
		}
		return eventMap;
	}

	/**
	 * 发送验证登录请求信息
	 *
	 * @param customerType 客户类型
	 * @param content      消息内容
	 * @param clientCode   客户端编码
	 * @param eventId      事件id
	 * @return 发送结果
	 */
	private String sendVailLoginInfo(IbmTypeEnum customerType, JSONObject content, String clientCode, String eventId)
			throws Exception {
		//存入事件id
		content.put("EVENT_ID_", eventId);
		content.put("CUSTOMER_TYPE_", customerType.name());
		content.put("METHOD_", IbmMethodEnum.VALI_LOGIN.name());
		switch (customerType) {
			case MEMBER:
				return RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "login");
			case AGENT:
				return RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login");
			default:
				log.info("验证失败，错误的客户类型：".concat(customerType.name()));
				return TypeEnum.FALSE.name();
		}
	}
}
