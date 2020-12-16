package com.ibm.old.v1.app.t.action;

import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_account.t.service.AppAccountTService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonThreadLocal;
import com.ibm.old.v1.app.t.controller.AppTokenController;
import com.ibm.old.v1.app.t.controller.AppUserController;
import com.ibm.old.v1.app.t.service.AppUserService;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_user.t.service.IbmHandicapUserTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.common.wck.init.InitUserDataInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: ibm注册
 * @Author: zjj
 * @Date: 2019-05-05 10:22
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenRegisterAction extends BaseAppAction {

	@Override public String run() throws Exception {
		String channelType = ChannelTypeEnum.PC.name();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		if (StringTool.isEmpty(json)) {
			bean.putEnum(ReturnCodeEnum.app400Session);
			bean.putSysEnum(ReturnCodeEnum.code400);
			return this.returnJson(bean);
		}
		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(json);
		String accountName = BeanThreadLocal.find(jsonMap.get("name"), "");
		String password = BeanThreadLocal.find(jsonMap.get("password"), "");
		String code = BeanThreadLocal.find(jsonMap.get("code"), "");
		String sessionId = BeanThreadLocal.find(jsonMap.get("session"), "");
		String email = BeanThreadLocal.find(jsonMap.get("email"), "");
		if (StringTool.isEmpty(accountName,password,code,sessionId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(bean);
		}
		try {
			// 验证码匹配
			AppTokenController tokenController = new AppTokenController();
			String verifyCode = tokenController.findVerifyCode(sessionId);

			if (StringTool.isEmpty(verifyCode) || !verifyCode.equals(code)) {
				bean.putEnum(ReturnCodeEnum.app400VerifyCode);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return returnJson(bean);
			}
			// 注册用户
			AppUserService appUserService = new AppUserService();
			AppUserT appUserT = appUserService.findAppUserByAccountName(accountName);
			if (appUserT != null) {
				bean.putEnum(ReturnCodeEnum.app409Register);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return this.returnJson(bean);
			}
			Date date = new Date();

			appUserT = new AppUserT();
			appUserT.setAppUserName(accountName);
			appUserT.setNickname(accountName);
			appUserT.setAppUserType(IbmTypeEnum.FREE.name());
			appUserT.setCreateTime(date);
			appUserT.setCreateTimeLong(date.getTime());
			appUserT.setUpdateTime(date);
			appUserT.setUpdateTimeLong(date.getTime());
			appUserT.setState(IbmStateEnum.OPEN.name());
			appUserT.setTenantCode(tenantCode);
			appUserT.setDesc(email);
			String appUserId = appUserRedisService.save(appUserT);

			AppAccountT appAccountT = new AppAccountT();
			AppAccountTService appAccountTService = new AppAccountTService();
			String commLocalASE = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("comm.local.ASE"), "false");

			if ("true".equals(commLocalASE)) {
				String key = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.ASE_key"), "");
				password = CommASEUtil.encode(key, password.trim()).trim();
			}
			appAccountT.setAccountName(accountName);
			appAccountT.setPassword(password);
			appAccountT.setAppUserId(appUserId);
			appAccountT.setCreateTime(date);
			appAccountT.setCreateTimeLong(date.getTime());
			appAccountT.setUpdateTime(date);
			appAccountT.setUpdateTimeLong(date.getTime());
			appAccountT.setState(IbmStateEnum.OPEN.name());
			appAccountT.setTenantCode(tenantCode);
			appAccountT.setChannelType(channelType);
			appAccountTService.save(appAccountT);

			// 初始化数据
			AppUserController userController = new AppUserController();
			userController.initUser(appUserId);
			initUserDataInfo(appUserId);

			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，", e);
			throw e;
		}
		return this.returnJson(bean);
	}


	private void initUserDataInfo(String appUserId) throws Exception {
		//获取用户可开启的方案ID
		AppUserService userService = new AppUserService();
		String planIds = userService.findPlanById(appUserId);

		//获取用户可开启的方案
		IbmPlanTService planTService = new IbmPlanTService();
		List<Map<String, Object>> plans = planTService.findPlan(planIds);

		for (Map<String, Object> plan : plans) {
			//初始化用户方案信息
			new InitUserDataInfo().initPlanUser(plan, appUserId);
		}

		//获取用户可开启的盘口ID
		String handicapIds = userService.findHandicapById(appUserId);

		//获取用户可开启的盘口
		IbmHandicapUserTService handicapUserTService = new IbmHandicapUserTService();
		IbmHandicapTService handicapTService = new IbmHandicapTService();
		List<Map<String, Object>> handicaps =  handicapTService.findHandicap(handicapIds);
		if (ContainerTool.notEmpty(handicaps)) {
			handicapUserTService.saveBatch(handicaps, appUserId);
		}
	}

}
