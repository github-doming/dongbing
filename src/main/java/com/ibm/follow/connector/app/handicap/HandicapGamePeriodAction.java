package com.ibm.follow.connector.app.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * 盘口游戏期数
 *
 * @Author: Dongming
 * @Date: 2020-04-17 11:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/handicap/gamePeriod", method = HttpConfig.Method.GET) public class HandicapGamePeriodAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String id = dataMap.getOrDefault("ID_", "").toString();
		Object gameCodeObj = dataMap.getOrDefault("GAME_CODE_", "");


		if (StringTool.isEmpty(id, gameCodeObj)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		GameUtil.Code gameCode = GameUtil.value(gameCodeObj);
		if (StringTool.isEmpty(gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			//查询ID
			String handicapId = new IbmHandicapMemberService().findHandicapId(id, appUserId);
			if (StringTool.isEmpty(handicapId)){
				handicapId = new IbmHandicapAgentService().findHandicapId(id, appUserId);
			}
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			HandicapUtil.Code handicapCode = HandicapUtil.code(handicapId);
			//查询最近 10 期
			Object[] periods =gameCode.getGameFactory().period(handicapCode).listPeriod(10);
			Arrays.sort(periods, Collections.reverseOrder());

			Map<String,Object> data = new HashMap<>(1);
			data.put("periods",periods);
			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("盘口游戏期数失败"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
