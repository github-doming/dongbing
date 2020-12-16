package com.ibm.follow.connector.admin.manage.client;

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
import com.ibm.follow.servlet.cloud.ibm_client_capacity.service.IbmClientCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_handicap_capacity.service.IbmClientHandicapCapacityService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 删除客户端
 * @Author: null
 * @Date: 2020-04-11 14:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/client/del", method = HttpConfig.Method.POST)
public class ClientDelAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String clientCode = dataMap.getOrDefault("clientCode","").toString();
        try{
            IbmClientService clientService=new IbmClientService();
            Map<String,Object> clientInfo=clientService.findInfo(clientCode);
            //不存在或者客户机状态不为open,close
            if (ContainerTool.isEmpty(clientInfo) ||!IbmStateEnum.CLOSE.name().equals(clientInfo.get("STATE_"))) {
                bean.putEnum(IbmCodeEnum.IBM_401_DATA);
                bean.putSysEnum(IbmCodeEnum.CODE_401);
                return bean.toJsonString();
            }
            String clientId=clientInfo.get("IBM_CLIENT_ID_").toString();
            List<String> handicapAgentIds=new IbmClientHaService().listHaIds(clientId);
            LogoutHaController logoutHaController=new LogoutHaController();
            for(String handicapAgentId:handicapAgentIds){
                logoutHaController.execute(handicapAgentId);
            }

            List<String> handicapMemberIds=new IbmClientHmService().listHmIds(clientId);
            LogoutHmController logoutHmController=new LogoutHmController();
            for(String handicapMemberId:handicapMemberIds){
                logoutHmController .execute(handicapMemberId);
            }
            JSONObject content = new JSONObject();
            content.put("METHOD_", IbmMethodEnum.CLIENT_DEL.name());
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
            Date nowTime=new Date();

            //删除或注销客户端
            clientService.cancelClient(clientId, nowTime, IbmStateEnum.CANCEL);

            //清理容量
            new IbmClientCapacityService().cancelClient(clientId, nowTime);
            new IbmClientHandicapCapacityService().cancelClient(clientId, nowTime);

            bean.success();
        } catch (Exception e) {
            log.error("客户端删除错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }
}
