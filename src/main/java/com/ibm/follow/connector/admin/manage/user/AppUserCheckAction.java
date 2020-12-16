package com.ibm.follow.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 用户验证
 * @Author: wwj
 * @Date: 2020/5/8 10:32
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/check")
public class AppUserCheckAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserName = dataMap.getOrDefault("appUserName", "").toString();
		try {
			//获取用户信息
			String userId = new IbmAdminAppUserService().findUserIdByUserName(appUserName);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(IbmCodeEnum.IBM_403_USER);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			bean.success();
		} catch (Exception e) {
			log.error("用户验证错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
