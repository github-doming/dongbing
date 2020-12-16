package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 会员投注设置
 * @Author: null
 * @Date: 2020-03-19 16:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/member/betSet")
public class MemberBetSetAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口会员id
		String handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");
		//游戏投注设置
		String gameBetSet = StringTool.getString(dataMap.get("gameBetSet"), "");

		if (StringTool.isEmpty(handicapMemberId, gameBetSet)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspHandicapMember handicapMember = new IbspHandicapMemberService().find(handicapMemberId);
			if (handicapMember == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();

			JSONArray array = JSONArray.parseArray(gameBetSet);
			IbspHmGameSet hmGameSet;
			for (Object jsonObj : array) {
				JSONObject gameSet = (JSONObject) jsonObj;
				String gameId = GameUtil.id(gameSet.getString("GAME_CODE_"));
				hmGameSet = hmGameSetService.findEntity(handicapMemberId, gameId);
				if (hmGameSet == null) {
					continue;
				}
				hmGameSet.setBetState(gameSet.getString("BET_STATE_"));
				hmGameSet.setIsAutoStartBet(gameSet.getString("IS_AUTO_START_BET_"));
				hmGameSet.setAutoStartBetTimeLong(Long.parseLong(gameSet.getString("AUTO_START_BET_TIME_LONG_")) + 5000L);
				hmGameSet.setIsAutoStopBet(gameSet.getString("IS_AUTO_STOP_BET_"));
				hmGameSet.setAutoStopBetTimeLong(Long.parseLong(gameSet.getString("AUTO_STOP_BET_TIME_LONG_")) + 5000L);
				hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
				hmGameSetService.update(hmGameSet);
			}
			String loginState = new IbspHmInfoService().findLoginState(handicapMemberId);
			if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
				bean.success();
				return bean.toString();
			}
			//发送游戏信息
			List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMember.getIbspHandicapMemberId());
			JSONObject content = new JSONObject();
			content.put("HANDICAP_MEMBER_ID_", handicapMember.getIbspHandicapMemberId());
			content.put("GAME_INFO_", gameInfo);
			content.put("SET_ITEM_", IbsMethodEnum.SET_GAME_INFO.name());
			content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
			EventThreadDefine.saveMemberConfigSetEvent(content, new Date(), this.getClass().getName().concat("发送会员游戏设置信息"),
					new IbspEventConfigSetService());

			bean.success();
		} catch (Exception e) {
			log.error("会员投注设置失败", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
