package com.ibm.follow.servlet.module.event_new;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import org.doming.core.tools.ContainerTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户设置任务
 * @Author: admin1
 * @Date: 2020-10-13 17:28
 */
public class ConfigSetThread extends CommEventThread {
	private IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
	private List<String> eventIds;
	private IbmTypeEnum customerType;

	public ConfigSetThread(List<String> eventIds,IbmTypeEnum customerType) {

		this.eventIds = eventIds;
		this.customerType = customerType;
	}


	@Override
	public String execute(String ignore) throws Exception {
		if (ContainerTool.isEmpty(eventIds)) {
			log.error("开启用户设置线程错误,事件id={}", eventIds);
			return null;
		}

		Map<String, String> eventInfo = configSetService.findEventInfos(eventIds);
		if (ContainerTool.isEmpty(eventInfo)) {
			log.error("开启用户设置线程错误,事件id={}", eventIds);
			return null;
		}
		Map<String, Object> existInfo;
		switch (customerType){
			case MEMBER:
				IbmClientHmService clientHmService = new IbmClientHmService();
				for(Map.Entry<String,String> entry:eventInfo.entrySet()){
					JSONObject content = JSONObject.parseObject(entry.getValue());
					String handicapMemberId = content.remove("HANDICAP_MEMBER_ID_").toString();
					content.put("EVENT_ID_", entry.getKey());
					existInfo = clientHmService.findExistHmId(handicapMemberId);
					if (ContainerTool.isEmpty(existInfo)) {
						log.info("发送盘口会员设置消息失败，不存在盘口会员id：".concat(handicapMemberId));
						return IbmTypeEnum.FALSE.name();
					}
					content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
					String clientCode = existInfo.get("CLIENT_CODE_").toString();
					RabbitMqTool.sendClientConfig(content.toString(),clientCode,"set");
				}
				break;

			case AGENT:
				IbmClientHaService clientHaService = new IbmClientHaService();
				for(Map.Entry<String,String> entry:eventInfo.entrySet()){
					JSONObject content = JSONObject.parseObject(entry.getValue());
					String handicapAgentId = content.remove("HANDICAP_AGENT_ID_").toString();
					content.put("EVENT_ID_", entry.getKey());
					existInfo = clientHaService.findExistHaId(handicapAgentId);
					if (ContainerTool.isEmpty(existInfo)) {
						log.info("发送盘口会员设置消息失败，不存在盘口会员id：".concat(handicapAgentId));
						return IbmTypeEnum.FALSE.name();
					}
					content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
					String clientCode = existInfo.get("CLIENT_CODE_").toString();
					RabbitMqTool.sendClientConfig(content.toString(),clientCode,"set");
				}
				break;

			default:
				log.info("验证失败，错误的客户类型："+customerType.name());
		}

		return null;
	}
}
