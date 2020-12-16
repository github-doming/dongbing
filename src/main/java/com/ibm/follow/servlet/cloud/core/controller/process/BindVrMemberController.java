package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.entity.VrFmGameSet;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.service.VrFmGameSetService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.entity.VrUserFollowMember;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.service.VrUserFollowMemberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 绑定虚拟会员控制器
 * @Author: null
 * @Date: 2020-07-15 16:53
 * @Version: v1.0
 */
public class BindVrMemberController {
	protected static final Logger log = LogManager.getLogger(BindVrMemberController.class);

	private Map<String, Object> clientInfo;
	private String appUserId;
	private VrMember vrMember;

	public BindVrMemberController(Map<String, Object> clientInfo, String appUserId, VrMember vrMember) {
		this.clientInfo=clientInfo;
		this.appUserId=appUserId;
		this.vrMember=vrMember;
	}

	public void execute() throws Exception {
		Date nowTime=new Date();

		VrUserFollowMemberService userFollowMemberService=new VrUserFollowMemberService();
		VrUserFollowMember userFollowMember=userFollowMemberService.findUserInfo(appUserId,vrMember.getVrMemberId());
		if(userFollowMember==null){
			userFollowMember=new VrUserFollowMember();
			userFollowMember.setUserId(appUserId);
			userFollowMember.setVrMemberId(vrMember.getVrMemberId());
			userFollowMember.setVrMemberAccount(vrMember.getVrMemberAccount());
			userFollowMember.setVrHandicapCode(vrMember.getHandicapCode());
			userFollowMember.setFollowState(IbmTypeEnum.TRUE.name());
			userFollowMember.setCreateTime(nowTime);
			userFollowMember.setCreateTimeLong(nowTime.getTime());
			userFollowMember.setUpdateTime(nowTime);
			userFollowMember.setUpdateTimeLong(nowTime.getTime());
			userFollowMember.setState(IbmStateEnum.OPEN.name());
			userFollowMemberService.save(userFollowMember);

			initGameSet(nowTime);
		}else{
			userFollowMember.setFollowState(IbmTypeEnum.TRUE.name());
			userFollowMember.setUpdateTime(nowTime);
			userFollowMember.setUpdateTimeLong(nowTime.getTime());
			userFollowMemberService.update(userFollowMember);
		}
		VrFmGameSetService gameSetService=new VrFmGameSetService();
		List<Map<String, Object>> gameInfo =gameSetService.findGameInfo(appUserId,vrMember.getVrMemberId());
		if (ContainerTool.isEmpty(gameInfo)) {
			log.error("获取用户【{}】游戏设置信息失败",appUserId);
			return ;
		}
		//绑定消息主体
		JSONObject content = new JSONObject();
		content.put("SET_ITEM_", IbmMethodEnum.BIND_VR_MEMBER.name());
		content.put("METHOD_", IbmMethodEnum.MANAGE_VR.name());
		content.put("EXIST_MEMBER_VR_ID_", clientInfo.get("EXIST_MEMBER_VR_ID_"));
		content.put("USER_ID_", appUserId);
		content.put("GAME_INFO_", gameInfo);

		IbmEventConfigSet configSet=new IbmEventConfigSet();
		configSet.setEventContent(content);
		configSet.setEventState(IbmStateEnum.SEND.name());
		configSet.setExecNumber(0);
		configSet.setCreateTime(nowTime);
		configSet.setCreateTimeLong(System.currentTimeMillis());
		configSet.setUpdateTimeLong(System.currentTimeMillis());
		configSet.setState(IbmStateEnum.OPEN.name());
		String eventId=new IbmEventConfigSetService().save(configSet);

		content.put("EVENT_ID_", eventId);
		RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");
	}

	private void initGameSet(Date nowTime) throws Exception {
		//第一次绑定的需要初始化游戏设置信息
		VrFmGameSetService gameSetService=new VrFmGameSetService();
		VrFmGameSet fmGameSet=new VrFmGameSet();
		//获取可用游戏
		Map<String,Object> availableMap=new IbmExpUserService().findAvailableHandicap(appUserId);
		String[] availableGame=availableMap.get("AVAILABLE_GAME_").toString().split(",");

		IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
		Map<String, Object> haGameSetInfo = haGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(haGameSetInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		fmGameSet.setVrMemberId(vrMember.getVrMemberId());
		fmGameSet.setUserId(appUserId);
		fmGameSet.setHandicapCode(vrMember.getHandicapCode());
		fmGameSet.setBetState(haGameSetInfo.get("BET_STATE_"));
		fmGameSet.setBetFollowMultipleT(haGameSetInfo.get("BET_FOLLOW_MULTIPLE_T_"));
		fmGameSet.setBetLeastAmountT(haGameSetInfo.get("BET_LEAST_AMOUNT_T_"));
		fmGameSet.setBetMostAmountT(haGameSetInfo.get("BET_MOST_AMOUNT_T_"));
		fmGameSet.setBetFilterNumber(haGameSetInfo.get("BET_FILTER_NUMBER_"));
		fmGameSet.setBetFilterTwoSide(haGameSetInfo.get("BET_FILTER_TWO_SIDE_"));
		fmGameSet.setNumberOpposing(haGameSetInfo.get("NUMBER_OPPOSING_"));
		fmGameSet.setTwoSideOpposing(haGameSetInfo.get("TWO_SIDE_OPPOSING_"));
		fmGameSet.setCreateTime(nowTime);
		fmGameSet.setCreateTimeLong(nowTime.getTime());
		fmGameSet.setUpdateTime(nowTime);
		fmGameSet.setUpdateTimeLong(nowTime.getTime());
		fmGameSet.setState(IbmStateEnum.OPEN.name());
		for(String gameCode:availableGame){
			fmGameSet.setGameCode(gameCode);
			gameSetService.save(fmGameSet);
		}
	}
}
