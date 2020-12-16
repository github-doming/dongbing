package com.ibm.follow.connector.admin.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 修改上级菜单
 *
 * @Author: Dongming
 * @Date: 2020-04-01 19:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/menu/editParent")
public class AppMenuParentEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String menuId =  StringTool.getString(dataMap, "menuId", null);
		String parentId = StringTool.getString(dataMap, "parentId", null);
		if (StringTool.isEmpty(menuId,parentId)){
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			if (menuId.equals(parentId)){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> menuInfo = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menuInfo)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);

			}
			String path;
			String parent = StringTool.getString(menuInfo, "PARENT_", null);
			if (!parent.equals(parentId)) {
				Map<String, Object> parentMenu = authorityService.findMenu(parentId);
				if (ContainerTool.isEmpty(parentMenu)) {
					bean.putEnum(IbmCodeEnum.IBM_404_DATA);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
				}
				path = StringTool.getString(parentMenu, "PATH_", null).concat(menuId).concat(".");
			} else {
				return bean.success();
			}

			//更新菜单的路径
			authorityService.updateMenuParent(menuId,parentId,path);
			//更新子菜单的路径
			authorityService.updateMenuChild(menuId,path);


			bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			bean.error(e.getMessage());
		}
		return  bean;
	}
}
