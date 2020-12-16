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
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * 新增新的菜单项
 *
 * @Author: Dongming
 * @Date: 2020-03-31 15:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/menu/add",method = HttpConfig.Method.POST)
public class AppMenuSaveAction extends CommAdminAction {
    private String parentId, menuName, menuCode, url, permissionCode, pic, state, menuType;
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
        if (StringTool.isEmpty(url) && StringTool.isEmpty(permissionCode)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        String menuKey = StringTool.isEmpty(url) ? permissionCode : url;
        try {
            AuthorityService authorityService = new AuthorityService();

            //查询菜单是否存在
            if (authorityService.checkMenu(menuKey)) {
                bean.putEnum(CodeEnum.IBS_403_EXIST);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            Map<String, Object> parentMenu = authorityService.findMenu(parentId);
            if (ContainerTool.isEmpty(parentMenu)) {
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            String menuId = RandomTool.getNumLetter(4);
            //校验至id不重复
            while (authorityService.findMenu(menuId) != null) {
                menuId = RandomTool.getNumLetter(4);
            }

            Menu menu = new Menu(menuId);
            menu.attr(menuName, menuCode, url, permissionCode, pic, sn, state);
            String path = parentMenu.get("PATH_").toString().concat(menuId).concat(".");
            authorityService.saveMenu(menu, parentId, path, permissionGrade, menuType, getTenant(),appUserId);

            // TODO: 2020/4/4 将目录分配至用户所在角色
            authorityService.saveMenu2Role(menuId, appUserId);
            return bean.success();
        } catch (Exception e) {
            log.error("新增新的菜单项错误", e);
            throw e;
        }
    }

    private boolean valiParameters() {
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
        return StringTool.isEmpty(parentId, menuName, state);
    }
}
