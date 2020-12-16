package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
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
 * @Description: 设置投注状态执行器
 * @Author: null
 * @Date: 2020-05-29 16:18
 * @Version: v1.0
 */
public class SetBetStateExecutor implements ClientMqExecutor {
	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String game = msgObj.getString("GAME_CODE_");
		String betState = msgObj.getString("BET_STATE_");
		if (StringTool.isEmpty(game, betState)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String handicap=new IbscExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicap)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(handicap);
		GameUtil.Code gameCode=GameUtil.Code.valueOf(game);

		IbscHmGameSetService hmGameSetService=new IbscHmGameSetService();
		IbscHmGameSet gameSet=hmGameSetService.findEntity(existHmId,game);
		if(gameSet==null){
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		if(StringTool.notEmpty(msgObj.get("BET_MODE_"))){
			gameSet.setBetMode(msgObj.getString("BET_MODE_"));
		}
		gameSet.setBetState(betState);
		gameSet.setUpdateTime(new Date());
		gameSet.setUpdateTimeLong(System.currentTimeMillis());
		hmGameSetService.update(gameSet);

		if (IbsTypeEnum.TRUE.name().equals(betState)) {
			new MemberConfigController().gameLimit(existHmId, handicapCode, gameCode, gameSet.getIbscHmGameSetId());

			new CodingItemThread(existHmId,gameCode).execute(null);
		}

		bean.success();
		return bean;
	}
}
