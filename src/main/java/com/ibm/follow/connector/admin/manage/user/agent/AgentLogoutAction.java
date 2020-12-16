package com.ibm.follow.connector.admin.manage.user.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.entity.IbmHandicapAgent;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 代理登出
 * @Author: null
 * @Date: 2020-03-21 13:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/logout")
public class AgentLogoutAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        //盘口会员id
        String handicapAgentId =  dataMap.getOrDefault("handicapAgentId","").toString();
        try{
            IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
            IbmHandicapAgent handicapAgent=handicapAgentService.find(handicapAgentId);
            if(handicapAgent==null){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHaInfoService haInfoService=new IbmHaInfoService();
            String loginState = haInfoService.findLoginState(handicapAgentId);
            if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
                bean.putEnum(IbmCodeEnum.IBM_404_CUSTOMER_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }

            //用户登出清理数据
            new LogoutHaController().execute(handicapAgentId);



            bean.success();
        } catch (Exception e) {
            log.error("登出盘口代理失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
