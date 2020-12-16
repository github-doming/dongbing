package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;
import com.ibm.follow.servlet.cloud.core.controller.init.LoginControllerDefine;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.entity.IbmHmInfo;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口会员登录数据控制器
 * @Author: null
 * @Date: 2019-09-03 11:44
 * @Version: v1.0
 */
public class LoginHmController implements CloudExecutor {
	protected static final Logger log = LogManager.getLogger(LoginHmController.class);

	private String handicapMemberId;

	public LoginHmController(String handicapMemberId) {
		this.handicapMemberId = handicapMemberId;
	}

	public String execute(Object data) throws Exception {
		Date nowTime = new Date();
		IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
		IbmHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
		if (handicapMember == null) {
			log.error("盘口会员登录失败，不存在盘口会员：" + handicapMemberId);
			return null;
		}
		//获取用户状态
		String state = handicapMember.getState();
		//首次登陆修改状态
		if (IbmStateEnum.FIRST.name().equals(state)) {
			handicapMember.setState(IbmStateEnum.OPEN.name());
		}
        handicapMember.setOperating(IbmStateEnum.NONE.name());
        handicapMember.setUpdateTimeLong(System.currentTimeMillis());
        handicapMemberService.update(handicapMember);

		JSONObject dataObj = JSONObject.parseObject(data.toString());
		String existHmId = dataObj.getString("EXIST_ID_");
		//添加会员存在信息
		saveExistInfo(existHmId,dataObj);

        String profitAmount="0";
        String memberAccount=handicapMember.getMemberAccount();
		if (dataObj.containsKey("CUSTOMER_INFO_")) {
			MemberUser userInfo =JSONObject.parseObject(dataObj.getString("CUSTOMER_INFO_"),MemberUser.class);
			//盈亏金额profitAmount
			profitAmount = String.valueOf(userInfo.getProfitAmount());
            //会员账户memberAccount
            memberAccount = userInfo.getMemberAccount();
            //可用额度usedQuota
			long usedQuota = NumberTool.longValueT(userInfo.getUsedQuota());
            //1，修改登录状态，获取盘口会员信息
            IbmHmInfoService hmInfoService = new IbmHmInfoService();
            String hmInfoId = hmInfoService.findIdByHmId(handicapMemberId);
            if (StringTool.isEmpty(hmInfoId)) {
                saveHmInfo(handicapMember.getAppUserId(), memberAccount, NumberTool.longValueT(userInfo.getProfitAmount()), usedQuota, nowTime, hmInfoService);
            } else {
                hmInfoService.updateById(hmInfoId, memberAccount, NumberTool.longValueT(userInfo.getProfitAmount()), usedQuota, nowTime);
            }
        }

		//2，修改盘口用户登录在线数量
        new IbmHmUserService().updateLogin(handicapMemberId, nowTime);
		Map<String, Object> hmSetMap;
		IbmHmSetService hmSetService = new IbmHmSetService();
		IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
		//3，首次登陆，初始化信息
		if (IbmStateEnum.FIRST.name().equals(state)) {
			hmSetMap = LoginControllerDefine.loginHmInit(handicapMember, hmSetService, hmGameSetService);
		} else {
			hmSetMap = hmSetService.findByHmId(handicapMemberId);
		}
		if (ContainerTool.isEmpty(hmSetMap)) {
			log.error("获取盘口会员【" + handicapMemberId + "】设置信息失败");
			return null;
		}
		//4,写入客户设置事件
		List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMemberId);
		if (ContainerTool.isEmpty(gameInfo)) {
			log.error("获取盘口会员【" + handicapMemberId + "】游戏设置信息失败");
			return null;
		}
        //5,清除历史盘口会员盈亏信息，新增新的盘口会员盈亏信息
        LoginControllerDefine.saveHmProfit(hmSetMap, handicapMember, profitAmount, nowTime);
        //6,模拟投注信息
        LoginControllerDefine.saveHmProfitVr(hmSetMap, handicapMember, nowTime);
        //8,添加登录日志信息
        LoginControllerDefine.saveHmLog(handicapMemberId, handicapMember.getAppUserId(), IbmStateEnum.LOGIN.name());

        CurrentTransaction.transactionCommit();
        //7,绑定用户代理
        bindUserAgent(handicapMember,memberAccount, nowTime);
		String clientCode = dataObj.getString("CLIENT_CODE_");
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("GAME_INFO_", gameInfo);
		content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
		content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
		String eventId=EventThreadDefine.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送会员游戏设置信息"),
				new IbmEventConfigSetService());
		content.put("EVENT_ID_",eventId);

		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
		content.remove("GAME_INFO_");
		content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
		content.put("BET_RATE_T_", hmSetMap.get("BET_RATE_T_"));
		content.put("BET_MERGER_", hmSetMap.get("BET_MERGER_"));
		eventId=EventThreadDefine.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送会员设置信息"),
				new IbmEventConfigSetService());
		content.put("EVENT_ID_",eventId);
		RabbitMqTool.sendClientConfig(content.toString(), clientCode, "set");
		//添加登录会员数
        IbmExpUserService expUserService=new IbmExpUserService();
        expUserService.updateLoginOnline(handicapMember.getAppUserId(), IbmTypeEnum.MEMBER);

		return handicapMemberId;
	}

	private void saveExistInfo(String existHmId, JSONObject data) throws Exception {
		String clientCode=data.getString("CLIENT_CODE_");
		String clientId=new IbmClientService().findId(clientCode);

		IbmClientHmService clientHmService=new IbmClientHmService();
		Map<String,Object> clientHmInfo=clientHmService.findByHmId(handicapMemberId);
		if(ContainerTool.isEmpty(clientHmInfo)){
			clientHmService.save(clientId,handicapMemberId,existHmId,clientCode,data.getString("HANDICAP_CODE_"));
		}else{
			clientHmService.update(clientHmInfo,existHmId,clientId,clientCode);
		}
	}

	/**
	 * 添加盘口会员信息
	 *
	 * @param appUserId     用户id
	 * @param memberAccount 会员账号
	 * @param profitAmount  盈亏
	 * @param usedQuota     金额
	 * @param nowTime       当前时间
	 * @param hmInfoService 盘口会员信息服务类
	 */
	private void saveHmInfo(String appUserId, String memberAccount, long profitAmount, long usedQuota, Date nowTime,
			IbmHmInfoService hmInfoService) throws Exception {
		IbmHmInfo hmInfo = new IbmHmInfo();
		hmInfo.setAppUserId(appUserId);
		hmInfo.setHandicapMemberId(handicapMemberId);
		hmInfo.setMemberAccount(memberAccount);
		hmInfo.setHandicapProfitT(profitAmount);
		hmInfo.setAmountT(usedQuota);
		hmInfo.setCreateTime(nowTime);
		hmInfo.setCreateTimeLong(nowTime.getTime());
		hmInfo.setUpdateTime(nowTime);
		hmInfo.setUpdateTimeLong(nowTime.getTime());
		hmInfo.setState(IbmStateEnum.LOGIN.name());
		hmInfoService.save(hmInfo);
	}

	/**
	 * 绑定用户代理
	 *
	 * @param handicapMember 盘口会员信息
	 */
	private void bindUserAgent(IbmHandicapMember handicapMember,String memberAccount, Date nowTime) throws Exception {
		//获取盘口代理id列表
		List<String> handicapAgentIds = new IbmHaInfoService().listHostingHaIdByUserId(handicapMember.getAppUserId());
		//尚未开启代理
		if (ContainerTool.isEmpty(handicapAgentIds)) {
			return;
		}
		//获取已有代理id列表
		IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
		List<String> haIds = handicapAgentMemberService.listHaId(handicapMemberId);
		if (ContainerTool.notEmpty(haIds)) {
			handicapAgentIds.removeAll(haIds);
			if (ContainerTool.isEmpty(handicapAgentIds)) {
				return;
			}
		}

		//获取客户端盘口会员信息
		IbmClientHmService clientHmService = new IbmClientHmService();
		Map<String, Object> bindInfo = clientHmService.findBindInfo(handicapMemberId);
        if(ContainerTool.isEmpty(bindInfo)){
            log.error("获取盘口会员【" + handicapMemberId + "】存在会员信息失败");
            return;
        }
		//投注模式
		List<Map<String, Object>> betModeInfos = new IbmHmGameSetService().listBetModeInfo(handicapMemberId);

		//绑定消息主体
		JSONObject content = new JSONObject(bindInfo);
		content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
		content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
        content.put("MEMBER_HANDICAP_CODE_",handicapMember.getHandicapCode());
        content.put("MEMBER_ACCOUNT_",memberAccount);
		IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
		for (String handicapAgentId : handicapAgentIds) {
			// 保存数据
			handicapAgentMemberService.save(handicapMember.getAppUserId(), handicapAgentId, handicapMemberId,
                    handicapMember.getHandicapCode(),memberAccount, nowTime);
			//绑定代理
            bindUserAgent(handicapAgentId,content,betModeInfos);
		}
	}

    /**
     * 绑定代理信息
     * @param handicapAgentId   盘口代理id
     * @param content           消息内容
     * @param betModeInfos      投注模式信息
     */
    public static void bindUserAgent(String handicapAgentId,JSONObject content, List<Map<String, Object>> betModeInfos) throws Exception {

        JSONObject betModeInfo = new JSONObject();
        //获取代理拥有游戏和类型
        Map<String,Object> agentGameInfo=new IbmHaGameSetService().findGameType(handicapAgentId);
        for (Map<String, Object> info : betModeInfos) {
            if(!agentGameInfo.containsKey(info.get("GAME_CODE_"))||
                    !StringTool.isContains(agentGameInfo.get(info.get("GAME_CODE_")).toString(),info.get("TYPE_").toString())){
                continue;
            }
            betModeInfo.put(info.get("GAME_CODE_").toString(), info.get("BET_MODE_"));
        }
        content.put("BET_MODE_INFO_", betModeInfo);
        //发送绑定数据到客户机
		EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,"添加绑定数据设置");
    }

}
