package com.ibs.plan.connector.admin.manage.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_client_capacity.service.IbspClientCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_handicap_capacity.service.IbspClientHandicapCapacityService;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
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
@ActionMapping(value = "/ibs/sys/manage/client/del", method = HttpConfig.Method.POST)
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
            IbspClientService clientService=new IbspClientService();
            Map<String,Object> clientInfo=clientService.findInfo(clientCode);
            //不存在或者客户机状态不为open,close
            if (ContainerTool.isEmpty(clientInfo) ||!IbsStateEnum.CLOSE.name().equals(clientInfo.get("STATE_"))) {
                bean.putEnum(CodeEnum.IBS_401_DATA);
                bean.putSysEnum(CodeEnum.CODE_401);
                return bean;
            }
            String clientId=clientInfo.get("IBM_CLIENT_ID_").toString();


            List<String> handicapMemberIds=new IbspClientHmService().listHmIds(clientId);
            LogoutHmController logoutHmController=new LogoutHmController();
            for(String handicapMemberId:handicapMemberIds){
                logoutHmController .execute(handicapMemberId,new Date());
            }
            JSONObject content = new JSONObject();
            content.put("METHOD_", IbsMethodEnum.CLIENT_DEL.name());
            String result= RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
            if (StringTool.isEmpty(result)) {
                bean.putEnum(CodeEnum.IBS_401_MESSAGE);
                bean.putSysEnum(CodeEnum.CODE_401);
                return bean;
            }
            if (!Boolean.parseBoolean(result)) {
                bean.putEnum(CodeEnum.IBS_403_MQ);
                bean.putSysEnum(CodeEnum.CODE_403);
                return bean;
            }
            Date nowTime=new Date();

            //删除或注销客户端
            clientService.cancelClient(clientId, nowTime, IbsStateEnum.CANCEL);

            //清理容量
            new IbspClientCapacityService().cancelClient(clientId, nowTime);
            new IbspClientHandicapCapacityService().cancelClient(clientId, nowTime);

            bean.success();
        } catch (Exception e) {
            log.error("客户端删除错误", e);
            return bean.error(e.getMessage());
        }
        return bean;
    }
}
