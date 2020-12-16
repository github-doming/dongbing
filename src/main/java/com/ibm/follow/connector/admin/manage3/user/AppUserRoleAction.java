package com.ibm.follow.connector.admin.manage3.user;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_manage_role.service.IbmManageRoleService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.common.servlet.ModelAndView;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取角色信息
 * @Author: wwj
 * @Date: 2019/11/13 16:17
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/role") public class AppUserRoleAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {

        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        try{
            Map<String, Object> data = new HashMap<>(1);
            IbmManageRoleService roleService = new IbmManageRoleService();
            List<Map<String, Object>> roles =  roleService.listShow();
            IbmHandicapService handicapService = new IbmHandicapService();
            roles.forEach(map->{

                try {
                    String haIds = (String) map.get("AGENT_HANDICAP_ID_");
                    if(!StringTool.isEmpty(haIds)){
                        map.put("agentHandicapNames",handicapService.findHandicapName(haIds));
                    }
                    String hmIds = (String) map.get("MEMBER_HANDICAP_ID_");
                    if(!StringTool.isEmpty(hmIds)){
                        map.put("memberHandicapNames",handicapService.findHandicapName(hmIds));
                    }

                    map.remove("ROW_NUM");
                    map.remove("AGENT_HANDICAP_ID_");
                    map.remove("MEMBER_HANDICAP_ID_");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            data.put("roleInfos",roles);

            return new ModelAndView("/pages/com/ibm/admin/manager/user/roleList.jsp", data);
        }catch (Exception e){
            log.error("错误", e);
            return new JsonResultBeanPlus().error(e.getMessage());
        }
    }
}
