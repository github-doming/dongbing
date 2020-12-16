package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_user.entity.IbspUser;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户盘口设置表单信息
 * @Author: null
 * @Date: 2020-03-18 14:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/handicapSetForm")
public class AppUserHandicapSetFormAction
		extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserId = StringTool.getString(dataMap, "appUserId", "");

		try {
			Map<String, Object> data = new HashMap<>(10);
			IbspUserService userService = new IbspUserService();
			IbspUser user = userService.find(appUserId);
			if (user == null) {
				bean.putEnum(ReturnCodeEnum.app404Login);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			data.put("userName", user.getNickname());
			//获取所有游戏
			List<Map<String, Object>> allGameInfo = new IbspGameService().findGameInfo();
			data.put("allGameInfo", allGameInfo);
			//可用盘口最大在线数量,获取可用游戏
			Map<String, Object> availableHandicap = new IbspExpUserService().findAvailableHandicap(appUserId);
			data.putAll(availableHandicap);
			//获取会员盘口使用信息
			List<Map<String, Object>> memberInfos = new IbspHmUserService().listHandicapInfo(appUserId);
			data.put("memberInfos", memberInfos);
			//获取所有会员盘口
			List<String> memberAllUsable = new IbspHandicapService().findHandicapCode();
			data.put("memberAllUsable", memberAllUsable);


			bean.success(data);
		} catch (Exception e) {
			log.error("用户表单页面错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
