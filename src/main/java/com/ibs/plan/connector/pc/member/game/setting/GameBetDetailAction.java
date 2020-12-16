package com.ibs.plan.connector.pc.member.game.setting;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改游戏详情信息
 * @Author: null
 * @Date: 2020-05-29 15:00
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/gameBetDetail", method = HttpConfig.Method.POST)
public class GameBetDetailAction extends CommCoreAction {
	private String handicapMemberId;
	private String gameCode;
	private String betSecond;
	private String splitTwoSideAmount;
	private String splitNumberAmount;
	private String increaseState;
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if(super.denyTime()){
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		if (!valiParameters()) {
			return bean.put401Data();
		}
		try {
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
			IbspHmGameSetService gameSetService=new IbspHmGameSetService();
			IbspHmGameSet gameSet=gameSetService.findEntity(handicapMemberId,gameId);
			if (gameSet == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String,Object> existInfo =new IbspClientHmService().findExistHmInfo(handicapMemberId);
			if (ContainerTool.isEmpty(existInfo)){
				bean.putSysEnum(CodeEnum.IBS_403_DATA_ERROR);
				bean.putEnum(CodeEnum.CODE_403);
				return bean;
			}
			String clientCode = existInfo.remove("CLIENT_CODE_").toString();

			gameSet.setIncreaseState(increaseState);
			if(IbsStateEnum.AUTO.name().equals(increaseState)){
				String increaseStopTime = dataMap.getOrDefault("INCREASE_STOP_TIME_LONG_", "").toString();
				gameSet.setIncreaseStopTimeLong(Long.parseLong(increaseStopTime));
			}
			gameSet.setBetSecond(Integer.parseInt(betSecond));
			gameSet.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
			gameSet.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSetService.update(gameSet);

			Map<String,Object> handicapGameInfo=new IbspHandicapGameService().findInfo(gameSet.getHandicapId(),gameId);

			JSONObject content = new JSONObject();
			content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
			content.put("BET_STATE_", gameSet.getBetState());
			content.put("GAME_CODE_", gameCode);
			content.put("GAME_DRAW_TYPE_", handicapGameInfo.get("GAME_DRAW_TYPE_"));
			content.put("METHOD_", IbsMethodEnum.SET_GAME.name());
			content.put("BET_MODE_", gameSet.getBetMode());
			content.put("BET_SECOND_", betSecond);
			content.put("INCREASE_STATE_",increaseState);
			content.put("SPLIT_TWO_SIDE_AMOUNT_", splitTwoSideAmount);
			content.put("SPLIT_NUMBER_AMOUNT_", splitNumberAmount);
			String eventId= EventThreadDefine.saveMemberConfigSetEvent(content,new Date());
			content.put("EVENT_ID_",eventId);

			RabbitMqTool.sendMember(content.toString(),clientCode,"set");

			//添加盘口会员日志信息
			saveHmLog();
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改游戏详情信息失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
	private void saveHmLog() throws Exception {
		IbspLogHm logHm = new IbspLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		logHm.setHandleContent(",BET_SECOND_:".concat(betSecond).concat(",SPLIT_TWO_SIDE_AMOUNT_:")
				.concat(splitTwoSideAmount).concat(",SPLIT_NUMBER_AMOUNT_:").concat(splitNumberAmount));
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbsStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbspLogHmService().save(logHm);
	}


	private boolean valiParameters() {
		handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		betSecond = dataMap.getOrDefault("BET_SECOND_", "").toString();
		splitTwoSideAmount = dataMap.getOrDefault("SPLIT_TWO_SIDE_AMOUNT_", "").toString();
		splitNumberAmount = dataMap.getOrDefault("SPLIT_NUMBER_AMOUNT_", "").toString();

		increaseState = dataMap.getOrDefault("INCREASE_STATE_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, gameCode, betSecond, splitTwoSideAmount,
				splitNumberAmount)) {
			return false;
		}

		return IbsStateEnum.increaseType(increaseState);
	}
}
