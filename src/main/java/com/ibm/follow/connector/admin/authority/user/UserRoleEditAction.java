package com.ibm.follow.connector.admin.authority.user;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 操作人员角色信息修改
 *
 * @Author: Dongming
 * @Date: 2020-04-06 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/user/roleEdit")
public class UserRoleEditAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        if (!LogThreadLocal.isSuccess()) {
            return LogThreadLocal.findLog();
        }
        String userId = StringTool.getString(dataMap, "userId", null);
        String roleId = StringTool.getString(dataMap, "roleId", null);
        if (StringTool.isEmpty(userId, roleId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            AuthorityService authorityService = new AuthorityService();
            Map<String, Object> user = authorityService.findUser(userId);
            if (ContainerTool.isEmpty(user)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> role = authorityService.findRole(roleId);
            if (ContainerTool.isEmpty(role)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Map<String, Object> cardAdminInfo = new IbmCardAdminService().findAdminInfo(userId);
            if (ContainerTool.notEmpty(cardAdminInfo)) {
                bean.putEnum(IbmCodeEnum.IBM_404_CARD_ADMIN);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            authorityService.updateUserRole(userId, roleId, adminUser.getUserId());

           bean.success();
        } catch (Exception e) {
            log.error("操作人员角色信息修改 错误");
            bean.error(e.getMessage());
        }
        return bean;
    }
}
