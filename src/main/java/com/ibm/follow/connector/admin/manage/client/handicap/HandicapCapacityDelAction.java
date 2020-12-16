package com.ibm.follow.connector.admin.manage.client.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHaController;
import com.ibm.follow.servlet.cloud.core.controller.process.LogoutHmController;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * @Description: 删除盘口容量信息
 * @Author: null
 * @Date: 2020-04-11 16:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/handicap/del", method = HttpConfig.Method.POST)
public class HandicapCapacityDelAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode","").toString();
        String handicapCode = dataMap.getOrDefault("handicapCode","").toString();
        try{
            IbmClientService clientService=new IbmClientService();
            Map<String,Object> clientInfo=clientService.findInfo(clientCode);
            //不存在或者客户机状态不为CLOSE
            if (ContainerTool.isEmpty(clientInfo) ||!IbmStateEnum.CLOSE.name().equals(clientInfo.get("STATE_"))) {
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return bean.toJsonString();
            }

            List<String> handicapAgentIds=new IbmClientHaService().findHaIds(clientCode,handicapCode);
            LogoutHaController logoutHaController=new LogoutHaController();
            for(String handicapAgentId:handicapAgentIds){
                //用户登出清理数据
                logoutHaController.execute(handicapAgentId);
            }
            List<String> handicapMemberIds=new IbmClientHmService().findHmIds(clientCode,handicapCode);
            LogoutHmController logoutHmController=new LogoutHmController();
            for(String handicapMemberId:handicapMemberIds){
                logoutHmController.execute(handicapMemberId);
            }
            JSONObject content = new JSONObject();
            content.put("handicapCode",handicapCode);
            content.put("METHOD_", IbmMethodEnum.HANDICAP_CAPACITY_DEL.name());
            String result= RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
            if (StringTool.isEmpty(result)) {
                bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return bean.toJsonString();
            }
            if (!Boolean.parseBoolean(result)) {
                bean.putEnum(IbmCodeEnum.IBM_403_MQ);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean.toJsonString();
            }

            bean.success();
        } catch (Exception e) {
            log.error("盘口容量删除错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
