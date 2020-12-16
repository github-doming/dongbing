package com.ibm.follow.connector.admin.manage3.user.role;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_manage_role.entity.IbmManageRole;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 修改角色盘口信息
 * @Author: wwj
 * @Date: 2019/11/15 17:01
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role/handicap/update") public class HandicapUpdateAction extends CommAdminAction {


    @Override
    public Object run() throws Exception {
        System.out.println("/ibm/admin/manage/user/role/handicap/update");
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String handicapMaxNum = request.getParameter("HANDICAP_MAX_NUM_");
        String roleId = request.getParameter("roleId");
        String handicapId = request.getParameter("handicapId");
        String category = request.getParameter("category");


        if (StringTool.isEmpty(roleId,handicapMaxNum,handicapId,category)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }

        int handicapOnlineMax = Integer.parseInt(handicapMaxNum);
        if(handicapOnlineMax<1){
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try{

            IbmManageRole manageRole =  new IbmManageRoleService().find(roleId);
            IbmTypeEnum type = IbmTypeEnum.valueOf(category);
            int changeNum=0;
//            if(IbmTypeEnum.AGENT.equal(type)){
//                IbmHaUserService haUserService = new IbmHaUserService();
//                changeNum = haUserService.updateOnlineNumByHandIicapId(handicapId,handicapOnlineMax,manageRole.getOnlineHaNumberMax());
//            }else{
//                IbmHmUserService hmUserService = new IbmHmUserService();
//                changeNum = hmUserService.updateOnlineNumByHandIicapId(handicapId,handicapOnlineMax,manageRole.getOnlineHmNumberMax());
//            }
            bean.success(changeNum);
        }catch (Exception e) {
            log.error("修改最大在线人数错误", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

}
