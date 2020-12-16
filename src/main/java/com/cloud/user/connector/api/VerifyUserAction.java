package com.cloud.user.connector.api;
import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_user.service.AppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;
/**
 * 验证用户信息
 *
 * @Author: Dongming
 * @Date: 2020-06-20 14:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 */
@ActionMapping(value = "/cloud/user/api/verifyUser", method = HttpConfig.Method.GET)
public class VerifyUserAction extends BaseApiAction {
	@Override protected Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String userName = StringTool.trimAll(StringTool.getString(jsonData, "userName", ""));
		String userType = StringTool.trimAll(StringTool.getString(jsonData, "userType", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(userId, userName, userType)) {
			return bean.put401Data();
		}
		try {
			Map<String, Object> info = new AppUserService().findInfoByName(userId, userName, userType);
			if (!StateEnum.OPEN.name().equals(info.get("STATE_"))) {
				bean.putEnum(ReturnCodeEnum.app404LoginDisable);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			Map<String, Object> data = new HashMap<>(2);
			data.put("userId", userId);
			data.put("nickname", info.get("NICKNAME_"));
			bean.success(data);
		} catch (Exception e) {
			log.error("获取session失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
