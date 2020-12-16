package com.ibm.old.v1.app.t.action;

import all.app.common.service.AppAccountService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonThreadLocal;
import com.ibm.old.v1.app.t.controller.AppTokenController;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
/**
 * @Description: 用户登录
 * @Author: zjj
 * @Date: 2019-05-05 10:10
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppUserLoginAction extends BaseAppAction {

	@Override public String run() throws Exception {
		String channelType = ChannelTypeEnum.PC.name();
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		if (StringTool.isEmpty(json)) {
			bean.putEnum(ReturnCodeEnum.app400Session);
			bean.putSysEnum(ReturnCodeEnum.code400);
			return returnJson(bean);
		}
		Map<String, Object> jsonMap = JsonThreadLocal.findThreadLocal().get().json2map(json);
		String accountName = BeanThreadLocal.find(jsonMap.get("name"), "");
		String password = BeanThreadLocal.find(jsonMap.get("password"), "");
		String code = BeanThreadLocal.find(jsonMap.get("code"), "");
		String sessionId = BeanThreadLocal.find(jsonMap.get("session"), "");
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
			// 查找账号
			AppAccountService accountTService = new AppAccountService();
			AppAccountT account = accountTService.findAppAccount(this.tenantCode, accountName, password);
			if (account == null) {
				bean.putEnum(ReturnCodeEnum.app400Login);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return returnJson(bean);
			}
			// 查找用户
			AppUserT appUserT = appUserRedisService.findAppUser(this.tenantCode,accountName, password);
			if (appUserT == null) {
				bean.putEnum(ReturnCodeEnum.app400Login);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return returnJson(bean);
			}

			Date nowTime = new Date();
			// 更新登录时间
			account.setTenantCode(tenantCode);
			account.setLoginTime(nowTime);
			account.setLoginTimeLong(nowTime.getTime());
			accountTService.update(account);
			appUserT.setTenantCode(tenantCode);
			appUserT.setLoginTime(nowTime);
			appUserT.setLoginTimeLong(nowTime.getTime());
			appUserRedisService.update(appUserT);

			// 查找AppTokenT
			AppTokenT appTokenT = appTokenRedisService
					.findAppToken(this.tenantCode, accountName, password, channelType);
			if (appTokenT == null) {
				appTokenT = new AppTokenT();
				appTokenT.setAppUserId(appUserT.getAppUserId());
				appTokenT.setUserType(appUserT.getAppUserType());
				appTokenT.setChannelType(channelType);
				appTokenT.setState(IbmStateEnum.OPEN.name());
				appTokenT.setTenantCode(tenantCode);
				// 新增redis的token和数据库的token
				appTokenRedisService.saveAppToken(tenantCode,appTokenT);
			} else {
				appTokenT.setState(IbmStateEnum.OPEN.name());
				appTokenT.setTenantCode(tenantCode);
				// 更新redis的token和数据库的token
				appTokenRedisService.updateAppToken(tenantCode,appTokenT);
			}
			// AppUser保存在redis
			appUserRedisService.saveAppUserByRedis(appUserT);

			// 返回token
			bean.success();
			bean.setData(appTokenT.getValue());
		} catch (Exception e) {
			log.error("获取session失败，", e);
			throw e;
		}
		return this.returnJson(bean);
	}
}
