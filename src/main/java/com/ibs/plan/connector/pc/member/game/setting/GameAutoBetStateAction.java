package com.ibs.plan.connector.pc.member.game.setting;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 修改自动投注状态和时间
 * @Author: null
 * @Date: 2020-05-29 14:35
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/gameAutoBetState", method = HttpConfig.Method.POST)
public class GameAutoBetStateAction extends CommCoreAction {
	private String handicapMemberId;
	private String gameCode;

	private String betAutoStop;
	private String betAutoStopTime;

	private String betAutoStart;
	private String betAutoStartTime;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			return bean.put401Data();
		}
		if(super.denyTime()){
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		try {
			String gameId = GameUtil.id(gameCode);
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
			if(StringTool.notEmpty(betAutoStop,betAutoStopTime)){
				gameSet.setIsAutoStopBet(betAutoStop);
				gameSet.setAutoStopBetTimeLong(Long.parseLong(betAutoStopTime)+5000L);
			}else{
				gameSet.setIsAutoStartBet(betAutoStart);
				gameSet.setAutoStartBetTimeLong(Long.parseLong(betAutoStartTime)+5000L);
			}
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSetService.update(gameSet);

			//添加盘口会员日志信息
			saveHmLog();
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改自动投注状态和时间失败", e);
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
		if (StringTool.notEmpty(betAutoStop, betAutoStopTime)) {
			logHm.setHandleContent(
					"IS_AUTO_STOP_BET_:".concat(betAutoStop).concat(",AUTO_STOP_BET_TIME_LONG_:").concat(betAutoStopTime));
		}else{
			logHm.setHandleContent("IS_AUTO_START_BET_:".concat(betAutoStart).concat(",AUTO_START_BET_TIME_LONG_:")
					.concat(betAutoStartTime));
		}
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

		betAutoStop = dataMap.getOrDefault("IS_AUTO_STOP_BET_", "").toString();
		betAutoStopTime = dataMap.getOrDefault("AUTO_STOP_BET_TIME_LONG_", "").toString();

		betAutoStart = dataMap.getOrDefault("IS_AUTO_START_BET_", "").toString();
		betAutoStartTime = dataMap.getOrDefault("AUTO_START_BET_TIME_LONG_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			return true;
		}
		if (StringTool.isEmpty(betAutoStop, betAutoStopTime) && StringTool.isEmpty(betAutoStart, betAutoStartTime)) {
			return true;
		}

		return IbsTypeEnum.booleanType(betAutoStop) && IbsTypeEnum.booleanType(betAutoStart);
	}
}
