package com.ibs.plan.module.server.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.controller.strategy.FindClientByPercent;
import com.ibs.plan.module.cloud.core.controller.strategy.FindClientController;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户登录
 * @Author: null
 * @Date: 2020-05-23 10:53
 * @Version: v1.0
 */
public class LoginThread extends CommEventThread {
	private List<String> eventIds;

	public LoginThread(List<String> eventIds){
		this.eventIds=eventIds;
	}
	public LoginThread(String eventId){
		this(new ArrayList<>(1));
		eventIds.add(eventId);
	}

	@Override
	public String execute(String inVar) throws Exception {
		if(ContainerTool.isEmpty(eventIds)){
			log.error("开启登录线程错误,事件id={}",eventIds);
			return null;
		}
		IbspEventLoginService loginService=new IbspEventLoginService();
		Map<String, Object> eventInfo=loginService.findEventInfos(eventIds);
		if(ContainerTool.isEmpty(eventInfo)){
			log.error("开启登录线程错误,事件id={}",eventIds);
			return null;
		}
		Map<String,Map<String,Object>> loginInfo=new IbspHandicapMemberService().findLoginInfo(eventInfo.keySet());
		for(Map.Entry<String, Map<String, Object>> entry:loginInfo.entrySet()){
			//执行事件，获取结果
			String eventId=eventInfo.get(entry.getKey()).toString();
			JSONObject content =new JSONObject();
			content.put("EVENT_ID_", eventId);
			content.put("METHOD_", IbsMethodEnum.LOGIN.name());
			content.putAll(entry.getValue());
			//发送登录信息
			Map<String, Object> existInfo=getUsableClient(content);
			if (ContainerTool.isEmpty(existInfo)) {
				log.error("事件:{},开启客户端失败，客户机容量已满,请联系客服人员",eventId);
				content.clear();
				content.put("code", "error");
				content.put("msg", "开启客户端失败，客户机容量已满,请联系客服人员");
				loginService.updateResultByState(eventId,content, IbsStateEnum.FINISH);
				continue;
			}
			String clientCode=existInfo.get("CLIENT_CODE_").toString();
			log.info("事件：{},登录结果：{}",eventId, RabbitMqTool.sendMember(content.toString(),clientCode,"manage"));
		}

		return null;
	}
	/**
	 * 发送登录信息
	 * @param content		登录消息
	 * @return 登录信息
	 */
	public static Map<String, Object> getUsableClient(JSONObject content) throws Exception {
		Map<String, Object> existInfo = new IbspClientHmService().findExistHmInfo(content.getString("HANDICAP_MEMBER_ID_"));
		if (ContainerTool.isEmpty(existInfo)) {
			//获取会员方案类型是否为合集
			String planType=new IbspHandicapMemberService().findHmPlanType(content.getString("HANDICAP_MEMBER_ID_"));

			if(!PlanMainConfig.CLIENT_TYPE.equals(planType)){
				planType="SINGLE";
			}
			// 获取可用客户机
			existInfo = new FindClientController().strategy(new FindClientByPercent()).
					findUsableClient(content.getString("HANDICAP_CODE_"),planType);
		}
		return existInfo;
	}
}
