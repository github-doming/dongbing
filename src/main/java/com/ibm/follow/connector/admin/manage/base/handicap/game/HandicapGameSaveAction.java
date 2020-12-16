package com.ibm.follow.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_game.entity.IbmGame;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.entity.IbmHandicapGame;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 新增盘口游戏
 * @Author: null
 * @Date: 2020-04-18 11:37
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/game/save") public class HandicapGameSaveAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");
		//盘口游戏类型
		String type = StringTool.getString(dataMap, "type", "");
		//游戏codes
		String gameCodes = StringTool.getString(dataMap.get("gameCodes"), "");

		if (StringTool.isEmpty(handicapId, gameCodes)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		JSONArray codeList = JSON.parseArray(gameCodes);
		try {
			IbmHandicapService handicapService = new IbmHandicapService();
			IbmGameService gameService = new IbmGameService();
			IbmHandicapGameService handicapGameService = new IbmHandicapGameService();

			IbmHandicap handicap = handicapService.find(handicapId);
			if (handicap == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return super.returnJson(bean);
			}
			Date nowTime = new Date();

			IbmHandicapGame handicapGame = new IbmHandicapGame();
			handicapGame.setHandicapId(handicapId);
			handicapGame.setHandicapName(handicap.getHandicapName());
			handicapGame.setHandicapCode(handicap.getHandicapCode());
			handicapGame.setType(type);
			handicapGame.setCloseTime(0);
			handicapGame.setSn(0);
			handicapGame.setCreateUser(adminUser.getUserName());
			handicapGame.setCreateTime(nowTime);
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setState(IbmStateEnum.OPEN.name());

			IbmExpUserService expUserService = new IbmExpUserService();
			IbmHandicapMemberService handicapMemberService = new IbmHandicapMemberService();
			IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
			IbmHmGameSet hmGameSetDef = hmGameSetService.findDef();
			IbmHmGameSet hmGameSet = new IbmHmGameSet();
			hmGameSet.setHandicapId(handicapId);
			hmGameSet.setUserId(adminUserId);
			hmGameSet.setBetState(hmGameSetDef.getBetState());
			hmGameSet.setBetMode(hmGameSetDef.getBetMode());
			hmGameSet.setBetAutoStop(hmGameSetDef.getBetAutoStop());
			hmGameSet.setBetAutoStopTimeLong(hmGameSetDef.getBetAutoStopTimeLong());
			hmGameSet.setBetAutoStart(hmGameSetDef.getBetAutoStart());
			hmGameSet.setBetAutoStartTimeLong(hmGameSetDef.getBetAutoStartTimeLong());
			hmGameSet.setBetSecond(hmGameSetDef.getBetSecond());
			hmGameSet.setSplitTwoSideAmount(hmGameSetDef.getSplitTwoSideAmount());
			hmGameSet.setSplitNumberAmount(hmGameSetDef.getSplitNumberAmount());
			hmGameSet.setCreateTime(nowTime);
			hmGameSet.setUpdateTime(nowTime);
			hmGameSet.setState(IbmStateEnum.OPEN.name());

			IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
			IbmHaGameSetService haGameSetService = new IbmHaGameSetService();

			IbmHaGameSet haGameSetDef = haGameSetService.findDef();
			IbmHaGameSet haGameSet = new IbmHaGameSet();
			haGameSet.setHandicapId(handicapId);
			haGameSet.setUserId(adminUserId);
			haGameSet.setBetState(haGameSetDef.getBetState());
			haGameSet.setBetFollowMultipleT(haGameSetDef.getBetFollowMultipleT());
			haGameSet.setBetLeastAmountT(haGameSetDef.getBetLeastAmountT());
			haGameSet.setBetMostAmountT(haGameSetDef.getBetMostAmountT());
			haGameSet.setBetFilterNumber(haGameSetDef.getBetFilterNumber());
			haGameSet.setNumberOpposing(haGameSetDef.getNumberOpposing());
			haGameSet.setTwoSideOpposing(haGameSetDef.getTwoSideOpposing());
			haGameSet.setBetRecordRows(haGameSetDef.getBetRecordRows());
			haGameSet.setCreateTime(nowTime);
			haGameSet.setUpdateTime(nowTime);
			haGameSet.setState(IbmStateEnum.OPEN.name());

			for (Object gameCode : codeList) {
				IbmGame game = gameService.findEntity(gameCode);
				handicapGame.setIbmHandicapGameId(null);
				handicapGame.setGameId(game.getIbmGameId());
				handicapGame.setGameCode(game.getGameCode());
				handicapGame.setGameName(game.getGameName());
				handicapGame.setIcon(game.getIcon());
				handicapGame.setCreateTimeLong(System.currentTimeMillis());
				handicapGame.setUpdateTimeLong(System.currentTimeMillis());
				handicapGameService.save(handicapGame);

				// 同步用户所拥有的的游戏
                /*
                    1. 获取拥有盘口和游戏的用户
                    2. 根据盘口和用户找出拥有的盘口会员或代理
                    3. 给会员或代理添加游戏
                 */
				List<String> userIds = expUserService
						.listUserId(game.getGameCode(), handicap.getHandicapCode(), handicap.getHandicapCategory());
				if (ContainerTool.isEmpty(userIds)) {
					continue;
				}
				hmGameSet.setGameId(game.getIbmGameId());
				for (String userId : userIds) {
					if (IbmTypeEnum.MEMBER.equal(handicap.getHandicapCategory())) {
						List<String> handicapMemberIds = handicapMemberService
								.listId(userId, handicap.getHandicapCode());
						if (ContainerTool.isEmpty(handicapMemberIds)) {
							continue;
						}
						for (String handicapMemberId : handicapMemberIds) {
							if (StringTool.isEmpty(hmGameSetService.findId(handicapMemberId, game.getIbmGameId()))) {
								hmGameSet.setIbmHmGameSetId(null);
								hmGameSet.setHandicapMemberId(handicapMemberId);
								hmGameSet.setCreateTimeLong(System.currentTimeMillis());
								hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
								hmGameSetService.save(hmGameSet);
							}
						}
					} else {
						List<String> handicapAgentIds = handicapAgentService.listId(userId, handicap.getHandicapCode());
						if (ContainerTool.isEmpty(handicapAgentIds)) {
							continue;
						}
						for (String handicapAgentId : handicapAgentIds) {
							if (StringTool.isEmpty(haGameSetService.findId(handicapAgentId, game.getIbmGameId()))) {
								haGameSet.setIbmHaGameSetId(null);
								haGameSet.setHandicapAgentId(handicapAgentId);
								haGameSet.setCreateTimeLong(System.currentTimeMillis());
								haGameSet.setUpdateTimeLong(System.currentTimeMillis());
								haGameSetService.save(haGameSet);
							}
						}
					}
				}
			}

			bean.success();
		} catch (Exception e) {
			log.error("新增盘口游戏错误",e.getMessage());
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
