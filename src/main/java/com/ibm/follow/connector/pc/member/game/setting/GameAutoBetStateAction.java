package com.ibm.follow.connector.pc.member.game.setting;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.entity.IbmHmGameSet;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_hm.entity.IbmLogHm;
import com.ibm.follow.servlet.cloud.ibm_log_hm.service.IbmLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
/**
 * @Description: 修改自动投注状态和时间
 * @Author: zjj
 * @Date: 2019-09-09 16:39
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/gameAutoBetState", method = HttpConfig.Method.POST) public class GameAutoBetStateAction
		extends CommCoreAction {

	private String handicapMemberId;
	private String gameCode;

	private String betAutoStop;
	private String betAutoStopTime;

	private String betAutoStart;
	private String betAutoStartTime;

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
		try {
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			IbmHmGameSetService hmGameSetService = new IbmHmGameSetService();
			IbmHmGameSet hmGameSet = hmGameSetService.findByHmIdAndGameCode(handicapMemberId, gameId);
			if (hmGameSet == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			if(StringTool.notEmpty(betAutoStop,betAutoStopTime)){
				hmGameSet.setBetAutoStop(betAutoStop);
				hmGameSet.setBetAutoStopTimeLong(Long.parseLong(betAutoStopTime)+5000L);
			}else{
				hmGameSet.setBetAutoStart(betAutoStart);
				hmGameSet.setBetAutoStartTimeLong(Long.parseLong(betAutoStartTime)+5000L);
			}
			hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
			hmGameSetService.update(hmGameSet);

			//添加盘口会员日志信息
			saveHmLog(hmGameSet);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改自动投注状态和时间失败")+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
	private void saveHmLog(IbmHmGameSet hmGameSet) throws Exception {
		IbmLogHm logHm = new IbmLogHm();
		logHm.setHandicapMemberId(hmGameSet.getHandicapMemberId());
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		if (StringTool.notEmpty(betAutoStop, betAutoStopTime)) {
			logHm.setHandleContent(
					"BET_AUTO_STOP_:".concat(betAutoStop).concat(",BET_AUTO_STOP_TIME_LONG_:").concat(betAutoStopTime));
		}else{
			logHm.setHandleContent("BET_AUTO_START_:".concat(betAutoStart).concat(",BET_AUTO_START_TIME_LONG_:")
					.concat(betAutoStartTime));
		}
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbmStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbmLogHmService().save(logHm);
	}
	private boolean valiParameters() {
		handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		betAutoStop = dataMap.getOrDefault("BET_AUTO_STOP_", "").toString();
		betAutoStopTime = dataMap.getOrDefault("BET_AUTO_STOP_TIME_LONG_", "").toString();

		betAutoStart = dataMap.getOrDefault("BET_AUTO_START_", "").toString();
		betAutoStartTime = dataMap.getOrDefault("BET_AUTO_START_TIME_LONG_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			return true;
		}
		if (StringTool.isEmpty(betAutoStop, betAutoStopTime) && StringTool.isEmpty(betAutoStart, betAutoStartTime)) {
			return true;
		}

		return IbmTypeEnum.booleanType(betAutoStop) && IbmTypeEnum.booleanType(betAutoStart);
	}
}
