package com.ibm.follow.connector.app.member.game;
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
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 盘口会员详细注单
 *
 * @Author: Dongming
 * @Date: 2020-04-17 15:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/member/gameBetDetails", method = HttpConfig.Method.GET) public class GameBetDetailAction
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
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		Object gameCodeObj = dataMap.getOrDefault("GAME_CODE_", "");
		Object period = dataMap.getOrDefault("period", "");

		GameUtil.Code gameCode = GameUtil.value(gameCodeObj);
		if (StringTool.isEmpty(handicapMemberId, period, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			//查询ID
			String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			Map<String, Object> data = new HashMap<>(7);
			IbmHmBetItemService hmBetItemService = new IbmHmBetItemService();

			if(StringTool.isContains(period.toString(),"-")){
				period=period.toString().substring(4);
			}
			//获取当期投注总额和当期投注总数
			hmBetItemService.findSum(gameId, handicapMemberId, period, data);

			List<Map<String, Object>> betInfos = hmBetItemService.listBetInfo(gameId, handicapMemberId, period);
			String betState;
			for (Map<String, Object> betInfo : betInfos) {
				if ("manual".equals(betInfo.get("FOLLOW_MEMBER_ACCOUNT_"))) {
					betInfo.put("FOLLOW_MEMBER_ACCOUNT_", IbmTypeEnum.MANUAL.getMsg());
				}
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
				betState = betInfo.get("EXEC_STATE_").toString();
				if (NumberTool.getInteger(betInfo.remove("BET_TYPE_")) == IbmTypeEnum.FOLLOW.ordinal()) {
					betInfo.put("FOLLOW_MEMBER_ACCOUNT_", "合并投注");
				}
				if (IbmStateEnum.FAIL.name().equals(betState)) {
					betInfo.put("EXEC_STATE_", "投注失败");
					betInfo.put("EXEC_CONTENT_", betInfo.get("DESC_"));
				} else {
					betInfo.put("EXEC_STATE_", betListFormat(betState, betInfo.get("BET_MODE_").toString()));
				}
				betInfo.put("BET_FUND_", NumberTool.doubleT(betInfo.remove("FUND_T_")));
				betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.remove("PROFIT_T_")));

				betInfo.remove("ROW_NUM");
				betInfo.remove("DESC_");
			}
			String type = HandicapGameUtil.type(gameCode, HandicapUtil.code(handicapId));
			assert gameCode != null;
			String tableName=gameCode.getGameFactory().period(HandicapUtil.code(handicapId)).getDrawTableName();
			//开奖结果
			Map<String, Object> drawInfo = new CacheService().findGameDraw(tableName, type, period);
			data.put("betInfos", betInfos);
			data.put("drawInfo", drawInfo);

			bean.success(data);
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
	 * @param betMode  投注模式
	 * @return 完成投注数
	 */
	private static String betListFormat(String betState, String betMode) {
		if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
			return "模拟投注";
		}
		switch (IbmStateEnum.valueOf(betState)) {
			case FINISH:
				betState = "投注完成";
				break;
			case SUCCESS:
				betState = "投注成功";
				break;
			case PROCESS:
			case SEND:
				betState = "等待投注";
				break;
			default:
				log.error("投注类型错误=" + betState);
				betState = "投注成功";
				break;
		}
		return betState;
	}
}
