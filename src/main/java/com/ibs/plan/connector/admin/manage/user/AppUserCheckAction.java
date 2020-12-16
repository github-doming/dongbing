package com.ibs.plan.connector.admin.manage.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.user.IbsAdminAppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 用户验证
 * @Author: wwj
 * @Date: 2020/5/8 10:32
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/check")
public class AppUserCheckAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String appUserName = StringTool.getString(dataMap,"appUserName", "");
		try {
			//获取用户信息
			String userId = new IbsAdminAppUserService().findUserIdByUserName(appUserName);
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.IBS_403_USER);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean.toString();
			}
			bean.success();
		} catch (Exception e) {
			log.error("用户验证错误", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
