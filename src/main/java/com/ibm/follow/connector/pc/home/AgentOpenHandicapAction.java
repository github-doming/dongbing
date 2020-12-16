package com.ibm.follow.connector.pc.home;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 首页，开启代理盘口
 * @Author: Dongming
 * @Date: 2019-08-30 17:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/openAgentHandicap", method = HttpConfig.Method.GET) public class AgentOpenHandicapAction
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

			if (StringTool.isEmpty(handicapId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//验证账户
			Map<String, Object> account = new IbmHandicapAgentService().findInfo(handicapAgentId, appUserId);
			if (ContainerTool.isEmpty(account)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			account.remove("AGENT_PASSWORD_");
			account.remove("ROW_NUM");

			Map<String, Object> data = new HashMap<>(2);
			//获取该用户所有盘口代理账号
			List<Map<String, Object>> allAccount = new IbmHandicapAgentService().listAllAccount(appUserId, handicapId);
			data.put("account", account);
			data.put("allAccount", allAccount);

			// 获取该盘口代理的信息
			Map<String, Object> haInfo = new IbmHaInfoService().findShowInfo(handicapAgentId);
			if (ContainerTool.notEmpty(haInfo)) {
				haInfo.remove("ROW_NUM");
				data.put("haInfo", haInfo);
				if (IbmStateEnum.LOGIN.name().equals(haInfo.get("STATE_"))) {
					// 登录-个人信息-跟投会员设置
					Map<String, Object> haSetInfo = new IbmHaSetService().findShowInfo(handicapAgentId);
					//跟随会员列表转换数据类型
					turnFormat(haSetInfo);
					data.put("haSetInfo", haSetInfo);
				}
			}
			//盘口游戏信息获取
			List<Map<String, Object>> gameInfos = new IbmHaGameSetService().listGameInfo(handicapAgentId);

			data.put("gameInfo", gameInfos);
			// 获取代理游戏设置
			List<Map<String, Object>> haGameSetInfos = new IbmHaGameSetService().listShow(handicapAgentId);
			if (ContainerTool.notEmpty(haGameSetInfos)) {
				for (Map<String, Object> haGameSetInfo : haGameSetInfos) {
					haGameSetInfo.put("BET_FOLLOW_MULTIPLE_",
							NumberTool.doubleT(haGameSetInfo.remove("BET_FOLLOW_MULTIPLE_T_")));
					haGameSetInfo
							.put("BET_LEAST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_LEAST_AMOUNT_T_")));
					haGameSetInfo
							.put("BET_MOST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_MOST_AMOUNT_T_")));
					haGameSetInfo.remove("ROW_NUM");
				}
				data.put("haGameSetInfos", haGameSetInfos);
			}

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("开启代理盘口错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 跟随会员列表转换数据类型
	 *
	 * @param haSetInfo 代理设置信息
	 */
	public static void turnFormat(Map<String, Object> haSetInfo) {
		haSetInfo.remove("ROW_NUM");
		JSONArray followMemberArray = new JSONArray();
		JSONObject followMemberObj = JSONObject.fromObject(haSetInfo.get("FOLLOW_MEMBER_LIST_INFO_"));
		for (Object key : followMemberObj.keySet()) {
			JSONArray array = new JSONArray();
			array.add(key);
			array.add(followMemberObj.get(key));
			followMemberArray.add(array);
		}
		haSetInfo.put("FOLLOW_MEMBER_LIST_INFO_", followMemberArray);
	}
}
