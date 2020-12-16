package com.ibs.plan.connector.app.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
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
@ActionMapping(value = "/ibs/app/handicap/gamePeriod", method = HttpConfig.Method.GET)
public class HandicapGamePeriodAction
		extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String id = dataMap.getOrDefault("ID_", "").toString();
		Object gameCodeObj = dataMap.getOrDefault("GAME_CODE_", "");


		if (StringTool.isEmpty(id, gameCodeObj)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		GameUtil.Code gameCode = GameUtil.value(gameCodeObj);
		if (StringTool.isEmpty(gameCode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			//查询ID
			String handicapId = new IbspHandicapMemberService().findHandicapId(id, appUserId);

			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			HandicapUtil.Code handicapCode = HandicapUtil.code(handicapId);
			//查询最近 10 期
			Object[] periods = gameCode.getGameFactory().period(handicapCode).listPeriod(10);
			Arrays.sort(periods, Collections.reverseOrder());

			Map<String, Object> data = new HashMap<>(1);
			data.put("periods", periods);
			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN.concat("盘口游戏期数失败"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
