package com.ibm.follow.connector.admin.manage3.handicap.agent;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapAgentService;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 登出代理
 * @Author: null
 * @Date: 2020-03-11 17:27
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/agent/logout", method = HttpConfig.Method.GET)
public class AgentLogoutAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        String handicapAgentId = request.getParameter("handicapAgentId");
        if (StringTool.isEmpty(handicapAgentId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        try {
            IbmAdminHandicapAgentService handicapAgentService = new IbmAdminHandicapAgentService();
            if (!handicapAgentService.isExist(handicapAgentId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHaInfoService haInfoService = new IbmHaInfoService();
            String loginState = haInfoService.findLoginState(handicapAgentId);
            if (StringTool.isEmpty(loginState) || !IbmStateEnum.LOGIN.name().equals(loginState)) {
                bean.putEnum(IbmCodeEnum.IBM_404_CUSTOMER_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //用户登出清理数据
            new LogoutHaController().execute(handicapAgentId);

            bean.success();
        } catch (Exception e) {
            log.error("登出盘口代理信息错误", e);
            throw e;
        }
        return bean;
    }
}
