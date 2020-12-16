package com.ibm.old.v1.pc.ibm_home.action;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_cms_message_content.t.service.IbmCmsMessageContentTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;
import com.ibm.old.v1.pc.ibm_handicap_user.t.service.IbmPcHandicapUserTService;
import com.ibm.old.v1.pc.ibm_plan_user.t.controller.IbmPlanUserController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 首页，主页面活动
 * @Author: Dongming
 * @Date: 2019-01-09 17:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmHomePageAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			Map<String, Object> data = new HashMap<>(5);

			// 用户信息
			AppUserService userService = new AppUserService();
			data.put("userInfo", userService.findHomeInfo(appUserId));

			// 用户系统消息
			IbmCmsMessageContentTService messageContentTService=new IbmCmsMessageContentTService();
			//通过用户ID查询消息
			data.put("cmsInfo", messageContentTService.listHomeInfo(appUserId));

			// 用户最近打开盘口
			IbmPcHandicapUserTService handicapUserTService = new IbmPcHandicapUserTService();
			data.put("recentLogin", handicapUserTService.listRecentLoginHandicapInfo(appUserId));

			// 用户正在托管盘口会员
			data.put("onHosting", handicapUserTService.listOnHostingHandicapInfo(appUserId));

			// 游戏方案开启信息列表
			IbmPlanUserController planUserTController = new IbmPlanUserController();
			data.put("gamePlan", planUserTController.listHomeGamePlanOrder(appUserId));

			jrb.success();
			jrb.setData(data);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}
}
