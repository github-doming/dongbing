package com.ibm.connector.core.user;

import all.app.common.service.AppAccountService;
import all.app.common.service.AppUserRedisService;
import all.gen.app_account.t.entity.AppAccountT;
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
import com.ibm.common.tools.EncryptTool;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.connector.service.user.AppVerifyAccountService;
import com.ibm.follow.servlet.cloud.core.controller.init.RegisterInitController;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 注册用户
 * @Author: Dongming
 * @Date: 2019-08-28 17:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RegisterAction extends CommCoreAction {
	private ChannelTypeEnum channelType;
	private IbmTypeEnum type;

	public RegisterAction(ChannelTypeEnum channelType, IbmTypeEnum type) {
		super.isTime = false;
		this.channelType = channelType;
		this.type = type;
	}

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String accountName = jsonData.getOrDefault("name", "").toString().replace(" ", "");
		String password = jsonData.getOrDefault("password", "").toString().replace(" ", "");
		String code = jsonData.getOrDefault("code", "").toString().replace(" ", "");
		String sessionId = jsonData.getOrDefault("session", "").toString();
		String email = jsonData.getOrDefault("email", "").toString();
		if (StringTool.isEmpty(accountName, password, code, sessionId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if(!accountName.matches(regExpAccount) || !password.matches(regExpPwd)){
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}

		try {
			// 验证码匹配
			String verifyCode = new AppVerifyAccountService().findVerifyCode(sessionId, channelType);
			if (StringTool.isEmpty(verifyCode) || !verifyCode.equals(code)) {
				bean.putEnum(ReturnCodeEnum.app400VerifyCode);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 注册用户
			AppUserService userService = new AppUserService();
			AppUserT appUser = userService.findByAccountName(accountName);
			if (appUser != null) {
				bean.putEnum(ReturnCodeEnum.app409Register);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}
			Date date = new Date();

			appUser = new AppUserT();
			appUser.setAppUserName(accountName);
			appUser.setNickname(accountName);
			appUser.setAppUserType(type.name());
			appUser.setCreateTime(date);
			appUser.setCreateTimeLong(System.currentTimeMillis());
			appUser.setUpdateTime(date);
			appUser.setUpdateTimeLong(System.currentTimeMillis());
			appUser.setState(IbmStateEnum.OPEN.name());
			appUser.setTenantCode(getTenant());
			appUser.setDesc(email);

			String appUserId = new AppUserRedisService().save(appUser);

			//保存会员账户
			AppAccountT appAccount = new AppAccountT();
			password = EncryptTool.encode(EncryptTool.Type.ASE, password);
			appAccount.setAccountName(accountName);
			appAccount.setPassword(password);
			appAccount.setAppUserId(appUserId);
			appAccount.setCreateTime(date);
			appAccount.setCreateTimeLong(System.currentTimeMillis());
			appAccount.setUpdateTime(date);
			appAccount.setUpdateTimeLong(System.currentTimeMillis());
			appAccount.setState(IbmStateEnum.OPEN.name());
			appAccount.setTenantCode(getTenant());
			appAccount.setChannelType(channelType.name());
			new AppAccountService().save(appAccount);

			//注册初始化信息
			new RegisterInitController().execute(appUserId);

			bean.success();
		} catch (Exception e) {
			log.error("注册用户失败，", e);
			throw e;
		}

		return bean;
	}

}
