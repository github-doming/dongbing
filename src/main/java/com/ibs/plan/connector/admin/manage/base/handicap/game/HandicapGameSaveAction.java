package com.ibs.plan.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.entity.IbspHandicapGame;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增盘口游戏
 * @Author: null
 * @Date: 2020-04-18 11:37
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/game/save", method = HttpConfig.Method.POST)
public class HandicapGameSaveAction
		extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");
		//游戏codes
		String gameCodes = StringTool.getString(dataMap.get("gameCodes"), "");

		if (StringTool.isEmpty(handicapId, gameCodes)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}

		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			IbspGameService gameService = new IbspGameService();
			IbspHandicapGameService handicapGameService = new IbspHandicapGameService();

			IbspHandicap handicap = handicapService.find(handicapId);
			if (handicap == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			Date nowTime = new Date();

			IbspHandicapGame handicapGame = new IbspHandicapGame();
			handicapGame.setHandicapId(handicapId);
			handicapGame.setCloseTime(0);
			handicapGame.setDrawTime(0);
			handicapGame.setSn(0);
			handicapGame.setCreateUser(appUserId);
			handicapGame.setCreateTime(nowTime);
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setState(IbsStateEnum.OPEN.name());

			IbspExpUserService expUserService = new IbspExpUserService();
			IbspHandicapMemberService handicapMemberService = new IbspHandicapMemberService();
			IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();
			Map<String, Object> hmGameSetDefMap = hmGameSetService.findInitInfo();
			IbspHmGameSet hmGameSet = new IbspHmGameSet();
			hmGameSet.setHandicapId(handicapId);

			hmGameSet.setBetState(hmGameSetDefMap.get("BET_STATE_"));
			hmGameSet.setBetMode(hmGameSetDefMap.get("BET_MODE_"));
			hmGameSet.setIsAutoStartBet(hmGameSetDefMap.get("IS_AUTO_START_BET_"));
			hmGameSet.setAutoStartBetTimeLong(NumberTool.getLong(hmGameSetDefMap.get("AUTO_START_BET_TIME_LONG_"), 0L));
			hmGameSet.setIsAutoStopBet(hmGameSetDefMap.get("IS_AUTO_STOP_BET_"));
			hmGameSet.setAutoStartBetTimeLong(NumberTool.getLong(hmGameSetDefMap.get("AUTO_STOP_BET_TIME_LONG_"), 0L));
			hmGameSet.setIsInverse(hmGameSetDefMap.get("IS_INVERSE_"));
			hmGameSet.setIncreaseState(hmGameSetDefMap.get("INCREASE_STATE_"));
			hmGameSet.setIncreaseStopTimeLong(NumberTool.getLong(hmGameSetDefMap.get("INCREASE_STOP_TIME_LONG_")));

			hmGameSet.setBetSecond(hmGameSetDefMap.get("BET_SECOND_"));
			hmGameSet.setSplitTwoSideAmount(hmGameSetDefMap.get("SPLIT_TWO_SIDE_AMOUNT_"));
			hmGameSet.setSplitNumberAmount(hmGameSetDefMap.get("SPLIT_NUMBER_AMOUNT_"));
			hmGameSet.setCreateTime(nowTime);
			hmGameSet.setUpdateTime(nowTime);
			hmGameSet.setState(IbsStateEnum.OPEN.name());

			List<Map<String, Object>> gameInfos = gameService.listByGameCodes(gameCodes.split(","));
			for (Map<String, Object> gameInfo : gameInfos) {
				String gameId = gameInfo.get("GAME_ID_").toString();
				handicapGame.setIbspHandicapGameId(null);
				handicapGame.setGameId(gameId);
				handicapGame.setGameCode(gameInfo.get("GAME_CODE_"));
				handicapGame.setHandicapGameName(gameInfo.get("GAME_NAME_"));
				handicapGame.setIcon(gameInfo.get("ICON_"));
				handicapGame.setCreateTimeLong(System.currentTimeMillis());
				handicapGame.setUpdateTimeLong(System.currentTimeMillis());
				handicapGameService.save(handicapGame);

				// 同步用户所拥有的的游戏
                /*
                    1. 获取拥有盘口和游戏的用户
                    2. 根据盘口和用户找出拥有的盘口会员
                    3. 给会员添加游戏
                 */
				List<String> userIds = expUserService.listUserId(gameInfo.get("GAME_CODE_").toString());
				if (ContainerTool.isEmpty(userIds)) {
					continue;
				}
				hmGameSet.setGameId(gameId);
				List<Map<String, Object>> hmInfos = handicapMemberService.lisIds(userIds);
				for (Map<String, Object> hmInfo : hmInfos) {
					if (ContainerTool.isEmpty(hmInfo)) {
						continue;
					}
					String hmId = hmInfo.get("HANDICAP_MEMBER_ID_").toString();
					if (StringTool.isEmpty(hmGameSetService.findId(hmId, gameId))) {
						hmGameSet.setIbspHmGameSetId(null);
						hmGameSet.setHandicapMemberId(hmId);
						hmGameSet.setCreateTimeLong(System.currentTimeMillis());
						hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
						hmGameSetService.save(hmGameSet);
					}

				}
//				for (String userId : userIds) {
//					List<String> handicapMemberIds = handicapMemberService.listId(userId, handicap.getHandicapCode());
//					if (ContainerTool.isEmpty(handicapMemberIds)) {
//						continue;
//					}
//					for (String handicapMemberId : handicapMemberIds) {
//						if (StringTool.isEmpty(hmGameSetService.findId(handicapMemberId, gameId))) {
//							hmGameSet.setIbspHmGameSetId(null);
//							hmGameSet.setHandicapMemberId(handicapMemberId);
//							hmGameSet.setCreateTimeLong(System.currentTimeMillis());
//							hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
//							hmGameSetService.save(hmGameSet);
//						}
//					}
//				}
			}

			bean.success();
		} catch (Exception e) {
			log.error("新增盘口游戏错误");
			throw e;
		}
		return bean.toString();
	}
}
