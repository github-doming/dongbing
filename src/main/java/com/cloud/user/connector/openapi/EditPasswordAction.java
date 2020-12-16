package com.cloud.user.connector.openapi;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * 用户修改密码
 * @Author: Dongming
 * @Date: 2020-06-19 18:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/out/password", method = HttpConfig.Method.POST) public class EditPasswordAction
		extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		String newPwd = StringTool.trimAll(StringTool.getString(dataMap, "newPwd", ""));
		String confirmPwd = StringTool.trimAll(StringTool.getString(dataMap, "confirmPwd", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(newPwd) ) {
			return bean.put401Data();
		}
		if(!newPwd.equalsIgnoreCase(confirmPwd)){
			bean.putEnum(CodeEnum.CLOUD_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if ( !newPwd.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		newPwd = EncryptTool.encode(EncryptTool.Type.ASE,newPwd);
		if (StringTool.isEmpty(newPwd)) {
			return bean.put401Data();
		}

		try {

			AppAccountService accountService = new AppAccountService();
			accountService.update(userId,newPwd,userId);

			return bean.success();
		} catch (Exception e) {
			log.error("检查是否存在用户名失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
