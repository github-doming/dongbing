package com.ibm.connector.admin.user;

import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.core.user.LoginAction;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理端 登录
 *
 * @Author: Dongming
 * @Date: 2020-03-26 17:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/user/login")
public class AdminLoginAction
		extends LoginAction {

	public AdminLoginAction() {
		super(ChannelTypeEnum.ADMIN, IbmTypeEnum.ADMIN);
	}

	@Override
	public Object run() throws Exception {
		JsonResultBean runResult = (JsonResultBean) super.run();
		if (!runResult.isSuccess()) {
			return runResult;
		}
		String token = runResult.getData().toString();
		AuthorityService authorityService = new AuthorityService();
		Map<String,Object> userRole = authorityService.findUserRole(appUser.getAppUserId());
		String roleCode = StringTool.getString(userRole,"APP_GROUP_CODE_","");

		Map<String,Object> data = new HashMap<>(2);
		data.put("token",token);
		data.put("roleCode",roleCode);
		runResult.setData(data);
		return runResult;
	}
}
