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

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 菜单项删除
 *
 * @Author: Dongming
 * @Date: 2020-04-01 20:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/menu/del",method = HttpConfig.Method.POST)
public class AppMenuDelAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String menuId = StringTool.getString(dataMap, "menuId", null);
		if (StringTool.isEmpty(menuId, menuId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {

			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> menuInfo = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menuInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			deleteMenu(menuId, authorityService);


			return bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			throw e;
		}
	}

	/**
	 * 删除菜单
	 *
	 * @param menuId 菜单主键
	 */
	private void deleteMenu(String menuId, AuthorityService authorityService) throws SQLException {
		authorityService.deleteMenu(menuId, appUserId);
		List<String> childMenuIds = authorityService.listChildMenuId(menuId);
		for (String childMenuId : childMenuIds) {
			deleteMenu(childMenuId, authorityService);
		}
	}
}
