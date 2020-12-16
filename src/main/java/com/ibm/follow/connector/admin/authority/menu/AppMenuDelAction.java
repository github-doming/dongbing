package com.ibm.follow.connector.admin.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

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
@ActionMapping(value = "/ibm/admin/authority/menu/del")
public class AppMenuDelAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String menuId =  StringTool.getString(dataMap, "menuId", null);
		if (StringTool.isEmpty(menuId,menuId)){
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {

			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> menuInfo = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menuInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			deleteMenu(menuId,authorityService);
			bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 删除菜单
	 * @param menuId 菜单主键
	 */
	private void deleteMenu(String menuId,AuthorityService authorityService) throws SQLException {
		authorityService.deleteMenu(menuId,adminUser.getUserId());
		List<String> childMenuIds = authorityService.listChildMenuId(menuId);
		for (String childMenuId :childMenuIds){
			deleteMenu(childMenuId,authorityService);
		}
	}
}
