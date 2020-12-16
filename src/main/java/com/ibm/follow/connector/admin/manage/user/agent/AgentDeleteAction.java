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
 * @Description: 删除代理
 * @Author: null
 * @Date: 2020-03-21 14:00
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/agent/delete")
public class AgentDeleteAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        //盘口会员id
        String handicapAgentId = StringTool.getString(dataMap.get("handicapAgentId"), "");


        try{
            IbmHandicapAgentService handicapAgentService=new IbmHandicapAgentService();
            IbmHandicapAgent handicapAgent=handicapAgentService.find(handicapAgentId);
            if(handicapAgent==null){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //会员登出状态
            logoutHappening(handicapAgentId);

            String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName()).concat(",删除代理信息:")
                    .concat(handicapAgentId);

            //删除盘口代理信息

            handicapAgentService.delHaInfo(handicapAgentId,desc);

            bean.success();
        } catch (Exception e) {
            log.error("删除盘口会员失败", e);
            bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private void logoutHappening(String handicapAgentId) throws Exception {

        String loginState = new IbmHaInfoService().findLoginState(handicapAgentId);
        if (StringTool.notEmpty(loginState) && IbmStateEnum.LOGIN.name().equals(loginState)) {
            //用户登出清理数据
            new LogoutHaController().execute(handicapAgentId);
        }
    }
}
