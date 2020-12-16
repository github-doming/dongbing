package com.ibm.follow.connector.admin.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;

import java.util.List;
import java.util.Map;

/**
 * 菜单列表
 *
 * @Author: Dongming
 * @Date: 2020-03-26 18:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/menu/list")
public class AppMenuListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		try {
			// 根据用户角色获取出应有的菜单列表
			AuthorityService authorityService = new AuthorityService();
			List<Map<String,Object>> menu = authorityService.listUserMenus(adminUser.getUserId());

			return bean.success(menu);
		} catch (Exception e) {
			log.error("菜单列表页面错误", e);
			return bean.error(e.getMessage());
		}
	}

}
