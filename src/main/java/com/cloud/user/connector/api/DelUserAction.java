package com.cloud.user.connector.api;

import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_user.entity.AppUser;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * 删除用户
 *
 * @Author: Dongming
 * @Date: 2020-06-20 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 */
@ActionMapping(value = "/cloud/user/api/delUser", method = HttpConfig.Method.GET)
public class DelUserAction
		extends BaseApiAction {
	@Override
	protected Object execute() throws Exception {
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(token)) {
			return bean.put401Data();
		}
		try {

			AppUserService userService = new AppUserService();
			AppUser user = userService.findUserByToken(token);

			if (user == null) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			Date nowTime = new Date();
			userService.delUser(userId, nowTime);

			bean.success();
		} catch (Exception e) {
			log.error("删除用户失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
