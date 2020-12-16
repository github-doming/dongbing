package com.ibm.follow.connector.admin.manage3.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.entity.IbmManageRole;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 查找角色未拥有的盘口
 * @Author: wwj
 * @Date: 2019/11/13 18:06
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role/handicap/no") public class HandicapNoAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String roleId = request.getParameter("roleId");

        String userCategory = request.getParameter("userCategory");
        if (StringTool.isEmpty(roleId,userCategory)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);
        try {
            IbmHandicapService handicapService = new IbmHandicapService();
            IbmManageRoleService roleService = new IbmManageRoleService();
            IbmManageRole manageRole = roleService.find(roleId);
            String ids = roleService.getRoleHandicp(manageRole.getRoleLevel());
            List<Map<String, Object>> handicapInfos = handicapService.listNoHandicap(ids,category);
            if (ContainerTool.isEmpty(handicapInfos)){
                return bean.fail("已添加所有盘口，请勿重复添加。");
            }
            bean.success(handicapInfos);
        } catch (Exception e) {
            log.error("查找不存在的盘口错误", e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
