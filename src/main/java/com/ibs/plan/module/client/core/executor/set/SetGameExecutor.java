package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.controller.MemberConfigController;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.core.thread.CodingItemThread;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import com.ibs.plan.module.client.ibsc_hm_game_set.entity.IbscHmGameSet;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 游戏设置执行器
 * @Author: null
 * @Date: 2020-05-29 16:17
 * @Version: v1.0
 */
public class SetGameExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String handicapCode=new IbscExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode);

		String gameCode = msgObj.getString("GAME_CODE_");
		String gameDrawType=msgObj.getString("GAME_DRAW_TYPE_");
		String betState = msgObj.getString("BET_STATE_");
		String betMode = msgObj.getString("BET_MODE_");
		String increaseState=msgObj.getString("INCREASE_STATE_");
		Integer betSecond = msgObj.getInteger("BET_SECOND_");
		Integer splitTwoSideAmount = msgObj.getInteger("SPLIT_TWO_SIDE_AMOUNT_");
		Integer splitNumberAmount = msgObj.getInteger("SPLIT_NUMBER_AMOUNT_");

		if (StringTool.isEmpty(gameCode, betState, betMode,increaseState,
				betSecond, splitTwoSideAmount, splitNumberAmount)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean ;
		}

		IbscHmGameSetService hmGameSetService=new IbscHmGameSetService();
		IbscHmGameSet gameSet=hmGameSetService.findEntity(existHmId,gameCode);
		String hmGameSetId;
		if(gameSet==null){
			gameSet=new IbscHmGameSet();
			gameSet.setExistHmId(existHmId);
			gameSet.setGameCode(gameCode);
			gameSet.setGameDrawType(gameDrawType);
			gameSet.setBetState(betState);
			gameSet.setBetMode(betMode);
			gameSet.setIncreaseState(increaseState);
			gameSet.setBetSecond(betSecond);
			gameSet.setSplitTwoSideAmount(splitTwoSideAmount);
			gameSet.setSplitNumberAmount(splitNumberAmount);
			gameSet.setCreateTime(new Date());
			gameSet.setCreateTimeLong(System.currentTimeMillis());
			gameSet.setUpdateTime(new Date());
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSet.setState(IbsStateEnum.OPEN.name());
			hmGameSetId=hmGameSetService.save(gameSet);
		}else{
			gameSet.setBetState(betState);
			gameSet.setBetMode(betMode);
			gameSet.setIncreaseState(increaseState);
			gameSet.setBetSecond(betSecond);
			gameSet.setSplitTwoSideAmount(splitTwoSideAmount);
			gameSet.setSplitNumberAmount(splitNumberAmount);
			gameSet.setUpdateTime(new Date());
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			hmGameSetService.update(gameSet);

			hmGameSetId=gameSet.getIbscHmGameSetId();
		}
		if (IbsTypeEnum.TRUE.name().equals(betState)) {
			GameUtil.Code game=GameUtil.Code.valueOf(gameCode);
			new MemberConfigController().gameLimit(existHmId, code,game, hmGameSetId);

			new CodingItemThread(existHmId, game).execute(null);
		}

		return bean.success();
	}
}
