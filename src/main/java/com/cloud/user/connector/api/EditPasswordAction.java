package com.cloud.user.connector.api;
import c.a.util.core.enums.ReturnCodeEnum;
import com.cloud.common.core.BaseApiAction;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.common.tool.EncryptTool;
import com.cloud.user.app_account.service.AppAccountService;
import com.cloud.user.app_token.service.AppTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
/**
 * 修改用户密码
 *
 * @Author: Dongming
 * @Date: 2020-06-16 11:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/user/api/editPassword", method = HttpConfig.Method.GET) public class EditPasswordAction
		extends BaseApiAction {
	@Override public Object execute() throws Exception {
		String userId = StringTool.trimAll(StringTool.getString(jsonData, "userId", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		String editUserId = StringTool.trimAll(StringTool.getString(jsonData, "editUserId", ""));
		String userPassWord = StringTool.trimAll(StringTool.getString(jsonData, "userPassWord", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty( userId, token,editUserId,userPassWord)) {
			return bean.put401Data();
		}
		String regExpPwd = "[a-zA-Z](?![a-zA-Z]+$)\\w{5,20}";
		if ( !userPassWord.matches(regExpPwd)) {
			bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
			bean.putSysEnum(ReturnCodeEnum.code409);
			return bean;
		}
		userPassWord = EncryptTool.encode(EncryptTool.Type.ASE,userPassWord);
		if (StringTool.isEmpty(userPassWord)) {
			return bean.put401Data();
		}
		try {
			String valiUserId = new AppTokenService().findUserIdByToken(token);
			if (StringTool.isEmpty(valiUserId) ) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return bean;
			}
			//验证用户是否出错
			if (!userId.equals(valiUserId)){
				bean.putEnum(CodeEnum.CLOUD_403_DATA_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			//修改密码
			AppAccountService accountService = new AppAccountService();
			accountService.update(editUserId,userPassWord,userId);
			bean.success(userId);
		} catch (Exception e) {
			log.error("获取session失败，{}", e.getMessage());
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}

		return bean;
	}
}
