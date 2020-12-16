package com.ibs.plan.module.server.thread;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 登出线程
 *
 * @Author: Dongming
 * @Date: 2020-05-26 14:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LogoutThread extends CommEventThread {
	private IbspEventLogoutService logoutService = new IbspEventLogoutService();
	private List<String> eventIds;
	public LogoutThread(List<String> eventIds) {
		this.eventIds = eventIds;
	}
	public LogoutThread(String eventId) {
		eventIds = new ArrayList<>();
		eventIds.add(eventId);
	}

	@Override public String execute(String inVar) throws Exception {
		if (ContainerTool.isEmpty(eventIds)) {
			log.error("开启登录线程错误,事件id={}", eventIds);
			return null;
		}

		Map<String, Object> eventInfo = logoutService.findEventInfos(eventIds);
		for (Map.Entry<String, Object> entry : eventInfo.entrySet()) {
			String eventId = entry.getKey();
			JSONObject content = JSONObject.parseObject(entry.getValue().toString());
			content.put("EVENT_ID_", eventId);
			content.put("METHOD_", IbsMethodEnum.LOGOUT.name());

			String clientCode = content.getString("CLIENT_CODE_");
			log.info("事件：{},登录结果：{}",eventId, RabbitMqTool.sendMember(content.toString(),clientCode,"manage"));
		}

		return null;
	}
}
