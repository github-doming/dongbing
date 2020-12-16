package com.ibs.plan.connector.admin.manage.authority.menu;

import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import com.ibs.plan.connector.admin.service.authority.Menu;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * 菜单项修改
 *
 * @Author: Dongming
 * @Date: 2020-04-01 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/menu/edit",method = HttpConfig.Method.POST)
public class AppMenuEditAction extends CommAdminAction {
	private String menuId, parentId, menuName, menuCode, url, permissionCode, pic, state, menuType;
	private Integer sn, permissionGrade;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		AuthorityService authorityService = new AuthorityService();
		try {
			Map<String, Object> menuInfo = authorityService.findMenu(menuId);
			if (ContainerTool.isEmpty(menuInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);

			}
			Menu menu = new Menu(menuId);
			menu.attr(StringTool.getString(menuInfo, "APP_MENU_NAME_", null),
					StringTool.getString(menuInfo, "APP_MENU_CODE_", null),
					StringTool.getString(menuInfo, "ADMIN_URL_", null),
					StringTool.getString(menuInfo, "PERMISSION_CODE_", null),
					StringTool.getString(menuInfo, "ADMIN_PIC_", null),
					NumberTool.getInteger(menuInfo.get("SN_"), 0),
					StringTool.getString(menuInfo, "STATE_", null));


			String path = null;
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
				parent = null;
			}
			setMenuAttr(menu);
			authorityService.updateMenu(menu, parent, path, permissionGrade, menuType, appUserId);

			//更新子菜单的路径
			if (StringTool.notEmpty(path)) {
				authorityService.updateMenuChild(menuId, path);
			}
			bean.success();
		} catch (Exception e) {
			log.error("菜单项修改错误");
			throw e;
		}
		return bean;
	}


	private void setMenuAttr(Menu menu) {
		if (menu.getMenuName().equals(menuName)) {
			menu.setMenuName(null);
		} else {
			menu.setMenuName(menuName);
		}
		if (menu.getMenuCode().equals(menuCode)) {
			menu.setMenuCode(null);
		} else {
			menu.setMenuCode(menuCode);
		}
		if (menu.getMenuUrl().equals(url)) {
			menu.setMenuUrl(null);
		} else {
			menu.setMenuUrl(url);
		}
		if (menu.getPermissionCode().equals(permissionCode)) {
			menu.setPermissionCode(null);
		} else {
			menu.setPermissionCode(permissionCode);
		}
		if (menu.getMenuPic().equals(pic)) {
			menu.setMenuPic(null);
		} else {
			menu.setMenuPic(pic);
		}
		if (menu.getSn().equals(sn)) {
			menu.setSn(null);
		} else {
			menu.setSn(sn);
		}
		if (menu.getState().equals(state)) {
			menu.setState(null);
		} else {
			menu.setState(state);
		}
	}

	private boolean valiParameters() {
		menuId = StringTool.getString(dataMap, "menuId", null);
		//父级菜单
		parentId = StringTool.getString(dataMap, "parentId", null);
		menuName = StringTool.getString(dataMap, "menuName", null);
		menuCode = StringTool.getString(dataMap, "menuCode", null);
		url = StringTool.getString(dataMap, "url", null);
		permissionCode = StringTool.getString(dataMap, "permissionCode", null);
		permissionGrade = NumberTool.getInteger(dataMap.get("permissionGrade"), 0);
		pic = StringTool.getString(dataMap, "pic", null);
		sn = NumberTool.getInteger(dataMap.get("sn"), 0);
		state = StringTool.getString(dataMap, "state", null);
		menuType = StringTool.getString(dataMap, "menuType", null);
		return StringTool.isEmpty(parentId, menuId, state);
	}
}
