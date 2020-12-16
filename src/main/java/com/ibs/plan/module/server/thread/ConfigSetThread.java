package com.ibs.plan.module.server.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.rabbitmq.client.Connection;
import org.doming.core.tools.ContainerTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 用户设置
 * @Author: null
 * @Date: 2020-05-25 14:32
 * @Version: v1.0
 */
public class ConfigSetThread extends CommEventThread {

	private IbspEventConfigSetService configSetService = new IbspEventConfigSetService();
	private List<String> eventIds;
	public ConfigSetThread(List<String> eventIds) {
		this.eventIds = eventIds;
	}
	@Override public String execute(String inVar) throws Exception {
		if (ContainerTool.isEmpty(eventIds)) {
			log.error("开启用户设置线程错误,事件id={}", eventIds);
			return null;
		}
		Map<String, String> eventInfo = configSetService.findEventInfos(eventIds);
		if (ContainerTool.isEmpty(eventInfo)) {
			log.error("开启用户设置线程错误,事件id={}", eventIds);
			return null;
		}
		IbspClientHmService clientHmService = new IbspClientHmService();
		try (Connection connection = RabbitMqTool.createConnection()){
			for(Map.Entry<String,String> entry:eventInfo.entrySet()){
				JSONObject content = JSONObject.parseObject(entry.getValue());
				content.put("EVENT_ID_", entry.getKey());
				Map<String, Object> existHmInfo = clientHmService.findExistHmInfo(content.remove("HANDICAP_MEMBER_ID_").toString());
				content.put("EXIST_HM_ID_", existHmInfo.get("EXIST_HM_ID_"));
				String clientCode = existHmInfo.get("CLIENT_CODE_").toString();
				RabbitMqTool.sendMember(connection,content.toString(),clientCode,"set");
			}
		}
		return null;
	}
}
