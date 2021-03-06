package com.ibs.plan.connector.admin.manage.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询菜单的上级菜单
 *
 * @Author: Dongming
 * @Date: 2020-04-09 15:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/menu/showParent",method = HttpConfig.Method.GET)
public class AppMenuParentShowAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String menuId = StringTool.getString(dataMap, "menuId", null);
		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> menu = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menu)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			List<Map<String, Object>> menuList = authorityService.listUserMenus(appUserId);
			Map<String, Object> data = new HashMap<>(1);
			data.put("PARENT_ID_", menu.get("PARENT_"));
			data.put("PARENT_NAME_", authorityService.findMenu(menu.get("PARENT_").toString()).get("APP_MENU_NAME_"));
			data.put("menuList", menuList);

			bean.success(data);
		} catch (Exception e) {
			log.error("菜单项展示错误", e);
			return bean.error(e.getMessage());
		}

		return bean;
	}
}
