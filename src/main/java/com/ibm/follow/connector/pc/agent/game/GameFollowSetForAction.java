package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @deprecated
 * @Description: 游戏跟投设置
 * @Author:
 * @Date: 2019-09-12 14:03
 * @Version: v1.0
 */
//@ActionMapping(value = "/ibm/pc/agent/gameFollowSet", method = HttpConfig.Method.POST)
public class GameFollowSetForAction
		extends CommCoreAction {
	private String handicapAgentId;
	private String betState;
	private String betFollowMultiple;
	private String betLeastAmount;
	private String betMostAmount;
	private String betFilterNumber;
	private String betFilterTwoSide;
	private String twoSideOpposing;
	private String numberOpposing;
	private String extensionSet;
	private String gameCode;

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		if (valiParameters()) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
		Object followSet = dataMap.getOrDefault("FOLLOW_SET_", "");

		if (StringTool.isEmpty(handicapAgentId) || ContainerTool.isEmpty(followSet)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbmHaInfoService haInfoService = new IbmHaInfoService();
			String loginState = haInfoService.findLoginState(handicapAgentId);

			if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
			String gameId = GameUtil.id(gameCode);
			IbmHaGameSet haGameSet = haGameSetService.findByHaIdAndGameId(handicapAgentId, gameId);
			if (haGameSet == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			haGameSet.setBetState(betState);
			haGameSet.setBetFollowMultipleT(NumberTool.intValueT(betFollowMultiple));
			haGameSet.setBetLeastAmountT(NumberTool.intValueT(betLeastAmount));
			haGameSet.setBetMostAmountT(NumberTool.intValueT(betMostAmount));
			haGameSet.setBetFilterNumber(betFilterNumber);
			haGameSet.setBetFilterTwoSide(betFilterTwoSide);
			haGameSet.setNumberOpposing(numberOpposing);
			haGameSet.setTwoSideOpposing(twoSideOpposing);
			haGameSet.setExtensionSet(extensionSet);
			haGameSet.setUpdateTimeLong(System.currentTimeMillis());
			haGameSetService.update(haGameSet);

			//添加盘口代理日志信息
			saveHaLog(haGameSet);

			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("GAME_CODE_", gameCode);
			content.put("BET_STATE_", betState);
			content.put("BET_FOLLOW_MULTIPLE_T_", NumberTool.intValueT(betFollowMultiple));
			content.put("BET_LEAST_AMOUNT_T_", NumberTool.intValueT(betLeastAmount));
			content.put("BET_MOST_AMOUNT_T_", NumberTool.intValueT(betMostAmount));
			content.put("BET_FILTER_NUMBER_", betFilterNumber);
			content.put("BET_FILTER_TWO_SIDE_", betFilterTwoSide);
			content.put("NUMBER_OPPOSING_", numberOpposing);
			content.put("TWO_SIDE_OPPOSING_", twoSideOpposing);
			content.put("EXTENSION_SET_", extensionSet);

			String desc= this.getClass().getName().concat("，修改跟投设置");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId, IbmTypeEnum.AGENT,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"), e);
			throw e;
		}
		bean.success();
		return bean;
	}

	private void saveHaLog(IbmHaGameSet haGameSet) throws Exception {
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(haGameSet.getHandicapAgentId());
		logHa.setAppUserId(appUserId);
		logHa.setHandleType("UPDATE");
		logHa.setHandleContent("BET_STATE_:".concat(haGameSet.getBetState()).concat(",BET_FOLLOW_MULTIPLE_:")
				.concat(haGameSet.getBetFollowMultipleT().toString()).concat(",BET_LEAST_AMOUNT_:")
				.concat(haGameSet.getBetLeastAmountT().toString()).concat(",BET_MOST_AMOUNT_:")
				.concat(haGameSet.getBetMostAmountT().toString()).concat(",BET_FILTER_NUMBER_:")
				.concat(haGameSet.getBetFilterNumber()).concat(",BET_FILTER_TWO_SIDE_:")
				.concat(haGameSet.getBetFilterTwoSide()).concat(",NUMBER_OPPOSING_:")
				.concat(haGameSet.getNumberOpposing()).concat(",TWO_SIDE_OPPOSING_:")
				.concat(haGameSet.getTwoSideOpposing()));
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHa.setDesc(this.getClass().getName());
		new IbmLogHaService().save(logHa);
	}

	private boolean valiParameters() {

		handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
		betState = dataMap.getOrDefault("BET_STATE_", "").toString();
		betFollowMultiple = dataMap.getOrDefault("BET_FOLLOW_MULTIPLE_", "").toString();
		betLeastAmount = dataMap.getOrDefault("BET_LEAST_AMOUNT_", "").toString();
		betMostAmount = dataMap.getOrDefault("BET_MOST_AMOUNT_", "").toString();
		betFilterNumber = dataMap.getOrDefault("BET_FILTER_NUMBER_", "").toString();
		betFilterTwoSide = dataMap.getOrDefault("BET_FILTER_TWO_SIDE_", "").toString();
		twoSideOpposing = dataMap.getOrDefault("TWO_SIDE_OPPOSING_", "").toString();
		numberOpposing = dataMap.getOrDefault("NUMBER_OPPOSING_", "").toString();
		extensionSet = dataMap.getOrDefault("EXTENSION_SET_", "").toString();
		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		return StringTool
				.isEmpty(handicapAgentId, betState, betFollowMultiple, betLeastAmount, betMostAmount);
	}
}
