package com.cloud.user.connector.api;
import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_token.service.AppTokenService;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;
/**
 * 验证首页登录进入的用户
 *
 * @Author: Dongming
 * @Date: 2020-06-16 11:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/verifyToken", method = HttpConfig.Method.GET) public class VerifyTokenAction
		extends BaseApiAction {
	@Override public Object execute() throws Exception {
		String accountName = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String channelType = StringTool.trimAll(StringTool.getString(jsonData, "channelType", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(accountName, token)) {
			return bean.put401Data();
		}
		try {
			String userId = new AppTokenService().findUserIdByToken(token, channelType);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			Map<String, Object> info = new AppUserService().findInfoByName(userId, accountName);
			if (!StateEnum.OPEN.name().equals(info.get("STATE_"))) {
				bean.putEnum(ReturnCodeEnum.app404LoginDisable);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			Map<String,Object> data = new HashMap<>(2);
			data.put("userId",userId);
			data.put("nickname",info.get("NICKNAME_"));
			data.put("userType",info.get("channelType"));
			bean.success(data);
		} catch (Exception e) {
			log.error("验证首页登录进入的用户失败",e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}

		return bean;
	}
}
