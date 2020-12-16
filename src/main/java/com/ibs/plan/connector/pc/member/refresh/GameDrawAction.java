package com.ibs.plan.connector.pc.member.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.service.CacheService;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏开奖信息
 * @Author: null
 * @Date: 2020-05-26 15:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/gameDraw", method = HttpConfig.Method.GET)
public class GameDrawAction extends CommCoreAction {
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
			return bean.put401Data();
		}
		try {
			int checkNum = NumberTool.getInteger(check);
			if (checkNum > 20) {
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				bean.putEnum(CodeEnum.IBS_404_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}
			//封盘时间
			Map<String, Object> gameInfoMap = new IbspHandicapGameService().findInfo(handicapId, gameId);
			Integer closeTime = NumberTool.getInteger(gameInfoMap.get("CLOSE_TIME"), null);
			if (closeTime == null) {
				bean.putEnum(CodeEnum.IBS_404_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			HandicapUtil.Code handicap = HandicapUtil.code(handicapId);
			GameUtil.Code game = GameUtil.Code.valueOf(gameCode);

			//开奖时间
			IbspGameService gameService = new IbspGameService();
			int drawTime = gameService.findDrawTime(gameId);

			Object period = game.getGameFactory().period(handicap).findPeriod();
			long drawTimeLong = game.getGameFactory().period(handicap).getDrawTime();

			//开奖结果
			String tableName = game.getGameFactory().period(handicap).getDrawTableName();
			List<Map<String, Object>> drawInfos = new CacheService().listGameDraw(tableName, gameInfoMap.get("GAME_DRAW_TYPE_"), checkNum);

			Map<String, Object> periodInfo = new HashMap<>(3);
			periodInfo.put("period", period);
			periodInfo.put("closeTime", drawTimeLong - closeTime * 1000);
			periodInfo.put("drawTime", drawTimeLong + drawTime * 1000);

			Map<String, Object> data = new HashMap<>(2);
			data.put("drawInfos", drawInfos);
			data.put("periodInfo", periodInfo);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取游戏开奖信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
