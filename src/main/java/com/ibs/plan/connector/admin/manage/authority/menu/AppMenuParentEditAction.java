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

import java.util.Map;

/**
 * 修改上级菜单
 *
 * @Author: Dongming
 * @Date: 2020-04-01 19:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/menu/editParent",method = HttpConfig.Method.POST)
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			if (menuId.equals(parentId)){
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> menuInfo = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menuInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);

			}
			String path;
			String parent = StringTool.getString(menuInfo, "PARENT_", null);
			if (!parent.equals(parentId)) {
				Map<String, Object> parentMenu = authorityService.findMenu(parentId);
				if (ContainerTool.isEmpty(parentMenu)) {
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
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


			return bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			throw e;
		}
	}
}
