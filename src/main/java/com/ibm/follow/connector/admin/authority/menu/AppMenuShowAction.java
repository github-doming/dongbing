package com.ibm.follow.connector.admin.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单项展示
 *
 * @Author: Dongming
 * @Date: 2020-04-01 14:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/menu/show")
public class AppMenuShowAction extends CommAdminAction {
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
			if (ContainerTool.isEmpty(menu)){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			menu.put("PARENT_NAME_",authorityService.findMenu(menu.get("PARENT_").toString()).get("APP_MENU_NAME_"));
			Map<String, Object> data = new HashMap<>(1);
			data.put("menu", menu);

			bean.success(data);
		} catch (Exception e) {
			log.error("菜单项展示错误", e);
			return bean.error(e.getMessage());
		}

		return bean;
	}
}
