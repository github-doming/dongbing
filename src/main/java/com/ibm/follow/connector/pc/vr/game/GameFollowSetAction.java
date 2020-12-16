package com.ibm.follow.connector.pc.vr.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
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
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 游戏跟投设置
 * @Author: null
 * @Date: 2020-07-16 13:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/gameFollowSet", method = HttpConfig.Method.POST)
public class GameFollowSetAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if(super.denyTime()){
			bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
			bean.putSysEnum(IbmCodeEnum.CODE_503);
			return bean;
		}
		String vrMemberId = dataMap.getOrDefault("VR_MEMBER_ID_", "").toString();

		Object followSet = dataMap.getOrDefault("FOLLOW_SET_", "");

		if (StringTool.isEmpty(vrMemberId) || ContainerTool.isEmpty(followSet)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
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
			JSONArray gameSets = JSONArray.parseArray(followSet.toString());
			JSONObject content = new JSONObject();
			content.put("EXIST_MEMBER_VR_ID_", clientInfo.get("EXIST_MEMBER_VR_ID_"));
			content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
			content.put("METHOD_", IbmMethodEnum.MANAGE_VR.name());
			content.put("USER_ID_", appUserId);
			content.put("GAME_INFO_", gameSets);
			SetBean setBean=new SetBean();

			VrFmGameSetService gameSetService=new VrFmGameSetService();
			Date nowTime = new Date();
			for (int i = 0; i < gameSets.size(); i++) {
				JSONObject jsonObject = gameSets.getJSONObject(i);
				String gameCode = jsonObject.getString("GAME_CODE_");
				setBean.analyze(jsonObject);
				if (!setBean.vali()) {
					bean.putEnum(IbmCodeEnum.IBM_401_DATA);
					bean.putSysEnum(IbmCodeEnum.CODE_401);
					return super.returnJson(bean);
				}
				String gameSetId=gameSetService.findId(appUserId,vrMemberId,gameCode);
				if (StringTool.isEmpty(gameSetId)) {
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return super.returnJson(bean);
				}
				gameSetService.update(gameSetId, setBean.betState, setBean.betFollowMultipleTh,
						setBean.betLeastAmountTh, setBean.betMostAmountTh, setBean.betFilterNumber,
						setBean.betFilterTwoSide, setBean.numberOpposing, setBean.twoSideOpposing, nowTime);
			}
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
			log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"));
			bean.error(e.getMessage());
		}

		return bean;
	}
	private static class SetBean {
		String betState;
		Integer betFollowMultipleTh;
		Integer betLeastAmountTh;
		Integer betMostAmountTh;
		String betFilterNumber;
		String betFilterTwoSide;
		String numberOpposing;
		String twoSideOpposing;

		/**
		 * 解析设置bean
		 *
		 * @param json 设置json信息
		 * @return 设置bean
		 */
		public SetBean analyze(JSONObject json) {
			betState = json.getString("BET_STATE_");
			betFollowMultipleTh = NumberTool.intValueT(json.getDouble("BET_FOLLOW_MULTIPLE_"));
			betLeastAmountTh = NumberTool.intValueT(json.getInteger("BET_LEAST_AMOUNT_"));
			betMostAmountTh = NumberTool.intValueT(json.getInteger("BET_MOST_AMOUNT_"));
			betFilterNumber = json.getString("BET_FILTER_NUMBER_");
			betFilterTwoSide = json.getString("BET_FILTER_TWO_SIDE_");
			numberOpposing = json.getString("NUMBER_OPPOSING_");
			twoSideOpposing = json.getString("TWO_SIDE_OPPOSING_");
			return this;
		}

		/**
		 * 验证参数是否合法
		 *
		 * @return 合法 true
		 */
		public boolean vali() {
			return IbmTypeEnum.booleanType(betState) && IbmTypeEnum.booleanType(betFilterNumber) && IbmTypeEnum
					.booleanType(betFilterTwoSide) && IbmTypeEnum.booleanType(numberOpposing) && IbmTypeEnum
					.booleanType(twoSideOpposing);
		}

	}
}
