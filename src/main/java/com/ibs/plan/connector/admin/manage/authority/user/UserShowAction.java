package com.ibs.plan.connector.admin.manage.authority.user;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 展示操作人信息
 *
 * @Author: Dongming
 * @Date: 2020-04-06 16:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/user/show")
public class UserShowAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String userId = StringTool.getString(dataMap, "userId", null);

		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> user = authorityService.findUser(userId);
			if (ContainerTool.isEmpty(user)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			return bean.success(user);
		} catch (Exception e) {
			log.error("展示操作人信息错误", e);
			return bean.error(e.getMessage());
		}
	}
}
