package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
import com.ibs.plan.module.cloud.ibsp_exp_user.entity.IbspExpUser;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 修改盘口信息
 * @Author: null
 * @Date: 2020-03-18 13:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/handicapSet")
public class AppUserHandicapSetAction extends CommAdminAction {
	private String appUserId, avaiableGame, memberUsable;
	private int onlineHmNumberMax;

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

		if (onlineHmNumberMax <= 0 || onlineHmNumberMax >= Byte.MAX_VALUE) {
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return super.returnJson(bean);
		}

		try {
			IbspUserService userService = new IbspUserService();
			IbspUser user = userService.find(appUserId);
			if (user == null) {
				bean.putEnum(ReturnCodeEnum.app404Login);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			//修改最大在线会员
			IbspExpUserService expUserService = new IbspExpUserService();
			IbspExpUser expUser = expUserService.findByUserId(appUserId);
			//修改可用游戏
			updateAvailableGame(expUser.getAvailableGame());

			//修改可用会员盘口
			Map<Object, Object> memberInfo = ContainerTool
					.list2Map(JSON.parseArray(memberUsable), "HANDICAP_CODE_", "ONLINE_NUMBER_MAX_");
			checkMapValue(memberInfo);
			updateMemberHandicap(memberInfo);

			expUser.setAvailableGame(avaiableGame);

			expUser.setOnlineMax(onlineHmNumberMax);
			expUser.setUpdateUser(appUserId);
			expUser.setUpdateTime(new Date());
			expUser.setUpdateTimeLong(System.currentTimeMillis());
			expUserService.update(expUser);


			bean.success();
		} catch (Exception e) {
			log.error("修改用户盘口失败，", e);
			throw e;
		}
		return bean.toString();
	}

	private void checkMapValue(Map<Object, Object> map) {
		Iterator<Map.Entry<Object, Object>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<Object, Object> entry = entries.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (NumberTool.getInteger(value) > Byte.MAX_VALUE) {
				map.put(key, Byte.MAX_VALUE);
			}
		}
	}

	/**
	 * 修改会员盘口
	 */
	private void updateMemberHandicap(Map<Object, Object> memberInfo) throws Exception {
		IbspHandicapService handicapService = new IbspHandicapService();

		//获取用户拥有的所有会员盘口
		IbspHmUserService hmUserService = new IbspHmUserService();
		List<String> hmHandicapCodes = hmUserService.findHandicapCodeByUserId(appUserId);

		//新会员盘口codes-原来盘口codes-需要新加的盘口
		Set<Object> saveHmCodes = new HashSet<>(ContainerTool.getDifferent(memberInfo.keySet(), hmHandicapCodes));

		//原来盘口codes-新会员盘口codes-需要删除的盘口
		Set<Object> delHmCodes = new HashSet<>(ContainerTool.getDifferent(hmHandicapCodes, memberInfo.keySet()));

		Set<String> setStr;
		List<Map<String, Object>> handicaps;
		if (ContainerTool.notEmpty(saveHmCodes)) {
			setStr = new HashSet<>();
			for (Object code : saveHmCodes) {
				setStr.add(code.toString());
			}
			handicaps = handicapService.findHandicap(setStr);
			hmUserService.save(handicaps, appUserId, memberInfo);
		}

		if (ContainerTool.notEmpty(delHmCodes)) {
			Date nowTime = new Date();
			JSONObject content = new JSONObject();

			handicaps = hmUserService.findHandicap(appUserId, delHmCodes);
			LogoutHmController logoutHmController = new LogoutHmController();
			for (Map<String, Object> handicap : handicaps) {
				if (StringTool.notEmpty(handicap.get("ONLINE_MEMBERS_IDS_"))) {
					String[] handicapMemberIds = handicap.get("ONLINE_MEMBERS_IDS_").toString().split(",");

					for (String handicapMemberId : handicapMemberIds) {
						logoutHmController.execute(handicapMemberId, nowTime);
					}
				}
				hmUserService.updateByAppUserId(appUserId, handicap.get("HANDICAP_ID_").toString());
			}
		}

		//修改最大在线数
		memberInfo.keySet().retainAll(hmHandicapCodes);
		if (ContainerTool.notEmpty(memberInfo.keySet())) {
			for (Object handicapCode : memberInfo.keySet()) {
				hmUserService.updateOnlineNumMax(appUserId, handicapCode, memberInfo.get(handicapCode));
			}
		}


	}

	/**
	 * 修改可用游戏
	 */
	private void updateAvailableGame(String oldGames) throws Exception {
		List<String> usingGame = Arrays.asList(oldGames.split(","));
		List<String> updateGame = Arrays.asList(avaiableGame.split(","));

		//新游戏codes-原来游戏codes-需要新加的游戏
		Set<Object> saveGameCodes = new HashSet<>(ContainerTool.getDifferent(updateGame, usingGame));

		//原来游戏codes-新游戏codes-需要删除的游戏
		Set<Object> delGameCodes = new HashSet<>(ContainerTool.getDifferent(usingGame, updateGame));

		//获取会员信息
		List<Map<String, Object>> memberInfos = new IbspHandicapMemberService().findMemberByUserId(appUserId);

		IbspHandicapGameService handicapGameService = new IbspHandicapGameService();

		IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();

		List<String> gameIds = new ArrayList<>();
		if (ContainerTool.notEmpty(saveGameCodes)) {
			Map<String, Object> initMemberGameSet = hmGameSetService.findInitInfo();

			for (Object code : saveGameCodes) {
				gameIds.add(GameUtil.id(code.toString()));
			}
			//新增会员游戏设置
			for (Map<String, Object> memberInfo : memberInfos) {
				//获取存在的游戏id
				List<String> memberGameIds = handicapGameService
						.findHandicapGameInfo(memberInfo.get("HANDICAP_ID_").toString(), gameIds);
				hmGameSetService.save(memberInfo, memberGameIds, initMemberGameSet);
			}
		}
		Date nowTime = new Date();
		JSONObject content = new JSONObject();
		if (ContainerTool.notEmpty(delGameCodes)) {
			gameIds.clear();
			for (Object code : delGameCodes) {
				gameIds.add(GameUtil.id(code.toString()));
			}
			IbspEventConfigSetService eventConfigSetService = new IbspEventConfigSetService();
			content.put("SET_ITEM_", IbsMethodEnum.SET_BET_STATE.name());
			content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
			content.put("BET_STATE_", IbsTypeEnum.FALSE.name());
			//删除会员游戏设置
			for (Map<String, Object> memberInfo : memberInfos) {
				if (IbsStateEnum.LOGIN.name().equals(memberInfo.get("STATE_"))) {
					List<Map<String, Object>> hmGameSet = hmGameSetService.findBetInfo(memberInfo, gameIds);
					//发送消息，停止该游戏投注
					for (Map<String, Object> map : hmGameSet) {
						content.put("HANDICAP_MEMBER_ID_", map.get("HANDICAP_MEMBER_ID_"));
						content.put("GAME_CODE_", GameUtil.code(map.get("GAME_ID_").toString()).name());
						EventThreadDefine
								.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("，修改投注状态"),
										eventConfigSetService);
					}
				}
				hmGameSetService.delGameSet(memberInfo, gameIds);
			}

		}
		//重新发送游戏信息
		if (ContainerTool.notEmpty(saveGameCodes) || ContainerTool.notEmpty(delGameCodes)) {
			content.clear();
			List<Map<String, Object>> gameInfo;
			content.put("SET_ITEM_", IbsMethodEnum.SET_GAME_INFO.name());
			content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
			for (Map<String, Object> memberInfo : memberInfos) {
				if (IbsMethodEnum.LOGIN.name().equals(memberInfo.get("STATE_"))) {
					gameInfo = hmGameSetService.findByHmId(memberInfo.get("IBS_HANDICAP_MEMBER_ID_").toString());
					content.put("HANDICAP_MEMBER_ID_", memberInfo.get("IBS_HANDICAP_MEMBER_ID_"));
					content.put("GAME_INFO_", gameInfo);
					EventThreadDefine
							.saveMemberConfigSetEvent(content, nowTime, this.getClass().getName().concat("发送会员游戏设置信息"),
									new IbspEventConfigSetService());
				}
			}
		}
	}

	private boolean valiParameters() {

		appUserId = StringTool.getString(dataMap, "appUserId", "");
		//会员可用盘口
		memberUsable = StringTool.getString(dataMap, "memberUsable", "");
		//可用游戏avaiableGame
		avaiableGame = StringTool.getString(dataMap, "avaiableGame", "");
		//会员最大登录数量
		onlineHmNumberMax = NumberTool.getInteger(dataMap, "onlineHmNumberMax", 10);

		return StringTool.isEmpty(appUserId, onlineHmNumberMax, memberUsable, avaiableGame);
	}
}
