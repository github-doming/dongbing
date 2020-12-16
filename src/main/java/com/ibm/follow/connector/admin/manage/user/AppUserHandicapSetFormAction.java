package com.ibm.follow.connector.admin.manage.user;

import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.cloud.ibm_user.service.IbmUserService;
import org.doming.core.common.servlet.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户盘口设置表单信息
 * @Author: null
 * @Date: 2020-03-18 14:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicapSetForm") public class AppUserHandicapSetFormAction
		extends CommAdminAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = dataMap.getOrDefault("appUserId", "").toString();

		try {
			Map<String, Object> data = new HashMap<>(10);

			Map<String,Object> userInfo = new IbmUserService().userInfo(appUserId);
			data.put("userName", userInfo.get("NICKNAME_"));
			//获取所有游戏
			List<Map<String, Object>> allGameInfo = new IbmGameService().findGameInfo();
			data.put("allGameInfo", allGameInfo);
			//可用盘口最大在线数量,获取可用游戏
			Map<String, Object> availableHandicap = new IbmExpUserService().findAvailableHandicap(appUserId);
			data.putAll(availableHandicap);
			//获取会员盘口使用信息
			List<Map<String, Object>> memberInfos = new IbmHmUserService().listHandicapInfo(appUserId);
			data.put("memberInfos", memberInfos);
			//获取代理盘口使用信息
			List<Map<String, Object>> agentInfos = new IbmHaUserService().listHandicapInfo(appUserId);
			data.put("agentInfos", agentInfos);

			//获取所有会员盘口
			IbmHandicapService handicapService = new IbmHandicapService();
			List<String> memberAllUsable = handicapService.findHandicapCode(IbmTypeEnum.MEMBER);
			data.put("memberAllUsable", memberAllUsable);
			//获取所有代理盘口
			List<String> agentAllUsable = handicapService.findHandicapCode(IbmTypeEnum.AGENT);
			data.put("agentAllUsable", agentAllUsable);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("用户表单页面错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
