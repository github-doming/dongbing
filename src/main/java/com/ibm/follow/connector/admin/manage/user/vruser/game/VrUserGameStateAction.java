package com.ibm.follow.connector.admin.manage.user.vruser.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientByPercent;
import com.ibm.follow.servlet.cloud.core.controller.strategy.FindClientController;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.vr_client_member.entity.VrClientMember;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.entity.VrMemberGameSet;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 游戏状态修改
 * @Author: admin1
 * @Date: 2020/7/16 16:53
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/game/state")
public class VrUserGameStateAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String gameSetId = StringTool.getString(dataMap.get("VR_MEMBER_GAME_SET_ID_"), "");
		String state = StringTool.getString(dataMap.get("state"), "");
		if (StringTool.isEmpty(gameSetId, state)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		Date nowTime = new Date();
		try {
			VrMemberGameSetService memberGameSetService = new VrMemberGameSetService();
			VrMemberGameSet gameSet = memberGameSetService.find(gameSetId);
			if (gameSet == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			gameSet.setBetState(state);
			gameSet.setUpdateTime(nowTime);
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			memberGameSetService.update(gameSet);

			String memberId = gameSet.getVrMemberId();
			String gameCode = gameSet.getGameCode();
			VrMember member = new VrMemberService().find(memberId);
			String gameType = new IbmHandicapGameService().findGameType(member.getHandicapCode(), gameCode);

			//写入事件,发送客户端
			//如果虚拟会员没有绑定客户端,进行绑定
			VrClientMemberService clientMemberService = new VrClientMemberService();
			Map<String, Object> clientInfo = clientMemberService.findInfo(gameSet.getVrMemberId());
			String existId;
			if (ContainerTool.isEmpty(clientInfo)) {
				clientInfo = new FindClientController().strategy(new FindClientByPercent())
						.findVerifyClient("VR", IbmTypeEnum.MEMBER);
				if (ContainerTool.isEmpty(clientInfo)) {
					bean.putEnum(IbmCodeEnum.IBM_403_MAX_CAPACITY);
					bean.putEnum(IbmCodeEnum.CODE_403);
					return bean;
				}
				existId = RandomTool.getNumLetter32();
				VrClientMember clientMember = new VrClientMember();
				clientMember.setVrMemberId(gameSet.getVrMemberId());
				clientMember.setExistMemberVrId(existId);
				clientMember.setClientId(clientInfo.get("CLIENT_ID_"));
				clientMember.setClientCode(clientInfo.get("CLIENT_CODE_"));
				clientMember.setHandicapCode("VR");
				clientMember.setCreateTime(nowTime);
				clientMember.setCreateTimeLong(System.currentTimeMillis());
				clientMember.setUpdateTime(nowTime);
				clientMember.setUpdateTimeLong(System.currentTimeMillis());
				clientMember.setState(IbmStateEnum.OPEN.name());
				clientMemberService.save(clientMember);
				//TODO 发送绑定信息  vrc_exist_member
				//
				JSONObject content = new JSONObject();
				content.put("EXIST_MEMBER_VR_ID_", existId);
				content.put("USER_ID_", member.getVrMemberId());
				content.put("VR_MEMBER_ACCOUNT_", member.getVrMemberAccount());
				content.put("HANDICAP_CODE_", member.getHandicapCode());
				content.put("SET_ITEM_", IbmMethodEnum.BIND_VR_CLIENT.name());
				content.put("METHOD_", IbmMethodEnum.MANAGE_VR.name());

				IbmEventConfigSet configSet = new IbmEventConfigSet();
				configSet.setEventContent(content);
				configSet.setEventState(IbmStateEnum.SEND.name());
				configSet.setExecNumber(0);
				configSet.setCreateTime(new Date());
				configSet.setCreateTimeLong(System.currentTimeMillis());
				configSet.setUpdateTimeLong(System.currentTimeMillis());
				configSet.setState(IbmStateEnum.OPEN.name());
				String eventId = new IbmEventConfigSetService().save(configSet);
				CurrentTransaction.transactionCommit();

				content.put("EVENT_ID_", eventId);
				RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");

			} else {
				existId = clientInfo.get("EXIST_MEMBER_VR_ID_").toString();
			}

			//发送事件
			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("EXIST_ID_", existId);
			content.put("BET_STATE_", state);
			content.put("GAME_CODE_", gameCode);
			content.put("GAME_TYPE_", gameType);
			content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_VR.name());
			content.put("METHOD_", IbmMethodEnum.GAME_VR.name());

			IbmEventConfigSet configSet = new IbmEventConfigSet();
			configSet.setEventContent(content);
			configSet.setEventState(IbmStateEnum.SEND.name());
			configSet.setExecNumber(0);
			configSet.setCreateTime(new Date());
			configSet.setCreateTimeLong(System.currentTimeMillis());
			configSet.setUpdateTimeLong(System.currentTimeMillis());
			configSet.setState(IbmStateEnum.OPEN.name());
			String eventId = new IbmEventConfigSetService().save(configSet);

			CurrentTransaction.transactionCommit();

			content.put("EVENT_ID_", eventId);
			RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");

			bean.success();
		} catch (Exception e) {
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}
}