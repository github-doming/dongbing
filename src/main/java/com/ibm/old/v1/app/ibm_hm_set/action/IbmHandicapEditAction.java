package com.ibm.old.v1.app.ibm_hm_set.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_hm_set.t.entity.IbmHmSetT;
import com.ibm.old.v1.cloud.ibm_hm_set.t.service.IbmHmSetTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.tools.IbmCmdTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 盘口设置修改
 * @Author: Dongming
 * @Author: cwy
 * @Date: 2019-08-05 13:04
 * @Email: 1060953761@qq.com
 * @Version: v1.0
 */
@ActionMapping(name = "HandicapShow", value = "/ibm/app/ibm_hm_set/edit.dm") public class IbmHandicapEditAction
		extends BaseAsynCommAction {

	/**
	 * 执行方法
	 *
	 * @return 执行结果
	 * @throws Exception 执行失败
	 */
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
			boolean updateType;
			switch (IbmCmdTool.Set.valueOf(cmd)) {
				case HANDICAP_SET:
					updateType = setHandicapSet(jrb, handicapMemberId);
					break;
				case GAME_SET:
					updateType = setHandicapGameSet(jrb, gameCode, handicapMemberId);
					break;
				case MERGE_SET:
					updateType = setMergeSet(jrb, handicapMemberId);
					break;
				case BET_MODE_SET:
					updateType = betModeSet(jrb, gameCode, handicapMemberId);
					break;
				default:
					jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
					return jrb;
			}
			if (updateType) {
				jrb.success();
			} else {
				jrb.putEnum(IbmCodeEnum.IBM_403_UPDATE);
				jrb.putSysEnum(IbmCodeEnum.CODE_403);
			}

		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "盘口设置修改错误", e);
			throw e;
		}
		return jrb;
	}
	/**
	 * 投注模式
	 *
	 * @param jrb              json返回bean类扩展类
	 * @param gameCode         游戏code
	 * @param handicapMemberId 会员id
	 */
	private boolean betModeSet(JsonResultBeanPlus jrb, String gameCode, String handicapMemberId) throws Exception {
		String betMode = dataMap.get("BET_MODE_").toString();
		if (StringTool.isEmpty(betMode, gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		String gameId = GameTool.findId(gameCode);
		//判断游戏id是否存在
		if (StringTool.isEmpty(gameId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}

		return new IbmHmGameSetTService().updateBetModel(betMode, gameId, handicapMemberId);

	}
	/**
	 * 合并状态
	 *
	 * @param jrb              json返回bean类扩展类
	 * @param handicapMemberId 会员id
	 */
	private boolean setMergeSet(JsonResultBeanPlus jrb, String handicapMemberId) throws Exception {
		String betMerger = dataMap.get("BET_MERGER_").toString();
		if (StringTool.isEmpty(betMerger)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		return new IbmHmSetTService().updateBetMerger(betMerger, handicapMemberId);

	}

	/**
	 * 修改盘口设置
	 *
	 * @param jrb              json返回bean类扩展类
	 * @param handicapMemberId 会员id
	 */
	private boolean setHandicapSet(JsonResultBeanPlus jrb, String handicapMemberId) throws Exception {
		IbmHmSetTService hmSetService = new IbmHmSetTService();
		if (!ContainerTool
				.containsKey(dataMap, "BET_RATE_", "LOSS_LIMIT_MIN_", "PROFIT_LIMIT_MAX_", "PROFIT_LIMIT_MIN_",
						"RESET_TYPE_", "RESET_PROFIT_MAX_", "RESET_LOSS_MIN_", "BLAST_STOP_")) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		String betRate = dataMap.get("BET_RATE_").toString();
		String lossLimitMin = dataMap.get("LOSS_LIMIT_MIN_").toString();
		String profitLimitMax = dataMap.get("PROFIT_LIMIT_MAX_").toString();
		String profitLimitMin = dataMap.get("PROFIT_LIMIT_MIN_").toString();
		String resetType = dataMap.get("RESET_TYPE_").toString();
		String resetProfitMax = dataMap.get("RESET_PROFIT_MAX_").toString();
		String resetLossMin = dataMap.get("RESET_LOSS_MIN_").toString();
		String blastStop = dataMap.get("BLAST_STOP_").toString();

		IbmHmSetT hmSet = hmSetService.findHmSet(handicapMemberId, appUserT.getAppUserId());
		// 判断盘口设置是否存在
		if (StringTool.isEmpty(hmSet)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return false;
		}
		Date nowTime = new Date();
		hmSet.setBetRateT(NumberTool.notEmptyLongValue(betRate));
		hmSet.setLossLimitMinT(NumberTool.notEmptyLongValue(lossLimitMin));
		hmSet.setProfitLimitMaxT(NumberTool.notEmptyLongValue(profitLimitMax));
		hmSet.setProfitLimitMinT(NumberTool.notEmptyLongValue(profitLimitMin));
		hmSet.setResetType(resetType);
		hmSet.setResetProfitMaxT(NumberTool.notEmptyLongValue(resetProfitMax));
		hmSet.setResetLossMinT(NumberTool.notEmptyLongValue(resetLossMin));
		hmSet.setBlastStop(blastStop);
		hmSet.setUpdateTime(nowTime);
		hmSet.setUpdateTimeLong(nowTime.getTime());
		hmSet.setDesc(this.getClass().getName() + "-" + IbmCmdTool.Set.HANDICAP_SET.getName());

		return hmSetService.update(hmSet);

	}

	/**
	 * 修改盘口游戏设置
	 *
	 * @param jrb              json返回bean类扩展类
	 * @param gameCode         游戏code
	 * @param handicapMemberId 会员id
	 */
	private boolean setHandicapGameSet(JsonResultBeanPlus jrb, String gameCode, String handicapMemberId)
			throws Exception {

		if (StringTool.isEmpty(gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}

		if (!ContainerTool.containsKey(dataMap, "BET_AUTO_STOP_", "BET_AUTO_STOP_TIME_", "BET_AUTO_START_",
				"BET_AUTO_START_TIME_", "INCREASE_STATE_", "INCREASE_STOP_TIME_", "BET_SECOND_",
				"SPLIT_TWO_SIDE_AMOUNT_", "SPLIT_NUMBER_AMOUNT_")) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		String betAutoStop = dataMap.get("BET_AUTO_STOP_").toString();
		String betAutoStopTime = dataMap.get("BET_AUTO_STOP_TIME_").toString();
		String betAutoStart = dataMap.get("BET_AUTO_START_").toString();
		String betAutoStartTime = dataMap.get("BET_AUTO_START_TIME_").toString();
		String increaseState = dataMap.get("INCREASE_STATE_").toString();
		String increaseStopTime = dataMap.get("INCREASE_STOP_TIME_").toString();
		String betSecond = dataMap.get("BET_SECOND_").toString();
		String splitTwoSideAmount = dataMap.get("SPLIT_TWO_SIDE_AMOUNT_").toString();
		String splitNumberAmount = dataMap.get("SPLIT_NUMBER_AMOUNT_").toString();

		String gameId = GameTool.findId(gameCode);
		//判断游戏id是否存在
		if (StringTool.isEmpty(gameId)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		IbmHmGameSetT hmGameSet = new IbmHmGameSetTService().finHmGameSetById(handicapMemberId, gameId);
		//判断游戏设置是否存在
		if (StringTool.isEmpty(hmGameSet)) {
			jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return false;
		}
		//更新游戏设置
		hmGameSet.setBetAutoStop(betAutoStop);
		hmGameSet.setBetAutoStopTime(new Date(NumberTool.getLong(betAutoStopTime)));
		hmGameSet.setBetAutoStart(betAutoStart);
		hmGameSet.setBetAutoStartTime(new Date(NumberTool.getLong(betAutoStartTime)));
		hmGameSet.setIncreaseState(increaseState);
		hmGameSet.setIncreaseStopTime(new Date(NumberTool.getLong(increaseStopTime)));
		hmGameSet.setBetSecond(Integer.parseInt(betSecond));
		hmGameSet.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
		hmGameSet.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
		hmGameSet.setDesc(this.getClass().getName() + "-" + IbmCmdTool.Set.GAME_SET.getName());
		IbmHmGameSetTService hmGameSetService = new IbmHmGameSetTService();
		return hmGameSetService.update(hmGameSet);
	}
}
