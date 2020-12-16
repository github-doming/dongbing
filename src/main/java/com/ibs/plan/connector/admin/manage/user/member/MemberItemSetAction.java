package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.entity.IbspHandicapMember;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_hm_set.entity.IbspHmSet;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
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
@ActionMapping(value = "/ibs/sys/manage/user/member/itemSet")
public class MemberItemSetAction
		extends CommAdminAction {
	private String handicapMemberId;
	private String handicapUrl;
	private String handicapCaptcha;
	private String autoLoginState;
	private String handicapSet;
	private String availableGame;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
			IbspHandicapMember handicapMember = handicapMemberService.find(handicapMemberId);
			if (handicapMember == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_MEMBER);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Date nowTime = new Date();
			//修改自动登录时间
			if (IbsTypeEnum.TRUE.name().equals(autoLoginState)) {
				//自动登录时间
				handicapMember.setLandedTimeLong(NumberTool.getLong(dataMap.get("LANDED_TIME_LONG_")));
			} else {
				handicapMember.setLandedTimeLong(0L);
			}
			handicapMember.setUpdateTime(nowTime);
			handicapMember.setUpdateTimeLong(nowTime.getTime());
			handicapMemberService.update(handicapMember);

			//修改盘口详情
			new IbspHandicapItemService().update(handicapMember.getHandicapItemId(), handicapUrl, handicapCaptcha);

			//修改可用游戏设置
			updateAvailableGameSet(handicapMember);

			//修改盘口会员设置
			updateHmSet();

			bean.success();
		} catch (Exception e) {
			log.error("会员详情设置失败", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void updateHmSet() throws Exception {
		JSONObject setObj = JSONObject.parseObject(handicapSet);
		IbspHmSetService hmSetService = new IbspHmSetService();
		IbspHmSet hmSet = hmSetService.findEntityByHmId(handicapMemberId);
		Integer betRateTh = NumberTool.intValueT(setObj.get("BET_RATE_")) / 100;
		hmSet.setBetRateT(betRateTh);
		hmSet.setProfitLimitMax(NumberTool.getInteger(setObj.get("PROFIT_LIMIT_MAX_")));
		hmSet.setProfitLimitMin(NumberTool.getInteger(setObj.get("PROFIT_LIMIT_MIN_")));
		hmSet.setLossLimitMin(NumberTool.getInteger(setObj.get("LOSS_LIMIT_MIN_")));
		hmSet.setBetMerger(setObj.getString("BET_MERGER_"));
		hmSet.setBlastStop(setObj.getString("BLAST_STOP_"));
		hmSet.setUpdateTimeLong(System.currentTimeMillis());
		hmSet.setDesc(this.getClass().getName().concat("修改盘口设置"));
		hmSetService.update(hmSet);
		JSONObject content = new JSONObject();
		content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
		content.put("SET_ITEM_", IbsMethodEnum.SET_HANDICAP.name());
		content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
		content.put("BET_RATE_T_", hmSet.getBetRateT());
		content.put("BET_MERGER_", hmSet.getBetMerger());
		EventThreadDefine.saveMemberConfigSetEvent(content, new Date(), this.getClass().getName().concat("发送会员设置信息"),
				new IbspEventConfigSetService());
	}

	private void updateAvailableGameSet(IbspHandicapMember handicapMember) throws Exception {
		Map<String, Object> memberInfo = new HashMap<>(2);
		memberInfo.put("IBM_HANDICAP_MEMBER_ID_", handicapMemberId);
		memberInfo.put("HANDICAP_ID_", handicapMember.getHandicapId());

		IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();
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
			hmGameSetService.save(memberInfo, gameIds, initMemberGameSet);
		}

		if (ContainerTool.notEmpty(delGameCodes)) {
			gameIds.clear();
			for (Object code : delGameCodes) {
				gameIds.add(GameUtil.id(code.toString()));
			}
			hmGameSetService.delGameSet(memberInfo, gameIds);
		}
		//判断会员是否登录中
		IbspHmInfoService hmInfoService = new IbspHmInfoService();
		String loginState = hmInfoService.findLoginState(handicapMemberId);
		if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
			return;
		}
		Date nowTime = new Date();
		//获取游戏信息，重新发送游戏信息
		List<Map<String, Object>> gameInfo = hmGameSetService.findByHmId(handicapMemberId);
		JSONObject content = new JSONObject();
		content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
		content.put("GAME_INFO_", gameInfo);
		content.put("SET_ITEM_", IbsMethodEnum.SET_GAME_INFO.name());
		content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
		EventThreadDefine.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送会员游戏设置信息"),
				new IbspEventConfigSetService());
	}

	private boolean valiParameters() {
		//盘口会员id
		handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");
		//导航地址
		handicapUrl = StringTool.getString(dataMap.get("handicapUrl"), "");
		//导航验证码
		handicapCaptcha = StringTool.getString(dataMap.get("handicapCaptcha"), "");
		//自动登录状态
		autoLoginState = StringTool.getString(dataMap.get("autoLoginState"), "");
		//盘口会员设置
		handicapSet = StringTool.getString(dataMap.get("handicapSet"), "");
		//可用游戏设置
		availableGame = StringTool.getString(dataMap.get("availableGame"), "");

		return StringTool
				.isEmpty(handicapMemberId, handicapUrl, handicapCaptcha, autoLoginState, handicapSet, availableGame);
	}
}
