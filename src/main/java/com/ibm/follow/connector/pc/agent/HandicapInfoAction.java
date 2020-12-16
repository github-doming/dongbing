package com.ibm.follow.connector.pc.agent;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.connector.pc.home.AgentOpenHandicapAction;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 获取盘口会员的盘口信息
 * @Author: Dongming
 * @Date: 2019-09-04 14:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/handicapInfo", method = HttpConfig.Method.GET) public class HandicapInfoAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
		if (StringTool.isEmpty(handicapAgentId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			String handicapId = new IbmHandicapAgentService().findHandicapId(handicapAgentId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			Map<String,Object>  account =new IbmHandicapAgentService().findInfo(handicapAgentId,appUserId);
			if (ContainerTool.isEmpty(account)){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			account.remove("AGENT_PASSWORD_");
			account.remove("ROW_NUM");
			// 获取该盘口会员的信息
			//是否登录
			Map<String, Object> haInfo = new IbmHaInfoService().findShowInfo(handicapAgentId);

			if (ContainerTool.isEmpty(haInfo) || !IbmStateEnum.LOGIN.name().equals(haInfo.get("STATE_"))) {
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			Map<String, Object> data = new HashMap<>(5);
			data.put("account",account);
			data.put("haInfo", haInfo);
			haInfo.remove("ROW_NUM");
			Map<String, Object> haSetInfo = new IbmHaSetService().findShowInfo(handicapAgentId);
			//跟随会员列表转换数据类型
			AgentOpenHandicapAction.turnFormat(haSetInfo);
			data.put("haSetInfo", haSetInfo);

			//盘口游戏信息获取
			List<Map<String, Object>> gameInfos = new IbmHaGameSetService().listGameInfo(handicapAgentId);
			data.put("gameInfo", gameInfos);

			// 获取代理游戏设置
			List<Map<String, Object>> haGameSetInfos = new IbmHaGameSetService().listShow(handicapAgentId);
			for (Map<String, Object> haGameSetInfo : haGameSetInfos) {
				haGameSetInfo
						.put("BET_FOLLOW_MULTIPLE_", NumberTool.doubleT(haGameSetInfo.remove("BET_FOLLOW_MULTIPLE_T_")));
				haGameSetInfo.put("BET_LEAST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_LEAST_AMOUNT_T_")));
				haGameSetInfo.put("BET_MOST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_MOST_AMOUNT_T_")));
				haGameSetInfo.remove("ROW_NUM");
			}
			data.put("haGameSetInfos", haGameSetInfos);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("获取盘口会员的盘口信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
