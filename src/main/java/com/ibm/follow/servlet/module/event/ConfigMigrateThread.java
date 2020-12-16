package com.ibm.follow.servlet.module.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.CommEventThread;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.service.IbmEventConfigInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.module.event.controller.OpenClientController;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 客户端迁移配置
 *
 * @Author: Dongming
 * @Date: 2020-01-10 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ConfigMigrateThread extends CommEventThread {
	private Map<String, Object> eventInfo;

	private IbmEventConfigInfoService configInfoService = new IbmEventConfigInfoService();

	ConfigMigrateThread(Map<String, Object> eventInfo) {
		this.eventInfo = eventInfo;
	}

	@Override public String execute(String ignore) throws Exception {
		//事件id
		String eventId = eventInfo.get("EVENT_ID_").toString();

		// 执行事件，获取结果
		JSONObject result = clientMigrate(eventId);
		if (ContainerTool.isEmpty(result)) {
			result = new JSONObject();
			result.put("code", "error");
			result.put("msg", "客户端迁移失败,请稍后再试");
		}
		configInfoService.updateResult(eventId, result);

		return null;
	}

	private JSONObject clientMigrate(String eventId) throws Exception {
        /*
         * 1. 根据信息，先开启客户端
         * 2. 开启客户端后传输数据到客户端
         */
        JSONObject resultJson = new JSONObject();

		JSONObject content = JSONObject.parseObject(eventInfo.get("EVENT_CONTENT_").toString());
		content.put("EVENT_ID_", eventId);
		content.put("METHOD_", IbmMethodEnum.CLIENT_MIGRATE.name());
		content.put("CUSTOMER_TYPE_", eventInfo.get("CUSTOMER_TYPE_"));
		
		IbmTypeEnum customerType = IbmTypeEnum.valueOf(eventInfo.get("CUSTOMER_TYPE_").toString());
		String result = null;
		switch (customerType) {
			case MEMBER:
				result =sendMemberClientMigrate(content);
				break;
			case AGENT:
				result =sendAgentClientMigrate(content);
			break;
			default:
				log.error("登录失败,错误的客户类型:" + customerType.getMsg());
		}
		if (StringTool.isEmpty(result)) {
			return resultJson;
		}
		if (StringTool.isContains(result, "code")) {
			return JSONObject.parseObject(result);
		}
        if (!Boolean.parseBoolean(result)) {
            log.error("客户设置，发送消息失败");
            return resultJson;
        }
        //监听事件结果- 200ms轮训一次-15次《状态没有改为PROCESS,FAIL,SUCCESS》
        Map<String, Object> eventMap = listeningEventResult(eventId);
        if (ContainerTool.isEmpty(eventMap)) {
            log.error("客户设置，事件状态异常，事件id=".concat(eventId));
            return resultJson;
        }
        //事件结果
        JSONObject eventResultJson = JSONObject.parseObject(eventMap.get("EVENT_RESULT_").toString());
        //参数错误
        if (StringTool.contains(eventResultJson.getString("codeSys"), IbmCodeEnum.CODE_500.getCode(),
                IbmCodeEnum.CODE_401.getCode())) {
            log.info("客户端迁移失败=" + eventMap.get("EVENT_RESULT_"));
            resultJson.put("code", "error");
            resultJson.put("msg", "客户端迁移参数错误");
            return resultJson;
        }
        //返回处理结果
        resultJson.put("code", eventResultJson.getString("codeSys"));
        resultJson.put("msg", eventResultJson.getString("msg"));
		return resultJson;
	}
    /**
     * 监听事件结果
     *
     * @param eventId 事件id
     * @return 事件结果
     */
    private Map<String, Object> listeningEventResult(String eventId) throws SQLException, InterruptedException {
        Map<String, Object> eventMap = null;
        int pollNum = 15;
        for (int i = 0; i < pollNum; i++) {
            eventMap = configInfoService.findEventResult(eventId);
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
                    log.error("开启客户端错误，事件Id=" + eventId + ",结果=" + eventMap.get("EVENT_RESULT_"));
                    return new HashMap<>(1);
                case SUCCESS:
                    break;
                default:
                    log.error("开启客户端，事件状态异常，事件id=".concat(eventId));
                    return new HashMap<>(1);
            }
            break;
        }
        return eventMap;
    }
	/**
	 * 发送代理迁移信息
	 * @param content 迁移正文
	 * @return 发送结果
	 */
	private String sendAgentClientMigrate(JSONObject content ) throws Exception {
		String handicapAgentId = content.get("HANDICAP_AGENT_ID_").toString();
		//  开启客户端
		JSONObject json = new JSONObject();
		json.put("HANDICAP_CODE_", content.get("HANDICAP_CODE_"));
		json.put("HANDICAP_AGENT_ID_", handicapAgentId);
		String eventId = EventThreadDefine.saveOpenClientEvent(json, IbmTypeEnum.AGENT, "代理数据迁移", null);
		//执行开启客户端事件
		JSONObject result = new OpenClientController().execute(eventId);
		if (ContainerTool.isEmpty(result)) {
			result.put("code", "error");
			result.put("msg", "登录失败,请联系客服管理人员");
			return result.toJSONString();
		}
		if (!"200".equals(result.get("code"))) {
			log.info("登录失败，开启代理客户端失败:".concat(result.toString()));
			result.put("code", result.get("code"));
			result.put("msg", result.get("msg"));
			return result.toJSONString();
		}
		Map<String, Object> existInfo = new IbmClientHaService().findExistHaId(handicapAgentId);
		if (ContainerTool.isEmpty(existInfo)) {
			log.info("登录失败，客户端存在代理信息为空,会员id:".concat(handicapAgentId));
			result.put("code", "error");
			result.put("msg", "登录失败,请稍后再试");
			return result.toJSONString();
		}
		Map<String, Object> loginInfo = new IbmHandicapAgentService().findLoginInfo(handicapAgentId);
        loginInfo.put("HANDICAP_AGENT_ID_", handicapAgentId);
        loginInfo.remove("HANDICAP_ID_");
		content.put("loginInfo", loginInfo);

		// 盘口配置信息
		Map<String, Object> handicapInfo = new IbmHaSetService().findFollowInfo(handicapAgentId);
		handicapInfo.remove("IBM_HA_SET_ID_");
		content.put("handicapInfo", handicapInfo);

		// 游戏配置信息
		List<Map<String, Object>> gameInfos = new IbmHaGameSetService().findByHaId(handicapAgentId);
		content.put("gameInfos", gameInfos);

		JSONArray bindInfos = getBindInfos(handicapAgentId);
		content.put("bindInfos", bindInfos);

		String clientCode = existInfo.get("CLIENT_CODE_").toString();
		content.put("EXIST_HA_ID_", existInfo.get("EXIST_HA_ID_"));
		// 发送数据迁移信息
		return RabbitMqTool.sendAgentInfo(content.toString(), clientCode, "manage");
	}

	/**
	 * 发送会员迁移信息
	 * @param content 迁移正文
	 * @return 发送结果
	 */
	private String sendMemberClientMigrate(JSONObject content ) throws Exception {
		String handicapMemberId = content.get("HANDICAP_MEMBER_ID_").toString();
		//  开启客户端
		JSONObject json = new JSONObject();
		json.put("HANDICAP_CODE_", content.get("HANDICAP_CODE_"));
		json.put("HANDICAP_MEMBER_ID_", handicapMemberId);
		String eventId = EventThreadDefine.saveOpenClientEvent(json, IbmTypeEnum.MEMBER, "会员数据迁移", null);
		//执行开启客户端事件
		JSONObject result = new OpenClientController().execute(eventId);
		if (ContainerTool.isEmpty(result)) {
			result.put("code", "error");
			result.put("msg", "登录失败,请联系客服管理人员");
			return result.toJSONString();
		}
		if (!"200".equals(result.get("code"))) {
			log.info("登录失败，开启会员客户端失败:".concat(result.toString()));
			result.put("code", result.get("code"));
			result.put("msg", result.get("msg"));
			return result.toJSONString();
		}
		Map<String, Object> existInfo = new IbmClientHmService().findExistHmId(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)) {
			log.info("登录失败，客户端存在会员信息为空,会员id:".concat(handicapMemberId));
			result.put("code", "error");
			result.put("msg", "登录失败,请稍后再试");
			return result.toJSONString();
		}
		// 读取数据
		Map<String, Object> loginInfo = new IbmHandicapMemberService().findLoginInfo(handicapMemberId);
        loginInfo.put("HANDICAP_MEMBER_ID_", handicapMemberId);
        loginInfo.remove("HANDICAP_ID_");
		content.put("loginInfo", loginInfo);

		// 盘口配置信息
		Map<String, Object> handicapInfo = new IbmHmSetService().findInfo(handicapMemberId);
		content.put("handicapInfo", handicapInfo);

		// 游戏配置信息
		List<Map<String, Object>> gameInfos = new IbmHmGameSetService().findByHmId(handicapMemberId);
		content.put("gameInfos", gameInfos);


		String clientCode = existInfo.get("CLIENT_CODE_").toString();
		content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
		// 发送数据迁移信息
		return RabbitMqTool.sendMemberInfo(content.toString(), clientCode, "manage");
	}

	/**
	 * 获取绑定信息
	 * @param handicapAgentId 盘口代理ID
	 * @return  绑定信息
	 */
	private JSONArray getBindInfos(String handicapAgentId) throws Exception {
		JSONArray bindInfos=new JSONArray();
		//获取绑定会员信息
        Map<String,Object> accountInfo;
		IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
		IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
		IbmClientHmService clientHmService = new IbmClientHmService();
		List<String> handicapMemberIds =new IbmHandicapAgentMemberService().listHmId(handicapAgentId);
		for(String handicapMemberId:handicapMemberIds){
			JSONObject bindInfo=new JSONObject();
            Map<String, Object> memberInfo = clientHmService.findBindInfo(handicapMemberId);
            if(ContainerTool.isEmpty(bindInfo)){
                log.error("获取盘口会员【" + handicapMemberId + "】存在会员信息失败");
                continue ;
            }
            bindInfo.putAll(memberInfo);
            accountInfo=handicapMemberService.findMemberAccountInfo(handicapMemberId);
			//投注模式
			JSONObject betModeInfo = new JSONObject();
			List<Map<String, Object>> betModeInfos = hmGameSetService.listBetModeInfo(handicapMemberId);
			for (Map<String, Object> info : betModeInfos) {
				betModeInfo.put(info.get("GAME_CODE_").toString(), info.get("BET_MODE_"));
			}
			bindInfo.put("MEMBER_HANDICAP_CODE_",accountInfo.get("HANDICAP_CODE_"));
			bindInfo.put("MEMBER_ACCOUNT_",accountInfo.get("MEMBER_ACCOUNT_"));
			bindInfo.put("BET_MODE_INFO_", betModeInfo);

			bindInfos.add(bindInfo);
		}
		return bindInfos;
	}

}
