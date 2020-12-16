package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONArray;
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
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import com.ibs.plan.module.client.ibsc_hm_game_set.entity.IbscHmGameSet;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 设置游戏信息执行器
 * @Author: null
 * @Date: 2020-05-29 16:16
 * @Version: v1.0
 */
public class SetGameInfoExecutor implements ClientMqExecutor {

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

		JSONArray gameInfo = msgObj.getJSONArray("GAME_INFO_");
		if (ContainerTool.isEmpty(gameInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		IbscHmGameSetService hmGameSetService=new IbscHmGameSetService();
		IbscHmGameSet gameSet=new IbscHmGameSet();
		gameSet.setExistHmId(existHmId);
		gameSet.setCreateTime(new Date());
		gameSet.setCreateTimeLong(System.currentTimeMillis());
		gameSet.setUpdateTime(new Date());
		gameSet.setUpdateTimeLong(System.currentTimeMillis());
		gameSet.setState(IbsStateEnum.OPEN.name());
		for (int i = 0; i < gameInfo.size(); i++) {
			JSONObject info = gameInfo.getJSONObject(i);
			gameSet.setGameCode(info.get("GAME_CODE_"));
			gameSet.setGameDrawType(info.get("GAME_DRAW_TYPE_"));
			gameSet.setBetState(info.get("BET_STATE_"));
			gameSet.setBetMode(info.get("BET_MODE_"));
			gameSet.setIncreaseState(info.get("INCREASE_STATE_"));
			gameSet.setBetSecond(info.getInteger("BET_SECOND_"));
			gameSet.setSplitTwoSideAmount(info.getInteger("SPLIT_TWO_SIDE_AMOUNT_"));
			gameSet.setSplitNumberAmount(info.getInteger("SPLIT_NUMBER_AMOUNT_"));
			String hmGameSetId = hmGameSetService.save(gameSet);

			if (IbsTypeEnum.TRUE.name().equals(info.get("BET_STATE_"))) {
				new MemberConfigController().gameLimit(existHmId, code, GameUtil.Code.valueOf(info.getString("GAME_CODE_")), hmGameSetId);
			}
		}
		bean.success();
		return bean;
	}
}
