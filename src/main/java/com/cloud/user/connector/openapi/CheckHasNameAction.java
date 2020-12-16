package com.cloud.user.connector.openapi;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_user.service.AppUserService;
import com.cloud.user.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
/**
 * 检查是否存在用户名
 * @Author: Dongming
 * @Date: 2020-06-19 18:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/out/checkHasName", method = HttpConfig.Method.GET) public class CheckHasNameAction
		extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String checkName = StringTool.trimAll(StringTool.getString(dataMap, "checkName", ""));
		String userType = StringTool.trimAll(StringTool.getString(dataMap, "userType", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(userType, checkName)) {
			return bean.put401Data();
		}
		try {
			//校验被检查的用户
			String checkUserId =  new AppAccountService().findUserIdByName(checkName);
			if (StringTool.isEmpty(checkUserId)) {
				bean.putEnum(ReturnCodeEnum.app404Login);
				bean.putSysEnum(ReturnCodeEnum.code404);
				return bean;
			}
			//校验被检查的用户状态
			if (StateEnum.OPEN.name().equals(new AppUserService().findState(checkUserId,userType))) {
				return bean.success(checkUserId);
			}
			bean.putEnum(ReturnCodeEnum.app404LoginDisable);
			bean.putSysEnum(ReturnCodeEnum.code400);
			return bean;
		} catch (Exception e) {
			log.error("检查是否存在用户名失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
