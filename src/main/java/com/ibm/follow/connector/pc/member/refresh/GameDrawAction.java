package com.ibm.follow.connector.pc.member.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.service.CacheService;
import com.ibm.common.core.CommQueryAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏开奖信息
 * @Author: Dongming
 * @Date: 2019-09-05 14:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/gameDraw", method = HttpConfig.Method.GET)
public class GameDrawAction extends CommQueryAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		Object check = dataMap.getOrDefault("check", "1");
		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		try {
			int checkNum = NumberTool.getInteger(check);
			if (checkNum > 20) {
				bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_GAME);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//封盘时间
			Integer closeTime = new IbmHandicapGameService().findCloseTime(handicapId, gameId);
			if (closeTime == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			HandicapUtil.Code handicap = HandicapUtil.code(handicapId);
			GameUtil.Code game = GameUtil.Code.valueOf(gameCode);
			//开奖时间
			IbmGameService gameService = new IbmGameService();
			int drawTime = gameService.findDrawTime(gameId);

			Object period = game.getGameFactory().period(handicap).findPeriod();

			long drawTimeLong = game.getGameFactory().period(handicap).getDrawTime();

			String type = HandicapGameUtil.type(game, handicap);
			//开奖结果
			String tableName = game.getGameFactory().period(handicap).getDrawTableName();
			List<Map<String, Object>> drawInfos = new CacheService().listGameDraw(tableName, type, checkNum);


			Map<String, Object> periodInfo = new HashMap<>(3);
			periodInfo.put("period", period);
			periodInfo.put("closeTime", drawTimeLong - closeTime * 1000);
			periodInfo.put("drawTime", drawTimeLong + drawTime * 1000);

			Map<String, Object> data = new HashMap<>(2);
			data.put("drawInfos", drawInfos);
			data.put("periodInfo", periodInfo);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("游戏开奖信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
