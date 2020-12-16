package com.cloud.user.connector.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_token.entity.AppToken;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_verify_code.service.AppVerifyCodeService;
import com.cloud.user.user_login_log.entity.UserLoginLog;
import com.cloud.user.user_login_log.service.UserLoginLogService;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * 用户登录
 *
 * @Author: Dongming
 * @Date: 2020-06-15 14:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LoginAction extends BaseMvcData {

	private ChannelTypeEnum channelType;

	public LoginAction(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}

	@Override
	public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String accountName = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String accountPassword = StringTool.trimAll(StringTool.getString(jsonData, "password", ""));
		String code = StringTool.trimAll(StringTool.getString(jsonData, "code", ""));
		String platformType = StringTool.trimAll(StringTool.getString(jsonData, "platformType", ""));
		String sessionId = StringTool.trimAll(StringTool.getString(jsonData, "session", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(accountName, accountPassword, code, sessionId)) {
			return bean.put401Data();
		}
		try {
			// 验证码匹配
			String verifyCode = new AppVerifyCodeService().findVerifyCode(sessionId, channelType);
			if (StringTool.isEmpty(verifyCode) || !verifyCode.equals(code)) {
				bean.putEnum(ReturnCodeEnum.app400VerifyCode);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 查找账号
			AppAccountService accountService = new AppAccountService();
			String appUserId = accountService.findAppUserId(accountName, accountPassword);
			if (StringTool.isEmpty(appUserId)) {
				bean.putEnum(ReturnCodeEnum.app400Login);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 查找用户
			String userType = getUserType();
			AppUserService userService = new AppUserService();
			if (!StateEnum.OPEN.name().equals(userService.findState(appUserId, userType))) {
				bean.putEnum(ReturnCodeEnum.app404LoginDisable);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			Date nowTime = new Date();
			// 更新登录时间
			accountService.updateLogin(appUserId, accountName, nowTime);
			userService.updateLogin(appUserId, nowTime);

			AppTokenService tokenService = new AppTokenService();
			String tokenId = tokenService.findId(appUserId, channelType);
			long periodTimeLong = System.currentTimeMillis() + DateTool.getMillisecondsMinutes(30);
			String value = RandomTool.getNumLetter32();
			if (StringTool.isEmpty(tokenId)) {
				AppToken token = new AppToken();
				token.setAppSessionId(sessionId);
				token.setAppUserId(appUserId);
				token.setValue(value);
				token.setUserType(userType);
				token.setRegisterType(userType);
				token.setChannelType(channelType.name());
				token.setIp(findServletIp());
				token.setPeriodTime(new Date(periodTimeLong));
				token.setPeriodTimeLong(periodTimeLong);
				token.setCreateTime(nowTime);
				token.setCreateTimeLong(System.currentTimeMillis());
				token.setUpdateTime(nowTime);
				token.setUpdateTimeLong(System.currentTimeMillis());
				token.setState(StateEnum.OPEN.name());
				tokenService.save(token);
			} else {
				tokenService.update(tokenId, value, findServletIp(), periodTimeLong, nowTime);
			}
			// 保存登录日志
			saveLog(appUserId,accountName,platformType,nowTime);
			// 返回token
			bean.success(value);
		} catch (Exception e) {
			log.error("用户登录失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
	private void saveLog(String appUserId,String accountName,String platformType,Date nowTime) throws Exception {
		// 保存登录日志
		UserLoginLog loginLog = new UserLoginLog();
		if("CARD".equalsIgnoreCase(platformType)){
			Map<String, Object> cardAdminInfo = new CardAdminService().findUserInfo(appUserId,"");
			loginLog.setUserPath(cardAdminInfo.get("USER_PATH_"));
		}

		loginLog.setUserId(appUserId);
		loginLog.setUserName(accountName);

		loginLog.setChannelType(platformType);
		loginLog.setOpertContent(StateEnum.LOGIN.name());
		loginLog.setIp(findServletIp());
		loginLog.setCreateTime(nowTime);
		loginLog.setCreateTimeLong(System.currentTimeMillis());
		loginLog.setUpdateTime(nowTime);
		loginLog.setUpdateTimeLong(System.currentTimeMillis());
		loginLog.setState(StateEnum.OPEN.name());
		new UserLoginLogService().save(loginLog);
	}

	private String getUserType() {
		switch (channelType) {
			case SYS:
			case ADMIN:
				return "ADMIN";
			default:
				return "USER";
		}
	}
}
