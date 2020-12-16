package com.ibs.plan.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.tools.GameTool;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口会员投注信息
 * @Author: null
 * @Date: 2020-05-26 18:27
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/gameBetInfo", method = HttpConfig.Method.POST)
public class GameBetInfoAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		long nextTime = System.currentTimeMillis();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		String checkTimeStr = dataMap.getOrDefault("checkTime", "").toString();

		if(StringTool.isEmpty(handicapMemberId,gameCode)){
			return bean.put401Data();
		}
		String gameId =GameUtil.id(gameCode);
		if (StringTool.isEmpty(gameId)) {
			return bean.put401Data();
		}

		String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
		if (StringTool.isEmpty(handicapId)) {
			return bean.put404HandicapMember();
		}
		GameUtil.Code game = GameUtil.Code.valueOf(gameCode);
		HandicapUtil.Code handicap = HandicapUtil.code(handicapId);

		//检查时间
		long checkTime;
		int number = 0;
		if (StringTool.isEmpty(checkTimeStr)) {
			checkTime =game.getGameFactory().period(handicap).getLotteryDrawTime();
			number = 20;
		} else {
			checkTime = NumberTool.getLong(checkTimeStr) - 200;
		}
		try {
			Map<String, Object> data = new HashMap<>(7);
			IbspHmBetItemService hmBetItemService=new IbspHmBetItemService();

			Object period=game.getGameFactory().period(handicap).findPeriod();
			if(StringTool.isContains(period.toString(),"-")){
				period=period.toString().substring(4);
			}
			//获取当期投注总额和当期投注总数
			hmBetItemService.findSum(gameId, handicapMemberId, period, data);

			//查询新数据
			List<Map<String, Object>> betNewInfos = hmBetItemService.listNewBetInfo(gameId, handicapMemberId, checkTime, number);
			String betState;
			for (Map<String, Object> betInfo : betNewInfos) {
				//投注信息为空的时候
				if(StringTool.isEmpty(betInfo.get("BET_CONTENT_"))){
					betInfo.put("BET_CONTENT_", "投注完成");
				}else {
					betInfo.put("BET_SHOW_", GameTool.getBetShow(game, betInfo.get("BET_CONTENT_").toString().split("#")));
					betInfo.put("BET_CONTENT_", GameTool.getBetContent(game, betInfo.get("BET_CONTENT_").toString().split("#")));
				}
				if (NumberTool.getInteger(betInfo.remove("BET_TYPE_")) == IbsTypeEnum.MERGE.ordinal()) {
					betInfo.put("PLAN_GROUP_DESC_", "合并投注");
				}
				betState = betInfo.get("EXEC_STATE_").toString();
				if (IbsStateEnum.FAIL.name().equals(betState)) {
					betInfo.put("EXEC_STATE_", "投注失败");
					betInfo.put("EXEC_CONTENT_", betInfo.remove("DESC_"));
				}else{
					betInfo.put("EXEC_STATE_", betListFormat(betState, betInfo.remove("BET_MODE_").toString()));
				}
				betInfo.put("BET_FUND_", NumberTool.doubleT(betInfo.remove("FUND_T_")));
				betInfo.put("FUNDS_INDEX_", String.valueOf(NumberTool.getInteger(betInfo.get("FUNDS_INDEX_"))+1));
				betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.remove("PROFIT_T_")));
				betInfo.remove("ROW_NUM");
			}
			data.put("betNewInfos", betNewInfos);
			data.put("nextTime", nextTime);
			if (number != 0) {
				data.put("betInfo", new HashMap<>(1));
				bean.success(data);
				return bean;
			}
			//历史更新数据
			List<Map<String, Object>> betInfos = hmBetItemService.listDrawInfo(gameId, handicapMemberId, checkTime);
			for (Map<String, Object> betInfo : betInfos) {
				if (NumberTool.getInteger(betInfo.remove("BET_TYPE_")) == IbsTypeEnum.MERGE.ordinal()) {
					betInfo.put("PLAN_GROUP_DESC_", IbsTypeEnum.MERGE.getMsg());
				}
				betState = betInfo.get("EXEC_STATE_").toString();
				if (IbsStateEnum.FAIL.name().equals(betState)) {
					betInfo.put("EXEC_STATE_", "投注失败");
					betInfo.put("EXEC_CONTENT_", betInfo.remove("DESC_"));
				} else {
					betInfo.put("EXEC_STATE_", betListFormat(betState, betInfo.get("BET_MODE_").toString()));
				}
				betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.remove("PROFIT_T_")));
				betInfo.remove("ROW_NUM");
			}

			data.put("betInfo", betInfos);
			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取盘口会员投注信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private Object betListFormat(String betState, String betMode) {
		if (IbsTypeEnum.VIRTUAL.name().equals(betMode)) {
			return IbsTypeEnum.VIRTUAL.getMsg();
		}
		switch (IbsStateEnum.valueOf(betState)) {
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
			case FAIL:
			default:
				log.error("投注类型错误=" + betState);
				betState = "投注成功";
				break;
		}
		return betState;
	}
}
