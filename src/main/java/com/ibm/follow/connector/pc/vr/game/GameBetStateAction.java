package com.ibm.follow.connector.pc.vr.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.service.VrFmGameSetService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 游戏投注状态
 * @Author: null
 * @Date: 2020-07-16 11:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/gameBetState", method = HttpConfig.Method.POST)
public class GameBetStateAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String vrMemberId = dataMap.getOrDefault("VR_MEMBER_ID_", "").toString();

		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String betState = dataMap.getOrDefault("BET_STATE_", "").toString();
		if (StringTool.isEmpty(vrMemberId, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		if (!IbmTypeEnum.booleanType(betState)) {
			bean.putEnum(IbmCodeEnum.IBM_401_STATE);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			VrMemberService vrMemberService=new VrMemberService();
			VrMember vrMember=vrMemberService.find(vrMemberId);
			if(vrMember==null){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			//如果虚拟会员没有绑定客户端，暂定不可跟投
			VrClientMemberService clientMemberService=new VrClientMemberService();
			Map<String,Object> clientInfo=clientMemberService.findInfo(vrMemberId);
			if(ContainerTool.isEmpty(clientInfo)){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return super.returnJson(bean);
			}
			VrFmGameSetService fmGameSetService=new VrFmGameSetService();
			Map<String,Object> gameInfo=fmGameSetService.findGameInfo(appUserId,vrMemberId,gameCode);
			if(ContainerTool.isEmpty(gameInfo)){
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			fmGameSetService.updateBetState(gameInfo.remove("VR_FM_GAME_SET_ID_").toString(),betState);

			gameInfo.put("BET_STATE_",betState);
			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("USER_ID_", appUserId);
			content.put("GAME_CODE_", gameCode);
			content.put("GAME_INFO_", gameInfo);
			content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
			content.put("METHOD_", IbmMethodEnum.MANAGE_VR.name());

			IbmEventConfigSet configSet=new IbmEventConfigSet();
			configSet.setEventContent(content);
			configSet.setEventState(IbmStateEnum.SEND.name());
			configSet.setExecNumber(0);
			configSet.setCreateTime(new Date());
			configSet.setCreateTimeLong(System.currentTimeMillis());
			configSet.setUpdateTimeLong(System.currentTimeMillis());
			configSet.setState(IbmStateEnum.OPEN.name());
			String eventId=new IbmEventConfigSetService().save(configSet);

			CurrentTransaction.transactionCommit();

			content.put("EVENT_ID_", eventId);
			RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");

			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏跟投状态失败"));
			bean.error(e.getMessage());
		}
		return bean;
	}
}
