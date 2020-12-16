package com.ibm.follow.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.controller.process.LoginHmController;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent_member.service.IbmHandicapAgentMemberService;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.entity.IbmHandicapMember;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.entity.IbmHmSet;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 会员详情设置
 * @Author: null
 * @Date: 2020-03-19 18:27
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/member/itemSet") public class MemberItemSetAction
		extends CommAdminAction {
	private String handicapMemberId;
	private String handicapUrl;
	private String handicapCaptcha;
	private String autoLoginState;
	private String handicapSet;
	private String availableGame;

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
			IbmHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
			if (handicapMember == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			Date nowTime = new Date();
			//修改自动登录时间
			if (IbmTypeEnum.TRUE.name().equals(autoLoginState)) {
				//自动登录时间
				handicapMember.setLandedTimeLong(NumberTool.getLong(dataMap.get("landedTime")));
			} else {
				handicapMember.setLandedTimeLong(0L);
			}
			handicapMember.setUpdateTime(nowTime);
			handicapMember.setUpdateTimeLong(nowTime.getTime());
			handicapMemberService.update(handicapMember);

			//修改盘口详情
			new IbmHandicapItemService().update(handicapMember.getHandicapItemId(), handicapUrl, handicapCaptcha);

			//修改可用游戏设置
			updateAvailableGameSet(handicapMember);

			//修改盘口会员设置
			updateHmSet();

			bean.success();
		} catch (Exception e) {
			log.error("会员详情设置失败", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}

	private void updateHmSet() throws Exception {
		JSONObject setObj = JSONObject.parseObject(handicapSet);
		IbmHmSetService hmSetService = new IbmHmSetService();
		IbmHmSet hmSet = hmSetService.findEntityByHmId(handicapMemberId);
		Integer betRateTh = NumberTool.intValueT(setObj.get("BET_RATE_")) / 100;
		hmSet.setBetRecordRows(NumberTool.getInteger(setObj.get("BET_RECORD_ROWS_")));
		hmSet.setBetRateT(betRateTh);
		hmSet.setProfitLimitMax(NumberTool.getInteger(setObj.get("PROFIT_LIMIT_MAX_")));
		hmSet.setProfitLimitMin(NumberTool.getInteger(setObj.get("PROFIT_LIMIT_MIN_")));
		hmSet.setLossLimitMin(NumberTool.getInteger(setObj.get("LOSS_LIMIT_MIN_")));
		hmSet.setBetMerger(setObj.getString("BET_MERGER_"));
		hmSet.setUpdateTimeLong(System.currentTimeMillis());
		hmSet.setDesc(this.getClass().getName().concat("修改盘口设置"));
		hmSetService.update(hmSet);

		JSONObject content = new JSONObject();
		content.put("SET_ITEM_", IbmMethodEnum.SET_HANDICAP.name());
		content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());
		content.put("BET_RATE_T_", hmSet.getBetRateT());
		content.put("BET_MERGER_", hmSet.getBetMerger());

		String desc= this.getClass().getName().concat("，发送会员设置信息");
		EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
	}

	private void updateAvailableGameSet(IbmHandicapMember handicapMember) throws Exception {
		Map<String, Object> memberInfo = new HashMap<>(2);
		memberInfo.put("IBM_HANDICAP_MEMBER_ID_", handicapMemberId);
		memberInfo.put("HANDICAP_ID_", handicapMember.getHandicapId());

		IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
		List<String> usingGame = hmGameSetService.listGameCodes(handicapMemberId);

		List<String> updateGame = Arrays.asList(availableGame.split(","));

		//新游戏codes-原来游戏codes-需要新加的游戏
		Set<Object> saveGameCodes = new HashSet<>(ContainerTool.getDifferent(updateGame, usingGame));

		//原来游戏codes-新游戏codes-需要删除的游戏
		Set<Object> delGameCodes = new HashSet<>(ContainerTool.getDifferent(usingGame, updateGame));

		List<String> gameIds = new ArrayList<>();
		if (ContainerTool.notEmpty(saveGameCodes)) {
			Map<String, Object> initMemberGameSet = hmGameSetService.findInitInfo();
			for (Object code : saveGameCodes) {
				gameIds.add(GameUtil.id(code.toString()));
			}
			hmGameSetService.save(handicapMember.getAppUserId(), memberInfo, gameIds, initMemberGameSet);
		}

		if (ContainerTool.notEmpty(delGameCodes)) {
			gameIds.clear();
			for (Object code : delGameCodes) {
				gameIds.add(GameUtil.id(code.toString()));
			}
			hmGameSetService.delGameSet(memberInfo, gameIds);
		}
		//判断会员是否登录中
		IbmHmInfoService hmInfoService = new IbmHmInfoService();
		String loginState = hmInfoService.findLoginState(handicapMemberId);
		if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
			return;
		}

		//获取游戏信息，重新发送游戏信息
		List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMemberId);
		JSONObject content = new JSONObject();
		content.put("GAME_INFO_", gameInfo);
		content.put("SET_ITEM_", IbmMethodEnum.SET_GAME_INFO.name());
		content.put("METHOD_", IbmMethodEnum.MEMBER_SET.name());

		String desc= this.getClass().getName().concat("，发送会员游戏设置信息");
		boolean flag=EventThreadDefine.sendClientConfig(content,handicapMemberId,IbmTypeEnum.MEMBER,desc);
		if (!flag){
			return;
		}
		content.clear();
		//重新发送绑定信息
		IbmHandicapAgentMemberService handicapAgentMemberService = new IbmHandicapAgentMemberService();
		List<String> handicapAgentIds = handicapAgentMemberService.listHaId(handicapMemberId);
		if (ContainerTool.isEmpty(handicapAgentIds)) {
			return;
		}
        Map<String,Object> accountInfo=new IbmHandicapMemberService().findMemberAccountInfo(handicapMemberId);

		IbmEventConfigSetService configSetService = new IbmEventConfigSetService();
		Map<String, Object> bindInfo = new IbmClientHmService().findBindInfo(handicapMemberId);
        if(ContainerTool.isEmpty(bindInfo)){
            log.error("获取盘口会员【" + handicapMemberId + "】存在会员信息失败");
            return;
        }
		//投注模式
		List<Map<String, Object>> betModeInfos = new IbmHmGameSetService().listBetModeInfo(handicapMemberId);

		content.putAll(bindInfo);
		content.put("SET_ITEM_", IbmMethodEnum.SET_BIND.name());
		content.put("METHOD_", IbmMethodEnum.MANAGE_SET.name());
		content.put("MEMBER_HANDICAP_CODE_", handicapMember.getHandicapCode());
		content.put("MEMBER_ACCOUNT_", accountInfo.get("MEMBER_ACCOUNT_"));
		for (String handicapAgentId : handicapAgentIds) {
            LoginHmController.bindUserAgent(handicapAgentId,content,betModeInfos);
		}
	}

	private boolean valiParameters() {
		//盘口会员id
		handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");
		//导航地址
		handicapUrl =StringTool.getString(dataMap.get("handicapUrl"), "");
		//导航验证码
		handicapCaptcha = StringTool.getString(dataMap.get("handicapCaptcha"), "");
		//自动登录状态
		autoLoginState = StringTool.getString(dataMap.get("autoLoginState"), "");
		//盘口会员设置
		handicapSet = StringTool.getString(dataMap.get("handicapSet"), "");
		//可用游戏设置
		availableGame =StringTool.getString(dataMap.get("availableGame"), "");

		return StringTool
				.isEmpty(handicapMemberId, handicapUrl, handicapCaptcha, autoLoginState, handicapSet, availableGame);
	}
}
