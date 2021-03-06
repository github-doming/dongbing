package com.ibm.follow.connector.admin.authority.user;

import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import com.ibm.follow.servlet.cloud.ibm_card_admin_price.service.IbmCardAdminPriceService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 操作人员删除
 *
 * @Author: Dongming
 * @Date: 2020-04-06 17:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/authority/user/del")
public class UserDelAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        if (!LogThreadLocal.isSuccess()) {
            return LogThreadLocal.findLog();
        }
        String userId = StringTool.getString(dataMap, "userId", null);

        try {
            AuthorityService authorityService = new AuthorityService();
            Map<String, Object> user = authorityService.findUser(userId);
            if (ContainerTool.isEmpty(user)) {
                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            if (user.get("APP_GROUP_CODE_").toString().contains("CARD")) {
                bean.putEnum(IbmCodeEnum.IBM_404_CARD_ADMIN);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;

            }

            authorityService.deleteUser(userId, adminUser.getUserId());
            new IbmCardAdminService().updateState(userId, IbmStateEnum.DEL.name());
            new IbmCardAdminPriceService().delByUserId(userId);

           bean.success();
        } catch (Exception e) {
            log.error("操作人员删除 错误");
            bean.error(e.getMessage());
        }
        return bean;
    }
}
