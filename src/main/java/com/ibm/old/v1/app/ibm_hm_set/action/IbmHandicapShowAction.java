package com.ibm.old.v1.app.ibm_hm_set.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.tools.IbmCmdTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 盘口信息展示
 * @Author: Dongming
 * @Date: 2019-08-02 17:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "HandicapShow", value = "/ibm/app/ibm_hm_set/show.dm") public class IbmHandicapShowAction
		extends BaseAsynCommAction {

	@Override public Object run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		String handicapMemberId = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		String gameCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("GAME_CODE_"), "");
		if (StringTool.isEmpty(handicapMemberId, cmd)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {
			switch (IbmCmdTool.Set.valueOf(cmd)) {
				case HANDICAP_SET:
					getHandicapSet(jrb, handicapMemberId);
					break;
				case GAME_SET:
					getHandicapGameSet(jrb, handicapMemberId, gameCode);
					break;
				default:
					jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
			}
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "盘口信息展示错误", e);
			throw e;
		}

		return jrb;
	}

	/**
	 * 获取 盘口设置
	 *
	 * @param jrb              返回集成类
	 * @param handicapMemberId 盘口会员ID
	 */
	private void getHandicapSet(JsonResultBeanPlus jrb, String handicapMemberId) throws Exception {

		Map<String, Object> hmSetInfo = new IbmHmSetTService().findInfoById(handicapMemberId, appUserT.getAppUserId());
		//判断用户盘口设置是否存在
		if (ContainerTool.isEmpty(hmSetInfo)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			jrb.setSuccess(false);
			return;
		}
		//放入 盘口设置 信息
		hmSetInfo.put("BET_RATE_", NumberTool.doubleT(hmSetInfo.remove("BET_RATE_T_")));
		hmSetInfo.put("PROFIT_LIMIT_MAX_", NumberTool.doubleT(hmSetInfo.remove("PROFIT_LIMIT_MAX_T_")));
		hmSetInfo.put("LOSS_LIMIT_MIN_", NumberTool.doubleT(hmSetInfo.remove("LOSS_LIMIT_MIN_T_")));
		hmSetInfo.put("PROFIT_LIMIT_MIN_", NumberTool.doubleT(hmSetInfo.remove("PROFIT_LIMIT_MIN_T_")));
		hmSetInfo.put("RESET_PROFIT_MAX_", NumberTool.doubleT(hmSetInfo.remove("RESET_PROFIT_MAX_T_")));
		hmSetInfo.put("RESET_LOSS_MIN_", NumberTool.doubleT(hmSetInfo.remove("RESET_LOSS_MIN_T_")));
		jrb.setData(hmSetInfo);
		jrb.success();
	}

	/**
	 * 获取游戏设置
	 *
	 * @param jrb              返回集成类
	 * @param handicapMemberId 盘口会员ID
	 * @param gameCode         游戏code
	 */
	private void getHandicapGameSet(JsonResultBeanPlus jrb, String handicapMemberId, String gameCode) throws Exception {
		if (StringTool.isEmpty(gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return;
		}
		String gameId = GameTool.findId(gameCode);
		//判断游戏id是否存在
		if (StringTool.isEmpty(gameId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return;
		}

		Map<String, Object> hmGameSetInfo = new IbmHmGameSetTService().findInfoById(handicapMemberId, gameId);
		//判断用户盘口游戏设置是否存在
		if (StringTool.isEmpty(hmGameSetInfo)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return;
		}
		//放入用户盘口游戏设置信息
		if (StringTool.notEmpty(hmGameSetInfo.get("BET_AUTO_STOP_TIME_"))) {
			hmGameSetInfo.put("BET_AUTO_STOP_TIME_", DateTool.parseLong(hmGameSetInfo.remove("BET_AUTO_STOP_TIME_")));
		} else {
			hmGameSetInfo.put("BET_AUTO_STOP_TIME_", DateTool.parseLong("00:00:00"));
		}
		if (StringTool.notEmpty(hmGameSetInfo.get("BET_AUTO_START_TIME_"))) {
			hmGameSetInfo.put("BET_AUTO_START_TIME_", DateTool.parseLong(hmGameSetInfo.remove("BET_AUTO_START_TIME_")));
		} else {
			hmGameSetInfo.put("BET_AUTO_START_TIME_", DateTool.parseLong("00:00:00"));
		}
		if (StringTool.notEmpty(hmGameSetInfo.get("INCREASE_STOP_TIME_"))) {
			hmGameSetInfo.put("INCREASE_STOP_TIME_", DateTool.parseLong(hmGameSetInfo.remove("INCREASE_STOP_TIME_")));
		} else {
			hmGameSetInfo.put("INCREASE_STOP_TIME_", DateTool.parseLong("00:00:00"));
		}
		jrb.setData(hmGameSetInfo);
		jrb.success();
	}

}
