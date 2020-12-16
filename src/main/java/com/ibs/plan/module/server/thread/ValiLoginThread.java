package com.ibs.plan.module.server.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.service.IbspEventLoginValiService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 验证登录
 * @Author: null
 * @Date: 2020-05-28 14:00
 * @Version: v1.0
 */
public class ValiLoginThread extends CommEventThread {

	private String eventId;


	public ValiLoginThread(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public String execute(String inVar) throws Exception {
		IbspEventLoginValiService loginValiService = new IbspEventLoginValiService();
		String handicapMemberId = loginValiService.findEventInfo(eventId);

		if (StringTool.isEmpty(handicapMemberId)) {
			log.error("开启验证登录线程错误,事件id={}", eventId);
			return null;
		}
		Map<String, Object> loginInfo = new IbspHandicapMemberService().findLoginInfo(handicapMemberId);
		if (ContainerTool.isEmpty(loginInfo)) {
			log.error("开启验证登录线程错误,事件id={}", eventId);
			return null;
		}

		JSONObject content = new JSONObject();
		content.putAll(loginInfo);
		content.put("EVENT_ID_", eventId);
		content.put("METHOD_", IbsMethodEnum.VALI_LOGIN.name());
		content.put("HANDICAP_MEMBER_ID_", handicapMemberId);

		//发送登录信息
		Map<String, Object> existInfo = LoginThread.getUsableClient(content);
		if (ContainerTool.isEmpty(existInfo)) {
			log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员", eventId);
			content.clear();
			content.put("code", "error");
			content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
			loginValiService.updateResultByState(eventId,content, IbsStateEnum.FINISH);
			return null;
		}
		String clientCode = existInfo.get("CLIENT_CODE_").toString();
		log.info("事件：{},登录结果：{}", eventId, RabbitMqTool.sendMember(content.toString(), clientCode, "manage"));
		return null;
	}
}
