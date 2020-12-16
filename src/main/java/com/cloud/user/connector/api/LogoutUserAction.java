package com.cloud.user.connector.api;
import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_token.service.AppTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * 用户登出
 *
 * @Author: Dongming
 * @Date: 2020-06-20 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 */
@ActionMapping(value = "/cloud/user/api/logoutUser", method = HttpConfig.Method.GET) public class LogoutUserAction
		extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(token)) {
			return bean.put401Data();
		}
		try {
			// 找到AppUser
			AppTokenService tokenService = new AppTokenService();
			String userId = tokenService.findUserIdByToken(token);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			tokenService.logout(token);
			bean.success();
		} catch (Exception e) {
			log.error("注销登录失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
