package com.cloud.user.connector.core;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_token.service.AppTokenService;
import org.doming.core.tools.StringTool;
/**
 * @Author: Dongming
 * @Date: 2020-06-15 17:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UpdatePasswordAction extends BaseMvcData {
	@Override public Object run() throws Exception {
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		//旧密码
		String oldPassword = StringTool.trimAll(StringTool.getString(dataMap, "oldPassword", ""));
		//新密码
		String newPassword = StringTool.trimAll(StringTool.getString(dataMap, "newPassword", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(token, oldPassword, newPassword)) {
			return bean.put401Data();
		}
		if (StringTool.isEmpty(oldPassword, newPassword) || oldPassword.equals(newPassword)) {
			return bean.put401Data();
		}
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if (!oldPassword.matches(regExpPwd) || !newPassword.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		try {
			//密码加密
			newPassword = EncryptTool.encode(EncryptTool.Type.ASE,newPassword);
			if (StringTool.isEmpty(newPassword)) {
				return bean.put401Data();
			}
			// 找到AppUser
			String userId = new AppTokenService().findUserIdByToken(token);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}

			//验证密码是否出错
			AppAccountService accountService = new AppAccountService();
			String accountId = accountService.findIdByPassword(userId, oldPassword);
			accountService.update(accountId,newPassword);


			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}

		return bean;
	}
}
