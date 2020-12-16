package com.ibm.follow.connector.admin.manage3.user.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.entity.IbmManageRole;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/11/13 17:11
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */

@ActionMapping(value = "/ibm/admin/manage/user/handicap/roleList") public class HandicpListRoleActipn
        extends CommAdminAction {

    @Override
    public Object run() throws Exception {

        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String key = request.getParameter("key");
        String userCategory = request.getParameter("userCategory");

        IbmTypeEnum category = IbmTypeEnum.valueOf(userCategory);

        try{

            IbmManageRoleService roleService = new IbmManageRoleService();
            IbmManageRole manageRole = roleService.find(key);
            IbmHandicapService handicapService = new IbmHandicapService();
            String ids ;
            List<Map<String, Object>> handicapInfos=new ArrayList<>();
//            if(IbmTypeEnum.AGENT.equal(category)){
//                ids= manageRole.getAgentHandicapId();
//                handicapInfos = handicapService.findHandicap(ids);
//                handicapInfos.forEach(map->{
//                    map.put("ONLINE_NUMBER_MAX_",manageRole.getOnlineHaNumberMax());
//                });
//            }else{
//                ids= manageRole.getMemberHandicapId();
//                handicapInfos = handicapService.findHandicap(ids);
//                handicapInfos.forEach(map->{
//                    map.put("ONLINE_NUMBER_MAX_",manageRole.getOnlineHmNumberMax());
//                });
//            }

            bean.success(handicapInfos);
        } catch (Exception e) {
            log.error("获取盘口列表错误", e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
