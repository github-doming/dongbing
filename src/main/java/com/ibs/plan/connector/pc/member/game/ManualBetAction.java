package com.ibs.plan.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.game.Game;
import com.common.game.factory.GameFactory;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.core.controller.mq.ManualBetController;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.entity.IbspHmBetItem;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 手动投注
 * @Author: null
 * @Date: 2020-05-29 10:23
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/manualBet", method = HttpConfig.Method.POST)
public class ManualBetAction extends CommCoreAction {
	private String handicapMemberId;
	private String betContentStr;
	private String betFundsStr;
	private String gameCode;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (!valiParameters()) {
			return bean.put401Data();
		}
		if(super.denyTime()){
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		try{
			String gameId =GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			// 获取该盘口会员的信息
			String loginState= new IbspHmInfoService().findLoginState(handicapMemberId);
			//是否登录
			if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}
			IbspHmGameSetService gameSetService=new IbspHmGameSetService();
			Map<String,Object> hmGameSet=gameSetService.findInfo(handicapMemberId,gameId);
			if(ContainerTool.isEmpty(hmGameSet)|| IbsTypeEnum.VIRTUAL.name().equals(hmGameSet.get("BET_MODE_"))){
				bean.putEnum(CodeEnum.IBS_404_VR_NOT_MANUAL_BET);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String,Object> handicapGameInfo=new IbspHandicapGameService().findInfo(handicapId, gameId);
			if(ContainerTool.isEmpty(handicapGameInfo)){
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			int closeTime= NumberTool.getInteger(handicapGameInfo,"CLOSE_TIME",0);

			HandicapUtil.Code handicap =HandicapUtil.code(handicapId);
			GameUtil.Code game =GameUtil.Code.valueOf(gameCode);

			long  drawTime =game.getGameFactory().period(handicap).getDrawTime()-System.currentTimeMillis();
			if (drawTime <= closeTime  || drawTime > game.getGameFactory().getInterval()) {
				bean.putEnum(CodeEnum.IBS_403_CLOSING_TIME);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			GameFactory<?> factory = game.getGameFactory();
			Object period=factory.period(handicap).findPeriod();
			//存储手动投注数据
			String hmBetItemId = manualBet(game, handicap, gameId, handicapId);
			if (StringTool.isEmpty(hmBetItemId)) {
				bean.putSysEnum(CodeEnum.IBS_403_DATA_ERROR);
				bean.putEnum(CodeEnum.CODE_403);
				return bean;
			}
			//发送投注信息
			String result = new ManualBetController(hmBetItemId, handicapMemberId,game,handicap,
					handicapGameInfo.get("GAME_DRAW_TYPE_").toString(),period).execute();
			if (!Boolean.parseBoolean(result)) {
				bean.putSysEnum(CodeEnum.IBS_403_DATA_ERROR);
				bean.putEnum(CodeEnum.CODE_403);
				return bean;
			}
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "手动投注失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private String manualBet(GameUtil.Code gameCode, HandicapUtil.Code handicap, String gameId, String handicapId) throws Exception {
		int betFundsTh = NumberTool.intValueT(betFundsStr);
		int fundsTh = 0;
		int contentLen = 0;
		GameFactory<?> factory = gameCode.getGameFactory();
		Game game = factory.game();
		Map<Integer, Set<Integer>> betCode = game.getBetCodeByJson(betContentStr);
		if (ContainerTool.isEmpty(betCode)) {
			return null;
		}
		StringBuilder content = new StringBuilder();
		for (Map.Entry<Integer, Set<Integer>> entry : betCode.entrySet()) {
			for (Integer type : entry.getValue()) {
				contentLen++;
				fundsTh += betFundsTh;
				content.append(game.itemStr( entry.getKey(), type, betFundsTh)).append("#");
			}
		}
		Object period=factory.period(handicap).findPeriod();
		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		IbspHmBetItem betItem=new IbspHmBetItem();
		betItem.setClientBetId(IbsTypeEnum.MANUAL.getCode());
		betItem.setHandicapMemberId(handicapMemberId);
		betItem.setHandicapId(handicapId);
		betItem.setGameId(gameId);
		betItem.setPlanId(IbsTypeEnum.MANUAL.name());
		betItem.setPeriod(period);
		betItem.setBetMode(IbsTypeEnum.REAL.name());
		betItem.setBetType(IbsTypeEnum.MANUAL.ordinal());
		betItem.setPlanGroupDesc(IbsTypeEnum.MANUAL.getMsg());
		betItem.setBetContentLen(contentLen);
		betItem.setBetContent(content.toString());
		betItem.setFundT(fundsTh);
		betItem.setExecState(IbsStateEnum.SEND.name());
		betItem.setCreateTime(new Date());
		betItem.setCreateTimeLong(System.currentTimeMillis());
		betItem.setUpdateTimeLong(System.currentTimeMillis());
		betItem.setState(IbsStateEnum.OPEN.name());
		return new IbspHmBetItemService().save(betItem);
	}

	private boolean valiParameters() {

		handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		betContentStr = dataMap.getOrDefault("BET_CONTENT_", "").toString();
		betFundsStr = dataMap.getOrDefault("BET_FUNDS_", "").toString();

		return !StringTool.isEmpty(handicapMemberId, gameCode, betContentStr, betFundsStr);
	}
}
