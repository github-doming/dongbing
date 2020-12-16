package com.ibm.follow.connector.admin.manage.user.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 代理详情表单
 * @Author: null
 * @Date: 2020-03-21 14:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/itemForm") public class AgentItemFormAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		//盘口会员id
		String handicapAgentId = StringTool.getString(dataMap.get("handicapAgentId"), "");

		try {
			Map<String, Object> map = new HashMap<>(4);

			//代理信息和自动登录信息
			IbmHandicapAgentService handicapAgentService = new IbmHandicapAgentService();
			Map<String, Object> itemInfo = handicapAgentService.findItemInfo(handicapAgentId);
			if (ContainerTool.isEmpty(itemInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.isEmpty(itemInfo.get("LANDED_TIME_LONG_"))
					|| Long.parseLong(itemInfo.get("LANDED_TIME_LONG_").toString()) == 0) {
				itemInfo.put("AUTO_LOGIN_STATE_", IbmTypeEnum.FALSE.name());
			} else {
				itemInfo.put("AUTO_LOGIN_STATE_", IbmTypeEnum.TRUE.name());
			}
			map.put("itemInfo", itemInfo);

			//代理可用游戏信息
			List<Map<String, Object>> availableGame = new IbmHaGameSetService().listGameInfo(handicapAgentId);
			for (Map<String, Object> gameInfo : availableGame) {
				gameInfo.remove("BET_STATE_");
				gameInfo.remove("ICON_");
			}
			map.put("availableGame", availableGame);

			//所有可用盘口游戏信息
			List<Map<String, Object>> allHandicapGame = new IbmHandicapGameService()
					.findHandicapInfo(itemInfo.remove("HANDICAP_ID_"));
			map.put("allHandicapGame", allHandicapGame);

			//TODO  盘口设置信息

			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("会员投注设置失败", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
