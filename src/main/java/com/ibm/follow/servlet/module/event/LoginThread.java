package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.entity.IbmEventClientClose;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.module.event.controller.OpenClientController;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 用户登录任务
 *
 * @Author: Dongming
 * @Date: 2019-12-24 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginThread extends CommEventThread {
	private Map<String, Object> eventInfo;
	private IbmClientHmService clientHmService = new IbmClientHmService();

	private IbmClientHaService clientHaService = new IbmClientHaService();

	private IbmEventLoginService loginService = new IbmEventLoginService();

	private IbmTypeEnum customerType;
	public LoginThread(Map<String, Object> eventInfo) {
		this.eventInfo = eventInfo;
	}

	@Override public String execute(String ignore) throws Exception {
		//事件id
		String eventId = eventInfo.get("EVENT_ID_").toString();
		//执行事件，获取结果
		JSONObject result = login(eventId);
		if (ContainerTool.isEmpty(result)) {
			result = new JSONObject();
			result.put("code", "error");
			result.put("msg", "用户登录失败,请稍后再试");
		}
		//写入结果
		loginService.updateResult(eventId, result);

		return null;
	}

	private JSONObject login(String eventId) throws Exception {
		JSONObject resultJson = new JSONObject();
		//事件内容
		JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
		content.put("EVENT_ID_", eventId);
		//发送登录信息
		String result = sendLoginInfo(content);
		if (StringTool.isEmpty(result)) {
			return resultJson;
		}
		if (StringTool.isContains(result, "code")) {
			return JSONObject.parseObject(result);
		}
		if (!Boolean.parseBoolean(result)) {
			log.error("登录，发送消息失败");
			return resultJson;
		}
		//监听登录事件结果- 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
		Map<String, Object> eventMap = listeningEventResult(eventId);
		if (ContainerTool.isEmpty(eventMap)||StringTool.isEmpty(eventMap.get("EVENT_RESULT_"))) {
			log.error("登录事件状态异常，事件id=".concat(eventId));
			return resultJson;
		}
		JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
		//参数错误
		if (StringTool.contains(eventResultJson.getString("codeSys"), CodeEnum.CODE_500.getCode(),
				CodeEnum.CODE_401.getCode())) {
			log.info("用户登录失败=" + eventMap.get("EVENT_RESULT_"));
			resultJson.put("code", "error");
			resultJson.put("msg", "用户登录参数错误");
			return resultJson;
		}
		TransactionsBase db = CurrentTransaction.getDataBase();
		try {
			CurrentTransaction.beginTransaction();
			//登录成功
			if (eventResultJson.getBoolean("success")) {
				// 添加登录信息
				String hmOrHaId = EventThreadDefine.saveLoginResult(customerType, eventResultJson.get("data"), content);
				resultJson.put("data", hmOrHaId);
			} else {
				//登录失败，清除数据
				closeClientEvent(content);
			}
			resultJson.put("code", eventResultJson.getString("codeSys"));
			resultJson.put("msg", eventResultJson.getString("msg"));
		} catch (Exception e) {
			CurrentTransaction.rollTransaction();
			log.error("登录失败", e);
		} finally {
			CurrentTransaction.endTransaction();
		}
		return resultJson;
	}
	/**
	 * 关闭开启的客户端
	 *
	 * @param content 事件正文
	 */
	private void closeClientEvent(JSONObject content) throws Exception {
		JSONObject json = new JSONObject();
		switch (customerType) {
			case MEMBER:
				json.put("HANDICAP_MEMBER_ID_", content.getString("HANDICAP_MEMBER_ID_"));
				break;
			case AGENT:
				json.put("HANDICAP_AGENT_ID_", content.getString("HANDICAP_AGENT_ID_"));
				break;
			default:
				break;
		}
		IbmEventClientClose eventClientClose = new IbmEventClientClose();
		eventClientClose.setCustomerType(customerType.name());
		eventClientClose.setEventState(IbmStateEnum.BEGIN.name());
		eventClientClose.setEventContent(json);
		eventClientClose.setExecNumber(0);
		eventClientClose.setCreateTime(new Date());
		eventClientClose.setCreateTimeLong(System.currentTimeMillis());
		eventClientClose.setUpdateTimeLong(System.currentTimeMillis());
		eventClientClose.setState(IbmStateEnum.OPEN.name());
		eventClientClose.setDesc(customerType.getMsg().concat("登录事件,关闭客户端"));
		new IbmEventClientCloseService().save(eventClientClose);
	}

	/**
	 * 监听事件结果
	 *
	 * @param eventId 事件id
	 * @return 事件结果
	 */
	private Map<String, Object> listeningEventResult(String eventId) throws Exception {
		Map<String, Object> eventMap = null;
		int pollNum = 15;
		for (int i = 0; i < pollNum; i++) {
			eventMap = loginService.findEventResult(eventId);
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
					log.error("用户登录错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
					return new HashMap<>(1);
				case SUCCESS:
					break;
				default:
					log.error("登录事件状态异常，事件id=".concat(eventId));
					return new HashMap<>(1);
			}
			break;
		}
		return eventMap;
	}

	private String sendLoginInfo(JSONObject content) throws Exception {
        customerType=IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());

		content.put("HANDICAP_CODE_", HandicapUtil.code(content.getString("HANDICAP_ID_")));
		content.put("CUSTOMER_TYPE_", customerType.name());
		content.put("METHOD_", IbmMethodEnum.LOGIN.name());
		String result;
		switch (customerType) {
			case MEMBER:
				result = sendMemberLogin(content, eventInfo.get("CLIENT_CODE_"));
				break;
			case AGENT:
				result = sendAgentLogin(content, eventInfo.get("CLIENT_CODE_"));
				break;
			default:
				log.error("登录失败,错误的客户类型" + eventInfo.get("CUSTOMER_TYPE_"));
				return null;
		}
		return result;
	}
	/**
	 * 发送代理登录
	 *
	 * @param content          登录结果
	 * @param assignClientCode 指定客户端编码
	 * @return 发送结果
	 */
	private String sendAgentLogin(JSONObject content, Object assignClientCode) throws Exception {
		String handicapAgentId = content.get("HANDICAP_AGENT_ID_").toString();
		Map<String, Object> existInfo = clientHaService.findExistHaId(handicapAgentId);
		if (ContainerTool.isEmpty(existInfo)) {
			JSONObject result = new JSONObject();
			//  开启客户端
			JSONObject json = new JSONObject();
			json.put("HANDICAP_CODE_", content.get("HANDICAP_CODE_"));
			json.put("HANDICAP_AGENT_ID_", handicapAgentId);
			String eventId = EventThreadDefine.saveOpenClientEvent(json, IbmTypeEnum.AGENT,"登录事件,开启客户端",assignClientCode);
			//执行开启客户端事件
			JSONObject openResult = new OpenClientController().execute(eventId);
			if (ContainerTool.isEmpty(openResult)) {
				result.put("code", "error");
				result.put("msg", "登录失败,请联系客服管理人员");
				return result.toJSONString();
			}
			if (!"200".equals(openResult.get("code"))) {
				log.info("登录失败,开启代理客户端失败:".concat(openResult.toString()));
				result.put("code", openResult.get("code"));
				result.put("msg", openResult.get("msg"));
				return result.toJSONString();
			}
			existInfo = clientHaService.findExistHaId(handicapAgentId);
			if (ContainerTool.isEmpty(existInfo)) {
				log.info("登录失败，客户端存在代理信息为空,代理:".concat(handicapAgentId));
				result.put("code", "error");
				result.put("msg", "登录失败,请稍后再试");
				return result.toJSONString();
			}
		}
		String clientCode = existInfo.get("CLIENT_CODE_").toString();
		content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
		return RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "login");
	}

	/**
	 * 发送会员登录
	 *
	 * @param content          登录结果
	 * @param assignClientCode 指定客户端编码
	 * @return 发送结果
	 */
	private String sendMemberLogin(JSONObject content, Object assignClientCode) throws Exception {
		String handicapMemberId = content.get("HANDICAP_MEMBER_ID_").toString();
		Map<String, Object> existInfo = clientHmService.findExistHmId(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)) {
			JSONObject result = new JSONObject();
			//  开启客户端
			JSONObject json = new JSONObject();
			json.put("HANDICAP_CODE_", content.get("HANDICAP_CODE_"));
			json.put("HANDICAP_MEMBER_ID_", handicapMemberId);

			String eventId = EventThreadDefine.saveOpenClientEvent(json, IbmTypeEnum.MEMBER,"登录事件,开启客户端",assignClientCode);
			//执行开启客户端事件
			JSONObject openResult = new OpenClientController().execute(eventId);
			if (ContainerTool.isEmpty(openResult)) {
				result.put("code", "error");
				result.put("msg", "登录失败,请联系客服管理人员");
				return result.toJSONString();
			}
			if (!"200".equals(openResult.get("code"))) {
				log.info("登录失败，开启会员客户端失败:".concat(openResult.toString()));
				result.put("code", openResult.get("code"));
				result.put("msg", openResult.get("msg"));
				return result.toJSONString();
			}
			existInfo = clientHmService.findExistHmId(handicapMemberId);
            if (ContainerTool.isEmpty(existInfo)) {
                log.info("登录失败，客户端存在会员信息为空,会员id:".concat(handicapMemberId));
                result.put("code", "error");
                result.put("msg", "登录失败,请稍后再试");
                return result.toJSONString();
            }
		}
		String clientCode = existInfo.get("CLIENT_CODE_").toString();
		content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
		return RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "login");
	}
}
