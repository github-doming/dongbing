package com.ibm.connector.core.user;
import all.app.common.service.AppAccountService;
import all.app.common.service.AppTokenRedisService;
import all.app.common.service.AppUserRedisService;
import all.gen.app_account.t.entity.AppAccountT;
import all.gen.app_token.t.entity.AppTokenT;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.connector.service.user.AppVerifyAccountService;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 用户登录
 * @Author: Dongming
 * @Date: 2019-08-28 18:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginAction extends CommCoreAction {

	protected AppUserT appUser;
	private ChannelTypeEnum channelType;
	private IbmTypeEnum userType;
	public LoginAction(ChannelTypeEnum channelType,IbmTypeEnum userType) {
		super.isTime = false;
		this.channelType = channelType;
		this.userType = userType;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String accountName = jsonData.getOrDefault("name", "").toString().replace(" ","");
		String password = jsonData.getOrDefault("password", "").toString().replace(" ","");
		String code = jsonData.getOrDefault("code", "").toString().replace(" ","");
		String sessionId = jsonData.getOrDefault("session", "").toString();
		if (StringTool.isEmpty(accountName, password, code, sessionId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			// 验证码匹配
			String verifyCode = new AppVerifyAccountService().findVerifyCode(sessionId,channelType);
			if (StringTool.isEmpty(verifyCode) || !verifyCode.equals(code)) {
				bean.putEnum(ReturnCodeEnum.app400VerifyCode);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 查找账号
			AppAccountService accountService = new AppAccountService();
			AppAccountT account = accountService.findAppAccount(getTenant(), accountName, password);
			if (account == null) {
				bean.putEnum(ReturnCodeEnum.app400Login);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 查找用户
			AppUserService userService = new AppUserService();
			appUser = userService.findAppUser(getTenant(), accountName, password, userType.name());
			if (appUser == null) {
				bean.putEnum(ReturnCodeEnum.app400Login);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			if(!IbmStateEnum.OPEN.name().equals(appUser.getState())){
				bean.putEnum(ReturnCodeEnum.app404LoginDisable);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			//TODO 验证到期时间


			// 更新登录时间
			AppUserRedisService userRedisService = new AppUserRedisService();
			Date nowTime = new Date();
			account.setLoginTime(nowTime);
			account.setLoginTimeLong(System.currentTimeMillis());
			accountService.update(account);
			appUser.setLoginTime(nowTime);
			appUser.setLoginTimeLong(System.currentTimeMillis());
			userRedisService.update(appUser);

			// 查找AppTokenT
			AppTokenRedisService tokenRedisService = new AppTokenRedisService();
			AppTokenT appToken = tokenRedisService
					.findAppToken(getTenant(), accountName, password, channelType.name());
			if (appToken == null) {
				appToken = new AppTokenT();
				appToken.setAppUserId(appUser.getAppUserId());
				appToken.setUserType(appUser.getAppUserType());
				appToken.setChannelType(channelType.name());
				appToken.setState(IbmStateEnum.OPEN.name());
				appToken.setTenantCode(getTenant());
				appToken.setIp(findServletIp());
				appToken.setCreateTime(nowTime);
				appToken.setCreateTimeLong(System.currentTimeMillis());
				// 新增redis的token和数据库的token
				tokenRedisService.saveAppToken(getTenant(),appToken);
			} else {
				appToken.setIp(findServletIp());
				appToken.setState(IbmStateEnum.OPEN.name());
				appToken.setUpdateTime(nowTime);
				appToken.setUpdateTimeLong(System.currentTimeMillis());
				// 更新redis的token和数据库的token
				tokenRedisService.updateAppToken(getTenant(),appToken);
			}
			// AppUser保存在redis
			userRedisService.saveAppUserByRedis(appUser);
			// 返回token
			bean.success();
			bean.setData(appToken.getValue());
		} catch (Exception e) {
			log.error("用户登录失败，", e);
			throw e;
		}
		return bean;
	}
}
