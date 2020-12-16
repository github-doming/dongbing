package com.ibm.follow.connector.app.agent.game;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.CacheService;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 盘口代理详细注单
 *
 * @Author: Dongming
 * @Date: 2020-04-17 15:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/gameBetDetails", method = HttpConfig.Method.GET) public class GameBetDetailAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (super.denyTime()) {
			bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
			bean.putSysEnum(IbmCodeEnum.CODE_503);
			return bean;
		}
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
		Object gameCodeObj = dataMap.getOrDefault("GAME_CODE_", "");
		Object period = dataMap.getOrDefault("period", "");

		GameUtil.Code gameCode = GameUtil.value(gameCodeObj);
		if (StringTool.isEmpty(handicapAgentId, period, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String handicapId = new IbmHandicapAgentService().findHandicapId(handicapAgentId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);

			IbmHaMemberBetItemService haGameSetService = new IbmHaMemberBetItemService();
			//查询新数据
			List<Map<String, Object>> betInfos = haGameSetService.listBetInfo(gameId, handicapAgentId, period);

			for (Map<String, Object> betInfo : betInfos) {
				//投注信息为空的时候
				if (StringTool.isEmpty(betInfo.get("BET_CONTENT_"))) {
					betInfo.put("BET_CONTENT_", null);
				} else {
					JSONArray betContent = new JSONArray();
					String[] contents = betInfo.remove("BET_CONTENT_").toString().split("#");
					for (String content : contents) {
						JSONObject betItem = new JSONObject();
						String[] items = content.split("\\|");
						betItem.put("rank", items[0]);
						betItem.put("type", items[1]);
						betItem.put("funds", NumberTool.doubleT(items[2]));
						betContent.add(betItem);
					}
					betInfo.put("BET_CONTENT_", betContent);
				}
				//投注信息为空的时候
				if (StringTool.isEmpty(betInfo.get("FOLLOW_CONTENT_"))) {
					betInfo.put("FOLLOW_CONTENT_", null);
				} else {
					JSONArray betContent = new JSONArray();
					String[] contents = betInfo.remove("FOLLOW_CONTENT_").toString().split("#");
					for (String content : contents) {
						JSONObject betItem = new JSONObject();
						String[] items = content.split("\\|");
						betItem.put("rank", items[0]);
						betItem.put("type", items[1]);
						betItem.put("funds", NumberTool.doubleT(items[2]));
						betContent.add(betItem);
					}
					betInfo.put("FOLLOW_CONTENT_", betContent);
				}
				betInfo.put("EXEC_STATE_", betListFormat(String.valueOf(betInfo.get("EXEC_STATE_"))));
				betInfo.put("BET_FUND_", NumberTool.doubleT(betInfo.remove("BET_FUND_T_")));
				betInfo.put("FOLLOW_FUND_", NumberTool.doubleT(betInfo.remove("FOLLOW_FUND_T_")));
				betInfo.remove("ROW_NUM");
			}
			String type = HandicapGameUtil.type(gameCode, HandicapUtil.code(handicapId));

			//开奖结果

			assert gameCode != null;
			String tableName=gameCode.getGameFactory().period(HandicapUtil.code(handicapId)).getDrawTableName();
			Map<String, Object> drawInfo = new CacheService().findGameDraw(tableName, type, period);
			Map<String, Object> data = new HashMap<>(2);
			data.put("betInfos", betInfos);
			data.put("drawInfo", drawInfo);

			bean.success(data);

			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("盘口游戏期数失败"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 输出格式化
	 *
	 * @param betState 投注状态
	 * @return 完成投注数
	 */
	private String betListFormat(String betState) {
		switch (IbmStateEnum.valueOf(betState)) {
			case PROCESS:
				return "等待投注";
			case FAIL:
				return "投注失败";
			case SUCCESS:
				return "投注成功";
			default:
				log.error("投注类型错误=" + betState);
				return "投注成功";
		}
	}

}
