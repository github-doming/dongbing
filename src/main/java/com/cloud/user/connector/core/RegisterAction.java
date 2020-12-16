package com.cloud.user.connector.core;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.entity.AppAccount;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import com.cloud.user.app_verify_code.service.AppVerifyCodeService;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
/**
 * 注册用户
 *
 * @Author: Dongming
 * @Date: 2020-06-15 17:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RegisterAction extends BaseMvcData {
	private ChannelTypeEnum channelType;

	public RegisterAction(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}
	@Override public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);
		String accountName = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String password = StringTool.trimAll(StringTool.getString(jsonData, "password", ""));
		String code = StringTool.trimAll(StringTool.getString(jsonData, "code", ""));
		String sessionId = StringTool.trimAll(StringTool.getString(jsonData, "session", ""));
		String email = StringTool.trimAll(StringTool.getString(jsonData, "email", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(accountName, password, code, sessionId)) {
			return bean.put401Data();
		}

		String regExpAccount = "[a-zA-Z0-9]{6,20}$";
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if (!accountName.matches(regExpAccount) || !password.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}

		try {
			if (ChannelTypeEnum.SYS.equals(channelType) || ChannelTypeEnum.ADMIN.equals(channelType) ){
				bean.putEnum(ReturnCodeEnum.code403);
				bean.putSysEnum(ReturnCodeEnum.code403);
				return bean;
			}

			// 验证码匹配
			String verifyCode = new AppVerifyCodeService().findVerifyCode(sessionId, channelType);
			if (StringTool.isEmpty(verifyCode) || !verifyCode.equals(code)) {
				bean.putEnum(ReturnCodeEnum.app400VerifyCode);
				bean.putSysEnum(ReturnCodeEnum.code400);
				return bean;
			}
			// 注册用户
			AppAccountService accountService = new AppAccountService();
			String userId = accountService.findUserIdByName(accountName);
			if (StringTool.notEmpty(userId)) {
				bean.putEnum(ReturnCodeEnum.app409Register);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}

			Date date = new Date();
			AppUser appUser = new AppUser();
			appUser.setAppUserName(accountName);
			appUser.setNickname(accountName);
			appUser.setAppUserType(getUserType());
			appUser.setCreateTime(date);
			appUser.setCreateTimeLong(System.currentTimeMillis());
			appUser.setUpdateTime(date);
			appUser.setUpdateTimeLong(System.currentTimeMillis());
			appUser.setState(StateEnum.OPEN.name());
			appUser.setTenantCode(getTenant());
			appUser.setDesc(email);
			userId =  new AppUserService().save(appUser);

			//保存会员账户
			AppAccount appAccount = new AppAccount();
			password = EncryptTool.encode(EncryptTool.Type.ASE, password);
			appAccount.setAccountName(accountName);
			appAccount.setPassword(password);
			appAccount.setAppUserId(userId);
			appAccount.setChannelType(channelType.name());
			appAccount.setRegisterType("USER");
			appAccount.setCreateTime(date);
			appAccount.setCreateTimeLong(System.currentTimeMillis());
			appAccount.setUpdateTime(date);
			appAccount.setUpdateTimeLong(System.currentTimeMillis());
			appAccount.setState(StateEnum.OPEN.name());
			accountService.save(appAccount);

			//初始化点数
			String repId = new AppUserPointRepService().save(new HashMap<>(2), userId, 0, "初始化会员", date);
			new AppUserPointService().save(userId,repId,0,date);

			bean.success();
		} catch (Exception e) {
			log.error("注册用户失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
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
