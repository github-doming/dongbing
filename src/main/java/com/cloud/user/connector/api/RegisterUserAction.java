package com.cloud.user.connector.api;
import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.entity.AppAccount;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 注册用户
 * 		--修改添加用户平台角色  IBM、IBS用户角色编码必须一致，
 * 			channelUserType 用户注册没有这个字段，在平台添加操作员时需要对应添加这个字段
 * 			临时使用app_user表 APP_USER_CODE_ 字段保存
 *
 * @Author: Dongming
 * @Date: 2020-06-18 14:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/registerUser", method = HttpConfig.Method.GET) public class RegisterUserAction
		extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String channelType = StringTool.trimAll(StringTool.getString(jsonData, "channelType", ""));
		String accountName = StringTool.trimAll(StringTool.getString(jsonData, "userAccount", ""));
		String password = StringTool.trimAll(StringTool.getString(jsonData, "userPassWord", ""));
		String state = StringTool.trimAll(StringTool.getString(jsonData, "state", ""));
		String tenantCode = StringTool.trimAll(StringTool.getString(jsonData, "tenantCode", ""));
		String channelUserType = StringTool.trimAll(StringTool.getString(jsonData, "channelUserType", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(userId, token, channelType, accountName, password, state)) {
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
			String valiUserId = new AppTokenService().findUserIdByToken(token);
			if (StringTool.isEmpty(valiUserId)) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			//验证用户是否出错
			if (!userId.equals(valiUserId)) {
				bean.putEnum(CodeEnum.CLOUD_403_DATA_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}

			// 注册用户
			AppAccountService accountService = new AppAccountService();
			userId = accountService.findUserIdByName(accountName);
			if (StringTool.notEmpty(userId)) {
				bean.putEnum(ReturnCodeEnum.app409Register);
				bean.putSysEnum(ReturnCodeEnum.code409);
				return bean;
			}
			String userType = getUserType(channelType);

			Date date = new Date();
			AppUser appUser = new AppUser();
			appUser.setAppUserName(accountName);
			appUser.setNickname(accountName);
			appUser.setAppUserType(userType);
			appUser.setAppUserCode(channelUserType);
			appUser.setCreateTime(date);
			appUser.setCreateTimeLong(System.currentTimeMillis());
			appUser.setUpdateTime(date);
			appUser.setUpdateTimeLong(System.currentTimeMillis());
			appUser.setState(StateEnum.OPEN.name());
			appUser.setTenantCode(tenantCode);
			userId = new AppUserService().save(appUser);

			//保存会员账户
			AppAccount appAccount = new AppAccount();
			password = EncryptTool.encode(EncryptTool.Type.ASE, password);
			appAccount.setAccountName(accountName);
			appAccount.setPassword(password);
			appAccount.setAppUserId(userId);
			appAccount.setChannelType(channelType);
			appAccount.setRegisterType(userType);
			appAccount.setCreateTime(date);
			appAccount.setCreateTimeLong(System.currentTimeMillis());
			appAccount.setUpdateTime(date);
			appAccount.setUpdateTimeLong(System.currentTimeMillis());
			appAccount.setState(StateEnum.OPEN.name());
			accountService.save(appAccount);

			if ("USER".equals(userType)){
				//初始化点数
				String repId = new AppUserPointRepService().save(new HashMap<>(2), userId, 0, "初始化会员", date);
				new AppUserPointService().save(userId,repId,0,date);
			}

			Map<String,Object> data = new HashMap<>(2);
			data.put("userId",userId);
			data.put("userName",accountName);
			bean.success(data);
		} catch (Exception e) {
			log.error("注册用户失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}

	private String getUserType(String channelType) {
		switch (channelType) {
			case "SYS":
			case "ADMIN":
				return "ADMIN";
			default:
				return "USER";
		}
	}
}
