package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.init.LoginControllerDefine;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.entity.IbmHaInfo;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口代理登录数据控制器
 * @Author: null
 * @Date: 2019-09-03 11:44
 * @Version: v1.0
 */
public class LoginHaController implements CloudExecutor {
	protected static final Logger log = LogManager.getLogger(LoginHaController.class);
	private String handicapAgentId;

	public LoginHaController(String handicapAgentId) {
		this.handicapAgentId = handicapAgentId;
	}

	public String execute(Object data) throws Exception {
		Date nowTime = new Date();
		IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
		IbmHandicapAgent handicapAgent = handicapAgentService.find(handicapAgentId);
		if (handicapAgent == null) {
			log.error("盘口代理登录失败，不存在盘口会员：" + handicapAgentId);
			return null;
		}
		String state = handicapAgent.getState();
		//首次登陆修改状态
		if (IbmStateEnum.FIRST.name().equals(state)) {
			handicapAgent.setState(IbmStateEnum.OPEN.name());
		}
        handicapAgent.setOperating(IbmStateEnum.NONE.name());
        handicapAgent.setUpdateTimeLong(System.currentTimeMillis());
        handicapAgentService.update(handicapAgent);

		JSONObject dataObj = JSONObject.parseObject(data.toString());
		String existHaId = dataObj.getString("EXIST_ID_");
		//添加代理存在信息
		saveExistInfo(existHaId,dataObj);

        JSONArray memberListInfo;
        //会员列表信息
		if (dataObj.containsKey("CUSTOMER_INFO_")) {
			memberListInfo = JSONArray.parseArray(dataObj.getString("CUSTOMER_INFO_"));
		}else{
			memberListInfo=new JSONArray();
		}

		//1，修改登录状态，获取盘口代理信息
		IbmHaInfoService haInfoService = new IbmHaInfoService();
		String haInfoId = haInfoService.findIdByHaId(handicapAgentId);
		if (StringTool.isEmpty(haInfoId)) {
			saveHaInfo(handicapAgent.getAppUserId(), memberListInfo, haInfoService, nowTime);
		} else {
			haInfoService.updateById(haInfoId, memberListInfo, nowTime);
		}
		//2，修改盘口用户登录在线数量
		IbmHaUserService haUserService = new IbmHaUserService();
		haUserService.updateLogin(handicapAgentId, nowTime);

		String clientCode=dataObj.getString("CLIENT_CODE_");
		JSONObject content = new JSONObject();
		content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
		content.put("EXIST_HA_ID_", existHaId);
		content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
		//3,首次登陆，初始化信息
		if (IbmStateEnum.FIRST.name().equals(state)) {
			LoginControllerDefine.loginHaInit(handicapAgent, memberListInfo);
			content.put("FOLLOW_MEMBER_TYPE_", IbmTypeEnum.ALL.name());
		} else {
			Map<String, Object> haSetMap = new IbmHaSetService().findByHaId(handicapAgentId,memberListInfo);
			if (ContainerTool.isEmpty(haSetMap)) {
				log.error("获取盘口代理【" + handicapAgentId + "】跟随会员信息失败");
				return null;
			}
			content.put("FOLLOW_MEMBER_TYPE_", haSetMap.get("FOLLOW_MEMBER_TYPE_"));
			content.put("FOLLOW_MEMBER_LIST_INFO_", haSetMap.get("FOLLOW_MEMBER_LIST_INFO_"));
		}

		//4，写入客户设置事件
		IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
		List<Map<String, Object>> gameInfo = haGameSetService.findByHaId(handicapAgentId);
		if (ContainerTool.isEmpty(gameInfo)) {
			log.error("获取盘口代理【" + handicapAgentId + "】游戏设置信息失败");
			return null;
		}
		//6,添加登录日志信息
		LoginControllerDefine.saveHaLog(handicapAgentId, handicapAgent.getAppUserId(), IbmStateEnum.LOGIN.name());

        CurrentTransaction.transactionCommit();
        //5.绑定用户代理
        bindUserMember(handicapAgent.getAppUserId(),haGameSetService);

        String eventId=EventThreadDefine.saveAgentConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送代理设置信息"),
                new IbmEventConfigSetService());
		content.put("EVENT_ID_",eventId);

		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");

        content.remove("FOLLOW_MEMBER_TYPE_");
        content.remove("FOLLOW_MEMBER_LIST_INFO_");
        content.put("GAME_INFO_", gameInfo);
        content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
		eventId=EventThreadDefine.saveAgentConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送代理游戏设置信息"),
                new IbmEventConfigSetService());
		content.put("EVENT_ID_",eventId);

		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
        //添加登录会员数
        IbmExpUserService expUserService=new IbmExpUserService();
        expUserService.updateLoginOnline(handicapAgent.getAppUserId(), IbmTypeEnum.AGENT);

		return handicapAgentId;
	}

	private void saveExistInfo(String existHaId, JSONObject data) throws Exception {
		String clientCode=data.getString("CLIENT_CODE_");
		String clientId=new IbmClientService().findId(clientCode);

		IbmClientHaService clientHaService=new IbmClientHaService();
		Map<String,Object> clientHmInfo=clientHaService.findByHaId(handicapAgentId);
		if(ContainerTool.isEmpty(clientHmInfo)){
			clientHaService.save(clientId,handicapAgentId,existHaId,clientCode,data.getString("HANDICAP_CODE_"));
		}else{
			clientHaService.update(clientHmInfo,existHaId,clientId,clientCode);
		}
	}

	/**
	 * 绑定用户会员
	 *
	 * @param appUserId 用户主键
     * @param haGameSetService
	 */
	private void bindUserMember(String appUserId,IbmHaGameSetService haGameSetService) throws Exception {
		//获取盘口会员id列表
		List<String> handicapMemberIds = new IbmHmInfoService().listHostingHmIdByUserId(appUserId);
		//尚未开启会员
		if (ContainerTool.isEmpty(handicapMemberIds)) {
			return;
		}
		//获取已有代理id列表
		IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
		List<String> hmIds = handicapAgentMemberService.listHmId(handicapAgentId);
		if (ContainerTool.notEmpty(hmIds)) {
			handicapMemberIds.removeAll(hmIds);
			if (ContainerTool.isEmpty(handicapMemberIds)) {
				return;
			}
		}
		//获取代理拥有游戏和类型
        Map<String,Object> agentGameInfo=haGameSetService.findGameType(handicapAgentId);

		//绑定消息主体
		JSONObject content = new JSONObject();
		content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
		content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
		content.put("HANDICAP_AGENT_ID_", handicapAgentId);

        Map<String,Object> memberInfo;
		Date nowTime = new Date();
		IbmClientHmService clientHmService = new IbmClientHmService();
		IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
        IbmHandicapMemberService handicapMemberService=new IbmHandicapMemberService();
		for (String handicapMemberId : handicapMemberIds) {
            memberInfo=handicapMemberService.findMemberAccountInfo(handicapMemberId);
			// 保存数据
			handicapAgentMemberService.save(memberInfo.get("APP_USER_ID_").toString(), handicapAgentId, handicapMemberId,
                    memberInfo.get("HANDICAP_CODE_").toString(),memberInfo.get("MEMBER_ACCOUNT_").toString(), nowTime);

            bindUserMember(memberInfo,agentGameInfo,content,configSetService,clientHmService);
		}

	}

    /**
     * 绑定会员
     * @param memberInfo        盘口会员信息
     * @param agentGameInfo     代理游戏信息
     * @param content           消息内容
     * @param configSetService  服务类
     * @param clientHmService   服务类
     */
    public static void bindUserMember(Map<String,Object> memberInfo,Map<String,Object> agentGameInfo, JSONObject content,
        IbmEventConfigSetService configSetService,IbmClientHmService clientHmService) throws Exception {
        //投注模式
        JSONObject betModeInfo = new JSONObject();
        Map<String, Object> bindInfo =clientHmService.findBindInfo(memberInfo.get("HANDICAP_MEMBER_ID_").toString());
        if(ContainerTool.isEmpty(bindInfo)){
            log.error("获取盘口会员【" + memberInfo.get("HANDICAP_MEMBER_ID_") + "】存在会员信息失败");
            return ;
        }
		Map<String, Object> existInfo =  new IbmClientHaService().findExistHaId(content.remove("HANDICAP_AGENT_ID_").toString());
		if (ContainerTool.isEmpty(existInfo)){
			return;
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();
        List<Map<String, Object>> betModeInfos = new IbmHmGameSetService().listBetModeInfo(memberInfo.get("HANDICAP_MEMBER_ID_").toString());
        for (Map<String, Object> info : betModeInfos) {
            if(!agentGameInfo.containsKey(info.get("GAME_CODE_"))||
                    !StringTool.isContains(agentGameInfo.get(info.get("GAME_CODE_")).toString(),info.get("TYPE_").toString())){
                continue;
            }
            betModeInfo.put(info.get("GAME_CODE_").toString(), info.get("BET_MODE_"));
        }
        content.putAll(bindInfo);
        content.put("MEMBER_HANDICAP_CODE_",memberInfo.get("HANDICAP_CODE_"));
        content.put("MEMBER_ACCOUNT_",memberInfo.get("MEMBER_ACCOUNT_"));
        content.put("BET_MODE_INFO_", betModeInfo);
		content.put("EXIST_HA_ID_",existInfo.get("EXIST_HA_ID_"));

        String eventId=EventThreadDefine.saveAgentConfigSetEvent(content, new Date(), "，添加绑定数据设置",
                configSetService);
		content.put("EVENT_ID_",eventId);
		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
    }

    /**
	 * 添加盘口代理信息
	 *
	 * @param appUserId      用户id
	 * @param memberListInfo 会员列表信息
	 * @param haInfoService  service
	 * @param nowTime        当前时间
	 */
	private void saveHaInfo(String appUserId, JSONArray memberListInfo, IbmHaInfoService haInfoService, Date nowTime)
			throws Exception {
		IbmHaInfo haInfo = new IbmHaInfo();
		haInfo.setAppUserId(appUserId);
		haInfo.setHandicapAgentId(handicapAgentId);
		haInfo.setMemberListInfo(memberListInfo);
		haInfo.setCreateTime(nowTime);
		haInfo.setCreateTimeLong(nowTime.getTime());
		haInfo.setUpdateTime(nowTime);
		haInfo.setUpdateTimeLong(nowTime.getTime());
		haInfo.setState(IbmStateEnum.LOGIN.name());
		haInfoService.save(haInfo);
	}

}
