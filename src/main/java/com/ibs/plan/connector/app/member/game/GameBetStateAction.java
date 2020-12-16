package com.ibs.plan.connector.app.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_event_config_set.service.IbspEventConfigSetService;
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
 * @Description: 修改游戏投注状态
 * @Author: null
 * @Date: 2019-11-26 16:35
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/member/gameBetState", method = HttpConfig.Method.POST)
public class GameBetStateAction extends CommCoreAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (super.denyTime()) {
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String betMode = dataMap.getOrDefault("BET_MODE_", "").toString();

		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		if (!IbsTypeEnum.booleanType(betMode)) {
			bean.putEnum(CodeEnum.IBS_401_STATE);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			//用户是否登录
			if (!IbsStateEnum.LOGIN.equal(new IbspHmInfoService().findLoginState(handicapMemberId))) {
				bean.putEnum(CodeEnum.IBS_403_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			IbspHmGameSetService hmGameSetService = new IbspHmGameSetService();
			IbspHmGameSet hmGameSet = hmGameSetService.findByHmIdAndGameCode(handicapMemberId, gameId);
			if (hmGameSet == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			if(hmGameSet.getBetMode().equals(betMode)){
				hmGameSet.setBetState(IbsTypeEnum.TRUE.name().equals(hmGameSet.getBetState())?IbsTypeEnum.FALSE.name():IbsTypeEnum.TRUE.name());
			}else{
				if(IbsTypeEnum.FALSE.name().equals(hmGameSet.getBetState())){
					hmGameSet.setBetState(IbsTypeEnum.TRUE.name());
				}
			}
			hmGameSet.setBetMode(betMode);
			hmGameSet.setUpdateTimeLong(System.currentTimeMillis());
			hmGameSetService.update(hmGameSet);

			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("HANDICAP_MEMBER_ID_", handicapMemberId);
			content.put("BET_STATE_", hmGameSet.getBetState());
			content.put("BET_MODE_", betMode);
			content.put("GAME_CODE_", gameCode);
			content.put("SET_ITEM_", IbsMethodEnum.SET_BET_STATE.name());
			content.put("METHOD_", IbsMethodEnum.MEMBER_SET.name());
			EventThreadDefine.saveMemberConfigSetEvent(content, new Date(), this.getClass().getName().concat("，修改投注状态"), new IbspEventConfigSetService());

			//添加盘口会员日志信息
			saveHmLog(handicapMemberId, betMode);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("修改游戏投注状态失败"));
			throw e;
		}
		return bean;
	}

	private void saveHmLog(String handicapMemberId, String betState) throws Exception {
		IbspLogHm logHm = new IbspLogHm();
		logHm.setHandicapMemberId(handicapMemberId);
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		logHm.setHandleContent("BET_MODE_".concat(betState));
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbsStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbspLogHmService().save(logHm);
	}
}
